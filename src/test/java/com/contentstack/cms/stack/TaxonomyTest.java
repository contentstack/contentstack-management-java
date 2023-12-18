package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaxonomyTest {

    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = TestClient.AUTHTOKEN;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Taxonomy taxonomy;
    protected static Terms terms;
    protected static JSONObject body;

    // Create a JSONObject, JSONObject could be created in multiple ways.
    // We choose JSONParser that converts string to JSONObject
    static String theBody = "{\n" +
            "  \"taxonomy\": {\n" +
            "    \"name\": \"Taxonomy 1\",\n" +
            "    \"description\": \"Description updated for Taxonomy 1\"\n" +
            "  }\n" +
            "}";

    @BeforeAll
    static void setup() {
        final String AUTHTOKEN = TestClient.AUTHTOKEN;
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        taxonomy = stack.taxonomy();
        terms = stack.taxonomy(_uid).terms();
        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theBody);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }

    @Test
    void findTest() {
        Request request = taxonomy.find().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies?include_rules=true&include_permissions=true",
                request.url().toString());
    }

    @Test
    void findTestWithParams() {
        taxonomy.addParam("limit", 2);
        taxonomy.addParam("skip", 2);
        taxonomy.addParam("include_count", false);
        Request request = taxonomy.find().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies?limit=2&skip=2&include_count=false",
                request.url().toString());
    }

    @Test
    void findTestCustomParams() {
        taxonomy.clearParams();
        taxonomy.addParam("include_rules", true);
        taxonomy.addParam("include_permissions", true);
        Request request = taxonomy.find().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies?include_rules=true&include_permissions=true",
                request.url().toString());
    }

    @Test
    void fetchTest() {
        Request request = taxonomy.fetch("taxonomyId").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies/taxonomyId?limit=2&skip=2&include_count=false",
                request.url().toString());
    }

    @Test
    void updateTest() {
        Request request = taxonomy.update("taxonomyId", body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/taxonomyId", request.url().toString());
    }

    @Test
    void deleteTest() {
        Request request = taxonomy.delete("taxonomyId").request();
        Assertions.assertEquals(5, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/taxonomyId", request.url().toString());
    }

    @Test
    void deleteTestWithHeaders() {
        taxonomy.clearParams();
        taxonomy.addHeader("Content-Type", "application/json");
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        headers.put("key_param1", "key_param_value");
        headers.put("key_param2", "key_param_value");
        params.put("key_param3", "key_param_value");
        params.put("key_param4", "key_param_value");
        taxonomy.addHeaders(headers);
        taxonomy.addParams(params);
        Request request = taxonomy.delete("taxonomyId").request();
        Assertions.assertEquals(5, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/taxonomyId", request.url().toString());
    }

    @Test
    void createTest() {
        JSONObject obj = new JSONObject();
        obj.put("name", "sample");
        Request request = taxonomy.create(obj).request();
        Assertions.assertEquals(4, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies", request.url().toString());
    }

    @Test
    void testCreateTerm() {
        terms.clearParams();
        JSONObject term = new JSONObject();
        JSONObject termDetails = new JSONObject();
        termDetails.put("uid", "term_1");
        termDetails.put("name", "Term 1");
        termDetails.put("description", "Term 1 Description for Taxonomy 1");
        term.put("term", termDetails);
        term.put("parent_uid", null);
        Request request = terms.create(term).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/auth999999999/terms",
                request.url().toString());
    }

    @Test
    void testFindTerm() {
        terms.clearParams();
        terms.addParam("limit", 3);
        Request request = terms.find().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNull(request.body());
        Assertions.assertEquals("limit=3", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/" + _uid + "/terms?limit=3",
                request.url().toString());
    }

    @Test
    void testFetchTerm() {
        terms.clearParams();
        terms.addParam("include_referenced_entries_count", true);
        terms.addParam("include_children_count", false);
        Request request = terms.fetch(_uid).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNull(request.body());
        Assertions.assertEquals("include_children_count=false&include_referenced_entries_count=true",
                request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies/auth999999999/terms/auth999999999?include_children_count=false&include_referenced_entries_count=true",
                request.url().toString());
    }

    @Test
    void testTermUpdate() {
        terms.clearParams();
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        terms.addParam("include_referenced_entries_count", true);
        terms.addParam("include_children_count", false);
        terms.addHeader("Accept-Encoding", "UTF-8");
        headers.put("Accept-Encoding", "UTF-8");
        params.put("include_children_count", "true");
        terms.addParams(params);
        terms.addHeaders(headers);
        Request request = terms.update(_uid, new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
    }


    @Test
    void testTermMoveOrReorder() {
        terms.clearParams();
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        terms.addHeader("Accept-Encoding", "UTF-8");
        headers.put("Accept-Encoding", "UTF-8");
        params.put("force", true);
        terms.addParams(params);
        terms.addHeaders(headers);
        Request request = terms.reorder(_uid, new JSONObject()).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
    }

    @Test
    void testTermSearch() {
        terms.clearParams();
        Request request = terms.search("contentstack").request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
    }

    @Test
    void testDescendantsTerm() {
        terms.clearParams();
        terms.addParam("include_count", true);
        Request request = terms.descendants("termId45").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNull(request.body());
        Assertions.assertEquals("include_count=true", request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies/auth999999999/terms/termId45/descendants?include_count=true",
                request.url().toString());
    }

    @Test
    void testAncestorsTerm() {
        terms.clearParams();
        terms.addParam("include_referenced_entries_count", true);
        terms.addParam("include_children_count", false);
        Request request = terms.ancestors("termId45").request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNull(request.body());
        Assertions.assertEquals("include_children_count=false&include_referenced_entries_count=true",
                request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies/auth999999999/terms/termId45/ancestors?include_children_count=false&include_referenced_entries_count=true",
                request.url().toString());
    }

    @Test
    void findTestAPI() throws IOException {
        Taxonomy taxonomy = new Contentstack.Builder()
                .setAuthtoken(TestClient.AUTHTOKEN)
                .setHost("api.contentstack.io")
                .build()
                .stack("blt12c1ba95c1b11e88", "")
                .taxonomy();
        Response<ResponseBody> response = taxonomy.addHeader("authtoken", "blt67b95aeb964f5262").find().execute();
        System.out.println(response);
    }

}
