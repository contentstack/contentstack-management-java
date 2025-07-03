package com.contentstack.cms.stack;

import java.io.IOException;
import java.util.HashMap;

import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.Request;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import com.contentstack.cms.Contentstack;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Response;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlobalFieldAPITest {

    public static GlobalField globalField;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String globalFieldUid = "global_field_1";
    protected static String globalFieldUid2 = "nested_global_field";
    protected static Stack stack;
    private int _COUNT = 2;

    @BeforeAll
    public void setUp() {
        stack = TestClient.getStack().addHeader(Util.API_KEY, API_KEY)
                .addHeader("api_key", API_KEY);
        _COUNT = stack.headers.size();
    }

    @Order(1)
    @Test
    void testCreateGlobalField() {
        JSONObject requestBody = Utils.readJson("globalfield/global_field_create.json");
        globalField = stack.globalField();
        Request request = globalField.create(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(2)
    @Test
    void testFetchSingleGlobalField() {
        globalField = stack.globalField(globalFieldUid);
        Request request = globalField.fetch().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(3)
    @Test
    void testFetchSingleNestedGlobalFieldWithParams() {
        globalField = stack.globalField(globalFieldUid);
        globalField.addParam("include_schema", true);
        Request request = globalField.fetch().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_schema=true", request.url().query().toString());
    }

    @Order(4)
    @Test
    void testUpdateGlobalField() {
        globalField = stack.globalField(globalFieldUid);
        JSONObject requestBody = Utils.readJson("globalfield/global_field_update.json");
        Request request = globalField.update(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(5)
    @Test
    void testFindAllGlobalFields() {
        globalField = stack.globalField();
        Request request = globalField.find().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(6)
    @Test
    void testFindWithParamsNGF() {
        globalField = stack.globalField();
        globalField.addParam("include_count", true);
        globalField.addParam("include_global_field_schema", true);

        Request request = globalField.find().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
    }

    @Order(7)
    @Test
    void testDeleteGlobalField() {
        globalField = stack.globalField(globalFieldUid);
        Request request = globalField.delete().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("force=true", request.url().encodedQuery());
    }

    @Order(8)
    @Test
    void testImportGlobalField() {
        globalField = stack.globalField();
        JSONObject requestBody = Utils.readJson("globalfield/global_field_import.json");
        Request request = globalField.imports(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals("import", request.url().pathSegments().get(2));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(9)
    @Test
    void testExportGlobalField() {
        globalField = stack.globalField(globalFieldUid);
        Request request = globalField.export().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertEquals("export", request.url().pathSegments().get(3));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(10)
    @Test
    void testCreateNestedGlobalField() {
        JSONObject requestBody = Utils.readJson("globalfield/nested_global_field_create.json");
        globalField = stack.globalField().addHeader("api_version", "3.2");
        Request request = globalField.create(requestBody).request();
        globalField.removeHeader("api_version");

        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(11)
    @Test
    void testFetchSingleNestedGlobalField() {
        globalField = stack.globalField(globalFieldUid).addHeader("api_version", "3.2");
        Request request = globalField.fetch().request();

        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(12)
    @Test
    void testFetchSingleGlobalFieldWithParams() {
        globalField = stack.globalField(globalFieldUid).addHeader("api_version", "3.2");
        globalField.addParam("include_schema", true);
        Request request = globalField.fetch().request();

        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_schema=true", request.url().query().toString());
    }

    @Order(13)
    @Test
    void testUpdateNestedGlobalField() {
        globalField = stack.globalField(globalFieldUid).addHeader("api_version", "3.2");
        JSONObject requestBody = Utils.readJson("globalfield/nested_global_field_update.json");
        Request request = globalField.update(requestBody).request();

        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(14)
    @Test
    void testFindAllNestedGlobalField() {
        globalField = stack.globalField().addHeader("api_version", "3.2");
        Request request = globalField.find().request();

        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(15)
    @Test
    void testFindWithParams() {
        globalField = stack.globalField().addHeader("api_version", "3.2");
        globalField.addParam("include_count", true);
        globalField.addParam("include_global_field_schema", true);

        Request request = globalField.find().request();

        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
    }

    @Order(16)
    @Test
    void testDeleteNestedGlobalField() {
        globalField = stack.globalField(globalFieldUid).addHeader("api_version", "3.2");
        Request request = globalField.delete().request();

        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("force=true", request.url().encodedQuery());
    }

    @Order(17)
    @Test
    void testImportNestedGlobalField() {
        globalField = stack.globalField().addHeader("api_version", "3.2");
        JSONObject requestBody = Utils.readJson("globalfield/nested_global_field_import.json");
        Request request = globalField.imports(requestBody).request();

        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals("import", request.url().pathSegments().get(2));
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Order(18)
    @Test
    void testExportNestedGlobalField() {
        globalField = stack.globalField(globalFieldUid).addHeader("api_version", "3.2");
        Request request = globalField.export().request();

        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
        Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
        Assertions.assertEquals("export", request.url().pathSegments().get(3));
        Assertions.assertNull(request.url().encodedQuery());
    }

    // @Order(19)
    // @Test
    // void testRestoreNestedGlobalField() {
    //     globalField = stack.globalField(globalFieldUid).addHeader("api_version", "3.2");
    //     JSONObject requestBody = Utils.readJson("mockglobalfield/nested_global_field_restore.json");
    //     Request request = globalField.restore(requestBody).request();
    //     Assertions.assertEquals("PUT", request.method());
    //     Assertions.assertTrue(request.url().isHttps());
    //     Assertions.assertEquals(4, request.url().pathSegments().size());
    //     Assertions.assertEquals("v3", request.url().pathSegments().get(0));
    //     Assertions.assertEquals("global_fields", request.url().pathSegments().get(1));
    //     Assertions.assertEquals(globalFieldUid, request.url().pathSegments().get(2));
    //     Assertions.assertEquals("restore", request.url().pathSegments().get(3));
    //     Assertions.assertNull(request.url().encodedQuery());
    // }
    @Test
    void testApiVersionHeaderIsSet() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        String apiVersion = "3.2";
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        GlobalField gfWithApiVersion = new GlobalField(stack.client, stack.headers, "feature");
        gfWithApiVersion.addHeader("api_version", apiVersion);
        Request request = gfWithApiVersion.fetch().request();
        Assertions.assertEquals(apiVersion, request.header("api_version"));
    }

    // --- Nested Global Field Test Suite ---
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class NestedGlobalFieldTests {

        GlobalField nestedGlobalField;
        String nestedUid = "nested_global_field";
        String apiVersion = "3.2";

        @BeforeEach
        void setupNested() {
            HashMap<String, Object> headers = new HashMap<>();
            headers.put(Util.API_KEY, API_KEY);
            headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
            Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
            nestedGlobalField = new GlobalField(stack.client, stack.headers, nestedUid);
            nestedGlobalField.addHeader("api_version", apiVersion);
        }

        @Test
        @Order(1)
        void testCreateNestedGlobalField() throws IOException {
            JSONObject requestBody = Utils.readJson("globalfield/nested_global_field.json");
            Request request = nestedGlobalField.create(requestBody).request();
            Assertions.assertEquals("https://api.contentstack.io/v3/global_fields", request.url().toString());
            Assertions.assertEquals("/v3/global_fields", request.url().encodedPath());
            Assertions.assertEquals("https", request.url().scheme());
            Assertions.assertEquals("POST", request.method());
            Assertions.assertEquals(apiVersion, request.header("api_version"));
            Response<ResponseBody> response = nestedGlobalField.create(requestBody).execute();
            Assertions.assertEquals(201, response.code());
        }

        @Test
        @Order(2)
        void testGetNestedGlobalField() throws IOException {
            nestedGlobalField.addParam("include_global_fields", true);
            nestedGlobalField.addParam("include_validation_keys", true);
            Request request = nestedGlobalField.fetch().request();
            Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + nestedUid + "?include_global_fields=true&include_validation_keys=true", request.url().toString());
            Assertions.assertEquals("https", request.url().scheme());
            Assertions.assertEquals("GET", request.method());
            Assertions.assertEquals(apiVersion, request.header("api_version"));
            Response<ResponseBody> response = nestedGlobalField.fetch().execute();
            Assertions.assertEquals(200, response.code());
            JsonObject responseBody = Utils.toJson(response).getAsJsonObject();
            JsonObject globalField = responseBody.getAsJsonObject("global_field");
            Assertions.assertEquals("Nested Global Field", globalField.get("title").getAsString());
            Assertions.assertTrue(globalField.has("referred_global_fields"));
            Assertions.assertTrue(globalField.has("validation_keys"));
        }

        @Test
        @Order(3)
        void testUpdateNestedGlobalField() throws IOException {
            JSONObject requestBody = Utils.readJson("globalfield/nested_global_field_update1.json");
            Request request = nestedGlobalField.update(requestBody).request();
            Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + nestedUid, request.url().toString());
            Assertions.assertEquals("/v3/global_fields/" + nestedUid, request.url().encodedPath());
            Assertions.assertEquals("https", request.url().scheme());
            Assertions.assertEquals("PUT", request.method());
            Assertions.assertEquals(apiVersion, request.header("api_version"));
            Response<ResponseBody> response = nestedGlobalField.update(requestBody).execute();
            Assertions.assertEquals(200, response.code());
            JsonObject responseBody = Utils.toJson(response).getAsJsonObject();
            JsonObject globalField = responseBody.getAsJsonObject("global_field");
            Assertions.assertEquals("Nested Global Field", globalField.get("title").getAsString());

        }

        @Test
        @Order(4)
        void testDeleteNestedGlobalField() throws IOException {
            Request request = nestedGlobalField.delete().request();
            Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + nestedUid + "?force=true", request.url().toString());
            Assertions.assertEquals("https", request.url().scheme());
            Assertions.assertEquals("DELETE", request.method());
            Assertions.assertEquals(apiVersion, request.header("api_version"));
            Response<ResponseBody> response = nestedGlobalField.delete().execute();
            Assertions.assertEquals(200, response.code());
            JsonObject responseBody = Utils.toJson(response).getAsJsonObject();
            Assertions.assertEquals("Global Field deleted successfully.", responseBody.get("notice").getAsString());
        }

        @Test
        void testApiVersionHeaderIsolation() throws IOException {
            // Set api_version via addHeader (should not pollute shared headers)
            GlobalField gfWithApiVersion = new GlobalField(stack.client, stack.headers, "feature");
            gfWithApiVersion.addHeader("api_version", apiVersion);
            // Before request, shared headers should NOT have api_version
            Assertions.assertFalse(stack.headers.containsKey("api_version"),
                    "api_version should not be present in shared headers before GlobalField request");
            // Make a request (should use a copy of headers internally)
            Request rq1 = gfWithApiVersion.find().request();

            // After request, shared headers should STILL NOT have api_version
            Assertions.assertFalse(stack.headers.containsKey("api_version"),
                    "api_version should not be present in shared headers after GlobalField request");
            // Now, create a ContentType and make a request
            ContentType ct = stack.contentType("author");
            Request rq2 = ct.fetch().request();
            JSONObject requestBody = Utils.readJson("mockcontenttype/create.json");
            Request rq3 = ct.create(requestBody).request();
            // Again, shared headers should not have api_version
            Assertions.assertFalse(stack.headers.containsKey("api_version"),
                    "api_version should not be present in shared headers after ContentType request");
            // Also, ContentType's request should not have api_version header
            Assertions.assertNull(rq2.header("api_version"),
                    "api_version should not be present in ContentType request headers");
            Assertions.assertNull(rq3.header("api_version"),
                    "api_version should not be present in ContentType request headers");
        }
    }

}
