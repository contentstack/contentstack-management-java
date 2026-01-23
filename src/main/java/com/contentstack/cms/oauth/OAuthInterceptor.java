package com.contentstack.cms.oauth;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.contentstack.cms.core.Util;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OAuthInterceptor implements Interceptor {

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
        return oauthHandler != null
                && oauthHandler.getTokens() != null
                && !oauthHandler.getTokens().isExpired();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("X-User-Agent", Util.defaultUserAgent())
                .header("User-Agent", Util.defaultUserAgent())
                .header("x-header-ea", earlyAccess != null ? String.join(",", earlyAccess) : "true");

        // Skip Content-Type header for DELETE /releases/{release_uid} request
        // to avoid "Body cannot be empty when content-type is set to 'application/json'" error
        if (!isDeleteReleaseRequest(originalRequest)) {
            String contentType = originalRequest.url().toString().contains("/token")
                    ? "application/x-www-form-urlencoded"
                    : "application/json";
            requestBuilder.header("Content-Type", contentType);
        }

        // Skip auth header for token endpoints
        if (!originalRequest.url().toString().contains("/token")) {
            if (oauthHandler.getTokens() != null && oauthHandler.getTokens().hasAccessToken()) {
                requestBuilder.header("Authorization", "Bearer " + oauthHandler.getAccessToken());
            }
        }

        // Execute request with retry and refresh handling
        return executeRequest(chain, requestBuilder.build(), 0);
    }

    /**
     * Checks if the request is a DELETE request to /releases/{release_uid} endpoint.
     * This endpoint should not have Content-Type header as it doesn't accept a body.
     *
     * @param request The HTTP request to check
     * @return true if this is a DELETE /releases/{release_uid} request
     */
    private boolean isDeleteReleaseRequest(Request request) {
        if (!"DELETE".equals(request.method())) {
            return false;
        }
        String path = request.url().encodedPath();
        // Match pattern: /v3/releases/{release_uid} (no trailing path segments)
        return path.matches(".*/releases/[^/]+$");
    }

    private Response executeRequest(Chain chain, Request request, int retryCount) throws IOException {
        // Skip token refresh for token endpoints to avoid infinite loops
        if (request.url().toString().contains("/token")) {
            return chain.proceed(request);
        }

        // Ensure we have tokens
        if (oauthHandler == null || oauthHandler.getTokens() == null) {
            throw new IOException(Util.OAUTH_NO_TOKENS);
        }

        // Check if we need to refresh the token before making the request
        if (oauthHandler.getTokens().isExpired()) {

            synchronized (refreshLock) {
                try {

                    oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);

                    // Update authorization header with new token
                    request = request.newBuilder()
                            .header("Authorization", "Bearer " + oauthHandler.getAccessToken())
                            .build();
                } catch (InterruptedException | ExecutionException | TimeoutException e) {

                    throw new IOException(Util.OAUTH_REFRESH_FAILED, e);
                }
            }
        }

        // Execute request
        Response response = chain.proceed(request);

        // Handle error responses
        if (!response.isSuccessful() && retryCount < MAX_RETRIES) {
            int code = response.code();
            response.close();

            // Handle 401 with token refresh
            if (code == 401 && oauthHandler != null && oauthHandler.getTokens() != null
                    && oauthHandler.getTokens().hasRefreshToken()) {

                synchronized (refreshLock) {
                    try {

                        oauthHandler.refreshAccessToken().get(30, TimeUnit.SECONDS);

                        // Update authorization header with new token
                        request = request.newBuilder()
                                .header("Authorization", "Bearer " + oauthHandler.getAccessToken())
                                .build();

                        return executeRequest(chain, request, retryCount + 1);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        throw new IOException(Util.OAUTH_REFRESH_FAILED + " after 401", e);
                    }
                }
            }

            // Handle other retryable errors (429, 5xx)
            if ((code == 429 || code >= 500) && code != 501) {
                try {
                    long delay = Math.min(1000 * (1 << retryCount), 30000);
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
