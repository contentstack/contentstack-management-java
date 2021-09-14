package com.contentstack.cms.core;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * The type Header interceptor that extends Interceptor </br>
 * Interceptor: Observes, modifies, and potentially short-circuits requests
 * going out and the corresponding responses coming back in. Typically,
 * interceptors add, remove, or transform headers on the request or response..
 */
public class HeaderInterceptor implements Interceptor {

    public static final String SDK_NAME = "contentstack-management-java";
    public static final String SDK_VERSION = "v0.0.1";
    public static final String AUTHTOKEN = "authtoken";
    public static final String X_USER_AGENT_KEY = "X-User-Agent";
    public static final String X_USER_AGENT_VALUE = SDK_NAME + "/" + SDK_VERSION;
    public static final String User_AGENT = "User-Agent";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    public HeaderInterceptor() {
    }

    /**
     * The Authtoken.
     */
    String authtoken;

    /**
     * Instantiates a new Header interceptor.
     *
     * @param authtoken the authtoken
     */
    public HeaderInterceptor(String authtoken) {
        this.authtoken = authtoken;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request =
                chain.request()
                        .newBuilder()
                        .addHeader(X_USER_AGENT_KEY, X_USER_AGENT_VALUE)
                        .addHeader(User_AGENT, Util.defaultUserAgent())
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON);

        if (this.authtoken != null) {
            request.addHeader(AUTHTOKEN, this.authtoken);
        }
        return chain.proceed(request.build());
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
