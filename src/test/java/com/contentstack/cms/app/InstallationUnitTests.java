package com.contentstack.cms.app;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class InstallationUnitTests {

    private static Installation installation;
    private final static String authtoken = Dotenv.load().get("authToken");


    @BeforeAll
    public static void setUp() {
        String orgId = Dotenv.load().get("organizationUid");
        String manifestId = Dotenv.load().get("userId");
        String installationId = Dotenv.load().get("userId");

        assert orgId != null; // should not be null
        assert manifestId != null; // should not be null
        assert installationId != null; // should not be null
        assert authtoken != null;
        installation = new Contentstack.Builder()
                .setAuthtoken(authtoken)
                .build()
                .organization(orgId)
                .app(manifestId)
                .installation(installationId)
                .addHeader("authtoken", authtoken);
    }

    @Test
    void testInstallationCreate() {
        JSONObject body = Utils.readJson("app/installation.json");
        assert authtoken != null;
        Request requestInfo = installation.create(body).request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertNotNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/manifests/" + Dotenv.load().get("userId") + "/install", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testInstallationFetch() {
        Request requestInfo = installation.fetch().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/manifests/" + Dotenv.load().get("userId") + "/installations", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testInstallationFetchByAppId() {
        String appId = Dotenv.load().get("organizationUid");
        Request requestInfo = installation.fetch(appId).request();
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/manifests/" + appId + "/installations", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testGetAll() {
        Request requestInfo = installation.find().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + Dotenv.load().get("organizationUid"), requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testReinstall() {
        JSONObject body = Utils.readJson("app/installation.json");
        Request requestInfo = installation.reinstall(body).request();
        Assertions.assertEquals("PUT", requestInfo.method());
        Assertions.assertNotNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/manifests/" + Dotenv.load().get("userId") + "/reinstall", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }


    @Test
    void testInstallationUpdateWithInstallationId() {
        JSONObject body = Utils.readJson("app/installation.json");
        String installationId = Dotenv.load().get("organizationUid");
        assert installationId != null;
        Request requestInfo = installation.update(installationId, body).request();
        Assertions.assertEquals("PUT", requestInfo.method());
        Assertions.assertNotNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + installationId, requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testInstallationUpdate() {
        JSONObject body = Utils.readJson("app/installation.json");
        String authToken = Dotenv.load().get("organizationUid");
        Request requestInfo = installation.update(body).request();
        Assertions.assertEquals("PUT", requestInfo.method());
        Assertions.assertNotNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + authToken, requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testUninstallationApp() {
        String installationId = Dotenv.load().get("organizationUid");
        Request requestInfo = installation.uninstall(installationId).request();
        Assertions.assertEquals("DELETE", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + installationId, requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testCreateTokenApp() {
        JSONObject body = Utils.readJson("app/installation.json");
        Request requestInfo = installation.createToken(body).request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertNotNull(requestInfo.body());
        Assertions.assertNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/token", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testGetWebhookApp() {
        String installationId = Dotenv.load().get("organizationUid");
        assert installationId != null;
        Request requestInfo = installation.getWebhook().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + installationId + "/locations/configuration", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testGetWebhookAppUsingCustomInstallationUid() {
        String installationId = Dotenv.load().get("organizationUid");
        assert installationId != null;
        Request requestInfo = installation.getWebhook(installationId).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + installationId + "/locations/configuration", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testGetAllExecutions() {
        String webhookId = Dotenv.load().get("organizationUid");
        assert webhookId != null;
        Request requestInfo = installation.findExecutions(webhookId).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + Dotenv.load().get("userId") + "/webhooks/" + webhookId + "/executions", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testFetchExecutions() {
        String webhookId = Dotenv.load().get("organizationUid");
        String executionId = Dotenv.load().get("organizationUid");
        assert webhookId != null;
        assert executionId != null;
        Request requestInfo = installation.fetchExecution(webhookId, executionId).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertTrue(requestInfo.url().toString().contains("executions"));
        Assertions.assertTrue(requestInfo.isHttps());
    }


}
