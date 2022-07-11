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
class TokenUnitTest {

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
    void allDeliveryTokens() {
        Request request = tokens.deliveryTokens().fetch().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/delivery_tokens",
                request.url().toString());
    }

    @Test
    void singleDeliveryToken() {
        Request request = tokens.deliveryTokens().single(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/delivery_tokens/" + _uid,
                request.url().toString());
    }

    @Test
    void createDeliveryToken() {
        Request request = tokens.deliveryTokens().create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/delivery_tokens",
                request.url().toString());
    }

    @Test
    void updateDeliveryToken() {
        Request request = tokens.deliveryTokens().update(_uid, body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/delivery_tokens/" + _uid, request.url().toString());
    }

    @Test
    void deleteDeliveryToken() {
        Request request = tokens.deliveryTokens().delete(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("force=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/delivery_tokens/" + _uid + "?force=false",
                request.url().toString());
    }

    @Test
    void deleteDeliveryTokenForcefully() {
        Request request = tokens.deliveryTokens().delete(_uid, true).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("force=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/delivery_tokens/" + _uid + "?force=true",
                request.url().toString());
    }

    @Test
    void allManagementToken() {
        Request request = tokens.managementToken().fetch().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/management_tokens",
                request.url().toString());
    }

    @Test
    void singleManagementToken() {
        Request request = tokens.managementToken().single(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/management_tokens/" + _uid, request.url().toString());
    }

    @Test
    void createManagementToken() {
        Request request = tokens.managementToken().create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/management_tokens", request.url().toString());
    }

    @Test
    void updateManagementToken() {
        Request request = tokens.managementToken().update(_uid, body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/management_tokens/" + _uid, request.url().toString());
    }

    @Test
    void deleteManagementToken() {
        Request request = tokens.managementToken().delete(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/management_tokens/" + _uid, request.url().toString());
    }
}
