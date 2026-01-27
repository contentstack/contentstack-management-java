package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PublishQueueUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    ;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.USER_ID;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static PublishQueue publishQueue;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        publishQueue = stack.publishQueue(_uid);
    }

    @Test
    @Order(1)
    void publishQueueHeaders() {
        publishQueue.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(1, publishQueue.headers.size());
    }

    @Test
    @Order(2)
    void publishQueueParamsWithSizeZero() {
        Assertions.assertEquals(0, publishQueue.params.size());
    }

    @Test
    @Order(3)
    void publishQueueParamsWithSizeMin() {
        Assertions.assertEquals(0, publishQueue.params.size());
    }

    @Test
    @Order(4)
    void publishQueueParamsWithSizeMax() {
        publishQueue.addParam("publish_queue_uid", "your_publish_queue_uid");
        Assertions.assertEquals(1, publishQueue.params.size());
    }

    @Test
    @Order(5)
    void publishQueueFetchAll() {
        Request request = publishQueue.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("publish-queue", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    @Order(6)
    void publishQueueFetchByWorkflowId() {
        Request request = publishQueue.fetchActivity().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("publish-queue", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("publish_queue_uid=your_publish_queue_uid", request.url().encodedQuery());    }

    @Test
    @Order(7)
    void publishQueueCancelScheduledAction() {
        Request request = publishQueue.cancelScheduledAction().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("publish-queue", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("publish_queue_uid=your_publish_queue_uid", request.url().encodedQuery());    }

    @Test
    void testPublishQueueException() {
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "token");
        PublishQueue thePublishQueue = stack.publishQueue();
        thePublishQueue.clearParams();
        thePublishQueue.addParam("key", "value");
        thePublishQueue.removeParam("key");
        thePublishQueue.addHeader("key", "value");
        Assertions.assertThrows(IllegalAccessError.class, thePublishQueue::fetchActivity);
    }

}
