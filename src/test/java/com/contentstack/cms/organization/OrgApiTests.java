package com.contentstack.cms.organization;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("unit")
class OrgApiTests {

    private static Organization ORG;
    private final String ORG_ID = TestClient.ORGANIZATION_UID;

    private JSONObject theJSONBody(@NotNull String _body) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(_body);
        } catch (ParseException e) {
            return null;
        }
    }

    @BeforeEach
    void setUp() {
        ORG = TestClient.getClient().organization()
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("apiKey", TestClient.API_KEY);
    }

    @Order(1)
    @Test
    void testGetAll() {
        Request request = ORG.find().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations", request.url().toString());
    }

    @Order(2)
    @Test
    void testGetAllWithParams() {
        ORG.addParam("include_plan", true);
        Request request = ORG.find().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations?include_plan=true", request.url().toString());
    }

    @Order(3)
    @Test
    void testGetSingle() {
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("apiKey", TestClient.API_KEY);
        Request request = ORG.fetch().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID, request.url().toString());

    }

    @Order(4)
    @Test
    void testGetSingleWithInclude() {
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("apiKey", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.fetch().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("include_plan=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "?include_plan=true", request.url().toString());
    }

    @Order(5)
    @Test
    void testRole() {
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("apiKey", TestClient.API_KEY)
                .addParam("include_plan", true)
                .addParam("limit", 2)
                .addParam("skip", 2)
                .addParam("asc", true)
                .addParam("desc", true)
                .addParam("include_count", true)
                .addParam("include_stack_roles", true);
        Request request = ORG.roles().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("asc=true&include_stack_roles=true&include_plan=true&limit=2&skip=2&include_count=true&desc=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/roles?asc=true&include_stack_roles=true&include_plan=true&limit=2&skip=2&include_count=true&desc=true", request.url().toString());

    }

    @Order(6)
    @Test
    void testRoleWithQueryPrams() {

        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("apiKey", TestClient.API_KEY)
                .addParam("include_plan", true).addParam("limit", 2)
                .addParam("skip", 2)
                .addParam("asc", true)
                .addParam("desc", true)
                .addParam("include_count", true)
                .addParam("include_stack_roles", false);
        Request request = ORG.roles().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("asc=true&include_stack_roles=false&include_plan=true&limit=2&skip=2&include_count=true&desc=true", request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/roles?asc=true&include_stack_roles=false&include_plan=true&limit=2&skip=2&include_count=true&desc=true", request.url().toString());

    }

    @Order(7)
    @Test
    void testInviteUser() {
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        String requestBody = "{\n" +
                "\t\"share\": {\n" +
                "\t\t\"users\": {\n" +
                "\t\t\t\"abc@sample.com\": [\"{{technology}}\"],\n" +
                "\t\t\t\"xyz@sample.com\": [\"{{technology}}\"]\n" +
                "\t\t},\n" +
                "\t\t\"stacks\": {\n" +
                "\t\t\t\"abc@sample.com\": {\n" +
                "\t\t\t\t\"{{apiKey}}\": [\"{{stackRoleUid1}}\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"xyz@sample.com\": {\n" +
                "\t\t\t\t\"technology\": [\"technology\"],\n" +
                "\t\t\t\t\"technology\": [\"technology\", \"technology\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        Request request = ORG.inviteUser(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/share", request.url().toString());


    }

    @Order(8)
    @Test
    void testRemoveUser() {
        String _body = "{\n" +
                "\t\"share\": {\n" +
                "\t\t\"users\": {\n" +
                "\t\t\t\"abc@sample.com\": [\"{{orgAdminRoleUid}}\"],\n" +
                "\t\t\t\"xyz@sample.com\": [\"{{orgMemberRoleUid}}\"]\n" +
                "\t\t},\n" +
                "\t\t\"stacks\": {\n" +
                "\t\t\t\"abc@sample.com\": {\n" +
                "\t\t\t\t\"{{apiKey}}\": [\"{{stackRoleUid1}}\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"xyz@sample.com\": {\n" +
                "\t\t\t\t\"blta1ed1f11111c1eb1\": [\"blt111d1b110111e1f1\"],\n" +
                "\t\t\t\t\"bltf0c00caa0f0000f0\": [\"bltcea22222d2222222\", \"blt333f33cb3e33ffde\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(_body);
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.inviteUser(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/share", request.url().toString());

    }

    @Order(9)
    @Test
    void testResendInvitation() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("include_plan", true);
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.resendInvitation(ORG_ID).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(6, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/share/" + ORG_ID + "/resend_invitation", request.url().toString());
    }


    @Order(10)
    @Test
    void testTransferOwnership() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("include_plan", true);
        String strBody = "{\n" +
                "\t\"transfer_to\": \"shaileshmishra@gmail.com\"\n" +
                "}";
        JSONObject body = theJSONBody(strBody);
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.transferOwnership(body).request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/transfer-ownership", request.url().toString());
    }

    @Order(11)
    @Test
    void testStacks() {
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.stacks().request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/stacks?include_plan=true", request.url().toString());
    }

    @Test
    @Order(12)
    void testLogDetails() {
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.logsDetails().request();

        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/logs", request.url().toString());
    }

    @Order(13)
    @Test
    void testLogsItem() {

        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.logItem("fake@loguid").request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(5, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/logs/fake@loguid", request.url().toString());

    }

    @Order(14)
    @Test
    void testAllInvitation() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("include_plan", true);
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Request request = ORG.allInvitations().request();
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("organizations", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + ORG_ID + "/share?include_plan=true", request.url().toString());
    }

    @Order(15)
    @Test
    void testAllInvitationWithQuery() throws IOException {
        ORG = TestClient.getClient().organization(ORG_ID)
                .addHeader("authtoken", TestClient.AUTHTOKEN)
                .addHeader("api_key", TestClient.API_KEY)
                .addParam("include_plan", true);
        Response<ResponseBody> response = ORG.allInvitations().execute();
        Assertions.assertFalse(response.isSuccessful());
    }

}
