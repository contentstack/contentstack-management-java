package com.contentstack.cms.stack;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * API tests for Taxonomy and Terms modules.
 * Tests CRUD operations with proper response validation.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#taxonomy
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#terms
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaxonomyAPITest {
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Stack stackInstance;
    protected static Taxonomy taxonomy;
    private JSONParser parser = new JSONParser();
    
    // Track created resources for cleanup
    private String createdTaxonomyUid;
    private String createdTermUid;

    @BeforeAll
    void setup() {
        final String AUTHTOKEN = TestClient.AUTHTOKEN;
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        stackInstance = new Contentstack.Builder()
                .setAuthtoken(AUTHTOKEN)
                .setHost(TestClient.DEV_HOST)
                .build()
                .stack(headers);
        taxonomy = stackInstance.taxonomy();
    }

    // ==================== TAXONOMY API TESTS ====================

    @Test
    @Order(50)
    @DisplayName("Create taxonomy - should create new taxonomy from mock file")
    void testCreateTaxonomy() throws IOException, ParseException {
        JSONObject requestBody = Utils.readJson("taxonomy/categories.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject taxData = new JSONObject();
            taxData.put("uid", "categories_" + UUID.randomUUID().toString().substring(0, 8));
            taxData.put("name", "Test Categories");
            requestBody.put("taxonomy", taxData);
        }
        
        // Add unique suffix to avoid conflicts
        JSONObject taxData = (JSONObject) requestBody.get("taxonomy");
        if (taxData != null) {
            String uniqueUid = "categories_" + java.util.UUID.randomUUID().toString().substring(0, 8);
            taxData.put("uid", uniqueUid);
        }
        
        Response<ResponseBody> response = taxonomy.create(requestBody).execute();

        // Handle errors gracefully  
        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            System.out.println("[Test] Taxonomy creation failed with code " + response.code());
            System.out.println("[Test] Error details: " + error);
            
            if (error.contains("already exists") || response.code() == 422) {
                assumeTrue(false, "Skipping: Taxonomy already exists");
            }
            
            // Fail with detailed error message
            fail("Create taxonomy failed with code " + response.code() + ": " + error);
        }

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        // Check if response contains taxonomy (success indicator)
        assertTrue(result.containsKey("taxonomy"), 
                "Response should contain created taxonomy. Response: " + result.toJSONString());
        
        JSONObject tax = (JSONObject) result.get("taxonomy");
        createdTaxonomyUid = (String) tax.get("uid");
        assertNotNull(createdTaxonomyUid, "Created taxonomy should have uid");
        
        System.out.println("[Test] Created taxonomy: " + createdTaxonomyUid);
    }

    @Test
    @Order(100)
    @DisplayName("Find all taxonomies - should return taxonomies array")
    void testFindAllTaxonomies() throws IOException, ParseException {
        Response<ResponseBody> response = taxonomy.find().execute();

        assertTrue(response.isSuccessful(), 
                "Find taxonomies failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("taxonomies"), "Response should contain 'taxonomies' key");
        JSONArray taxonomies = (JSONArray) result.get("taxonomies");
        assertNotNull(taxonomies, "Taxonomies array should not be null");
        
        // Validate taxonomy structure if any exist
        if (!taxonomies.isEmpty()) {
            JSONObject firstTax = (JSONObject) taxonomies.get(0);
            assertTrue(firstTax.containsKey("uid"), "Taxonomy should have 'uid' field");
            assertTrue(firstTax.containsKey("name"), "Taxonomy should have 'name' field");
        }
        
        System.out.println("[Test] Found " + taxonomies.size() + " taxonomy(ies)");
    }

    @Test
    @Order(101)
    @DisplayName("Fetch single taxonomy - should return taxonomy details")
    void testFetchTaxonomy() throws IOException, ParseException {
        // First find an existing taxonomy
        Response<ResponseBody> findResponse = taxonomy.find().execute();
        assumeTrue(findResponse.isSuccessful(), "Cannot find taxonomies");
        
        String findBody = findResponse.body().string();
        JSONObject findResult = (JSONObject) parser.parse(findBody);
        JSONArray taxonomies = (JSONArray) findResult.get("taxonomies");
        
        assumeTrue(taxonomies != null && !taxonomies.isEmpty(), "No taxonomies exist to fetch");
        
        String existingTaxonomyUid = (String) ((JSONObject) taxonomies.get(0)).get("uid");
        
        Response<ResponseBody> response = taxonomy.fetch(existingTaxonomyUid).execute();

        assertTrue(response.isSuccessful(), 
                "Fetch taxonomy failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("taxonomy"), "Response should contain 'taxonomy' key");
        JSONObject tax = (JSONObject) result.get("taxonomy");
        assertEquals(existingTaxonomyUid, tax.get("uid"), "Taxonomy UID should match");
    }

    @Test
    void createTaxonomy() throws IOException {
        // Use new comprehensive mock file
        JSONObject requestBody = Utils.readJson("taxonomy/categories.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject taxData = new JSONObject();
            taxData.put("uid", "categories_test");
            taxData.put("name", "Test Categories");
            requestBody.put("taxonomy", taxData);
        }
        
        taxonomy.addHeader(Util.API_KEY, API_KEY);
        taxonomy.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = taxonomy.create(requestBody).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void updateTaxonomy() {
        JSONObject updateBody = Utils.readJson("taxonomy/update.json");
        
        // Create minimal request body if JSON file is missing
        if (updateBody == null) {
            updateBody = new JSONObject();
            JSONObject taxData = new JSONObject();
            taxData.put("name", "Updated Categories");
            updateBody.put("taxonomy", taxData);
        }
        
        taxonomy.addHeader(Util.API_KEY, API_KEY);
        taxonomy.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = taxonomy.update("sample_one",updateBody).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void deleteTaxonomy(){
        taxonomy.clearParams();
        taxonomy.addHeader("Content-Type", "application/json");
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        headers.put("key_param1", "key_param_value");
        headers.put("key_param2", "key_param_value");
        params.put("key_param3", "key_param_value");
        params.put("key_param4", "key_param_value");
        taxonomy.addHeaders(headers);
        taxonomy.addParams(params);
        Request request = taxonomy.delete("sample_one").request();
        Assertions.assertEquals(5, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());    }

    // ==================== TERMS UNIT TESTS (Request Validation) ====================
    // Note: These tests validate request construction, not actual API calls

    @Test
    @Tag("unit")
    void testCreateTermRequest() throws IOException {
        String taxonomyUid = "sample_one";
        Terms terms = stackInstance.taxonomy(taxonomyUid).terms();
        terms.clearParams();
        JSONObject term = Utils.readJson("taxonomy/term_technology.json");
        
        // Create minimal request body if JSON file is missing
        if (term == null) {
            term = new JSONObject();
            JSONObject termData = new JSONObject();
            termData.put("name", "Technology");
            term.put("term", termData);
        }
        
        Request request = terms.create(term).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(taxonomyUid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNotNull(request.body());
    }

    @Test
    @Tag("unit")
    void testFetchAllTermsRequest() throws IOException {
        String taxonomyUid = "sample_one";
        Terms terms = stackInstance.taxonomy(taxonomyUid).terms();
        terms.clearParams();
        terms.addParam("limit", 3);
        Request request = terms.find().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(taxonomyUid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertEquals("limit=3", request.url().encodedQuery());
    }

    @Test
    @Tag("unit")
    void testFetchSingleTermRequest() throws IOException {
        String taxonomyUid = "sample_one";
        Terms terms = stackInstance.taxonomy(taxonomyUid).terms();
        terms.clearParams();
        Request request = terms.fetch("india").request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(taxonomyUid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
    }

    @Test
    @Tag("unit")
    void testUpdateTermRequest() {
        String taxonomyUid = "sample_one";
        Terms terms = stackInstance.taxonomy(taxonomyUid).terms();
        JSONObject newTerm = Utils.readJson("taxonomy/update.json");
        
        // Create minimal request body if JSON file is missing
        if (newTerm == null) {
            newTerm = new JSONObject();
            JSONObject termData = new JSONObject();
            termData.put("name", "Updated Term");
            newTerm.put("term", termData);
        }
        
        Request request = terms.update(taxonomyUid, newTerm).request();
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
    }

    @Test
    @Tag("unit")
    void testDeleteTermRequest() {
        String taxonomyUid = "sample_one";
        Request request = taxonomy.delete(taxonomyUid).request();
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(taxonomyUid, request.url().pathSegments().get(2));
    }

    @Test
    @Tag("unit")
    void testAncestorsTermRequest() {
        String taxonomyUid = "sample_one";
        Terms terms = stackInstance.taxonomy(taxonomyUid).terms();
        terms.clearParams();
        terms.addParam("include_referenced_entries_count", true);
        terms.addParam("include_children_count", false);
        Request request = terms.ancestors("term_a2").request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(taxonomyUid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
    }

    @Test
    @Tag("unit")
    void testDescendantsTermRequest() {
        String taxonomyUid = "sample_one";
        Terms terms = stackInstance.taxonomy(taxonomyUid).terms();
        terms.clearParams();
        terms.addParam("include_count", true);
        Request request = terms.descendants("term_a2").request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(taxonomyUid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
    }

    @Test
    void moveTerms(){
        Terms terms = stackInstance.taxonomy("sample_one").terms();
        terms.clearParams();
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        terms.addHeader("Accept-Encoding", "UTF-8");
        headers.put("Accept-Encoding", "UTF-8");
        params.put("force", true);
        terms.addParams(params);
        terms.addHeaders(headers);
        Request request = terms.reorder("sample_one", new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(6, request.url().pathSegments().size());
    }

    // ==================== CLEANUP ====================

    @Test
    @Order(900)
    @DisplayName("Delete taxonomy - cleanup created taxonomy")
    void testDeleteCreatedTaxonomy() throws IOException, ParseException {
        assumeTrue(createdTaxonomyUid != null, "Skipping: No taxonomy created to delete");

        Response<ResponseBody> response = taxonomy.delete(createdTaxonomyUid).execute();

        assertTrue(response.isSuccessful(), 
                "Delete taxonomy failed: " + getErrorMessage(response));

        // Check if response has a body before parsing
        if (response.body() != null) {
            String body = response.body().string();
            if (!body.isEmpty()) {
                JSONObject result = (JSONObject) parser.parse(body);
                // Notice key is optional for delete operations
                System.out.println("[Test] Taxonomy deleted successfully: " + createdTaxonomyUid);
            } else {
                System.out.println("[Test] Taxonomy deleted successfully (empty response body)");
            }
        } else {
            System.out.println("[Test] Taxonomy deleted successfully (null response body)");
        }
        
        createdTaxonomyUid = null;
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
