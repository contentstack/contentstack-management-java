package com.contentstack.cms.core;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.contentstack.cms.core.Constants.*;

/**
 * The type Header interceptor that extends Interceptor
 * </br>Interceptor: Observes, modifies, and potentially short-circuits requests
 * going out and the corresponding responses coming back in. Typically,
 * interceptors add, remove, or transform headers on the request or response..
 */
public class HeaderInterceptor implements Interceptor {

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
        Request.Builder request = chain.request()
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
