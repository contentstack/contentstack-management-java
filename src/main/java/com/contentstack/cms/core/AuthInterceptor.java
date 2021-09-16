package com.contentstack.cms.core;

import okhttp3.Headers;
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
public class AuthInterceptor implements Interceptor {

    private final String AUTHTOKEN = "authtoken";
    private final String X_USER_AGENT_KEY = "X-User-Agent";
    private final String User_AGENT = "User-Agent";
    private final String CONTENT_TYPE = "Content-Type";
    private final String APPLICATION_JSON = "application/json";

    public AuthInterceptor() {
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
    public AuthInterceptor(String authtoken) {
        this.authtoken = authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Adding User agent properties
        String xUserAgent = Util.SDK_NAME + "/" + Util.SDK_VERSION;
        // Adding static header agent properties
        Headers headers = new Headers.Builder()
                .add(X_USER_AGENT_KEY, xUserAgent)
                .add(User_AGENT, Util.defaultUserAgent())
                .add(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        Request.Builder request = chain.request().newBuilder()
                .headers(headers);

        if (this.authtoken != null) {
            request.addHeader(AUTHTOKEN, this.authtoken);
        }
        return chain.proceed(request.build());
    }


}
