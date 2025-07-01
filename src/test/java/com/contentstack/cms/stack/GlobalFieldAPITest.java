package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GlobalFieldAPITest {

    public static GlobalField globalField;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
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

}