package com.contentstack.cms;

import com.contentstack.cms.core.Error;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;


/*
 @author  ishaileshmishra
 @since   CMS v0.0.1
 */
public class ContentstackAPITest {

    private static Dotenv dotenv;

    @BeforeAll
    public static void getCredentials() {
        dotenv = Dotenv.load();
    }

    @Test
    void testCredentialsAuthtoken() {
        String authToken = dotenv.get("auth_token");
        Assertions.assertNotNull(authToken, "authToken is not null");
    }

    @Test
    void testCredentialsUsername() {
        Assertions.assertNotNull(dotenv.get("username"), "username/ email is not null");
    }

    @Test
    void testCredentialsPassword() {
        Assertions.assertNotNull(dotenv.get("password"), "password is not null");
    }

    @Test
    void testCredentialsDeliveryToken() {
        Assertions.assertNotNull(dotenv.get("delivery_token"), "deliveryToekn is not null");
    }

    @Test
    void testCredentialsApiKey() {
        Assertions.assertNotNull(dotenv.get("api_key"), "apikey is not null");
    }

    @Test
    void testCredentialsEnv() {
        Assertions.assertNotNull(dotenv.get("environment"), "environment is not null");
    }

    @Test
    void testCredentialsHost() {
        Assertions.assertNotNull(dotenv.get("host"), "host is not null");
    }

    @Test
    void testCredentialsOrganizationUid() {
        Assertions.assertNotNull(dotenv.get("organizations_uid"), "organization uid is not null");
    }

    @Test
    void testContentstackUserLogin() throws IOException {
        String authToken = dotenv.get("auth_token");
        Contentstack contentstack = new Contentstack
                .Builder()
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


}
