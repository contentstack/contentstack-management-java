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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * API tests for Locale module.
 * Tests CRUD operations on locales with proper validations.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#locale
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocaleAPITest {

    protected static String API_KEY = TestClient.API_KEY;
    protected static Stack stackInstance;
    private JSONParser parser = new JSONParser();
    private String createdLocaleCode;

    @BeforeAll
    public void setupEnv() {
        stackInstance = TestClient.getStack()
                .addHeader(Util.API_KEY, API_KEY)
                .addHeader("api_key", API_KEY);
    }

    // ========== READ OPERATIONS ==========

    @Test
    @Order(100)
    @DisplayName("Find all locales - should return locales array with master locale")
    void testFindLocales() throws IOException, ParseException {
        Response<ResponseBody> response = stackInstance.locale().find().execute();

        assertTrue(response.isSuccessful(), 
                "Find locales failed with code " + response.code() + ": " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        // Validate response structure
        assertTrue(result.containsKey("locales"), "Response should contain 'locales' key");
        JSONArray locales = (JSONArray) result.get("locales");
        assertNotNull(locales, "Locales array should not be null");
        assertFalse(locales.isEmpty(), "Stack should have at least one locale (master)");
        
        // Validate locale structure
        JSONObject firstLocale = (JSONObject) locales.get(0);
        assertTrue(firstLocale.containsKey("code"), "Locale should have 'code' field");
        assertTrue(firstLocale.containsKey("name"), "Locale should have 'name' field");
    }

    @Test
    @Order(101)
    @DisplayName("Fetch single locale - en-us (master locale)")
    void testFetchMasterLocale() throws IOException, ParseException {
        Response<ResponseBody> response = stackInstance.locale("en-us").fetch().execute();

        // en-us might not exist, handle gracefully
        if (response.code() == 404 || response.code() == 422) {
            // Try fetching without code to get any locale
            Response<ResponseBody> findResponse = stackInstance.locale().find().execute();
            if (findResponse.isSuccessful()) {
                String body = findResponse.body().string();
                JSONObject result = (JSONObject) parser.parse(body);
                JSONArray locales = (JSONArray) result.get("locales");
                if (!locales.isEmpty()) {
                    JSONObject firstLocale = (JSONObject) locales.get(0);
                    String code = (String) firstLocale.get("code");
                    
                    // Fetch specific locale
                    response = stackInstance.locale(code).fetch().execute();
                    assertTrue(response.isSuccessful(), 
                            "Fetch locale " + code + " failed: " + getErrorMessage(response));
                }
            }
        } else {
            assertTrue(response.isSuccessful(), 
                    "Fetch locale failed: " + getErrorMessage(response));
            
            String body = response.body().string();
            JSONObject result = (JSONObject) parser.parse(body);
            assertTrue(result.containsKey("locale"), "Response should contain 'locale' key");
            
            JSONObject locale = (JSONObject) result.get("locale");
            assertNotNull(locale.get("code"), "Locale should have code");
            assertNotNull(locale.get("name"), "Locale should have name");
        }
    }

    // ========== CREATE OPERATION ==========

    @Test
    @Order(200)
    @DisplayName("Create locale - should create new locale successfully")
    void testCreateLocale() throws IOException, ParseException {
        JSONObject requestBody = Utils.readJson("locale/french.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject localeData = new JSONObject();
            localeData.put("code", "fr-fr");
            localeData.put("name", "French - France");
            requestBody.put("locale", localeData);
        }
        
        // Try valid but less common locale codes to avoid conflicts
        // Reference: https://www.contentstack.com/docs/developers/multilingual-content/list-of-supported-languages
        String[] fallbackCodes = {"fr-mc", "fr-lu", "lb-lu", "fo-fo", "mt"};
        String[] fallbackNames = {"French - Monaco", "French - Luxembourg", "Luxembourgish - Luxembourg", 
                                  "Faroese - Faroe Islands", "Maltese"};
        
        Response<ResponseBody> response = null;
        boolean localeCreated = false;
        
        // Try fr-fr first (from the JSON file)
        response = stackInstance.locale().create(requestBody).execute();
        
        if (response.isSuccessful()) {
            localeCreated = true;
        } else if (response.code() == 422 || getErrorMessage(response).contains("already exists")) {
            // fr-fr already exists, try fallback codes
            for (int i = 0; i < fallbackCodes.length; i++) {
                JSONObject localeData = (JSONObject) requestBody.get("locale");
                if (localeData != null) {
                    localeData.put("code", fallbackCodes[i]);
                    localeData.put("name", fallbackNames[i]);
                }
                
                response = stackInstance.locale().create(requestBody).execute();
                
                if (response.isSuccessful()) {
                    localeCreated = true;
                    break;
                } else if (response.code() != 422 && !getErrorMessage(response).contains("already exists")) {
                    // Different error, not "already exists" - fail the test
                    break;
                }
            }
        }
        
        if (!localeCreated && (response.code() == 422 || getErrorMessage(response).contains("already exists"))) {
            assumeTrue(false, "Skipping: All test locale codes already exist in stack");
        }

        assertTrue(response.isSuccessful(), 
                "Create locale failed with code " + response.code() + ": " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        // Validate response
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("locale"), "Response should contain created locale");
        
        JSONObject locale = (JSONObject) result.get("locale");
        createdLocaleCode = (String) locale.get("code");
        assertNotNull(createdLocaleCode, "Created locale should have code");
        assertNotNull(locale.get("name"), "Created locale should have name");
        
        System.out.println("[Test] Created locale: " + createdLocaleCode);
    }

    // ========== UPDATE OPERATION ==========

    @Test
    @Order(300)
    @DisplayName("Update locale - should update locale details")
    void testUpdateLocale() throws IOException, ParseException {
        // Skip if no locale was created
        assumeTrue(createdLocaleCode != null, "Skipping: No locale created to update");

        JSONObject requestBody = Utils.readJson("locale/update.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject localeData = new JSONObject();
            localeData.put("name", "French - France Updated");
            requestBody.put("locale", localeData);
        }
        
        Response<ResponseBody> response = stackInstance.locale(createdLocaleCode).update(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update locale failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        assertTrue(result.containsKey("locale"), "Response should contain updated locale");
    }

    // ========== FALLBACK OPERATIONS ==========

    @Test
    @Order(400)
    @DisplayName("Set fallback locale - should configure fallback language")
    void testSetFallbackLocale() throws IOException, ParseException {
        // Skip if no locale was created - fallback can only be set for non-master locales
        assumeTrue(createdLocaleCode != null, 
            "ASSUMPTION FAILED: Cannot set fallback locale. " +
            "PREREQUISITE: A non-master locale must be created first (master locale cannot have fallback). " +
            "CURRENT STATE: createdLocaleCode=" + createdLocaleCode + ". " +
            "DEPENDENCY: This test depends on testCreateLocale() running successfully. " +
            "ACTION: Ensure testCreateLocale() completes successfully before this test runs.");
        
        // First check if locale already has a fallback by fetching it
        Response<ResponseBody> fetchResponse = stackInstance.locale(createdLocaleCode).fetch().execute();
        boolean alreadyHasFallback = false;
        
        if (fetchResponse.isSuccessful()) {
            String fetchBody = fetchResponse.body().string();
            JSONObject fetchResult = (JSONObject) parser.parse(fetchBody);
            if (fetchResult.containsKey("locale")) {
                JSONObject locale = (JSONObject) fetchResult.get("locale");
                Object fallback = locale.get("fallback_locale");
                if (fallback != null && !fallback.toString().isEmpty()) {
                    alreadyHasFallback = true;
                    System.out.println("[Test] Locale " + createdLocaleCode + " already has fallback: " + fallback);
                }
            }
        }
        
        // Create request body dynamically using the created locale
        JSONObject requestBody = new JSONObject();
        JSONObject localeData = new JSONObject();
        localeData.put("code", createdLocaleCode);
        localeData.put("fallback_locale", "en-us");  // Set master locale as fallback
        requestBody.put("locale", localeData);
        
        Response<ResponseBody> response = stackInstance.locale(createdLocaleCode).setFallback(requestBody).execute();

        // If locale already has fallback, try to update it instead (which should work)
        if (!response.isSuccessful()) {
            String error = getErrorMessage(response);
            if (alreadyHasFallback || error.contains("already added") || error.contains("already exists") || response.code() == 247) {
                // Try update fallback instead (which should work even if fallback exists)
                Response<ResponseBody> updateResponse = stackInstance.locale(createdLocaleCode).updateFallback(requestBody).execute();
                
                if (updateResponse.isSuccessful()) {
                    String updateBody = updateResponse.body().string();
                    JSONObject updateResult = (JSONObject) parser.parse(updateBody);
                    assertTrue(updateResult.containsKey("notice"), "Response should contain success notice");
                    System.out.println("[Test] Updated fallback locale en-us for " + createdLocaleCode + " (was already set)");
                    return; // Successfully updated, exit test
                } else {
                    assumeTrue(false, String.format(
                        "ASSUMPTION FAILED: Cannot set or update fallback locale. " +
                        "PREREQUISITE: Locale '%s' should allow fallback configuration. " +
                        "CURRENT STATE: Locale '%s' already has fallback configured (HTTP %d). " +
                        "SET ATTEMPT ERROR: %s. " +
                        "UPDATE ATTEMPT ERROR: %s. " +
                        "DEPENDENCY: This test depends on locale state. " +
                        "ACTION: Either use a different locale or manually remove the existing fallback configuration first.",
                        createdLocaleCode, createdLocaleCode, response.code(), error, getErrorMessage(updateResponse)
                    ));
                }
            }
        }

        assertTrue(response.isSuccessful(), 
                "Set fallback locale failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        System.out.println("[Test] Set fallback locale en-us for " + createdLocaleCode);
    }

    @Test
    @Order(401)
    @DisplayName("Update fallback locale - should update fallback configuration")
    void testUpdateFallbackLocale() throws IOException, ParseException {
        // Skip if no locale was created - fallback can only be updated for non-master locales
        assumeTrue(createdLocaleCode != null, "Skipping: No locale created, cannot update fallback");
        
        // Create request body dynamically using the created locale
        // Try changing fallback to a different valid locale (if exists)
        // For now, try to set it back to en-us or keep the same
        JSONObject requestBody = new JSONObject();
        JSONObject localeData = new JSONObject();
        localeData.put("code", createdLocaleCode);
        localeData.put("fallback_locale", "en-us");  // Keep en-us as fallback
        requestBody.put("locale", localeData);
        
        Response<ResponseBody> response = stackInstance.locale(createdLocaleCode).updateFallback(requestBody).execute();

        assertTrue(response.isSuccessful(), 
                "Update fallback locale failed: " + getErrorMessage(response));
        
        System.out.println("[Test] Updated fallback locale for " + createdLocaleCode);
    }

    // ========== DELETE OPERATION ==========

    @Test
    @Order(900)
    @DisplayName("Delete locale - should remove created locale")
    void testDeleteLocale() throws IOException, ParseException {
        assumeTrue(createdLocaleCode != null, "Skipping: No locale created to delete");

        Response<ResponseBody> response = stackInstance.locale(createdLocaleCode).delete().execute();

        assertTrue(response.isSuccessful(), 
                "Delete locale failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        assertTrue(result.containsKey("notice"), "Response should contain success notice");
        
        // Clear the created locale code
        createdLocaleCode = null;
        System.out.println("[Test] Locale deleted successfully");
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
