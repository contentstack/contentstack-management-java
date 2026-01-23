package com.contentstack.cms.stack;

import java.io.IOException;
import java.util.List;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.Request;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import retrofit2.Response;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentTypeAPITest {

    public static ContentType contentType;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static String contentTypeUid = "contentType";
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
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(2)
    @Test
    void testCreate() {
        JSONObject requestBody = Utils.readJson("mockcontenttype/create.json");
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.create(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    @Order(3)
    void testFetchAPI() {
        contentType = stack.contentType(contentTypeUid);
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);

        Request request = contentType.find().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_count=true&include_global_field_schema=true",
                request.url().query().toString());    }

    @Test
    @Order(4)
    void testSingleApi() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.fetch().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(5)
    @Test
    void testUpdate() {
        contentType = stack.contentType(contentTypeUid);
        JSONObject requestBody = Utils.readJson("mockcontenttype/update.json");
        Request request = contentType.update(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(6)
    @Test
    void testFieldVisibilityRule() {
        contentType = stack.contentType(contentTypeUid);
        JSONObject requestBody = Utils.readJson("mockcontenttype/visibility.json");
        Request request = contentType.fieldVisibilityRule(requestBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(7)
    @Test
    void testReference() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.reference(false).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Order(8)
    @Test
    void testReferenceIncludingGlobalField() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.referenceIncludeGlobalField().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_global_fields=true", request.url().encodedQuery());    }

    @Order(9)
    @Test
    void testExport() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.export().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(10)
    @Test
    void testExportByVersion() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.export(1).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("version=1", request.url().encodedQuery());    }

    @Order(11)
    @Test
    void testImport() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.imports().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(12)
    @Test
    void testImportOverwrite() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.importOverwrite().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("overwrite=true", request.url().encodedQuery());    }

    @Order(13)
    @Test
    void testDeleteContentType() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.delete().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(14)
    @Test
    void testDeleteForcefully() {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.delete().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void testcontentPojo() throws IOException {
        contentType = stack.contentType(contentTypeUid);
        Request request = contentType.fetchAsPojo().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void testcontentPojoList() throws IOException {
        contentType = stack.contentType(contentTypeUid);
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);

        Request request = contentType.findAsPojo().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_count=true&include_global_field_schema=true",
                request.url().query().toString());    }

}
