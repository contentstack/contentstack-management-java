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
    private final OAuthHandler oauthHandler;
    private String[] earlyAccess;

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
            if (oauthHandler.getTokens() != null) {
                if (oauthHandler.getTokens().isExpired() && oauthHandler.getTokens().hasRefreshToken()) {
                    try {
                        oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        throw new IOException("Failed to refresh access token", e);
                    }
                }
                if (oauthHandler.getTokens().hasAccessToken()) {
                    requestBuilder.header("Authorization", "Bearer " + oauthHandler.getAccessToken());
                    System.out.println("Added Authorization header: Bearer " + oauthHandler.getAccessToken());
                }
            }
        }

        Request request = requestBuilder.build();
        Response response = chain.proceed(request);
        if (response.code() == 401 && oauthHandler.getTokens() != null && oauthHandler.getTokens().hasRefreshToken()) {
            response.close();
            try {
                oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);
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