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
import java.util.logging.Logger;

@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContentTypeAPITest {

    public static ContentType contentType;
    public static Logger logger = Logger.getLogger(ContentTypeAPITest.class.getName());
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("managementToken1");
    private static String contentTypeUid = "following";
    private static Stack stack;


    @BeforeAll
    public void setUp() throws IOException {
        Contentstack contentstack = new Contentstack.Builder().build();
        String emailId = Dotenv.load().get("username");
        String password = Dotenv.load().get("password");
        contentstack.login(emailId, password);
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        stack = contentstack.stack(headers);
    }

    @Order(14)
    @Test
    void setupBeforeStart() throws IOException {
        contentType = stack.contentType(contentTypeUid);
        Response<ResponseBody> response = contentType.fetch().execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            logger.info(jsonObject.toString());
            int position = jsonObject.size() - 1;
            JsonElement arrayResponse = jsonObject.getAsJsonArray("content_types").get(position);
            contentTypeUid = arrayResponse.getAsJsonObject().get("uid").getAsString();
            Assertions.assertTrue(jsonObject.has("content_types"));
        }
    }

    @Order(10)
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
        contentType.addParam("include_count", true);
        contentType.addParam("include_global_field_schema", true);
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
        Response<ResponseBody> response = contentType.single().execute();
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
        Response<ResponseBody> response = contentType.update( requestBody).execute();
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
                 requestBody).execute();
        Assertions.assertFalse(response.isSuccessful());

    }

    @Order(6)
    @Test
    @Disabled("No need to import all time")
    void testReference() throws IOException {
        Response<ResponseBody> response = contentType.reference( false).execute();
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
        Response<ResponseBody> response = contentType.referenceIncludeGlobalField().execute();
        assert response.body() != null;
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(8)
    @Test
    void testExport() throws IOException {
        Response<ResponseBody> response = contentType.export().execute();
        assert response.body() != null;
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(9)
    @Test
    void testExportByVersion() throws IOException {
        Response<ResponseBody> response = contentType.export( 1).execute();
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
    @Disabled("avoid running delete forcefully")
    void testDeleteContentType() throws IOException {
        Response<ResponseBody> response = contentType.delete().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(13)
    @Disabled("avoid running delete forcefully")
    @Test
    void testDeleteForcefully() throws IOException {
        Response<ResponseBody> response = contentType.delete().execute();
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
