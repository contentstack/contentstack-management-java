package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;

import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
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
    @Order(1)
    void allRoleHeaders() {
        roles.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(3, roles.headers.size());
    }

    @Test
    @Order(2)
    void allRoleParamsWithSizeZero() {
        Assertions.assertEquals(0, roles.params.size());
    }

    @Test
    @Order(3)
    void allRoleParamsWithSizeMin() {
        roles.addParam("paramFakeKey", "paramFakeValue");
        Assertions.assertEquals(1, roles.params.size());
    }

    @Test
    @Order(4)
    void allRoleParamsWithSizeMax() {
        roles.removeParam("paramFakeKey");
        roles.addParam("include_rules", true);
        roles.addParam("include_permissions", true);
        Assertions.assertEquals(2, roles.params.size());
    }

    @Test
    @Order(5)
    void rolesQueryParams() {
        Request request = roles.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/roles?include_rules=true&include_permissions=true",
                request.url().toString());
    }

    @Test
    @Order(6)
    void allRoles() {
        roles.addParam("include_rules", true);
        roles.addParam("include_permissions", true);
        Request request = roles.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/roles?include_rules=true&include_permissions=true",
                request.url().toString());
    }

    @Test
    @Order(7)
    void singleRole() {
        Request request = roles.single(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/roles/" + _uid,
                request.url().toString());
    }

    @Test
    @Order(8)
    void createRole() {
        Request request = roles.create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/roles",
                request.url().toString());
    }

    @Test
    @Order(9)
    void updateRole() {
        Request request = roles.update(_uid, body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/roles/" + _uid, request.url().toString());
    }

    @Test
    @Order(10)
    void deleteRole() {
        Request request = roles.delete(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/roles/" + _uid ,
                request.url().toString());
    }

}
