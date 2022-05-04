package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import com.contentstack.cms.models.Error;
import com.contentstack.cms.core.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StackAPITest {

    private static final String TAG = StackAPITest.class.getSimpleName();
    private final static Logger log = Logger.getLogger(StackAPITest.class.getName());
    // log.warning(e.getLocalizedMessage().toString());

    private Stack stackInstance;
    private final String organizationUid = Dotenv.load().get("organizationUid");
    private final String userId = Dotenv.load().get("userId");
    private final String apiKey = Dotenv.load().get("api_key");
    private final String ownershipToken = Dotenv.load().get("ownershipToken");

    private JsonObject toJson(Response<ResponseBody> response) throws IOException {
        assert response.body() != null;
        return new Gson().fromJson(response.body().string(), JsonObject.class);
    }

    @BeforeAll
    public void setUp() throws IOException {
        Contentstack contentstack = new Contentstack.Builder().build();
        String emailId = Dotenv.load().get("username");
        String password = Dotenv.load().get("password");
        contentstack.login(emailId, password);
        assert apiKey != null;
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, apiKey);
        stackInstance = contentstack.stack(headers);
    }

    @Test
    void testStackFetchAll() {
        try {
            assert apiKey != null;
            Response<ResponseBody> response = stackInstance.fetch().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                log.fine(jsonResp.get("stack").getAsJsonObject().toString());
                Assertions.assertTrue(jsonResp.has("stack"));
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
            Response<ResponseBody> response = stackInstance.fetch(organizationUid).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testFetchWithOptionalQueryParams() {
        try {
            Map<String, Boolean> query = new HashMap<>();
            query.put("include_collaborators", true);
            query.put("include_stack_variables", false);
            query.put("include_discrete_variables", true);
            query.put("include_count", false);
            assert organizationUid != null;
            Response<ResponseBody> response = stackInstance.fetch(organizationUid, query).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack"));
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testFetchWithOptionalOrgUidAndQueryParams() {
        try {
            Map<String, Boolean> query = new HashMap<>();
            query.put("include_collaborators", true);
            query.put("include_stack_variables", false);
            query.put("include_discrete_variables", true);
            query.put("include_count", false);
            assert apiKey != null;
            assert organizationUid != null;
            Response<ResponseBody> response = stackInstance.fetch(organizationUid, query).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack"));
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
                log.info("Stack Created");
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
    void testStackUpdate() {
        try {
            JSONObject requestBody = Utils.readJson("mockstack/update.json");
            Response<ResponseBody> response = stackInstance.update(requestBody).execute();
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
            Response<ResponseBody> response = stackInstance.transferOwnership(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                assert response.errorBody() != null;
                Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
                int errCode = error.getErrorCode();
                String errMessage = error.getErrorMessage();
                Assertions.assertEquals(109, errCode);
                Assertions.assertEquals(
                        "Sorry about that. Provided email is not a part of the organization.",
                        errMessage);
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

    @Test
    void testStackAcceptOwnership() throws IOException {
        assert apiKey != null;
        assert userId != null;
        assert ownershipToken != null;
        Response<ResponseBody> response = stackInstance.acceptOwnership(ownershipToken, userId).execute();
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
            Assertions.assertEquals(4,
                    response.raw().request().headers().size());

        }

    }

    @Test
    void testStackSetting() {
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.setting().execute();
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
            Response<ResponseBody> response = stackInstance.updateSetting(requestBody).execute();
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
            Response<ResponseBody> response = stackInstance.updateSetting(requestBody).execute();
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
            Response<ResponseBody> response = stackInstance.share(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Assertions.assertEquals("/v3/stacks/share",
                        response.raw().request().url().encodedPath());
                Assertions.assertEquals(5,
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
            Response<ResponseBody> response = stackInstance.unshare(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Assertions.assertEquals("/v3/stacks/unshare",
                        response.raw().request().url().encodedPath());
                Assertions.assertEquals(5,
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
        Response<ResponseBody> response = stackInstance.allUsers().execute();
        if (response.isSuccessful()) {
            JsonObject jsonResp = toJson(response);
            Assertions.assertTrue(jsonResp.has("stack"));
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
            Response<ResponseBody> response = stackInstance.roles(requestBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
                Assertions.assertTrue(jsonResp.has("users"));
            } else {
                Assertions.assertEquals("/v3/stacks/users/roles",
                        response.raw().request().url().encodedPath());
                Assertions.assertEquals(5,
                        response.raw().request().headers().size());
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

}
