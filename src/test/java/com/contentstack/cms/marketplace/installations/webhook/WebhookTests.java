package com.contentstack.cms.marketplace.installations.webhook;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class WebhookTests {

    private static Webhook webhook;
    static String AUTHTOKEN = TestClient.AUTHTOKEN;
    static String ORG_ID = TestClient.ORGANIZATION_UID;
    static String WEBHOOK_ID = TestClient.ORGANIZATION_UID;


    @BeforeAll
    static void setUp() {
        Contentstack client = TestClient.getClient();
        webhook = client.organization(ORG_ID).marketplace().installation("installation_id").webhook(WEBHOOK_ID);
    }

    @Test
    void testFindExecutionLogs() {
        webhook.addHeader("authtoken", AUTHTOKEN);
        webhook.addParam("sort", "asc");
        webhook.addParam("order", "order");
        webhook.addParam("limit", "10");
        webhook.addParam("skip", "5");

        Request request = webhook.findExecutionLogs().request();
        Assertions.assertEquals("/installations/installation_id/webhooks/blte0fdf06879c18c1e/executions", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals("installation_id", request.url().pathSegments().get(1));
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testFetchExecutionLogs() {
        webhook.addHeader("authtoken", AUTHTOKEN);
        webhook.addParam("sort", "asc");
        webhook.addParam("order", "order");
        webhook.addParam("limit", "10");
        webhook.addParam("skip", "5");

        Request request = webhook.fetchExecutionLogs(WEBHOOK_ID).request();
        Assertions.assertEquals("/installations/installation_id/webhooks/blte0fdf06879c18c1e/executions/blte0fdf06879c18c1e", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals("installation_id", request.url().pathSegments().get(1));
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


    @Test
    void testRetryExecution() {
        webhook.addHeader("authtoken", AUTHTOKEN);
        webhook.addParam("sort", "asc");
        webhook.addParam("order", "order");
        webhook.addParam("limit", "10");
        webhook.addParam("skip", "5");
        Request request = webhook.retryExecution("execution_id").request();
        Assertions.assertEquals("/installations/installation_id/webhooks/blte0fdf06879c18c1e/executions/execution_id/retry", request.url().encodedPath());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(7, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals("installation_id", request.url().pathSegments().get(1));
        Assertions.assertEquals("webhooks", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&skip=5&sort=asc&order=order", request.url().query());
    }


}
