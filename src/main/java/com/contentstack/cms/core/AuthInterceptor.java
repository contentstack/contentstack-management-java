package com.contentstack.cms.core;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
        Request.Builder request = chain.request().newBuilder()
                .header(Util.X_USER_AGENT, xUserAgent)
                .header(Util.USER_AGENT, Util.defaultUserAgent())
                .header(Util.CONTENT_TYPE, Util.CONTENT_TYPE_VALUE);

        if (this.authtoken != null) {
            request.addHeader(Util.AUTHTOKEN, this.authtoken);
        }
        return chain.proceed(request.build());
    }

}
