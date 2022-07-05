package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.net.Proxy;
import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebhookUnitTest {

    private static final String AUTHTOKEN = Dotenv.load().get("authToken");
    private static final String API_KEY = Dotenv.load().get("api_key");
    private static final String _uid = Dotenv.load().get("workflow_uid");
    private static final String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    private static Webhook webhook;
    private static final String wehooksHost = "https://api.contentstack.io/v3/webhooks";

    protected static JSONObject body;


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        webhook = stack.webhook();

        try {
            JSONParser parser = new JSONParser();
            String requestBody = "{\n" +
                    "  \"webhook\":{\n" +
                    "    \"name\":\"Test\",\n" +
                    "    \"destinations\":[\n" +
                    "      {\n" +
                    "        \"target_url\":\"http://example.com\",\n" +
                    "        \"http_basic_auth\":\"basic\",\n" +
                    "        \"http_basic_password\":\"test\",\n" +
                    "        \"custom_header\":[\n" +
                    "          {\n" +
                    "            \"header_name\":\"Custom\",\n" +
                    "            \"value\":\"testing\"\n" +
                    "          }\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"channels\":[\n" +
                    "      \"assets.create\"\n" +
                    "    ],\n" +
                    "    \"branches\":[\n" +
                    "      \"main\"\n" +
                    "    ],\n" +
                    "    \"retry_policy\":\"manual\",\n" +
                    "    \"disabled\":false,\n" +
                    "    \"concise_payload\":true\n" +
                    "  }\n" +
                    "}";
            body = (JSONObject) parser.parse(requestBody);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }


    @Test
    @Order(1)
    void webhookHeaders() {
        webhook.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(3, webhook.headers.size());
    }

    @Test
    @Order(2)
    void webhookParamsWithSizeZero() {
        Assertions.assertEquals(0, webhook.params.size());
    }

    @Test
    @Order(3)
    void webhookParamsWithSizeMin() {
        webhook.addParam("paramFakeKey", "paramFakeValue");
        Assertions.assertEquals(1, webhook.params.size());
    }

    @Test
    @Order(4)
    void webhookParamsWithSizeMax() {
        webhook.removeParam("paramFakeKey");
        webhook.addParam("include_rules", true);
        webhook.addParam("include_permissions", true);
        Assertions.assertEquals(2, webhook.params.size());
    }

    @Test
    @Order(5)
    void fetchWebhook() {
        // supports no parameters
        Request request = webhook.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "?include_rules=true&include_permissions=true", request.url().toString());
    }

    @Test
    @Order(6)
    void fetchByWebhookUid() {
        webhook.removeParam("include_rules");
        webhook.removeParam("include_permissions");
        assert _uid != null;
        Request request = webhook.fetch(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid, request.url().toString());
    }

    @Test
    @Order(7)
    void createWebhook() {
        Request request = webhook.create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost, request.url().toString());
    }

    @Test
    void updateWebhook() {
        assert _uid != null;
        Request request = webhook.update(_uid, body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid, request.url().toString());
    }

    @Test
    void deleteWebhook() {
        assert _uid != null;
        Request request = webhook.delete(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid, request.url().toString());
    }


    @Test
    void exportWebhook() {
        assert _uid != null;
        Request request = webhook.export(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid + "/export", request.url().toString());
    }

    @Test
    void importWebhook() {
        assert _uid != null;
        Request request = webhook.importWebhook("webhookFile", "/Application/Library/ishaileshmishra/filename.json").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid + "/export", request.url().toString());
    }

    @Test
    void importExistingWebhook() {
        Request request = webhook.importExisting().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid + "/export", request.url().toString());
    }

    @Test
    void getExecutionWebhook() {
        Request request = webhook.getExecutions(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid + "/export", request.url().toString());
    }


    @Test
    void retryLogWebhook() {
        Request request = webhook.retry(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid + "/retry", request.url().toString());
    }

    @Test
    void getExecutionLogWebhook() {
        Request request = webhook.getExecutionLog(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(wehooksHost + "/" + _uid + "/logs", request.url().toString());
    }


}
