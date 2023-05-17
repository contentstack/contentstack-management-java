package com.contentstack.cms.marketplace.installations;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class InstallationUnitTests {

    private static Installation installation;
    private static Contentstack client;
    private final static String authtoken = Dotenv.load().get("authToken");


    @BeforeAll
    public static void setUp() {
        client = new Contentstack.Builder().setAuthtoken(authtoken).build();
    }

    @BeforeClass
    public static void init() {
        String orgId = Dotenv.load().get("organizationUid");
        String manifestId = Dotenv.load().get("userId");
        String installationId = Dotenv.load().get("userId");
        installation = client.organization(orgId).marketplace().installation().addHeader("authtoken", authtoken);
    }

    @Test
    void testInstallationCreate() {
        assert authtoken != null;
        Request requestInfo = installation.createInstallationToken("installationId").request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertNotNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/manifests/" + Dotenv.load().get("userId") + "/install", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testInstallationFetch() {
        Request requestInfo = installation.findInstallation().request();
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
        Request requestInfo = installation.findInstallation().request();
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/manifests/" + appId + "/installations", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

    @Test
    void testGetAll() {
        Request requestInfo = installation.findInstallations().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/installations/" + Dotenv.load().get("organizationUid"), requestInfo.url().toString().split("v3")[1]);
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


}
