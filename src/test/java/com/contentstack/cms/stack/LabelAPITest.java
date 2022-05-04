package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class LabelAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("api_key");
    protected static String _uid = Dotenv.load().get("auth_token");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    static Label label;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        label = stack.label();
    }


    @Test
    void getAllLabels() throws IOException {
        Response<RequestBody> response = label.get().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void getAllLabelsWithBody() throws IOException {
        Map<String, Object> jsonJson = new HashMap<>();
        jsonJson.put("include_count", false);
        jsonJson.put("include_branch", false);
        Response<RequestBody> response = label.get(jsonJson).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void getAllLabelsWithBodyWithBranch() throws IOException {
        Map<String, Object> jsonJson = new HashMap<>();
        jsonJson.put("include_count", false);
        jsonJson.put("include_branch", false);
        Response<RequestBody> response = label.addBranch("main").get(jsonJson).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void getLabel() throws IOException {
        Response<ResponseBody> response = label.get(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());

    }

    @Test
    void getLabelWithQuery() throws IOException {
        Map<String, Object> theQuery = new HashMap<>();
        theQuery.put("include_branch", false);
        Response<ResponseBody> response = label.get(_uid, theQuery).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void addLabelPost() throws IOException {
        JSONObject theQuery = new JSONObject();

        ArrayList list = new ArrayList();
        list.add("label_uid");
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", list);
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);

        Response<ResponseBody> response = label.add(theQuery).execute();
        Assertions.assertTrue(response.isSuccessful());
    }


    @Test
    void labelUpdate() throws IOException {

        JSONObject theQuery = new JSONObject();
        ArrayList list = new ArrayList();
        list.add("label_uid");
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", list);
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);

        Response<ResponseBody> response = label.update(_uid, theQuery).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void labelDelete() throws IOException {
        Response<ResponseBody> response = label.delete(_uid).execute();
        Assertions.assertTrue(response.isSuccessful());
    }
}
