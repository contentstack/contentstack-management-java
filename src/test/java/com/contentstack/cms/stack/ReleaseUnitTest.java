package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReleaseUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.USER_ID;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Release release;
    protected static JSONObject body;

    // Create a JSONObject, JSONObject could be created in multiple ways.
    // We choose JSONParser that converts string to JSONObject
    static String theJsonString = "{\n" + "\t\"release\": {\n" + "\t\t\"name\": \"Release Name\",\n" + "\t\t\"description\": \"2018-12-12\",\n" + "\t\t\"locked\": false,\n" + "\t\t\"archived\": false\n" + "\t}\n" + "}";

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        release = stack.releases(_uid);

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
        Assertions.assertEquals("https://api.contentstack.io/v3/releases?include_rules=true&include_permissions=true", request.url().toString());
    }

    @Test
    @Order(6)
    void fetchSingleUrl() {
        release.addParam("include_rules", true);
        release.addParam("include_permissions", true);
        Request request = release.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid, request.url().toString());
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
        Assertions.assertEquals("https://api.contentstack.io/v3/releases", request.url().toString());
    }

    @Test
    @Order(8)
    void updateRelease() {
        Request request = release.update(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid, request.url().toString());
    }

    @Test
    @Order(9)
    void deleteRelease() {
        Request request = release.delete().request();
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
        Request request = release.item().find().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/items", request.url().toString());
    }

    @Test
    @Order(10)
    void createItemRelease() {
        Request request = release.item().create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/item", request.url().toString());
    }

    @Test
    @Order(10)
    void createMultipleReleaseItems() {
        Request request = release.item().createMultiple(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/items", request.url().toString());
    }

    @Test
    @Order(10)
    void updateReleaseItems() {
        Request request = release.item().update(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/update_items", request.url().toString());
    }

    @Test
    @Order(10)
    void deleteReleaseItems() {
        Request request = release.item().delete().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/releases/" + _uid + "/items", request.url().toString());
    }

    @Test
    void testReleaseException() {
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "token");
        Release theRelease = stack.releases("uid");
        theRelease.clearParams();
        theRelease.addParam("key", "value");
        theRelease.removeParam("key");
        theRelease.addHeader("key", "value");
        theRelease.deploy(new JSONObject());
        theRelease.clone(new JSONObject());
        //Assertions.assertThrows(IllegalAccessError.class, theRelease::fetch);
        Request request = theRelease.find().request();
        Assertions.assertNull(request.body());
    }


    @Test
    void testReleasesException() {
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "token");
        Release release = stack.releases();
        Assertions.assertThrows(IllegalAccessError.class, release::item);
        Release newRelease = stack.releases("uid");
        newRelease.item().clearParams();
        newRelease.item().addParam("key", "value");
        newRelease.item().removeParam("key");
        newRelease.item().addHeader("key", "value");
    }

}
