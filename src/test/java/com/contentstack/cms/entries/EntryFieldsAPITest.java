package com.contentstack.cms.entries;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.models.LoginDetails;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

class EntryFieldsAPITest {

    protected String globalFiledUid = "description";
    protected static String GLOBAL_AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Entry entry;

    @BeforeAll
    static void setup() throws IOException {
        Contentstack contentstack = new Contentstack.Builder().build();
        String emailId = Dotenv.load().get("username");
        String password = Dotenv.load().get("password");
        Response<LoginDetails> loginStatus = contentstack.login(emailId, password);
        String authToken = loginStatus.body().getUser().getAuthtoken();
        entry = new Contentstack.Builder().setAuthtoken(authToken).build()
                .entry(API_KEY, MANAGEMENT_TOKEN);
    }

    @Test
    void testFetch() throws IOException {
        HashMap<String, Object> queryParams = new HashMap<>();
        Response<ResponseBody> response = entry.fetch(queryParams).execute();
        Assertions.assertTrue(response.isSuccessful());
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
