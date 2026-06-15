package com.contentstack.cms.core;

import com.contentstack.cms.Contentstack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EndpointTest {

    @BeforeEach
    @AfterEach
    void resetCache() {
        Endpoint.resetCache();
    }

    // ── canonical IDs — contentDelivery ───────────────────────────────────────

    @Test
    void testNaContentDelivery() {
        assertEquals("https://cdn.contentstack.io",
                Endpoint.getContentstackEndpoint("na", "contentDelivery"));
    }

    @Test
    void testNaContentManagement() {
        assertEquals("https://api.contentstack.io",
                Endpoint.getContentstackEndpoint("na", "contentManagement"));
    }

    @Test
    void testEuContentDelivery() {
        assertEquals("https://eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "contentDelivery"));
    }

    @Test
    void testEuContentManagement() {
        assertEquals("https://eu-api.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "contentManagement"));
    }

    @Test
    void testAuContentDelivery() {
        assertEquals("https://au-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("au", "contentDelivery"));
    }

    @Test
    void testAzureNaContentDelivery() {
        assertEquals("https://azure-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("azure-na", "contentDelivery"));
    }

    @Test
    void testAzureEuContentDelivery() {
        assertEquals("https://azure-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("azure-eu", "contentDelivery"));
    }

    @Test
    void testGcpNaContentDelivery() {
        assertEquals("https://gcp-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("gcp-na", "contentDelivery"));
    }

    @Test
    void testGcpEuContentDelivery() {
        assertEquals("https://gcp-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("gcp-eu", "contentDelivery"));
    }

    // ── region aliases resolve to the same endpoint ───────────────────────────

    @ParameterizedTest
    @ValueSource(strings = {"na", "us", "aws-na", "aws_na", "NA", "US", "AWS-NA", "AWS_NA"})
    void testNaAliasesAllResolveToNaCdn(String alias) {
        assertEquals("https://cdn.contentstack.io",
                Endpoint.getContentstackEndpoint(alias, "contentDelivery"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"eu", "aws-eu", "aws_eu", "EU", "AWS-EU", "AWS_EU"})
    void testEuAliasesAllResolveToEuCdn(String alias) {
        assertEquals("https://eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint(alias, "contentDelivery"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"au", "aws-au", "aws_au", "AU", "AWS-AU", "AWS_AU"})
    void testAuAliasesAllResolveToAuCdn(String alias) {
        assertEquals("https://au-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint(alias, "contentDelivery"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"azure-na", "azure_na", "AZURE-NA", "AZURE_NA"})
    void testAzureNaAliasesResolve(String alias) {
        assertEquals("https://azure-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint(alias, "contentDelivery"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"azure-eu", "azure_eu", "AZURE-EU", "AZURE_EU"})
    void testAzureEuAliasesResolve(String alias) {
        assertEquals("https://azure-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint(alias, "contentDelivery"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"gcp-na", "gcp_na", "GCP-NA", "GCP_NA"})
    void testGcpNaAliasesResolve(String alias) {
        assertEquals("https://gcp-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint(alias, "contentDelivery"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"gcp-eu", "gcp_eu", "GCP-EU", "GCP_EU"})
    void testGcpEuAliasesResolve(String alias) {
        assertEquals("https://gcp-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint(alias, "contentDelivery"));
    }

    // ── omitHttps ─────────────────────────────────────────────────────────────

    @Test
    void testOmitHttpsFalseKeepsScheme() {
        String url = Endpoint.getContentstackEndpoint("na", "contentDelivery", false);
        assertTrue(url.startsWith("https://"));
    }

    @Test
    void testOmitHttpsTrueStripsScheme() {
        String host = Endpoint.getContentstackEndpoint("na", "contentDelivery", true);
        assertFalse(host.startsWith("https://"));
        assertEquals("cdn.contentstack.io", host);
    }

    @Test
    void testOmitHttpsEuCdn() {
        assertEquals("eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "contentDelivery", true));
    }

    @Test
    void testOmitHttpsAzureNaApi() {
        assertEquals("azure-na-api.contentstack.com",
                Endpoint.getContentstackEndpoint("azure-na", "contentManagement", true));
    }

    @Test
    void testOmitHttpsGcpEuCdn() {
        assertEquals("gcp-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("gcp-eu", "contentDelivery", true));
    }

    // ── various service keys ───────────────────────────────────────────────────

    @Test
    void testNaAuth() {
        assertEquals("https://auth-api.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "auth"));
    }

    @Test
    void testNaGraphqlDelivery() {
        assertEquals("https://graphql.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "graphqlDelivery"));
    }

    @Test
    void testNaPreview() {
        assertEquals("https://rest-preview.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "preview"));
    }

    @Test
    void testNaApplication() {
        assertEquals("https://app.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "application"));
    }

    @Test
    void testNaAssetManagement() {
        assertEquals("https://am-api.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "assetManagement"));
    }

    @Test
    void testNaDeveloperHub() {
        assertEquals("https://developerhub-api.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "developerHub"));
    }

    @Test
    void testEuAutomate() {
        assertEquals("https://eu-prod-automations-api.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "automate"));
    }

    @Test
    void testNaLaunch() {
        assertEquals("https://launch-api.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "launch"));
    }

    // ── all-endpoints map ──────────────────────────────────────────────────────

    @Test
    void testGetAllEndpointsForNaNotEmpty() {
        Map<String, String> endpoints = Endpoint.getContentstackEndpoints("na");
        assertNotNull(endpoints);
        assertFalse(endpoints.isEmpty());
    }

    @Test
    void testGetAllEndpointsForNaContainsExpectedKeys() {
        Map<String, String> endpoints = Endpoint.getContentstackEndpoints("na");
        assertEquals("https://cdn.contentstack.io", endpoints.get("contentDelivery"));
        assertEquals("https://api.contentstack.io", endpoints.get("contentManagement"));
        assertEquals("https://auth-api.contentstack.com", endpoints.get("auth"));
    }

    @Test
    void testGetAllEndpointsForEu() {
        Map<String, String> endpoints = Endpoint.getContentstackEndpoints("eu");
        assertEquals("https://eu-cdn.contentstack.com", endpoints.get("contentDelivery"));
        assertEquals("https://eu-api.contentstack.com", endpoints.get("contentManagement"));
    }

    @Test
    void testGetAllEndpointsOmitHttps() {
        Map<String, String> hosts = Endpoint.getContentstackEndpoints("eu", true);
        assertEquals("eu-cdn.contentstack.com", hosts.get("contentDelivery"));
        assertEquals("eu-api.contentstack.com", hosts.get("contentManagement"));
        hosts.values().forEach(v -> assertFalse(v.startsWith("https://"),
                "Expected no scheme but got: " + v));
    }

    @Test
    void testGetAllEndpointsWithSchemePresentByDefault() {
        Map<String, String> endpoints = Endpoint.getContentstackEndpoints("na");
        endpoints.values().forEach(v -> assertTrue(v.startsWith("https://"),
                "Expected https:// but got: " + v));
    }

    // ── error handling ────────────────────────────────────────────────────────

    @Test
    void testEmptyRegionThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("", "contentDelivery"));
        assertTrue(ex.getMessage().contains("Empty region"));
    }

    @Test
    void testNullRegionThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint(null, "contentDelivery"));
    }

    @Test
    void testInvalidRegionThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("asia-pacific", "contentDelivery"));
        assertTrue(ex.getMessage().contains("Invalid region"));
        assertTrue(ex.getMessage().contains("asia-pacific"));
    }

    @Test
    void testUnknownServiceThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("na", "cms"));
        assertTrue(ex.getMessage().contains("cms"));
        assertTrue(ex.getMessage().contains("na"));
    }

    @Test
    void testEmptyServiceThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("na", ""));
    }

    @Test
    void testNullServiceThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("na", null));
    }

    // ── Contentstack class proxy ───────────────────────────────────────────────

    @Test
    void testContentstackProxyWithScheme() {
        assertEquals("https://eu-api.contentstack.com",
                Contentstack.getContentstackEndpoint("eu", "contentManagement"));
    }

    @Test
    void testContentstackProxyOmitHttps() {
        assertEquals("eu-api.contentstack.com",
                Contentstack.getContentstackEndpoint("eu", "contentManagement", true));
    }

    // ── Builder.setRegion() ────────────────────────────────────────────────────

    @Test
    void testBuilderSetRegionEuResolvesCorrectHost() {
        Contentstack client = new Contentstack.Builder()
                .setRegion("eu")
                .build();
        assertEquals("eu-api.contentstack.com", client.getHost());
    }

    @Test
    void testBuilderSetRegionNaResolvesCorrectHost() {
        Contentstack client = new Contentstack.Builder()
                .setRegion("na")
                .build();
        assertEquals("api.contentstack.io", client.getHost());
    }

    @Test
    void testBuilderSetRegionAzureNaResolvesCorrectHost() {
        Contentstack client = new Contentstack.Builder()
                .setRegion("azure-na")
                .build();
        assertEquals("azure-na-api.contentstack.com", client.getHost());
    }

    @Test
    void testBuilderSetRegionGcpEuResolvesCorrectHost() {
        Contentstack client = new Contentstack.Builder()
                .setRegion("gcp-eu")
                .build();
        assertEquals("gcp-eu-api.contentstack.com", client.getHost());
    }

    @Test
    void testBuilderSetRegionInvalidThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contentstack.Builder().setRegion("invalid-region"));
    }

    @Test
    void testBuilderSetRegionWithAuthtoken() {
        Contentstack client = new Contentstack.Builder()
                .setAuthtoken("fake_authtoken")
                .setRegion("au")
                .build();
        assertEquals("au-api.contentstack.com", client.getHost());
    }

    // ── caching — second call returns same result without re-parsing ───────────

    @Test
    void testCachingReturnsSameResult() {
        String first  = Endpoint.getContentstackEndpoint("na", "contentDelivery");
        String second = Endpoint.getContentstackEndpoint("na", "contentDelivery");
        assertEquals(first, second);
    }

    // ── refresh() — forces live download and replaces cache ───────────────────

    @Test
    void testRefreshReturnsPositiveRegionCount() {
        int count = Endpoint.refresh();
        assertTrue(count > 0, "refresh() should return at least one region");
    }

    @Test
    void testRefreshLoadsAllKnownRegions() {
        int count = Endpoint.refresh();
        assertEquals(7, count, "Expected 7 regions from the live registry");
    }

    @Test
    void testRefreshUpdatesCache() {
        // prime the cache from the bundled file
        Endpoint.getContentstackEndpoint("na", "contentDelivery");

        // force refresh from live
        Endpoint.refresh();

        // subsequent resolution should still work correctly
        assertEquals("https://cdn.contentstack.io",
                Endpoint.getContentstackEndpoint("na", "contentDelivery"));
    }

    @Test
    void testContentstackRefreshRegionsProxy() {
        int count = Contentstack.refreshRegions();
        assertTrue(count > 0);
    }

    @Test
    void testRefreshThenResolveAllRegions() {
        Endpoint.refresh();
        // verify all 7 canonical region IDs still resolve after a live refresh
        String[] regions = {"na", "eu", "au", "azure-na", "azure-eu", "gcp-na", "gcp-eu"};
        for (String region : regions) {
            assertDoesNotThrow(() -> Endpoint.getContentstackEndpoint(region, "contentManagement"),
                    "Should resolve contentManagement for region: " + region);
        }
    }
}
