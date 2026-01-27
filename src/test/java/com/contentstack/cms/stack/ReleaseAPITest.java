package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ReleaseAPITest {

    public static Release releases;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Stack stack;
    protected String releaseUid1;
    protected String releaseUid2;
    JSONParser parser = new JSONParser();

    @BeforeAll
    public void setUp() {
        stack = TestClient.getStack().addHeader(Util.API_KEY, API_KEY)
                .addHeader("api_key", API_KEY);
        
        // Clean up any existing releases with our test names to avoid conflicts
        try {
            Response<ResponseBody> response = stack.releases().find().execute();
            if (response.isSuccessful() && response.body() != null) {
                String responseString = response.body().string();
                JSONObject responseObject = (JSONObject) parser.parse(responseString);
                org.json.simple.JSONArray releases = (org.json.simple.JSONArray) responseObject.get("releases");
                
                if (releases != null) {
                    for (Object obj : releases) {
                        JSONObject release = (JSONObject) obj;
                        String name = (String) release.get("name");
                        String uid = (String) release.get("uid");
                        
                        // Delete test releases
                        if (name != null && (name.equals("First release") || 
                            name.equals("First release update") ||
                            name.startsWith("Q1 2024"))) {
                            try {
                                stack.releases(uid).delete().execute();
                                System.out.println("[Setup] Cleaned up existing release: " + name);
                            } catch (Exception e) {
                                // Ignore cleanup errors
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[Setup] Could not clean up releases: " + e.getMessage());
        }
    }

    @Order(1)
    @Test
    void testCreateRelease() throws IOException, ParseException {
        JSONObject requestBody = Utils.readJson("release/create.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject releaseData = new JSONObject();
            releaseData.put("name", "First release");
            releaseData.put("description", "Adding release date");
            requestBody.put("release", releaseData);
        }
        
        Response<ResponseBody> response = stack.releases().create(requestBody).execute();

        // Skip test if Releases V2 is not enabled
        if (!response.isSuccessful() && response.code() == 403) {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
            if (errorBody.contains("Releases V2 is not included in your plan")) {
                assumeTrue(false, "Skipping: Releases V2 not enabled for test credentials");
            }
        }
        
        assertTrue(response.isSuccessful(), "Release creation should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);
        JSONObject releaseObject = (JSONObject) responseObject.get("release");
        releaseUid1 = (String) releaseObject.get("uid");

        assertNotNull(releaseUid1, "Release UID should not be null");
        assertEquals("First release", releaseObject.get("name"), "Release name should match");
        assertEquals("Adding release date", releaseObject.get("description"),
                "Release description should match");
        assertEquals("Release created successfully.", responseObject.get("notice"), "Success notice should be present");
    }

    @Order(2)
    @Test
    void testFindReleases() throws IOException, ParseException {
        Response<ResponseBody> response = stack.releases().find().execute();

        // Skip test if Releases V2 is not enabled
        if (!response.isSuccessful() && response.code() == 403) {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
            if (errorBody.contains("Releases V2 is not included in your plan")) {
                assumeTrue(false, "Skipping: Releases V2 not enabled for test credentials");
            }
        }
        
        assertTrue(response.isSuccessful(), "Fetch releases should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);
        org.json.simple.JSONArray releases = (org.json.simple.JSONArray) responseObject.get("releases");

        assertNotNull(releases, "Releases array should not be null");
        assertFalse(releases.isEmpty(), "Releases array should not be empty");

        boolean foundRelease = false;
        for (Object obj : releases) {
            JSONObject release = (JSONObject) obj;
            if (releaseUid1.equals(release.get("uid"))) {
                foundRelease = true;
                assertEquals("First release", release.get("name"), "Release name should match");
                break;
            }
        }

        assertTrue(foundRelease, "Created release should be present in the releases list");
    }

    @Test
    @Order(3)
    void testUpdateRelease() throws IOException, ParseException {
        assumeTrue(releaseUid1 != null, "Skipping: Release UID not available (previous test may have failed)");
        assertNotNull(releaseUid1, "Release UID should be available for updating");

        JSONObject requestBody = Utils.readJson("release/update.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject releaseData = new JSONObject();
            releaseData.put("name", "First release update");
            releaseData.put("description", "Adding release date");
            requestBody.put("release", releaseData);
        }

        Response<ResponseBody> response = stack.releases(releaseUid1).update(requestBody).execute();
        assertTrue(response.isSuccessful(), "Release update should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);
        JSONObject releaseObject = (JSONObject) responseObject.get("release");
        assertNotNull(releaseObject, "Release object should not be null");
        assertEquals(releaseUid1, releaseObject.get("uid"), "Release UID should match");
        assertEquals("First release update", releaseObject.get("name"), "Release name should match");
        assertEquals("Adding release date", releaseObject.get("description"),
                "Release description should match");
        assertEquals("Release updated successfully.", responseObject.get("notice"), "Success notice should be present");
    }

    @Test
    @Order(4)
    void testFetchReleaseByUid() throws IOException, ParseException {
        assumeTrue(releaseUid1 != null, "Skipping: Release UID not available (previous test may have failed)");
        assertNotNull(releaseUid1, "Release UID should be available for fetching");

        Response<ResponseBody> response = stack.releases(releaseUid1).fetch().execute();

        assertTrue(response.isSuccessful(), "Fetch release by UID should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);
        JSONObject releaseObject = (JSONObject) responseObject.get("release");

        assertNotNull(releaseObject, "Release object should not be null");
        assertEquals(releaseUid1, releaseObject.get("uid"), "Release UID should match");
        assertEquals("First release update", releaseObject.get("name"), "Release name should match");
    }

    @Order(5)
    @Test
    void testCloneRelease() throws IOException, ParseException {
        assumeTrue(releaseUid1 != null, "Skipping: Release UID not available (previous test may have failed)");
        JSONObject requestBody = Utils.readJson("release/create.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject releaseData = new JSONObject();
            releaseData.put("name", "Cloned Release");
            requestBody.put("release", releaseData);
        }
        
        Response<ResponseBody> response = stack.releases(releaseUid1).clone(requestBody).execute();

        assertTrue(response.isSuccessful(), "Clone release should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);
        JSONObject releaseObject = (JSONObject) responseObject.get("release");
        releaseUid2 = (String) releaseObject.get("uid");

        assertNotNull(releaseUid2, "Clone release UID should not be null");
        assertEquals("First release", releaseObject.get("name"), "Clone release name should match");
        assertEquals("Adding release date", releaseObject.get("description"),
                "Second release description should match");
        assertEquals("Release cloned successfully.", responseObject.get("notice"), "Success notice should be present");
    }

    @Order(6)
    @Test
    void testDeployRelease() throws IOException, ParseException {
        assumeTrue(releaseUid1 != null, "Skipping: Release UID not available (previous test may have failed)");
        JSONObject requestBody = Utils.readJson("release/create.json");
        
        // Create minimal request body if JSON file is missing
        if (requestBody == null) {
            requestBody = new JSONObject();
            JSONObject releaseData = new JSONObject();
            releaseData.put("name", "Deployment Release");
            requestBody.put("release", releaseData);
        }

        assertNotNull(releaseUid2, "Release UID should be available for deployment");
        Request request = stack.releases(releaseUid1).deploy(requestBody).request();
        Assertions.assertEquals("POST", request.method(), "Request method should be PUT");
        Assertions.assertTrue(request.url().toString().contains(releaseUid1),
                "Request URL should contain the release UID");
        Assertions.assertEquals("releases", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
    }

    @Order(7)
    @Test
    void testDeleteRelease1() throws IOException, ParseException {
        assumeTrue(releaseUid1 != null, "Skipping: Release UID not available (previous test may have failed)");
        assertNotNull(releaseUid1, "Release UID should be available for deletion");

        Response<ResponseBody> response = stack.releases(releaseUid1).delete().execute();

        assertTrue(response.isSuccessful(), "Release deletion should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);

        assertEquals("Release deleted successfully.", responseObject.get("notice"), "Success notice should be present");
    }

    @Order(8)
    @Test
    void testDeleteRelease2() throws IOException, ParseException {
        assumeTrue(releaseUid2 != null, "Skipping: Release UID not available (previous test may have failed)");
        assertNotNull(releaseUid2, "Release UID should be available for deletion");

        Response<ResponseBody> response = stack.releases(releaseUid2).delete().execute();

        assertTrue(response.isSuccessful(), "Release deletion should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);

        assertEquals("Release deleted successfully.", responseObject.get("notice"), "Success notice should be present");
    }
}