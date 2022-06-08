package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebhookUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String _uid = Dotenv.load().get("workflow_uid");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    protected static AuditLog auditLog;


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        auditLog = stack.auditLog();

    }


    @Test
    @Order(1)
    void workflowHeaders() {
        auditLog.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(3, auditLog.headers.size());
    }

    @Test
    @Order(2)
    void workflowParamsWithSizeZero() {
        Assertions.assertEquals(0, auditLog.params.size());
    }

    @Test
    @Order(3)
    void workflowParamsWithSizeMin() {
        auditLog.addParam("paramFakeKey", "paramFakeValue");
        Assertions.assertEquals(1, auditLog.params.size());
    }

    @Test
    @Order(4)
    void workflowParamsWithSizeMax() {
        auditLog.removeParam("paramFakeKey");
        auditLog.addParam("include_rules", true);
        auditLog.addParam("include_permissions", true);
        Assertions.assertEquals(2, auditLog.params.size());
    }

    @Test
    @Order(5)
    void workflowFetchAll() {
        Request request = auditLog.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("audit-logs", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/audit-logs?include_rules=true&include_permissions=true", request.url().toString());
    }

    @Test
    @Order(6)
    void workflowFetchByWorkflowId() {
        Request request = auditLog.fetchAuditLog(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("audit-logs", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/audit-logs/" + _uid, request.url().toString());
    }


}
