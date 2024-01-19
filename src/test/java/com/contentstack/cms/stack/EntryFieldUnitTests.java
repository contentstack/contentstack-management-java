package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntryFieldUnitTests {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.AUTHTOKEN;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static String contentType = "product";
    static Entry entryInstance;
    JSONObject deleteRequestBody = deleteRequestBody();
    JSONObject _verRequestBody = versionNameRequestBody();

    @BeforeAll
    static void setup() {

        HashMap<String, Object> headers = new HashMap<>();
        headers.put("authorization", MANAGEMENT_TOKEN);
        headers.put("api_key", API_KEY);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        entryInstance = stack.contentType(contentType).entry(_uid);
    }

    @Test
    void testEntryFetchIsHttps() {
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("include_workflow", false);
        entryInstance.addParam("include_publish_details", true);
        Request resp = entryInstance.find().request();
        Assertions.assertTrue(resp.isHttps());
    }

    @Test
    void testEntryFetchQuery() {
        entryInstance.clearParams();
        entryInstance.addParam("include_publish_details", true);
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("include_workflow", false);
        Request resp = entryInstance.find().request();
        Assertions.assertEquals("include_publish_details=true&locale=en-us&include_workflow=false", resp.url().query());
    }

    @Test
    void testEntryFetchEncodedPath() {
        Request resp = entryInstance.find().request();
        Assertions.assertEquals("/v3/content_types/product/entries", resp.url().encodedPath());
    }

    @Test
    void testEntryFetchHeaders() {
        Request resp = entryInstance.find().request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    // --------------------[Single]----------------

    @Test
    void testEntryFetchHeaderNames() {
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        matcher.add("Content-Type");
        Request resp = entryInstance.find().request();
        Assertions.assertFalse(resp.headers().names().containsAll(matcher));
    }

    @Test
    void testEntryFetchMethod() {
        Request resp = entryInstance.find().request();
        Assertions.assertEquals("GET", resp.method());
    }

    @Test
    void testSingleEntryQuery() {
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("include_workflow", false);
        entryInstance.addParam("include_publish_details", true);
        Request resp = entryInstance.fetch().request();
        Assertions.assertEquals("include_publish_details=true&locale=en-us&include_workflow=false", resp.url().query());
    }

    @Test
    void testSingleEntryEncodedPath() {
        Request resp = entryInstance.fetch().request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid, resp.url().encodedPath());
    }

    @Test
    void testSingleEntryHeaders() {
        Request resp = entryInstance.fetch().request();
        Assertions.assertEquals(3, resp.headers().size());
    }

    @Test
    void testSingleEntryHeaderNames() {
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        matcher.add("Content-Type");
        Request resp = entryInstance.fetch().request();
        Assertions.assertTrue(resp.headers().names().containsAll(matcher));
    }

    // ------------------------[Create]------------------------------

    @Test
    void testSingleEntryMethod() {
        Request resp = entryInstance.fetch().request();
        Assertions.assertEquals("GET", resp.method());
    }

    @Test
    void testSingleEntryCompleteUrl() {
        entryInstance.clearParams();
        Request resp = entryInstance.fetch().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid,
                resp.url().toString());
    }

    @Test
    void testCreateEntryQueryRespNull() {
        Request resp = entryInstance.create(new JSONObject()).request();
        Assertions.assertNull(resp.url().query());
    }

    @Test
    void testCreateEntryQuery() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");

        Request resp = entryInstance.create(new JSONObject()).request();
        Assertions.assertEquals("locale=en-us", resp.url().query());
    }

    @Test
    void testCreateEntryEncodedPath() {
        Request resp = entryInstance.create(new JSONObject()).request();
        Assertions.assertEquals("/v3/content_types/product/entries", resp.url().encodedPath());
    }

    @Test
    void testCreateEntryHeaders() {
        Request resp = entryInstance.create(new JSONObject()).request();
        Assertions.assertEquals(3, resp.headers().size());
    }

    @Test
    void testCreateEntryHeaderNames() {
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        Request resp = entryInstance.create(new JSONObject()).request();
        Assertions.assertTrue(resp.headers().names().containsAll(matcher));
    }

    @Test
    void testCreateEntryMethod() {
        Request resp = entryInstance.create(new JSONObject()).request();
        Assertions.assertEquals("POST", resp.method());
    }

    @Test
    void testCreateEntryCompleteUrl() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject()).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries?locale=en-us",
                resp.url().toString());
    }

    // ------Update Entry
    JSONObject updateRequestBody() {
        JSONObject requestBody = new JSONObject();
        JSONObject entryObj = new JSONObject();
        entryObj.put("title", "example");
        entryObj.put("url", "/example");
        requestBody.put("entry", entryObj);
        return requestBody;
    }

    @Test
    void testUpdateEntryMethod() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(new JSONObject()).request();
        Assertions.assertEquals("PUT", resp.method());
    }

    @Test
    void testUpdateEntryHeaderSize() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(new JSONObject()).request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testUpdateEntryHeaders() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(new JSONObject()).request();
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
    }

    @Test
    void testUpdateEntryEncodedPath() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(new JSONObject()).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid, resp.url().encodedPath());
    }

    @Test
    void testUpdateEntryHost() {
        Request resp = entryInstance.update(new JSONObject()).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testUpdateEntryQuery() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        Request resp = entryInstance.update(new JSONObject()).request();
        Assertions.assertEquals("locale=en-us", resp.url().query());
    }

    // Atomic Operation

    @Test
    void testUpdateEntryRequestBody() {
        Request resp = entryInstance.update(updateRequestBody()).request();
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void testUpdateEntryCompleteUrl() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        Request resp = entryInstance.update(new JSONObject()).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types/product/entries/" + _uid + "?locale=en-us",
                resp.url().toString());
    }

    JSONObject atomicRequestBody() {
        JSONObject requestBody = new JSONObject();
        JSONObject multipleContent = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("2");
        array.add("3");
        JSONObject pushObj = new JSONObject();
        JSONObject dataArray = new JSONObject();
        dataArray.put("data", array);
        pushObj.put("PUSH", dataArray);
        multipleContent.put("multiple_number", pushObj);
        requestBody.put("entry", multipleContent);
        return requestBody;
    }

    @Test
    void testAtomicOperationMethod() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Assertions.assertEquals("PUT", resp.method());
    }

    @Test
    void testAtomicOperationHeaderSize() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Assertions.assertEquals(3, resp.headers().size());
    }

    @Test
    void testAtomicOperationHeaders() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
    }

    @Test
    void testAtomicOperationEncodedPath() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid, resp.url().encodedPath());
    }

    @Test
    void testAtomicOperationHost() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testAtomicOperationQuery() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Assertions.assertNull(resp.url().query());
    }

    // Delete Entry

    @Test
    void testAtomicOperationRequestBody() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void testAtomicOperationCompleteUrl() {
        Request resp = entryInstance.atomicOperation(atomicRequestBody()).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid,
                resp.url().toString());
    }

    JSONObject deleteRequestBody() {
        JSONObject requestBody = new JSONObject();
        JSONObject multipleContent = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("hi-in");
        array.add("mr-in");
        array.add("es-eu");
        multipleContent.put("locale", array);
        requestBody.put("entry", multipleContent);
        return requestBody;
    }

    @Test
    void testDeleteMethod() {
        Request resp = entryInstance.delete(deleteRequestBody).request();
        Assertions.assertEquals("DELETE", resp.method());
    }

    @Test
    void testDeleteHeaderSize() {
        Request resp = entryInstance.delete(deleteRequestBody).request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testDeleteHeaders() {
        Request resp = entryInstance.delete(deleteRequestBody).request();
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
    }

    @Test
    void testDeleteEncodedPath() {
        Request resp = entryInstance.delete(deleteRequestBody).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid, resp.url().encodedPath());
    }

    @Test
    void testDeleteHost() {
        Request resp = entryInstance.delete(deleteRequestBody).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testDeleteQuery() {
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("delete_all_localized", true);
        Request resp = entryInstance.delete().request();
        Assertions.assertEquals("delete_all_localized=true&locale=en-us", resp.url().query());
    }

    // versionName request body tests

    @Test
    void testDeleteRequestBody() {
        Request resp = entryInstance.delete(deleteRequestBody()).request();
        Assertions.assertNotNull(resp.body());
    }

    JSONObject versionNameRequestBody() {
        JSONObject requestBody = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("_version_name", "Test version");
        content.put("locale", "fr-fr");
        content.put("force", true);
        requestBody.put("entry", content);
        return requestBody;
    }

    @Test
    void testVersionNameMethod() {
        Request resp = entryInstance.versionName(1, _verRequestBody).request();
        Assertions.assertEquals("POST", resp.method());
    }

    @Test
    void testVersionNameHeaderSize() {
        Request resp = entryInstance.versionName(1, _verRequestBody).request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testVersionNameHeaders() {
        Request resp = entryInstance.versionName(1, _verRequestBody).request();
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
    }

    @Test
    void testVersionNameEncodedPath() {
        Request resp = entryInstance.versionName(1, _verRequestBody).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/versions/1/name",
                resp.url().encodedPath());
    }

    @Test
    void testVersionNameHost() {
        Request resp = entryInstance.versionName(1, _verRequestBody).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testVersionNameQuery() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("delete_all_localized", true);
        Request resp = entryInstance.delete().request();
        Assertions.assertEquals("delete_all_localized=true&locale=en-us", resp.url().query());
    }

    @Test
    void testVersionNameRequestBody() {
        Request resp = entryInstance.versionName(1, _verRequestBody).request();
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void testVersionNameCompleteUrl() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("delete_all_localized", true);
        Request resp = entryInstance.delete().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "?delete_all_localized=true&locale=en-us", resp.url().toString());
    }

    @Test
    void testDeleteVersionName() {
        Request resp = entryInstance.deleteVersionName(1, new JSONObject()).request();

        Assertions.assertEquals("DELETE", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/versions/1/name",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "/versions/1/name", resp.url().toString());
    }

    @Test
    void testDetailVersionName() {

        // skip=1&limit=5&named=false&include_count=false&locale=en-us
        entryInstance.clearParams();
        entryInstance.addParam("skip", 1);
        entryInstance.addParam("limit", 5);
        entryInstance.addParam("named", false);
        entryInstance.addParam("include_count", false);
        entryInstance.addParam("locale", "en-us");
        Request resp = entryInstance.detailOfAllVersion().request();

        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/versions",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("named=false&limit=5&skip=1&include_count=false&locale=en-us", resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                        + "/versions?named=false&limit=5&skip=1&include_count=false&locale=en-us",
                resp.url().toString());
    }

    // Get Reference tests

    @Test
    void testGetReference() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        Request resp = entryInstance.getReference().request();

        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/references",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("locale=en-us", resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "/references?locale=en-us", resp.url().toString());
    }

    @Test
    void testGetLanguages() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        Request resp = entryInstance.getLanguage().request();
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/locales",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("locale=en-us", resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "/locales?locale=en-us", resp.url().toString());
    }

    @Test
    void testGetLocalise() {

        HashMap<String, Object> mapRequest = new HashMap<>();
        HashMap<String, Object> content = new HashMap<>();
        content.put("title", "example");
        content.put("url", "/example");
        content.put("tag", new ArrayList<>().add("something else"));
        content.put("uid", _uid);
        content.put("created_by", _uid);
        mapRequest.put("entry", content);

        JSONObject requestBody = new JSONObject(mapRequest);
        Request resp = entryInstance.localize(requestBody, "fr-fr").request();

        Assertions.assertEquals("PUT", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("locale=fr-fr", resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "?locale=fr-fr", resp.url().toString());
    }

    @Test
    void testGetUnLocalise() {

        Request resp = entryInstance.unLocalize("fr-fr").request();
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/unlocalize",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("locale=fr-fr", resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "/unlocalize?locale=fr-fr", resp.url().toString());
    }

    @Test
    void testExport() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        Request resp = entryInstance.export().request();
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/export",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("locale=en-us", resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "/export?locale=en-us", resp.url().toString());
    }

    @Test
    void testImport() {
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("overwrite", false);
        Request resp = entryInstance.imports().request();
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(3, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/import",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("locale=en-us&overwrite=false", resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/import"
                + "?locale=en-us&overwrite=false", resp.url().toString());
    }

    @Test
    void testImportWithUid() {
        entryInstance.clearParams();
        entryInstance.addParam("locale", "en-us");
        entryInstance.addParam("overwrite", false);
        Request resp = entryInstance.importExisting().request();
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(3, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/import",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("locale=en-us&overwrite=false", resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "/import?locale=en-us&overwrite=false", resp.url().toString());
    }

    @Test
    void testPublish() {
        JSONObject requestBody = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("environments", new ArrayList<String>().add("development"));
        content.put("locales", new ArrayList<String>().add("en-us"));
        requestBody.put("entry", content);
        requestBody.put("locale", "en-us");
        requestBody.put("version", 1);
        requestBody.put("scheduled_at", "2019-02-14T18:30:00.000Z");

        Request resp = entryInstance.publish(requestBody).request();
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/publish",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        // Assertions.assertEquals("locale=en-us&overwrite=false", resp.url().query());
        Assertions.assertNull(resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "/publish", resp.url().toString());
    }

    @Test
    void testPublishWithReference() {
        // Request Body
        JSONObject requestBody = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("environments", new ArrayList<String>().add("{environment_uid}"));
        content.put("locales", new ArrayList<String>().add("{publish_locale}"));
        requestBody.put("entry", content);
        requestBody.put("skip_workflow_stage_check", 1);
        requestBody.put("publish_with_reference", "2019-02-14T18:30:00.000Z");

        // Query Params
        entryInstance.clearParams();
        entryInstance.addParam("approvals", true);
        entryInstance.addParam("skip_workflow_stage_check", true);

        // Publish With Reference
        Request resp = entryInstance.publishWithReference(requestBody).request(); // sending request body and
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/bulk/publish",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("x-bulk-action=publish&skip_workflow_stage_check=true&approvals=true",
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/bulk/publish?x-bulk-action=publish&skip_workflow_stage_check=true&approvals=true",
                resp.url().toString());
    }

    @Test
    void testUnpublish() {
        // Request Body
        JSONObject requestBody = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("environments", new ArrayList<String>().add("{environment_uid}"));
        content.put("locales", new ArrayList<String>().add("{publish_locale}"));
        requestBody.put("entry", content);
        requestBody.put("skip_workflow_stage_check", 1);
        requestBody.put("publish_with_reference", "2019-02-14T18:30:00.000Z");

        // Unpublished Reference
        Request resp = entryInstance.unpublish(requestBody).request(); // sending request body and query parameter
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(3, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/unpublish",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        // Assertions.assertEquals("locale=en-us&overwrite=false", resp.url().query());
        Assertions.assertNull(resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid + "/unpublish",
                resp.url().toString());
        entryInstance.removeParam("locale");
    }


    @Test
    void testHeader() {
        Request resp = entryInstance
        .addHeader("API_KEY", "API_VALUE")
        .versionName(1, _verRequestBody)
        .request();
        Assertions.assertEquals(3, resp.headers().size());
    }

    @Test
    void queryFiltersOnTaxonomy() {
        JSONObject query = new JSONObject();
        query.put("taxonomies.taxonomy_uid", "{ \"$in\" : [\"term_uid1\" , \"term_uid2\" ] }");
        Request request = entryInstance.query(query).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("product", request.url().pathSegments().get(2));
        Assertions.assertEquals("entries", request.url().pathSegments().get(3));
        Assertions.assertNull(request.body());
        Assertions.assertEquals("query={\"taxonomies.taxonomy_uid\":\"{ \\\"$in\\\" : [\\\"term_uid1\\\" , \\\"term_uid2\\\" ] }\"}", request.url().query());

    }

}
