package com.contentstack.cms.core;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * The type Header interceptor.
 */
public class SessionInterceptor implements Interceptor {

    /**
     * The Authtoken.
     */
    String authtoken;

    /**
     * Instantiates a new Header interceptor.
     *
     * @param authtoken the authtoken
     */
    public SessionInterceptor(String authtoken) {
        this.authtoken = authtoken;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String loginUrl = "/user-session";
        if (request.url().toString().equalsIgnoreCase(loginUrl)) {
            if (request.method().equalsIgnoreCase("POST")) {
                // login
                // get response body and set authtoken

            } else if (request.method().equalsIgnoreCase("DELETE")) {
                // logout
                // set blank the authtoken
            }
        }

        return chain.proceed(request);
    }

}
