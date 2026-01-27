package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
public class EnvironmentUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    static Environment environment;

    @BeforeAll
    public static void setupEnv() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        environment = stackInstance.environment("development");
    }

    @Test
    void fetchDefault() {
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "mngToken");
        Environment env = stack.environment();
        Assertions.assertThrows(NullPointerException.class, env::fetch);
        env = stack.environment("env");
        env.addHeader("key", "value");
        env.addParam("key", "value");
        env.removeParam("key");
        env.clearParams();
    }

    @Test
    void fetchEmptyDefault() {
        Stack stack = new Contentstack.Builder().build().stack("apiKey", "mngToken");
        Environment env = stack.environment("");
        Assertions.assertThrows(IllegalStateException.class, env::fetch);
    }

    @Test
    void fetchLocales() {
        environment.addParam("include_count", false);
        environment.addParam("asc", "created_at");
        environment.addParam("desc", "updated_at");
        Request request = environment.find().request();
        Assertions.assertEquals(2, request.headers().names().size()); // X-User-Agent + User-Agent
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("environments", request.url().pathSegments().get(1));
        Assertions.assertEquals("asc=created_at&include_count=false&desc=updated_at", request.url().encodedQuery());    }

    @Test
    void addLocale() {
        Request request = environment.fetch().request();
        Assertions.assertEquals(2, request.headers().names().size()); // X-User-Agent + User-Agent
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("environments", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void getLocale() {
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        Request request = environment.create(requestBody).request();
        Assertions.assertEquals(2, request.headers().names().size()); // X-User-Agent + User-Agent
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("environments", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void updateLocale() {
        JSONObject requestBody = Utils.readJson("environment/add_env.json");
        Request request = environment.update(requestBody).request();
        Assertions.assertEquals(2, request.headers().names().size()); // X-User-Agent + User-Agent
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("environments", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void deleteLocale() {
        Request request = environment.delete().request();
        Assertions.assertEquals(2, request.headers().names().size()); // X-User-Agent + User-Agent
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("environments", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

}
