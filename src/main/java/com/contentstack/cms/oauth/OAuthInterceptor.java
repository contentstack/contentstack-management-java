package com.contentstack.cms.oauth;

import com.contentstack.cms.core.Util;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;


public class OAuthInterceptor implements Interceptor {
    private static final Logger logger = Logger.getLogger(OAuthInterceptor.class.getName());
    private static final int MAX_RETRIES = 3;
    private final OAuthHandler oauthHandler;
    private String[] earlyAccess;
    private final Object refreshLock = new Object();

    public OAuthInterceptor(OAuthHandler oauthHandler) {
        this.oauthHandler = oauthHandler;
    }


    public void setEarlyAccess(String[] earlyAccess) {
        this.earlyAccess = earlyAccess;
    }


    public boolean isOAuthConfigured() {
        return oauthHandler != null && oauthHandler.getConfig() != null;
    }


    public boolean hasValidTokens() {
        return oauthHandler != null && 
               oauthHandler.getTokens() != null && 
               !oauthHandler.getTokens().isExpired();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        
        System.out.println("\nOAuth Interceptor - Request Details:");
        System.out.println("URL: " + originalRequest.url());
        System.out.println("Method: " + originalRequest.method());
        System.out.println("Has Tokens: " + (oauthHandler.getTokens() != null));
        if (oauthHandler.getTokens() != null) {
            System.out.println("Access Token: " + oauthHandler.getTokens().getAccessToken());
            System.out.println("Token Expired: " + oauthHandler.getTokens().isExpired());
        }
        
        Request.Builder requestBuilder = originalRequest.newBuilder()
            .header("X-User-Agent", Util.defaultUserAgent())
            .header("User-Agent", Util.defaultUserAgent())
            .header("Content-Type", originalRequest.url().toString().contains("/token") ? "application/x-www-form-urlencoded" : "application/json")
            .header("X-Header-EA", earlyAccess != null ? String.join(",", earlyAccess) : "true");
        // Skip auth header for token endpoints
        if (!originalRequest.url().toString().contains("/token")) {
            if (oauthHandler.getTokens() != null && oauthHandler.getTokens().hasAccessToken()) {
                requestBuilder.header("Authorization", "Bearer " + oauthHandler.getAccessToken());
                System.out.println("Added Authorization header: Bearer " + oauthHandler.getAccessToken());
            }
        }

        // Execute request with retry and refresh handling
        return executeRequest(chain, requestBuilder.build(), 0);
    }

    private Response executeRequest(Chain chain, Request request, int retryCount) throws IOException {
        // Skip token refresh for token endpoints to avoid infinite loops
        if (request.url().toString().contains("/token")) {
            return chain.proceed(request);
        }

        // Ensure we have tokens
        if (oauthHandler == null || oauthHandler.getTokens() == null) {
            throw new IOException("No OAuth tokens available. Please authenticate first.");
        }

        // Check if we need to refresh the token before making the request
        if (oauthHandler.getTokens().isExpired()) {
            
            synchronized (refreshLock) {
                try {
                    logger.info("Token expired, refreshing...");
                    oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);
                    
                    // Update authorization header with new token
                    request = request.newBuilder()
                        .header("Authorization", "Bearer " + oauthHandler.getAccessToken())
                        .build();
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    logger.severe("Token refresh failed: " + e.getMessage());
                    throw new IOException("Failed to refresh access token", e);
                }
            }
        }

        // Execute request
        Response response = chain.proceed(request);

        // Handle error responses
        if (!response.isSuccessful() && retryCount < MAX_RETRIES) {
            int code = response.code();
            String body = null;
            try {
                if (response.body() != null) {
                    body = response.body().string();
                }
            } catch (IOException e) {
                // Ignore body read errors
            }
            response.close();
            
            logger.info("Request failed with code " + code + ": " + body);

            // Handle 401 with token refresh
            if (code == 401 && oauthHandler != null && oauthHandler.getTokens() != null && 
                oauthHandler.getTokens().hasRefreshToken()) {
                
                synchronized (refreshLock) {
                    try {
                        logger.info("Attempting token refresh after 401");
                        oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);
                        
                        // Update authorization header with new token
                        request = request.newBuilder()
                            .header("Authorization", "Bearer " + oauthHandler.getAccessToken())
                            .build();
                            
                        return executeRequest(chain, request, retryCount + 1);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        throw new IOException("Failed to refresh access token after 401", e);
                    }
                }
            }
            
            // Handle other retryable errors (429, 5xx)
            if ((code == 429 || code >= 500) && code != 501) {
                try {
                    // Exponential backoff
                    long delay = Math.min(1000 * (1 << retryCount), 30000);
                    logger.info("Retrying request after " + delay + "ms delay");
                    Thread.sleep(delay);
                    return executeRequest(chain, request, retryCount + 1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Retry interrupted", e);
                }
            }
        }

        return response;
    }
}