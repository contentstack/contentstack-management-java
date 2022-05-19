package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit") public class LocaleUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Locale locale;

    @BeforeAll
    public static void setupEnv() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        locale = stackInstance.locale();
    }

    @Test
    void fetchLocales() {
        Request request = locale.fetch().request();
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
        JSONObject requestBody = Utils.readJson("locales/add_locale.json");
        Request request = locale.addLocale(requestBody).request();
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
        Request request = locale.getLocale("en-us").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
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
        JSONObject requestBody = Utils.readJson("locales/update_locale.json");
        Request request = locale.updateLocale("en-us", requestBody).request();
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
        Request request = locale.deleteLocale("en-us").request();
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

    @Test
    void setFallbackLocale() {
        JSONObject requestBody = Utils.readJson("locales/set_fallback_lang.json");
        Request request = locale.setFallbackLocale(requestBody).request();
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
    void updateFallbackLocale() {
        JSONObject requestBody = Utils.readJson("locales/update_fallback.json");
        Request request = locale.updateFallbackLocale("en-us", requestBody).request();
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
}
