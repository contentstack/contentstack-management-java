package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GlobalFieldUnitTests {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static String GLOBAL_FIELD_UID = MANAGEMENT_TOKEN;
    static GlobalField globalField;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        globalField = stack.globalField(GLOBAL_FIELD_UID);
    }

    @Test
    void testGlobalFieldFind() {
        Request response = globalField.find().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields", response.url().toString());
        Assertions.assertEquals("/v3/global_fields", response.url().encodedPath());
        Assertions.assertEquals("https", response.url().scheme());
        Assertions.assertNull(response.url().query(), "No Query is expected");
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
        Assertions.assertEquals("GET", response.method());
    }

    @Test
    void testGlobalFieldFetch() throws IOException {
        Request response = globalField.fetch().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + GLOBAL_FIELD_UID,
                response.url().toString());
        Assertions.assertEquals("/v3/global_fields/" + GLOBAL_FIELD_UID, response.url().encodedPath());
        Assertions.assertEquals("https", response.url().scheme());
        Assertions.assertNull(response.url().query(), "No Query is expected");
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
        Assertions.assertEquals("GET", response.method());
    }

    @Test
    void testCreate() throws IOException {
        Request response = globalField.create(new JSONObject()).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields", response.url().toString());
        Assertions.assertEquals("/v3/global_fields", response.url().encodedPath());
        Assertions.assertEquals("https", response.url().scheme());
        Assertions.assertNull(response.url().query(), "No Query is expected");
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
        Assertions.assertEquals("POST", response.method());
    }

    @Test
    void testUpdate() throws IOException {
        Request response = globalField.update(new JSONObject()).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + GLOBAL_FIELD_UID,
                response.url().toString());
        Assertions.assertEquals("/v3/global_fields/" + GLOBAL_FIELD_UID, response.url().encodedPath());
        Assertions.assertEquals("https", response.url().scheme());
        Assertions.assertNull(response.url().query(), "No Query is expected");
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
        Assertions.assertEquals("PUT", response.method());
    }

    @Test
    void testDelete() throws IOException {
        Request response = globalField.delete().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + GLOBAL_FIELD_UID + "?force=true",
                response.url().toString());
        Assertions.assertEquals("/v3/global_fields/" + GLOBAL_FIELD_UID, response.url().encodedPath());
        Assertions.assertEquals("https", response.url().scheme());
        Assertions.assertEquals("force=true", response.url().query(), "No Query is expected");
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
        Assertions.assertEquals("DELETE", response.method());
    }

    @Test
    @Disabled
    void testGlobalFieldImport() throws IOException {
        JSONObject globalFieldBody = new JSONObject();
        JSONObject otherDetails = new JSONObject();
        otherDetails.put("title", "technology");
        globalFieldBody.put("global_field", otherDetails);
        Request response = globalField.imports(globalFieldBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + AUTHTOKEN + "/export",
                response.url().toString());
        Assertions.assertEquals("/v3/global_fields/" + AUTHTOKEN + "/export", response.url().encodedPath());
        Assertions.assertEquals("https", response.url().scheme());
        Assertions.assertNull(response.url().query(), "No Query is expected");
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
        Assertions.assertEquals("GET", response.method());
    }

    @Test
    void testGlobalFieldExport() throws IOException {
        Request response = globalField.export().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + GLOBAL_FIELD_UID + "/export",
                response.url().toString());
        Assertions.assertEquals("/v3/global_fields/" + GLOBAL_FIELD_UID + "/export", response.url().encodedPath());
        Assertions.assertEquals("https", response.url().scheme());
        Assertions.assertNull(response.url().query(), "No Query is expected");
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
        Assertions.assertEquals("GET", response.method());
    }

}
