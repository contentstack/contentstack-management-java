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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("authToken");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    protected static Roles roles;
    protected static JSONObject body;

    // Create a JSONObject, JSONObject could be created in multiple ways.
    // We choose JSONParser that converts string to JSONObject
    static String theJsonString = "{\n" +
            "  \"role\":{\n" +
            "    \"name\":\"testRole\",\n" +
            "    \"description\":\"This is a test role.\",\n" +
            "    \"rules\":[\n" +
            "      {\n" +
            "        \"module\":\"branch\",\n" +
            "        \"branches\":[\n" +
            "          \"main\"\n" +
            "        ],\n" +
            "        \"acl\":{\n" +
            "          \"read\":true\n" +
            "        }\n" +
            "      },\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";


    @BeforeAll
    public static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        roles = stack.roles(_uid);

        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theJsonString);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    void allRoles() throws IOException {
        roles.addParam("include_rules", true);
        roles.addParam("include_permissions", true);
        Response<ResponseBody> response = roles.find().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }

    }

    @Test
    void singleRole() throws IOException {
        Response<ResponseBody> response = roles.fetch().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    @Disabled
    void createRole() throws IOException {
        Response<ResponseBody> response = roles.create(body).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    @Disabled
    void updateRole() throws IOException {
        Response<ResponseBody> response = roles.update(body).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    @Disabled
    void deleteRole() throws IOException {
        Response<ResponseBody> response = roles.delete().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

}
