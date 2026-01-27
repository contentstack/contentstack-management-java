package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import com.google.gson.JsonObject;

import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.AUTHTOKEN;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Roles roles;
    protected static String Taxonomy_uid = "sample_two";
    protected static Taxonomy taxonomy;
    protected static Terms term;
    protected static Stack stack;


    @BeforeAll
    static void setup() {
        final String AUTHTOKEN = TestClient.AUTHTOKEN;
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        roles = stack.roles(_uid);
        taxonomy = stack.taxonomy();
        term = stack.taxonomy(Taxonomy_uid).terms();
    }

    @Test
    void allRoles() throws IOException {
        roles = stack.roles(_uid);
        roles.addParam("include_rules", true);
        roles.addParam("include_permissions", true);
        Response<ResponseBody> response = roles.find().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }

    }

    @Test
    void singleRole() throws IOException {
        roles = stack.roles(_uid);
        Response<ResponseBody> response = roles.fetch().execute();
        if (response.isSuccessful()) {
            Assertions.assertTrue(response.isSuccessful());
        } else {
            Assertions.assertFalse(response.isSuccessful());
        }
    }

    @Test
    void createRole() {
        JSONObject body = new JSONObject();
        body.put("abcd", "abcd");
        roles = stack.roles(_uid);
        roles.addHeader(Util.API_KEY, API_KEY);
        roles.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = roles.create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void updateRole() {

        roles = stack.roles(_uid);
        roles.addHeader("authtoken", AUTHTOKEN);
        roles.addHeader("api_key", API_KEY);
        JSONObject object = new JSONObject();
        object.put("key", "value");

        Request request = roles.update(object).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void deleteRole() {
        roles = stack.roles(_uid);
        roles.addHeader("authtoken", AUTHTOKEN);
        roles.addHeader("api_key", API_KEY);
        Request request = roles.delete().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }
    @Test
    void createRoleWithRule1() throws IOException{
        JSONObject requestTaxonomy = Utils.readJson("mocktaxonomy/create.json");
        taxonomy.addHeader(Util.API_KEY, API_KEY);
        taxonomy.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = taxonomy.create(requestTaxonomy).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }
    @Test
    void createRoleWithRule2() throws IOException{
        JSONObject requestTerm = Utils.readJson("mocktaxonomy/createTerm.json");
        Request request = term.create(requestTerm).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }
    @Test
    void createRoleWithTaxonomy() throws IOException{
        JSONObject requestBody = Utils.readJson("mockrole/createRole.json");
        roles.addHeader(Util.API_KEY, API_KEY);
        roles.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = roles.create(requestBody).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());    }

}
