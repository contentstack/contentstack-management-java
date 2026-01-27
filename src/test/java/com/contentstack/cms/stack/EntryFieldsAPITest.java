package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;

import okhttp3.Request;
import okhttp3.ResponseBody;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.*;

import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntryFieldsAPITest {

    private static Entry entry;
    private static int _COUNT = 2;
    private static String API_KEY = TestClient.API_KEY;
    private static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    private static ContentType contentType = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN)
            .contentType("test");

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
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(2)
    @Test
    void testEntryFetch() {
        entry = contentType.entry(API_KEY);
        Request request = entry.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(3)
    @Test
    void testEntryCreate() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryCreate = new JSONObject();
        entryCreate.put("entry", body);
        Request request = entry.create(entryCreate).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(4)
    @Test
    void testUpdate() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryUpdate = new JSONObject();
        entryUpdate.put("entry", body);

        entry = contentType.entry(API_KEY);
        Request request = entry.update(entryUpdate).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(5)
    @Test
    void testAtomicOperation() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(API_KEY);
        Request request = entry.atomicOperation(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(6)
    @Test
    void testEntryDelete() {
        entry = contentType.entry(API_KEY);
        Request request = entry.delete().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(7)
    @Test
    void testEntryVersionName() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(API_KEY);
        Request request = entry.versionName(1, entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(8, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(8)
    @Test
    void testEntryDetailOfAllVersion() {

        entry = contentType.entry(API_KEY);
        Request request = entry.detailOfAllVersion().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(9)
    @Test
    void testEntryDeleteVersionName() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(API_KEY);
        Request request = entry.deleteVersionName(1, entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(8, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(10)
    @Test
    void testEntryGetReference() {

        entry = contentType.entry(API_KEY);
        Request request = entry.getReference().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(11)
    @Test
    void testEntryLocalise() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(API_KEY);
        Request request = entry.localize(entryBody, "en-us").request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Order(12)
    @Test
    void testEntryExport() {
        entry = contentType.entry(API_KEY);
        Request request = entry.export().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(13)
    @Test
    void testEntryImports() {
        Request request = entry.imports().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(14)
    @Test
    void testEntryImportExisting() {

        entry = contentType.entry(API_KEY);
        Request request = entry.importExisting().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(15)
    @Test
    void testEntryPublish() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(API_KEY);
        Request request = entry.publish(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(16)
    @Test
    void testEntryPublishWithReference() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);
        Request request = entry.publishWithReference(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("bulk", request.url().pathSegments().get(1));
        Assertions.assertEquals("x-bulk-action=publish", request.url().encodedQuery());    }

    @Order(17)
    @Test
    void testPublishWithReference() {
        @SuppressWarnings("unchecked")
        JSONObject body = new JSONObject();
        body.put("title", "The Create an entry call creates a new entry for the selected content type for testing");
        body.put("url", "www.***REMOVED***.in/stack/content_type/entry/fakeuid/code");
        @SuppressWarnings("unchecked")
        JSONObject entryBody = new JSONObject();
        entryBody.put("entry", body);

        entry = contentType.entry(API_KEY);
        Request request = entry.unpublish(entryBody).request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Order(18)
    @Test
    public void testEntryQuery() {
        @SuppressWarnings("unchecked")
        JSONObject query = new JSONObject();
        query.put("taxonomies.taxonomy_uid", "{ \"$in\" : [\"term_uid1\" , \"term_uid2\" ] }");
        Request request = entry.query(query).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("test", request.url().pathSegments().get(2));
        Assertions.assertEquals("entries", request.url().pathSegments().get(3));
        Assertions.assertNull(request.body());
        Assertions.assertEquals("query={\"taxonomies.taxonomy_uid\":\"{ \\\"$in\\\" : [\\\"term_uid1\\\" , \\\"term_uid2\\\" ] }\"}", request.url().query());

    }

    @Order(19)
    @Test
    void testSetWorkflowStage() throws ParseException, IOException {
        String workflowStagePayload = "{\n" +
            "  \"workflow\": {\n" +
            "    \"workflow_stage\": {\n" +
            "      \"uid\": \"uid\",\n" +
            "      \"assigned_by_roles\": [{ \"uid\": \"uid\", \"name\": \"Content Manager\" }],\n" +
            "      \"due_date\": \"Thu Feb 06 2025\",\n" +
            "      \"comment\": \"Review the entry\",\n" +
            "      \"notify\": true,\n" +
            "      \"assigned_to\": [{ \"uid\": \"user_uid\", \"name\": \"name\", \"email\": \"mail.com\" }]\n" +
            "    }\n" +
            "  }\n" +
            "}";
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(workflowStagePayload);
        Entry entry1 = TestClient.getClient().stack(API_KEY,MANAGEMENT_TOKEN).contentType("author").entry("entry_uid");
        Request request = entry1.setWorkflowStage(body).request();
        Assertions.assertNotNull(request);
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("author", request.url().pathSegments().get(2));
        Assertions.assertEquals("entries", request.url().pathSegments().get(3));
        Assertions.assertEquals("entry_uid", request.url().pathSegments().get(4));
        Assertions.assertEquals("workflow", request.url().pathSegments().get(5));    }

    @Order(20)
    @Test
    void testSetWorkflowStageWithMissingFields() throws ParseException, IOException {
        String invalidWorkflowStage = "{\n\t\"workflow\": {\n\t\t\"workflow_stage\": {\n\t\t\t\"comment\": \"Review the entry\",\n\t\t\t\"notify\": true\n\t\t}\n\t}\n}";
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(invalidWorkflowStage);
        Entry entry = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).contentType("author").entry("entry_uid");
        Request request = entry.setWorkflowStage(body).request();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().toString().contains("workflow"));
        Assertions.assertFalse(((JSONObject) body.get("workflow")).get("workflow_stage") instanceof JSONObject && ((JSONObject) ((JSONObject) body.get("workflow")).get("workflow_stage")).containsKey("due_date"));
        Assertions.assertFalse(((JSONObject) body.get("workflow")).get("workflow_stage") instanceof JSONObject && ((JSONObject) ((JSONObject) body.get("workflow")).get("workflow_stage")).containsKey("assigned_to"));
    }

    @Order(21)
    @Test
    void setWorkflowStage_VerifyPayloadContent() throws ParseException {
        String payload = "{ \"workflow\": { \"workflow_stage\": { \"uid\": \"stage_uid\", \"comment\": \"Approve\", \"notify\": true, \"assigned_to\": [{ \"uid\": \"user1\", \"name\": \"User One\", \"email\": \"user1@example.com\" }] } } }";
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(payload);
        Entry entry = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).contentType("author").entry("entry_uid");
        Request request = entry.setWorkflowStage(body).request();
    
        String requestBodyString = body.toJSONString();
        JSONObject requestBodyJson = (JSONObject) parser.parse(requestBodyString);
        JSONObject workflowStage = (JSONObject) ((JSONObject) requestBodyJson.get("workflow")).get("workflow_stage");
        Assertions.assertEquals("stage_uid", (String) workflowStage.get("uid"));
        Assertions.assertEquals("Approve", (String) workflowStage.get("comment"));
        Assertions.assertTrue((Boolean) workflowStage.get("notify"));

        JSONArray assignedTo = (JSONArray) workflowStage.get("assigned_to");
        Assertions.assertEquals(1, assignedTo.size());
        JSONObject assignedToUser = (JSONObject) assignedTo.get(0);
        Assertions.assertEquals("user1", assignedToUser.get("uid"));
        Assertions.assertEquals("User One", assignedToUser.get("name"));
        Assertions.assertEquals("user1@example.com", assignedToUser.get("email"));
    }

    @Order(22)
    @Test
    void setWorkflowStage_VerifyQueryParams() throws ParseException {
        String payload = "{ \"workflow\": { \"workflow_stage\": { \"uid\": \"stage_uid\" } } }";
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(payload);
        Entry entry = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).contentType("author").entry("entry_uid");
        entry.addParam("locale", "en-us");
        Request request = entry.setWorkflowStage(body).request();
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("locale=en-us", request.url().encodedQuery());    }

    @Order(23)
    @Test
    void testPublishRequest_ValidRequest() throws ParseException {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = contentstack.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN);
        String publishRequestPayload = "{\n" +
                "    \"workflow\": {\n" +
                "        \"publishing_rule\": {\n" +
                "            \"uid\": \"rule_uid\",\n" +
                "            \"action\": \"publish\",\n" +
                "            \"status\": 1,\n" +
                "            \"notify\": true,\n" +
                "            \"comment\": \"Approve this entry.\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(publishRequestPayload);
        Request request = stack.contentType("author").entry("entry_uid").publishRequest(body).request();

        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("author", request.url().pathSegments().get(2));
        Assertions.assertEquals("entries", request.url().pathSegments().get(3));
        Assertions.assertEquals("entry_uid", request.url().pathSegments().get(4));
        Assertions.assertEquals("workflow", request.url().pathSegments().get(5));
        Assertions.assertTrue(request.headers().names().contains("authorization"));
        Assertions.assertTrue(request.headers().names().contains("api_key"));
    }

    @Order(24)
    @Test
    void testPublishRequest_InvalidRequestBody() throws ParseException, IOException {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = contentstack.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN);
        String invalidPayload = "{ \"invalid_field\": \"invalid_value\" }";
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(invalidPayload);
        Request request = stack.contentType("author").entry("entry_uid").publishRequest(body).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.headers().names().contains("authorization"));
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
    }

    @Order(25)
    @Test
    void testPublishRequest_RejectRequest() throws ParseException {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = contentstack.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN);
        String rejectRequestPayload = "{\n" +
                "    \"workflow\": {\n" +
                "        \"publishing_rule\": {\n" +
                "            \"uid\": \"rule_uid\",\n" +
                "            \"action\": \"unpublish\",\n" +
                "            \"status\": -1,\n" +
                "            \"notify\": false,\n" +
                "            \"comment\": \"Rejected due to incomplete information.\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(rejectRequestPayload);
        Request request = stack.contentType("author").entry("entry_uid").publishRequest(body).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().toString().contains("/workflow"));
        Assertions.assertTrue(request.headers().names().contains("authorization"));
    }

    @Test
    void testEntryPojo() throws IOException {
        Request request = contentType.entry(API_KEY).fetchAsPojo().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("test", request.url().pathSegments().get(2));
        Assertions.assertEquals("entries", request.url().pathSegments().get(3));
        Assertions.assertNull(request.url().encodedQuery());
    }
    @Test
    void testEntryPojoList() throws IOException {
        entry.addParam("include_count", true);
        entry.addParam("limit", 10);
        Request request = entry.findAsPojo().request();
        Assertions.assertEquals(_COUNT, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("content_types", request.url().pathSegments().get(1));
        Assertions.assertEquals("test", request.url().pathSegments().get(2));
        Assertions.assertEquals("entries", request.url().pathSegments().get(3));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("limit=10&include_count=true", request.url().query().toString());
    }

}
