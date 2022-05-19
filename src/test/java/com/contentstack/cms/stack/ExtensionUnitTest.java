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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Tag("unit")
class ExtensionUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Extensions extension;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        extension = stack.extensions();
    }

    @Test
    void extensionGetAll() {
        Map<String, Object> body = new HashMap<>();
        body.put("keyForSomething", "valueForSomething");
        Request request = extension.getAll("\"type\":\"field\"", false).request();
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
    void getSingleWithUid() {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("include_count", false);
        queryParam.put("include_branch", false);
        Request request = extension.get(_uid, queryParam).request();
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
    void extensionUpdate() {
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

        Request request = extension.update(_uid, queryParam, body).request();
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
    void extensionDelete() {
        Request request = extension.delete(_uid).request();
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
    void extensionGetSingle() {
        Map<String, Object> theQuery = new HashMap<>();
        theQuery.put("include_branch", false);
        Request request = extension.get(_uid, theQuery).request();
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
    void testUploadCustomField() {
        Map<String, RequestBody> params = new HashMap<>();
        RequestBody someDataBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) {
            }
        };
        params.put("DYNAMIC_PARAM_NAME", someDataBody);
        Map<String, Object> param = new HashMap<>();
        param.put("include_branch", false);
        Request request = extension.uploadCustomField(params, param).request();
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
    void updateTheExtension() {
        JSONObject theQuery = new JSONObject();
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", Arrays.asList("label_uid0", "label_uid1"));
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);
        Map<String, Object> params = new HashMap<>();
        params.put("include_something", "true");
        Request request = extension.uploadCustomField(params, theQuery).request();
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
    void extensionDeleteAgain() {
        Request request = extension.delete(_uid).request();
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
