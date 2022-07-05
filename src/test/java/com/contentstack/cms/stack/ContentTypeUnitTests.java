package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentTypeUnitTests {

    private final Logger logger = Logger.getLogger(ContentTypeUnitTests.class.getName());
    JSONObject requestBody = Utils.readJson("mockcontenttype/update.json");
    ContentType contentType;
    private final String contentTypeUid = "product";
    String API_KEY = Dotenv.load().get("api_key");
    String AUTHTOKEN = Dotenv.load().get("authToken");
    String managementToken = Dotenv.load().get("auth_token");

    @BeforeAll
    public void setUp() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, managementToken);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        contentType = stack.contentType();
    }

    @Test
    void testFetch() {
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request request = contentType.fetch().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types?include_count=true&include_global_field_schema=true",
                request.url().toString());
    }

    @Test
    void testGetAllContentTypesIncludeCount() {
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.fetch().request();
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_count"));
    }

    @Test
    void testGetAllContentTypesIncludeQuery() {
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.fetch().request();
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_global_field_schema"));
    }

    @Test
    void testGetAllContentTypesEncodedPath() {
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
        Request response = contentType.fetch().request();
        Assertions.assertEquals("/v3/content_types", response.url().encodedPath());
    }

    @Test
    void testGetAllContentTypesEncodedUrl() {
        Request response = contentType.fetch().request();
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", response.url().encodedQuery());
    }

    @Test
    void testGetAllContentTypesUrl() {
        Request response = contentType.fetch().request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types?include_count=true&include_global_field_schema=true",
                response.url().toString());
    }

    @Test
    void testGetAllContentTypesAuth() {
        Request response = contentType.fetch().request();
        Assertions.assertEquals(managementToken, response.header("authorization"));
    }

    @Test
    void testGetSingleQueryIncludeGlobalFieldSchema() {
        Request response = contentType.single(contentTypeUid).request();
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_global_field_schema"));
    }

    @Test
    void testGetSingleEncodedPath() {
        Request response = contentType.single(contentTypeUid).request();
        Assertions.assertEquals("/v3/content_types/product", response.url().encodedPath());
    }

    @Test
    void testGetSingleEncodedQuery() {
        Request response = contentType.single(contentTypeUid).request();
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", response.url().encodedQuery());
    }

    @Test
    void testGetSingleCompleteUrl() {
        Request response = contentType.single(contentTypeUid).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types/product?include_count=true&include_global_field_schema=true",
                response.url().toString());
    }

    @Test
    void testGetSingleMethod() {
        Request response = contentType.single(contentTypeUid).request();
        Assertions.assertEquals("GET", response.method());
    }

    @Test
    void testGetSingleHeader() {
        Request response = contentType.single(contentTypeUid).request();
        Assertions.assertEquals(API_KEY, response.header("api_key"));
    }

    @Test
    void testGetSingleAuthorization() {
        Request response = contentType.single(contentTypeUid).request();
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
        Request request = contentType.update(contentTypeUid,
                requestBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product",
                request.url().toString());
    }

    @Test
    void testUpdateMethod() {
        Request request = contentType.update(contentTypeUid,
                requestBody).request();
        Assertions.assertEquals("PUT", request.method());
    }

    @Test
    void testUpdateHeader() {
        Request request = contentType.update(contentTypeUid, new JSONObject()).request();
        Assertions.assertEquals(managementToken, request.header("authorization"));
        Assertions.assertEquals(API_KEY, request.header("api_key"));
    }

    @Test
    void testUpdateHeaderSize() {
        Request request = contentType.update(contentTypeUid, new JSONObject()).request();
        Assertions.assertEquals(2, request.headers().size());
    }

    @Test
    void testUpdateRequestBody() {
        Request request = contentType.update(contentTypeUid, new JSONObject()).request();
        assert request.body() != null;
        Assertions.assertEquals("multipart/form-data",
                Objects.requireNonNull(request.body().contentType()).toString());
    }

    @Test
    void testUpdateIsHttps() {

        Request request = contentType.update(contentTypeUid, new JSONObject()).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testFieldVisibilityRuleIsHttps() {

        Request request = contentType.fieldVisibilityRule(contentTypeUid, requestBody).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testFieldVisibilityRuleCompleteUrl() {

        Request request = contentType.fieldVisibilityRule(contentTypeUid, requestBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product", request.url().toString());
    }

    @Test
    void testFieldVisibilityRule() {
        Request request = contentType.fieldVisibilityRule(contentTypeUid, requestBody).request();
        Assertions.assertEquals("PUT", request.method());
    }

    @Test
    void testFieldVisibilityRuleHeaders() {
        Request request = contentType.fieldVisibilityRule(contentTypeUid, requestBody).request();
        Assertions.assertEquals(API_KEY, request.header("api_key"));
        Assertions.assertEquals(managementToken, request.header("authorization"));
    }

    @Test
    void testFieldVisibilityRuleHeadersSize() {
        Request request = contentType.fieldVisibilityRule(contentTypeUid, requestBody).request();
        Assertions.assertEquals(2, request.headers().size());
    }

    @Test
    void testDeleteHeaderSize() {
        Request request = contentType.delete(contentTypeUid).request();
        Assertions.assertEquals(2, request.headers().size());
    }

    @Test
    void testDeleteHeaderMethod() {
        Request request = contentType.delete(contentTypeUid).request();
        Assertions.assertEquals("DELETE", request.method());
    }

    @Test
    void testDeleteHeaderCompleteUrl() {
        Request request = contentType.delete(contentTypeUid).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product", request.url().toString());
    }

    @Test
    void testDeleteHeaderCheckHeaders() {
        Request request = contentType.delete(contentTypeUid).request();
        Assertions.assertEquals(API_KEY, request.headers().get("api_key"));
        Assertions.assertEquals(managementToken, request.headers().get("authorization"));
    }

    @Test
    void testDeleteWithIsForceHeaders() {
        Request request = contentType.delete(contentTypeUid, true).request();
        Assertions.assertEquals(API_KEY, request.headers().get("api_key"));
        Assertions.assertEquals(managementToken, request.headers().get("authorization"));
    }

    @Test
    void testDeleteWithIsForce() {
        Request request = contentType.delete(contentTypeUid, true).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product?force=true",
                request.url().toString());

    }

    @Test
    void testDeleteWithIsForceMethod() {
        Request request = contentType.delete(contentTypeUid, true).request();
        Assertions.assertEquals("DELETE", request.method());

    }

    @Test
    void testReference() {
        Request request = contentType.reference(contentTypeUid, false).request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testReferenceCompleteUrl() {
        Request request = contentType.reference(contentTypeUid, false).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/references?include_global_fields=false",
                request.url().toString());
    }

    @Test
    void testReferenceCompleteUrlIsHTTPS() {
        Request request = contentType.reference(contentTypeUid, false).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testReferenceEncodedPath() {
        Request request = contentType.reference(contentTypeUid, false).request();
        Assertions.assertEquals("/v3/content_types/product/references", request.url().encodedPath());
    }

    @Test
    void testReferenceQueryExpectedNull() {
        Request request = contentType.reference(contentTypeUid, false).request();
        Assertions.assertNotNull(request.url().encodedQuery());
    }

    @Test
    void testReferenceQuery() {
        Request request = contentType.reference(contentTypeUid, false).request();
        Assertions.assertNotNull(request.url().encodedQuery());
    }

    @Test
    void testReferenceIncludeGlobalFieldEncodedPath() {
        Request request = contentType.referenceIncludeGlobalField(
                contentTypeUid).request();
        Assertions.assertEquals("/v3/content_types/product/references", request.url().encodedPath());
    }

    @Test
    void testReferenceIncludeGlobalFieldQuery() {
        Request request = contentType.referenceIncludeGlobalField(contentTypeUid).request();
        Assertions.assertEquals("include_global_fields=true", request.url().encodedQuery());
    }

    @Test
    void testExportQueryShouldBeNUll() {
        Request request = contentType.export(contentTypeUid).request();
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Test
    void testExportMethod() {
        Request request = contentType.export(contentTypeUid).request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testExportUrl() {
        Request request = contentType.export(contentTypeUid).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/export",
                request.url().toString());
    }

    @Test
    void testExportUrlEncodeQuery() {
        Request request = contentType.export(contentTypeUid, 1).request();
        Assertions.assertEquals("version=1", request.url().encodedQuery());
    }

    @Test
    void testExportIsHttps() {
        Request request = contentType.export(contentTypeUid, 1).request();
        Assertions.assertTrue(request.isHttps());
    }

    @Test
    void testExportHeaderSize() {
        Request request = contentType.export(contentTypeUid, 1).request();
        Assertions.assertEquals(2, request.headers().size());
    }

    @Test
    void testExportHeaderAPIKeyAuth() {
        Request request = contentType.export(contentTypeUid, 1).request();
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
        Request request = contentType.imports().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/import", request.url().toString());
    }

    @Test
    void testImportIncludeOverwrite() {
        Request request = contentType.importOverwrite().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/import?overwrite=true",
                request.url().toString());
    }

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
        Request request = contentType.importOverwrite().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/import?overwrite=true",
                request.url().toString());
    }
}
