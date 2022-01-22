package com.contentstack.cms.entries;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Testcases for Entry class
 */
class EntryFieldUnitTests {

    protected static String _AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Entry entryInstance;

    @BeforeAll
    static void setup() {
        entryInstance = new Contentstack.Builder().setAuthtoken(_AUTHTOKEN).build().entry(API_KEY, MANAGEMENT_TOKEN);
    }

    @Test
    void testEntryFetchIsHttps() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        queryParams.put("include_workflow", false);
        queryParams.put("include_publish_details", true);
        Request resp = entryInstance.fetch(queryParams).request();
        Assertions.assertTrue(resp.isHttps());
    }

    @Test
    void testEntryFetchQuery() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        queryParams.put("include_workflow", false);
        queryParams.put("include_publish_details", true);
        Request resp = entryInstance.fetch(queryParams).request();
        Assertions.assertEquals("include_publish_details=true&locale=en-us&include_workflow=false", resp.url().query());
    }

    @Test
    void testEntryFetchEncodedPath() {
        Request resp = entryInstance.fetch(new HashMap<>()).request();
        Assertions.assertEquals("/v3/content_types/product/entries", resp.url().encodedPath());
    }

    @Test
    void testEntryFetchHeaders() {
        Request resp = entryInstance.fetch(new HashMap<>()).request();
        Assertions.assertEquals(3, resp.headers().size());
    }

    @Test
    void testEntryFetchHeaderNames() {
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        matcher.add("Content-Type");
        Request resp = entryInstance.fetch(new HashMap<>()).request();
        Assertions.assertTrue(resp.headers().names().containsAll(matcher));
    }

    @Test
    void testEntryFetchMethod() {
        Request resp = entryInstance.fetch(new HashMap<>()).request();
        Assertions.assertEquals("GET", resp.method());
    }

    // --------------------[Single]----------------

    @Test
    void testSingleEntryQuery() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        queryParams.put("include_workflow", false);
        queryParams.put("include_publish_details", true);
        Request resp = entryInstance.single(API_KEY, queryParams).request();
        Assertions.assertEquals("include_publish_details=true&locale=en-us&include_workflow=false", resp.url().query());
    }

    @Test
    void testSingleEntryEncodedPath() {
        Request resp = entryInstance.single(API_KEY, new HashMap<>()).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + API_KEY, resp.url().encodedPath());
    }

    @Test
    void testSingleEntryHeaders() {
        Request resp = entryInstance.single(API_KEY, new HashMap<>()).request();
        Assertions.assertEquals(3, resp.headers().size());
    }

    @Test
    void testSingleEntryHeaderNames() {
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        matcher.add("Content-Type");
        Request resp = entryInstance.single(API_KEY, new HashMap<>()).request();
        Assertions.assertTrue(resp.headers().names().containsAll(matcher));
    }

    @Test
    void testSingleEntryMethod() {
        Request resp = entryInstance.single(API_KEY, new HashMap<>()).request();
        Assertions.assertEquals("GET", resp.method());
    }

    @Test
    void testSingleEntryCompleteUrl() {
        Request resp = entryInstance.single(API_KEY, new HashMap<>()).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + API_KEY,
                resp.url().toString());
    }

    // ------------------------[Create]------------------------------

    @Test
    void testCreateEntryQueryRespNull() {
        Request resp = entryInstance.create(new JSONObject(), null).request();
        Assertions.assertNull(resp.url().query());
    }

    @Test
    void testCreateEntryQuery() {
        Map<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject(), query).request();
        Assertions.assertEquals("locale=en-us", resp.url().query());
    }

    @Test
    void testCreateEntryEncodedPath() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
        Assertions.assertEquals("/v3/content_types/product/entries", resp.url().encodedPath());
    }

    @Test
    void testCreateEntryHeaders() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
        Assertions.assertEquals(3, resp.headers().size());
    }

    @Test
    void testCreateEntryHeaderNames() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        matcher.add("Content-Type");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
        Assertions.assertTrue(resp.headers().names().containsAll(matcher));
    }

    @Test
    void testCreateEntryMethod() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
        Assertions.assertEquals("GET", resp.method());
    }

    @Test
    void testCreateEntryCompleteUrl() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + API_KEY,
                resp.url().toString());
    }

}
