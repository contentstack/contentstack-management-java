package com.contentstack.cms.marketplace.apps;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppUnitTests {

    private static App app;
    private final static String authtoken = Dotenv.load().get("authToken");
    private final static String organizationUid = Dotenv.load().get("organizationUid");

    private JSONObject theJSONBody(@NotNull String _body) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(_body);
        } catch (ParseException e) {
            System.out.println("Json Reading Error: " + e.getLocalizedMessage());
            return null;
        }
    }

    @BeforeAll
    public static void setUp() {
        app = new Contentstack.Builder()
                .setAuthtoken(authtoken)
                .build()
                .organization(organizationUid).marketplace()
                .app(organizationUid).addHeader("authtoken", authtoken);
    }


    @Test
    void testFetch() {
        assert organizationUid != null;
        Request request = app.fetch(organizationUid).request();
        Assertions.assertEquals("/v3/manifest/" + organizationUid,
                request.url().encodedPath());
        Assertions.assertEquals("GET",
                request.method());
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
        Request request = app.fetch(organizationUid).request();
        Assertions.assertEquals("/v3/manifest/" + organizationUid,
                request.url().encodedPath());
    }

    @Test
    void testDelete() {
        Request request = app.delete(organizationUid).request();
        Assertions.assertEquals("/v3/manifests/" + organizationUid,
                request.url().encodedPath());
        Assertions.assertEquals("DELETE",
                request.method());
        Assertions.assertFalse(request.headers().get("organization_uid").isEmpty());
        Assertions.assertFalse(request.headers().get("authtoken").isEmpty());

    }

    @Test
    void testDeleteWithoutId() {
        Request request = app.delete().request();
        Assertions.assertEquals("/v3/manifests/" + organizationUid,
                request.url().encodedPath());
        Assertions.assertEquals("DELETE",
                request.method());
        Assertions.assertFalse(request.headers().get("organization_uid").isEmpty());
        Assertions.assertFalse(request.headers().get("authtoken").isEmpty());
    }


}
