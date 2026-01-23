package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Tag("unit")
class ExtensionUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.USER_ID;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static JSONObject body;
    static Extensions extension;
    static String requestBody = "{\n" +
            "\t\"extension\": {\n" +
            "\t\t\"tags\": [\n" +
            "\t\t\t\"tag1\",\n" +
            "\t\t\t\"tag2\"\n" +
            "\t\t],\n" +
            "\t\t\"data_type\": \"text\",\n" +
            "\t\t\"title\": \"Old Extension\",\n" +
            "\t\t\"src\": \"Enter either the source code (use 'srcdoc') or the external hosting link of the extension depending on the hosting method you selected.\",\n"
            +
            "\t\t\"multiple\": false,\n" +
            "\t\t\"config\": \"{}\",\n" +
            "\t\t\"type\": \"field\"\n" +
            "\t}\n" +
            "}";

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        extension = stack.extensions(_uid);

        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(requestBody);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Test
    void fetchExtension() {
        extension.clearParams();
        JSONObject params = new JSONObject();
        params.put("type", "field");
        extension.addParam("query", "\"type\":\"field\"");
        extension.addParam("include_branch", false);
        Request request = extension.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void fetcgSingleExt() {
        extension.clearParams();
        extension.addParam("include_count", false);
        extension.addParam("include_branch", false);
        Request request = extension.fetch().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());    }

    @Test
    void extensionUpdate() {
        extension.clearParams();
        extension.addParam("include_count", false);
        extension.addParam("include_branch", false);
        Request request = extension.update(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());    }

    @Test
    void extensionDelete() {
        Request request = extension.delete().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void extensionGetSingle() {
        extension.addParam("include_count", false);
        extension.addParam("include_branch", false);
        Request request = extension.fetch().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());    }

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
        extension.clearParams();
        params.put("DYNAMIC_PARAM_NAME", someDataBody);
        extension.addParam("include_branch", false);
        Request request = extension.uploadCustomField(params).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertNotNull(request.body());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_branch=false", request.url().encodedQuery());    }

    // @Test
    // void updateTheExtension() {
    // Request request = extension.uploadCustomField(body).request();
    // Assertions.assertEquals(2, request.headers().names().size());
    // Assertions.assertEquals("POST", request.method());
    // Assertions.assertNotNull(request.body());
    // Assertions.assertTrue(request.url().isHttps());
    //    // Assertions.assertEquals(2, request.url().pathSegments().size());
    // Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
    // Assertions.assertNull(request.url().encodedQuery());
    //    // }

    @Test
    void extensionDeleteAgain() {
        Request request = extension.delete().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertNull(request.body());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }


    @Test
    void testExtensionException() {
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "token");
        Extensions theExtensions = stack.extensions();
        theExtensions.addParam("key", "value");
        theExtensions.removeParam("key");
        theExtensions.addHeader("key", "value");
        JSONObject obj = new JSONObject();
        obj.put("key", "value");
        Map<String, RequestBody> map = new HashMap<>();
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) {

            }
        };
        map.put("someKey", requestBody);
        theExtensions.uploadCustomField(map);
        theExtensions.uploadCustomFieldUsingJSONObject(body);
        Assertions.assertThrows(IllegalAccessError.class, theExtensions::fetch);
    }

    @Test
    void testExtensionUpdateException() {
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "token");
        Extensions theExtensions = stack.extensions("customFieldUid");
        Request request = theExtensions.update(body).request();
        Assertions.assertNotNull(request.body());
    }
}
