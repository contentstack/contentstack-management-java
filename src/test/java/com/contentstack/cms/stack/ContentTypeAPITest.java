package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentTypeAPITest {

    public static ContentType contentType;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static String contentTypeUid = "fake_content_type";
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
    void setupBeforeStart() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.find().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types", request.url().toString());
    }

    @Order(2)
    @Test
    void testCreate() {
        JSONObject requestBody = Utils.readJson("mockcontenttype/create.json");
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.create(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types", request.url().toString());

    }

    @Test
    @Order(3)
    void testFetchAPI() {
        contentType = stack.contentType(contentTypeUid);
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);

        Request request = contentType.find().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_count=true&include_global_field_schema=true",
                request.url().query().toString());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types?include_count=true&include_global_field_schema=true",
                request.url().toString());

    }

    @Test
    @Order(4)
    void testSingleApi() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.fetch().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/fake_content_type", request.url().toString());
    }

    @Order(5)
    @Test
    void testUpdate() {
        contentType = stack.contentType(contentTypeUid);
        JSONObject requestBody = Utils.readJson("mockcontenttype/update.json");
        Request request = contentType.update(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/fake_content_type", request.url().toString());
    }

    @Order(6)
    @Test
    void testFieldVisibilityRule() {
        contentType = stack.contentType(contentTypeUid);
        JSONObject requestBody = Utils.readJson("mockcontenttype/visibility.json");
        Request request = contentType.fieldVisibilityRule(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/fake_content_type", request.url().toString());

    }

    @Order(7)
    @Test
    void testReference() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.reference(false).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types/fake_content_type/references?include_global_fields=false",
                request.url().toString());
    }

    @Order(8)
    @Test
    void testReferenceIncludingGlobalField() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.referenceIncludeGlobalField().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_global_fields=true", request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types/fake_content_type/references?include_global_fields=true",
                request.url().toString());
    }

    @Order(9)
    @Test
    void testExport() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.export().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/fake_content_type/export",
                request.url().toString());
    }

    @Order(10)
    @Test
    void testExportByVersion() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.export(1).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("version=1", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/fake_content_type/export?version=1",
                request.url().toString());
    }

    @Order(11)
    @Test
    void testImport() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.imports().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/import", request.url().toString());
    }

    @Order(12)
    @Test
    void testImportOverwrite() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.importOverwrite().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("overwrite=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/import?overwrite=true",
                request.url().toString());
    }

    @Order(13)
    @Test
    void testDeleteContentType() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.delete().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/fake_content_type", request.url().toString());
    }

    @Order(14)
    @Test
    void testDeleteForcefully() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.delete().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/fake_content_type", request.url().toString());
    }

}
