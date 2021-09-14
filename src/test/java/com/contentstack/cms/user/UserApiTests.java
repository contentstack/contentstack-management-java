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

/**
 * The type User api tests.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("API")
public class UserApiTests {

    private static User userInstance;
    private static String emailId;
    private static String password;
    private static Contentstack client;

    /**
     * Init before all.
     */
    @BeforeAll
    public static void initBeforeAll() {
        // Accessing the authtoken from the .env file
        Dotenv dotenv = Dotenv.load();
        String DEFAULT_AUTHTOKEN = dotenv.get("auth_token");
        emailId = dotenv.get("username");
        password = dotenv.get("password");
        client = new Contentstack.Builder().setAuthtoken(DEFAULT_AUTHTOKEN).build();
        userInstance = client.user();
    }


    @Test
    void testContentstackLogin() {
        Contentstack client = new Contentstack.Builder().build();
        //ContentstackResponse<LoginDetails, Error> response = client.login(username, password);
        //Assertions.assertTrue(isLoggedIn);
    }


    @Test()
    void throwExceptionWhileLoginWithoutAuthtoken() {
        try {
            new Contentstack.Builder().build().user();
        } catch (Exception e) {
            e.getLocalizedMessage();
            Assertions.assertEquals("Please login to access user instance", e.getLocalizedMessage());
        }

    }

    /**
     * Api test get user.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(1)
    void api_test_get_user() throws IOException {
        Response<ResponseBody> response = userInstance.getUser().execute();
        if (!response.isSuccessful()) {
            Gson gson = new Gson();
            assert response.errorBody() != null;
            Error error = gson.fromJson(response.errorBody().charStream(), Error.class);
            Assertions.assertNull(error.getErrors());
            Assertions.assertEquals(105, error.getErrorCode());
            Assertions.assertEquals("You're not allowed in here unless you're logged in.", error.getErrorMessage());
        }
    }


    /**
     * Test api testcase activate user.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(3)
    void test_api_testcase_activate_user() throws IOException {
        Response<ResponseBody> response = userInstance.activateUser("bltf36705c7361d4734").execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    /**
     * Test api testcase request password.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(4)
    void test_api_testcase_request_password() throws IOException {
        Response<ResponseBody> response = userInstance.requestPassword().execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    /**
     * Test api testcase reset password.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(5)
    void test_api_testcase_reset_password() throws IOException {
        Response<ResponseBody> response = userInstance.resetPassword().execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }


    /**
     * Test api testcase logout.
     *
     * @throws IOException the io exception
     */
    @Test
    @Order(6)
    void test_api_testcase_logout() throws IOException {
        Response<ResponseBody> response = userInstance.logout().execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    /**
     * Test api testcase logout with authtoken.
     *
     * @throws IOException the io exception
     */
    @Test
    @DisplayName("api testcase for logout with authtoken")
    @Order(7)
    void test_api_testcase_logout_with_authtoken() throws IOException {
        Response<ResponseBody> response = userInstance.logoutWithAuthtoken("bltf36705c7361d4734").execute();
        if (!response.isSuccessful()) {
            assert response.errorBody() != null;
        }
    }

    /**
     * Test api testcase get user organisation.
     *
     * @throws IOException the io exception
     */
    @Test
    @DisplayName("api testcase for get user organisation")
    @Order(8)
    void test_api_testcase_get_user_organisation() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("limit", 10);
        Response<ResponseBody> response = userInstance.getUserOrganizations(map).execute();
        if (response.isSuccessful() || response.body() != null) {
            assert response.body() != null;
            JsonObject convertedToJson = new Gson().fromJson(response.body().string(), JsonObject.class);
            JsonObject userObj = convertedToJson.getAsJsonObject("user");
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
            Error error = new Gson().fromJson(response.errorBody().string(), Error.class);
            Assertions.assertEquals("You're not allowed in here unless you're logged in.", error.getErrorMessage());
            Assertions.assertEquals(105, error.getErrorCode());
        }
    }

}
