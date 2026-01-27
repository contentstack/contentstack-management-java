package com.contentstack.cms.user;

import com.contentstack.cms.TestClient;
import com.google.gson.Gson;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {

    protected static final String activationToken = TestClient.AUTHTOKEN;
    protected static User user;

    @BeforeAll
    public static void initBeforeAll() {
        user = TestClient.getClient().user();
    }

    JSONObject strToJson(String body) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    void testGetUser() {
        Request request = user.getUser().request();        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().query());
    }

    @Test()
    @Order(4)
    void testUpdateUser() {
        String body = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"company\": \"contentstack.ind\"\n" +
                "\t}\n" +
                "}";
        JSONObject jsonBody = strToJson(body);
        Request userLogin = user.update(jsonBody).request();
        Assertions.assertTrue(userLogin.isHttps());    }

    @Test
    @Order(5)
    @Disabled
    void testActivateUser() throws IOException {
        String body = "{\n" +
                "\"user\": {\n" +
                "\"first_name\": \"Shailesh\",\n" +
                "\"last_name\": \"Mishra\",\n" +
                "\"password\": \"fake@password\",\n" +
                "\"password_confirmation\": \"fake@password\"\n" +
                "}\n" +
                "}";
        JSONObject jsonBody = strToJson(body);
        Response<ResponseBody> response = user.activateAccount(activationToken, jsonBody).execute();
        Assertions.assertFalse(response.isSuccessful());
    }

    @Test
    @Order(6)
    void testRequestPassword() throws IOException {
        String body = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject jsonBody = strToJson(body);
        Response<ResponseBody> response = user.requestPassword(jsonBody).execute();
        if (response.isSuccessful()) {
            JSONObject responsBody = strToJson(response.body().string());
            Assertions.assertTrue(responsBody.containsKey("notice"));
            Assertions.assertEquals(
                    "If this email address exists, we will send you an email with instructions for resetting your password.",
                    responsBody.get("notice").toString());
        }
    }

    @Test
    @Order(7)
    @Disabled
    void testResetPassword() throws IOException {
        String body = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"abcdefghijklmnop\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        JSONObject jsonBody = strToJson(body);
        String strError = "Uh oh, we weren't able to reset your password. You may have entered an invalid password, or the link might have expired. Please try again or email support at support@contentstack.com.";
        Response<ResponseBody> response = user.resetPassword(jsonBody).execute();
        if (response.isSuccessful()) {
            JSONObject respBody = strToJson(response.body().string());
            Assertions.assertTrue(respBody.containsKey("notice"));
        } else {
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals(strError, error.getLocalizedMessage());
        }
    }

    @Test
    @Order(8)
    @Disabled
    void testLogout() throws IOException {
        Response<ResponseBody> response = user.logout().execute();
        if (response.isSuccessful()) {
            JSONObject jsonBody = strToJson(response.body().string());
            Assertions.assertTrue(jsonBody.containsKey("notice"));
            Assertions.assertEquals("You've logged out successfully.",
                    jsonBody.get("notice").toString());
        }
    }

}
