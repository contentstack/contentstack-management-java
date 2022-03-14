package com.contentstack.cms.user;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Error;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocaleApiTests {

    private static User userInstance;
    private static Dotenv dotenv;

    @BeforeAll
    public static void initBeforeAll() throws IOException {
        dotenv = Dotenv.load();
        String emailId = dotenv.get("username"); // gets username from .env
        String password = dotenv.get("password"); // gets password from .env
        Contentstack client = new Contentstack.Builder().build();
        client.login(emailId, password);
        userInstance = client.user();
    }


    @Test
    @Order(1)
    void testGetUser() throws IOException {
        Response<ResponseBody> userLogin = userInstance.getUser().execute();
        if (userLogin.isSuccessful()) {
            JsonObject user = toJson(userLogin);
            Assertions.assertEquals(dotenv.get("userId"),
                    user.getAsJsonObject("user").get("uid").toString().replace("\"", ""));
        }
    }


    @Test()
    @Order(2)
    void testUpdateUser() throws IOException {
        String body = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"company\": \"company name inc.\"\n" +
                "\t}\n" +
                "}";
        Response<ResponseBody> userLogin = userInstance.updateUser(body).execute();
        if (userLogin.isSuccessful()) {
            JsonObject user = toJson(userLogin);
            Assertions.assertTrue(user.has("notice"));
            Assertions.assertEquals("Profile updated successfully.",
                    user.get("notice").toString().replace("\"", ""));
        }
    }

    @Test
    @Order(3)
    void testUserOrg() throws IOException {
        HashMap<String, Object> query = new HashMap<>();
        query.put("limit", 10);
        Response<ResponseBody> response = userInstance.getUserOrganizations(query).execute();
        if (response.isSuccessful()) {
            JsonObject user = toJson(response);
            Assertions.assertTrue(user.getAsJsonObject("user").has("organizations"));
            Assertions.assertEquals(9,
                    user.getAsJsonObject("user")
                            .getAsJsonArray("organizations").size());
        }
    }

    @Test
    @Order(4)
    void testActivateUser() throws IOException {
        String actToken = dotenv.get("userId");
        String body = "{\n" +
                "\"user\": {\n" +
                "\"first_name\": \"Shailesh\",\n" +
                "\"last_name\": \"Mishra\",\n" +
                "\"password\": \"fake@password\",\n" +
                "\"password_confirmation\": \"fake@password\"\n" +
                "}\n" +
                "}";
        Response<ResponseBody> response =
                userInstance.activateUser(actToken, body)
                        .execute();
        String errMsg = response.errorBody().string();
        Error error = new Gson().fromJson(errMsg, Error.class);
        Assertions.assertEquals("Unable to activate at this moment. Please contact support@contentstack.com for help.", error.getErrorMessage());
        Assertions.assertEquals(102, error.getErrorCode());
        Assertions.assertNotNull(error.getErrors());

    }

    @Test
    @Order(5)
    void testRequestPassword() throws IOException {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        Response<ResponseBody> response =
                userInstance.requestPassword(strBody)
                        .execute();

        if (response.isSuccessful()) {
            JsonObject user = toJson(response);
            Assertions.assertTrue(user.has("notice"));
            Assertions.assertEquals(
                    "If this email address exists, we will send you an email with instructions for resetting your password.",
                    user.get("notice").getAsString());
        }
    }

    @Test
    @Order(6)
    void testResetPassword() throws IOException {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"abcdefghijklmnop\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        String strError = "Uh oh, we weren't able to reset your password. You may have entered an invalid password, or the link might have expired. Please try again or email support at support@contentstack.com.";
        Response<ResponseBody> response = userInstance.resetPassword(requestBody).execute();
        if (response.isSuccessful()) {
            JsonObject user = toJson(response);
            Assertions.assertTrue(user.has("notice"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(108, error.getErrorCode());
            Assertions.assertEquals(strError, error.getErrorMessage());
        }
    }

    @Test
    @Order(7)
    void testUserOrgWithAllAvailKeys() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("limit", 10);
        Response<ResponseBody> response = userInstance.getUserOrganizations(map).execute();
        if (response.isSuccessful() || response.body() != null) {
            assert response.body() != null;
            JsonObject userObj = toJson(response).getAsJsonObject("user");
            Assertions.assertTrue(userObj.isJsonObject());
            Assertions.assertTrue(userObj.has("uid"));
            Assertions.assertTrue(userObj.has("created_at"));
            Assertions.assertTrue(userObj.has("updated_at"));
            Assertions.assertTrue(userObj.has("email"));
            Assertions.assertTrue(userObj.has("username"));
            Assertions.assertTrue(userObj.has("first_name"));
            Assertions.assertTrue(userObj.has("last_name"));
            Assertions.assertTrue(userObj.has("org_uid"));
            Assertions.assertTrue(userObj.has("shared_org_uid"));
            Assertions.assertTrue(userObj.has("authtoken"));
            Assertions.assertTrue(userObj.has("profile_type"));
            Assertions.assertTrue(userObj.has("failed_attempts"));
            Assertions.assertTrue(userObj.has("roles"));
            Assertions.assertTrue(userObj.getAsJsonArray("roles").isJsonArray());
            Assertions.assertTrue(userObj.getAsJsonArray("organizations").isJsonArray());

        } else {
            assert response.errorBody() != null;
            Error error = new Gson()
                    .fromJson(response
                            .errorBody()
                            .string(), Error.class);
            Assertions.assertEquals(
                    "You're not allowed in here unless you're logged in.",
                    error.getErrorMessage());
            Assertions.assertEquals(105,
                    error.getErrorCode());
        }
    }

    @Test
    @Order(8)
    void testLogout() throws IOException {
        Response<ResponseBody> response = userInstance.logout().execute();
        if (response.isSuccessful()) {
            JsonObject user = toJson(response);
            Assertions.assertTrue(user.has("notice"));
            Assertions.assertEquals("You've logged out successfully.",
                    user.get("notice").getAsString());
        }
    }

    @Test
    @Order(9)
    void testLogoutWithAuthtoken() throws IOException {
        String strAuth = dotenv.get("auth_token");
        Response<ResponseBody> response = userInstance.logoutWithAuthtoken(strAuth).execute();
        if (response.isSuccessful()) {
            JsonObject user = toJson(response);
            Assertions.assertTrue(user.has("notice"));
            Assertions.assertEquals("You've logged out successfully.",
                    user.get("notice").getAsString());
        }
    }


    private JsonObject toJson(Response<ResponseBody> response) throws IOException {
        return new Gson().fromJson(response.body().string(), JsonObject.class);
    }

}
