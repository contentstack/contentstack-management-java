package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
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
class LabelUnitTest {


    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("authToken");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    static Label label;
    protected static JSONObject body;


    static String theJsonString = "{\n" +
            "  \"label\": {\n" +
            "    \"name\": \"Test\",\n" +
            "    \"parent\": [\n" +
            "      \"label_uid\"\n" +
            "    ],\n" +
            "    \"content_types\": [\n" +
            "      \"content_type_uid\"\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        label = stack.label(_uid);

        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theJsonString);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }


    @Test
    void getAllLabels() {
        Request request = label.find().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels", request.url().toString());
    }

    @Test
    void fetchLabels() {
        label.addParam("include_count", false);
        label.addParam("include_branch", false);
        Request request = label.find().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels?include_count=false&include_branch=false", request.url().toString());
    }

    @Test
    void fetchWithBranch() {
        label.clearParams();
        label.addParam("include_count", false);
        label.addParam("include_branch", false);
        Request request = label.addBranch("main").find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels?include_count=false&include_branch=false", request.url().toString());
    }

    @Test
    void getLabel() {
        label.clearParams();
        Request request = label.fetch().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels/" + _uid, request.url().toString());
    }

    @Test
    void getLabelWithQuery() {
        label.addParam("include_branch", false);
        Request request = label.fetch().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels/" + _uid + "?include_branch=false", request.url().toString());
    }

    @Test
    void addLabelPost() {
        Request request = label.create(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels", request.url().toString());
    }


    @Test
    void labelUpdate() {
        Request request = label.update( body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels/" + _uid, request.url().toString());
    }

    @Test
    void labelDelete() {
        Request request = label.delete().request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels/" + _uid, request.url().toString());
    }


}
