package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.models.Error;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.logging.Logger;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

@Tag("api")
class StackAPITest {

    private static final String TAG = StackAPITest.class.getSimpleName();
    private final static Logger log = Logger.getLogger(StackAPITest.class.getName());
    private final String organizationUid = TestClient.ORGANIZATION_UID;
    private final String userId = TestClient.USER_ID;
    private final String apiKey = TestClient.API_KEY;
    private final String ownershipToken = TestClient.OWNERSHIP;
    private Stack stack;

    private JsonObject toJson(Response<ResponseBody> response) throws IOException {
        assert response.body() != null;
        return new Gson().fromJson(response.body().string(), JsonObject.class);
    }

    @BeforeAll
    public void setUp() {
        stack = TestClient.getStack();
    }

    @Test
    void testStackFetchAll() {
        try {
            stack.clearParams();
            assert apiKey != null;
            Response<ResponseBody> response = stack.find().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stacks"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testFetchWithOptionalOrgUid() {
        try {
            assert apiKey != null;
            assert organizationUid != null;
            stack.addHeader("organization_uid", organizationUid);
            Response<ResponseBody> response = stack.find().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stacks"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testFetchWithOptionalQueryParams() {
        try {

            stack.addParam("include_collaborators", true);
            stack.addParam("include_stack_variables", false);
            stack.addParam("include_discrete_variables", true);
            stack.addParam("include_count", false);
            assert organizationUid != null;
            stack.addHeader("organization_uid", organizationUid);
            Response<ResponseBody> response = stack.find().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stacks"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testFetchWithOptionalOrgUidAndQueryParams() {
        try {
            stack.addParam("include_collaborators", true);
            stack.addParam("include_stack_variables", false);
            stack.addParam("include_discrete_variables", true);
            stack.addParam("include_count", false);
            assert apiKey != null;
            assert organizationUid != null;
            stack.addHeader("organization_uid", organizationUid);
            Response<ResponseBody> response = stack.find().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stacks"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Disabled("Avoid creation of stack everytime testcases run")
    @Test
    void testCreateStack() {
        try {
            JSONObject requestBody = Utils.readJson("mockstack/create_stack.json");
            assert organizationUid != null;
            Contentstack client = new Contentstack.Builder().build();
            Response<ResponseBody> response = client.stack().create(organizationUid, requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack"));
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    @Disabled
    void testStackUpdate() {
        try {
            JSONObject requestBody = Utils.readJson("mockstack/update.json");
            Response<ResponseBody> response = stack.update(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack"));
                Assertions.assertTrue(jsonResp.has("notice"));
            }
        } catch (IOException e) {
            log.warning(TAG + ": " + e.getLocalizedMessage());
        }
    }

    @Test
    void testStackTransferOwnership() {
        try {
            JSONObject requestBody = Utils.readJson("mockstack/ownership.json");
            assert apiKey != null;
            Response<ResponseBody> response = stack.transferOwnership(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                assert response.errorBody() != null;
                Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
                int errCode = error.getErrorCode();
                Assertions.assertEquals(105, errCode);
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testStackAcceptOwnership() throws IOException {
        stack.addParam("api_key", apiKey);
        stack.addParam("uid", userId);
        Response<ResponseBody> response = stack.acceptOwnership(ownershipToken).execute();
        if (response.isSuccessful()) {
            JsonObject jsonResp = toJson(response);
            Assertions.assertTrue(jsonResp.has("notice"));
        } else {
            Assertions.assertEquals("/v3/stacks/accept_ownership/" + ownershipToken,
                    response.raw().request()
                            .url()
                            .encodedPath());
            Assertions.assertTrue(
                    response.raw().request()
                            .url()
                            .queryParameterNames()
                            .contains("uid"));
            Assertions.assertTrue(
                    response.raw().request()
                            .url()
                            .queryParameterNames()
                            .contains("api_key"));
            Assertions.assertEquals(
                    "uid=" + userId + "&api_key=" +
                            apiKey,
                    response.raw()
                            .request()
                            .url()
                            .query());
            Assertions.assertEquals(6,
                    response.raw().request().headers().size());

        }

    }

    @Test
    void testStackSetting() {
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stack.setting().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack_settings"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testStackUpdateSetting() {
        JSONObject requestBody = Utils.readJson("mockstack/setting.json");
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stack.updateSetting(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
                Assertions.assertTrue(jsonResp.has("stack_settings"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testStackResetSetting() {
        JSONObject requestBody = Utils.readJson("mockstack/setting.json");
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stack.updateSetting(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
                Assertions.assertTrue(jsonResp.has("stack_settings"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testStackShare() {
        JSONObject requestBody = Utils.readJson("mockstack/share_stack.json");
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stack.share(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Assertions.assertEquals("/v3/stacks/share",
                        response.raw().request().url().encodedPath());
                Assertions.assertEquals(6,
                        response.raw().request().headers().size());
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testStackUnshare() {
        JSONObject requestBody = Utils.readJson("mockstack/unshare.json");
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stack.unshare(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Assertions.assertEquals("/v3/stacks/unshare",
                        response.raw().request().url().encodedPath());
                Assertions.assertEquals(6,
                        response.raw().request().headers().size());
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testStackAllUsers() throws IOException {

        assert apiKey != null;
        assert userId != null;
        assert ownershipToken != null;
        Response<ResponseBody> response = stack.allUsers().execute();
        if (response.isSuccessful()) {
            JsonObject jsonResp = toJson(response);
            Assertions.assertTrue(jsonResp.has("stacks"));
        } else {
            Assertions.assertTrue(true,
                    response.raw().request().url()
                            .queryParameter("include_collaborators"));
        }

    }

    @Test
    void testStackRole() {
        JSONObject requestBody = Utils.readJson("mockstack/update_user_role.json");
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stack.roles(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
                Assertions.assertTrue(jsonResp.has("users"));
            } else {
                Assertions.assertEquals("/v3/stacks/users/roles",
                        response.raw().request().url().encodedPath());
                Assertions.assertEquals(6,
                        response.raw().request().headers().size());
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

}
