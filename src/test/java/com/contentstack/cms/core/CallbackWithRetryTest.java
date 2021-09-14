package com.contentstack.cms.core;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.user.LoginDetails;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

class CallbackWithRetryTest {

    public static Dotenv dotenv;

    @BeforeAll
    public static void setup() {
        dotenv = Dotenv.load(); // load the credentials
    }

    @Test
    public void testRetryPolicy() throws IOException {
        String emailId = Objects.requireNonNull(dotenv.get("username"));
        String password = Objects.requireNonNull(dotenv.get("password"));
        Contentstack client = new Contentstack.Builder().build();
        Response<LoginDetails> login = client.login(emailId, password);
        if (login.isSuccessful()) {
            Assertions.assertEquals("Login Successful.", login.body().getNotice());
        } else {
            Assertions.fail("could not loggedIn, Invalid credentials");
        }
    }
}