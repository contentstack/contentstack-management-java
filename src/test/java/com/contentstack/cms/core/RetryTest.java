package com.contentstack.cms.core;

import com.contentstack.cms.Contentstack;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

class RetryTest {


    @Test
    public void testRetryPolicy() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake@authtoken")
                .build();
        Call<ResponseBody> userModelCall = client.user().getUser();
        RetryCallback callback = new RetryCallback(userModelCall) {
            @Override
            public void onResponse(Call call, Response response) {
                Assertions.assertNotNull(call);
                Assertions.assertNotNull(response);
            }
        };

        Throwable throwable = new Throwable();
        callback.onFailure(userModelCall, throwable);
    }
}