package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

@Tag("api")
public class LocaleAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    static Locale locale;
    protected static Stack stackInstance;

    @BeforeAll
    public static void setupEnv() {
        // Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        // Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
         stackInstance = TestClient.getStack().addHeader(Util.API_KEY, API_KEY)
                .addHeader("api_key", API_KEY);
        locale = stackInstance.locale(MANAGEMENT_TOKEN);
    }

    @Test
    void fetchLocales() throws IOException {
        Response<ResponseBody> response = locale.find().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void addLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/add_locale.json");
        Response<ResponseBody> response = locale.create(requestBody).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void getLocale() throws IOException {
        Response<ResponseBody> response = locale.fetch().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void updateLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/update_locale.json");
        Response<ResponseBody> response = locale.update(requestBody).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void deleteLocale() throws IOException {
        Response<ResponseBody> response = locale.delete().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void setFallbackLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/set_fallback_lang.json");
        Response<ResponseBody> response = locale.setFallback(requestBody).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void updateFallbackLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/update_fallback.json");
        Response<ResponseBody> response = locale.updateFallback(requestBody).execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }
}
