package com.contentstack.cms.marketplace.apps.oauth;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.marketplace.apps.App;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;


@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OauthTests {

    private static Contentstack client;
    private static App app;
    private final static String AUTHTOKEN = Dotenv.load().get("authToken");
    private final static String ORGANIZATION_UID = Dotenv.load().get("organizationUid");

    @BeforeAll
    public static void setUp() {
        client = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
    }

    @BeforeClass
    public static void onlyOnce() {
        app = client.organization(ORGANIZATION_UID).marketplace().app(ORGANIZATION_UID).addHeader("authtoken", AUTHTOKEN);
    }

    @Test
    void testFetch() {
        assert ORGANIZATION_UID != null;
        Request request = app.fetch(ORGANIZATION_UID).request();
        Assertions.assertEquals("/v3/manifest/" + ORGANIZATION_UID, request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertFalse(request.headers().get("organization_uid").isEmpty());
        Assertions.assertFalse(request.headers().get("authtoken").isEmpty());
    }

    @Test
    void testFind() {
        Request request = app.find().request();
        Assertions.assertEquals("/v3/manifests",
                request.url().encodedPath());
        Assertions.assertEquals("GET",
                request.method());
        Assertions.assertFalse(request.headers().get("organization_uid").isEmpty());
        Assertions.assertFalse(request.headers().get("authtoken").isEmpty());

    }

    @Test
    void testCreate() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("target_type", "dev");
        requestBody.put("name", "test");
        Request request = app.create(requestBody).request();
        Assertions.assertEquals("/v3/manifest",
                request.url().encodedPath());
        Assertions.assertEquals("POST",
                request.method());
        Assertions.assertFalse(request.headers().get("organization_uid").isEmpty());
        Assertions.assertFalse(request.headers().get("authtoken").isEmpty());
        Assertions.assertNotNull(request.body());
    }

    @Test
    void testUpdate() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("target_type", "dev");
        requestBody.put("name", "test");
        Request request = app.fetch(ORGANIZATION_UID).request();
        Assertions.assertEquals("/v3/manifest/" + ORGANIZATION_UID,
                request.url().encodedPath());
    }

    @Test
    void testDelete() {
        Request request = app.delete(ORGANIZATION_UID).request();
        Assertions.assertEquals("/v3/manifests/" + ORGANIZATION_UID,
                request.url().encodedPath());
        Assertions.assertEquals("DELETE",
                request.method());
        Assertions.assertFalse(request.headers().get("organization_uid").isEmpty());
        Assertions.assertFalse(request.headers().get("authtoken").isEmpty());

    }

    @Test
    void testDeleteWithoutId() {
        Request request = app.delete().request();
        Assertions.assertEquals("/v3/manifests/" + ORGANIZATION_UID,
                request.url().encodedPath());
        Assertions.assertEquals("DELETE",
                request.method());
        Assertions.assertFalse(request.headers().get("organization_uid").isEmpty());
        Assertions.assertFalse(request.headers().get("authtoken").isEmpty());
    }



}
