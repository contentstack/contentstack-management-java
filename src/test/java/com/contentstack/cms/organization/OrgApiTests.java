package com.contentstack.cms.organization;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.models.Error;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("api")
public class OrgApiTests {

    private String organizationUid;
    private static Contentstack contentstack;
    private static Organization organization;

    private JSONObject theJSONBody(@NotNull String _body) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(_body);
        } catch (ParseException e) {
            return null;
        }
    }

    private JsonObject toJson(Response<ResponseBody> response) throws IOException {
        assert response.body() != null;
        return new Gson().fromJson(response.body().string(), JsonObject.class);
    }

    @BeforeAll
    public static void setUp() throws IOException {
        String emailId = Dotenv.load().get("username");
        String password = Dotenv.load().get("password");
        contentstack = new Contentstack.Builder().build();
        contentstack.login(emailId, password);
        organization = contentstack.organization();
    }

    @Order(1)
    @Test
    void testGetAll() throws IOException {
        Response<ResponseBody> response = organization.find().execute();
        if (response.isSuccessful()) {
            JSONObject jsonOrgs = theJSONBody(response.body().string());
            JSONArray array = (JSONArray) jsonOrgs.get("organizations");
            organizationUid = ((JSONObject) array.get(0)).get("uid").toString();
            organization = contentstack.organization(organizationUid);
            Assertions.assertTrue(array.size() > 0);
        } else {
            Assertions.fail("should be passed");
        }
    }

    @Order(2)
    @Test
    void testGetAllWithParams() throws IOException {
        organization.addParam("include_plan", true);
        Response<ResponseBody> response = organization.find().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("organizations"));
            JsonElement planId = respJson.get("organizations").getAsJsonArray().get(0);
            JsonObject jsonPlanId = planId.getAsJsonObject();
            Assertions.assertTrue(jsonPlanId.has("plan_id"));
        } else {
            Assertions.fail("should be passed");
        }
    }

    @Order(3)
    @Test
    void testGetSingle() throws IOException {
        Response<ResponseBody> response = organization.fetch().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("organization"));
            JsonObject isJsonObject = respJson.get("organization").getAsJsonObject();
            Assertions.assertTrue(isJsonObject.isJsonObject());
        } else {
            Assertions.fail("should be passed");
        }
    }

    @Order(4)
    @Test
    void testGetSingleWithInclude() throws IOException {
        organization.addParam("include_plan", true);
        Response<ResponseBody> response = organization.fetch().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("organization"));
            JsonObject isJsonObject = respJson.get("organization").getAsJsonObject();
            Assertions.assertTrue(isJsonObject.has("plan_id"));
        } else {
            Assertions.fail("should be passed");
        }
    }

    @Order(5)
    @Test
    void testRole() throws IOException {
        organization.addParam("limit", 2);
        organization.addParam("skip", 2);
        organization.addParam("asc", true);
        organization.addParam("desc", true);
        organization.addParam("include_count", true);
        organization.addParam("include_stack_roles", true);
        Response<ResponseBody> response = organization.roles().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("roles"));
            JsonArray asJsonArray = respJson.get("roles").getAsJsonArray();
            Assertions.assertTrue(asJsonArray.isJsonArray());
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "You don't have the permission to do this operation.",
                    error.getErrorMessage());
            Assertions.assertEquals(316, error.getErrorCode());
        }
    }

    @Order(6)
    @Test
    void testRoleWithQueryPrams() throws IOException {
        organization.addParam("limit", 2);
        organization.addParam("skip", 2);
        organization.addParam("asc", true);
        organization.addParam("desc", true);
        organization.addParam("include_count", true);
        organization.addParam("include_stack_roles", false);
        Response<ResponseBody> response = organization.roles().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("roles"));
            JsonArray asJsonArray = respJson.get("roles").getAsJsonArray();
            Assertions.assertTrue(asJsonArray.isJsonArray());
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "You don't have the permission to do this operation.",
                    error.getErrorMessage());
            Assertions.assertEquals(316, error.getErrorCode());
        }
    }

    @Order(7)
    @Test
    @Disabled
    void testInviteUser() throws IOException {
        organization.addParam("include_plan", true);
        String requestBody = "{\n" +
                "\t\"share\": {\n" +
                "\t\t\"users\": {\n" +
                "\t\t\t\"abc@sample.com\": [\"{{technology}}\"],\n" +
                "\t\t\t\"xyz@sample.com\": [\"{{technology}}\"]\n" +
                "\t\t},\n" +
                "\t\t\"stacks\": {\n" +
                "\t\t\t\"abc@sample.com\": {\n" +
                "\t\t\t\t\"{{apiKey}}\": [\"{{stackRoleUid1}}\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"xyz@sample.com\": {\n" +
                "\t\t\t\t\"technology\": [\"technology\"],\n" +
                "\t\t\t\t\"technology\": [\"technology\", \"technology\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        Response<ResponseBody> response = organization.inviteUser(body).execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("shares"));
            JsonArray asJsonArray = respJson.get("shares").getAsJsonArray();
            Assertions.assertTrue(asJsonArray.isJsonArray());
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "Unable to share at this moment.",
                    error.getErrorMessage());
            Assertions.assertEquals(315, error.getErrorCode());
        }
    }

    @Order(8)
    @Test
    @Disabled
    void testRemoveUser() throws IOException {
        String _body = "{\n" +
                "\t\"share\": {\n" +
                "\t\t\"users\": {\n" +
                "\t\t\t\"abc@sample.com\": [\"{{orgAdminRoleUid}}\"],\n" +
                "\t\t\t\"xyz@sample.com\": [\"{{orgMemberRoleUid}}\"]\n" +
                "\t\t},\n" +
                "\t\t\"stacks\": {\n" +
                "\t\t\t\"abc@sample.com\": {\n" +
                "\t\t\t\t\"{{apiKey}}\": [\"{{stackRoleUid1}}\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"xyz@sample.com\": {\n" +
                "\t\t\t\t\"blta1ed1f11111c1eb1\": [\"blt111d1b110111e1f1\"],\n" +
                "\t\t\t\t\"bltf0c00caa0f0000f0\": [\"bltcea22222d2222222\", \"blt333f33cb3e33ffde\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(_body);
        Response<ResponseBody> response = organization.removeUsers(body).execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("notice"));
            Assertions.assertTrue(respJson.has("shares"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "Unable to delete share at this moment.",
                    error.getErrorMessage());
            Assertions.assertEquals(323, error.getErrorCode());
        }
    }

    @Order(9)
    @Test
    @Disabled
    void testResendInvitation() throws IOException {
        HashMap<String, Object> query = new HashMap<>();
        query.put("include_plan", true);
        Response<ResponseBody> response = organization.resendInvitation("shareUid").execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("notice"));
            Assertions.assertTrue(respJson.has("shares"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "Unable to resend invitation as the invitation is either not initiated or is already accepted.",
                    error.getErrorMessage());
            Assertions.assertEquals(328, error.getErrorCode());
        }
    }


    @Order(10)
    @Test
    @Disabled
    void testTransferOwnership() throws IOException {
        HashMap<String, Object> query = new HashMap<>();
        query.put("include_plan", true);
        String strBody = "{\n" +
                "\t\"transfer_to\": \"ishaileshmishra@gmail.com\"\n" +
                "}";
        JSONObject body = theJSONBody(strBody);
        Response<ResponseBody> response = organization.transferOwnership(body).execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("notice"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "You don't have the permission to do this operation.",
                    error.getErrorMessage());
            Assertions.assertEquals(316, error.getErrorCode());
        }
    }

    @Order(11)
    @Test
    @Disabled
    void testStacks() throws IOException {
        Response<ResponseBody> response = organization.stacks().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("stacks"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "You don't have the permission to do this operation.",
                    error.getErrorMessage());
            Assertions.assertEquals(316, error.getErrorCode());
        }
    }

    @Test
    @Order(12)
    @Disabled
    void testLogDetails() throws IOException {
        Response<ResponseBody> response = organization.logsDetails().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("logs"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "You don't have the permission to do this operation.",
                    error.getErrorMessage());
            Assertions.assertEquals(316, error.getErrorCode());
        }
    }

    @Order(13)
    @Test
    @Disabled
    void testLogsItem() throws IOException {
        Response<ResponseBody> response = organization.logItem("fake@loguid").execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("stacks"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(
                    "Organization log entry doesn't exists.",
                    error.getErrorMessage());
            Assertions.assertEquals(141, error.getErrorCode());
        }
    }

    @Order(14)
    @Test
    @Disabled
    void testAllInvitation() throws IOException {
        HashMap<String, Object> query = new HashMap<>();
        query.put("include_plan", true);
        Response<ResponseBody> response =
                organization.allInvitations().execute();
        if (response.isSuccessful()) {
            JsonObject respJson = toJson(response);
            Assertions.assertTrue(respJson.has("shares"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(),
                    Error.class);
            Assertions.assertEquals(
                    "You're not allowed in here unless you're logged in.",
                    error.getErrorMessage());
            Assertions.assertEquals(309, error.getErrorCode());
        }
    }

    @Order(15)
    @Test
    void testAllInvitationWithQuery() {
        organization.allInvitations().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // This is android thing
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // This is android thing
            }
        });
    }

}
