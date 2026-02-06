package com.contentstack.cms.core;

import com.contentstack.cms.Contentstack;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.util.concurrent.atomic.AtomicBoolean;

@Tag("unit")
class RetryTest {

    @Test
    void testRetryPolicy_backwardCompatibleConstructor() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake@authtoken")
                .build();
        Call<ResponseBody> userModelCall = client.user().getUser();
        RetryCallback<ResponseBody> callback = new RetryCallback<ResponseBody>(userModelCall) {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Assertions.assertNotNull(call);
                Assertions.assertNotNull(response);
            }
        };

        Throwable throwable = new Throwable();
        callback.onFailure(userModelCall, throwable);
    }

    @Test
    void testRetryCallback_withRetryConfig() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake@authtoken")
                .build();
        Call<ResponseBody> call = client.user().getUser();
        RetryConfig config = RetryConfig.builder().retryLimit(2).build();
        RetryCallback<ResponseBody> callback = new RetryCallback<ResponseBody>(call, config) {
            @Override
            public void onResponse(Call<ResponseBody> c, Response<ResponseBody> response) {
                Assertions.fail("Unexpected success");
            }
        };
        callback.onFailure(call, new Throwable("fail"));
    }

    @Test
    void testOnFinalFailure_calledWhenErrorNotRetryable() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake@authtoken")
                .build();
        Call<ResponseBody> call = client.user().getUser();
        RetryConfig noRetryConfig = RetryConfig.builder()
                .retryCondition((statusCode, error) -> false)
                .retryLimit(3)
                .build();
        AtomicBoolean finalFailureCalled = new AtomicBoolean(false);
        RetryCallback<ResponseBody> callback = new RetryCallback<ResponseBody>(call, noRetryConfig) {
            @Override
            public void onResponse(Call<ResponseBody> c, Response<ResponseBody> response) {
                Assertions.fail("Unexpected success");
            }

            @Override
            protected void onFinalFailure(Call<ResponseBody> c, Throwable t) {
                finalFailureCalled.set(true);
            }
        };
        callback.onFailure(call, new RuntimeException("not retryable"));
        Assertions.assertTrue(finalFailureCalled.get(), "onFinalFailure should be called when error is not retryable");
    }

    @Test
    void testOnFinalFailure_calledWhenRetryLimitReached() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake@authtoken")
                .build();
        Call<ResponseBody> call = client.user().getUser();
        RetryConfig limitZeroConfig = RetryConfig.builder()
                .retryLimit(0)
                .retryCondition((statusCode, error) -> true)
                .build();
        AtomicBoolean finalFailureCalled = new AtomicBoolean(false);
        RetryCallback<ResponseBody> callback = new RetryCallback<ResponseBody>(call, limitZeroConfig) {
            @Override
            public void onResponse(Call<ResponseBody> c, Response<ResponseBody> response) {
                Assertions.fail("Unexpected success");
            }

            @Override
            protected void onFinalFailure(Call<ResponseBody> c, Throwable t) {
                finalFailureCalled.set(true);
            }
        };
        callback.onFailure(call, new java.io.IOException("network error"));
        Assertions.assertTrue(finalFailureCalled.get(), "onFinalFailure should be called when retry limit is 0");
    }

    @Test
    void testRetryCallback_withContentstackGetRetryConfig() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake@authtoken")
                .setRetryConfig(RetryConfig.builder().retryLimit(5).build())
                .build();
        Assertions.assertNotNull(client.getRetryConfig());
        Assertions.assertEquals(5, client.getRetryConfig().getRetryLimit());
        Call<ResponseBody> call = client.user().getUser();
        RetryCallback<ResponseBody> callback = new RetryCallback<ResponseBody>(call, client.getRetryConfig()) {
            @Override
            public void onResponse(Call<ResponseBody> c, Response<ResponseBody> response) {
                Assertions.assertNotNull(response);
            }
        };
        callback.onFailure(call, new Throwable("test"));
    }
}
