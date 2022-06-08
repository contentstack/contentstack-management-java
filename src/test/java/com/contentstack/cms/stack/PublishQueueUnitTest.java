package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PublishQueueUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String _uid = Dotenv.load().get("workflow_uid");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    protected static PublishQueue publishQueue;


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        publishQueue = stack.publishQueue();
    }

    @Test
    @Order(1)
    void publishQueueHeaders() {
        publishQueue.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(3, publishQueue.headers.size());
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
        Request request = publishQueue.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("publish-queue", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/publish-queue?publish_queue_uid=your_publish_queue_uid", request.url().toString());
    }

    @Test
    @Order(6)
    void publishQueueFetchByWorkflowId() {
        Request request = publishQueue.fetchActivity(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("publish-queue", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("publish_queue_uid=your_publish_queue_uid", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/publish-queue/blti9u09nunu3u7?publish_queue_uid=your_publish_queue_uid", request.url().toString());
    }

    @Test
    @Order(7)
    void publishQueueCancelScheduledAction() {
        Request request = publishQueue.cancelScheduledAction(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("publish-queue", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("publish_queue_uid=your_publish_queue_uid", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/publish-queue/blti9u09nunu3u7/unschedule?publish_queue_uid=your_publish_queue_uid", request.url().toString());
    }


}
