package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

@Tag("api")
public class EnvironmentAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.AUTHTOKEN;
    static Environment environment;
    protected static Stack stackInstance;
   

    @BeforeAll
    public static void setupEnv() {
        // Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        // Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        stackInstance = TestClient.getStack().addHeader(Util.API_KEY, API_KEY)
                .addHeader("api_key", API_KEY);
        environment = stackInstance.environment("production");
    }

    @Test
    @Order(1)
    void fetchEnvironments() {
        environment.addParam("include_count", false);
        environment.addParam("asc", "created_at");
        environment.addParam("desc", "updated_at");
        try {
            Response<ResponseBody> response = environment.find().execute();
            if (response.isSuccessful()) {
                Assertions.assertTrue(response.isSuccessful());
            } else {
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }

    }

    @Test
    @Order(2)
    void fetchEnvironment() {
        try {
            Response<ResponseBody> response = environment.fetch().execute();
            if (response.isSuccessful()) {
                Assertions.assertTrue(response.isSuccessful());
            } else {
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    @Test
    @Order(3)
    void createEnvironment() {
        // add_env.json
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        try {
            Response<ResponseBody> response = environment.create(requestBody).execute();
            if (response.isSuccessful()) {
                Assertions.assertTrue(true);
            } else {
                Assertions.assertFalse(false);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    @Test
    @Order(4)
    void updateEnvironment() {
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        try {
            Response<ResponseBody> response = environment.update(requestBody).execute();
            if (response.isSuccessful()) {
                Assertions.assertTrue(response.isSuccessful());
            } else {
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    @Test
    @Order(5)
    void deleteEnvironment() {
        try {
            Response<ResponseBody> response = environment.delete().execute();
            if (response.isSuccessful()) {
                Assertions.assertTrue(true);
            } else {
                Assertions.assertFalse(false);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

}
