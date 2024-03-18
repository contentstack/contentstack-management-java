package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@Tag("unit")
class TokenUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.AUTHTOKEN;
    protected static String MANAGEMENT_TOKEN = TestClient.AUTHTOKEN;
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
        Request request = tokens.deliveryTokens().find().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/delivery_tokens",
                request.url().toString());
    }

    @Test
    void singleDeliveryToken() {
        Request request = tokens.deliveryTokens(_uid).fetch().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/delivery_tokens/" + _uid,
                request.url().toString());
    }

    @Test
    void createDeliveryToken() {
        Request request = tokens.deliveryTokens().create(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/delivery_tokens",
                request.url().toString());
    }

    @Test
    void updateDeliveryToken() {
        Request request = tokens.deliveryTokens(_uid).update(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/delivery_tokens/" + _uid, request.url().toString());
    }

    @Test
    void deleteDeliveryToken() {
        Request request = tokens.deliveryTokens(_uid).delete().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("force=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/delivery_tokens/" + _uid + "?force=false",
                request.url().toString());
    }

    @Test
    void deleteDeliveryTokenForcefully() {
        Request request = tokens.deliveryTokens(_uid).delete(true).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("delivery_tokens", request.url().pathSegments().get(1));
        Assertions.assertEquals("force=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/delivery_tokens/" + _uid + "?force=true",
                request.url().toString());
    }

    @Test
    void allManagementToken() {
        Request request = tokens.managementToken().find().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/management_tokens",
                request.url().toString());
    }

    @Test
    void singleManagementToken() {
        Request request = tokens.managementToken(_uid).fetch().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/management_tokens/" + _uid, request.url().toString());
    }

    @Test
    void createManagementToken() {
        Request request = tokens.managementToken().create(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/management_tokens", request.url().toString());
    }

    @Test
    void updateManagementToken() {
        Request request = tokens.managementToken(_uid).update(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/management_tokens/" + _uid, request.url().toString());
    }

    @Test
    void deleteManagementToken() {
        Request request = tokens.managementToken(_uid).delete().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("management_tokens", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/management_tokens/" + _uid, request.url().toString());
    }

    @Test
    void testHeaderAndParams() {
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack("apiKey", "managementToken");
        DeliveryToken deliveryToken = stack.tokens().deliveryTokens("test_id");
        deliveryToken.clearParams();
        deliveryToken.addHeader("headerKey", "headerValue");
        deliveryToken.addParam("key", "Value");
        deliveryToken.removeParam("key");
        Request request = tokens.deliveryTokens().find().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/delivery_tokens",
                request.url().toString());
    }
}
