package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Endpoint;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * API-level tests for the endpoint integration feature.
 *
 * <p>URL-structure tests verify the resolved host is wired into every outgoing
 * request. Live-call tests make real network requests to the NA management API
 * using credentials from {@code .env}.
 */
@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EndpointAPITest {

    private static final String API_KEY          = TestClient.API_KEY;
    private static final String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    private static final String REGION           = TestClient.REGION;

    // ── URL structure — all 7 regions ────────────────────────────────────────

    @Order(1)
    @Test
    void testNaRegionRequestUrlIsCorrect() {
        Stack stack = clientForRegion("na").stack(API_KEY, MANAGEMENT_TOKEN);
        Request req = stack.contentType("test").find().request();
        assertTrue(req.url().isHttps());
        assertEquals("api.contentstack.io", req.url().host());
        assertEquals("v3", req.url().pathSegments().get(0));
    }

    @Order(2)
    @Test
    void testEuRegionRequestUrlIsCorrect() {
        Stack stack = clientForRegion("eu").stack(API_KEY, MANAGEMENT_TOKEN);
        Request req = stack.contentType("test").find().request();
        assertTrue(req.url().isHttps());
        assertEquals("eu-api.contentstack.com", req.url().host());
    }

    @Order(3)
    @Test
    void testAuRegionRequestUrlIsCorrect() {
        Stack stack = clientForRegion("au").stack(API_KEY, MANAGEMENT_TOKEN);
        Request req = stack.contentType("test").find().request();
        assertTrue(req.url().isHttps());
        assertEquals("au-api.contentstack.com", req.url().host());
    }

    @Order(4)
    @Test
    void testAzureNaRegionRequestUrlIsCorrect() {
        Stack stack = clientForRegion("azure-na").stack(API_KEY, MANAGEMENT_TOKEN);
        Request req = stack.contentType("test").find().request();
        assertTrue(req.url().isHttps());
        assertEquals("azure-na-api.contentstack.com", req.url().host());
    }

    @Order(5)
    @Test
    void testAzureEuRegionRequestUrlIsCorrect() {
        Stack stack = clientForRegion("azure-eu").stack(API_KEY, MANAGEMENT_TOKEN);
        Request req = stack.contentType("test").find().request();
        assertTrue(req.url().isHttps());
        assertEquals("azure-eu-api.contentstack.com", req.url().host());
    }

    @Order(6)
    @Test
    void testGcpNaRegionRequestUrlIsCorrect() {
        Stack stack = clientForRegion("gcp-na").stack(API_KEY, MANAGEMENT_TOKEN);
        Request req = stack.contentType("test").find().request();
        assertTrue(req.url().isHttps());
        assertEquals("gcp-na-api.contentstack.com", req.url().host());
    }

    @Order(7)
    @Test
    void testGcpEuRegionRequestUrlIsCorrect() {
        Stack stack = clientForRegion("gcp-eu").stack(API_KEY, MANAGEMENT_TOKEN);
        Request req = stack.contentType("test").find().request();
        assertTrue(req.url().isHttps());
        assertEquals("gcp-eu-api.contentstack.com", req.url().host());
    }

    // ── region aliases are wired correctly ────────────────────────────────────

    @Order(8)
    @Test
    void testUsAliasResolvesToNaHost() {
        Stack stack = clientForRegion("us").stack(API_KEY, MANAGEMENT_TOKEN);
        assertEquals("api.contentstack.io", stack.contentType("t").find().request().url().host());
    }

    @Order(9)
    @Test
    void testAwsEuAliasResolvesToEuHost() {
        Stack stack = clientForRegion("aws-eu").stack(API_KEY, MANAGEMENT_TOKEN);
        assertEquals("eu-api.contentstack.com", stack.contentType("t").find().request().url().host());
    }

    @Order(10)
    @Test
    void testAzureNaUnderscoreAliasResolvesCorrectly() {
        Stack stack = clientForRegion("azure_na").stack(API_KEY, MANAGEMENT_TOKEN);
        assertEquals("azure-na-api.contentstack.com", stack.contentType("t").find().request().url().host());
    }

    @Order(11)
    @Test
    void testUppercaseRegionAliasResolvesCorrectly() {
        Stack stack = clientForRegion("GCP-EU").stack(API_KEY, MANAGEMENT_TOKEN);
        assertEquals("gcp-eu-api.contentstack.com", stack.contentType("t").find().request().url().host());
    }

    // ── setRegion wires host into Retrofit base URL ───────────────────────────

    @Order(12)
    @Test
    void testSetRegionWiresCorrectBaseUrl() {
        Contentstack client = new Contentstack.Builder()
                .setRegion("eu")
                .build();
        assertTrue(client.getBaseUrl().contains("eu-api.contentstack.com"),
                "Base URL should contain EU CMA host");
    }

    @Order(13)
    @Test
    void testSetRegionNaWiresDefaultHost() {
        Contentstack client = new Contentstack.Builder()
                .setRegion("na")
                .build();
        assertEquals("api.contentstack.io", client.getHost());
        assertTrue(client.getBaseUrl().contains("api.contentstack.io"));
    }

    // ── getContentstackEndpoints() returns full map ───────────────────────────

    @Order(14)
    @Test
    void testGetAllEndpointsForRegionNotEmpty() {
        Map<String, String> endpoints = Contentstack.getContentstackEndpoints(REGION);
        assertNotNull(endpoints);
        assertFalse(endpoints.isEmpty());
        assertTrue(endpoints.containsKey("contentManagement"));
        assertTrue(endpoints.containsKey("contentDelivery"));
        assertTrue(endpoints.containsKey("auth"));
    }

    @Order(15)
    @Test
    void testGetAllEndpointsOmitHttps() {
        Map<String, String> hosts = Contentstack.getContentstackEndpoints("eu", true);
        hosts.values().forEach(v ->
                assertFalse(v.startsWith("https://"), "Expected no scheme but got: " + v));
        assertEquals("eu-api.contentstack.com", hosts.get("contentManagement"));
    }

    // ── live API call — uses env region + credentials ─────────────────────────

    @Order(16)
    @Test
    void testLiveCallWithSetRegionReturnsResponse() throws IOException {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken(TestClient.AUTHTOKEN)
                .setRegion(REGION)
                .build();

        String resolvedHost = Endpoint.getContentstackEndpoint(REGION, "contentManagement", true);
        assertEquals(resolvedHost, client.getHost());

        Response<okhttp3.ResponseBody> response = client.user().getUser().execute();
        // 200 = valid authtoken, 401 = invalid but network + endpoint resolution worked
        assertTrue(response.code() == 200 || response.code() == 401,
                "Expected 200 or 401, got: " + response.code());
    }

    @Order(17)
    @Test
    void testLiveStackCallWithSetRegion() throws IOException {
        Stack stack = new Contentstack.Builder()
                .setAuthtoken(TestClient.AUTHTOKEN)
                .setRegion(REGION)
                .build()
                .stack(API_KEY, MANAGEMENT_TOKEN);

        Request req = stack.contentType("").find().request();
        String expectedHost = Endpoint.getContentstackEndpoint(REGION, "contentManagement", true);
        assertEquals(expectedHost, req.url().host());
        assertTrue(req.url().isHttps());
        assertEquals(Util.VERSION, req.url().pathSegments().get(0));
    }

    // ── helper ────────────────────────────────────────────────────────────────

    private Contentstack clientForRegion(String region) {
        return new Contentstack.Builder().setRegion(region).build();
    }
}
