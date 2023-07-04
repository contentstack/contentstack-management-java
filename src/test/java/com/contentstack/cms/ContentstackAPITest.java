package com.contentstack.cms;

import com.contentstack.cms.models.Error;
import com.contentstack.cms.models.LoginDetails;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

/*
 @author  ***REMOVED***@gmail.com
 @since   CMS v0.0.1
 */
public class ContentstackAPITest {

    private static Dotenv dotenv;

    @BeforeAll
    public static void getCredentials() {
        dotenv = Dotenv.load();
    }

    @Test
    void testContentstackUserLogin() throws IOException {
        String authToken = dotenv.get("authToken");
        Contentstack contentstack = new Contentstack.Builder()
                .setAuthtoken(authToken)
                .build();
        Response<ResponseBody> user = contentstack.user().getUser().execute();
        if (user.isSuccessful()) {
            assert user.body() != null;
            String responseString = user.body().string();
            System.out.println(responseString);
        } else {
            assert user.errorBody() != null;
            String errString = user.errorBody().string();
            Error error = new Gson().fromJson(errString, Error.class);
            Assertions.assertEquals(105, error.getErrorCode(), "not loggedIn");
        }
        Assertions.assertNotNull(authToken);
        Assertions.assertEquals(authToken, contentstack.authtoken);
    }

    @Test
    void testContentstackUserLoginWithNullAuthtoken() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(null).build();
        try {
            contentstack.user().getUser().execute();
        } catch (Exception e) {
            Assertions.assertEquals(
                    "Please login to access user instance",
                    e.getLocalizedMessage());
        }
        Assertions.assertNull(contentstack.authtoken);
    }

    @Test
    void testContentstackUserLoginWithInvalidCredentials() {
        Contentstack contentstack = new Contentstack.Builder().build();
        try {
            contentstack.login("invalid@credentials.com", "invalid@password");
        } catch (Exception e) {
            Assertions.assertEquals(
                    "Please login to access user instance",
                    e.getLocalizedMessage());
        }
        Assertions.assertNull(contentstack.authtoken);
    }

    @Test
    void testContentstackUserLoginWhenAlreadyLoggedIn() throws IOException {
        Contentstack contentstack = new Contentstack.Builder()
                .setAuthtoken(null)
                .build();
        Response<LoginDetails> response = contentstack.login("invalid@credentials.com", "invalid@password",
                "invalid_tfa_token");
        Assertions.assertEquals(422, response.code());
    }

    @Test
    void testLogoutWithAuthtoken() throws IOException {
        String authToken = dotenv.get("auth_token");
        Contentstack contentstack = new Contentstack.Builder()
                .setAuthtoken(authToken)
                .build();
        Response<ResponseBody> status = contentstack.logoutWithAuthtoken(authToken);
        if (status.isSuccessful()) {
            assert status.body() != null;
            System.out.println(status.body().string());
            Assertions.assertEquals(200, status.code());
        } else {
            assert status.errorBody() != null;
            Error error = new Gson().fromJson(status.errorBody().string(), Error.class);
            Assertions.assertEquals(105, error.getErrorCode());
        }
    }

    @Test
    void testLogoutFromContentstack() throws IOException {
        Contentstack contentstack = new Contentstack.Builder().build();
        Response<ResponseBody> logout = contentstack.logoutWithAuthtoken(null);
        if (logout.isSuccessful()) {
            Assertions.assertEquals(200, logout.code());
        } else {
            assert logout.errorBody() != null;
            Error error = new Gson().fromJson(logout.errorBody().string(), Error.class);
            Assertions.assertEquals(105, error.getErrorCode());
        }
        Assertions.assertNull(contentstack.authtoken);
    }

}
