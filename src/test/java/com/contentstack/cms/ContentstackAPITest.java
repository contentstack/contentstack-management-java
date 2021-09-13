package com.contentstack.cms;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;


public class ContentstackAPITest {

    private static String authToken;
    private static String deliverToken, _API_KEY, _ENV, _HOST, _USERNAME, _PASSWORD;

    @BeforeClass
    public static void getCredentials() {
        Dotenv dotenv = Dotenv.load();
        authToken = dotenv.get("auth_token");
        _USERNAME = dotenv.get("username");
        _PASSWORD = dotenv.get("password");
//        deliverToken = dotenv.get("delivery_token");
//        _API_KEY = dotenv.get("api_key");
//        _ENV = dotenv.get("environment");
//        _HOST = dotenv.get("host");
    }

    @Test
    void testContentstackUserLogin() throws IOException {
        Contentstack contentstack = new Contentstack.Builder()
                .setAuthtoken(authToken)
                .build();
        Response<ResponseBody> login = contentstack.login(_USERNAME, _PASSWORD);
//        if (logout.isSuccessful()) {
//            assert logout.body() != null;
//            String responseString = logout.body().string();
//        }
        Assertions.assertEquals("authtoken_dummy", contentstack.authtoken);
    }


}
