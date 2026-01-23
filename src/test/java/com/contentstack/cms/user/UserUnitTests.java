package com.contentstack.cms.user;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserUnitTests {

    private User userInstance;
    private String ACTIVATION_TOKEN = TestClient.USER_ID;
    private String AUTHTOKEN = TestClient.AUTHTOKEN;

    /*
     * Is valid boolean.
     *
     * @param url the url
     *
     * @return the boolean
     */
    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
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

    /*
     * Sets all.
     */
    @BeforeAll
    public void setupAll() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        userInstance = contentstack.user();
    }

    @Test
    @DisplayName("tests embedded url path from getUser method")
    void testUserClassWithGetUserContainingRelativeUrl() {
        Request request = userInstance.getUser().request();
        Assertions.assertEquals("/v3/user",
                request.url().encodedPath());
    }

    @Test
    @Order(1)
    @DisplayName("tests all parameters from getUser method")
    void testUserClassWithGetUserContainingAllParameters() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    @Test
    @Order(2)
    @DisplayName("tests url from getUser method")
    void testUserClassWithGetUserContainingHost() {
        Request requestInfo = userInstance.getUser().request();    }

    @Test
    @Order(3)
    @DisplayName("tests request method from getUser")
    void testUserClassWithGetUserContainingMethod() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(4)
    @DisplayName("tests headers from getUser method")
    void testUserClassWithGetUserContainingHeaders() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(5)
    @DisplayName("User's getUser should not contain any parameters")
    void testUserClassWithGetUserContainingQueryParameters() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    @Order(6)
    @DisplayName("tests all parameters from updateUser method")
    void testUserClassWithUpdateUserContainingAllParameters() {

        JSONObject body;
        try {
            JSONParser parser = new JSONParser();
            String strBody = "{\n" +
                    "\t\"user\": {\n" +
                    "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                    "\t}\n" +
                    "}";
            body = (JSONObject) parser.parse(strBody);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

        Request requestInfo = userInstance.update(body).request();
        Assertions.assertEquals("PUT", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    @Test
    @Order(7)
    @DisplayName("tests url from updateUser method")
    void testUserClassWithUpdateUserContainingHost() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance.update(body).request();    }

    @Test
    @Order(8)
    @DisplayName("tests request method from updateUser")
    void testUserClassWithUpdateUserContainingMethod() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance.update(body).request();
        Assertions.assertEquals("PUT",
                requestInfo.method());
    }

    @Test
    @Order(9)
    @DisplayName("tests headers from updateUser method")
    void testUserClassWithUpdateUserContainingHeaders() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance.update(body).request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(10)
    @DisplayName("User's updateUser should not contain any parameters")
    void testUserClassWithUpdateUserContainingQueryParameters() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance.update(body).request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    @Order(16)
    @DisplayName("tests all parameters from ActivateUser method")
    void testUserClassWithActivateUserContainingAllParameters() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .activateAccount(ACTIVATION_TOKEN, body)
                .request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    @Test
    @Order(17)
    @DisplayName("tests url from ActivateUser method")
    void testUserClassWithActivateUserContainingHost() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .activateAccount(ACTIVATION_TOKEN, body)
                .request();
        Assertions.assertEquals("/v3/user/activate/" + ACTIVATION_TOKEN,
                        requestInfo.url().encodedPath());

    }

    @Test
    @Order(18)
    @DisplayName("tests request method from ActivateUser")
    void testUserClassWithActivateUserContainingMethod() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .activateAccount(ACTIVATION_TOKEN, body)
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    @Order(19)
    @DisplayName("tests headers from ActivateUser method")
    void testUserClassWithActivateUserContainingHeaders() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .activateAccount(ACTIVATION_TOKEN, body)
                .request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(20)
    @DisplayName("User's ActivateUser should not contain any parameters")
    void testUserClassWithActivateUserContainingQueryParameters() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .activateAccount(ACTIVATION_TOKEN, body)
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    @Order(21)
    @DisplayName("tests all parameters from RequestPassword method")
    void testUserClassWithRequestPasswordContainingAllParameters() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .requestPassword(body)
                .request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    @Test
    @Order(22)
    @DisplayName("tests url from RequestPassword method")
    void testUserClassWithRequestPasswordContainingHost() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .requestPassword(body)
                .request();
        Assertions.assertEquals("/v3/user/forgot_password",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(23)
    @DisplayName("tests request method from RequestPassword")
    void testUserClassWithRequestPasswordContainingMethod() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance
                .requestPassword(body)
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    @Order(24)
    @DisplayName("tests headers from RequestPassword method")
    void testUserClassWithRequestPasswordContainingHeaders() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance.requestPassword(body).request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    void testUserClassWithRequestPasswordContainingQueryParameters() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(strBody);
        Request requestInfo = userInstance.requestPassword(body).request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    void testUserClassWithResetPasswordContainingAllParameters() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"technology\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(requestBody);
        Request requestInfo = userInstance.resetPassword(body).request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    @Test
    @Order(27)
    @DisplayName("tests url from ResetPassword method")
    void testUserClassWithResetPasswordContainingHost() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"technology\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(requestBody);
        Request requestInfo = userInstance.resetPassword(body).request();
        Assertions.assertEquals("/v3/user/reset_password",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(28)
    @DisplayName("tests request method from ResetPassword")
    void testUserClassWithResetPasswordContainingMethod() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"technology\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(requestBody);
        Request requestInfo = userInstance
                .resetPassword(body)
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    @Order(29)
    @DisplayName("tests headers from ResetPassword method")
    void testUserClassWithResetPasswordContainingHeaders() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"technology\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(requestBody);
        Request requestInfo = userInstance.resetPassword(body).request();
        Assertions.assertEquals(0,
                requestInfo.headers().names().size());
    }

    @Test
    @Order(30)
    @DisplayName("User's ResetPassword should not contain any parameters")
    void testUserClassWithResetPasswordContainingQueryParameters() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"technology\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = strToJson(requestBody);
        Request requestInfo = userInstance.resetPassword(body).request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    @Order(31)
    @DisplayName("tests all parameters from Logout method")
    void testUserClassWithLogoutContainingAllParameters() {
        Request requestInfo = userInstance.logout().request();
        Assertions.assertEquals("DELETE", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    @Test
    @Order(32)
    @DisplayName("tests url from Logout method")
    void testUserClassWithLogoutContainingHost() {
        Request requestInfo = userInstance.logout().request();        Assertions.assertEquals("/v3/user-session", requestInfo.url().encodedPath());
    }

    @Test
    @Order(33)
    @DisplayName("tests request method from Logout")
    void testUserClassWithLogoutContainingMethod() {
        Request requestInfo = userInstance.logout().request();
        Assertions.assertEquals("DELETE", requestInfo.method());
    }

    @Test
    @Order(34)
    @DisplayName("tests headers from Logout method")
    void testUserClassWithLogoutContainingHeaders() {
        Request requestInfo = userInstance.logout().request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(35)
    @DisplayName("User's Logout should not contain any parameters")
    void testUserClassWithLogoutContainingQueryParameters() {
        Request requestInfo = userInstance
                .logout()
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    @Order(36)
    @DisplayName("tests all parameters from LogoutWithAuthtoken method")
    void testUserClassWithLogoutWithAuthtokenContainingAllParameters() {
        Request requestInfo = userInstance
                .logoutWithAuthtoken("authtoken")
                .request();

        Assertions.assertEquals("DELETE", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(37)
    @DisplayName("tests url from LogoutWithAuthtoken method")
    void testUserClassWithLogoutWithAuthtokenContainingHost() {
        Request requestInfo = userInstance
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals("/v3/user-session",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(38)
    @DisplayName("tests request method from LogoutWithAuthtoken")
    void testUserClassWithLogoutWithAuthtokenContainingMethod() {
        Request requestInfo = userInstance
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals("DELETE",
                requestInfo.method());
    }

    @Test
    @Order(39)
    @DisplayName("tests headers from LogoutWithAuthtoken method")
    void testUserClassWithLogoutWithAuthtokenContainingHeaders() {
        Request requestInfo = userInstance
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals(1,
                requestInfo.headers().names().size());
    }

    @Test
    @Order(40)
    @DisplayName("User's LogoutWithAuthtoken should not contain any parameters")
    void testUserClassWithLogoutWithAuthtokenContainingQueryParameters() {
        Request requestInfo = userInstance
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    @Order(41)
    @DisplayName("User With Super Class Implementation")
    void testUserBaseParameters() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "something");
        Request requestInfo = userInstance.
                addParam("param", (Object)"value")
                .addHeader("key", "value")
                .addHeaders(headers)
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    @Test
    @Order(42)
    @DisplayName("Test login with TOTP token")
    void testLoginWithTOTPToken() {
        // Test basic login with TOTP token
        Map<String, String> params = new HashMap<>();
        params.put("tfaToken", "123456");
        Request requestInfo = userInstance.login("test@example.com", "password", params).request();
        
        // Verify request format
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals("/v3/user-session", requestInfo.url().encodedPath());
        
        // Verify request body
        String requestBody = requestInfo.body() != null ? requestInfo.body().toString() : "";
        Assertions.assertTrue(requestBody.contains("\"tfa_token\":\"123456\""));
    }

    @Test
    @Order(43)
    @DisplayName("Test login with MFA parameters")
    void testLoginWithMFAParameters() {
        // Test login with both token and secret (should throw exception)
        Map<String, String> params = new HashMap<>();
        params.put("tfaToken", "123456");
        params.put("mfaSecret", "test-secret");
        
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> userInstance.login("test@example.com", "password", params).request()
        );
        
        Assertions.assertTrue(exception.getMessage().contains("Cannot provide both tfaToken and mfaSecret"));
    }

    @Test
    @Order(44)
    @DisplayName("Test login validation")
    void testLoginValidation() {
        Map<String, String> params = new HashMap<>();
        params.put("tfaToken", "123456");
        
        // Test with missing required parameters
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> userInstance.login("", "password", params).request());
            
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> userInstance.login("test@example.com", "", params).request());
            
        Assertions.assertThrows(IllegalArgumentException.class, 
            () -> userInstance.login("test@example.com", "password", new HashMap<>()).request());
    }

    @Test
    @Order(45)
    @DisplayName("Test TOTP generation from MFA secret")
    void testTOTPGenerationFromSecret() {
        // Test login with MFA secret only
        Map<String, String> params = new HashMap<>();
        params.put("mfaSecret", "test-secret");
        Request requestInfo = userInstance.login("test@example.com", "password", params).request();
        
        // Verify request format
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals("/v3/user-session", requestInfo.url().encodedPath());
        
        // Verify generated TOTP format
        String requestBody = requestInfo.body() != null ? requestInfo.body().toString() : "";
        Assertions.assertTrue(requestBody.contains("\"tfa_token\":\""));
        
        // Extract TOTP token and verify it's 6 digits
        String token = requestBody.split("\"tfa_token\":\"")[1].split("\"")[0];
        Assertions.assertTrue(token.matches("\\d{6}"), "TOTP should be 6 digits");
    }

    @Test
    @Order(46)
    @DisplayName("Test invalid MFA secret handling")
    void testInvalidMFASecret() {
        // Test with invalid MFA secret
        Map<String, String> params = new HashMap<>();
        params.put("mfaSecret", "invalid-secret");
        
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> userInstance.login("test@example.com", "password", params).request()
        );
        
        Assertions.assertTrue(exception.getMessage().contains("Invalid MFA secret key"));
    }

    @Test
    @Order(47)
    @DisplayName("Test empty parameters")
    void testEmptyParameters() {
        // Test with empty parameters map
        Map<String, String> params = new HashMap<>();
        
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> userInstance.login("test@example.com", "password", params).request()
        );
        
        Assertions.assertTrue(exception.getMessage().contains("Must provide either tfaToken or mfaSecret"));
    }

    @Test
    @Order(48)
    @DisplayName("Test empty values in parameters")
    void testEmptyValues() {
        // Test with empty tfaToken
        Map<String, String> params1 = new HashMap<>();
        params1.put("tfaToken", "");
        
        IllegalArgumentException exception1 = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> userInstance.login("test@example.com", "password", params1).request()
        );
        Assertions.assertTrue(exception1.getMessage().contains("Must provide either tfaToken or mfaSecret"));
        
        // Test with empty mfaSecret
        Map<String, String> params2 = new HashMap<>();
        params2.put("mfaSecret", "");
        
        IllegalArgumentException exception2 = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> userInstance.login("test@example.com", "password", params2).request()
        );
        Assertions.assertTrue(exception2.getMessage().contains("Must provide either tfaToken or mfaSecret"));
    }
}
