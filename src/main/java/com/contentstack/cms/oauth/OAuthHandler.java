package com.contentstack.cms.oauth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import com.contentstack.cms.models.OAuthConfig;
import com.contentstack.cms.models.OAuthTokens;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import lombok.Getter;
import lombok.Setter;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Handles OAuth 2.0 authentication flow for Contentstack
 * Supports both traditional OAuth flow with client secret and PKCE flow
 */
@Getter
public class OAuthHandler {
    private OkHttpClient httpClient;
    private final OAuthConfig config;
    private final Gson gson;
    private final Object tokenLock = new Object();

    private String codeVerifier;
    private String codeChallenge;
    private String state;
    
    @Getter @Setter
    private OAuthTokens tokens;

    /**
     * Creates a new OAuth handler instance
     * @param httpClient HTTP client for making requests
     * @param config OAuth configuration
     */
    public OAuthHandler(OkHttpClient httpClient, OAuthConfig config) {
        this.httpClient = httpClient;
        this.config = config;
        this.gson = new Gson();

        // Validate config before proceeding
        config.validate();
        
        // Only generate PKCE codeVerifier if clientSecret is not provided
        if (config.getClientSecret() == null || config.getClientSecret().trim().isEmpty()) {
            this.codeVerifier = generateCodeVerifier();
            this.codeChallenge = null;
        }
    }


    private Request.Builder _getHeaders() {
        Request.Builder builder = new Request.Builder()
            .header("Content-Type", "application/x-www-form-urlencoded");
            
        // Only add authorization header for non-token endpoints
        if (tokens != null && tokens.getAccessToken() != null) {
            builder.header("authorization", "Bearer " + tokens.getAccessToken());
        }
        return builder;
    }

    /**
     * Generates a cryptographically secure code verifier for PKCE
     * @return A random URL-safe string between 43-128 characters
     */
    private String generateCodeVerifier() {
        final int CODE_VERIFIER_LENGTH = 96;  
        final String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
        SecureRandom random = new SecureRandom();
        StringBuilder verifier = new StringBuilder();
        for (int i = 0; i < CODE_VERIFIER_LENGTH; i++) {
            verifier.append(charset.charAt(random.nextInt(charset.length())));
        }
        return verifier.toString();
    }

    /**
     * Generates code challenge from code verifier using SHA-256
     * @param verifier The code verifier to hash
     * @return BASE64URL-encoded SHA256 hash of the verifier
     */
    private String generateCodeChallenge(String verifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(verifier.getBytes(StandardCharsets.UTF_8));
            
            String base64String = Base64.getEncoder().encodeToString(hash);
            return base64String
                    .replace('+', '-')
                    .replace('/', '_')
                    .replaceAll("=+$", "");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }


    /**
     * Starts the OAuth authorization flow
     * @return Authorization URL for the user to visit
     */
    public String authorize() {
        try {
            String baseUrl = config.getFormattedAuthorizationEndpoint();

            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            urlBuilder.append("?response_type=").append(config.getResponseType())
                     .append("&client_id=").append(URLEncoder.encode(config.getClientId(), "UTF-8"))
                     .append("&redirect_uri=").append(URLEncoder.encode(config.getRedirectUri(), "UTF-8"))
                     .append("&app_id=").append(URLEncoder.encode(config.getAppId(), "UTF-8"));
            
            // Add scope if provided
            if (config.getScope() != null && !config.getScope().trim().isEmpty()) {
                urlBuilder.append("&scope=").append(URLEncoder.encode(config.getScope(), "UTF-8"));
            }

            if (config.getClientSecret() != null && !config.getClientSecret().trim().isEmpty()) {
                return urlBuilder.toString();
            } else {
                // PKCE flow: add code_challenge
                this.codeChallenge = generateCodeChallenge(this.codeVerifier);
                urlBuilder.append("&code_challenge=").append(URLEncoder.encode(this.codeChallenge, "UTF-8"))
                         .append("&code_challenge_method=S256");
                return urlBuilder.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode URL parameters", e);
        }
    }

    /**
     * Exchanges authorization code for tokens
     * @param code The authorization code from callback
     * @return Future containing the tokens
     */
    public CompletableFuture<OAuthTokens> exchangeCodeForToken(String code) {
        if (code == null || code.trim().isEmpty()) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("Authorization code cannot be null or empty"));
        }

        System.out.println("\nExchanging authorization code for tokens...");
        return CompletableFuture.supplyAsync(() -> {
            try {
                FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("grant_type", "authorization_code")
                    .add("code", code.trim())
                    .add("redirect_uri", config.getRedirectUri())
                    .add("client_id", config.getClientId())
                    .add("app_id", config.getAppId());

                // Choose between client_secret and code_verifier like JS SDK
                if (config.getClientSecret() != null) {
                    formBuilder.add("client_secret", config.getClientSecret());
                } else {
                    formBuilder.add("code_verifier", this.codeVerifier);
                }

                Request request = _getHeaders()
                    .url(config.getTokenEndpoint())
                    .post(formBuilder.build())
                    .build();

                return executeTokenRequest(request);
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException("Failed to exchange code for tokens", e);
            }
        });
    }

    /**
     * Saves tokens from a successful OAuth response
     * @param tokens The tokens to save
     */
    private void _saveTokens(OAuthTokens tokens) {
        synchronized (tokenLock) {
            this.tokens = tokens;
        }
    }

    private OAuthTokens _getTokens() {
        synchronized (tokenLock) {
            return this.tokens;
        }
    }

    /**
     * Refreshes the access token using the refresh token
     * @return Future containing the new tokens
     */
    public CompletableFuture<OAuthTokens> refreshAccessToken() {
        // Check if we have tokens and refresh token
        if (tokens == null) {
            return CompletableFuture.failedFuture(
                new IllegalStateException("No tokens available"));
        }
        if (!tokens.hasRefreshToken()) {
            return CompletableFuture.failedFuture(
                new IllegalStateException("No refresh token available"));
        }
        
        // Check if token is actually expired
        if (!tokens.isExpired()) {
            return CompletableFuture.completedFuture(tokens);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("\nRefreshing access token...");
                System.out.println("Current token expired: " + tokens.isExpired());
                System.out.println("Has refresh token: " + tokens.hasRefreshToken());
                System.out.println("Time until expiry: " + tokens.getTimeUntilExpiration() + "ms");

                FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("grant_type", "refresh_token")
                    .add("refresh_token", tokens.getRefreshToken())
                    .add("client_id", config.getClientId())
                    .add("app_id", config.getAppId());

                // Add client_secret if available, otherwise add code_verifier
                if (config.getClientSecret() != null && !config.getClientSecret().trim().isEmpty()) {
                    formBuilder.add("client_secret", config.getClientSecret());
                } else if (this.codeVerifier != null) {
                    formBuilder.add("code_verifier", this.codeVerifier);
                }

                Request request = new Request.Builder()
                    .url(config.getTokenEndpoint())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .post(formBuilder.build())
                    .build();

                OAuthTokens newTokens = executeTokenRequest(request);
                System.out.println("Token refresh successful!");
                System.out.println("New token expires in: " + newTokens.getExpiresIn() + " seconds");
                return newTokens;
            } catch (IOException | RuntimeException e) {
                System.err.println("Token refresh failed: " + e.getMessage());
                throw new RuntimeException("Failed to refresh tokens", e);
            }
        });
    }

    /**
     * Executes a token request and processes the response
     */
    private OAuthTokens executeTokenRequest(Request request) throws IOException {
        System.out.println("\nToken Request Details:");
        System.out.println("URL: " + request.url());
        System.out.println("Method: " + request.method());
        System.out.println("Headers: " + request.headers());

        Response response = null;
        ResponseBody responseBody = null;
        try {
            response = httpClient.newCall(request).execute();
            responseBody = response.body();

            System.out.println("\nToken Response Details:");
            System.out.println("Status Code: " + response.code());
            System.out.println("Headers: " + response.headers());

            if (!response.isSuccessful()) {
                String error = responseBody != null ? responseBody.string() : "Unknown error";
                System.err.println("Error Response Body: " + error);
                
                // Try to parse error as JSON for better error message
                try {
                    com.contentstack.cms.models.Error errorObj = gson.fromJson(error, com.contentstack.cms.models.Error.class);
                    throw new RuntimeException("Token request failed: " + 
                        (errorObj != null ? errorObj.getErrorMessage() : error));
                } catch (JsonParseException e) {
                    // If not JSON, use raw error string
                    throw new RuntimeException("Token request failed with status " + 
                        response.code() + ": " + error);
                }
            }

            String body = responseBody != null ? responseBody.string() : "{}";
            System.out.println("Success Response Body: " + body);
            
            OAuthTokens newTokens = gson.fromJson(body, OAuthTokens.class);
            
            // Set token expiry time
            if (newTokens.getExpiresIn() != null) {
                newTokens.setExpiresAt(new Date(System.currentTimeMillis() + (newTokens.getExpiresIn() * 1000)));
            }
            
            // Keep refresh token if new one not provided
            if (newTokens.getRefreshToken() == null && this.tokens != null && this.tokens.hasRefreshToken()) {
                newTokens.setRefreshToken(this.tokens.getRefreshToken());
            }
            
            _saveTokens(newTokens);
            return newTokens;
        } catch (JsonParseException e) {
            throw new RuntimeException("Failed to parse token response", e);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * Handles the OAuth redirect by exchanging the code for tokens
     * @param code Authorization code from the redirect
     * @return Future containing the tokens
     */
    public CompletableFuture<OAuthTokens> handleRedirect(String code) {
        return exchangeCodeForToken(code);
    }

    /**
     * Logs out the user and optionally revokes authorization
     * @param revokeAuthorization Whether to revoke the app authorization
     */
    public CompletableFuture<Void> logout(boolean revokeAuthorization) {
        return CompletableFuture.runAsync(() -> {
            if (revokeAuthorization && tokens != null) {
                revokeOauthAppAuthorization().join();
            }
            this.tokens = null;
        });
    }

    /**
     * Gets the current OAuth app authorization status
     * @return Future containing the authorization details
     */
    public CompletableFuture<OAuthTokens> getOauthAppAuthorization() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Request request = _getHeaders()
                    .url(config.getFormattedAuthorizationEndpoint() + "/status")
                    .get()
                    .build();

                Response response = null;
                ResponseBody responseBody = null;
                try {
                    response = httpClient.newCall(request).execute();
                    responseBody = response.body();

                    if (!response.isSuccessful()) {
                        String error = responseBody != null ? responseBody.string() : "Unknown error";
                        throw new RuntimeException("Failed to get authorization status: " + error);
                    }

                    String body = responseBody != null ? responseBody.string() : "{}";
                    return gson.fromJson(body, OAuthTokens.class);
                } finally {
                    if (responseBody != null) responseBody.close();
                    if (response != null) response.close();
                }
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException("Failed to get authorization status", e);
            }
        });
    }

    /**
     * Revokes the OAuth app authorization
     * @return Future that completes when revocation is done
     */
    public CompletableFuture<Void> revokeOauthAppAuthorization() {
        return CompletableFuture.runAsync(() -> {
            try {
                Request request = _getHeaders()
                    .url(config.getFormattedAuthorizationEndpoint() + "/revoke")
                    .post(new FormBody.Builder()
                        .add("app_id", config.getAppId())
                        .add("client_id", config.getClientId())
                        .build())
                    .build();

                Response response = null;
                ResponseBody responseBody = null;
                try {
                    response = httpClient.newCall(request).execute();
                    responseBody = response.body();

                    if (!response.isSuccessful()) {
                        String error = responseBody != null ? responseBody.string() : "Unknown error";
                        throw new RuntimeException("Failed to revoke authorization: " + error);
                    }
                } finally {
                    if (responseBody != null) responseBody.close();
                    if (response != null) response.close();
                }
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException("Failed to revoke authorization", e);
            }
        });
    }

    // Convenience methods for token access
    public String getAccessToken() { 
        OAuthTokens t = _getTokens();
        return t != null ? t.getAccessToken() : null; 
    }
    
    public String getRefreshToken() { 
        OAuthTokens t = _getTokens();
        return t != null ? t.getRefreshToken() : null; 
    }
    
    public String getOrganizationUID() { 
        OAuthTokens t = _getTokens();
        return t != null ? t.getOrganizationUid() : null; 
    }
    
    public String getUserUID() { 
        OAuthTokens t = _getTokens();
        return t != null ? t.getUserUid() : null; 
    }
    
    public Long getTokenExpiryTime() { 
        OAuthTokens t = _getTokens();
        return t != null ? t.getExpiresIn() : null; 
    }
    
    /**
     * Checks if we have a valid access token
     * @return true if we have a non-expired access token
     */
    public boolean hasValidAccessToken() {
        OAuthTokens t = _getTokens();
        return t != null && t.hasAccessToken() && !t.isExpired();
    }
}