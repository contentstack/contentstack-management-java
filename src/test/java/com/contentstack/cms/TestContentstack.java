package com.contentstack.cms;

import com.contentstack.cms.user.model.UserModel;
import lombok.var;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestContentstack {


    @Nested
    @DisplayName("UnitTest Contentstack Test")
    @Tag("Unit")
    class UnitContentstackTest{

        @Test
        @Order(1)
        void emptyArgsInitialisation(){
            try {
                new Contentstack.Client("");
            }catch (Exception exception){
                Assertions.assertEquals("authtoken can not be null or empty", exception.getLocalizedMessage());
            }
        }

        @Test
        @Order(2)
        void withArgsInitialisation(){
            var client = new Contentstack.Client("authtoken");
            Assertions.assertNotNull(client);
        }

        @Test
        @Order(3)
        void withBuildArgsInitialisation(){
            var client = new Contentstack.Client("authtoken").build();
            Assertions.assertNotNull(client);
        }

        @Test
        @Order(4)
        void withoutBuildArgsInitialisation(){
            var client = new Contentstack.Client("authtoken");
            Assertions.assertNotNull(client);
        }
    }


    ///////////////////////////////////////////////////////////////
    //////////////      [API Testcases for Contentstack]      //////////////
    ///////////////////////////////////////////////////////////////


    @Nested
    @DisplayName("API Contentstack Test")
    @Tag("Api")
    class APIContentstackTest{

        @Test
        void withoutBuildArgsInitialisation() throws IOException {
            var client = new Contentstack.Client("authtoken");
            Response updateUser = client.user().getUser(UserModel.class).execute();
            System.out.println(updateUser);
            //System.out.println(updateUser);
        }
    }
}
