package com.contentstack.cms.core;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jetbrains.annotations.NotNull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <b>The type Header interceptor that extends Interceptor</b>
 * <p>
 * Interceptors are a powerful way to customize requests with Retrofit. A common
 * use-case where you want to intercept
 * the actual request is to observe, modifies, and potentially short-circuits
 * requests going out and the corresponding
 * responses coming back in. Typically, interceptors add, remove, or transform
 * headers on the request. Depending on the
 * API implementation, you'll want to pass the auth token as the value for the
 * Authorization header.
 *
 * @author ***REMOVED***
 * @since v0.1.0
 */
public class AuthInterceptor implements Interceptor {

    protected String authtoken;
    protected String[] earlyAccess;
    protected RetryConfig retryConfig = RetryConfig.defaultConfig();
    // The `public AuthInterceptor() {}` is a default constructor for the
    // `AuthInterceptor` class. It is
    // used to create an instance of the `AuthInterceptor` class without passing any
    // arguments. In this
    // case, it initializes the `authtoken` variable to `null`.
    public AuthInterceptor() {
    }

    // The `public AuthInterceptor(String authtoken)` is a constructor for the
    // `AuthInterceptor` class that
    // takes an `authtoken` parameter.
    public AuthInterceptor(String authtoken) {
        this.authtoken = authtoken;
    }

    /**
     * The function sets the value of the authtoken variable.
     *
     * @param authtoken The authtoken parameter is a string that represents an
     *                  authentication token.
     */
    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public void setEarlyAccess(String[] earlyAccess) {
        this.earlyAccess = earlyAccess;
    }

    /**
     * This function intercepts a request and adds headers to it, including a user
     * agent, content type, and
     * authentication token if available.
     *
     * @param chain The `chain` parameter is an object of type `Interceptor.Chain`.
     *              It represents the chain
     *              of interceptors that will be executed for a given request. It
     *              provides methods to proceed to the
     *              next interceptor in the chain or to proceed with the request and
     *              get the response.
     * @return The method is returning a Response object.
     */
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String xUserAgent = Util.SDK_NAME + "/v" + Util.SDK_VERSION;
        Request originalRequest = chain.request();
        Request.Builder request = originalRequest.newBuilder()
                .header(Util.X_USER_AGENT, xUserAgent)
                .header(Util.USER_AGENT, Util.defaultUserAgent());

        // Skip Content-Type header for DELETE /releases/{release_uid} request
        // to avoid "Body cannot be empty when content-type is set to 'application/json'" error
        if (!isDeleteReleaseRequest(originalRequest)) {
            request.header(Util.CONTENT_TYPE, Util.CONTENT_TYPE_VALUE);
        }

        // Attach the client-level authtoken only when the request doesn't already
        // carry one (e.g. via @HeaderMap) - addHeader would APPEND a duplicate
        // authtoken header and the API rejects the request as unauthenticated.
        if (this.authtoken != null && originalRequest.header(Util.AUTHTOKEN) == null) {
            request.addHeader(Util.AUTHTOKEN, this.authtoken);
        }
        
        if (this.earlyAccess != null && this.earlyAccess.length > 0) {
            String commaSeparated = String.join(", ", earlyAccess);
            request.addHeader(Util.EARLY_ACCESS_HEADER, commaSeparated);
        }
        Request finalRequest = request.build();
        long startedAt = System.currentTimeMillis();
        Response response = executeRequest(chain, finalRequest, 0);
        RequestObserver localObserver = observer;
        if (localObserver != null) {
            try {
                // Never hand credentials to observers: sensitive headers are
                // masked on a defensive copy before the callback sees it.
                localObserver.onCall(maskSensitiveHeaders(finalRequest), response,
                        System.currentTimeMillis() - startedAt);
            } catch (Exception ignored) {
                // observers must never break real requests
            }
        }
        return response;
    }

    private static Request maskSensitiveHeaders(Request request) {
        Request.Builder masked = request.newBuilder();
        for (String name : new String[]{"authtoken", "authorization", "access_token"}) {
            String value = request.header(name);
            if (value != null) {
                masked.header(name, value.length() > 12
                        ? value.substring(0, 6) + "..." + value.substring(value.length() - 4)
                        : "***");
            }
        }
        return masked.build();
    }

    /**
     * Observer for outgoing requests/responses. Intended for test harnesses
     * and diagnostics (e.g. capturing cURL commands for test reports);
     * not part of the public API surface.
     */
    public interface RequestObserver {
        void onCall(Request request, Response response, long durationMs);
    }

    private static volatile RequestObserver observer;

    /**
     * Registers a JVM-wide request observer (pass null to remove). The observer
     * is invoked after each request completes, with the final request (including
     * interceptor-added headers) and the response. Exceptions thrown by the
     * observer are swallowed.
     *
     * @param requestObserver the observer, or null to unregister
     */
    public static void setRequestObserver(RequestObserver requestObserver) {
        observer = requestObserver;
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

    public void setRetryConfig(RetryConfig retryConfig) {
        this.retryConfig = retryConfig != null ? retryConfig : RetryConfig.defaultConfig();
    }

    private Response executeRequest(Chain chain, Request request, int retryCount) throws IOException {
        try {
            Response response = chain.proceed(request);
            int code = response.code();
            if (retryCount < retryConfig.getRetryLimit() && retryConfig.getRetryCondition().shouldRetry(code, null)) {
                response.close();
                long delay = RetryUtil.calculateDelay(retryConfig, retryCount + 1, code);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Retry interrupted", ex);
                }
                return executeRequest(chain, request, retryCount + 1);
            }
            return response;
        } catch (SocketTimeoutException e) {
            if (retryCount < retryConfig.getRetryLimit() && retryConfig.getRetryCondition().shouldRetry(0, e)) {
                long delay = RetryUtil.calculateDelay(retryConfig, retryCount + 1, 0);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Retry interrupted", ex);
                }
                return executeRequest(chain, request, retryCount + 1);
            }
            throw e;
        }
    }

}
