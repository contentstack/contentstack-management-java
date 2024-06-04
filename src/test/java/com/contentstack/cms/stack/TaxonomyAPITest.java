package com.contentstack.cms.stack;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.*;
import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;




@Tag("api")
public class TaxonomyAPITest {
    protected static String API_KEY = TestClient.API_KEY;
    protected static String _uid = "sample_one";
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Taxonomy taxonomy;
    protected static Terms terms;

    @BeforeAll
    static void setup() {
        final String AUTHTOKEN = TestClient.AUTHTOKEN;
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        taxonomy = stack.taxonomy();
        terms = stack.taxonomy(_uid).terms();
    }

    @Test
    void fetchAll(){

        try {
            Response<ResponseBody> response = taxonomy.find().execute();
            if (response.isSuccessful()) {
                Assertions.assertTrue(response.isSuccessful());
            } else {
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    @Test
    void fetchSingle() {
        try {
            Response<ResponseBody> response = taxonomy.fetch("regions").execute();
            if (response.isSuccessful()) {
                Assertions.assertTrue(response.isSuccessful());
            } else {
                Assertions.assertFalse(response.isSuccessful());
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    @Test
    void createTaxonomy() throws IOException {
        // add create.json
        JSONObject requestBody = Utils.readJson("mocktaxonomy/create.json");
        taxonomy.addHeader(Util.API_KEY, API_KEY);
        taxonomy.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = taxonomy.create(requestBody).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies", request.url().toString());

    }

    @Test
    void updateTaxonomy() {
        JSONObject updateBody = Utils.readJson("mocktaxonomy/update.json");
        taxonomy.addHeader(Util.API_KEY, API_KEY);
        taxonomy.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = taxonomy.update("sample_one",updateBody).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/sample_one", request.url().toString());

    }

    @Test
    void deleteTaxonomy(){
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
        Request request = taxonomy.delete("sample_one").request();
        Assertions.assertEquals(5, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/sample_one", request.url().toString());
    }

    @Test
    void createTerm() throws IOException {
        terms.clearParams();
        JSONObject term = Utils.readJson("mockTaxonomy/createTerm.json");
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
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/"+ _uid +"/terms",
                request.url().toString());
    }
    @Test
    void fetchAllTerms() throws IOException {
        terms.clearParams();
        terms.addParam("limit", 3);
        Request request = terms.find().request();
        Assertions.assertEquals(2, request.headers().names().size());
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
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/"+ _uid +"/terms?limit=3",
                request.url().toString());
    }

    @Test
    void fetchSingleTerm() throws IOException {
        terms.clearParams();
        Request request = terms.fetch("india").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/"+ _uid +"/terms/india",
                request.url().toString());
    }

    @Test
    void updateTerm(){
        JSONObject Newterm = Utils.readJson("mockTaxonomy/updateTerm.json");
        Request request = terms.update(_uid,Newterm).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertEquals("terms", request.url().pathSegments().get(3));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/"+ _uid +"/terms/sample_one",
                request.url().toString());
    }

    @Test
    void deleteTerm(){
        Request request = taxonomy.delete("sample_one").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("taxonomies", request.url().pathSegments().get(1));
        Assertions.assertEquals(_uid, request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/taxonomies/"+ _uid +"", request.url().toString());
    }

    @Test
    void ancestorsTerm(){
        terms.clearParams();
        terms.addParam("include_referenced_entries_count", true);
        terms.addParam("include_children_count", false);
        Request request = terms.ancestors("term_a2").request();
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
        Assertions.assertEquals("include_children_count=false&include_referenced_entries_count=true",
                request.url().encodedQuery());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/taxonomies/"+ _uid +"/terms/term_a2/ancestors?include_children_count=false&include_referenced_entries_count=true",
                request.url().toString());

    }

    @Test
    void descendantsTerm(){
        terms.clearParams();
        terms.addParam("include_count", true);
        Request request = terms.descendants("term_a2").request();
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
                "https://api.contentstack.io/v3/taxonomies/"+ _uid +"/terms/term_a2/descendants?include_count=true",
                request.url().toString());

    }

    @Test
    void moveTerms(){
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
}
