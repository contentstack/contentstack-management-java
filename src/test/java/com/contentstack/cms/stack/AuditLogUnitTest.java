package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuditLogUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.USER_ID;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static AuditLog auditLog;


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        auditLog = stack.auditLog(_uid);
        auditLog = stack.auditLog();
        auditLog.clearParams();

    }


    @Test
    @Order(1)
    void workflowHeaders() {
        auditLog.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(1, auditLog.headers.size());
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
        Request request = auditLog.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
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
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "managementToken");
        AuditLog audit = stack.auditLog("audit_uid");
        Request request = audit.fetch().request();
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("audit-logs", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/audit-logs/audit_uid", request.url().toString());
    }


}
