package com.contentstack.cms.organization;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.user.CSResponse;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import retrofit2.Response;

import java.io.IOException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrgApiTests {

    private Organization organization;
    String emailId = Dotenv.load().get("username");
    String password = Dotenv.load().get("password");
    String authtoken = Dotenv.load().get("authtoken"); // read default authtoken from the .env file

    @BeforeAll
    public void setUp() throws IOException {
        Contentstack contentstack = new Contentstack.Builder().build();
        contentstack.login(emailId, password);
        organization = contentstack.organization();
    }

    @Test
    @DisplayName("organization fetch all")
    void testOrganizationFetchAll() throws IOException {
        Response<ResponseBody> response = organization.getAll().execute();

//        if (response.isSuccessful()){
//            response.toJson()
//            response.toString()
//            response.(LoginDetails.class)
//        }

        //Request orgsRequest = organization.getAll().request();
        //System.out.println(orgs);
    }


}
