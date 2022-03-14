package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentUnitTest {

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
        Request request = environment.fetch(queryParam).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("locales", request.url().pathSegments().get(1));
        Assertions.assertEquals("include_count=true", request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/locales?include_count=true",
                request.url().toString());
    }

    @Test
    void addLocale() {
        Request request = environment.get("development").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("locales", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/locales",
                request.url().toString());
    }

    @Test
    void getLocale() {
        //add_env.json
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        Request request = environment.add(requestBody).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("locales", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/locales/en-us",
                request.url().toString());
    }

    @Test
    void updateLocale() {
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        Request request = environment.update("development", requestBody).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("locales", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/locales/en-us",
                request.url().toString());
    }

    @Test
    void deleteLocale() {
        Request request = environment.delete("development").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("locales", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/locales/en-us",
                request.url().toString());
    }

}
