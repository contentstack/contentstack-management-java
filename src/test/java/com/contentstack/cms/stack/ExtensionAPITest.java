package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExtensionAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.AUTHTOKEN;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    static Extensions extension;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        extension = stack.extensions(_uid);
    }

    @Test
    void extensionGetAll() {
        extension.addParam("query", "\"type\":\"field\"");
        extension.addParam("include_branch", true);
        extension.addHeader("api_key", API_KEY);
        extension.addHeader("authtoken", AUTHTOKEN);
        Request request = extension.find().request();

        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("query=%22type%22:%22field%22&include_branch=true", request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/extensions?query=%22type%22:%22field%22&include_branch=true",
                request.url().toString());
    }

    @Test
    void getSingleWithUid() {
        extension.addParam("include_branch", true);
        extension.addHeader("api_key", API_KEY);
        extension.addHeader("authtoken", AUTHTOKEN);
        Request request = extension.fetch().request();

        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + AUTHTOKEN + "?include_branch=true",
                request.url().toString());
    }

    @Test
    void extensionUpdate() {
        extension.addParam("include_branch", true);
        extension.addHeader("api_key", API_KEY);
        extension.addHeader("authtoken", AUTHTOKEN);

        Request request = extension.update(new JSONObject()).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("query=%22type%22:%22field%22&include_branch=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + AUTHTOKEN
                + "?query=%22type%22:%22field%22&include_branch=true", request.url().toString());
    }

    @Test
    void extensionDelete() {
        extension.addParam("include_branch", true);
        extension.addHeader("api_key", API_KEY);
        extension.addHeader("authtoken", AUTHTOKEN);
        Request request = extension.delete().request();

        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + AUTHTOKEN, request.url().toString());
    }

    @Test
    void extensionGetSingle() {
        extension.addParam("include_branch", true);
        extension.addHeader("api_key", API_KEY);
        extension.addHeader("authtoken", AUTHTOKEN);
        Request request = extension.fetch().request();

        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_branch=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + AUTHTOKEN + "?include_branch=true",
                request.url().toString());
    }

    @Test
    void testUploadCustomField() {

        extension.addHeader("api_key", API_KEY);
        extension.addHeader("authtoken", AUTHTOKEN);
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
        Request request = extension.uploadCustomField(params).request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions?include_branch=true",
                request.url().toString());
    }

    @Test
    void extensionDeleteAgain() {

        extension.addParam("include_branch", true);
        extension.addHeader("api_key", API_KEY);
        extension.addHeader("authtoken", AUTHTOKEN);
        Request request = extension.delete().request();

        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("extensions", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/extensions/" + AUTHTOKEN, request.url().toString());
    }

}
