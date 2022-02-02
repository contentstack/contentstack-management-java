package com.contentstack.cms.user;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.*;
import retrofit2.Retrofit;

import java.net.URL;
import java.util.HashMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserUnitTests {

    private User userInstance;
    private String activationToken;

    /*
     * Is valid boolean.
     *
     * @param url the url
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

    /*
     * Sets all.
     */
    @BeforeAll
    public void setupAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.contentstack.io/v3/").build();
        userInstance = new User(retrofit);
        Dotenv dotenv = Dotenv.load();
        activationToken = dotenv.get("activation_token");
    }


//    /*
//     * Test user class with get user constructor.
//     */
//    @Test
//    @DisplayName("User class default constructor throws exception")
//    void testUserClassWithGetUserConstructor() {
//        Assertions.assertThrows(UnsupportedOperationException.class, User::new);
//    }

    //*************************//

    /*
     * Test user class with get user containing relative url.
     */
    @Test
    @DisplayName("tests embedded url path from getUser method")
    void testUserClassWithGetUserContainingRelativeUrl() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals("/v3/user",
                requestInfo.url().encodedPath());
    }

    /*
     * Test user class with get user containing all parameters.
     */
    @Test
    @Order(1)
    @DisplayName("tests all parameters from getUser method")
    void testUserClassWithGetUserContainingAllParameters() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /*
     * Test user class with get user containing host.
     */
    @Test
    @Order(2)
    @DisplayName("tests url from getUser method")
    void testUserClassWithGetUserContainingHost() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    /*
     * Test user class with get user containing method.
     */
    @Test
    @Order(3)
    @DisplayName("tests request method from getUser")
    void testUserClassWithGetUserContainingMethod() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    /*
     * Test user class with get user containing headers.
     */
    @Test
    @Order(4)
    @DisplayName("tests headers from getUser method")
    void testUserClassWithGetUserContainingHeaders() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    /*
     * Test user class with get user containing query parameters.
     */
    @Test
    @Order(5)
    @DisplayName("User's getUser should not contain any parameters")
    void testUserClassWithGetUserContainingQueryParameters() {
        Request requestInfo = userInstance.getUser().request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************//

    /*
     * Test user class with update user containing all parameters.
     */
    @Test
    @Order(6)
    @DisplayName("tests all parameters from updateUser method")
    void testUserClassWithUpdateUserContainingAllParameters() {
        Request requestInfo = userInstance.updateUser("").request();
        Assertions.assertEquals("PUT", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /*
     * Test user class with update user containing host.
     */
    @Test
    @Order(7)
    @DisplayName("tests url from updateUser method")
    void testUserClassWithUpdateUserContainingHost() {
        Request requestInfo = userInstance.updateUser("").request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    /*
     * Test user class with update user containing method.
     */
    @Test
    @Order(8)
    @DisplayName("tests request method from updateUser")
    void testUserClassWithUpdateUserContainingMethod() {
        Request requestInfo = userInstance.updateUser("").request();
        Assertions.assertEquals("PUT",
                requestInfo.method());
    }

    /*
     * Test user class with update user containing headers.
     */
    @Test
    @Order(9)
    @DisplayName("tests headers from updateUser method")
    void testUserClassWithUpdateUserContainingHeaders() {
        Request requestInfo = userInstance.updateUser("").request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    /*
     * Test user class with update user containing query parameters.
     */
    @Test
    @Order(10)
    @DisplayName("User's updateUser should not contain any parameters")
    void testUserClassWithUpdateUserContainingQueryParameters() {
        Request requestInfo = userInstance.updateUser("").request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************//

    /*
     * Test user class with get user organizations containing all parameters.
     */
    @Test
    @Order(11)
    @DisplayName("tests all parameters from getUserOrganizations method")
    void testUserClassWithGetUserOrganizationsContainingAllParameters() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance.getUserOrganizations(map).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(2,
                requestInfo.url().queryParameterNames().size(), "executes with 2 query parameter");
    }

    /*
     * Test user class with get user organizations containing host.
     */
    @Test
    @Order(12)
    @DisplayName("tests url from getUserOrganizations method")
    void testUserClassWithGetUserOrganizationsContainingHost() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance.getUserOrganizations(map).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    /*
     * Test user class with get user organizations containing method.
     */
    @Test
    @Order(13)
    @DisplayName("tests request method from getUserOrganizations")
    void testUserClassWithGetUserOrganizationsContainingMethod() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance.getUserOrganizations(map).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    /*
     * Test user class with get user organizations containing headers.
     */
    @Test
    @Order(14)
    @DisplayName("tests headers from getUserOrganizations method")
    void testUserClassWithGetUserOrganizationsContainingHeaders() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance.getUserOrganizations(map).request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    /*
     * Test user class with get user organizations containing query parameters.
     */
    @Test
    @Order(15)
    @DisplayName("User's getUserOrganizations should not contain any parameters")
    void testUserClassWithGetUserOrganizationsContainingQueryParameters() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance.getUserOrganizations(map).request();
        Assertions.assertEquals(2,
                requestInfo.url().queryParameterNames().size());
        Assertions.assertEquals("[include_orgs, include_orgs_roles]",
                requestInfo.url().queryParameterNames().toString());
    }

    //*************************//

    /*
     * Test user class with activate user containing all parameters.
     */
    @Test
    @Order(16)
    @DisplayName("tests all parameters from ActivateUser method")
    void testUserClassWithActivateUserContainingAllParameters() {
        Request requestInfo = userInstance
                .activateUser(activationToken, "")
                .request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /*
     * Test user class with activate user containing host.
     */
    @Test
    @Order(17)
    @DisplayName("tests url from ActivateUser method")
    void testUserClassWithActivateUserContainingHost() {
        Request requestInfo = userInstance
                .activateUser(activationToken, "")
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/activate/" + activationToken,
                        requestInfo.url().encodedPath());

    }

    /*
     * Test user class with activate user containing method.
     */
    @Test
    @Order(18)
    @DisplayName("tests request method from ActivateUser")
    void testUserClassWithActivateUserContainingMethod() {
        Request requestInfo = userInstance
                .activateUser(activationToken, "")
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    /*
     * Test user class with activate user containing headers.
     */
    @Test
    @Order(19)
    @DisplayName("tests headers from ActivateUser method")
    void testUserClassWithActivateUserContainingHeaders() {
        Request requestInfo = userInstance
                .activateUser(activationToken, "")
                .request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    /*
     * Test user class with activate user containing query parameters.
     */
    @Test
    @Order(20)
    @DisplayName("User's ActivateUser should not contain any parameters")
    void testUserClassWithActivateUserContainingQueryParameters() {
        Request requestInfo = userInstance
                .activateUser(activationToken, "")
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************//

    /*
     * Test user class with request password containing all parameters.
     */
    @Test
    @Order(21)
    @DisplayName("tests all parameters from RequestPassword method")
    void testUserClassWithRequestPasswordContainingAllParameters() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance
                .requestPassword(strBody)
                .request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /*
     * Test user class with request password containing host.
     */
    @Test
    @Order(22)
    @DisplayName("tests url from RequestPassword method")
    void testUserClassWithRequestPasswordContainingHost() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance
                .requestPassword(strBody)
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/forgot_password",
                        requestInfo.url().encodedPath());
    }

    /*
     * Test user class with request password containing method.
     */
    @Test
    @Order(23)
    @DisplayName("tests request method from RequestPassword")
    void testUserClassWithRequestPasswordContainingMethod() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance
                .requestPassword(strBody)
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    /*
     * Test user class with request password containing headers.
     */
    @Test
    @Order(24)
    @DisplayName("tests headers from RequestPassword method")
    void testUserClassWithRequestPasswordContainingHeaders() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance.requestPassword(strBody).request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    /*
     * Test user class with request password containing query parameters.
     */
    @Test
    void testUserClassWithRequestPasswordContainingQueryParameters() {
        String strBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"email\": \"john.doe@contentstack.com\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance.requestPassword(strBody).request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************//

    /*
     * Test user class with reset password containing all parameters.
     */
    @Test
    void testUserClassWithResetPasswordContainingAllParameters() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"abcdefghijklmnop\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance
                .resetPassword(requestBody)
                .request();

        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /*
     * Test user class with reset password containing host.
     */
    @Test
    @Order(27)
    @DisplayName("tests url from ResetPassword method")
    void testUserClassWithResetPasswordContainingHost() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"abcdefghijklmnop\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance
                .resetPassword(requestBody)
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/reset_password",
                        requestInfo.url().encodedPath());
    }

    /*
     * Test user class with reset password containing method.
     */
    @Test
    @Order(28)
    @DisplayName("tests request method from ResetPassword")
    void testUserClassWithResetPasswordContainingMethod() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"abcdefghijklmnop\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance
                .resetPassword(requestBody)
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    /*
     * Test user class with reset password containing headers.
     */
    @Test
    @Order(29)
    @DisplayName("tests headers from ResetPassword method")
    void testUserClassWithResetPasswordContainingHeaders() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"abcdefghijklmnop\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance.resetPassword(requestBody).request();
        Assertions.assertEquals(0,
                requestInfo.headers().names().size());
    }

    /*
     * Test user class with reset password containing query parameters.
     */
    @Test
    @Order(30)
    @DisplayName("User's ResetPassword should not contain any parameters")
    void testUserClassWithResetPasswordContainingQueryParameters() {
        String requestBody = "{\n" +
                "\t\"user\": {\n" +
                "\t\t\"reset_password_token\": \"abcdefghijklmnop\",\n" +
                "\t\t\"password\": \"Simple@123\",\n" +
                "\t\t\"password_confirmation\": \"Simple@123\"\n" +
                "\t}\n" +
                "}";
        Request requestInfo = userInstance.resetPassword(requestBody).request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************//

    /*
     * Test user class with logout containing all parameters.
     */
    @Test
    @Order(31)
    @DisplayName("tests all parameters from Logout method")
    void testUserClassWithLogoutContainingAllParameters() {
        Request requestInfo = userInstance
                .logout()
                .request();

        Assertions.assertEquals("DELETE", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /*
     * Test user class with logout containing host.
     */
    @Test
    @Order(32)
    @DisplayName("tests url from Logout method")
    void testUserClassWithLogoutContainingHost() {
        Request requestInfo = userInstance
                .logout()
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user-session",
                        requestInfo.url().encodedPath());
    }

    /*
     * Test user class with logout containing method.
     */
    @Test
    @Order(33)
    @DisplayName("tests request method from Logout")
    void testUserClassWithLogoutContainingMethod() {
        Request requestInfo = userInstance
                .logout()
                .request();
        Assertions.assertEquals("DELETE",
                requestInfo.method());
    }

    /*
     * Test user class with logout containing headers.
     */
    @Test
    @Order(34)
    @DisplayName("tests headers from Logout method")
    void testUserClassWithLogoutContainingHeaders() {
        Request requestInfo = userInstance
                .logout()
                .request();
        Assertions.assertEquals(0,
                requestInfo.headers().names().size());
    }

    /*
     * Test user class with logout containing query parameters.
     */
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

    //*************************//

    /*
     * Test user class with logout with authtoken containing all parameters.
     */
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

    /*
     * Test user class with logout with authtoken containing host.
     */
    @Test
    @Order(37)
    @DisplayName("tests url from LogoutWithAuthtoken method")
    void testUserClassWithLogoutWithAuthtokenContainingHost() {
        Request requestInfo = userInstance
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user-session",
                        requestInfo.url().encodedPath());
    }

    /*
     * Test user class with logout with authtoken containing method.
     */
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

    /*
     * Test user class with logout with authtoken containing headers.
     */
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

    /*
     * Test user class with logout with authtoken containing query parameters.
     */
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

    //*************************//

    /*
     * Test user class with get user organization containing all parameters.
     */
    @Test
    @Order(41)
    @DisplayName("tests all parameters from GetUserOrganization method")
    void testUserClassWithGetUserOrganizationContainingAllParameters() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance
                .getUserOrganizations(map)
                .request();

        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /*
     * Test user class with get user organization containing host.
     */
    @Test
    @Order(42)
    @DisplayName("tests url from GetUserOrganization method")
    void testUserClassWithGetUserOrganizationContainingHost() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance
                .getUserOrganizations(map)
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user",
                        requestInfo.url().encodedPath());
    }

    /*
     * Test user class with get user organization containing method.
     */
    @Test
    @Order(43)
    @DisplayName("tests request method from GetUserOrganization")
    void testUserClassWithGetUserOrganizationContainingMethod() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance
                .getUserOrganizations(map)
                .request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    /*
     * Test user class with get user organization containing headers.
     */
    @Test
    @Order(44)
    @DisplayName("tests headers from GetUserOrganization method")
    void testUserClassWithGetUserOrganizationContainingHeaders() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance
                .getUserOrganizations(map)
                .request();
        Assertions.assertEquals(0,
                requestInfo.headers().names().size());
    }

    /*
     * Test user class with get user organization containing query parameters.
     */
    @Test
    @Order(45)
    @DisplayName("User's GetUserOrganization should not contain any parameters")
    void testUserClassWithGetUserOrganizationContainingQueryParameters() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("include_orgs", true);
        map.put("include_orgs_roles", true);
        Request requestInfo = userInstance
                .getUserOrganizations(map)
                .request();
        Assertions.assertEquals(2,
                requestInfo.url().queryParameterNames().size());
    }


}
