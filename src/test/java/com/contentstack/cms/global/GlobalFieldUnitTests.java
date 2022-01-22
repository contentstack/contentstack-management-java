package com.contentstack.cms.global;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GlobalFieldUnitTests {

    protected String globalFiledUid = "description";
    protected static String GLOBAL_AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static GlobalField globalField;

    @BeforeAll
    static void setup() {
        globalField = new Contentstack.Builder()
                .setAuthtoken(GLOBAL_AUTHTOKEN).build()
                .globalField(API_KEY, MANAGEMENT_TOKEN);
    }

    @Test
    void testGetAllGlobalFieldsHeader() {
        Request response = globalField.fetch().request();
        Assertions.assertEquals(API_KEY, response.headers().get("api_key"));
    }

    @Test
    void testGetAllGlobalFieldsGet() {
        Request response = globalField.fetch().request();
        Assertions.assertEquals("GET", response.method());
    }

    @Test
    void testGetAllGlobalFieldsUrl() {
        Request response = globalField.fetch().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields", response.url().toString());
    }

    @Test
    void testGetAllGlobalFieldsIsHttps() {
        Request response = globalField.fetch().request();
        Assertions.assertTrue(response.isHttps());
    }

    @Test
    void testGetAllGlobalFieldsEncodedPath() {
        Request response = globalField.fetch().request();
        Assertions.assertEquals("/v3/global_fields", response.url().encodedPath());
    }

    @Test
    void testSingleGFEncodedPath() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertEquals("/v3/global_fields/" + globalFiledUid, response.url().encodedPath());
    }

    @Test
    void testSingleGFUrl() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + globalFiledUid,
                response.url().toString());
    }

    @Test
    void testSingleGFSchema() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertEquals("https", response.url().scheme());
    }

    @Test
    void testSingleGFQuery() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertNull(response.url().query(), "No Query is expected");
    }

    @Test
    void testSingleGFQueryIsHttp() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertTrue(response.url().isHttps(), "Expected Https request, purely secured and trusted");
    }

    @Test
    void testSingleGFQueryHeaderSize() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertEquals(2, response.headers().size());
    }

    @Test
    void testSingleGFQueryHeaderSizeMethod() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertEquals("GET", response.method());
    }

    @Test
    void testSingleGFQueryHeaderAPIKey() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertEquals(API_KEY, response.headers("api_key").get(0));
    }

    @Test
    void testSingleGFQueryHeaderAuthtoken() {
        Request response = globalField.single(globalFiledUid).request();
        Assertions.assertEquals(MANAGEMENT_TOKEN, response.headers("authorization").get(0));
    }

    @Test
    void testUpdateGFQueryHeaders() {
        Request response = globalField.update("globalFieldUid", "requestBody").request();
        Assertions.assertEquals(2, response.headers().size());
    }

    @Test
    void testUpdateGFQueryMethod() {
        Request response = globalField.update(globalFiledUid, "requestBody").request();
        Assertions.assertEquals("PUT", response.method());
    }

    @Test
    void testUpdateGFQueryUrl() {
        Request response = globalField.update(globalFiledUid, "requestBody").request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/" + globalFiledUid,
                response.url().toString());
    }

    @Test
    void testDeleteGlobalField() {
        Request response = globalField.delete("globalFieldUid").request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/globalFieldUid?force=true",
                response.url().toString());
    }

    // @Test
    // void testImportGlobalField() {
    // Object fileObj = null;
    // Request response = globalField.imports(null).request();
    // Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/",
    // response.url().toString());
    // }

    @Test
    void testExportGlobalField() {
        Request response = globalField.export("globalFieldUid").request();
        Assertions.assertEquals("https://api.contentstack.io/v3/global_fields/globalFieldUid/export",
                response.url().toString());
    }

}
