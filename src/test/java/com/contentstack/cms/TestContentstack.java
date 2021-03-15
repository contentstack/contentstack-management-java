package com.contentstack.cms;

import lombok.var;
import org.junit.jupiter.api.*;
import retrofit2.Response;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestContentstack {


    @Nested
    @DisplayName("UnitTest Contentstack Test")
    @Tag("Unit")
    class UnitContentstackTest{

        @Test
        @Order(1)
        void noArgsInitialisation(){
            try {
                new Contentstack.Client();
            }catch (Exception exception){
                Assertions.assertEquals("authtoken can not be null or empty", exception.getLocalizedMessage());
            }
        }

        @Test
        @Order(2)
        void emptyArgsInitialisation(){
            try {
                new Contentstack.Client("");
            }catch (Exception exception){
                Assertions.assertEquals("authtoken can not be null or empty", exception.getLocalizedMessage());
            }
        }

        @Test
        @Order(3)
        void withArgsAuthtokenInitialisation(){
            var client = new Contentstack.Client("authtoken");
            Assertions.assertNotNull(client);
            Assertions.assertEquals("authtoken", client.getAuthtoken());
        }

        @Test
        @Order(4)
        void withBuildArgsInitialisation(){
            var client =
                    new Contentstack.Client("dummey_token").setPort("8080")
                            .setTimeout(300).setProtocol("https").setVersion("v3").setHostname("api.contentstack.io");
            Assertions.assertEquals("dummey_token", client.authtoken);
            Assertions.assertEquals("8080", client.port);
            Assertions.assertEquals(300, client.timeout);
            Assertions.assertEquals("https", client.protocol);
            Assertions.assertEquals("v3", client.version);
            Assertions.assertEquals("api.contentstack.io", client.hostname);
        }

        @Test
        @Order(5)
        void testSetAuthtoken(){
            var client = new Contentstack.Client("authtoken");
            client.setAuthtoken("coauthor");
            String expected = client.getAuthtoken();
            Assertions.assertEquals("coauthor", expected);
        }


        @Test
        @Order(6)
        void testGetAuthtoken(){
            var client = new Contentstack.Client("dummy_authtoken");
            String expected = client.getAuthtoken();
            Assertions.assertEquals("dummy_authtoken", expected);
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
        void withoutBuildArgsInitialisation() {
           var client = new Contentstack.Client("authtoken");
            try {
                Response updateUser = client.user().getUser().execute();
                System.out.println(updateUser);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
