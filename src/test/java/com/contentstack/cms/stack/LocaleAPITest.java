package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.Utils;
import com.contentstack.cms.stack.Locale;
import com.contentstack.cms.stack.Stack;
import io.github.cdimascio.dotenv.Dotenv;
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

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Locale locale;

    @BeforeAll
    public static void setupEnv() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
        Stack stackInstance = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        locale = stackInstance.locale();
    }

    @Test
    void fetchLocales() throws IOException {
        Response<ResponseBody> response = locale.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void addLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/add_locale.json");
        Response<ResponseBody> response = locale.create(requestBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void getLocale() throws IOException {
        Response<ResponseBody> response = locale.single("en-us").execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void updateLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/update_locale.json");
        Response<ResponseBody> response = locale.update("en-us", requestBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void deleteLocale() throws IOException {
        Response<ResponseBody> response = locale.delete("en-us").execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void setFallbackLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/set_fallback_lang.json");
        Response<ResponseBody> response = locale.setFallback(requestBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void updateFallbackLocale() throws IOException {
        JSONObject requestBody = Utils.readJson("locales/update_fallback.json");
        Response<ResponseBody> response = locale.updateFallback("en-us", requestBody).execute();
        Assertions.assertTrue(response.isSuccessful());
    }
}
