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
class ReleaseUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    protected static Release release;
    protected static JSONObject body;

    // Create a JSONObject, JSONObject could be created in multiple ways.
    // We choose JSONParser that converts string to JSONObject
    static String theJsonString = "{\n" +
            "\t\"release\": {\n" +
            "\t\t\"name\": \"Release Name\",\n" +
            "\t\t\"description\": \"2018-12-12\",\n" +
            "\t\t\"locked\": false,\n" +
            "\t\t\"archived\": false\n" +
            "\t}\n" +
            "}";


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        release = stack.releases();

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
    void allReleaseHeaders() {
        release.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(3, release.headers.size());
    }

    @Test
    @Order(2)
    void allReleaseParamsWithSizeZero() {
        Assertions.assertEquals(0, release.params.size());
    }

    @Test
    @Order(3)
    void allReleaseParamsWithSizeMin() {
        release.addParam("paramFakeKey", "paramFakeValue");
        Assertions.assertEquals(1, release.params.size());
    }

    @Test
    @Order(4)
    void allReleaseParamsWithSizeMax() {
        release.removeParam("paramFakeKey");
        release.addParam("include_rules", true);
        release.addParam("include_permissions", true);
        Assertions.assertEquals(2, release.params.size());
    }

    @Test
    @Order(5)
    void releaseQueryParams() {
        Request request = release.find().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/releases?include_rules=true&include_permissions=true",
                request.url().toString());
    }

    @Test
    @Order(6)
    void fetchSingleUrl() {
        release.addParam("include_rules", true);
        release.addParam("include_permissions", true);
        Request request = release.fetch(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/releases/" + _uid,
                request.url().toString());
    }

    @Test
    @Order(7)
    void createRelease() {
        Request request = release.create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/releases",
                request.url().toString());
    }

    @Test
    @Order(8)
    void updateRelease() {
        Request request = release.update(_uid, body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/releases/" + _uid,
                request.url().toString());
    }


    @Test
    @Order(9)
    void deleteRelease() {
        Request request = release.delete(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid, request.url().toString());
    }

    /// ==> Testcases for the release items <==////
    @Test
    @Order(10)
    void getReleaseItems() {
        Request request = release.item(_uid).find().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/items",
                request.url().toString());
    }

    @Test
    @Order(10)
    void createItemRelease() {
        Request request = release.item(_uid).create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/item",
                request.url().toString());
    }

    @Test
    @Order(10)
    void createMultipleReleaseItems() {
        Request request = release.item(_uid).createMultiple(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/items",
                request.url().toString());
    }

    @Test
    @Order(10)
    void updateReleaseItems() {
        Request request = release.item(_uid).update(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/update_items",
                request.url().toString());
    }

    @Test
    @Order(10)
    void deleteReleaseItems() {
        Request request = release.item(_uid).delete().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/items",
                request.url().toString());
    }

}
