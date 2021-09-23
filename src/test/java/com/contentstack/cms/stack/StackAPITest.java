package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StackAPITest {

    private Stack stackInstance;
    private final String organizationUid = Dotenv.load().get("organizations_uid");
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
        stackInstance = contentstack.stack();
    }


    @Test
    void testFetch() {
        try {
            assert apiKey != null;
            Response<ResponseBody> response = stackInstance.fetch(apiKey).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stacks"));
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
            Response<ResponseBody> response = stackInstance.fetch(apiKey, organizationUid).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResp = toJson(response);
                Assertions.assertTrue(jsonResp.has("stacks"));
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
            assert apiKey != null;
            Response<ResponseBody> response = stackInstance.fetch(apiKey, query).execute();
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
            Response<ResponseBody> response = stackInstance.fetch(apiKey, organizationUid, query).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testCreate() {
        try {
            String strBody = "{\n" +
                    "  \"stack\": {\n" +
                    "    \"name\": \"Stack by ishaileshmishra\",\n" +
                    "    \"description\": \"My new test stack created by ishaileshmishra\",\n" +
                    "    \"master_locale\": \"en-us\"\n" +
                    "  }\n" +
                    "}";
            assert organizationUid != null;
            Response<ResponseBody> response = stackInstance.create(organizationUid, strBody).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testUpdate() {
        try {
            String strBody = "{\n" +
                    "  \"stack\": {\n" +
                    "    \"name\": \"Stack by ishaileshmishra\",\n" +
                    "    \"description\": \"My new test stack created by ishaileshmishra\",\n" +
                    "    \"master_locale\": \"en-us\"\n" +
                    "  }\n" +
                    "}";
            assert apiKey != null;
            Response<ResponseBody> response = stackInstance.update(apiKey, strBody).execute();
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
            Response<ResponseBody> response = stackInstance.transferOwnership(apiKey, strBody).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testAcceptOwnership() {
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.acceptOwnership(ownershipToken, apiKey, userId).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testSetting() {
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.setting(apiKey).execute();
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
            Response<ResponseBody> response = stackInstance.updateSetting(apiKey, strBody).execute();
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
            Response<ResponseBody> response = stackInstance.updateSetting(apiKey, strBody).execute();
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
            Response<ResponseBody> response = stackInstance.share(apiKey, strBody).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testUnshare() {
        String strBody = "{\n" +
                "\t\"email\": \"manager@example.com\"\n" +
                "}";
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.unshare(apiKey, strBody).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testAllUsers() {
        try {
            assert apiKey != null;
            assert userId != null;
            assert ownershipToken != null;
            Response<ResponseBody> response = stackInstance.allUsers(apiKey).execute();
        } catch (IOException e) {
            e.printStackTrace();
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
            Response<ResponseBody> response = stackInstance.roles(apiKey, strBody).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
