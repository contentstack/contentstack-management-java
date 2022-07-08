package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkflowUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String _authtoken = Dotenv.load().get("auth_token");
    protected static String _uid = Dotenv.load().get("workflow_uid");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
    protected static Workflow workflow;
    protected static JSONObject body;

    static String theJsonString = "{\n" +
            "    \"workflow\":{\n" +
            "        \"workflow_stages\":[\n" +
            "            {\n" +
            "                \"color\":\"#2196f3\",\n" +
            "                \"SYS_ACL\":{\n" +
            "                    \"roles\":{\n" +
            "                        \"uids\":[\n" +
            "                            \n" +
            "                        ]\n" +
            "                    },\n" +
            "                    \"users\":{\n" +
            "                        \"uids\":[\n" +
            "                            \"$all\"\n" +
            "                        ]\n" +
            "                    },\n" +
            "                    \"others\":{\n" +
            "                        \n" +
            "                    }\n" +
            "                },\n" +
            "                \"next_available_stages\":[\n" +
            "                    \"$all\"\n" +
            "                ],\n" +
            "                \"allStages\":true,\n" +
            "                \"allUsers\":true,\n" +
            "                \"specificStages\":false,\n" +
            "                \"specificUsers\":false,\n" +
            "                \"entry_lock\":\"$none\",\n" +
            "                \"name\":\"Review\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"color\":\"#74ba76\",\n" +
            "                \"SYS_ACL\":{\n" +
            "                    \"roles\":{\n" +
            "                        \"uids\":[\n" +
            "                            \n" +
            "                        ]\n" +
            "                    },\n" +
            "                    \"users\":{\n" +
            "                        \"uids\":[\n" +
            "                            \"$all\"\n" +
            "                        ]\n" +
            "                    },\n" +
            "                    \"others\":{\n" +
            "                        \n" +
            "                    }\n" +
            "                },\n" +
            "                \"allStages\":true,\n" +
            "                \"allUsers\":true,\n" +
            "                \"specificStages\":false,\n" +
            "                \"specificUsers\":false,\n" +
            "                \"next_available_stages\":[\n" +
            "                    \"$all\"\n" +
            "                ],\n" +
            "                \"entry_lock\":\"$none\",\n" +
            "                \"name\":\"Complete\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"admin_users\":{\n" +
            "            \"users\":[\n" +
            "                \n" +
            "            ]\n" +
            "        },\n" +
            "        \"name\":\"Workflow\",\n" +
            "        \"enabled\":true,\n" +
            "        \"branches\":[\n" +
            "            \"main\",\n" +
            "            \"development\"\n" +
            "        ],\n" +
            "        \"content_types\":[\n" +
            "            \"$all\"\n" +
            "        ]\n" +
            "    }\n" +
            "}";


    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        workflow = stack.workflow();

        try {
            JSONParser parser = new JSONParser();
            body = (JSONObject) parser.parse(theJsonString);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }


    @Test
    @Order(1)
    void workflowHeaders() {
        workflow.addHeader("Content-Type", "application/json");
        Assertions.assertEquals(3, workflow.headers.size());
    }

    @Test
    @Order(2)
    void workflowParamsWithSizeZero() {
        Assertions.assertEquals(0, workflow.params.size());
    }

    @Test
    @Order(3)
    void workflowParamsWithSizeMin() {
        workflow.addParam("paramFakeKey", "paramFakeValue");
        Assertions.assertEquals(1, workflow.params.size());
    }

    @Test
    @Order(4)
    void workflowParamsWithSizeMax() {
        workflow.removeParam("paramFakeKey");
        workflow.addParam("include_rules", true);
        workflow.addParam("include_permissions", true);
        Assertions.assertEquals(2, workflow.params.size());
    }

    @Test
    @Order(5)
    void workflowFetchAll() {
        workflow.clearParams();
        Request request = workflow.fetch().request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows", request.url().toString());
    }

    @Test
    @Order(6)
    void workflowFetchByWorkflowId() {
        Request request = workflow.single(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/" + _uid, request.url().toString());
    }

    @Test
    @Order(7)
    void workflowCreate() {
        Request request = workflow.create(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows", request.url().toString());
    }

    @Test
    @Order(8)
    void workflowUpdate() {
        Request request = workflow.update(_uid, body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/" + _uid, request.url().toString());
    }

    @Test
    @Order(9)
    void workflowDisable() {
        Request request = workflow.disable(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/" + _uid + "/disable", request.url().toString());
    }

    @Test
    @Order(10)
    void workflowEnable() {
        Request request = workflow.enable(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/" + _uid + "/enable", request.url().toString());
    }

    @Test
    @Order(11)
    void workflowDelete() {
        Request request = workflow.delete(_uid).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/" + _uid, request.url().toString());
    }

    @Test
    @Order(13)
    void workflowCreatePublishRule() {
        Request request = workflow.createPublishRule(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/publishing_rules", request.url().toString());
    }

    @Test
    @Order(14)
    void workflowUpdatePublishRule() {
        Request request = workflow.updatePublishRule("ruleUid", body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/publishing_rules/ruleUid", request.url().toString());
    }

    @Test
    @Order(15)
    void workflowDeletePublishRule() {
        Request request = workflow.deletePublishRule("ruleUid").request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/workflows/publishing_rules/ruleUid", request.url().toString());
    }

    @Test
    @Order(16)
    void workflowFetchPublishRules() {
        List<String> contentTypes = Arrays.asList("contentTypeUid1", "contentTypeUid2", "contentTypeUid3");
        Request request = workflow.fetchPublishRules(contentTypes).request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertNotNull(request.url().toString());
    }

    @Test
    @Order(17)
    void workflowFetchPublishRule() {
        Request request = workflow.fetchPublishRule("ruleUid").request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNotNull(request.url().toString());
    }

    @Test
    @Order(17)
    void workflowFetchPublishRuleContentType() {
        Request request = workflow.fetchPublishRuleContentType("contentTypeUid").request();
        Assertions.assertEquals(3, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertNotNull("https://api.contentstack.io/v3/workflows", request.url().toString());
    }


    @Test
    @Order(18)
    void workflowFetchTasks() {
        Request request = workflow.fetchTasks(_authtoken).request();
        Assertions.assertEquals(4, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("workflows", request.url().pathSegments().get(1));
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertNotNull(request.url().toString());
    }


}
