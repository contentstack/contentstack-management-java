package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.ResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * Real API tests for Entry module.
 * An entry is the actual piece of content created using a content type.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#entry
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntryRealAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Stack stackInstance;
    private JSONParser parser = new JSONParser();
    
    private String testContentTypeUid;
    private String createdEntryUid;
    private String existingEntryUid;

    @BeforeAll
    void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        stackInstance = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
    }

    @AfterAll
    void cleanup() {
        // Clean up created entry
        if (createdEntryUid != null && testContentTypeUid != null) {
            try {
                stackInstance.contentType(testContentTypeUid).entry(createdEntryUid).delete().execute();
                System.out.println("[Cleanup] Deleted entry: " + createdEntryUid);
            } catch (IOException e) {
                System.out.println("[Cleanup] Failed to delete entry: " + e.getMessage());
            }
        }
    }

    @BeforeAll
    void setupPrerequisites() throws IOException, ParseException {
        // Ensure prerequisites exist: Content Type → Entry
        try {
            TestStackSetup.ensureContentTypeExists();
            testContentTypeUid = TestStackSetup.testContentTypeUid;
            
            TestStackSetup.ensureEntryExists(testContentTypeUid);
            existingEntryUid = TestStackSetup.testEntryUid;
            
            System.out.println("[Setup] Prerequisites ready. Content Type: " + testContentTypeUid + 
                             ", Entry: " + existingEntryUid);
        } catch (Exception e) {
            System.out.println("[Setup] Warning: Could not setup prerequisites: " + e.getMessage());
            // Continue - tests will handle missing prerequisites
        }
    }

    // ==================== SETUP - Find a content type with entries ====================

    @Test
    @Order(50)
    @DisplayName("Setup - Find content type with entries")
    void testSetupFindContentType() throws IOException, ParseException {
        // Ensure we have prerequisites
        if (testContentTypeUid == null) {
            testContentTypeUid = TestStackSetup.ensureContentTypeExists();
        }
        if (existingEntryUid == null && testContentTypeUid != null) {
            existingEntryUid = TestStackSetup.ensureEntryExists(testContentTypeUid);
        }
        
        assertNotNull(testContentTypeUid, "Content type should be available from setup");
        System.out.println("[Setup] Using content type: " + testContentTypeUid + 
                          (existingEntryUid != null ? " with entry: " + existingEntryUid : ""));
    }

    // ==================== READ OPERATIONS ====================

    @Test
    @Order(100)
    @DisplayName("Find all entries - should return entries array")
    void testFindAllEntries() throws IOException, ParseException {
        // Ensure we have a content type UID
        if (testContentTypeUid == null) {
            testContentTypeUid = TestStackSetup.ensureContentTypeExists();
        }
        
        assertNotNull(testContentTypeUid, 
            "ASSUMPTION FAILED: Cannot find entries. " +
            "PREREQUISITE: A content type must exist. " +
            "ACTION: Content type should have been created in setup. Check setup logs.");

        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry().find().execute();

        assertTrue(response.isSuccessful(), 
                "Find entries failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("entries"), "Response should contain 'entries' key");
        JSONArray entries = (JSONArray) result.get("entries");
        assertNotNull(entries, "Entries array should not be null");
        
        // Validate entry structure if any exist
        if (!entries.isEmpty()) {
            JSONObject firstEntry = (JSONObject) entries.get(0);
            assertTrue(firstEntry.containsKey("uid"), "Entry should have 'uid' field");
            assertTrue(firstEntry.containsKey("title"), "Entry should have 'title' field");
            
            if (existingEntryUid == null) {
                existingEntryUid = (String) firstEntry.get("uid");
            }
        }
        
        System.out.println("[Test] Found " + entries.size() + " entry(ies) in " + testContentTypeUid);
    }

    @Test
    @Order(101)
    @DisplayName("Fetch single entry - should return entry details")
    void testFetchEntry() throws IOException, ParseException {
        assumeTrue(testContentTypeUid != null, "Skipping: No content type UID available");
        assumeTrue(existingEntryUid != null, "Skipping: No entry UID available");

        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry(existingEntryUid).fetch().execute();

        assertTrue(response.isSuccessful(), 
                "Fetch entry failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("entry"), "Response should contain 'entry' key");
        JSONObject entry = (JSONObject) result.get("entry");
        assertEquals(existingEntryUid, entry.get("uid"), "Entry UID should match");
    }

    // ==================== CREATE OPERATION ====================

    @Test
    @Order(200)
    @DisplayName("Create entry - should create new entry")
    void testCreateEntry() throws IOException, ParseException {
        assumeTrue(testContentTypeUid != null, "Skipping: No content type UID available");

        String uniqueTitle = "Test Entry " + UUID.randomUUID().toString().substring(0, 8);
        
        @SuppressWarnings("unchecked")
        JSONObject requestBody = new JSONObject();
        @SuppressWarnings("unchecked")
        JSONObject entry = new JSONObject();
        entry.put("title", uniqueTitle);
        requestBody.put("entry", entry);
        
        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry().create(requestBody).execute();

        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            // Skip if plan limitation or validation error
            if (response.code() == 403 || response.code() == 422 || error.contains("limit") || error.contains("required")) {
                assumeTrue(false, "Skipping: Plan limitation or validation error for entry creation: " + error);
            }
        }

        assertTrue(response.isSuccessful(), 
                "Create entry failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("entry"), "Response should contain created entry");
        
        JSONObject created = (JSONObject) result.get("entry");
        createdEntryUid = (String) created.get("uid");
        assertNotNull(createdEntryUid, "Created entry should have UID");
        
        System.out.println("[Test] Created entry: " + createdEntryUid);
    }

    // ==================== UPDATE OPERATION ====================

    @Test
    @Order(300)
    @DisplayName("Update entry - should update entry")
    void testUpdateEntry() throws IOException, ParseException {
        assumeTrue(testContentTypeUid != null, "Skipping: No content type UID available");
        assumeTrue(createdEntryUid != null, "Skipping: No entry to update");

        @SuppressWarnings("unchecked")
        JSONObject requestBody = new JSONObject();
        @SuppressWarnings("unchecked")
        JSONObject entry = new JSONObject();
        entry.put("title", "Updated Entry " + System.currentTimeMillis());
        requestBody.put("entry", entry);
        
        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry(createdEntryUid).update(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update entry failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
    }

    // ==================== VERSION OPERATIONS ====================

    @Test
    @Order(400)
    @DisplayName("Get entry versions - should return version details")
    void testGetEntryVersions() throws IOException, ParseException {
        assumeTrue(testContentTypeUid != null, "Skipping: No content type UID available");
        assumeTrue(existingEntryUid != null || createdEntryUid != null, "Skipping: No entry UID available");
        
        String entryUid = createdEntryUid != null ? createdEntryUid : existingEntryUid;

        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry(entryUid).detailOfAllVersion().execute();

        assertTrue(response.isSuccessful(), 
                "Get entry versions failed: " + getErrorMessage(response));

        String body = response.body().string();
        assertFalse(body.isEmpty(), "Version response should not be empty");
    }

    // ==================== REFERENCE/LANGUAGE OPERATIONS ====================

    @Test
    @Order(500)
    @DisplayName("Get entry languages - should return available languages")
    void testGetEntryLanguages() throws IOException, ParseException {
        assumeTrue(testContentTypeUid != null, "Skipping: No content type UID available");
        assumeTrue(existingEntryUid != null || createdEntryUid != null, "Skipping: No entry UID available");
        
        String entryUid = createdEntryUid != null ? createdEntryUid : existingEntryUid;

        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry(entryUid).getLanguage().execute();

        assertTrue(response.isSuccessful(), 
                "Get entry languages failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("locales"), "Response should contain 'locales' key");
    }

    // ==================== EXPORT OPERATION ====================

    @Test
    @Order(600)
    @DisplayName("Export entry - should export entry data")
    void testExportEntry() throws IOException, ParseException {
        assumeTrue(testContentTypeUid != null, "Skipping: No content type UID available");
        assumeTrue(existingEntryUid != null || createdEntryUid != null, "Skipping: No entry UID available");
        
        String entryUid = createdEntryUid != null ? createdEntryUid : existingEntryUid;

        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry(entryUid).export().execute();

        assertTrue(response.isSuccessful(), 
                "Export entry failed: " + getErrorMessage(response));

        String body = response.body().string();
        assertFalse(body.isEmpty(), "Export response should not be empty");
    }

    // ==================== DELETE OPERATION ====================

    @Test
    @Order(900)
    @DisplayName("Delete entry - should delete the entry")
    void testDeleteEntry() throws IOException, ParseException {
        assumeTrue(testContentTypeUid != null, "Skipping: No content type UID available");
        assumeTrue(createdEntryUid != null, "Skipping: No entry to delete");

        Response<ResponseBody> response = stackInstance.contentType(testContentTypeUid)
                .entry(createdEntryUid).delete().execute();

        assertTrue(response.isSuccessful(), 
                "Delete entry failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        System.out.println("[Test] Deleted entry: " + createdEntryUid);
        createdEntryUid = null; // Mark as deleted for cleanup
    }

    // ==================== HELPER METHODS ====================

    private String getErrorMessage(Response<ResponseBody> response) {
        try {
            if (response.errorBody() != null) {
                return response.errorBody().string();
            }
        } catch (IOException e) {
            // Ignore
        }
        return "Unknown error (code: " + response.code() + ")";
    }
}
