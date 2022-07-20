package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;


@Tag("api")
public class EnvironmentAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    static Environment environment;

    @BeforeAll
    public static void setupEnv() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        environment = stackInstance.environment("production");
    }

    @Test
    @Order(1)
    void fetchLocales() {
        environment.addParam("include_count", false);
        environment.addParam("asc", "created_at");
        environment.addParam("desc", "updated_at");
        try {
            Response<ResponseBody> response = environment.find().execute();
            if (response.isSuccessful()){
                Assertions.assertTrue(response.isSuccessful());
            }else{
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Order(2)
    void addLocale() {
        try {
            Response<ResponseBody> response = environment.fetch().execute();
            if (response.isSuccessful()){
                Assertions.assertTrue(response.isSuccessful());
            }else{
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(3)
    void getLocale() {
        //add_env.json
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        try {
            Response<ResponseBody> response = environment.create(requestBody).execute();
            if (response.isSuccessful()){
                Assertions.assertTrue(response.isSuccessful());
            }else{
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(4)
    void updateLocale() {
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        try {
            Response<ResponseBody> response = environment.update( requestBody).execute();
            if (response.isSuccessful()){
                Assertions.assertTrue(response.isSuccessful());
            }else{
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(5)
    void deleteLocale() {
        try {
            Response<ResponseBody> response = environment.delete().execute();
            if (response.isSuccessful()){
                Assertions.assertTrue(response.isSuccessful());
            }else{
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
