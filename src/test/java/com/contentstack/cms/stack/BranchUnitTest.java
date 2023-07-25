package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Call;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BranchUnitTest {

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
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(3, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("v3", request.url().pathSegments().get(0));
                Assertions.assertNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches",
                                request.url().toString());
        }

        @Test
        void fetchBranchWithQueryParameter() {
                // limit=2&skip=2&include_count=false
                branch.addParam("limit", 2);
                branch.addParam("skip", 2);
                branch.addParam("include_count", false);
                Request request = branch.find().request();
                Assertions.assertEquals(1, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(3, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches", request.url().pathSegments().get(2));
                Assertions.assertEquals("v3", request.url().pathSegments().get(0));
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches?limit=2&skip=2&include_count=false",
                                request.url().toString());
        }

        @Test
        void allRoles() {
                branch.clearParams();
                branch.addParam("include_rules", true);
                branch.addParam("include_permissions", true);
                Request request = branch.find().request();
                Assertions.assertEquals(1, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(3, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches", request.url().pathSegments().get(2));
                Assertions.assertEquals("v3", request.url().pathSegments().get(0));
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches?include_rules=true&include_permissions=true",
                                request.url().toString());
        }

        @Test
        void singleRole() {
                Request request = branch.fetch().request();
                Assertions.assertEquals(1, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(4, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches", request.url().pathSegments().get(2));
                Assertions.assertEquals("v3", request.url().pathSegments().get(0));
                Assertions.assertNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches/" + AUTHTOKEN,
                                request.url().toString());
        }

        @Test
        void createRole() {
                Request request = branch.create(body).request();
                Assertions.assertEquals(0, request.headers().names().size());
                Assertions.assertEquals("POST", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(3, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches", request.url().pathSegments().get(2));
                Assertions.assertNotNull(request.body());
                Assertions.assertNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches",
                                request.url().toString());
        }

        @Test
        void deleteBranch() {
                Request request = branch.delete().request();
                Assertions.assertEquals(1, request.headers().names().size());
                Assertions.assertEquals("DELETE", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(4, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches/" + AUTHTOKEN,
                                request.url().toString());
        }

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
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(3, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches_compare?base_branch=" + AUTHTOKEN
                                                + "&compare_branch=dev&limit=20&skip=4",
                                request.url().toString());
        }

        @Test
        void branchCompareContentType() {
                Request request = branch.compare("dev")
                                .addHeader("authtoken", AUTHTOKEN)
                                .addParam("skip", 4).addParam("limit", 20)
                                .contentType().request();

                Assertions.assertEquals(2, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(4, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches_compare/content_types?base_branch="
                                                + AUTHTOKEN + "&compare_branch=dev&limit=20&skip=4",
                                request.url().toString());
        }

        @Test
        void testSpecificToGlobalField() {
                Request request = branch.compare("dev")
                                .addHeader("authtoken", AUTHTOKEN)
                                .addParam("skip", 4).addParam("limit", 20)
                                .specificGlobalField("global_field").request();

                Assertions.assertEquals(2, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(5, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches_compare/global_fields/global_field?base_branch="
                                                + AUTHTOKEN + "&compare_branch=dev&limit=20&skip=4",
                                request.url().toString());
        }

        @Test
        void testSpecificToContentType() {
                Request request = branch.compare("dev")
                                .addHeader("authtoken", AUTHTOKEN)
                                .addParam("skip", 4).addParam("limit", 20)
                                .specificContentType("ct_type").request();

                Assertions.assertEquals(2, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(5, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches_compare/content_types/ct_type?base_branch="
                                                + AUTHTOKEN + "&compare_branch=dev&limit=20&skip=4",
                                request.url().toString());
        }

        @Test
        void branchCompareGlobalField() {
                Request request = branch.compare("dev")
                                .addHeader("authtoken", AUTHTOKEN)
                                .addParam("skip", 4).addParam("limit", 20)
                                .globalField().request();

                Assertions.assertEquals(2, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(4, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches_compare", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches_compare/global_fields?base_branch="
                                                + AUTHTOKEN + "&compare_branch=dev&limit=20&skip=4",
                                request.url().toString());
        }

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
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(3, request.url().pathSegments().size());
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
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(3, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches_queue", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches_queue?base_branch=" + AUTHTOKEN
                                                + "&limit=20&skip=4",
                                request.url().toString());
        }

        @Test
        void branchMergeFetch() {
                Request request = branch.mergeQueue()
                                .addHeader("authtoken", AUTHTOKEN)
                                .addParam("skip", 4).addParam("limit", 20).fetch("jobId98344").request();

                Assertions.assertEquals(2, request.headers().names().size());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertTrue(request.url().isHttps());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertEquals(4, request.url().pathSegments().size());
                Assertions.assertEquals("stacks", request.url().pathSegments().get(1));
                Assertions.assertEquals("branches_queue", request.url().pathSegments().get(2));
                Assertions.assertNull(request.body());
                Assertions.assertNotNull(request.url().encodedQuery());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/stacks/branches_queue/jobId98344?base_branch="
                                                + AUTHTOKEN + "&limit=20&skip=4",
                                request.url().toString());
        }

}
