package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BranchUnitTest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static Branch branch;
    protected static JSONObject body;

    static String theJsonString = "{\n" +
            "    \"branch\": {\n" +
            "        \"uid\": \"release\",\n" +
            "        \"source\": \"main\"\n" +
            "    }\n" +
            "}";

    @BeforeAll
    static void setup() {
        branch = TestClient.getClient().stack().branch(AUTHTOKEN);
        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theJsonString);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }

    @Test
    void fetchBranch() {
        Request request = branch.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void fetchBranchWithQueryParameter() {
        // limit=2&skip=2&include_count=false
        branch.addParam("limit", 2);
        branch.addParam("skip", 2);
        branch.addParam("include_count", false);
        Request request = branch.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches", request.url().pathSegments().get(2));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void allRoles() {
        branch.clearParams();
        branch.addParam("include_rules", true);
        branch.addParam("include_permissions", true);
        Request request = branch.find().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches", request.url().pathSegments().get(2));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));    }

    @Test
    void singleRole() {
        Request request = branch.fetch().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches", request.url().pathSegments().get(2));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void createRole() {
        Request request = branch.create(body).request();
        Assertions.assertEquals(0, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void deleteBranch() {
        Request request = branch.delete().request();
        Assertions.assertEquals(1, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());    }

    @Test
    void getBranchCompareInstance() {
        Compare compare = branch.compare("dev");
        Assertions.assertNotNull(compare);
    }

    @Test
    void getCompareBranches() {
        Request request = branch.compare("dev")
                .addHeader("authtoken", AUTHTOKEN)
                .addParam("skip", 4).addParam("limit", 20)
                .all().request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void branchCompareContentType() {
        Request request = branch.compare("dev")
                .addHeader("authtoken", AUTHTOKEN)
                .addParam("skip", 4).addParam("limit", 20)
                .contentType().request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void testSpecificToGlobalField() {
        Request request = branch.compare("dev")
                .addHeader("authtoken", AUTHTOKEN)
                .addParam("skip", 4).addParam("limit", 20)
                .specificGlobalField("global_field").request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void testSpecificToContentType() {
        Request request = branch.compare("dev")
                .addHeader("authtoken", AUTHTOKEN)
                .addParam("skip", 4).addParam("limit", 20)
                .specificContentType("ct_type").request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void branchCompareGlobalField() {
        Request request = branch.compare("dev")
                .addHeader("authtoken", AUTHTOKEN)
                .addParam("skip", 4).addParam("limit", 20)
                .globalField().request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void testCreateBranch() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("uid", "global_field_uid");
        requestBody.put("type", "global_field");
        requestBody.put("merge_strategy", "merge_prefer_base");

        Call<ResponseBody> createBranch = TestClient.getClient().stack().branch("main").mergeQueue()
                .addHeader("authtoken", "authtoken")
                .addHeader("authorization", "managementToken")
                .addHeader("apiKey", "the_api_kay")
                .branch("redesign",
                        requestBody,
                        "merge_prefer_compare",
                        "sample comment");

        Request request = createBranch.request();
        System.out.println("request: " + request);
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_merge", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals(
                "default_merge_strategy=merge_prefer_compare&merge_comment=sample comment&base_branch=main&compare_branch=redesign",
                request.url().query());
    }

    @Test
    void branchMergeFind() {
        Request request = branch.mergeQueue()
                .addHeader("authtoken", AUTHTOKEN)
                .addParam("skip", 4).addParam("limit", 20).find().request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_queue", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void branchMergeFetch() {
        Request request = branch.mergeQueue()
                .addHeader("authtoken", AUTHTOKEN)
                .addParam("skip", 4).addParam("limit", 20).fetch("jobId98344").request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_queue", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }


    @Test
    void branchMergeFetchOtherParams() {
        HashMap<String, String> headParams = new HashMap<>();
        headParams.put("param1", "param1");
        Request request = branch.mergeQueue()
                .addHeader("authtoken", AUTHTOKEN).addParams(headParams)
                .addParam("skip", 4).addParam("limit", 20).addHeaders(headParams)
                .fetch("jobId98344").request();

        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches_queue", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void branchMergeParams() {
        HashMap<String, String> headParams = new HashMap<>();
        headParams.put("param1", "param1");

        Branch newBranch = TestClient.getClient().stack().branch();
        newBranch.addHeader("key", "value");
        newBranch.addParams(headParams);
        newBranch.addHeaders(headParams);

        Request request = newBranch.find().request();

        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
        Assertions.assertEquals("branches", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());    }

    @Test
    void branchWithoutUidNullPointerException() {
        Stack stack = TestClient.getClient().stack();
        assertThrows(NullPointerException.class, stack.branch()::fetch);
    }

    @Test
    void branchWithoutUidIllegalStateException() {
        Stack stack = TestClient.getClient().stack();
        assertThrows(IllegalStateException.class, stack.branch("")::fetch);
    }

    @Test
    void testCompare() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key", "value");
        Request request = branch.compare("dev").addHeaders(map).addParams(map).all().request();
        assertNotNull(request.url());
    }

    // --- publish_config.environments on branch create (DX-31564) -----------------
    // These run against a MockWebServer through a bare Retrofit, because the public
    // Contentstack.Builder only speaks https. Retrofit and the Gson converter match
    // what the client builds, so the request body is serialized the same way.

    private MockWebServer server;

    @BeforeEach
    void startServer() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void stopServer() throws IOException {
        server.shutdown();
    }

    private Branch mockedBranch(String uid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/v3/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return new Branch(retrofit, new HashMap<String, Object>(), uid);
    }

    private static JSONObject bodyWithEnvironments(List<String> environments) {
        JSONObject publishConfig = new JSONObject();
        publishConfig.put("environments", environments);
        JSONObject branchJson = new JSONObject();
        branchJson.put("uid", "dev");
        branchJson.put("source", "main");
        branchJson.put("publish_config", publishConfig);
        JSONObject root = new JSONObject();
        root.put("branch", branchJson);
        return root;
    }

    private static JSONObject parse(String json) throws ParseException {
        return (JSONObject) new JSONParser().parse(json);
    }

    private static JSONObject publishConfigSent(JSONObject sent) {
        return (JSONObject) ((JSONObject) sent.get("branch")).get("publish_config");
    }

    @Test
    void createSendsEnvironmentNamesAsGiven() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"branch\":{\"uid\":\"dev\"}}"));
        Response<ResponseBody> response = mockedBranch("dev")
                .create(bodyWithEnvironments(Arrays.asList("development", "staging"))).execute();
        assertTrue(response.isSuccessful());
        JSONObject sent = parse(server.takeRequest().getBody().readUtf8());
        assertEquals(Arrays.asList("development", "staging"), publishConfigSent(sent).get("environments"));
    }

    @Test
    void createSendsEmptyEnvironmentListAsEmptyArray() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"branch\":{\"uid\":\"dev\"}}"));
        mockedBranch("dev").create(bodyWithEnvironments(Collections.<String>emptyList())).execute();
        JSONObject sent = parse(server.takeRequest().getBody().readUtf8());
        assertTrue(publishConfigSent(sent).containsKey("environments"));
        assertEquals(Collections.emptyList(), publishConfigSent(sent).get("environments"));
    }

    @Test
    void createSendsDuplicateEnvironmentNamesAsGiven() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"branch\":{\"uid\":\"dev\"}}"));
        List<String> duplicates = Arrays.asList("development", "development");
        mockedBranch("dev").create(bodyWithEnvironments(duplicates)).execute();
        JSONObject sent = parse(server.takeRequest().getBody().readUtf8());
        assertEquals(duplicates, publishConfigSent(sent).get("environments"));
    }

    @Test
    void createSendsOneHundredFiftyEnvironmentNamesUnchanged() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"branch\":{\"uid\":\"dev\"}}"));
        List<String> many = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            many.add("env_" + i);
        }
        mockedBranch("dev").create(bodyWithEnvironments(many)).execute();
        JSONObject sent = parse(server.takeRequest().getBody().readUtf8());
        List<?> sentNames = (List<?>) publishConfigSent(sent).get("environments");
        assertEquals(150, sentNames.size());
        assertEquals(many, sentNames);
    }

    @Test
    void createSendsMixedCaseAndSpacedNamesUnchanged() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"branch\":{\"uid\":\"dev\"}}"));
        List<String> names = Arrays.asList("Development", " staging ", "PRODUCTION");
        mockedBranch("dev").create(bodyWithEnvironments(names)).execute();
        JSONObject sent = parse(server.takeRequest().getBody().readUtf8());
        assertEquals(names, publishConfigSent(sent).get("environments"));
    }

    @Test
    void createWithoutEnvironmentListSendsBodyUnchanged() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"branch\":{\"uid\":\"release\"}}"));
        mockedBranch("release").create(parse(theJsonString)).execute();
        JSONObject sent = parse(server.takeRequest().getBody().readUtf8());
        assertEquals(parse(theJsonString), sent);
        assertFalse(((JSONObject) sent.get("branch")).containsKey("publish_config"));
    }

    @Test
    void createRejectedWith422ReturnsApiErrorBodyUnchanged() throws Exception {
        String apiError = "{\"error_message\":\"Environment not found.\",\"error_code\":422,"
                + "\"errors\":{\"publish_config.environments\":[\"staging_typo\"]}}";
        server.enqueue(new MockResponse().setResponseCode(422).setBody(apiError));
        Response<ResponseBody> response = mockedBranch("dev")
                .create(bodyWithEnvironments(Arrays.asList("development", "staging_typo"))).execute();
        assertEquals(422, response.code());
        assertFalse(response.isSuccessful());
        assertEquals(apiError, response.errorBody().string());
        JSONObject sent = parse(server.takeRequest().getBody().readUtf8());
        assertEquals(Arrays.asList("development", "staging_typo"), publishConfigSent(sent).get("environments"));
    }

    @Test
    void fetchReturnsEnvironmentListUnchanged() throws Exception {
        String fetched = "{\"branch\":{\"uid\":\"dev\",\"source\":\"main\","
                + "\"publish_config\":{\"environments\":[\"development\",\"staging\"]}}}";
        server.enqueue(new MockResponse().setResponseCode(200).setBody(fetched));
        Response<ResponseBody> response = mockedBranch("dev").fetch().execute();
        assertEquals("/v3/stacks/branches/dev", server.takeRequest().getPath());
        assertEquals(fetched, response.body().string());
    }

}
