package com.contentstack.cms;

import com.contentstack.cms.core.AuthInterceptor;
import com.contentstack.cms.organization.Organization;
import com.contentstack.cms.stack.Stack;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ContentstackUnitTest {

    @Test
    void testDefaultClientInstance() {
        Contentstack client = new Contentstack.Builder().build();
        Assertions.assertEquals("api.contentstack.io", client.host);
        Assertions.assertEquals("443", client.port);
        Assertions.assertEquals("v3", client.version);
        Assertions.assertEquals(30, client.timeout);
        Assertions.assertNull(client.authtoken);
        Assertions.assertNotNull(client);
    }

    @Test
    void testClientDefaultPort() {
        Contentstack client = new Contentstack.Builder().build();
        Assertions.assertEquals("api.contentstack.io", client.host);
        Assertions.assertEquals("443", client.port);
    }

    @Test
    void testClientDefaultHost() {
        Contentstack client = new Contentstack.Builder().build();
        Assertions.assertEquals("api.contentstack.io", client.host);
    }

    @Test
    void testClientAPIDefaultVersion() {
        Contentstack client = new Contentstack.Builder().build();
        Assertions.assertEquals("v3", client.version);

    }

    @Test
    void testClientDefaultTimeout() {
        Contentstack client = new Contentstack.Builder().build();
        Assertions.assertEquals(30, client.timeout);
    }

    @Test
    void testClientDefaultAuthtoken() {
        Contentstack client = new Contentstack.Builder().build();
        Assertions.assertNull(client.authtoken);
    }

    @Test
    void testClientIfNotNull() {
        Contentstack client = new Contentstack.Builder().build();
        Assertions.assertNull(client.authtoken);
    }

    @Test
    void testClientDefaultHeaders() throws IOException {
        Contentstack client = new Contentstack.Builder().build();
        Response<ResponseBody> logout = client.logout();
        Headers defaultHeaders = logout.headers();
        Assertions.assertNotNull(defaultHeaders);
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
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("api.contentstack.io", 433));
        Contentstack contentstack = new Contentstack.Builder()
                .setProxy(proxy)
                .build();
        Assertions.assertNotNull(contentstack.proxy.toString());

    }

    @Test
    void setRetryOnFailure() {
        Contentstack contentstack = new Contentstack.Builder()
                .setRetry(true)
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
    void testSetAuthtoken() {
        Contentstack contentstack = new Contentstack.Builder()
                .setAuthtoken("authtoken_dummy")
                .build();
        Assertions.assertEquals("authtoken_dummy", contentstack.authtoken);
    }

    @Test
    void testSetOrganizations() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken(null)
                .build();
        Assertions.assertNull(client.authtoken);
        try {
            client.organization();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            Assertions.assertEquals("Login or configure OAuth to continue. organization", e.getLocalizedMessage());
        }
    }

    @Test
    void testSetAuthtokenLogin() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake@authtoken")
                .build();
        try {
            client.login("fake@email.com", "fake@password");
        } catch (Exception e) {
            Assertions.assertEquals("Operation not allowed. You are already logged in.", e.getMessage());
        }
        Assertions.assertEquals("fake@authtoken", client.authtoken);
    }

    @Test
    void testSetAuthtokenLoginWithTfa() {
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("tfaToken", "fake@tfa");
            client.login("fake@email.com", "fake@password", params);
        } catch (Exception e) {
            Assertions.assertEquals("Operation not allowed. You are already logged in.", e.getMessage());
        }
        Assertions.assertEquals("fake@authtoken", client.authtoken);
    }

    @Test
    public void testStackWithAPIKey() {
        // Arrange
        String apiKey = "123456789";
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        Stack stack = client.stack(apiKey);
        Assertions.assertNotNull(stack);
    }

    @Test
    public void testStackWithBranch() {
        // Arrange
        String apiKey = "branch";
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        Stack stack = client.stack(apiKey);
        Assertions.assertNotNull(stack);
    }

    @Test
    public void testStackWithAllOtherParams() {
        // Arrange
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        Stack stack = client.stack("apiKey", "management_token", "branch");
        Assertions.assertNotNull(stack);
    }

    @Test
    public void testValidOrganization() {
        // Arrange
        String organizationUid = "org-123";
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        Organization organization = client.organization(organizationUid);
        Assertions.assertNotNull(organization);
        Assertions.assertEquals("org-123", organizationUid);
    }

    @Test
    public void testNullOrganizationUid() {
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        Assertions.assertThrows(NullPointerException.class, () -> client.organization(null));
    }

    @Test
    public void testEmptyOrganizationUid() {
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        String emptyOrganizationUid = "";

        // Act & Assert
        Assertions.assertThrows(IllegalStateException.class, () -> client.organization(emptyOrganizationUid));
    }

    @Test
    public void testEarlyAccessHeader() throws IOException, InterruptedException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody("{}"));

        AuthInterceptor authInterceptor = new AuthInterceptor();
        authInterceptor.setEarlyAccess(new String[]{"Taxonomy"});

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();

        Request request = new Request.Builder()
                .url(mockWebServer.url("/"))
                .build();

        client.newCall(request).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        String earlyAccessHeader = recordedRequest.getHeader("x-header-ea");

        Assertions.assertNotNull(earlyAccessHeader);
        Assertions.assertEquals("Taxonomy", earlyAccessHeader);

        mockWebServer.shutdown();
    } 
    @Test
    public void testEarlyAccessMultipleHeader() throws IOException, InterruptedException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody("{}"));

        AuthInterceptor authInterceptor = new AuthInterceptor();
        authInterceptor.setEarlyAccess(new String[]{"Taxonomy","Teams"});

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();

        Request request = new Request.Builder()
                .url(mockWebServer.url("/"))
                .build();

        client.newCall(request).execute();
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        String earlyAccessHeader = recordedRequest.getHeader("x-header-ea");

        Assertions.assertNotNull(earlyAccessHeader);
        Assertions.assertEquals("Taxonomy, Teams", earlyAccessHeader);

        mockWebServer.shutdown();
    }               

    @Test
    public void testEarlyAccessHeaderEmpty() {
        String[] emptyEarlyAccessFeatures = {};
        Contentstack client = new Contentstack.Builder()
                .earlyAccess(emptyEarlyAccessFeatures)
                .build();
        
        Assertions.assertNotNull(client.earlyAccess);
        Assertions.assertEquals(0, client.earlyAccess.length);
    }

}
