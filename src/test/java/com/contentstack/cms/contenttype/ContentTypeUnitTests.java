package com.contentstack.cms.contenttype;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.RetryCallback;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContentTypeUnitTests {

    private final String TAG = ContentTypeUnitTests.class.getSimpleName();
    private final Logger log = Logger.getLogger(RetryCallback.class.getName());
    private Dotenv dotenv;
    ContentType contentType;
    private final String contentTypeUid = "product";


    @BeforeAll
    public void setUp() {
        log.info("Started unit tests for contentType");
        dotenv = Dotenv.load();
        String apiKey = dotenv.get("api_key");
        String authtoken = dotenv.get("auth_token");
        assert apiKey != null;
        contentType = new Contentstack.Builder().setAuthtoken(authtoken).build().contentType(apiKey);
    }


    @Test
    void testGetAllContentTypes() {
        String apiKey = dotenv.get("api_key");
        String authtoken = dotenv.get("auth_token");
        Map<String, Boolean> mapQuery = new HashMap<>();
        mapQuery.put("include_count", true);
        mapQuery.put("include_global_field_schema", true);
        Request response = contentType.fetch(
                "managementToken@fake", mapQuery).request();
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_count"));
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_global_field_schema"));
        Assertions.assertEquals("/v3/content_types", response.url().encodedPath());
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", response.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types?include_count=true&include_global_field_schema=true", response.url().toString());
        Assertions.assertEquals("GET", response.method());
//        Assertions.assertEquals(apiKey, response.header("api_key"));
        Assertions.assertEquals("managementToken@fake", response.header("authorization"));
//        Assertions.assertEquals(authtoken, response.header("authtoken"));
    }


    @Test
    void testGetSingle() {
        String apiKey = dotenv.get("api_key");
        String authtoken = dotenv.get("auth_token");
        Map<String, Boolean> mapQuery = new HashMap<>();
        mapQuery.put("include_count", true);
        mapQuery.put("include_global_field_schema", true);
        Request response = contentType.single(contentTypeUid,
                "managementToken@fake", mapQuery).request();
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_count"));
        Assertions.assertTrue(Objects.requireNonNull(response.url().query()).contains("include_global_field_schema"));
        Assertions.assertEquals("/v3/content_types", response.url().encodedPath());
        Assertions.assertEquals("include_count=true&include_global_field_schema=true", response.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types?include_count=true&include_global_field_schema=true", response.url().toString());
        Assertions.assertEquals("GET", response.method());
        Assertions.assertEquals(apiKey, response.header("api_key"));
        Assertions.assertEquals("managementToken@fake", response.header("authorization"));
        Assertions.assertEquals(authtoken, response.header("authtoken"));
    }

    @Test
    void testCreate() {
        String bodyStr = "";
        Request response = contentType.create(
                "authorization",
                bodyStr).request();
    }

    @Test
    void testUpdate() {
        String bodyStr = "";
        Request response = contentType.update(
                "contentTypeUid",
                "authorization",
                bodyStr).request();
    }

    @Test
    void testFieldVisibilityRule() {
        String bodyStr = "";
        Request response = contentType.fieldVisibilityRule(
                contentTypeUid,
                "auth",
                bodyStr).request();
    }

    @Test
    void testDelete() {
        Request response = contentType.delete(
                "auth").request();
    }

    @Test
    void testDeleteByForce() {
        Request response = contentType.delete(
                "auth", true).request();
    }

    @Test
    void testReference() {
        Request response = contentType.reference(
                contentTypeUid, "auth").request();
    }

    @Test
    void testReferenceIncludeGlobalField() {
        Request response = contentType.reference(
                contentTypeUid,
                "auth",
                true).request();
    }

    @Test
    void testExport() {
        Request response = contentType.export(
                "auth").request();
    }

    @Test
    void testExportIncludeVersion() {
        Request response = contentType.export(
                "auth", 1).request();
    }

    @Test
    void testImport() {
        Request response = contentType.imports(
                "auth"
        ).request();
    }

    @Test
    void testImportIncludeOverwrite() {
        Request response = contentType.imports(
                "auth", true
        ).request();
    }
}
