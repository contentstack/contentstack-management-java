package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
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

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Extensions extension;
    protected static JSONObject body;

    static String requestBody = "{\n" +
            "\t\"extension\": {\n" +
            "\t\t\"tags\": [\n" +
            "\t\t\t\"tag1\",\n" +
            "\t\t\t\"tag2\"\n" +
            "\t\t],\n" +
            "\t\t\"data_type\": \"text\",\n" +
            "\t\t\"title\": \"Old Extension\",\n" +
            "\t\t\"src\": \"Enter either the source code (use 'srcdoc') or the external hosting link of the extension depending on the hosting method you selected.\",\n" +
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
        extension = stack.extensions();

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
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/extensions?query=%22type%22:%22field%22&include_branch=false",
                request.url().toString());
    }

    @Test
    void fetcgSingleExt() {
        extension.clearParams();
        extension.addParam("include_count", false);
        extension.addParam("include_branch", false);
        Request request = extension.fetch(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
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
        extension.clearParams();
        extension.addParam("include_count", false);
        extension.addParam("include_branch", false);
        Request request = extension.update(_uid, body).request();
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
        Assertions.assertEquals(3, request.headers().names().size());
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
        extension.addParam("include_count", false);
        extension.addParam("include_branch", false);
        Request request = extension.fetch(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=false&include_branch=false", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + _uid + "?include_count=false&include_branch=false",
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
        extension.clearParams();
        params.put("DYNAMIC_PARAM_NAME", someDataBody);
        extension.addParam("include_branch", false);
        Request request = extension.uploadCustomField(params).request();
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

//    @Test
//    void updateTheExtension() {
//        Request request = extension.uploadCustomField(body).request();
//        Assertions.assertEquals(2, request.headers().names().size());
//        Assertions.assertEquals("POST", request.method());
//        Assertions.assertNotNull(request.body());
//        Assertions.assertTrue(request.url().isHttps());
//        Assertions.assertEquals("api.contentstack.io", request.url().host());
//        Assertions.assertEquals(2, request.url().pathSegments().size());
//        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
//        Assertions.assertNull(request.url().encodedQuery());
//        Assertions.assertEquals("https://api.contentstack.io/v3/extensions", request.url().toString());
//    }

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
