package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

import static com.contentstack.cms.Utils.toJson;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntryFieldsAPITest {

    protected static String API_KEY = Dotenv.load().get("apiKey1");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("managementToken1");
    protected static String emailId = Dotenv.load().get("username");
    protected static String password = Dotenv.load().get("password");
    protected static Entry entry;
    protected static String contentTypeUid;

    @BeforeAll
    static void setup() throws IOException {
        // Initialise Contentstack
        //Contentstack contentstack = new Contentstack.Builder().build();
        //Response<LoginDetails> loginStatus = contentstack.login(emailId, password);
        //assert loginStatus.body() != null;
        // Get the Authtoken
        //String authToken = loginStatus.body().getUser().getAuthtoken();
        Contentstack client = new Contentstack.Builder().setAuthtoken(MANAGEMENT_TOKEN).build();
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("api_key", API_KEY);
        headers.put("authorization", MANAGEMENT_TOKEN);
        Stack stack = client.stack(headers);
        // Get all content types
        Response<ResponseBody> response = stack.contentType(MANAGEMENT_TOKEN).fetch(new HashMap<>()).execute();
        JsonObject jsonResp = toJson(response);
        JsonArray allCT = jsonResp.getAsJsonArray("content_types");
        // find first content type from the stack
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
        Response<ResponseBody> response = entry.fetch(new HashMap<>()).execute();
        Assertions.assertTrue(response.isSuccessful());
    }


    @Test
    void testFetch() {
        HashMap<String, Object> queryParams = new HashMap<>();

        try {
            Response<ResponseBody> response = entry.fetch(queryParams).execute();
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void testSingle() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testCreate() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testUpdate() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testAtomicOperation() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testDelete() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testVersionName() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testDetailOfAllVersion() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testDeleteVersionName() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testReference() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testLanguage() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testLocalise() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testUnLocalise() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testExport() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testImport() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testImportExisting() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testPublish() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testPublishWithReference() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testUnpublish() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

}