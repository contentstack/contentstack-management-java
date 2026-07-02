package com.contentstack.cms.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Resolves Contentstack API endpoints for any region and service.
 *
 * <p>Endpoint data is loaded from the bundled {@code regions.json} classpath resource.
 * The parsed result is cached for the lifetime of the JVM process.
 * If the bundled file is absent, a live download from
 * {@code https://artifacts.contentstack.com/regions.json} is attempted as a fallback.
 *
 * <pre>{@code
 * // Get a specific service URL
 * String cdnUrl = Endpoint.getContentstackEndpoint("eu", "contentDelivery");
 * // → "https://eu-cdn.contentstack.com"
 *
 * // Get the host without the https:// scheme (for use with Builder.setHost)
 * String host = Endpoint.getContentstackEndpoint("eu", "contentDelivery", true);
 * // → "eu-cdn.contentstack.com"
 *
 * // Get all endpoints for a region
 * Map<String, String> all = Endpoint.getContentstackEndpoints("eu");
 * }</pre>
 */
public class Endpoint {

    private static final String REGIONS_URL = "https://artifacts.contentstack.com/regions.json";
    private static final String REGIONS_RESOURCE = "regions.json";

    /**
     * Domain suffixes that endpoint URLs are permitted to target. The regions
     * registry is fetched from a remote resource, so every URL derived from it
     * is validated against this allowlist before it can be used as a request
     * target. This prevents Server-Side Request Forgery (SSRF) should the
     * registry ever be tampered with or point at an unexpected host.
     */
    private static final String[] TRUSTED_DOMAIN_SUFFIXES = {
            ".contentstack.com",
            ".contentstack.io"
    };

    private static volatile JsonArray regionsData = null;

    private Endpoint() {}

    /**
     * Returns the URL for a specific service in the given region.
     *
     * @param region  canonical region ID ({@code na}, {@code eu}, {@code au}, {@code azure-na},
     *                {@code azure-eu}, {@code gcp-na}, {@code gcp-eu}) or any accepted alias.
     *                Case-insensitive; {@code -} and {@code _} separators are equivalent.
     * @param service service name (e.g. {@code contentManagement}, {@code contentDelivery})
     * @return full URL including {@code https://}
     * @throws IllegalArgumentException if region or service is unknown, or region is empty
     * @throws RuntimeException         if {@code regions.json} cannot be loaded
     */
    public static String getContentstackEndpoint(String region, String service) {
        return getContentstackEndpoint(region, service, false);
    }

    /**
     * Returns the URL for a specific service in the given region.
     *
     * @param region     canonical region ID or alias
     * @param service    service name
     * @param omitHttps  when {@code true}, strips {@code https://} from the result
     * @return URL string, with or without scheme depending on {@code omitHttps}
     * @throws IllegalArgumentException if region or service is unknown, or region is empty
     * @throws RuntimeException         if {@code regions.json} cannot be loaded
     */
    public static String getContentstackEndpoint(String region, String service, boolean omitHttps) {
        if (service == null || service.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Service must not be empty. Use getContentstackEndpoints(region) to retrieve all endpoints.");
        }
        JsonObject regionRow = resolveRegion(region);
        JsonObject endpoints = regionRow.getAsJsonObject("endpoints");
        if (endpoints == null || !endpoints.has(service)) {
            throw new IllegalArgumentException(
                    "Service \"" + service + "\" not found for region \"" + regionRow.get("id").getAsString() + "\"");
        }
        String url = validateTrustedEndpoint(endpoints.get(service).getAsString());
        return omitHttps ? stripHttps(url) : url;
    }

    /**
     * Returns all endpoint URLs for the given region as an ordered map.
     *
     * @param region canonical region ID or alias
     * @return map of service name → URL (includes {@code https://})
     * @throws IllegalArgumentException if region is unknown or empty
     * @throws RuntimeException         if {@code regions.json} cannot be loaded
     */
    public static Map<String, String> getContentstackEndpoints(String region) {
        return getContentstackEndpoints(region, false);
    }

    /**
     * Returns all endpoint URLs for the given region as an ordered map.
     *
     * @param region    canonical region ID or alias
     * @param omitHttps when {@code true}, strips {@code https://} from every URL
     * @return map of service name → URL
     * @throws IllegalArgumentException if region is unknown or empty
     * @throws RuntimeException         if {@code regions.json} cannot be loaded
     */
    public static Map<String, String> getContentstackEndpoints(String region, boolean omitHttps) {
        JsonObject regionRow = resolveRegion(region);
        JsonObject endpoints = regionRow.getAsJsonObject("endpoints");
        Map<String, String> result = new LinkedHashMap<>();
        if (endpoints != null) {
            for (Map.Entry<String, JsonElement> entry : endpoints.entrySet()) {
                String url = validateTrustedEndpoint(entry.getValue().getAsString());
                result.put(entry.getKey(), omitHttps ? stripHttps(url) : url);
            }
        }
        return result;
    }

    // ── internal ──────────────────────────────────────────────────────────────

    private static JsonObject resolveRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty region provided. Please provide a valid region.");
        }
        JsonArray regions = loadRegions();
        String normalized = region.trim().toLowerCase().replace('_', '-');

        // First pass: exact match on canonical id
        for (int i = 0; i < regions.size(); i++) {
            JsonObject row = regions.get(i).getAsJsonObject();
            if (row.get("id").getAsString().equals(normalized)) {
                return row;
            }
        }

        // Second pass: match on alias list (case-insensitive, normalised separators)
        for (int i = 0; i < regions.size(); i++) {
            JsonObject row = regions.get(i).getAsJsonObject();
            JsonArray aliases = row.getAsJsonArray("alias");
            if (aliases != null) {
                for (int j = 0; j < aliases.size(); j++) {
                    String alias = aliases.get(j).getAsString().toLowerCase().replace('_', '-');
                    if (alias.equals(normalized)) {
                        return row;
                    }
                }
            }
        }

        throw new IllegalArgumentException("Invalid region: " + region);
    }

    private static synchronized JsonArray loadRegions() {
        if (regionsData != null) {
            return regionsData;
        }

        // Try bundled classpath resource first
        InputStream is = Endpoint.class.getClassLoader().getResourceAsStream(REGIONS_RESOURCE);
        if (is != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String json = reader.lines().collect(Collectors.joining("\n"));
                regionsData = JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("regions");
                return regionsData;
            } catch (Exception e) {
                // fall through to live download
            }
        }

        // Fallback: download from Contentstack
        try {
            String json = downloadRegions();
            regionsData = JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("regions");
            return regionsData;
        } catch (Exception e) {
            throw new RuntimeException(
                    "contentstack/cms: regions.json not found and could not be downloaded. " +
                    "Ensure the JAR was built correctly or network access is available.", e);
        }
    }

    private static String downloadRegions() throws IOException {
        URL url = new URL(REGIONS_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10_000);
        conn.setReadTimeout(10_000);
        try (InputStream is = conn.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Forces a live download of the regions registry from
     * {@code https://artifacts.contentstack.com/regions.json} and replaces
     * the in-memory cache.
     *
     * <p>Call this if you suspect new Contentstack regions or service URLs have
     * been published since the SDK JAR was built, without needing to upgrade
     * the SDK version.
     *
     * <pre>{@code
     * int count = Endpoint.refresh();
     * System.out.println("Loaded " + count + " regions from live registry.");
     * }</pre>
     *
     * @return the number of regions loaded from the live registry
     * @throws RuntimeException if the download fails or the response is not valid JSON
     */
    public static synchronized int refresh() {
        try {
            String json = downloadRegions();
            JsonArray fresh = JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("regions");
            regionsData = fresh;
            return fresh.size();
        } catch (Exception e) {
            throw new RuntimeException(
                    "contentstack/cms: Failed to refresh regions.json from " + REGIONS_URL + ". " +
                    "Check network connectivity and try again.", e);
        }
    }

    /**
     * Validates that an endpoint URL sourced from the regions registry is a
     * well-formed HTTPS URL whose host belongs to a trusted Contentstack domain.
     *
     * <p>The regions registry is loaded from a remote resource; constraining the
     * host to an allowlist here neutralizes any Server-Side Request Forgery (SSRF)
     * risk before the value can flow into an outbound request.
     *
     * @param url the endpoint URL read from the registry
     * @return the URL, unchanged, when it targets a trusted host
     * @throws IllegalArgumentException if the URL is malformed, not HTTPS, or
     *                                  targets an untrusted host
     */
    private static String validateTrustedEndpoint(String url) {
        URL parsed;
        try {
            parsed = new URL(url);
        } catch (java.net.MalformedURLException e) {
            throw new IllegalArgumentException("Malformed endpoint URL in regions registry: " + url, e);
        }
        if (!"https".equalsIgnoreCase(parsed.getProtocol())) {
            throw new IllegalArgumentException("Endpoint URL must use HTTPS: " + url);
        }
        String host = parsed.getHost() == null ? "" : parsed.getHost().toLowerCase();
        for (String suffix : TRUSTED_DOMAIN_SUFFIXES) {
            // suffix begins with '.', so "contentstack.com" itself is matched via
            // the leading-dot form only for subdomains; also allow the bare apex.
            if (host.endsWith(suffix) || host.equals(suffix.substring(1))) {
                return url;
            }
        }
        throw new IllegalArgumentException("Endpoint URL targets an untrusted host: " + url);
    }

    private static String stripHttps(String url) {
        return url.replaceAll("^https?://", "");
    }

    /** Clears the in-memory cache. For use in tests only. */
    static void resetCache() {
        regionsData = null;
    }
}
