package com.contentstack.cms.organization;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import retrofit2.Retrofit;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrgMockTests {

    private Organization organization;
    private String DEFAULT_AUTHTOKEN;
    private String DEFAULT_ORG_UID;


    @BeforeAll
    public void setUp() {
        Dotenv dotenv = Dotenv.load();
        DEFAULT_AUTHTOKEN = dotenv.get("auth_token");
        DEFAULT_ORG_UID = dotenv.get("organizations_uid");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.contentstack.io/v3/").build();
        organization = new Organization(retrofit);
    }

    @Test void testInit(){
        Assertions.assertTrue(true);
    }

}
