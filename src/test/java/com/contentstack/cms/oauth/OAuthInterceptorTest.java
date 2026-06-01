package com.contentstack.cms.oauth;

import com.contentstack.cms.core.RetryConfig;
import com.contentstack.cms.models.OAuthTokens;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.SocketTimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class OAuthInterceptorTest {

    private OAuthInterceptor interceptor;

    @Mock
    private OAuthHandler mockHandler;

    @Mock
    private OAuthTokens mockTokens;

    @Before
    public void setup() {
        Mockito.lenient().when(mockTokens.isExpired()).thenReturn(false);
        Mockito.lenient().when(mockTokens.hasAccessToken()).thenReturn(true);
        Mockito.lenient().when(mockHandler.getTokens()).thenReturn(mockTokens);
        Mockito.lenient().when(mockHandler.getAccessToken()).thenReturn("test-access-token");

        interceptor = new OAuthInterceptor(mockHandler);
        interceptor.setRetryConfig(RetryConfig.builder().retryLimit(3).retryDelay(10).build());
    }

    @Test
    public void testRetry_onSocketTimeout_thenSuccess_retriesAndReturnsSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/content_types")
                .get()
                .build();
        TimeoutTestChain chain = new TimeoutTestChain(request, 1, 200);
        try (Response response = interceptor.intercept(chain)) {
            assertEquals(200, response.code());
            assertEquals(2, chain.getProceedCount());
        }
    }

    @Test
    public void testRetry_onSocketTimeout_exhaustsRetries_throws() {
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/content_types")
                .get()
                .build();
        TimeoutTestChain chain = new TimeoutTestChain(request, 5, 200);
        assertThrows(SocketTimeoutException.class, () -> interceptor.intercept(chain));
        assertEquals(4, chain.getProceedCount()); // 1 initial + 3 retries
    }

    @Test
    public void testRetry_onSocketTimeout_zeroRetryLimit_throwsImmediately() {
        interceptor.setRetryConfig(RetryConfig.builder().retryLimit(0).retryDelay(10).build());
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/content_types")
                .get()
                .build();
        TimeoutTestChain chain = new TimeoutTestChain(request, 5, 200);
        assertThrows(SocketTimeoutException.class, () -> interceptor.intercept(chain));
        assertEquals(1, chain.getProceedCount());
    }

    private static class TimeoutTestChain implements Interceptor.Chain {
        private final Request originalRequest;
        private final int timeoutCount;
        private final int successCode;
        private int proceedCount = 0;

        TimeoutTestChain(Request request, int timeoutCount, int successCode) {
            this.originalRequest = request;
            this.timeoutCount = timeoutCount;
            this.successCode = successCode;
        }

        int getProceedCount() { return proceedCount; }

        @Override
        public Request request() { return originalRequest; }

        @Override
        public Response proceed(Request request) throws IOException {
            proceedCount++;
            if (proceedCount <= timeoutCount) {
                throw new SocketTimeoutException("timeout");
            }
            return new Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(successCode)
                    .message("OK")
                    .body(ResponseBody.create("{}", MediaType.parse("application/json")))
                    .build();
        }

        @Override public Connection connection() { return null; }
        @Override public int connectTimeoutMillis() { return 0; }
        @Override public Interceptor.Chain withConnectTimeout(int timeout, java.util.concurrent.TimeUnit unit) { return this; }
        @Override public int readTimeoutMillis() { return 0; }
        @Override public Interceptor.Chain withReadTimeout(int timeout, java.util.concurrent.TimeUnit unit) { return this; }
        @Override public int writeTimeoutMillis() { return 0; }
        @Override public Interceptor.Chain withWriteTimeout(int timeout, java.util.concurrent.TimeUnit unit) { return this; }
        @Override public Call call() { return null; }
    }
}
