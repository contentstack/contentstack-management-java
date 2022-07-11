package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import com.contentstack.cms.stack.Extensions;
import com.contentstack.cms.stack.Stack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Tag("api")
class ExtensionAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Extensions extension;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        extension = stack.extensions();
    }

    @Test
    void extensionGetAll() throws IOException {
        extension.addParam("query", "\"type\":\"field\"");
        extension.addParam("include_branch", true);
        Response<ResponseBody> response = extension.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void getSingleWithUid() throws IOException {
        Response<ResponseBody> response = extension.single(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionUpdate() throws IOException {
        Response<ResponseBody> response = extension.update(_uid, new JSONObject()).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionDelete() throws IOException {
        Response<ResponseBody> response = extension.delete(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionGetSingle() throws IOException {
        Response<ResponseBody> response = extension.single(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void testUploadCustomField() throws IOException {
        Map<String, RequestBody> params = new HashMap<>();
        RequestBody someDataBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) {
            }
        };
        params.put("DYNAMIC_PARAM_NAME", someDataBody);
        Map<String, Object> param = new HashMap<>();
        param.put("include_branch", false);
        Response<ResponseBody> response = extension.uploadCustomField(params).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void updateTheExtension() throws IOException {
        //Response<ResponseBody> response = extension.uploadCustomField(new JSONObject()).execute();
        //Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionDeleteAgain() throws IOException {
        Response<ResponseBody> response = extension.delete(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

}
