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
 */
public class AuthInterceptor implements Interceptor {

    public AuthInterceptor() {
    }

    protected String authtoken;

    public AuthInterceptor(String authtoken) {
        this.authtoken = authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String xUserAgent = Util.SDK_NAME + "/" + Util.SDK_VERSION;
        Request.Builder request = chain.request().newBuilder()
                .header("X-User-Agent", xUserAgent)
                .header("User-Agent", Util.defaultUserAgent())
                .header("Content-Type", "application/json");

        if (this.authtoken != null) {
            request.addHeader("authtoken", this.authtoken);
        }
        return chain.proceed(request.build());
    }

}
