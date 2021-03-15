package com.contentstack.cms.user;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Error;
import com.google.gson.Gson;
import lombok.var;
import org.junit.jupiter.api.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUser {

    @Nested
    @DisplayName("UnitTest User")
    @Tag("Unit")
    class UnitTestUser{

        @Test
        @Order(1)
        void testUserInstance(){
            var client = new Contentstack.Client();
            var isUser = client.user();
            Assertions.assertTrue(isUser != null);
            Assertions.assertTrue(isUser instanceof User);
        }

        @Test
        @Order(1)
        void testUsersGetUser() throws IOException {
            var client = new Contentstack.Client("blt7834676734");
            var userInstance = client.user();
            var response = userInstance.getUser().execute();
            if (!response.isSuccessful()) {
                Gson gson = new Gson();
                Error  error=gson.fromJson(response.errorBody().charStream(), Error.class);
                System.out.println(error.getErrorCode());
                Assertions.assertEquals(105, error.getErrorCode());
            }
        }

        @Test
        @Order(2)
        void testUsersUpdateUser() throws IOException {
            var client = new Contentstack.Client("blt7834676734");
            var userInstance = client.user();
            var response = userInstance.updateUser().execute();
            if (!response.isSuccessful()) {
                Gson gson = new Gson();
                Error  error=gson.fromJson(response.errorBody().charStream(), Error.class);
                System.out.println(error.getErrorCode());
                Assertions.assertEquals("You're not allowed in here unless you're logged in.", error.getErrorMessage());
            }

        }

    }

}
