package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ExtensionAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
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
        Map<String, Object> body = new HashMap<>();
        body.put("keyForSomething", "valueForSomething");
        Response<ResponseBody> response = extension.getAll("\"type\":\"field\"", false).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void getSingleWithUid() throws IOException {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("include_count", false);
        queryParam.put("include_branch", false);
        Response<ResponseBody> response = extension.get(_uid, queryParam).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionUpdate() throws IOException {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("include_count", false);
        queryParam.put("include_branch", false);

        JSONObject body = new JSONObject();
        JSONObject innerBody = new JSONObject();
        innerBody.put("tags", Arrays.asList("tag1", "tag2"));
        innerBody.put("data_type", "text");
        innerBody.put("title", "Old Extension");
        innerBody.put("src", "Enter either the source code");
        body.put("extension", innerBody);

        Response<ResponseBody> response = extension.update(_uid, queryParam, body).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionDelete() throws IOException {
        Response<ResponseBody> response = extension.delete(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionGetSingle() throws IOException {
        Map<String, Object> theQuery = new HashMap<>();
        theQuery.put("include_branch", false);
        Response<ResponseBody> response = extension.get(_uid, theQuery).execute();
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
        Response<ResponseBody> response = extension.uploadCustomField(params, param).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void updateTheExtension() throws IOException {
        JSONObject theQuery = new JSONObject();
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", Arrays.asList("label_uid0", "label_uid1"));
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);
        Map<String, Object> params = new HashMap<>();
        params.put("include_something", "true");
        Response<ResponseBody> response = extension.uploadCustomField(params, theQuery).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void extensionDeleteAgain() throws IOException {
        Response<ResponseBody> response = extension.delete(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

}
