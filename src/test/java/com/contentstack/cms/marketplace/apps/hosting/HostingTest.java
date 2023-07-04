package com.contentstack.cms.marketplace.apps.hosting;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;


@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HostingTest {


    private static Hosting hosting;
    private final static String ORG_UID = TestClient.ORGANIZATION_UID;
    private final static String APP_UID = "APP_ID_8998";


    @BeforeAll
    static void loadBeforeAll() {
        hosting = TestClient.getClient().organization(ORG_UID).marketplace("api.contentstack.io").app(APP_UID).hosting()
                .addParam("param1", "value1")
                .addHeader("authtoken", ORG_UID);
    }

    @Test
    void testFetchHosting() {
        Request request = hosting.fetchHosting().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }


    @Test
    void testEnableToggleHosting() {
        Request request = hosting.enableToggleHosting().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/enable", request.url().encodedPath());
        Assertions.assertEquals("PATCH", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNull(request.url().query());
    }


    @Test
    void testDisableToggleHosting() {
        Request request = hosting.disableToggleHosting().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/disable", request.url().encodedPath());
        Assertions.assertEquals("PATCH", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNull(request.url().query());
    }


    @Test
    void testCreateSignedUploadUrl() {
        Request request = hosting.createSignedUploadUrl().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/signedUploadUrl", request.url().encodedPath());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNull(request.url().query());
    }


    @Test
    void testUploadFile() {
        Hosting localhosting = TestClient.getClient().organization(ORG_UID).marketplace().app(APP_UID).hosting();
        Request request = localhosting.uploadFile("www.google.com/abc/host/option1/option2/tag/upload").request();
        Assertions.assertEquals("/www.google.com/abc/host/option1/option2/tag/upload", request.url().encodedPath());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(7, request.url().pathSegments().size());
        Assertions.assertEquals("www.google.com", request.url().pathSegments().get(0));
        Assertions.assertEquals("abc", request.url().pathSegments().get(1));
        Assertions.assertEquals("host", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
    }


    @Test
    void testCreateDeployment() {
        JSONObject body = new JSONObject();
        body.put("key", "value");
        Hosting localhosting = TestClient.getClient().organization(ORG_UID).marketplace().app(APP_UID).hosting();
        localhosting.addHeader("authtoken", ORG_UID);
        Request request = localhosting.createDeployment(body).request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/deployments", request.url().encodedPath());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNull(request.url().query());
    }


    @Test
    void testFindDeployments() {
        Request request = hosting.findDeployments().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/deployments", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }


    @Test
    void testFetchDeployment() {
        Request request = hosting.fetchDeployment("devId").request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/deployments/devId", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }


    @Test
    void testGetLatestLiveDeployment() {
        Request request = hosting.getLatestLiveDeployment().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/latestLiveDeployment", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }

    @Test
    void testFindDeploymentLogs() {
        Request request = hosting.findDeploymentLogs("deploymentId").request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/deployments/deploymentId/logs", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }


    @Test
    void testCreateSignedDownloadUrl() {
        Request request = hosting.createSignedDownloadUrl().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/signedDownloadUrl", request.url().encodedPath());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }


    @Test
    void testDownloadFile() {
        Request request = hosting.downloadFile("www.google.com").request();
        Assertions.assertEquals("/www.google.com", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(1, request.url().pathSegments().size());
        Assertions.assertEquals("www.google.com", request.url().pathSegments().get(0));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }


    @Test
    void testToogleHosting() {
        hosting.addHeader("authtoken", ORG_UID);
        Request request = hosting.enableToggleHosting().request();
        Assertions.assertEquals("/manifests/APP_ID_8998/hosting/enable", request.url().encodedPath());
        Assertions.assertEquals("PATCH", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("hosting", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNull(request.url().query());
    }


}

