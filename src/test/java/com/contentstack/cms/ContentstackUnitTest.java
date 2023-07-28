package com.contentstack.cms;

import com.contentstack.cms.organization.Organization;
import com.contentstack.cms.stack.Stack;
import okhttp3.Headers;
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
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("hostname", 433));
        Contentstack contentstack = new Contentstack.Builder()
                .setProxy(proxy)
                .build();
        Assertions.assertEquals("HTTP @ hostname:433", contentstack.proxy.toString());

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
            Assertions.assertEquals("Please Login to access user instance", e.getLocalizedMessage());
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
            Assertions.assertEquals("User is already loggedIn, Please logout then try to login again", e.getMessage());
        }
        Assertions.assertEquals("fake@authtoken", client.authtoken);
    }

    @Test
    void testSetAuthtokenLoginWithTfa() {
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        try {
            client.login("fake@email.com", "fake@password", "fake@tfa");
        } catch (Exception e) {
            Assertions.assertEquals("User is already loggedIn, Please logout then try to login again", e.getMessage());
        }
        Assertions.assertEquals("fake@authtoken", client.authtoken);
    }

    @Test
    public void testStackWithAPIKey() {
        // Arrange
        String apiKey = "***REMOVED***";
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> client.organization(null));
    }

    @Test
    public void testEmptyOrganizationUid() {
        Contentstack client = new Contentstack.Builder().setAuthtoken("fake@authtoken").build();
        String emptyOrganizationUid = "";

        // Act & Assert
        Assertions.assertThrows(IllegalStateException.class, () -> client.organization(emptyOrganizationUid));
    }


}
