package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class TokenUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Tokens tokens;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        tokens = stack.tokens();
    }

    @Test
    void allDeliveryTokens() {
        Map<String, Object> body = new HashMap<>();
        body.put("keyForSomething", "valueForSomething");
        Request request = tokens.getDeliveryTokens().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/extensions?query=%22type%22%3A%22field%22&include_branch=false",
                request.url().toString());
    }

    @Test
    void singleDeliveryToken() {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("include_count", false);
        queryParam.put("include_branch", false);
        Request request = tokens.getDeliveryToken(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/extensions/" + _uid + "?include_count=false&include_branch=false",
                request.url().toString());
    }

    @Test
    void createDeliveryToken() {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("include_count", false);
        queryParam.put("include_branch", false);

        JSONObject body = new JSONObject();
        JSONObject innerBody = new JSONObject();
        innerBody.put("tags", Arrays.asList("tag1", "tag2"));
        innerBody.put("data_type", "text");
        innerBody.put("title", "Old Extension");
        innerBody.put("src", "Enter either the source code");
        body.put("extension", innerBody);

        Request request = tokens.createDeliveryToken(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/extensions/" + _uid + "?include_count=false&include_branch=false",
                request.url().toString());
    }

    @Test
    void updateDeliveryToken() {
        Request request = tokens.updateDeliveryToken(_uid, new JSONObject()).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + _uid, request.url().toString());
    }

    @Test
    void deleteDeliveryToken() {
        Map<String, Object> theQuery = new HashMap<>();
        theQuery.put("include_branch", false);
        Request request = tokens.deleteDeliveryToken(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + _uid + "?include_branch=false",
                request.url().toString());
    }

    @Test
    void deleteDeliveryTokenForcefully() {
        Map<String, Object> theQuery = new HashMap<>();
        theQuery.put("include_branch", false);
        Request request = tokens.deleteDeliveryToken(_uid, true).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + _uid + "?include_branch=false",
                request.url().toString());
    }

    @Test
    void allManagementToken() {
        Request request = tokens.getManagementTokens().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions?include_branch=false",
                request.url().toString());
    }

    @Test
    void singleManagementToken() {
        Request request = tokens.getManagementToken(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions", request.url().toString());
    }

    @Test
    void createManagementToken() {
        Request request = tokens.createManagementToken(new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + _uid, request.url().toString());
    }

    @Test
    void updateManagementToken() {
        Request request = tokens.updateManagementToken(_uid, new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + _uid, request.url().toString());
    }

    @Test
    void deleteManagementToken() {
        Request request = tokens.deleteManagementToken(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + _uid, request.url().toString());
    }
}
