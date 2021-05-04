package com.contentstack.cms.core;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.contentstack.cms.core.Constants.*;

/**
 * The type Header interceptor.
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

        Request request = chain.request().newBuilder()
                .addHeader(AUTHTOKEN, this.authtoken)
                .addHeader(X_USER_AGENT_KEY, X_USER_AGENT_VALUE)
                .addHeader(User_AGENT, Util.defaultUserAgent())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        return chain.proceed(request);
    }

}
