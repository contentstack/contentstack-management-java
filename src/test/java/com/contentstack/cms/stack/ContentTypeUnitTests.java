package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentTypeUnitTests {

    private final Logger logger = Logger.getLogger(ContentTypeUnitTests.class.getName());
    JSONObject requestBody = Utils.readJson("mockcontenttype/update.json");
    ContentType contentType;

    String API_KEY = TestClient.API_KEY;
    String AUTHTOKEN = TestClient.AUTHTOKEN;
    String managementToken = TestClient.MANAGEMENT_TOKEN;
    private Stack stack;

    @BeforeAll
    public void setUp() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, managementToken);
        stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        contentType = stack.contentType("product");
    }

    @Test
    void testFetch() {
        ContentType ct = stack.contentType();
        ct.addParam("include_count", true);
        ct.addParam("include_global_field_schema", true);
        Request request = ct.find().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", request.url().encodedQuery());    }

    @Test
    void testGetAllContentTypesIncludeCount() {
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.find().request();
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_count"));
    }

    @Test
    void testGetAllContentTypesIncludeQuery() {
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.find().request();
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_global_field_schema"));
    }

    @Test
    void testGetAllContentTypesEncodedPath() {
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.find().request();
        Assertions.assertEquals("/v3/content_types", response.url().encodedPath());
    }

    @Test
    void testGetAllContentTypesEncodedUrl() {
        Request response = contentType.find().request();
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", response.url().encodedQuery());
    }

    @Test
    void testGetAllContentTypesUrl() {
        contentType.clearParams();
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.find().request();    }

    @Test
    void testGetAllContentTypesAuth() {
        Request response = contentType.find().request();
        Assertions.assertEquals(managementToken, response.header("authorization"));
    }

    @Test
    void testGetSingleQueryIncludeGlobalFieldSchema() {
        Request response = contentType.fetch().request();
        Assertions.assertNull(response.url().query());
    }

    @Test
    void testGetSingleEncodedPath() {
        Request response = contentType.fetch().request();
        Assertions.assertEquals("/v3/content_types/product", response.url().encodedPath());
    }

    @Test
    void testGetSingleEncodedQuery() {
        contentType.clearParams();
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.fetch().request();
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", response.url().encodedQuery());
    }

    @Test
    void testGetSingleCompleteUrl() {
        contentType.clearParams();
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.fetch().request();    }

    @Test
    void testGetSingleMethod() {
        Request response = contentType.fetch().request();
        Assertions.assertEquals("GET", response.method());
    }

    @Test
    void testGetSingleHeader() {
        Request response = contentType.fetch().request();
        Assertions.assertEquals(API_KEY, response.header("api_key"));
    }

    @Test
    void testGetSingleAuthorization() {
        Request response = contentType.fetch().request();
        Assertions.assertEquals(managementToken, response.header("authorization"));
    }

    @Test
    void testCreateMethod() {
        Request request = contentType.create(requestBody).request();
        Assertions.assertEquals("POST", request.method());
        logger.info(request.url().toString());
    }

    @Test
    void testCreateHeaders() {
        Request request = contentType.create(requestBody).request();
        Assertions.assertEquals(API_KEY, request.headers().get("api_key"));
        Assertions.assertEquals(managementToken, request.headers().get("authorization"));
    }

    @Test
    void testCreateQuery() {
        Request request = contentType.create(requestBody).request();
        Assertions.assertEquals("/v3/content_types", request.url().encodedPath());
    }

    @Test
    void testUpdateCompleteUrl() {
        contentType.clearParams();
        Request request = contentType.update(
                requestBody).request();    }

    @Test
    void testUpdateMethod() {
        Request request = contentType.update(
                requestBody).request();
        Assertions.assertEquals("PUT", request.method());
    }

    @Test
    void testUpdateHeader() {
        Request request = contentType.update(new JSONObject()).request();
        Assertions.assertEquals(managementToken, request.header("authorization"));
        Assertions.assertEquals(API_KEY, request.header("api_key"));
    }

    @Test
    void testUpdateHeaderSize() {
        Request request = contentType.update(new JSONObject()).request();
        Assertions.assertEquals(2, request.headers().size());
    }

    @Test
    void testUpdateRequestBody() {
        Request request = contentType.update(new JSONObject()).request();
        assert request.body() != null;
        Assertions.assertEquals("multipart/form-data",
                Objects.requireNonNull(request.body().contentType()).toString());
    }

    @Test
    void testUpdateIsHttps() {

        Request request = contentType.update(new JSONObject()).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testFieldVisibilityRuleIsHttps() {
        Request request = contentType.fieldVisibilityRule(requestBody).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testFieldVisibilityRuleCompleteUrl() {
        contentType.clearParams();
        Request request = contentType.fieldVisibilityRule(requestBody).request();    }

    @Test
    void testFieldVisibilityRule() {
        Request request = contentType.fieldVisibilityRule(requestBody).request();
        Assertions.assertEquals("PUT", request.method());
    }

    @Test
    void testFieldVisibilityRuleHeaders() {
        Request request = contentType.fieldVisibilityRule(requestBody).request();
        Assertions.assertEquals(API_KEY, request.header("api_key"));
        Assertions.assertEquals(managementToken, request.header("authorization"));
    }

    @Test
    void testFieldVisibilityRuleHeadersSize() {
        Request request = contentType.fieldVisibilityRule(requestBody).request();
        Assertions.assertEquals(3, request.headers().size());
    }

    @Test
    void testDeleteHeaderSize() {
        Request request = contentType.delete().request();
        Assertions.assertEquals(2, request.headers().size());
    }

    @Test
    void testDeleteHeaderMethod() {
        Request request = contentType.delete().request();
        Assertions.assertEquals("DELETE", request.method());
    }

    @Test
    void testDeleteHeaderCompleteUrl() {
        contentType.clearParams();
        Request request = contentType.delete().request();    }

    @Test
    void testDeleteHeaderCheckHeaders() {
        Request request = contentType.delete().request();
        Assertions.assertEquals(API_KEY, request.headers().get("api_key"));
        Assertions.assertEquals(managementToken, request.headers().get("authorization"));
    }

    @Test
    void testDeleteWithIsForceHeaders() {
        Request request = contentType.delete().request();
        Assertions.assertEquals(API_KEY, request.headers().get("api_key"));
        Assertions.assertEquals(managementToken, request.headers().get("authorization"));
    }

    @Test
    void testDeleteWithIsForce() {
        contentType.clearParams();
        contentType.addParam("force", true);
        Request request = contentType.delete().request();    }

    @Test
    void testDeleteWithIsForceMethod() {
        Request request = contentType.delete().request();
        Assertions.assertEquals("DELETE", request.method());

    }

    @Test
    void testReference() {
        Request request = contentType.reference(false).request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testReferenceCompleteUrl() {
        Request request = contentType.reference(false).request();    }

    @Test
    void testReferenceCompleteUrlIsHTTPS() {
        Request request = contentType.reference(false).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testReferenceEncodedPath() {
        Request request = contentType.reference(false).request();
        Assertions.assertEquals("/v3/content_types/product/references", request.url().encodedPath());
    }

    @Test
    void testReferenceQueryExpectedNull() {
        Request request = contentType.reference(false).request();
        Assertions.assertNotNull(request.url().encodedQuery());
    }

    @Test
    void testReferenceQuery() {
        Request request = contentType.reference(false).request();
        Assertions.assertNotNull(request.url().encodedQuery());
    }

    @Test
    void testReferenceIncludeGlobalFieldEncodedPath() {
        Request request = contentType.referenceIncludeGlobalField().request();
        Assertions.assertEquals("/v3/content_types/product/references", request.url().encodedPath());
    }

    @Test
    void testReferenceIncludeGlobalFieldQuery() {
        Request request = contentType.referenceIncludeGlobalField().request();
        Assertions.assertEquals("include_global_fields=true", request.url().encodedQuery());
    }

    @Test
    void testExportQueryShouldBeNUll() {
        Request request = contentType.export().request();
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Test
    void testExportMethod() {
        Request request = contentType.export().request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testExportUrl() {
        Request request = contentType.export().request();    }

    @Test
    void testExportUrlEncodeQuery() {
        Request request = contentType.export(1).request();
        Assertions.assertEquals("version=1", request.url().encodedQuery());
    }

    @Test
    void testExportIsHttps() {
        Request request = contentType.export(1).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testExportHeaderSize() {
        Request request = contentType.export(1).request();
        Assertions.assertEquals(2, request.headers().size());
    }

    @Test
    void testExportHeaderAPIKeyAuth() {
        Request request = contentType.export(1).request();
        Assertions.assertEquals(API_KEY, request.header("api_key"));
        Assertions.assertEquals(managementToken, request.header("authorization"));
    }

    @Test
    void testImport() {
        Request request = contentType.imports().request();
        Assertions.assertEquals(API_KEY, request.header("api_key"));
        Assertions.assertEquals(managementToken, request.header("authorization"));
    }

    @Test
    void testImportMethod() {
        Request request = contentType.imports().request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testImportIsHttp() {
        Request request = contentType.imports().request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testImportUrl() {
        contentType.clearParams();
        Request request = contentType.imports().request();    }

    @Test
    void testImportIncludeOverwrite() {
        Request request = contentType.importOverwrite().request();    }

    @Test
    void testImportIncludeOverwriteIncludedQuery() {
        Request request = contentType.importOverwrite().request();
        Assertions.assertEquals("overwrite=true", request.url().encodedQuery());
    }

    @Test
    void testImportIncludeOverwriteIncludedPath() {
        Request request = contentType.importOverwrite().request();
        Assertions.assertEquals("/v3/content_types/import", request.url().encodedPath());
    }

    @Test
    void testImportIncludeOverwriteFalse() {
        Request request = contentType.importOverwrite().request();    }

    @Test
    void testHeaderAndParams() {
        contentType.addParam("key", "value");
        contentType.addHeader("headerKey", "headerValue");
        contentType.removeParam("key");
        Request request = contentType.importOverwrite().request();    }
}
