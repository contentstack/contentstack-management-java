package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Tag("unit") class LabelUnitTest {


    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Label label;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        label = stack.label();
    }


    @Test
    void getAllLabels() {
        Request request = label.get().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels", request.url().toString());
    }

    @Test
    void getAllLabelsWithBody() {
        Map<String, Object> jsonJson = new HashMap<>();
        jsonJson.put("include_count", false);
        jsonJson.put("include_branch", false);
        Request request = label.get(jsonJson).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("labels", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/labels?include_count=false&include_branch=false", request.url().toString());
    }

    @Test
    void getAllLabelsWithBodyWithBranch() {
        Map<String, Object> jsonJson = new HashMap<>();
        jsonJson.put("include_count", false);
        jsonJson.put("include_branch", false);
        Request request = label.addBranch("main").get(jsonJson).request();
        Assertions.assertEquals(3, request.headers().names().size());
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
        Request request = label.get(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
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
        Map<String, Object> theQuery = new HashMap<>();
        theQuery.put("include_branch", false);
        Request request = label.get(_uid, theQuery).request();
        Assertions.assertEquals(3, request.headers().names().size());
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
        JSONObject theQuery = new JSONObject();

        ArrayList list = new ArrayList();
        list.add("label_uid");
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", list);
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);

        Request request = label.add(theQuery).request();
        Assertions.assertEquals(3, request.headers().names().size());
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

        JSONObject theQuery = new JSONObject();
        ArrayList list = new ArrayList();
        list.add("label_uid");
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", list);
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);

        Request request = label.update(_uid, theQuery).request();
        Assertions.assertEquals(3, request.headers().names().size());
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
        Request request = label.delete(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
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
