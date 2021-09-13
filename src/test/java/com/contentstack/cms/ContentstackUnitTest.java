package com.contentstack.cms;

import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;


public class ContentstackUnitTest {


    @Test
    void testDefaultClientInstance() {
        Contentstack contentstack = new Contentstack.Builder().build();
        Assertions.assertEquals("api.contentstack.io", contentstack.host);
        Assertions.assertEquals("443", contentstack.port);
        Assertions.assertEquals("v3", contentstack.version);
        Assertions.assertEquals(30, contentstack.timeout);
        Assertions.assertNull(contentstack.authtoken);
        Assertions.assertNotNull(contentstack);
    }


    @Test
    void testDefaultClientInstanceWithoutAuthtoken() {
        Contentstack contentstack = new Contentstack.Builder().build();
        Assertions.assertNotNull(contentstack);
    }

    @Test
    void testDefaultClientPort() {
        Contentstack contentstack = new Contentstack.Builder().setPort("400").build();
        Assertions.assertEquals("400", contentstack.port);
    }

    @Test
    void testDefaultClientVersion() {
        Contentstack contentstack = new Contentstack.Builder().setVersion("v10").build();
        Assertions.assertEquals("v10", contentstack.version);
    }

    @Test
    void testDefaultClientTimeout() {
        Contentstack contentstack = new Contentstack.Builder().setTimeout(10).build();
        Assertions.assertEquals(10, contentstack.timeout);
    }

    @Test
    void testDefaultClientInstanceWithAuthtoken() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken("fake_authtoken").build();
        Assertions.assertNotNull(contentstack.authtoken);
        Assertions.assertEquals("fake_authtoken", contentstack.authtoken);
    }

    @Test
    void testClientSetPortMethod() {
        Contentstack contentstack = new Contentstack.Builder()
                .setPort("420")
                .setHost("cdn.contentstack.io")
                .setTimeout(10)
                .setVersion("v2")
                .build();
        Assertions.assertEquals(10, contentstack.timeout);
    }


    @Test
    void setProxy() {
        // This is how a developer can set the proxy
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("hostname", 433));
        Contentstack contentstack = new Contentstack.Builder()
                .setProxy(proxy)
                .build();
        Assertions.assertEquals("HTTP @ hostname:433", contentstack.proxy.toString());

    }

    @Test
    void setRetryOnFailure() {
        Contentstack contentstack = new Contentstack.Builder()
                .setRetryOnFailure(true)
                .build();
        Assertions.assertEquals(true, contentstack.retryOnFailure);
    }

    @Test
    void setHost() {
        String hostDemo = "api.thehostname.com";
        Contentstack contentstack = new Contentstack.Builder()
                .setHost(hostDemo)
                .build();
        Assertions.assertEquals(hostDemo, contentstack.host);
    }

    @Test
    void setPort() {
        Contentstack contentstack = new Contentstack.Builder()
                .setPort("443")
                .build();
        Assertions.assertEquals("443", contentstack.port);
    }

    @Test
    void setVersion() {
        Contentstack contentstack = new Contentstack.Builder()
                .setVersion("v8")
                .build();
        Assertions.assertEquals("v8", contentstack.version);
    }

    @Test
    void setTimeout() {
        Contentstack contentstack = new Contentstack.Builder()
                .setTimeout(3)
                .build();
        Assertions.assertEquals(3, contentstack.timeout);
    }

    @Test
    void setAuthtoken() {
        Contentstack contentstack = new Contentstack.Builder()
                .setAuthtoken("authtoken_dummy")
                .build();
        Assertions.assertEquals("authtoken_dummy", contentstack.authtoken);
    }

    @Test
    void testLogoutFromContentstack() throws IOException {
        Contentstack contentstack = new Contentstack.Builder()
                .setAuthtoken("authtoken_dummy")
                .build();
        Response<ResponseBody> logout = contentstack.logout();
        if (logout.isSuccessful()) {
            assert logout.body() != null;
            String responseString = logout.body().string();
        }
        Assertions.assertEquals("authtoken_dummy", contentstack.authtoken);
    }


}
