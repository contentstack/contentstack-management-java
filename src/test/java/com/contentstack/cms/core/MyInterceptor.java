package com.contentstack.cms.core;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // This method will be called when the interceptor is invoked.
        // You can add your custom logic here.

        // Get the original request from the chain
        Request originalRequest = chain.request();

        // Perform any pre-processing of the request if needed
        // For example, you can add headers to the request here
        Request modifiedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN")
                .build();

        // Proceed with the request by calling chain.proceed(modifiedRequest)
        // This will pass the modified request along the chain
        Response response = chain.proceed(modifiedRequest);

        // Perform any post-processing of the response if needed
        // For example, you can log response information here
        System.out.println("Response Code: " + response.code());
        System.out.println("Response Body: " + response.body().string());

        AuthInterceptor interceptor = new AuthInterceptor();
        interceptor.setAuthtoken("fakeIt");
        interceptor.intercept(chain);

        // Return the response
        return response;
    }

    @Test
    public void testCustomerInterceptor() {
        MyInterceptor interceptor = new MyInterceptor();
        Assertions.assertNotNull(interceptor);

    }
}


