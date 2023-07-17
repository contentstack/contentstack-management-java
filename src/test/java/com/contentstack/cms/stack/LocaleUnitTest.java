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
public class LocaleUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    static Locale locale;

    @BeforeAll
    public static void setupEnv() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        locale = stackInstance.locale("en-us");
    }

    @Test
    void fetchLocales() {
        locale.addParam("include_count", true);
        Request request = locale.find().request();
        Assertions.assertEquals(0, request.headers().names().size());
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
        Request request = locale.create(requestBody).request();
        Assertions.assertEquals(0, request.headers().names().size());
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
        locale.clearParams();
        Request request = locale.fetch().request();
        Assertions.assertEquals(0, request.headers().names().size());
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
        locale.clearParams();
        Request request = locale.update( requestBody).request();
        Assertions.assertEquals(0, request.headers().names().size());
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
        Request request = locale.delete().request();
        Assertions.assertEquals(0, request.headers().names().size());
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
        Request request = locale.setFallback(requestBody).request();
        Assertions.assertEquals(0, request.headers().names().size());
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
        Request request = locale.updateFallback( requestBody).request();
        Assertions.assertEquals(0, request.headers().names().size());
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
