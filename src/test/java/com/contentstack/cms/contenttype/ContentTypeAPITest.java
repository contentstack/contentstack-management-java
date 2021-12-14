package com.contentstack.cms.contenttype;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

class ContentTypeAPITest {

    public static ContentType contentType;
    public static Logger logger = Logger.getLogger(ContentTypeAPITest.class.getName());

    @BeforeAll
    static void setupBeforeStart() throws IOException {
        Dotenv env = Dotenv.load();
        Contentstack contentstack = new Contentstack.Builder().build();
        contentstack.login(env.get("username"), env.get("password"));
        String apiKey = Objects.requireNonNull(env.get("apiKey"));
        contentType = contentstack.contentType(apiKey);
    }


    @Test
    void testFetchAPI() throws IOException {
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("include_count", true);
        mapQuery.put("include_global_field_schema", true);
        Response<ResponseBody> response = contentType.fetch(mapQuery).execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
        } else {
            Assertions.fail();
        }

    }

    @Test
    void testSingle() throws IOException {
        Map<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("version", 1);
        mapQuery.put("include_global_field_schema", true);
        Response<ResponseBody> response = contentType.single("feeds", mapQuery).execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testCreate() throws IOException {
        JSONObject requestBody = Utils.readJson("mockcontenttype/create.json");
        Response<ResponseBody> response = contentType.create(requestBody).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("notice"));
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testUpdate() throws IOException {
        JSONObject requestBody = Utils.readJson("mockcontenttype/update.json");
        Response<ResponseBody> response = contentType.update("feeds", requestBody).execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("notice"));
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testFieldVisibilityRule() throws IOException {
        JSONObject requestBody = Utils.readJson("mockcontenttype/visibility.json");
        Response<ResponseBody> response = contentType
                .fieldVisibilityRule("feeds",
                        requestBody).execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testDelete() throws IOException {
        Response<ResponseBody> response = contentType.delete("feed").execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
        } else {
            Assertions.fail();
        }
    }


    @Test
    void testDeleteForcefully() throws IOException {
        Response<ResponseBody> response = contentType.delete("feed", true).execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testReference() throws IOException {

        Response<ResponseBody> response = contentType.reference("feed").execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testReferenceIncludingGlobalField() throws IOException {
        Response<ResponseBody> response = contentType.referenceIncludeGlobalField("feeds").execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testExport() throws IOException {
        Response<ResponseBody> response = contentType.export("feeds").execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_types"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testExportByVersion() throws IOException {
        Response<ResponseBody> response = contentType.export("feeds", 1).execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("schema"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testImport() throws IOException {
        Response<ResponseBody> response = contentType.imports().execute();
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("notice"));
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            Assertions.fail();
        }
    }

    @Test
    void testImportOverwrite() throws IOException {
        Response<ResponseBody> response = contentType.importOverwrite().execute();
        assert response.body() != null;
        if (response.isSuccessful()) {
            JsonObject jsonObject = Utils.toJson(response);
            Assertions.assertTrue(jsonObject.has("content_type"));
        } else {
            Assertions.fail();
        }
    }

}
