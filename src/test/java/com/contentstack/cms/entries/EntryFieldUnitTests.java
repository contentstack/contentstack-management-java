package com.contentstack.cms.entries;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONArray;
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
    protected static String _uid = Dotenv.load().get("auth_token");
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
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testCreateEntryHeaderNames() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
        Assertions.assertTrue(resp.headers().names().containsAll(matcher));
    }

    @Test
    void testCreateEntryMethod() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
        Assertions.assertEquals("POST", resp.method());
    }

    @Test
    void testCreateEntryCompleteUrl() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.create(new JSONObject(), queryParams).request();
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
        Request resp = entryInstance.update("entryUid", new JSONObject(), queryParams).request();
        Assertions.assertEquals("PUT", resp.method());
    }

    @Test
    void testUpdateEntryHeaderSize() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update("entryUid", new JSONObject(), queryParams).request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testUpdateEntryHeaders() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update("entryUid", new JSONObject(), queryParams).request();
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
        Request resp = entryInstance.update(_uid, new JSONObject(), queryParams).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid, resp.url().encodedPath());
    }

    @Test
    void testUpdateEntryHost() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(_uid, new JSONObject(), queryParams).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testUpdateEntryQuery() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(_uid, new JSONObject(), queryParams).request();
        Assertions.assertEquals("locale=en-us", resp.url().query());
    }

    @Test
    void testUpdateEntryRequestBody() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(_uid, updateRequestBody(), queryParams).request();
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void testUpdateEntryCompleteUrl() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("locale", "en-us");
        Request resp = entryInstance.update(_uid, new JSONObject(), queryParams).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/content_types/product/entries/" + _uid + "?locale=en-us",
                resp.url().toString());
    }

    // Atomic Operation

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
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Assertions.assertEquals("PUT", resp.method());
    }

    @Test
    void testAtomicOperationHeaderSize() {
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testAtomicOperationHeaders() {
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
    }

    @Test
    void testAtomicOperationEncodedPath() {
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid, resp.url().encodedPath());
    }

    @Test
    void testAtomicOperationHost() {
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testAtomicOperationQuery() {
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Assertions.assertNull(resp.url().query());
    }

    @Test
    void testAtomicOperationRequestBody() {
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void testAtomicOperationCompleteUrl() {
        Request resp = entryInstance.atomicOperation(_uid, atomicRequestBody()).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid,
                resp.url().toString());
    }

    // Delete Entry

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

    JSONObject deleteRequestBody = deleteRequestBody();

    @Test
    void testDeleteMethod() {
        Request resp = entryInstance.delete(_uid, deleteRequestBody).request();
        Assertions.assertEquals("DELETE", resp.method());
    }

    @Test
    void testDeleteHeaderSize() {
        Request resp = entryInstance.delete(_uid, deleteRequestBody).request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testDeleteHeaders() {
        Request resp = entryInstance.delete(_uid, deleteRequestBody).request();
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
    }

    @Test
    void testDeleteEncodedPath() {
        Request resp = entryInstance.delete(_uid, deleteRequestBody).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid, resp.url().encodedPath());
    }

    @Test
    void testDeleteHost() {
        Request resp = entryInstance.delete(_uid, deleteRequestBody).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testDeleteQuery() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        query.put("delete_all_localized", true);
        Request resp = entryInstance.delete(_uid, deleteRequestBody(), query).request();
        Assertions.assertEquals("delete_all_localized=true&locale=en-us", resp.url().query());
    }

    @Test
    void testDeleteRequestBody() {
        Request resp = entryInstance.delete(_uid, deleteRequestBody()).request();
        Assertions.assertNotNull(resp.body());
    }

    // versionName request body tests

    JSONObject versionNameRequestBody() {
        JSONObject requestBody = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("_version_name", "Test version");
        content.put("locale", "fr-fr");
        content.put("force", true);
        requestBody.put("entry", content);
        return requestBody;
    }

    JSONObject _verRequestBody = versionNameRequestBody();

    @Test
    void testVersionNameMethod() {
        Request resp = entryInstance.versionName(_uid, 1, _verRequestBody).request();
        Assertions.assertEquals("POST", resp.method());
    }

    @Test
    void testVersionNameHeaderSize() {
        Request resp = entryInstance.versionName(_uid, 1, _verRequestBody).request();
        Assertions.assertEquals(2, resp.headers().size());
    }

    @Test
    void testVersionNameHeaders() {
        Request resp = entryInstance.versionName(_uid, 1, _verRequestBody).request();
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
    }

    @Test
    void testVersionNameEncodedPath() {
        Request resp = entryInstance.versionName(_uid, 1, _verRequestBody).request();
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid + "/versions/1/name",
                resp.url().encodedPath());
    }

    @Test
    void testVersionNameHost() {
        Request resp = entryInstance.versionName(_uid, 1, _verRequestBody).request();
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
    }

    @Test
    void testVersionNameQuery() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        query.put("delete_all_localized", true);
        Request resp = entryInstance.delete(_uid, deleteRequestBody(), query).request();
        Assertions.assertEquals("delete_all_localized=true&locale=en-us", resp.url().query());
    }

    @Test
    void testVersionNameRequestBody() {
        Request resp = entryInstance.versionName(_uid, 1, _verRequestBody).request();
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void testVersionNameCompleteUrl() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        query.put("delete_all_localized", true);
        Request resp = entryInstance.delete(_uid, deleteRequestBody(), query).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "?delete_all_localized=true&locale=en-us", resp.url().toString());
    }

    @Test
    void testDeleteVersionName() {
        Request resp = entryInstance.deleteVersionName(_uid, 1, new JSONObject()).request();

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
        HashMap<String, Object> query = new HashMap<>();
        // skip=1&limit=5&named=false&include_count=false&locale=en-us
        query.put("skip", 1);
        query.put("limit", 5);
        query.put("named", false);
        query.put("include_count", false);
        query.put("locale", "en-us");
        Request resp = entryInstance.detailOfAllVersion(_uid, query).request();

        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(3, resp.headers().size());
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

        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        Request resp = entryInstance.getReference(_uid, query).request();

        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(3, resp.headers().size());
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

        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        Request resp = entryInstance.getLanguage(_uid, query).request();

        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(3, resp.headers().size());
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
        Request resp = entryInstance.localize(_uid, requestBody, "fr-fr").request();

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

        Request resp = entryInstance.unLocalize(_uid, "fr-fr").request();
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertTrue(contains);
        Assertions.assertEquals("/v3/content_types/product/entries/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("unlocalize=fr-fr", resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals("https://api.contentstack.io/v3/content_types/product/entries/" + _uid
                + "?unlocalize=fr-fr", resp.url().toString());
    }

    @Test
    void testExport() {

        // HashMap<String, Object> mapRequest = new HashMap<>();
        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        Request resp = entryInstance.export(_uid, query).request();

        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(3, resp.headers().size());
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

        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        query.put("overwrite", false);
        Request resp = entryInstance.imports(query).request();

        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
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
        HashMap<String, Object> query = new HashMap<>();
        query.put("locale", "en-us");
        query.put("overwrite", false);
        Request resp = entryInstance.importExisting(_uid, query).request();
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
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

        Request resp = entryInstance.publish(_uid, requestBody).request();
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
        HashMap<String, Object> query = new HashMap<>();
        query.put("approvals", true);
        query.put("skip_workflow_stage_check", true);

        // Publish With Reference
        Request resp = entryInstance.publishWithReference(requestBody, query).request(); // sending request body and
                                                                                         // query parameter
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
        Request resp = entryInstance.unpublish(_uid, requestBody).request(); // sending request body and query parameter
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(2, resp.headers().size());
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
    }

}
