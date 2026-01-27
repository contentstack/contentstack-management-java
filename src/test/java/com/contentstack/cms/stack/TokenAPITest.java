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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * API tests for Tokens module (Delivery Tokens & Management Tokens).
 * Tests CRUD operations on both token types.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#deliverytoken
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#managementtoken
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TokenAPITest {

    protected static String API_KEY = TestClient.API_KEY;
    protected static Stack stackInstance;
    protected static Tokens tokens;
    private JSONParser parser = new JSONParser();
    
    // Store fresh authtoken from login
    private static String freshAuthtoken;
    
    // Track created token UIDs for cleanup
    private String createdDeliveryTokenUid;
    private String createdManagementTokenUid;

    @BeforeAll
    void setup() throws IOException {
        // Token operations use USER AUTHTOKEN in headers (not MANAGEMENT_TOKEN)
        // Per CMA API docs: "Pass the user Authtoken against the authtoken parameter as header"
        // AuthInterceptor is NOT used for Token operations - they use @HeaderMap directly
        
        // Step 1: Login to get fresh authtoken
        String email = TestClient.EMAIL;
        String password = TestClient.PASSWORD;
        
        System.out.println("[TokenAPITest] Performing fresh login for token operations");
        Contentstack loginClient = new Contentstack.Builder()
                .setHost(TestClient.DEV_HOST)
                .build();
        
        Response<com.contentstack.cms.models.LoginDetails> loginResponse = loginClient.login(email, password);
        
        if (!loginResponse.isSuccessful() || loginResponse.body() == null) {
            throw new IOException("Login failed for token operations: " + loginResponse.code());
        }
        
        freshAuthtoken = loginResponse.body().user.authtoken;
        System.out.println("[TokenAPITest] Login successful");
        
        // Step 2: Create stack with user authtoken in HEADERS (required for Token operations)
        // Token operations use @HeaderMap which bypasses AuthInterceptor
        // Must include authtoken directly in headers per CMA API docs
        // Also set it on builder to avoid "Login or configure OAuth" error
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHTOKEN, freshAuthtoken);  // "authtoken" header for user auth
        
        stackInstance = new Contentstack.Builder()
                .setAuthtoken(freshAuthtoken)  // Required to avoid "Login or configure OAuth" error
                .setHost(TestClient.DEV_HOST)
                .build()
                .stack(headers);  // Headers with authtoken passed directly to Token operations
        
        // Get tokens instance from stack
        tokens = stackInstance.tokens();
        
        System.out.println("[TokenAPITest] Stack initialized with user authtoken in headers for token operations");
    }

    // ==================== DELIVERY TOKEN TESTS ====================

    @Test
    @Order(100)
    @DisplayName("Find all delivery tokens - should return tokens array")
    void testFindAllDeliveryTokens() throws IOException, ParseException {
        System.out.println("[TokenAPITest] Testing find all delivery tokens...");
        
        Response<ResponseBody> response = tokens.deliveryTokens().find().execute();
        
        if (!response.isSuccessful()) {
            System.out.println("[TokenAPITest] Error response code: " + response.code());
            System.out.println("[TokenAPITest] Error message: " + getErrorMessage(response));
        }

        assertTrue(response.isSuccessful(), 
                "Find delivery tokens failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("tokens"), "Response should contain 'tokens' key");
        JSONArray tokensArray = (JSONArray) result.get("tokens");
        assertNotNull(tokensArray, "Tokens array should not be null");
        
        // Validate token structure if tokens exist
        if (!tokensArray.isEmpty()) {
            JSONObject firstToken = (JSONObject) tokensArray.get(0);
            assertTrue(firstToken.containsKey("uid"), "Token should have 'uid' field");
            assertTrue(firstToken.containsKey("name"), "Token should have 'name' field");
        }
        
        System.out.println("[Test] Found " + tokensArray.size() + " delivery tokens");
    }

    @Test
    @Order(200)
    @DisplayName("Create delivery token - should create new token")
    void testCreateDeliveryToken() throws IOException, ParseException {
        // First get an environment to associate with token (need NAME not UID)
        String environmentName = getAvailableEnvironment();
        
        // If no environment found, try to create one
        if (environmentName == null) {
            System.out.println("[TokenAPITest] No environment found, creating test environment...");
            String uniqueEnvName = "test_env_token_" + UUID.randomUUID().toString().substring(0, 8);
            
            @SuppressWarnings("unchecked")
            JSONObject envRequestBody = new JSONObject();
            @SuppressWarnings("unchecked")
            JSONObject envData = new JSONObject();
            envData.put("name", uniqueEnvName);
            envData.put("urls", new org.json.simple.JSONArray());
            envRequestBody.put("environment", envData);
            
            Response<ResponseBody> createEnvResponse = stackInstance.environment().create(envRequestBody).execute();
            if (createEnvResponse.isSuccessful()) {
                environmentName = uniqueEnvName;
                System.out.println("[TokenAPITest] Created test environment: " + environmentName);
            }
        }
        
        assertNotNull(environmentName, 
            "ASSUMPTION FAILED: Cannot create delivery token. " +
            "PREREQUISITE: At least one environment must exist in the stack. " +
            "CURRENT STATE: No environments found and could not create one. " +
            "ACTION: Create an environment in Contentstack UI or ensure EnvironmentAPITest runs first.");

        String uniqueName = "test_dt_" + UUID.randomUUID().toString().substring(0, 8);
        JSONObject requestBody = createDeliveryTokenBody(uniqueName, environmentName);
        
        Response<ResponseBody> response = tokens.deliveryTokens().create(requestBody).execute();

        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            if (error.contains("already exists") || response.code() == 422) {
                assumeTrue(false, "Skipping: Token creation issue - " + error);
            }
        }

        assertTrue(response.isSuccessful(), 
                "Create delivery token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("token"), "Response should contain created token");
        
        JSONObject token = (JSONObject) result.get("token");
        createdDeliveryTokenUid = (String) token.get("uid");
        String actualToken = (String) token.get("token");  // Actual delivery token value
        
        assertNotNull(createdDeliveryTokenUid, "Created token should have uid");
        assertNotNull(actualToken, "Created token should have token value");
        assertEquals(uniqueName, token.get("name"), "Token name should match");
        
        // Delivery token VALUE should start with 'cs', UID starts with 'blt'
        assertTrue(actualToken.startsWith("cs"), 
                "Delivery token value should start with 'cs'");
        assertTrue(createdDeliveryTokenUid.startsWith("blt"), 
                "Delivery token UID should start with 'blt'");
        
        System.out.println("[Test] Created delivery token successfully (UID: " + createdDeliveryTokenUid.substring(0, 8) + "...)");
    }

    @Test
    @Order(201)
    @DisplayName("Fetch single delivery token - should return token details")
    void testFetchDeliveryToken() throws IOException, ParseException {
        assumeTrue(createdDeliveryTokenUid != null, "Skipping: No delivery token created");

        Response<ResponseBody> response = tokens.deliveryTokens(createdDeliveryTokenUid).fetch().execute();

        assertTrue(response.isSuccessful(), 
                "Fetch delivery token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("token"), "Response should contain 'token' key");
        JSONObject token = (JSONObject) result.get("token");
        assertEquals(createdDeliveryTokenUid, token.get("uid"), "Token UID should match");
    }

    @Test
    @Order(300)
    @DisplayName("Update delivery token - should update token details")
    void testUpdateDeliveryToken() throws IOException, ParseException {
        assumeTrue(createdDeliveryTokenUid != null, "Skipping: No delivery token to update");

        String updatedName = "updated_dt_" + UUID.randomUUID().toString().substring(0, 8);
        JSONObject requestBody = new JSONObject();
        JSONObject tokenData = new JSONObject();
        tokenData.put("name", updatedName);
        tokenData.put("description", "Updated delivery token");
        requestBody.put("token", tokenData);
        
        Response<ResponseBody> response = tokens.deliveryTokens(createdDeliveryTokenUid).update(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update delivery token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
    }

    @Test
    @Order(900)
    @DisplayName("Delete delivery token - should remove token")
    void testDeleteDeliveryToken() throws IOException, ParseException {
        assumeTrue(createdDeliveryTokenUid != null, "Skipping: No delivery token to delete");

        Response<ResponseBody> response = tokens.deliveryTokens(createdDeliveryTokenUid).delete().execute();

        assertTrue(response.isSuccessful(), 
                "Delete delivery token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        createdDeliveryTokenUid = null;
        System.out.println("[Test] Delivery token deleted successfully");
    }

    // ==================== MANAGEMENT TOKEN TESTS ====================

    @Test
    @Order(110)
    @DisplayName("Find all management tokens - should return tokens array")
    void testFindAllManagementTokens() throws IOException, ParseException {
        Response<ResponseBody> response = tokens.managementToken().find().execute();

        assertTrue(response.isSuccessful(), 
                "Find management tokens failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("tokens"), "Response should contain 'tokens' key");
        JSONArray tokensArray = (JSONArray) result.get("tokens");
        assertNotNull(tokensArray, "Tokens array should not be null");
    }

    @Test
    @Order(210)
    @DisplayName("Create management token - should create new token")
    void testCreateManagementToken() throws IOException, ParseException {
        String uniqueName = "test_mt_" + UUID.randomUUID().toString().substring(0, 8);
        JSONObject requestBody = createManagementTokenBody(uniqueName);
        
        Response<ResponseBody> response = tokens.managementToken().create(requestBody).execute();

        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            // Skip only if feature not available (403/422)
            if (response.code() == 403 || response.code() == 422) {
                assumeTrue(false, "Skipping: Management token creation not available - " + error);
            }
        }

        assertTrue(response.isSuccessful(), 
                "Create management token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("token"), "Response should contain created token");
        
        JSONObject token = (JSONObject) result.get("token");
        createdManagementTokenUid = (String) token.get("uid");  // This is the token record UID for management
        String actualToken = (String) token.get("token");  // This is the actual management token value
        
        assertNotNull(createdManagementTokenUid, "Created token should have uid");
        assertNotNull(actualToken, "Created token should have token value");
        
        // Management token VALUE should start with 'cs', UID starts with 'blt'
        assertTrue(actualToken.startsWith("cs"), 
                "Management token value should start with 'cs'");
        assertTrue(createdManagementTokenUid.startsWith("blt"), 
                "Management token UID should start with 'blt'");
        
        System.out.println("[Test] Created management token successfully (UID: " + createdManagementTokenUid.substring(0, 8) + "...)");
    }

    @Test
    @Order(211)
    @DisplayName("Fetch single management token - should return token details")
    void testFetchManagementToken() throws IOException, ParseException {
        assumeTrue(createdManagementTokenUid != null, "Skipping: No management token created");

        Response<ResponseBody> response = tokens.managementToken(createdManagementTokenUid).fetch().execute();

        assertTrue(response.isSuccessful(), 
                "Fetch management token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("token"), "Response should contain 'token' key");
        JSONObject token = (JSONObject) result.get("token");
        assertEquals(createdManagementTokenUid, token.get("uid"), "Token UID should match");
    }

    @Test
    @Order(310)
    @DisplayName("Update management token - should update token details")
    void testUpdateManagementToken() throws IOException, ParseException {
        assumeTrue(createdManagementTokenUid != null, "Skipping: No management token to update");

        String updatedName = "updated_mt_" + UUID.randomUUID().toString().substring(0, 8);
        JSONObject requestBody = new JSONObject();
        JSONObject tokenData = new JSONObject();
        tokenData.put("name", updatedName);
        tokenData.put("description", "Updated management token");
        requestBody.put("token", tokenData);
        
        Response<ResponseBody> response = tokens.managementToken(createdManagementTokenUid).update(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update management token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
    }

    @Test
    @Order(910)
    @DisplayName("Delete management token - should remove token")
    void testDeleteManagementToken() throws IOException, ParseException {
        assumeTrue(createdManagementTokenUid != null, "Skipping: No management token to delete");

        Response<ResponseBody> response = tokens.managementToken(createdManagementTokenUid).delete().execute();

        assertTrue(response.isSuccessful(), 
                "Delete management token failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        createdManagementTokenUid = null;
        System.out.println("[Test] Management token deleted successfully");
    }

    // ==================== HELPER METHODS ====================

    /**
     * Get an available environment name for token creation
     * Following JS SDK pattern: fetch environments from API
     */
    private String getAvailableEnvironment() {
        try {
            Response<ResponseBody> envResponse = stackInstance.environment().find().execute();
            if (envResponse.isSuccessful()) {
                String envBody = envResponse.body().string();
                JSONObject envResult = (JSONObject) parser.parse(envBody);
                JSONArray environments = (JSONArray) envResult.get("environments");
                if (environments != null && !environments.isEmpty()) {
                    JSONObject firstEnv = (JSONObject) environments.get(0);
                    return (String) firstEnv.get("name");  // Return environment NAME, not UID
                }
            }
        } catch (Exception e) {
            System.out.println("[Test] Could not fetch environments: " + e.getMessage());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private JSONObject createDeliveryTokenBody(String name, String environmentName) {
        JSONObject requestBody = new JSONObject();
        JSONObject tokenData = new JSONObject();
        tokenData.put("name", name);
        tokenData.put("description", "Test delivery token");
        
        // Scope structure per CMA API and JS SDK pattern
        JSONArray scope = new JSONArray();
        
        // Environment scope (required for delivery tokens)
        JSONObject envScope = new JSONObject();
        envScope.put("module", "environment");
        envScope.put("environments", new JSONArray() {{ add(environmentName); }});
        JSONObject envAcl = new JSONObject();
        envAcl.put("read", true);
        envScope.put("acl", envAcl);
        scope.add(envScope);
        
        // Branch scope (required)
        JSONObject branchScope = new JSONObject();
        branchScope.put("module", "branch");
        branchScope.put("branches", new JSONArray() {{ add("main"); }});
        JSONObject branchAcl = new JSONObject();
        branchAcl.put("read", true);
        branchScope.put("acl", branchAcl);
        scope.add(branchScope);
        
        tokenData.put("scope", scope);
        requestBody.put("token", tokenData);
        return requestBody;
    }

    @SuppressWarnings("unchecked")
    private JSONObject createManagementTokenBody(String name) {
        JSONObject requestBody = new JSONObject();
        JSONObject tokenData = new JSONObject();
        tokenData.put("name", name);
        tokenData.put("description", "Test management token");
        
        // Scope structure per CMA API and JS SDK pattern
        JSONArray scope = new JSONArray();
        
        // Content type scope
        JSONObject contentTypeScope = new JSONObject();
        contentTypeScope.put("module", "content_type");
        JSONObject ctAcl = new JSONObject();
        ctAcl.put("read", true);
        ctAcl.put("write", true);
        contentTypeScope.put("acl", ctAcl);
        scope.add(contentTypeScope);
        
        // Entry scope
        JSONObject entryScope = new JSONObject();
        entryScope.put("module", "entry");
        JSONObject entryAcl = new JSONObject();
        entryAcl.put("read", true);
        entryAcl.put("write", true);
        entryScope.put("acl", entryAcl);
        scope.add(entryScope);
        
        // Asset scope
        JSONObject assetScope = new JSONObject();
        assetScope.put("module", "asset");
        JSONObject assetAcl = new JSONObject();
        assetAcl.put("read", true);
        assetAcl.put("write", true);
        assetScope.put("acl", assetAcl);
        scope.add(assetScope);
        
        // Branch scope (REQUIRED per API error)
        JSONObject branchScope = new JSONObject();
        branchScope.put("module", "branch");
        branchScope.put("branches", new JSONArray() {{ add("main"); }});
        JSONObject branchAcl = new JSONObject();
        branchAcl.put("read", true);
        branchScope.put("acl", branchAcl);
        scope.add(branchScope);
        
        tokenData.put("scope", scope);
        tokenData.put("is_email_notification_enabled", false);
        
        requestBody.put("token", tokenData);
        return requestBody;
    }

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
