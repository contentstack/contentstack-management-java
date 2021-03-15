package com.contentstack.cms.core;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import static com.contentstack.cms.core.Constants.SDK_NAME;
import static com.contentstack.cms.core.Constants.SDK_VERSION;

public class HeaderInterceptor implements Interceptor {

    String authtoken;

    public HeaderInterceptor(String authtoken) {
        this.authtoken = authtoken;
    }


    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        String X_USER_AGENT = SDK_NAME+"/v"+SDK_VERSION;
        String USER_AGENT = Util.defaultUserAgent();

        Request request = chain.request().newBuilder()
                .addHeader("authtoken", this.authtoken)
                .addHeader("X-User-Agent", X_USER_AGENT)
                .addHeader("User-Agent", USER_AGENT)
                .addHeader("Content-Type", "application/json")
                .build();

        return chain.proceed(request);
    }

}
