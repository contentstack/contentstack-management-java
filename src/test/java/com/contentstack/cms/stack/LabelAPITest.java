package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LabelAPITest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _uid = Dotenv.load().get("authToken");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    static Label label;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        label = stack.label(_uid);
    }


    @Test
    @Disabled
    void getAllLabels() throws IOException {
        Response<ResponseBody> response = label.find().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void getAllLabelsWithBody() throws IOException {
        Response<ResponseBody> response = label.find().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void getAllLabelsWithBodyWithBranch() throws IOException {
        Response<ResponseBody> response = label.addBranch("main").find().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void getLabel() throws IOException {
        Response<ResponseBody> response = label.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());

    }

    @Test
    @Disabled
    void getLabelWithQuery() throws IOException {
        Response<ResponseBody> response = label.fetch().execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void addLabelPost() throws IOException {
        JSONObject theQuery = new JSONObject();

        ArrayList list = new ArrayList();
        list.add("label_uid");
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", list);
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);

        Response<ResponseBody> response = label.create(theQuery).execute();
        Assertions.assertTrue(response.isSuccessful());
    }


    @Test
    @Disabled
    void labelUpdate() throws IOException {

        JSONObject theQuery = new JSONObject();
        ArrayList list = new ArrayList();
        list.add("label_uid");
        JSONObject innerObject = new JSONObject();
        innerObject.put("name", "Test");
        innerObject.put("parent", list);
        innerObject.put("content_types", new ArrayList().add("content_type_uid"));
        theQuery.put("label", innerObject);

        Response<ResponseBody> response = label.update( theQuery).execute();
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    @Disabled
    void labelDelete() throws IOException {
        Response<ResponseBody> response = label.delete().execute();
        Assertions.assertTrue(response.isSuccessful());
    }
}
