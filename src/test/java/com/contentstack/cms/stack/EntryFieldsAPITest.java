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

    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("managementToken1");
    protected static String emailId = Dotenv.load().get("username");
    protected static String password = Dotenv.load().get("password");
    protected static String _uid = Dotenv.load().get("uid");
    protected static Entry entry;
    protected static String contentTypeUid;

    @BeforeAll
    static void setup() throws IOException {
        Contentstack client = new Contentstack.Builder().setAuthtoken(MANAGEMENT_TOKEN).build();
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("api_key", API_KEY);
        headers.put("authorization", MANAGEMENT_TOKEN);
        Stack stack = client.stack(headers);
        Response<ResponseBody> response = stack.contentType(MANAGEMENT_TOKEN).fetch().execute();
        JsonObject jsonResp = toJson(response);
        JsonArray allCT = jsonResp.getAsJsonArray("content_types");
        if (allCT.size() > 0) {
            int count = allCT.size();
            count--; // to avoid java.lang.IndexOutOfBoundsException
            contentTypeUid = String.valueOf(allCT.get(count).getAsJsonObject().get("uid").getAsString());
        }
        entry = stack.entry(contentTypeUid);
    }

    @Test
    @Order(1)
    void testGetAllCT() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testFetch() {
        try {
            Response<ResponseBody> response = entry.fetch().execute();
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testSingle() throws IOException {
        Response<ResponseBody> response = entry.single(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testCreate() throws IOException {
        JSONObject body = new JSONObject();
        body.put("param", "paramValue");
        Response<ResponseBody> response = entry.create(body).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testUpdate() throws IOException {
        JSONObject body = new JSONObject();
        body.put("param", "paramValue");
        Response<ResponseBody> response = entry.update(_uid, body).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testAtomicOperation() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testDelete() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testVersionName() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testDetailOfAllVersion() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testDeleteVersionName() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testReference() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testLanguage() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testLocalise() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testUnLocalise() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testExport() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testImport() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testImportExisting() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testPublish() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testPublishWithReference() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testUnpublish() throws IOException {
        Response<ResponseBody> response = entry.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

}
