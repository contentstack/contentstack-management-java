package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExtensionAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("authToken");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    static Extensions extension;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        extension = stack.extensions(_uid);
    }

    @Test
    @Disabled
    void extensionGetAll() throws IOException {
        extension.addParam("query", "\"type\":\"field\"");
        extension.addParam("include_branch", true);
        Response<ResponseBody> response = extension.find().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void getSingleWithUid() throws IOException {
        Response<ResponseBody> response = extension.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void extensionUpdate() throws IOException {
        Response<ResponseBody> response = extension.update(new JSONObject()).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void extensionDelete() throws IOException {
        Response<ResponseBody> response = extension.delete().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void extensionGetSingle() throws IOException {
        Response<ResponseBody> response = extension.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
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
    @Disabled
    void extensionDeleteAgain() throws IOException {
        Response<ResponseBody> response = extension.delete().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

}
