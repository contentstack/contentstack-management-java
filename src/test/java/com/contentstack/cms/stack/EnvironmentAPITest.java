package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Environment environment;

    @BeforeAll
    public static void setupEnv() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        environment = stackInstance.environment();
    }

    @Test
    void fetchLocales() {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("include_count", false);
        queryParam.put("asc", "created_at");
        queryParam.put("desc", "updated_at");
        try {
            Response<ResponseBody> response = environment.fetch(queryParam).execute();
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void addLocale() {
        try {
            Response<ResponseBody> response = environment.get("development").execute();
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getLocale() {
        //add_env.json
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        try {
            Response<ResponseBody> response = environment
                    .add(requestBody).execute();
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateLocale() {
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        try {
            Response<ResponseBody> response = environment
                    .update("development", requestBody).execute();
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteLocale() {
        try {
            Response<ResponseBody> response = environment
                    .delete("development").execute();
            Assertions.assertTrue(response.isSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
