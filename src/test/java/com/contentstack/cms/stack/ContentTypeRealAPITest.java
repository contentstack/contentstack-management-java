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
 * Real API tests for ContentType module.
 * Content type defines the structure or schema of a page or section.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#content-type
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContentTypeRealAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Stack stackInstance;
    private JSONParser parser = new JSONParser();
    
    private String createdContentTypeUid;
    private String existingContentTypeUid;

    @BeforeAll
    void setup() throws IOException, ParseException {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        stackInstance = new Contentstack.Builder()
                .setAuthtoken(AUTHTOKEN)
                .setHost(TestClient.DEV_HOST)
                .build()
                .stack(headers);
        
        // Ensure at least one content type exists for tests
        try {
            TestStackSetup.ensureContentTypeExists();
            existingContentTypeUid = TestStackSetup.testContentTypeUid;
            System.out.println("[Setup] Prerequisites ready. Content Type UID: " + existingContentTypeUid);
        } catch (Exception e) {
            System.out.println("[Setup] Warning: Could not setup prerequisites: " + e.getMessage());
            // Continue - tests will handle missing prerequisites
        }
    }

    @AfterAll
    void cleanup() {
        // Clean up created content type
        if (createdContentTypeUid != null) {
            try {
                stackInstance.contentType(createdContentTypeUid).delete().execute();
                System.out.println("[Cleanup] Deleted content type: " + createdContentTypeUid);
            } catch (IOException e) {
                System.out.println("[Cleanup] Failed to delete content type: " + e.getMessage());
            }
        }
    }

    // ==================== READ OPERATIONS ====================

    @Test
    @Order(100)
    @DisplayName("Find all content types - should return content types array")
    void testFindAllContentTypes() throws IOException, ParseException {
        Response<ResponseBody> response = stackInstance.contentType().find().execute();

        assertTrue(response.isSuccessful(), 
                "Find content types failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("content_types"), "Response should contain 'content_types' key");
        JSONArray contentTypes = (JSONArray) result.get("content_types");
        assertNotNull(contentTypes, "Content types array should not be null");
        
        // Validate content type structure if any exist
        if (!contentTypes.isEmpty()) {
            JSONObject firstCT = (JSONObject) contentTypes.get(0);
            assertTrue(firstCT.containsKey("uid"), "Content type should have 'uid' field");
            assertTrue(firstCT.containsKey("title"), "Content type should have 'title' field");
            
            // Store existing content type UID for other tests
            existingContentTypeUid = (String) firstCT.get("uid");
        }
        
        System.out.println("[Test] Found " + contentTypes.size() + " content type(s)");
    }

    @Test
    @Order(101)
    @DisplayName("Fetch single content type - should return content type details")
    void testFetchContentType() throws IOException, ParseException {
        // Ensure we have a content type UID (from setup or testFindAllContentTypes)
        if (existingContentTypeUid == null) {
            // Try to get from TestStackSetup
            existingContentTypeUid = TestStackSetup.testContentTypeUid;
        }
        
        // If still null, try to create one
        if (existingContentTypeUid == null) {
            existingContentTypeUid = TestStackSetup.ensureContentTypeExists();
        }
        
        assertNotNull(existingContentTypeUid, 
            "ASSUMPTION FAILED: Cannot fetch content type. " +
            "PREREQUISITE: At least one content type must exist in the stack. " +
            "ACTION: Content type should have been created in setup. Check setup logs.");

        Response<ResponseBody> response = stackInstance.contentType(existingContentTypeUid).fetch().execute();

        assertTrue(response.isSuccessful(), 
                "Fetch content type failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("content_type"), "Response should contain 'content_type' key");
        JSONObject ct = (JSONObject) result.get("content_type");
        assertEquals(existingContentTypeUid, ct.get("uid"), "Content type UID should match");
        assertNotNull(ct.get("title"), "Content type should have title");
        assertTrue(ct.containsKey("schema"), "Content type should have schema");
    }

    // ==================== CREATE OPERATION ====================

    @Test
    @Order(200)
    @DisplayName("Create content type - should create new content type")
    void testCreateContentType() throws IOException, ParseException {
        String uniqueUid = "test_ct_" + UUID.randomUUID().toString().substring(0, 8).replace("-", "_");
        
        // Use new comprehensive mock file
        JSONObject requestBody = Utils.readJson("contenttype/simple_create.json");
        JSONObject contentType = (JSONObject) requestBody.get("content_type");
        
        // Override with unique values
        contentType.put("uid", uniqueUid);
        contentType.put("title", "Test Content Type " + uniqueUid);
        
        Response<ResponseBody> response = stackInstance.contentType().create(requestBody).execute();

        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            // Skip if plan limitation
            if (response.code() == 403 || error.contains("limit") || error.contains("plan")) {
                assumeTrue(false, "Skipping: Plan limitation for content type creation");
            }
        }

        assertTrue(response.isSuccessful(), 
                "Create content type failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("content_type"), "Response should contain created content type");
        
        JSONObject created = (JSONObject) result.get("content_type");
        createdContentTypeUid = (String) created.get("uid");
        assertNotNull(createdContentTypeUid, "Created content type should have UID");
        
        System.out.println("[Test] Created content type: " + createdContentTypeUid);
    }

    // ==================== UPDATE OPERATION ====================

    @Test
    @Order(300)
    @DisplayName("Update content type - should update content type")
    void testUpdateContentType() throws IOException, ParseException {
        assumeTrue(createdContentTypeUid != null, "Skipping: No content type to update");

        // First fetch the current content type
        Response<ResponseBody> fetchResponse = stackInstance.contentType(createdContentTypeUid).fetch().execute();
        assumeTrue(fetchResponse.isSuccessful(), "Cannot fetch content type for update");
        
        String fetchBody = fetchResponse.body().string();
        JSONObject fetchResult = (JSONObject) parser.parse(fetchBody);
        JSONObject existingCT = (JSONObject) fetchResult.get("content_type");
        
        // Update the description
        existingCT.put("description", "Updated description - " + System.currentTimeMillis());
        
        @SuppressWarnings("unchecked")
        JSONObject requestBody = new JSONObject();
        requestBody.put("content_type", existingCT);
        
        Response<ResponseBody> response = stackInstance.contentType(createdContentTypeUid)
                .update(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update content type failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
    }

    // ==================== REFERENCE OPERATIONS ====================

    @Test
    @Order(400)
    @DisplayName("Get content type references - should return references")
    void testGetContentTypeReferences() throws IOException, ParseException {
        assumeTrue(existingContentTypeUid != null, "Skipping: No content type UID available");

        Response<ResponseBody> response = stackInstance.contentType(existingContentTypeUid)
                .reference(false).execute();

        assertTrue(response.isSuccessful(), 
                "Get content type references failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        // Response may have references array (could be empty)
        assertTrue(result.containsKey("references") || result.containsKey("content_type"), 
                "Response should contain 'references' or 'content_type' key");
    }

    @Test
    @Order(401)
    @DisplayName("Export content type - should return exported content type")
    void testExportContentType() throws IOException, ParseException {
        assumeTrue(existingContentTypeUid != null, "Skipping: No content type UID available");

        Response<ResponseBody> response = stackInstance.contentType(existingContentTypeUid)
                .export().execute();

        assertTrue(response.isSuccessful(), 
                "Export content type failed: " + getErrorMessage(response));

        String body = response.body().string();
        assertFalse(body.isEmpty(), "Export response should not be empty");
    }

    // ==================== DELETE OPERATION ====================

    @Test
    @Order(900)
    @DisplayName("Delete content type - should delete the content type")
    void testDeleteContentType() throws IOException, ParseException {
        assumeTrue(createdContentTypeUid != null, "Skipping: No content type to delete");

        Response<ResponseBody> response = stackInstance.contentType(createdContentTypeUid)
                .delete().execute();

        assertTrue(response.isSuccessful(), 
                "Delete content type failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        System.out.println("[Test] Deleted content type: " + createdContentTypeUid);
        createdContentTypeUid = null; // Mark as deleted for cleanup
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
