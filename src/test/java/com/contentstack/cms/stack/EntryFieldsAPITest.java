package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

import static com.contentstack.cms.Utils.toJson;

@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntryFieldsAPITest {

    private static Entry entry;
    private static String contentTypeUid;
    private static ContentType contentType;

    @BeforeAll
    public static void setup() throws IOException {
        String username = Dotenv.load().get("username");
        String password = Dotenv.load().get("password");
        String apiKey = Dotenv.load().get("apiKey");
        String managementToken = Dotenv.load().get("managementToken");
        Contentstack client = new Contentstack.Builder().build();
        client.login(username, password);
        Stack stack = client.stack();
        assert apiKey != null;
        assert managementToken != null;

        contentType = client.stack(apiKey, managementToken).contentType();
        Response<ResponseBody> response = contentType.find().execute();
        if (response.isSuccessful()){
            JsonObject contentTypesResp = toJson(response);
            JsonArray listCT = contentTypesResp.getAsJsonArray("content_types");
            if (listCT.size() > 0) {
                int index = (listCT.size() - 1);
                contentTypeUid = String.valueOf(listCT.get(index).getAsJsonObject().get("uid").getAsString());
            }
        }else{
            JsonObject error = toJson(response);
            System.out.println(error.getAsJsonObject());
        }
        contentType = client.stack(apiKey, managementToken).contentType(contentTypeUid);
    }

    @Test
    @Order(1)
    void testEntryFindAll() throws IOException {
        Response<ResponseBody> response = contentType.entry().find().execute();
        JsonObject jsonResp = toJson(response);
        JsonArray allCT = jsonResp.getAsJsonArray("entries");
        String entryUid = allCT.get(0).getAsJsonObject().get("uid").getAsString();
        entry = contentType.entry(entryUid);
        Assertions.assertTrue(allCT.size() > 0);
    }

    @Order(2)
    @Test
    void testEntryFetch() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(3)
    @Test
    @Disabled
    void testEntryCreate() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryCreate = new JSONObject();
        entryCreate.put("entry", body);
        Response<ResponseBody> response = entry.create(entryCreate).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(4)
    @Test
    void testUpdate() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryUpdate = new JSONObject();
        entryUpdate.put("entry", body);
        Response<ResponseBody> response = entry.update(entryUpdate).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(5)
    @Test
    void testAtomicOperation() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Response<ResponseBody> response = entry.atomicOperation(entryBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(6)
    @Test
    @Disabled
    void testEntryDelete() throws IOException {
        Response<ResponseBody> response = entry.delete().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(7)
    @Test
    @Disabled
    void testEntryVersionName() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Response<ResponseBody> response = entry.versionName(1, entryBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(8)
    @Test
    void testEntryDetailOfAllVersion() throws IOException {
        Response<ResponseBody> response = entry.detailOfAllVersion().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(9)
    @Test
    @Disabled
    void testEntryDeleteVersionName() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Response<ResponseBody> response = entry.deleteVersionName(1, entryBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(10)
    @Test
    void testEntryGetReference() throws IOException {
        Response<ResponseBody> response = entry.getReference().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(11)
    @Test
    void testEntryLocalise() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Response<ResponseBody> response = entry.localize(entryBody, "en-us").execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(12)
    @Test
    void testEntryExport() throws IOException {
        Response<ResponseBody> response = entry.export().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(13)
    @Test
    @Disabled
    void testEntryImports() throws IOException {
        Response<ResponseBody> response = entry.imports().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(14)
    @Test
    @Disabled
    void testEntryImportExisting() throws IOException {
        Response<ResponseBody> response = entry.importExisting().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(15)
    @Test
    @Disabled
    void testEntryPublish() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Response<ResponseBody> response = entry.publish(entryBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(16)
    @Test
    @Disabled
    void testEntryPublishWithReference() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Response<ResponseBody> response = entry.publishWithReference(entryBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Order(17)
    @Test
    @Disabled
    void testPublishWithReference() throws IOException {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.ishaileshmishra.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Response<ResponseBody> response = entry.unpublish(entryBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

}
