package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Error;
import com.contentstack.cms.core.RetryCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StackAPITest {

    private static final String TAG = StackAPITest.class.getSimpleName();
    private final Logger log = Logger.getLogger(RetryCallback.class.getName());

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
        stackInstance = contentstack.stack(apiKey);
    }

    @Test
    void testFetch() {
        try {
            assert apiKey != null;
            Response<ResponseBody> response = stackInstance.fetch().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                log.fine(jsonResp.get("stack").getAsJsonObject().toString());
                Assertions.assertTrue(jsonResp.has("stack"));
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreate() {
        try {

            String strBody = "{\n" +
                    "  \"stack\": {\n" +
                    "    \"name\": \"stack@create\",\n" +
                    "    \"description\": \"My new test stack\",\n" +
                    "    \"master_locale\": \"en-us\"\n" +
                    "  }\n" +
                    "}";
            assert organizationUid != null;
            Response<ResponseBody> response = stackInstance.create(organizationUid, strBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack"));
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testUpdate() {
        try {
            String strBody = "{\n" +
                    "\t\"stack\": {\n" +
                    "\t\t\"name\": \"ishaileshmishra@Update\",\n" +
                    "\t\t\"description\": \"My new test stack\"\n" +
                    "\t}\n" +
                    "}";
            Response<ResponseBody> response = stackInstance.update(strBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack"));
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTransferOwnership() {
        try {
            String strBody = "{\n" +
                    "\t\"transfer_to\": \"manager@example.com\"\n" +
                    "}";
            assert apiKey != null;
            Response<ResponseBody> response = stackInstance.transferOwnership(strBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
            } else {
                Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
                int errCode = error.getErrorCode();
                String errMessage = error.getErrorMessage();
                Assertions.assertEquals(141, errCode);
                Assertions.assertEquals("Sorry about that. Provided email is not a part of the organization.", errMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAcceptOwnership() throws IOException {
        // https://api.contentstack.io/v3/stacks/accept_ownership/blt2add6864996aa9f2?uid=bltc11e668e0295477f&api_key=blt4ae7bcd5e85cb660

        assert apiKey != null;
        assert userId != null;
        assert ownershipToken != null;
        Response<ResponseBody> response = stackInstance.acceptOwnership(ownershipToken, userId).execute();
        if (response.isSuccessful()) {
            JsonObject jsonResp = toJson(response);
            Assertions.assertTrue(jsonResp.has("notice"));
        } else {
            Assertions.assertEquals("/v3/stacks/accept_ownership/" + ownershipToken,
                    response.raw().
                            request()
                            .url()
                            .encodedPath().toString());
            Assertions.assertTrue(
                    response.raw().
                            request()
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
                            apiKey, response.raw()
                            .request()
                            .url()
                            .query());
            Assertions.assertEquals(4,
                    response.raw().request().headers().size());

        }

    }

    @Test
    void testSetting() {
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.setting().execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stack_settings"));
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateSetting() {
        String strBody = "{\n" +
                "  \"stack_settings\":{\n" +
                "    \"stack_variables\":{\n" +
                "      \"enforce_unique_urls\":true,\n" +
                "      \"sys_rte_allowed_tags\":\"style | figure | script\"\n" +
                "    },\n" +
                "    \"rte\":{\n" +
                "      \"cs_only_breakline\":true\n" +
                "    }\n" +
                "  }\n" +
                "}";
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.updateSetting(strBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
                Assertions.assertTrue(jsonResp.has("stack_settings"));
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testResetSetting() {
        String strBody = "{\n" +
                "    \"stack_settings\":{\n" +
                "        \"discrete_variables\":{},\n" +
                "        \"stack_variables\":{}\n" +
                "    }\n" +
                "}";
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.updateSetting(strBody).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("notice"));
                Assertions.assertTrue(jsonResp.has("stack_settings"));
            } else {
                Assertions.fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testShare() {
        String strBody = "{\n" +
                "\t\"emails\": [\n" +
                "\t\t\"manager@example.com\"\n" +
                "\t],\n" +
                "\t\"roles\": {\n" +
                "\t\t\"manager@example.com\": [\n" +
                "\t\t\t\"abcdefhgi1234567890\"\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.share(strBody).execute();
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
            e.printStackTrace();
        }
    }

    @Test
    void testUnshare() {
        String strBody = "{\n" +
                "\t\"email\": \"shailesh.mishra@contentstack.com\"\n" +
                "}";
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.unshare(strBody).execute();
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
            e.printStackTrace();
        }
    }

    @Test
    void testAllUsers() throws IOException {

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
    void testRole() {
        String strBody = "{\n" +
                "\t\"users\": {\n" +
                "\t\t\"user_uid\": [\"role_uid1\", \"role_uid2\"]\n" +
                "\t}\n" +
                "}";
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.roles(strBody).execute();
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
            e.printStackTrace();
        }
    }


}
