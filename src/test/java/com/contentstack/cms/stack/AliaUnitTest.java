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
class AliaUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("authToken");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    protected static Alias alias;
    protected static JSONObject body;

    // Create a JSONObject, JSONObject could be created in multiple ways.
    // We choose JSONParser that converts string to JSONObject
    static String theJsonString = "{\n" +
            "    \"branch\": {\n" +
            "        \"uid\": \"release\",\n" +
            "        \"source\": \"main\"\n" +
            "    }\n" +
            "}";


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        alias = stack.alias(_uid);

        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theJsonString);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }


    @Test
    void fetchBranch() {
        Request request = alias.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branch_aliases", request.url().pathSegments().get(2));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/branch_aliases",
                request.url().toString());
    }

    @Test
    void fetchBranchWithQueryParameter() {
        alias.addParam("limit", 2);
        alias.addParam("skip", 2);
        alias.addParam("include_count", false);
        Request request = alias.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branch_aliases", request.url().pathSegments().get(2));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/branch_aliases?limit=2&skip=2&include_count=false",
                request.url().toString());
    }

    @Test
    void allRoles() {
        alias.clearParams();
        alias.addParam("include_rules", true);
        alias.addParam("include_permissions", true);
        Request request = alias.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branch_aliases", request.url().pathSegments().get(2));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/branch_aliases?include_rules=true&include_permissions=true",
                request.url().toString());
    }

    @Test
    void singleRole() {
        Request request = alias.fetch().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branch_aliases", request.url().pathSegments().get(2));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/branch_aliases/" + _uid,
                request.url().toString());
    }



    @Test
    void updateRole() {
        Request request = alias.update(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branch_aliases", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/branch_aliases",
                request.url().toString());
    }


    @Test
    void deleteBranch() {
        Request request = alias.delete().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branch_aliases", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/branch_aliases/" + _uid,
                request.url().toString());
    }

}
