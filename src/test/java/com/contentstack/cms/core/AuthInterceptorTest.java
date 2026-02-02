package com.contentstack.cms.core;

import okhttp3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AuthInterceptorTest {

    private AuthInterceptor authInterceptor;

    @BeforeEach
    public void setup() {
        authInterceptor = new AuthInterceptor("test-authtoken");
    }

    // --- Retry tests (RetryConfig) ---

    @Test
    @Tag("unit")
    public void testRetryConfig_setRetryConfig() {
        RetryConfig config = RetryConfig.builder().retryLimit(5).build();
        authInterceptor.setRetryConfig(config);
        Assertions.assertNotNull(authInterceptor.retryConfig);
        Assertions.assertEquals(5, authInterceptor.retryConfig.getRetryLimit());
    }

    @Test
    @Tag("unit")
    public void testRetry_on429_thenSuccess_retriesAndReturnsSuccess() throws IOException {
        authInterceptor.setRetryConfig(RetryConfig.builder().retryLimit(3).retryDelay(10).build());
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/user")
                .get()
                .build();
        RetryTestChain chain = new RetryTestChain(request, 429, 200);
        try (Response response = authInterceptor.intercept(chain)) {
            Assertions.assertEquals(200, response.code());
            Assertions.assertEquals(2, chain.getProceedCount());
        }
    }

    @Test
    @Tag("unit")
    public void testRetry_on503_thenSuccess_retriesAndReturnsSuccess() throws IOException {
        authInterceptor.setRetryConfig(RetryConfig.builder().retryLimit(3).retryDelay(10).build());
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/user")
                .get()
                .build();
        RetryTestChain chain = new RetryTestChain(request, 503, 200);
        try (Response response = authInterceptor.intercept(chain)) {
            Assertions.assertEquals(200, response.code());
            Assertions.assertEquals(2, chain.getProceedCount());
        }
    }

    @Test
    @Tag("unit")
    public void testRetry_on404_doesNotRetry() throws IOException {
        authInterceptor.setRetryConfig(RetryConfig.builder().retryLimit(3).retryDelay(10).build());
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/user")
                .get()
                .build();
        RetryTestChain chain = new RetryTestChain(request, 404, 200);
        try (Response response = authInterceptor.intercept(chain)) {
            Assertions.assertEquals(404, response.code());
            Assertions.assertEquals(1, chain.getProceedCount());
        }
    }

    @Test
    @Tag("unit")
    public void testRetry_on200_doesNotRetry() throws IOException {
        authInterceptor.setRetryConfig(RetryConfig.builder().retryLimit(3).retryDelay(10).build());
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/user")
                .get()
                .build();
        RetryTestChain chain = new RetryTestChain(request, 200, 200);
        try (Response response = authInterceptor.intercept(chain)) {
            Assertions.assertEquals(200, response.code());
            Assertions.assertEquals(1, chain.getProceedCount());
        }
    }

    /**
     * Chain that returns a configurable response code on first proceed, then successCode on subsequent calls.
     */
    private static class RetryTestChain implements Interceptor.Chain {
        private final Request originalRequest;
        private final int firstResponseCode;
        private final int successCode;
        private int proceedCount = 0;

        RetryTestChain(Request request, int firstResponseCode, int successCode) {
            this.originalRequest = request;
            this.firstResponseCode = firstResponseCode;
            this.successCode = successCode;
        }

        int getProceedCount() {
            return proceedCount;
        }

        @Override
        public Request request() {
            return originalRequest;
        }

        @Override
        public Response proceed(Request request) throws IOException {
            proceedCount++;
            int code = proceedCount == 1 ? firstResponseCode : successCode;
            return new Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(code)
                    .message(code == 200 ? "OK" : "Error")
                    .body(ResponseBody.create("{}", MediaType.parse("application/json")))
                    .build();
        }

        @Override
        public Connection connection() {
            return null;
        }

        @Override
        public int connectTimeoutMillis() {
            return 0;
        }

        @Override
        public Interceptor.Chain withConnectTimeout(int timeout, java.util.concurrent.TimeUnit unit) {
            return this;
        }

        @Override
        public int readTimeoutMillis() {
            return 0;
        }

        @Override
        public Interceptor.Chain withReadTimeout(int timeout, java.util.concurrent.TimeUnit unit) {
            return this;
        }

        @Override
        public int writeTimeoutMillis() {
            return 0;
        }

        @Override
        public Interceptor.Chain withWriteTimeout(int timeout, java.util.concurrent.TimeUnit unit) {
            return this;
        }

        @Override
        public Call call() {
            return null;
        }
    }

    @Test
    public void AuthInterceptor() {
        AuthInterceptor expected = new AuthInterceptor("abc");
        AuthInterceptor actual = new AuthInterceptor();
        Assertions.assertNull(actual.authtoken);
        Assertions.assertEquals("abc", expected.authtoken);
    }

    @Test
    public void testSetAuthtoken() {
        AuthInterceptor actual = new AuthInterceptor();
        actual.setAuthtoken("abcd");
        Assertions.assertEquals("abcd", actual.authtoken);
    }

    @Test
    public void testBadArgumentException() {
        BadArgumentException exception = new BadArgumentException("Invalid Argument");
        String message = exception.getLocalizedMessage();
        Assertions.assertEquals("Invalid Argument", message);
    }

    @Test
    public void testDeleteReleaseRequest_shouldNotHaveContentTypeHeader() throws IOException {
        // Create a mock DELETE /releases/{uid} request
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/releases/blt123abc456")
                .delete()
                .build();

        // Create a test chain
        TestChain chain = new TestChain(request);

        // Intercept the request
        authInterceptor.intercept(chain);

        // Verify Content-Type header is NOT present
        Request processedRequest = chain.processedRequest;
        Assertions.assertNull(processedRequest.header("Content-Type"),
                "DELETE /releases/{uid} should not have Content-Type header");
    }

    @Test
    public void testDeleteReleaseItemRequest_shouldHaveContentTypeHeader() throws IOException {
        // DELETE /releases/{uid}/items should still have Content-Type
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/releases/blt123abc456/items")
                .delete()
                .build();

        TestChain chain = new TestChain(request);
        authInterceptor.intercept(chain);

        Request processedRequest = chain.processedRequest;
        Assertions.assertEquals("application/json", processedRequest.header("Content-Type"),
                "DELETE /releases/{uid}/items should have Content-Type header");
    }

    @Test
    public void testGetRequest_shouldHaveContentTypeHeader() throws IOException {
        // GET requests should have Content-Type
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/releases/blt123abc456")
                .get()
                .build();

        TestChain chain = new TestChain(request);
        authInterceptor.intercept(chain);

        Request processedRequest = chain.processedRequest;
        Assertions.assertEquals("application/json", processedRequest.header("Content-Type"),
                "GET requests should have Content-Type header");
    }

    @Test
    public void testPostRequest_shouldHaveContentTypeHeader() throws IOException {
        // POST requests should have Content-Type
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/releases")
                .post(RequestBody.create("{}", MediaType.parse("application/json")))
                .build();

        TestChain chain = new TestChain(request);
        authInterceptor.intercept(chain);

        Request processedRequest = chain.processedRequest;
        Assertions.assertEquals("application/json", processedRequest.header("Content-Type"),
                "POST requests should have Content-Type header");
    }

    @Test
    public void testDeleteOtherResource_shouldHaveContentTypeHeader() throws IOException {
        // DELETE to other resources should have Content-Type
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/content_types/sample_ct")
                .delete()
                .build();

        TestChain chain = new TestChain(request);
        authInterceptor.intercept(chain);

        Request processedRequest = chain.processedRequest;
        Assertions.assertEquals("application/json", processedRequest.header("Content-Type"),
                "DELETE to other resources should have Content-Type header");
    }

    @Test
    public void testDeleteReleaseRequest_shouldHaveUserAgentHeaders() throws IOException {
        // Verify other headers are still added for DELETE /releases/{uid}
        Request request = new Request.Builder()
                .url("https://api.contentstack.io/v3/releases/blt123abc456")
                .delete()
                .build();

        TestChain chain = new TestChain(request);
        authInterceptor.intercept(chain);

        Request processedRequest = chain.processedRequest;
        Assertions.assertNotNull(processedRequest.header("X-User-Agent"),
                "X-User-Agent header should be present");
        Assertions.assertNotNull(processedRequest.header("User-Agent"),
                "User-Agent header should be present");
        Assertions.assertEquals("test-authtoken", processedRequest.header("authtoken"),
                "authtoken header should be present");
    }

    /**
     * Test implementation of Interceptor.Chain for testing purposes
     */
    private static class TestChain implements Interceptor.Chain {
        private final Request originalRequest;
        public Request processedRequest;

        TestChain(Request request) {
            this.originalRequest = request;
        }

        @Override
        public Request request() {
            return originalRequest;
        }

        @Override
        public Response proceed(Request request) throws IOException {
            this.processedRequest = request;
            // Return a dummy response
            return new Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .body(ResponseBody.create("{}", MediaType.parse("application/json")))
                    .build();
        }

        @Override
        public Connection connection() {
            return null;
        }

        @Override
        public int connectTimeoutMillis() {
            return 0;
        }

        @Override
        public Interceptor.Chain withConnectTimeout(int timeout, java.util.concurrent.TimeUnit unit) {
            return this;
        }

        @Override
        public int readTimeoutMillis() {
            return 0;
        }

        @Override
        public Interceptor.Chain withReadTimeout(int timeout, java.util.concurrent.TimeUnit unit) {
            return this;
        }

        @Override
        public int writeTimeoutMillis() {
            return 0;
        }

        @Override
        public Interceptor.Chain withWriteTimeout(int timeout, java.util.concurrent.TimeUnit unit) {
            return this;
        }

        @Override
        public Call call() {
            return null;
        }
    }
}
