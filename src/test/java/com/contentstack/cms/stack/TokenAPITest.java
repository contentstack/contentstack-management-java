package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

@Tag("api")
class TokenAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    protected static Tokens tokens;
    protected static JSONObject body;

    // Create a JSONObject, JSONObject could be created in multiple ways.
    // We choose JSONParser that converts string to JSONObject
    static String theJsonString = "{\n" +
            "    \"token\":{\n" +
            "        \"name\":\"Updated Test Token\",\n" +
            "        \"description\":\"This is an updated management token.\",\n" +
            "        \"scope\":[\n" +
            "            {\n" +
            "                \"module\":\"content_type\",\n" +
            "                \"acl\":{\n" +
            "                    \"read\":true,\n" +
            "                    \"write\":true\n" +
            "                }\n" +
            "            },\n" +
            "            {\n" +
            "                \"module\":\"entry\",\n" +
            "                \"acl\":{\n" +
            "                    \"read\":true,\n" +
            "                    \"write\":true\n" +
            "                }\n" +
            "            },\n" +
            "            {\n" +
            "                \"module\":\"branch\",\n" +
            "                \"branches\":[\n" +
            "                    \"main\",\n" +
            "                    \"development\"\n" +
            "                ],\n" +
            "                \"acl\":{\n" +
            "                    \"read\":true\n" +
            "                }\n" +
            "            },\n" +
            "            {\n" +
            "                \"module\":\"branch_alias\",\n" +
            "                \"branch_aliases\":[\n" +
            "                    \"deploy\"\n" +
            "                ],\n" +
            "                \"acl\":{\n" +
            "                    \"read\":true\n" +
            "                }\n" +
            "            }\n" +
            "        ],\n" +
            "        \"expires_on\":\"2020-12-31\",\n" +
            "        \"is_email_notification_enabled\":true\n" +
            "    }\n" +
            "}";


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        tokens = stack.tokens();

        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theJsonString);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }

    @Test
    void allDeliveryTokens() throws IOException {
        Response<ResponseBody> response = tokens.deliveryTokens().find().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void singleDeliveryToken() throws IOException {
        Response<ResponseBody> response = tokens.deliveryTokens().fetch(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void createDeliveryToken() throws IOException {
        Response<ResponseBody> response = tokens.deliveryTokens().create(body).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void updateDeliveryToken() throws IOException {
        Response<ResponseBody> response = tokens.deliveryTokens().update(_uid, body).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void deleteDeliveryToken() throws IOException {
        Response<ResponseBody> response = tokens.deliveryTokens().delete(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void deleteDeliveryTokenForcefully() throws IOException {
        Response<ResponseBody> response = tokens.deliveryTokens().delete(_uid, true).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void allManagementToken() throws IOException {
        Response<ResponseBody> response = tokens.managementToken().find().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void singleManagementToken() throws IOException {
        Response<ResponseBody> response = tokens.managementToken().fetch(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void createManagementToken() throws IOException {
        Response<ResponseBody> response = tokens.managementToken().create(body).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void updateManagementToken() throws IOException {
        Response<ResponseBody> response = tokens.managementToken().update(_uid, body).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void deleteManagementToken() throws IOException {
        Response<ResponseBody> response = tokens.managementToken().delete(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }
}
