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
class RoleAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
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
            "      {\n" +
            "        \"module\":\"branch_alias\",\n" +
            "        \"branch_aliases\":[\n" +
            "          \"deploy\"\n" +
            "        ],\n" +
            "        \"acl\":{\n" +
            "          \"read\":true\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"module\":\"content_type\",\n" +
            "        \"content_types\":[\n" +
            "          \"$all\"\n" +
            "        ],\n" +
            "        \"acl\":{\n" +
            "          \"read\":true,\n" +
            "          \"sub_acl\":{\n" +
            "            \"read\":true\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"module\":\"asset\",\n" +
            "        \"assets\":[\n" +
            "          \"$all\"\n" +
            "        ],\n" +
            "        \"acl\":{\n" +
            "          \"read\":true,\n" +
            "          \"update\":true,\n" +
            "          \"publish\":true,\n" +
            "          \"delete\":true\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"module\":\"folder\",\n" +
            "        \"folders\":[\n" +
            "          \"$all\"\n" +
            "        ],\n" +
            "        \"acl\":{\n" +
            "          \"read\":true,\n" +
            "          \"sub_acl\":{\n" +
            "            \"read\":true\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"module\":\"environment\",\n" +
            "        \"environments\":[\n" +
            "          \"$all\"\n" +
            "        ],\n" +
            "        \"acl\":{\n" +
            "          \"read\":true\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"module\":\"locale\",\n" +
            "        \"locales\":[\n" +
            "          \"en-us\"\n" +
            "        ],\n" +
            "        \"acl\":{\n" +
            "          \"read\":true\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        roles = stack.roles();

        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theJsonString);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }


    @Test
    void rolesQueryParams() throws IOException {
        Response<ResponseBody> response = roles.getRoles().execute();
    }

    @Test
    void allRoles() throws IOException {
        roles.addParam("include_rules", true);
        roles.addParam("include_permissions", true);
        Response<ResponseBody> response = roles.getRoles().execute();
    }

    @Test
    void singleRole() throws IOException {
        Response<ResponseBody> response = roles.getRole(_uid).execute();
    }

    @Test
    void createRole() throws IOException {
        Response<ResponseBody> response = roles.createRole(body).execute();
    }

    @Test
    void updateRole() throws IOException {
        Response<ResponseBody> response = roles.updateRole(_uid, body).execute();
    }

    @Test
    void deleteRole() throws IOException {
        Response<ResponseBody> response = roles.deleteRole(_uid).execute();
    }

}
