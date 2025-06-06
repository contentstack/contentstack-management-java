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

@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ReleaseAPITest {

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
    }

    @Order(1)
    @Test
    void testCreateRelease() throws IOException, ParseException {
        JSONObject requestBody = Utils.readJson("releases/create_release1.json");
        Response<ResponseBody> response = stack.releases().create(requestBody).execute();

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
        assertNotNull(releaseUid1, "Release UID should be available for updating");

        JSONObject requestBody = Utils.readJson("releases/update_release1.json");

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
        JSONObject requestBody = Utils.readJson("releases/create_release1.json");
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
        JSONObject requestBody = Utils.readJson("releases/create_release1.json");

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
        assertNotNull(releaseUid2, "Release UID should be available for deletion");

        Response<ResponseBody> response = stack.releases(releaseUid2).delete().execute();

        assertTrue(response.isSuccessful(), "Release deletion should be successful");

        String responseString = response.body().string();
        JSONObject responseObject = (JSONObject) parser.parse(responseString);

        assertEquals("Release deleted successfully.", responseObject.get("notice"), "Success notice should be present");
    }
}