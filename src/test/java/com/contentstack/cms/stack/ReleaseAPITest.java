package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReleaseAPITest {

    public static Release releases;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Stack stack;
    private int _COUNT = 2;
    protected String releaseUid;

    @BeforeAll
    public void setUp() {
        stack = TestClient.getStack().addHeader(Util.API_KEY, API_KEY)
                .addHeader("api_key", API_KEY);
                
    }

    @Order(1)
    @Test
    void testCreateRelease() throws IOException {
        JSONObject requestBody = Utils.readJson("releases/create_release1.json");
        releases = stack.releases();
        Response<ResponseBody> response = releases.create(requestBody).execute();
        if (response.isSuccessful()) {

            System.out.println("Release created successfully: " + response.body().string());

        } else {
            System.err.println("Failed to create release: " + response.errorBody().string());
        }
    }

    @Order(2)
    @Test
    void testFetchReleases() throws IOException {
        Response<ResponseBody> response = releases.fetch().execute();
        if (response.isSuccessful()) {
            System.out.println("Fetched releases successfully: " + response.body().string());
        } else {
            System.err.println("Failed to fetch releases: " + response.errorBody().string());
        }
    }
}