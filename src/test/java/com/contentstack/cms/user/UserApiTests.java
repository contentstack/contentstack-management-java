package com.contentstack.cms.user;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Error;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("API")
@DisplayName("Api Testcases for User Class")
public class UserApiTests {

    private User userInstance;

    @BeforeAll
    public void initBeforeAll() {
        // Accessing the authtoken from the .env file
        Dotenv dotenv = Dotenv.load();
        String DEFAULT_AUTHTOKEN = dotenv.get("auth_token");
        Contentstack client = new Contentstack.Builder().setAuthtoken(DEFAULT_AUTHTOKEN).build();
        userInstance = client.user();
    }

    @Test
    @DisplayName("api testcase for get user")
    @Order(1)
    void api_test_get_user() throws IOException {
        Response<ResponseBody> response = userInstance.getUser().execute();
        if (!response.isSuccessful()) {
            Gson gson = new Gson();
            assert response.errorBody() != null;
            Error error = gson.fromJson(response.errorBody().charStream(), Error.class);
            Assertions.assertNull(error.getError());
            Assertions.assertEquals(105, error.getErrorCode());
            Assertions.assertEquals("You're not allowed in here unless you're logged in.", error.getErrorMessage());
        }
    }

    @Test
    @DisplayName("api testcase for update user")
    @Order(2)
    void test_api_testcase_update_user() throws IOException {
        Response<ResponseBody> response = userInstance.updateUser().execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
            Error error = new Gson().fromJson(response.errorBody().charStream(), Error.class);
            Assertions.assertNull(error.getError());
            Assertions.assertEquals(105, error.getErrorCode());
            Assertions.assertEquals("You're not allowed in here unless you're logged in.", error.getErrorMessage());
        }
    }

    @Test
    @DisplayName("api testcase for activate user")
    @Order(3)
    void test_api_testcase_activate_user() throws IOException {
        Response<ResponseBody> response = userInstance.activateUser("bltf36705c7361d4734").execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    @Test
    @DisplayName("api testcase for request password")
    @Order(4)
    void test_api_testcase_request_password() throws IOException {
        Response<ResponseBody> response = userInstance.requestPassword().execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    @Test
    @DisplayName("api testcase for reset password")
    @Order(5)
    void test_api_testcase_reset_password() throws IOException {
        Response<ResponseBody> response = userInstance.resetPassword().execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }


    @Test
    @DisplayName("api testcase for user logout")
    @Order(6)
    void test_api_testcase_logout() throws IOException {
        Response<ResponseBody> response = userInstance.logout().execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    @Test
    @DisplayName("api testcase for logout with authtoken")
    @Order(7)
    void test_api_testcase_logout_with_authtoken() throws IOException {
        Response<ResponseBody> response = userInstance.logoutWithAuthtoken("bltf36705c7361d4734").execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    @Test
    @DisplayName("api testcase for get user organisation")
    @Order(8)
    void test_api_testcase_get_user_organisation() throws IOException {
        Response<ResponseBody> response = userInstance.getUserOrganizations().execute();
        assert response.isSuccessful() || response.errorBody() != null;
        Error error = new Gson().fromJson(response.errorBody().charStream(), Error.class);
        Assertions.assertEquals(105, error.getErrorCode());
    }

}