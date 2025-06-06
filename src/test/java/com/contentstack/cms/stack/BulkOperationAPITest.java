package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BulkOperationAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static BulkOperation bulkOperation;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        bulkOperation = stack.bulkOperation();
        bulkOperation.addHeaders(headers);
        bulkOperation.addParams(headers);
        bulkOperation.addHeader("api_version", "3.2");
        bulkOperation.addParam("skip_workflow_stage_check", true);
        bulkOperation.addParam("approvals", true);
    }

    @Test
    @Order(1)
    void publishQueueHeaders() {
        bulkOperation = bulkOperation.addParam("test", "testValue");
        bulkOperation.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(4, bulkOperation.headers.size());
    }

    @Test
    @Order(2)
    void publishQueueParamsWithSizeZero() {
        Assertions.assertEquals(5, bulkOperation.params.size());
    }

    @Test
    @Order(3)
    void publishQueueParamsWithSizeMin() {
        Assertions.assertEquals(5, bulkOperation.params.size());
    }

    @Test
    @Order(4)
    void publishQueueParamsWithSizeMax() {
        Assertions.assertEquals(5, bulkOperation.params.size());
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
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/bulk/publish?authorization=managementToken99999999&skip_workflow_stage_check=true&test=testValue&api_key=apiKey99999999&approvals=true",
                request.url().toString());
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
        Assertions.assertEquals("authorization=managementToken99999999&skip_workflow_stage_check=true&test=testValue&api_key=apiKey99999999&approvals=true",
                request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/bulk/unpublish?authorization=managementToken99999999&skip_workflow_stage_check=true&test=testValue&api_key=apiKey99999999&approvals=true",
                request.url().toString());
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
        Assertions.assertEquals("authorization=managementToken99999999&skip_workflow_stage_check=true&test=testValue&api_key=apiKey99999999&approvals=true",
                request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/bulk/delete?authorization=managementToken99999999&skip_workflow_stage_check=true&test=testValue&api_key=apiKey99999999&approvals=true",
                request.url().toString());
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
        Assertions.assertEquals("authorization=managementToken99999999&skip_workflow_stage_check=true&test=testValue&api_key=apiKey99999999&approvals=true",
                request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/bulk/workflow?authorization=managementToken99999999&skip_workflow_stage_check=true&test=testValue&api_key=apiKey99999999&approvals=true",
                request.url().toString());
    }

    @Test
    @Order(8)
    void testAddBulkItems() {
        Request request = bulkOperation.addReleaseItems(new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
    }

    @Test
    @Order(9)
    void testUpdateBulkItems() {
        Request request = bulkOperation.updateReleaseItems(new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
    }

    @Test
    @Order(10)
    void testGetJobStatus() {
        Request request = bulkOperation.jobStatus("jobId").request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("jobs", request.url().pathSegments().get(2));
    }

}
