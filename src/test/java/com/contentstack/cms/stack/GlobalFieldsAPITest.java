package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.util.HashMap;

class GlobalFieldsAPITest {

    protected String GlobalFiledUid = "description";
    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static GlobalField globalField;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        globalField = stack.globalField();
    }

    @Test
    void testFetch() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                new JSONObject());
    }

    @Test
    void testSingle() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                new JSONObject());
    }

    @Test
    void testCreate() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                new JSONObject());
    }

    @Test
    void testUpdate() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                new JSONObject());
    }

    @Test
    void testDelete() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                new JSONObject());
    }

    @Test
    void testImport() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                new JSONObject());
    }

    @Test
    void testExport() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                new JSONObject());
    }

}
