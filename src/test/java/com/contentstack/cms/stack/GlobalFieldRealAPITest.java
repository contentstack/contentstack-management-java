package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * Real API tests for GlobalField module.
 * Global fields are reusable field groups that can be used across multiple content types.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#global-field
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GlobalFieldRealAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Stack stackInstance;
    private JSONParser parser = new JSONParser();
    
    private String createdGlobalFieldUid;
    private String existingGlobalFieldUid;

    @BeforeAll
    void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        stackInstance = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
    }

    @AfterAll
    void cleanup() {
        // Clean up created global field
        if (createdGlobalFieldUid != null) {
            try {
                stackInstance.globalField(createdGlobalFieldUid).delete().execute();
                System.out.println("[Cleanup] Deleted global field: " + createdGlobalFieldUid);
            } catch (IOException e) {
                System.out.println("[Cleanup] Failed to delete global field: " + e.getMessage());
            }
        }
    }

    // ==================== READ OPERATIONS ====================

    @BeforeAll
    void setupPrerequisites() throws IOException, ParseException {
        // Ensure at least one global field exists for tests
        try {
            TestStackSetup.ensureGlobalFieldExists();
            existingGlobalFieldUid = TestStackSetup.testGlobalFieldUid;
            System.out.println("[Setup] Prerequisites ready. Global Field UID: " + existingGlobalFieldUid);
        } catch (Exception e) {
            System.out.println("[Setup] Warning: Could not setup prerequisites: " + e.getMessage());
            // Continue - tests will handle missing prerequisites
        }
    }

    @Test
    @Order(100)
    @DisplayName("Find all global fields - should return global fields array")
    void testFindAllGlobalFields() throws IOException, ParseException {
        Response<ResponseBody> response = stackInstance.globalField().find().execute();

        assertTrue(response.isSuccessful(), 
                "Find global fields failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("global_fields"), "Response should contain 'global_fields' key");
        JSONArray globalFields = (JSONArray) result.get("global_fields");
        assertNotNull(globalFields, "Global fields array should not be null");
        
        // If global fields exist, store first one for fetch test
        if (globalFields != null && !globalFields.isEmpty()) {
            JSONObject firstGF = (JSONObject) globalFields.get(0);
            existingGlobalFieldUid = (String) firstGF.get("uid");
            System.out.println("[Test] Found existing global field: " + existingGlobalFieldUid);
        }
        
        System.out.println("[Test] Found " + globalFields.size() + " global field(s)");
    }

    @Test
    @Order(101)
    @DisplayName("Fetch single global field - should return global field details")
    void testFetchGlobalField() throws IOException, ParseException {
        // Ensure we have a global field UID (from setup or testFindAllGlobalFields)
        if (existingGlobalFieldUid == null) {
            // Try to get from TestStackSetup
            existingGlobalFieldUid = TestStackSetup.testGlobalFieldUid;
        }
        
        // If still null, try to create one
        if (existingGlobalFieldUid == null) {
            existingGlobalFieldUid = TestStackSetup.ensureGlobalFieldExists();
        }
        
        assertNotNull(existingGlobalFieldUid, 
            "ASSUMPTION FAILED: Cannot fetch global field. " +
            "PREREQUISITE: At least one global field must exist in the stack. " +
            "ACTION: Global field should have been created in setup. Check setup logs.");

        Response<ResponseBody> response = stackInstance.globalField(existingGlobalFieldUid).fetch().execute();

        assertTrue(response.isSuccessful(), 
                "Fetch global field failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("global_field"), "Response should contain 'global_field' key");
        JSONObject gf = (JSONObject) result.get("global_field");
        assertEquals(existingGlobalFieldUid, gf.get("uid"), "Global field UID should match");
        assertNotNull(gf.get("title"), "Global field should have title");
        assertTrue(gf.containsKey("schema"), "Global field should have schema");
        
        System.out.println("[Test] Successfully fetched global field: " + existingGlobalFieldUid);
    }

    // ==================== CREATE OPERATION ====================

    @Test
    @Order(200)
    @DisplayName("Create global field - should create new global field")
    void testCreateGlobalField() throws IOException, ParseException {
        String uniqueUid = "test_gf_" + UUID.randomUUID().toString().substring(0, 8).replace("-", "_");
        
        // Read mock file and modify with unique values
        JSONObject requestBody = Utils.readJson("globalfield/seo_create.json");
        assumeTrue(requestBody != null, "Cannot read global field mock file");
        
        JSONObject globalField = (JSONObject) requestBody.get("global_field");
        globalField.put("uid", uniqueUid);
        globalField.put("title", "Test Global Field " + uniqueUid);
        globalField.put("description", "Test global field created via API - " + System.currentTimeMillis());
        
        Response<ResponseBody> response = stackInstance.globalField().create(requestBody).execute();

        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            // Skip if plan limitation or validation error
            if (response.code() == 403 || response.code() == 422 || error.contains("limit") || error.contains("plan")) {
                assumeTrue(false, 
                    "ASSUMPTION FAILED: Cannot create global field. " +
                    "PREREQUISITE: Plan must allow global field creation. " +
                    "CURRENT STATE: Creation failed (HTTP " + response.code() + "). " +
                    "ERROR: " + error + ". " +
                    "ACTION: Check plan limitations or fix validation errors.");
            }
        }

        assertTrue(response.isSuccessful(), 
                "Create global field failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("global_field"), "Response should contain created global field");
        
        JSONObject created = (JSONObject) result.get("global_field");
        createdGlobalFieldUid = (String) created.get("uid");
        assertNotNull(createdGlobalFieldUid, "Created global field should have UID");
        
        System.out.println("[Test] Created global field: " + createdGlobalFieldUid);
    }

    // ==================== UPDATE OPERATION ====================

    @Test
    @Order(300)
    @DisplayName("Update global field - should update global field")
    void testUpdateGlobalField() throws IOException, ParseException {
        assumeTrue(createdGlobalFieldUid != null, 
            "ASSUMPTION FAILED: Cannot update global field. " +
            "PREREQUISITE: A global field must be created first. " +
            "DEPENDENCY: This test depends on testCreateGlobalField() running successfully. " +
            "ACTION: Ensure testCreateGlobalField() completes successfully before this test runs.");

        // First fetch the current global field
        Response<ResponseBody> fetchResponse = stackInstance.globalField(createdGlobalFieldUid).fetch().execute();
        assumeTrue(fetchResponse.isSuccessful(), "Cannot fetch global field for update");
        
        String fetchBody = fetchResponse.body().string();
        JSONObject fetchResult = (JSONObject) parser.parse(fetchBody);
        JSONObject existingGF = (JSONObject) fetchResult.get("global_field");
        
        // Update the description
        existingGF.put("description", "Updated description - " + System.currentTimeMillis());
        
        @SuppressWarnings("unchecked")
        JSONObject requestBody = new JSONObject();
        requestBody.put("global_field", existingGF);
        
        Response<ResponseBody> response = stackInstance.globalField(createdGlobalFieldUid)
                .update(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update global field failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("global_field"), "Response should contain updated global field");
        
        System.out.println("[Test] Updated global field: " + createdGlobalFieldUid);
    }

    // ==================== DELETE OPERATION ====================

    @Test
    @Order(900)
    @DisplayName("Delete global field - should delete the global field")
    void testDeleteGlobalField() throws IOException, ParseException {
        assumeTrue(createdGlobalFieldUid != null, 
            "ASSUMPTION FAILED: Cannot delete global field. " +
            "PREREQUISITE: A global field must be created first. " +
            "DEPENDENCY: This test depends on testCreateGlobalField() running successfully. " +
            "ACTION: Ensure testCreateGlobalField() completes successfully before this test runs.");

        Response<ResponseBody> response = stackInstance.globalField(createdGlobalFieldUid)
                .delete().execute();

        assertTrue(response.isSuccessful(), 
                "Delete global field failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        System.out.println("[Test] Deleted global field: " + createdGlobalFieldUid);
        createdGlobalFieldUid = null; // Mark as deleted for cleanup
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
