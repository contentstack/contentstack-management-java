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

/**
 * OkHttp interceptor that handles OAuth token injection and refresh
 */
public class OAuthInterceptor implements Interceptor {
    private static final Logger logger = Logger.getLogger(OAuthInterceptor.class.getName());
    private final OAuthHandler oauthHandler;
    private String[] earlyAccess;

    public OAuthInterceptor(OAuthHandler oauthHandler) {
        this.oauthHandler = oauthHandler;
    }

    /**
     * Sets early access features
     * @param earlyAccess Array of early access feature names
     */
    public void setEarlyAccess(String[] earlyAccess) {
        this.earlyAccess = earlyAccess;
    }

    /**
     * Checks if OAuth is configured
     * @return true if OAuth is configured
     */
    public boolean isOAuthConfigured() {
        return oauthHandler != null && oauthHandler.getConfig() != null;
    }

    /**
     * Checks if we have valid tokens
     * @return true if we have valid tokens
     */
    public boolean hasValidTokens() {
        return oauthHandler != null && 
               oauthHandler.getTokens() != null && 
               !oauthHandler.getTokens().isExpired();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Add standard headers
        Request.Builder requestBuilder = originalRequest.newBuilder()
            .header("X-User-Agent", Util.defaultUserAgent())
            .header("User-Agent", Util.defaultUserAgent())
            .header("Content-Type", "application/json")
            .header("X-Header-EA", earlyAccess != null ? String.join(",", earlyAccess) : "true");

        // Get current tokens
        if (oauthHandler.getTokens() != null) {
            // Check if token is expired and refresh if needed
            if (oauthHandler.getTokens().isExpired() && oauthHandler.getTokens().hasRefreshToken()) {
                try {
                    oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    throw new IOException("Failed to refresh access token", e);
                }
            }

            // Add token to request if available
            if (oauthHandler.getTokens().hasAccessToken()) {
                requestBuilder.header("Authorization", "Bearer " + oauthHandler.getAccessToken());
            }
        }

        Request request = requestBuilder.build();
        Response response = chain.proceed(request);

        // Handle 401 by refreshing token and retrying once
        if (response.code() == 401 && oauthHandler.getTokens() != null && oauthHandler.getTokens().hasRefreshToken()) {
            response.close();
            try {
                oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);
                
                // Retry with new token
                request = request.newBuilder()
                    .header("Authorization", "Bearer " + oauthHandler.getAccessToken())
                    .build();
                return chain.proceed(request);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                throw new IOException("Failed to refresh access token after 401", e);
            }
        }

        return response;
    }
}