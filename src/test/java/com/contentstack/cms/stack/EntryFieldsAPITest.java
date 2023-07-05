package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntryFieldsAPITest {

    private static Entry entry;
    private static int _COUNT = 2;
    private static String apiKey = TestClient.API_KEY;
    private static String managementToken = TestClient.MANAGEMENT_TOKEN;
    private static ContentType contentType = TestClient.getClient().stack(apiKey, managementToken).contentType("test");

    @BeforeAll
    public static void setup() throws IOException {
        entry = contentType.entry();
    }

    @Test
    @Order(1)
    void testEntryFindAll() {
        Request request = entry.find().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries", request.url().toString());
    }

    @Order(2)
    @Test
    void testEntryFetch() {
        entry = contentType.entry(apiKey);
        Request request = entry.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + TestClient.API_KEY, request.url().toString());
    }

    @Order(3)
    @Test
    void testEntryCreate() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryCreate = new JSONObject();
        entryCreate.put("entry", body);
        Request request = entry.create(entryCreate).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries", request.url().toString());
    }

    @Order(4)
    @Test
    void testUpdate() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryUpdate = new JSONObject();
        entryUpdate.put("entry", body);

        entry = contentType.entry(apiKey);
        Request request = entry.update(entryUpdate).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + TestClient.API_KEY, request.url().toString());
    }

    @Order(5)
    @Test
    void testAtomicOperation() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(apiKey);
        Request request = entry.atomicOperation(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + TestClient.API_KEY, request.url().toString());
    }

    @Order(6)
    @Test
    void testEntryDelete() {
        entry = contentType.entry(apiKey);
        Request request = entry.delete().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/blta6f1fdf46fe97007", request.url().toString());
    }

    @Order(7)
    @Test
    void testEntryVersionName() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(apiKey);
        Request request = entry.versionName(1, entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(8, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + TestClient.API_KEY + "/versions/1/name", request.url().toString());
    }

    @Order(8)
    @Test
    void testEntryDetailOfAllVersion() {

        entry = contentType.entry(apiKey);
        Request request = entry.detailOfAllVersion().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + apiKey + "/versions", request.url().toString());
    }

    @Order(9)
    @Test
    void testEntryDeleteVersionName() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(apiKey);
        Request request = entry.deleteVersionName(1, entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(8, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + apiKey + "/versions/1/name", request.url().toString());
    }

    @Order(10)
    @Test
    void testEntryGetReference() {

        entry = contentType.entry(apiKey);
        Request request = entry.getReference().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + apiKey + "/references", request.url().toString());
    }

    @Order(11)
    @Test
    void testEntryLocalise() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(apiKey);
        Request request = entry.localize(entryBody, "en-us").request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + apiKey + "?locale=en-us", request.url().toString());
    }

    @Order(12)
    @Test
    void testEntryExport() {
        entry = contentType.entry(apiKey);
        Request request = entry.export().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/blta6f1fdf46fe97007/export", request.url().toString());
    }

    @Order(13)
    @Test
    void testEntryImports() {
        Request request = entry.imports().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/import", request.url().toString());
    }

    @Order(14)
    @Test
    void testEntryImportExisting() {

        entry = contentType.entry(apiKey);
        Request request = entry.importExisting().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + apiKey + "/import", request.url().toString());
    }

    @Order(15)
    @Test
    void testEntryPublish() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(apiKey);
        Request request = entry.publish(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + apiKey + "/publish", request.url().toString());
    }

    @Order(16)
    @Test
    void testEntryPublishWithReference() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Request request = entry.publishWithReference(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("x-bulk-action=publish", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/bulk/publish?x-bulk-action=publish", request.url().toString());
    }

    @Order(17)
    @Test
    void testPublishWithReference() {
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(apiKey);
        Request request = entry.unpublish(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/test/entries/" + apiKey + "/unpublish", request.url().toString());
    }

}
