package com.contentstack.cms.global;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

class GlobalFieldsAPITest {

    protected String GlobalFiledUid = "description";
    protected static String GLOBAL_AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static GlobalField globalField;

    @BeforeAll
    static void setup() {
        globalField = new Contentstack.Builder().setAuthtoken(GLOBAL_AUTHTOKEN).build()
                .globalField(API_KEY, MANAGEMENT_TOKEN);

    }

    @Test
    void testFetch() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
    }

    @Test
    void testSingle() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
    }

    @Test
    void testCreate() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
    }

    @Test
    void testUpdate() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
    }

    @Test
    void testDelete() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
    }

    @Test
    void testImport() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
    }

    @Test
    void testExport() {
        Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
    }

}
