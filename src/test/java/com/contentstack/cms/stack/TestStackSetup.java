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
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Shared test setup utility to create prerequisites in the stack.
 * Creates: Global Fields → Content Types → Entries
 * 
 * This ensures all tests have the required dependencies and don't skip.
 */
public class TestStackSetup {
    
    private static final String API_KEY = TestClient.API_KEY;
    private static final String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    private static final String AUTHTOKEN = TestClient.AUTHTOKEN;
    
    private static Stack stackInstance;
    private static JSONParser parser = new JSONParser();
    
    // Shared test data - created once and reused
    public static String testGlobalFieldUid;
    public static String testContentTypeUid;
    public static String testEntryUid;
    
    /**
     * Initialize stack instance for test setup
     */
    public static Stack getStackInstance() {
        if (stackInstance == null) {
            HashMap<String, Object> headers = new HashMap<>();
            headers.put(Util.API_KEY, API_KEY);
            headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
            stackInstance = new Contentstack.Builder()
                    .setAuthtoken(AUTHTOKEN)
                    .setHost(TestClient.DEV_HOST)
                    .build()
                    .stack(headers);
        }
        return stackInstance;
    }
    
    /**
     * Create a global field if one doesn't exist.
     * Returns existing global field UID if found, otherwise creates a new one.
     */
    public static String ensureGlobalFieldExists() throws IOException, ParseException {
        Stack stack = getStackInstance();
        
        // First, try to find existing global field
        Response<ResponseBody> findResponse = stack.globalField().find().execute();
        if (!findResponse.isSuccessful()) {
            String error = findResponse.errorBody() != null ? findResponse.errorBody().string() : "Unknown error";
            System.err.println("[TestStackSetup] Find global fields failed (HTTP " + findResponse.code() + "): " + error);
            // Continue to try creating - might be empty stack
        }
        if (findResponse.isSuccessful()) {
            String findBody = findResponse.body().string();
            JSONObject result = (JSONObject) parser.parse(findBody);
            JSONArray globalFields = (JSONArray) result.get("global_fields");
            
            if (globalFields != null && !globalFields.isEmpty()) {
                JSONObject firstGF = (JSONObject) globalFields.get(0);
                String existingUid = (String) firstGF.get("uid");
                System.out.println("[TestStackSetup] Using existing global field: " + existingUid);
                testGlobalFieldUid = existingUid;
                return existingUid;
            }
        }
        
        // No existing global field, create one
        String uniqueUid = "test_gf_setup_" + UUID.randomUUID().toString().substring(0, 8).replace("-", "_");
        
        JSONObject requestBody = Utils.readJson("globalfield/seo_create.json");
        if (requestBody == null) {
            // Create minimal global field if mock file not available
            @SuppressWarnings("unchecked")
            JSONObject newRequestBody = new JSONObject();
            @SuppressWarnings("unchecked")
            JSONObject globalField = new JSONObject();
            globalField.put("title", "Test Global Field Setup");
            globalField.put("uid", uniqueUid);
            globalField.put("description", "Global field created for test setup");
            
            @SuppressWarnings("unchecked")
            JSONArray schema = new JSONArray();
            @SuppressWarnings("unchecked")
            JSONObject field = new JSONObject();
            field.put("display_name", "Test Field");
            field.put("uid", "test_field");
            field.put("data_type", "text");
            field.put("mandatory", false);
            field.put("multiple", false);
            schema.add(field);
            globalField.put("schema", schema);
            
            newRequestBody.put("global_field", globalField);
            requestBody = newRequestBody;
        } else {
            JSONObject globalField = (JSONObject) requestBody.get("global_field");
            globalField.put("uid", uniqueUid);
            globalField.put("title", "Test Global Field Setup");
        }
        
        System.out.println("[TestStackSetup] Attempting to create global field with UID: " + uniqueUid);
        System.out.println("[TestStackSetup] Using HOST: " + TestClient.DEV_HOST);
        System.out.println("[TestStackSetup] Using API_KEY: " + API_KEY.substring(0, Math.min(8, API_KEY.length())) + "...");
        
        Response<ResponseBody> createResponse = stack.globalField().create(requestBody).execute();
        
        if (createResponse.isSuccessful()) {
            String createBody = createResponse.body().string();
            JSONObject result = (JSONObject) parser.parse(createBody);
            JSONObject created = (JSONObject) result.get("global_field");
            testGlobalFieldUid = (String) created.get("uid");
            System.out.println("[TestStackSetup] ✅ Created global field: " + testGlobalFieldUid);
            return testGlobalFieldUid;
        } else {
            String error = createResponse.errorBody() != null ? createResponse.errorBody().string() : "Unknown error";
            System.err.println("[TestStackSetup] ❌ Failed to create global field (HTTP " + createResponse.code() + "): " + error);
            throw new IOException("Failed to create global field: " + error);
        }
    }
    
    /**
     * Create a content type if one doesn't exist.
     * Returns existing content type UID if found, otherwise creates a new one.
     */
    public static String ensureContentTypeExists() throws IOException, ParseException {
        Stack stack = getStackInstance();
        
        // First, try to find existing content type
        Response<ResponseBody> findResponse = stack.contentType().find().execute();
        if (!findResponse.isSuccessful()) {
            String error = findResponse.errorBody() != null ? findResponse.errorBody().string() : "Unknown error";
            System.err.println("[TestStackSetup] Find content types failed (HTTP " + findResponse.code() + "): " + error);
            // Continue to try creating - might be empty stack
        }
        if (findResponse.isSuccessful()) {
            String findBody = findResponse.body().string();
            JSONObject result = (JSONObject) parser.parse(findBody);
            JSONArray contentTypes = (JSONArray) result.get("content_types");
            
            if (contentTypes != null && !contentTypes.isEmpty()) {
                JSONObject firstCT = (JSONObject) contentTypes.get(0);
                String existingUid = (String) firstCT.get("uid");
                System.out.println("[TestStackSetup] Using existing content type: " + existingUid);
                testContentTypeUid = existingUid;
                return existingUid;
            }
        }
        
        // No existing content type, create one
        String uniqueUid = "test_ct_setup_" + UUID.randomUUID().toString().substring(0, 8).replace("-", "_");
        
        JSONObject requestBody = Utils.readJson("contenttype/simple_create.json");
        if (requestBody == null) {
            // Create minimal content type if mock file not available
            @SuppressWarnings("unchecked")
            JSONObject newRequestBody = new JSONObject();
            @SuppressWarnings("unchecked")
            JSONObject contentType = new JSONObject();
            contentType.put("title", "Test Content Type Setup");
            contentType.put("uid", uniqueUid);
            contentType.put("description", "Content type created for test setup");
            
            @SuppressWarnings("unchecked")
            JSONArray schema = new JSONArray();
            @SuppressWarnings("unchecked")
            JSONObject titleField = new JSONObject();
            requestBody = newRequestBody;
            titleField.put("display_name", "Title");
            titleField.put("uid", "title");
            titleField.put("data_type", "text");
            titleField.put("mandatory", true);
            titleField.put("multiple", false);
            schema.add(titleField);
            
            contentType.put("schema", schema);
            requestBody.put("content_type", contentType);
        } else {
            JSONObject contentType = (JSONObject) requestBody.get("content_type");
            contentType.put("uid", uniqueUid);
            contentType.put("title", "Test Content Type Setup");
        }
        
        System.out.println("[TestStackSetup] Attempting to create content type with UID: " + uniqueUid);
        System.out.println("[TestStackSetup] Using HOST: " + TestClient.DEV_HOST);
        System.out.println("[TestStackSetup] Using API_KEY: " + API_KEY.substring(0, Math.min(8, API_KEY.length())) + "...");
        
        Response<ResponseBody> createResponse = stack.contentType().create(requestBody).execute();
        
        if (createResponse.isSuccessful()) {
            String createBody = createResponse.body().string();
            JSONObject result = (JSONObject) parser.parse(createBody);
            JSONObject created = (JSONObject) result.get("content_type");
            testContentTypeUid = (String) created.get("uid");
            System.out.println("[TestStackSetup] ✅ Created content type: " + testContentTypeUid);
            return testContentTypeUid;
        } else {
            String error = createResponse.errorBody() != null ? createResponse.errorBody().string() : "Unknown error";
            System.err.println("[TestStackSetup] ❌ Failed to create content type (HTTP " + createResponse.code() + "): " + error);
            throw new IOException("Failed to create content type: " + error);
        }
    }
    
    /**
     * Create an entry if one doesn't exist for the given content type.
     * Returns existing entry UID if found, otherwise creates a new one.
     */
    public static String ensureEntryExists(String contentTypeUid) throws IOException, ParseException {
        Stack stack = getStackInstance();
        
        if (contentTypeUid == null) {
            contentTypeUid = ensureContentTypeExists();
        }
        
        // First, try to find existing entry
        Response<ResponseBody> findResponse = stack.contentType(contentTypeUid).entry().find().execute();
        if (findResponse.isSuccessful()) {
            String findBody = findResponse.body().string();
            JSONObject result = (JSONObject) parser.parse(findBody);
            JSONArray entries = (JSONArray) result.get("entries");
            
            if (entries != null && !entries.isEmpty()) {
                JSONObject firstEntry = (JSONObject) entries.get(0);
                String existingUid = (String) firstEntry.get("uid");
                System.out.println("[TestStackSetup] Using existing entry: " + existingUid);
                testEntryUid = existingUid;
                return existingUid;
            }
        }
        
        // No existing entry, create one
        @SuppressWarnings("unchecked")
        JSONObject requestBody = new JSONObject();
        @SuppressWarnings("unchecked")
        JSONObject entry = new JSONObject();
        entry.put("title", "Test Entry Setup " + System.currentTimeMillis());
        requestBody.put("entry", entry);
        
        System.out.println("[TestStackSetup] Attempting to create entry for content type: " + contentTypeUid);
        System.out.println("[TestStackSetup] Using HOST: " + TestClient.DEV_HOST);
        
        Response<ResponseBody> createResponse = stack.contentType(contentTypeUid).entry().create(requestBody).execute();
        
        if (createResponse.isSuccessful()) {
            String createBody = createResponse.body().string();
            JSONObject result = (JSONObject) parser.parse(createBody);
            JSONObject created = (JSONObject) result.get("entry");
            testEntryUid = (String) created.get("uid");
            System.out.println("[TestStackSetup] ✅ Created entry: " + testEntryUid);
            return testEntryUid;
        } else {
            String error = createResponse.errorBody() != null ? createResponse.errorBody().string() : "Unknown error";
            System.err.println("[TestStackSetup] ❌ Failed to create entry (HTTP " + createResponse.code() + "): " + error);
            throw new IOException("Failed to create entry: " + error);
        }
    }
    
    /**
     * Setup all prerequisites: Global Field → Content Type → Entry
     * Call this in @BeforeAll of test classes that need these prerequisites.
     */
    public static void setupAllPrerequisites() throws IOException, ParseException {
        System.out.println("[TestStackSetup] Setting up all prerequisites...");
        ensureGlobalFieldExists();
        ensureContentTypeExists();
        ensureEntryExists(testContentTypeUid);
        System.out.println("[TestStackSetup] Setup complete. GlobalField: " + testGlobalFieldUid + 
                          ", ContentType: " + testContentTypeUid + ", Entry: " + testEntryUid);
    }
}
