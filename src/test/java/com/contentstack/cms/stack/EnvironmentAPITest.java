package com.contentstack.cms.stack;

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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * API tests for Environment module.
 * Tests CRUD operations on publishing environments.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#environment
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnvironmentAPITest {

    protected static String API_KEY = TestClient.API_KEY;
    protected static Stack stackInstance;
    private JSONParser parser = new JSONParser();
    private String createdEnvName;

    @BeforeAll
    public void setupEnv() {
        stackInstance = TestClient.getStack()
                .addHeader(Util.API_KEY, API_KEY)
                .addHeader("api_key", API_KEY);
    }

    @AfterAll
    public void cleanup() throws IOException {
        // Delete created test environment at the very end
        // Note: createdEnvName tracks the current name (may have been updated)
        if (createdEnvName != null) {
            try {
                Response<ResponseBody> response = stackInstance.environment(createdEnvName).delete().execute();
                if (response.isSuccessful()) {
                    System.out.println("[Cleanup] Deleted test environment: " + createdEnvName);
                } else {
                    String error = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                    System.out.println("[Cleanup] Failed to delete environment " + createdEnvName + 
                                     " (HTTP " + response.code() + "): " + error);
                    // Try to find and delete by searching all environments
                    try {
                        Response<ResponseBody> findResponse = stackInstance.environment().find().execute();
                        if (findResponse.isSuccessful()) {
                            String findBody = findResponse.body().string();
                            JSONObject findResult = (JSONObject) parser.parse(findBody);
                            JSONArray environments = (JSONArray) findResult.get("environments");
                            if (environments != null) {
                                for (Object obj : environments) {
                                    JSONObject env = (JSONObject) obj;
                                    String envName = (String) env.get("name");
                                    // Check if this is our test environment (starts with test_env_)
                                    if (envName != null && envName.startsWith("test_env_")) {
                                        Response<ResponseBody> deleteResponse = stackInstance.environment(envName).delete().execute();
                                        if (deleteResponse.isSuccessful()) {
                                            System.out.println("[Cleanup] Deleted test environment (found by search): " + envName);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        System.out.println("[Cleanup] Could not search for test environments: " + e2.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("[Cleanup] Error deleting environment " + createdEnvName + ": " + e.getMessage());
            }
        }
    }

    // ========== CREATE OPERATION (Run first to ensure environment exists) ==========

    @Test
    @Order(50)
    @DisplayName("Create environment - should create new environment for testing")
    void testCreateEnvironment() throws IOException, ParseException {
        // Generate unique environment name
        String uniqueName = "test_env_" + UUID.randomUUID().toString().substring(0, 8);
        
        JSONObject requestBody = Utils.readJson("environment/development.json");
        if (requestBody == null) {
            // Create minimal request body if JSON file is missing
            requestBody = new JSONObject();
            JSONObject envData = new JSONObject();
            envData.put("name", uniqueName);
            requestBody.put("environment", envData);
        } else {
            // Override name to make it unique
            JSONObject envData = (JSONObject) requestBody.get("environment");
            if (envData != null) {
                envData.put("name", uniqueName);
            } else {
                envData = new JSONObject();
                envData.put("name", uniqueName);
                requestBody.put("environment", envData);
            }
        }
        
        Response<ResponseBody> response = stackInstance.environment().create(requestBody).execute();

        // Handle duplicate environment error
        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            if (error.contains("already exists")) {
                // Try to find existing environment with this name
                Response<ResponseBody> findResponse = stackInstance.environment().find().execute();
                if (findResponse.isSuccessful()) {
                    String findBody = findResponse.body().string();
                    JSONObject findResult = (JSONObject) parser.parse(findBody);
                    JSONArray environments = (JSONArray) findResult.get("environments");
                    if (environments != null) {
                        for (Object obj : environments) {
                            JSONObject env = (JSONObject) obj;
                            if (uniqueName.equals(env.get("name"))) {
                                createdEnvName = uniqueName;
                                System.out.println("[Test] Using existing environment: " + createdEnvName);
                                return;
                            }
                        }
                    }
                }
                assumeTrue(false, "Skipping: Environment already exists and could not be reused");
            }
        }

        assertTrue(response.isSuccessful(), 
                "Create environment failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("environment"), "Response should contain created environment");
        
        JSONObject env = (JSONObject) result.get("environment");
        createdEnvName = (String) env.get("name");
        assertNotNull(createdEnvName, "Created environment should have name");
        assertNotNull(env.get("uid"), "Created environment should have uid");
        
        System.out.println("[Test] Created environment: " + createdEnvName);
    }

    // ========== READ OPERATIONS ==========

    @Test
    @Order(100)
    @DisplayName("Find all environments - should return environments array")
    void testFindEnvironments() throws IOException, ParseException {
        Environment env = stackInstance.environment();
        env.addParam("include_count", true);
        
        Response<ResponseBody> response = env.find().execute();

        assertTrue(response.isSuccessful(), 
                "Find environments failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        // Validate response structure
        assertTrue(result.containsKey("environments"), "Response should contain 'environments' key");
        JSONArray environments = (JSONArray) result.get("environments");
        assertNotNull(environments, "Environments array should not be null");
        
        // Validate count if included
        if (result.containsKey("count")) {
            Long count = (Long) result.get("count");
            assertEquals(environments.size(), count.intValue(), "Count should match array size");
        }
        
        // If environments exist, validate structure
        if (!environments.isEmpty()) {
            JSONObject firstEnv = (JSONObject) environments.get(0);
            assertTrue(firstEnv.containsKey("name"), "Environment should have 'name' field");
            assertTrue(firstEnv.containsKey("uid"), "Environment should have 'uid' field");
        }
    }

    @Test
    @Order(101)
    @DisplayName("Fetch single environment - should fetch created test environment")
    void testFetchEnvironment() throws IOException, ParseException {
        // Ensure we have a created environment (from testCreateEnvironment)
        if (createdEnvName == null) {
            // Try to find any existing environment as fallback
            Response<ResponseBody> findResponse = stackInstance.environment().find().execute();
            if (findResponse.isSuccessful()) {
                String findBody = findResponse.body().string();
                JSONObject findResult = (JSONObject) parser.parse(findBody);
                JSONArray environments = (JSONArray) findResult.get("environments");
                
                if (environments != null && !environments.isEmpty()) {
                    JSONObject firstEnv = (JSONObject) environments.get(0);
                    createdEnvName = (String) firstEnv.get("name");
                    System.out.println("[Test] Using existing environment for fetch: " + createdEnvName);
                }
            }
        }
        
        assertNotNull(createdEnvName, 
            "ASSUMPTION FAILED: Cannot fetch environment. " +
            "PREREQUISITE: A test environment must be created first. " +
            "DEPENDENCY: This test depends on testCreateEnvironment() running successfully. " +
            "ACTION: Ensure testCreateEnvironment() completes successfully before this test runs.");
        
        Response<ResponseBody> response = stackInstance.environment(createdEnvName).fetch().execute();

        assertTrue(response.isSuccessful(), 
                "Fetch environment failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("environment"), "Response should contain 'environment' key");
        JSONObject env = (JSONObject) result.get("environment");
        assertEquals(createdEnvName, env.get("name"), "Environment name should match");
        assertNotNull(env.get("uid"), "Environment should have uid");
        
        System.out.println("[Test] Successfully fetched environment: " + createdEnvName);
    }


    // ========== UPDATE OPERATION ==========

    @Test
    @Order(300)
    @DisplayName("Update environment - should update environment details")
    void testUpdateEnvironment() throws IOException, ParseException {
        assumeTrue(createdEnvName != null, "Skipping: No environment created to update");

        JSONObject requestBody = Utils.readJson("environment/update.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject envData = new JSONObject();
            envData.put("name", createdEnvName + "_updated");
            requestBody.put("environment", envData);
        }
        
        JSONObject envData = (JSONObject) requestBody.get("environment");
        if (envData != null) {
            envData.put("name", createdEnvName + "_updated");
        }
        
        Response<ResponseBody> response = stackInstance.environment(createdEnvName).update(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update environment failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("environment"), "Response should contain updated environment");
        
        // Update the tracked name
        JSONObject env = (JSONObject) result.get("environment");
        createdEnvName = (String) env.get("name");
    }

    // ========== DELETE OPERATION ==========

    @Test
    @Order(900)
    @DisplayName("Delete environment - should remove created environment")
    void testDeleteEnvironment() throws IOException, ParseException {
        assumeTrue(createdEnvName != null, 
            "ASSUMPTION FAILED: Cannot delete environment. " +
            "PREREQUISITE: A test environment must be created first (testCreateEnvironment must run successfully). " +
            "CURRENT STATE: createdEnvName=" + createdEnvName + ". " +
            "DEPENDENCY: This test depends on testCreateEnvironment() running successfully. " +
            "ACTION: Ensure testCreateEnvironment() completes successfully before this test runs.");

        Response<ResponseBody> response = stackInstance.environment(createdEnvName).delete().execute();

        assertTrue(response.isSuccessful(), 
                "Delete environment failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        System.out.println("[Test] Environment deleted successfully: " + createdEnvName);
        // Note: Don't set createdEnvName = null here - @AfterAll cleanup will handle final deletion
    }

    // ========== HELPER METHODS ==========

    private String getErrorMessage(Response<ResponseBody> response) {
        try {
            if (response.errorBody() != null) {
                return response.errorBody().string();
            }
        } catch (IOException e) {
            // Ignore
        }
        return "Unknown error";
    }
}
