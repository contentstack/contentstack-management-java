package com.contentstack.cms.user;

import okhttp3.Request;
import org.junit.jupiter.api.*;
import retrofit2.Retrofit;

import java.net.URL;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("UNIT")
@DisplayName("Started running unit testcases for user class")
public class UserUnitTests {

    private User user;

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

    @BeforeAll
    public void setupAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.contentstack.io/v3/").build();
        user = new User(retrofit);
    }

    @Test
    @DisplayName("User class default constructor throws exception")
    void testUserClassWithGetUserConstructor() {
        Assertions.assertThrows(UnsupportedOperationException.class, User::new);
    }

    //*************************************************//

    @Test
    @DisplayName("tests embedded url path from getUser method")
    void testUserClassWithGetUserContainingRelativeUrl() {
        Request requestInfo = user.getUser().request();
        Assertions.assertEquals("/v3/user",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(1)
    @DisplayName("tests all parameters from getUser method")
    void testUserClassWithGetUserContainingAllParameters() {
        Request requestInfo = user.getUser().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(2)
    @DisplayName("tests url from getUser method")
    void testUserClassWithGetUserContainingHost() {
        Request requestInfo = user.getUser().request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    @Order(3)
    @DisplayName("tests request method from getUser")
    void testUserClassWithGetUserContainingMethod() {
        Request requestInfo = user.getUser().request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(4)
    @DisplayName("tests headers from getUser method")
    void testUserClassWithGetUserContainingHeaders() {
        Request requestInfo = user.getUser().request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(5)
    @DisplayName("User's getUser should not contain any parameters")
    void testUserClassWithGetUserContainingQueryParameters() {
        Request requestInfo = user.getUser().request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************************************//

    @Test
    @Order(6)
    @DisplayName("tests all parameters from updateUser method")
    void testUserClassWithUpdateUserContainingAllParameters() {
        Request requestInfo = user.updateUser().request();
        Assertions.assertEquals("PUT", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(7)
    @DisplayName("tests url from updateUser method")
    void testUserClassWithUpdateUserContainingHost() {
        Request requestInfo = user.updateUser().request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    @Order(8)
    @DisplayName("tests request method from updateUser")
    void testUserClassWithUpdateUserContainingMethod() {
        Request requestInfo = user.updateUser().request();
        Assertions.assertEquals("PUT",
                requestInfo.method());
    }

    @Test
    @Order(9)
    @DisplayName("tests headers from updateUser method")
    void testUserClassWithUpdateUserContainingHeaders() {
        Request requestInfo = user.updateUser().request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(10)
    @DisplayName("User's updateUser should not contain any parameters")
    void testUserClassWithUpdateUserContainingQueryParameters() {
        Request requestInfo = user.updateUser().request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************************************//

    @Test
    @Order(11)
    @DisplayName("tests all parameters from getUserOrganizations method")
    void testUserClassWithGetUserOrganizationsContainingAllParameters() {
        Request requestInfo = user.getUserOrganizations().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(2,
                requestInfo.url().queryParameterNames().size(), "executes with 2 query parameter");
    }

    @Test
    @Order(12)
    @DisplayName("tests url from getUserOrganizations method")
    void testUserClassWithGetUserOrganizationsContainingHost() {
        Request requestInfo = user.getUserOrganizations().request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    @Order(13)
    @DisplayName("tests request method from getUserOrganizations")
    void testUserClassWithGetUserOrganizationsContainingMethod() {
        Request requestInfo = user.getUserOrganizations().request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(14)
    @DisplayName("tests headers from getUserOrganizations method")
    void testUserClassWithGetUserOrganizationsContainingHeaders() {
        Request requestInfo = user.getUserOrganizations().request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(15)
    @DisplayName("User's getUserOrganizations should not contain any parameters")
    void testUserClassWithGetUserOrganizationsContainingQueryParameters() {
        Request requestInfo = user.getUserOrganizations().request();
        Assertions.assertEquals(2,
                requestInfo.url().queryParameterNames().size());
        Assertions.assertEquals("[include_orgs, include_orgs_roles]",
                requestInfo.url().queryParameterNames().toString());
    }

    //*************************************************//

    @Test
    @Order(16)
    @DisplayName("tests all parameters from ActivateUser method")
    void testUserClassWithActivateUserContainingAllParameters() {
        Request requestInfo = user
                .activateUser("bltf36705c7361d4734")
                .request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(17)
    @DisplayName("tests url from ActivateUser method")
    void testUserClassWithActivateUserContainingHost() {
        Request requestInfo = user
                .activateUser("bltf36705c7361d4734")
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/activate/bltf36705c7361d4734",
                        requestInfo.url().encodedPath());

    }

    @Test
    @Order(18)
    @DisplayName("tests request method from ActivateUser")
    void testUserClassWithActivateUserContainingMethod() {
        Request requestInfo = user
                .activateUser("bltf36705c7361d4734")
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    @Order(19)
    @DisplayName("tests headers from ActivateUser method")
    void testUserClassWithActivateUserContainingHeaders() {
        Request requestInfo = user
                .activateUser("bltf36705c7361d4734")
                .request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(20)
    @DisplayName("User's ActivateUser should not contain any parameters")
    void testUserClassWithActivateUserContainingQueryParameters() {
        Request requestInfo = user
                .activateUser("bltf36705c7361d4734")
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************************************//

    @Test
    @Order(21)
    @DisplayName("tests all parameters from RequestPassword method")
    void testUserClassWithRequestPasswordContainingAllParameters() {
        Request requestInfo = user
                .requestPassword()
                .request();
        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(22)
    @DisplayName("tests url from RequestPassword method")
    void testUserClassWithRequestPasswordContainingHost() {
        Request requestInfo = user
                .requestPassword()
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/forgot_password",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(23)
    @DisplayName("tests request method from RequestPassword")
    void testUserClassWithRequestPasswordContainingMethod() {
        Request requestInfo = user
                .requestPassword()
                .request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    @Order(24)
    @DisplayName("tests headers from RequestPassword method")
    void testUserClassWithRequestPasswordContainingHeaders() {
        Request requestInfo = user.requestPassword().request();
        Assertions.assertEquals(0, requestInfo.headers().names().size());
    }

    @Test
    @Order(25)
    @DisplayName("User's RequestPassword should not contain any parameters")
    void testUserClassWithRequestPasswordContainingQueryParameters() {
        Request requestInfo = user.requestPassword().request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************************************//

    @Test
    @Order(26)
    @DisplayName("tests all parameters from ResetPassword method")
    void testUserClassWithResetPasswordContainingAllParameters() {
        Request requestInfo = user
                .resetPassword()
                .request();

        Assertions.assertEquals("PUT", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(27)
    @DisplayName("tests url from ResetPassword method")
    void testUserClassWithResetPasswordContainingHost() {
        Request requestInfo = user
                .resetPassword()
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/reset_password",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(28)
    @DisplayName("tests request method from ResetPassword")
    void testUserClassWithResetPasswordContainingMethod() {
        Request requestInfo = user
                .resetPassword()
                .request();
        Assertions.assertEquals("PUT",
                requestInfo.method());
    }

    @Test
    @Order(29)
    @DisplayName("tests headers from ResetPassword method")
    void testUserClassWithResetPasswordContainingHeaders() {
        Request requestInfo = user.resetPassword().request();
        Assertions.assertEquals(0,
                requestInfo.headers().names().size());
    }

    @Test
    @Order(30)
    @DisplayName("User's ResetPassword should not contain any parameters")
    void testUserClassWithResetPasswordContainingQueryParameters() {
        Request requestInfo = user.resetPassword().request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************************************//

    @Test
    @Order(31)
    @DisplayName("tests all parameters from Logout method")
    void testUserClassWithLogoutContainingAllParameters() {
        Request requestInfo = user
                .logout()
                .request();

        Assertions.assertEquals("DELETE", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(32)
    @DisplayName("tests url from Logout method")
    void testUserClassWithLogoutContainingHost() {
        Request requestInfo = user
                .logout()
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/user-session",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(33)
    @DisplayName("tests request method from Logout")
    void testUserClassWithLogoutContainingMethod() {
        Request requestInfo = user
                .logout()
                .request();
        Assertions.assertEquals("DELETE",
                requestInfo.method());
    }

    @Test
    @Order(34)
    @DisplayName("tests headers from Logout method")
    void testUserClassWithLogoutContainingHeaders() {
        Request requestInfo = user
                .logout()
                .request();
        Assertions.assertEquals(0,
                requestInfo.headers().names().size());
    }

    @Test
    @Order(35)
    @DisplayName("User's Logout should not contain any parameters")
    void testUserClassWithLogoutContainingQueryParameters() {
        Request requestInfo = user
                .logout()
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************************************//

    @Test
    @Order(36)
    @DisplayName("tests all parameters from LogoutWithAuthtoken method")
    void testUserClassWithLogoutWithAuthtokenContainingAllParameters() {
        Request requestInfo = user
                .logoutWithAuthtoken("authtoken")
                .request();

        Assertions.assertEquals("DELETE", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(37)
    @DisplayName("tests url from LogoutWithAuthtoken method")
    void testUserClassWithLogoutWithAuthtokenContainingHost() {
        Request requestInfo = user
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user/user-session",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(38)
    @DisplayName("tests request method from LogoutWithAuthtoken")
    void testUserClassWithLogoutWithAuthtokenContainingMethod() {
        Request requestInfo = user
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals("DELETE",
                requestInfo.method());
    }

    @Test
    @Order(39)
    @DisplayName("tests headers from LogoutWithAuthtoken method")
    void testUserClassWithLogoutWithAuthtokenContainingHeaders() {
        Request requestInfo = user
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals(1,
                requestInfo.headers().names().size());
    }

    @Test
    @Order(40)
    @DisplayName("User's LogoutWithAuthtoken should not contain any parameters")
    void testUserClassWithLogoutWithAuthtokenContainingQueryParameters() {
        Request requestInfo = user
                .logoutWithAuthtoken("authtoken")
                .request();
        Assertions.assertEquals(0,
                requestInfo.url().queryParameterNames().size());
    }

    //*************************************************//

    @Test
    @Order(41)
    @DisplayName("tests all parameters from GetUserOrganization method")
    void testUserClassWithGetUserOrganizationContainingAllParameters() {
        Request requestInfo = user
                .getUserOrganizations()
                .request();

        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(0, requestInfo.headers().names().size());
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    @Test
    @Order(42)
    @DisplayName("tests url from GetUserOrganization method")
    void testUserClassWithGetUserOrganizationContainingHost() {
        Request requestInfo = user
                .getUserOrganizations()
                .request();
        Assertions
                .assertEquals("api.contentstack.io",
                        requestInfo.url().host());
        Assertions
                .assertEquals("/v3/user",
                        requestInfo.url().encodedPath());
    }

    @Test
    @Order(43)
    @DisplayName("tests request method from GetUserOrganization")
    void testUserClassWithGetUserOrganizationContainingMethod() {
        Request requestInfo = user
                .getUserOrganizations()
                .request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(44)
    @DisplayName("tests headers from GetUserOrganization method")
    void testUserClassWithGetUserOrganizationContainingHeaders() {
        Request requestInfo = user
                .getUserOrganizations()
                .request();
        Assertions.assertEquals(0,
                requestInfo.headers().names().size());
    }

    @Test
    @Order(45)
    @DisplayName("User's GetUserOrganization should not contain any parameters")
    void testUserClassWithGetUserOrganizationContainingQueryParameters() {
        Request requestInfo = user
                .getUserOrganizations()
                .request();
        Assertions.assertEquals(2,
                requestInfo.url().queryParameterNames().size());
    }

}
