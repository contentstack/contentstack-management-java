package com.contentstack.cms.oauth;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
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
    private final OkHttpClient httpClient;
    private final OAuthConfig config;
    private final Gson gson;

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
        
        // Generate PKCE parameters if needed
        if (config.isPkceEnabled()) {
            generatePkceParameters();
        }
    }

    /**
     * Returns common headers for OAuth requests
     */
    private Request.Builder _getHeaders() {
        return new Request.Builder()
            .header("Content-Type", "application/x-www-form-urlencoded");
    }

    /**
     * Generates a cryptographically secure code verifier for PKCE
     * @return A random URL-safe string between 43-128 characters
     */
    private String generateCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
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
            
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Generates PKCE parameters (code verifier and challenge)
     */
    private void generatePkceParameters() {
        this.codeVerifier = generateCodeVerifier();
        this.codeChallenge = generateCodeChallenge(this.codeVerifier);
        this.state = generateCodeVerifier(); // Use same method for state
    }

    /**
     * Starts the OAuth authorization flow
     * @return Authorization URL for the user to visit
     */
    public String authorize() {
        try {
            StringBuilder urlBuilder = new StringBuilder();
            
            // Build the authorization URL with parameters in correct order
            urlBuilder.append(config.getFormattedAuthorizationEndpoint())
                     .append("?app_id=").append(URLEncoder.encode(config.getAppId(), "UTF-8"))
                     .append("&response_type=").append(config.getResponseType())
                     .append("&client_id=").append(URLEncoder.encode(config.getClientId(), "UTF-8"))
                     .append("&redirect_uri=").append(URLEncoder.encode(config.getRedirectUri(), "UTF-8"))
                     .append("&state=").append(URLEncoder.encode(this.state, "UTF-8"));
            
            // Add PKCE parameters if enabled
            if (config.isPkceEnabled()) {
                urlBuilder.append("&code_challenge=").append(URLEncoder.encode(this.codeChallenge, "UTF-8"))
                         .append("&code_challenge_method=S256");
            }
            
            // Add scope if present
            if (config.getScope() != null && !config.getScope().trim().isEmpty()) {
                urlBuilder.append("&scope=").append(URLEncoder.encode(config.getScope(), "UTF-8"));
            }
            
            return urlBuilder.toString();
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
        return CompletableFuture.supplyAsync(() -> {
            try {
                FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("app_id", config.getAppId())
                    .add("grant_type", "authorization_code")
                    .add("code", code)
                    .add("client_id", config.getClientId())
                    .add("redirect_uri", config.getRedirectUri());

                if (config.isPkceEnabled()) {
                    formBuilder.add("code_verifier", this.codeVerifier);
                } else {
                    formBuilder.add("client_secret", config.getClientSecret());
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
        this.tokens = tokens;
    }

    /**
     * Refreshes the access token using the refresh token
     * @return Future containing the new tokens
     */
    public CompletableFuture<OAuthTokens> refreshAccessToken() {
        if (tokens == null || !tokens.hasRefreshToken()) {
            return CompletableFuture.failedFuture(
                new IllegalStateException("No refresh token available"));
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("app_id", config.getAppId())
                    .add("grant_type", "refresh_token")
                    .add("refresh_token", tokens.getRefreshToken())
                    .add("client_id", config.getClientId());

                if (!config.isPkceEnabled()) {
                    formBuilder.add("client_secret", config.getClientSecret());
                }

                Request request = _getHeaders()
                    .url(config.getTokenEndpoint())
                    .post(formBuilder.build())
                    .build();

                return executeTokenRequest(request);
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException("Failed to refresh tokens", e);
            }
        });
    }

    /**
     * Executes a token request and processes the response
     */
    private OAuthTokens executeTokenRequest(Request request) throws IOException {
        Response response = null;
        ResponseBody responseBody = null;
        try {
            response = httpClient.newCall(request).execute();
            responseBody = response.body();

            if (!response.isSuccessful()) {
                String error = responseBody != null ? responseBody.string() : "Unknown error";
                throw new RuntimeException("Token request failed: " + error);
            }

            String body = responseBody != null ? responseBody.string() : "{}";
            OAuthTokens newTokens = gson.fromJson(body, OAuthTokens.class);
            
            // Keep old refresh token if new one not provided
            if (this.tokens != null && newTokens.getRefreshToken() == null) {
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
    public String getAccessToken() { return tokens != null ? tokens.getAccessToken() : null; }
    public String getRefreshToken() { return tokens != null ? tokens.getRefreshToken() : null; }
    public String getOrganizationUID() { return tokens != null ? tokens.getOrganizationUid() : null; }
    public String getUserUID() { return tokens != null ? tokens.getUserUid() : null; }
    public Long getTokenExpiryTime() { return tokens != null ? tokens.getExpiresIn() : null; }
}