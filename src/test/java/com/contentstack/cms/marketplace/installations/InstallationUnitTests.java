package com.contentstack.cms.marketplace.installations;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class InstallationUnitTests {

    private static Installation installation;
    static String AUTHTOKEN = TestClient.AUTHTOKEN;
    static String ORG_ID = TestClient.ORGANIZATION_UID;


    @BeforeAll
    static void setUp() {
        Contentstack client = TestClient.getClient();
        installation = client.organization(ORG_ID).marketplace().installation(AUTHTOKEN);
    }

    @Test
    void testFindInstalledApps() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.findInstalledApps().request();
        Assertions.assertEquals("/installations/view/apps", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals("view", request.url().pathSegments().get(1));
        Assertions.assertEquals("apps", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testFindInstallations() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.findInstallations().request();
        Assertions.assertEquals("/installations", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(1, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testFetchInstallation() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.fetchInstallation().request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN, request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }

    @Test
    void testFetchInstallationData() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.fetchInstallationData().request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/installationData", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testUpdateInstallation() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        JSONObject body = new JSONObject();
        body.put("config", "SomeObject");
        body.put("server_config", "SomeObject");
        body.put("webhooks", "Array of Objects with webhook_uid and channels");
        body.put("ui_locations", "ui_locations");

        Request request = installation.updateInstallation( body).request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN, request.url().encodedPath());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testUpdateInstallationWithOnlyRequestBody() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        JSONObject body = new JSONObject();
        body.put("config", "SomeObject");
        body.put("server_config", "SomeObject");
        body.put("webhooks", "Array of Objects with webhook_uid and channels");
        body.put("ui_locations", "ui_locations");

        Request request = installation.updateInstallation(body).request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN, request.url().encodedPath());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testFindInstalledUsers() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.findInstalledUsers().request();
        Assertions.assertEquals("/installations/view/users", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals("view", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testFindInstalledStacks() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.findInstalledStacks().request();
        Assertions.assertEquals("/installations/view/stacks", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals("view", request.url().pathSegments().get(1));
        Assertions.assertEquals("stacks", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testUninstall() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.uninstall().request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN, request.url().encodedPath());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNull(request.url().query());
    }


    @Test
    void testFetchAppConfiguration() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.fetchAppConfiguration().request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/configuration", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testFetchServerConfiguration() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        Request request = installation.fetchServerConfiguration().request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/server-configuration", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testUpdateServerConfiguration() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        JSONObject body = new JSONObject();
        body.put("config", "SomeObject");
        body.put("server_config", "SomeObject");
        body.put("webhooks", "Array of Objects with webhook_uid and channels");
        body.put("ui_locations", "ui_locations");

        Request request = installation.updateServerConfiguration(body).request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/server-configuration", request.url().encodedPath());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testUpdateServerConfigurationWithInstallationId() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        JSONObject body = new JSONObject();
        body.put("config", "SomeObject");
        body.put("server_config", "SomeObject");
        body.put("webhooks", "Array of Objects with webhook_uid and channels");
        body.put("ui_locations", "ui_locations");

        Request request = installation.updateServerConfiguration(body).request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/server-configuration", request.url().encodedPath());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testUpdateStackConfiguration() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        JSONObject body = new JSONObject();
        body.put("config", "SomeObject");
        body.put("server_config", "SomeObject");
        body.put("webhooks", "Array of Objects with webhook_uid and channels");
        body.put("ui_locations", "ui_locations");

        Request request = installation.updateStackConfiguration( body).request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/configuration", request.url().encodedPath());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }

    @Test
    void testUpdateStackConfigurationWithoutDirectAuthtoken() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        JSONObject body = new JSONObject();
        body.put("config", "SomeObject");
        body.put("server_config", "SomeObject");
        body.put("webhooks", "Array of Objects with webhook_uid and channels");
        body.put("ui_locations", "ui_locations");

        Request request = installation.updateStackConfiguration(body).request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/configuration", request.url().encodedPath());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testCreateInstallationToken() {
        installation.addHeader("authtoken", AUTHTOKEN);
        installation.addParam("sort", "asc");
        installation.addParam("order", "order");
        installation.addParam("limit", "10");
        installation.addParam("skip", "5");

        JSONObject body = new JSONObject();
        body.put("config", "SomeObject");
        body.put("server_config", "SomeObject");
        body.put("webhooks", "Array of Objects with webhook_uid and channels");
        body.put("ui_locations", "ui_locations");

        Request request = installation.createInstallationToken().request();
        Assertions.assertEquals("/installations/" + AUTHTOKEN + "/token", request.url().encodedPath());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals(AUTHTOKEN, request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }

}
