package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BulkOperationTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    protected static BulkOperation bulkOperation;


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        bulkOperation = stack.bulkOperation();
        bulkOperation.addHeaders(headers);
        bulkOperation.addHeader("api_version", "3.2");
        bulkOperation.addParam("skip_workflow_stage_check", true);
        bulkOperation.addParam("approvals", true);
    }

    @Test
    @Order(1)
    void publishQueueHeaders() {
        bulkOperation.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(4, bulkOperation.headers.size());
    }

    @Test
    @Order(2)
    void publishQueueParamsWithSizeZero() {
        Assertions.assertEquals(2, bulkOperation.params.size());
    }

    @Test
    @Order(3)
    void publishQueueParamsWithSizeMin() {
        Assertions.assertEquals(2, bulkOperation.params.size());
    }

    @Test
    @Order(4)
    void publishQueueParamsWithSizeMax() {
        Assertions.assertEquals(2, bulkOperation.params.size());
    }

    @Test
    @Order(5)
    void testPublish() {
        Request request = bulkOperation.publish(new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/bulk/publish?skip_workflow_stage_check=true&approvals=true", request.url().toString());
    }

    @Test
    @Order(6)
    void testUnpublish() {
        Request request = bulkOperation.unpublish(new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("skip_workflow_stage_check=true&approvals=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/bulk/unpublish?skip_workflow_stage_check=true&approvals=true", request.url().toString());
    }

    @Test
    @Order(7)
    void testDelete() {
        Request request = bulkOperation.delete(new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("skip_workflow_stage_check=true&approvals=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/bulk/delete?skip_workflow_stage_check=true&approvals=true", request.url().toString());
    }

    @Test
    @Order(7)
    void testUpdateWorkflow() {

        Request request = bulkOperation.updateWorkflow(new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("skip_workflow_stage_check=true&approvals=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/bulk/workflow?skip_workflow_stage_check=true&approvals=true", request.url().toString());
    }


}
