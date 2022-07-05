package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
@Tag("api")
class ContentTypeAPITest {

    protected static String API_KEY = Dotenv.load().get("apiKey1");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("managementToken1");
    public static ContentType contentType;
    public static Logger logger = Logger.getLogger(ContentTypeAPITest.class.getName());
    private static String contentTypeUid;

    @BeforeAll
    static void setupBeforeStart() throws IOException {

        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().build().stack(headers);
        // TODO: Setting ManagementToken more than once
        contentType = stack.contentType("contentTypeUid");
        Response<ResponseBody> response = contentType.fetch().execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            int position = jsonObject.size() - 1;
            JsonElement arrayResponse = jsonObject.getAsJsonArray("content_types").get(position);
            contentTypeUid = arrayResponse.getAsJsonObject().get("uid").getAsString();
            Assertions.assertTrue(jsonObject.has("content_types"));
        }
    }

    @Order(1)
    @Test
    void testCreate() throws IOException {
        JSONObject requestBody = Utils.readJson("mockcontenttype/create.json");
        Response<ResponseBody> response = contentType.create(requestBody).execute();
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(201, response.code());
    }

    @Test
    @Order(2)
    void testFetchAPI() throws IOException {
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("include_count", true);
        mapQuery.put("include_global_field_schema", true);
        Response<ResponseBody> response = contentType.fetch().execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
            Assertions.assertTrue(jsonObject.has("count"));
        } else {
            logger.severe("testFetchAPI is failing");
            Assertions.fail();
        }

    }

    @Test
    @Order(3)
    void testSingleApi() throws IOException {
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("version", 1);
        mapQuery.put("include_global_field_schema", true);
        Response<ResponseBody> response = contentType.single(contentTypeUid).execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            logger.severe("testSingle is failing");
            Assertions.fail();
        }
    }

    @Order(4)
    @Test
    void testUpdate() throws IOException {
        JSONObject requestBody = Utils.readJson("mockcontenttype/update.json");
        Response<ResponseBody> response = contentType.update("fake_content_type", requestBody).execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("notice"));
            Assertions.assertTrue(jsonObject.has("content_type"));
        }
    }

    @Order(5)
    @Test
    void testFieldVisibilityRule() throws IOException {
        JSONObject requestBody = Utils.readJson("mockcontenttype/visibility.json");
        Response<ResponseBody> response = contentType.fieldVisibilityRule(
                "fake_content_type", requestBody).execute();
        Assertions.assertFalse(response.isSuccessful());

    }

    @Order(6)
    @Test
    void testReference() throws IOException {
        Response<ResponseBody> response = contentType.reference(contentTypeUid, false).execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("references"));
        } else {
            logger.severe("testReference is failing");
            Assertions.fail();
        }
    }

    @Order(7)
    @Test
    void testReferenceIncludingGlobalField() throws IOException {
        Response<ResponseBody> response = contentType.referenceIncludeGlobalField(contentTypeUid).execute();
        assert response.body() != null;
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(8)
    @Test
    void testExport() throws IOException {
        Response<ResponseBody> response = contentType.export(contentTypeUid).execute();
        assert response.body() != null;
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(9)
    @Test
    void testExportByVersion() throws IOException {
        Response<ResponseBody> response = contentType.export(contentTypeUid, 1).execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("schema"));
        } else {
            logger.severe("testExportByVersion is failing");
            Assertions.fail();
        }
    }

    @Order(10)
    @Disabled("No need to import all time")
    @Test
    void testImport() throws IOException {
        Response<ResponseBody> response = contentType.imports().execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("notice"));
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            logger.severe("testImport is failing");
            Assertions.fail();
        }
    }

    @Order(11)
    @Disabled("Avoid running same test again")
    @Test
    void testImportOverwrite() throws IOException {
        Response<ResponseBody> response = contentType.importOverwrite().execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            logger.severe("testImportOverwrite is failing");
            Assertions.fail();
        }
    }

    @Order(12)
    @Test
    void testDelete() throws IOException {
        Response<ResponseBody> response = contentType.delete("fake_content_type").execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(13)
    @Disabled("avoid running delete forcefully")
    @Test
    void testDeleteForcefully() throws IOException {
        Response<ResponseBody> response = contentType.delete(contentTypeUid, true).execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("fake_content_type"));
        } else {
            logger.severe("testDeleteForcefully is failing");
            Assertions.fail();
        }
    }

}
