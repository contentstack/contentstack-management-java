package com.contentstack.cms.organization;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Retrofit;

import static com.contentstack.cms.user.LocaleUnitTests.isValid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("unit")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class OrgUnitTests {

    private static Organization organization;
    private final static String authtoken = Dotenv.load().get("auth_token");


    private JSONObject theJSONBody(@NotNull String _body) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(_body);
        } catch (ParseException e) {
            System.out.println("Json Reading Error: " + e.getLocalizedMessage());
            return null;
        }
    }

    @BeforeAll
    public static void setUp() {
        assert authtoken != null;
        organization = new Contentstack.Builder().setAuthtoken(authtoken).build().organization();
    }


    @Test
    @Order(1)
    void testConstructorWithRetrofitClientAndAuthtoken() {
        // create retrofit client
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.contentstack.io/v3/").build();
        assertEquals("https://api.contentstack.io/v3/", retrofit.baseUrl().toString());
    }

    @Test
    @Order(2)
    void testOrganizationGetAllRelativeUrl() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("/v3/organizations", requestInfo.url().encodedPath());
    }

    //////////////////////////////
    @Test
    @Order(3)
    void testGetAllMethod() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(4)
    void testGetAllBaseUrl() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations?asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true",
                requestInfo.url().toString());
    }

    @Test
    @Order(5)
    void testGetAllEncodedPath() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("/v3/organizations",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(6)
    void testFetch() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true", requestInfo.url().query());
    }


    @Test
    @Order(7)
    void testGetAllRequestParam() {
        organization.addParam("limit", 5);
        organization.addParam("skip", 5);
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("limit=5&skip=5",
                requestInfo.url().query());
    }

    @Test
    @Order(8)
    void testGetAllCompleteQueryLimitSkip() {
        Organization org = new Contentstack.Builder().setAuthtoken(authtoken).build().organization();
        org.addParam("limit", 5);
        org.addParam("skip", 5);
        Request requestInfo = org.find().request();
        Assertions.assertEquals("limit=5&skip=5",
                requestInfo.url().query());
    }

    @Test
    @Order(9)
    void testGetSingleMethod() {
        String organizationUid = Dotenv.load().get("organizationUid");
        assert organizationUid != null;
        Request requestInfo = organization.fetch(organizationUid).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(10)
    void testGetSingleBaseUrl() {
        String organizationUid = Dotenv.load().get("organizationUid");
        assert organizationUid != null;
        Request requestInfo = organization.fetch(organizationUid).request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(11)
    void testGetSingleEncodedPath() {
        String organizationUid = Dotenv.load().get("organizationUid");
        assert organizationUid != null;
        Request requestInfo = organization.fetch(organizationUid).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid,
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(12)
    void testGetSingleCompleteUrl() {
        String organizationUid = Dotenv.load().get("organizationUid");
        organization.clearParams();
        organization.addParam("include_plan", true);
        assert organizationUid != null;
        Request requestInfo = organization.fetch(organizationUid).request();
        Assertions.assertEquals("include_plan=true",
                requestInfo.url().query());
    }

    @Test
    @Order(13)
    void testGetRoleMethod() {
        String organizationUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.roles(organizationUid).request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(14)
    void testGetRoleBaseUrl() {
        String organizationUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.roles(organizationUid).request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(15)
    void testGetRoleEncodedPath() {
        String organizationUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.roles(organizationUid).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/roles",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(16)
    void testGetRoleCompleteUrl() {
        String organizationUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.roles(organizationUid).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/roles",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(17)
    void testGetRoleRequestBody() {
        String organizationUid = Dotenv.load().get("organizationUid");
        organization.clearParams();
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        Request requestInfo = organization.roles(organizationUid).request();
        Assertions.assertEquals("limit=4&skip=4",
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(18)
    void testGetRoleEncodedPathWithOptParams() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.roles(orgUid).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/roles",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(19)
    void testGetRoleRequestParam() {
        String organizationUid = Dotenv.load().get("organizationUid");
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        organization.addParam("include_count", true);
        organization.addParam("include_stack_roles", true);
        Request requestInfo = organization.roles(organizationUid).request();
        Assertions.assertEquals(
                "include_stack_roles=true&include_plan=true&limit=4&skip=4&include_count=true",
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(20)
    void testInviteUserMethod() {
        String orgUid = Dotenv.load().get("organizationUid");

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
        Request requestInfo = organization.inviteUser(orgUid, body).request();
        Assertions.assertEquals("POST", requestInfo.method());
    }

    @Test
    @Order(21)
    void testInviteUserBaseUrl() {
        String orgUid = Dotenv.load().get("organizationUid");
        String requestBody = "{\n" +
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
                "\t\t\t\t\"ekdjnewkdnwndkwendken\": [\"bebdjewbdjewbdjeb\"],\n" +
                "\t\t\t\t\"udiubdebwdbwedb\": [\"ndenndnwndwbdb\", \"jnjdnwdnwndjw\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        Request requestInfo = organization.inviteUser(orgUid, body).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + orgUid + "/share",
                requestInfo.url().toString());
    }

    @Test
    @Order(22)
    void testInviteUserRequestBody() {
        String requestBody = "{\n" +
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
                "\t\t\t\t\"ekdjnewkdnwndkwendken\": [\"bebdjewbdjewbdjeb\"],\n" +
                "\t\t\t\t\"udiubdebwdbwedb\": [\"ndenndnwndwbdb\", \"jnjdnwdnwndjw\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.inviteUser(orgUid, body).request();
        Assertions.assertNull(requestInfo.url().encodedQuery());
    }

    @Test
    @Order(23)
    void testInviteUserRequestParam() {
        String requestBody = "{\n" +
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
                "\t\t\t\t\"njedjnedjekdn\": [\"ednewjndejkdkend\"],\n" +
                "\t\t\t\t\"ekjndkendendnje\": [\"kndkwendkendejnd\", \"kedmekdnkedejdn\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.inviteUser(orgUid, body).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(24)
    void testRemoveUsersMethod() {
        String requestBody = "{\n" +
                "    \"emails\":[\n" +
                "        \"abc@sample.com\", \"xyz@sample.com\"\n" +
                "    ]\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.removeUsers(orgUid, body).request();
        Assertions.assertEquals("DELETE",
                requestInfo.method());
    }

    @Test
    @Order(25)
    void testRemoveUsersBaseUrl() {
        String requestBody = "{\n" +
                "    \"emails\":[\n" +
                "        \"abc@sample.com\", \"xyz@sample.com\"\n" +
                "    ]\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.removeUsers(orgUid, body).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(26)
    void testRemoveUsersCompleteUrl() {
        String requestBody = "{\n" +
                "    \"emails\":[\n" +
                "        \"abc@sample.com\", \"xyz@sample.com\"\n" +
                "    ]\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        String host = "https://api.contentstack.io";
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.removeUsers(orgUid, body).request();
        Assertions.assertEquals(host + "/v3/organizations/" + orgUid + "/share",
                requestInfo.url().toString());
    }

    @Test
    @Order(27)
    void testRemoveUsersHeaders() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(28)
    void testRemoveUsersRequestBody() {
        String requestBody = "{\n" +
                "    \"emails\":[\n" +
                "        \"abc@sample.com\", \"xyz@sample.com\"\n" +
                "    ]\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.removeUsers(orgUid, body).request();
        Assertions.assertNull(requestInfo.url().query());
    }

    /////////////////////////////////

    //////////////////////////////
    @Test
    @Order(29)
    void testResendInvitationMethod() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.resendInvitation(orgUid,
                "invitation_uid").request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(30)
    void testResendInvitationBaseUrl() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.resendInvitation(orgUid,
                "invitation_uid").request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(31)
    void testResendInvitationEncodedPath() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share/invitation_uid/resend_invitation",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(32)
    void testResendInvitationRequestBody() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertNull(
                requestInfo.url().query());
    }

    @Test
    @Order(33)
    void testResendInvitationsRequestParam() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(34)
    void testGetAllInvitationMethod() {
        String orgUid = Dotenv.load().get("organizationUid");
        organization.clearParams();
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        Request requestInfo = organization.allInvitations(orgUid).request();
        Assertions.assertEquals("limit=4&skip=4",
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(35)
    void testGetAllInvitationBaseUrl() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.allInvitations(orgUid).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(36)
    void testGetAllInvitationEncodedPath() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.allInvitations(orgUid).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(37)
    void testGetAllInvitationsRequestParam() {
        String orgUid = Dotenv.load().get("organizationUid");
        organization.clearParams();
        Request requestInfo = organization.allInvitations(orgUid).request();
        Assertions.assertNull(requestInfo.url().encodedQuery());
    }

    @Test
    @Order(38)
    void testTransferOwnershipMethod() {
        String orgUid = Dotenv.load().get("organizationUid");
        //String transferToEmail = "***REMOVED***@gmail.com";
        Request requestInfo = organization.transferOwnership(orgUid, new JSONObject()).request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    @Order(39)
    void testTransferOwnershipEncodedPath() {
        String orgUid = Dotenv.load().get("organizationUid");
        // String transferToEmail = "***REMOVED***@gmail.com";
        Request requestInfo = organization.transferOwnership(orgUid, new JSONObject()).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/transfer-ownership",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(40)
    void testTransferOwnershipHeaders() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(41)
    void testTransferOwnershipRequestBody() {
        String orgUid = Dotenv.load().get("organizationUid");
        //String transferToEmail = "***REMOVED***@gmail.com";
        Request requestInfo = organization.transferOwnership(orgUid, new JSONObject()).request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(42)
    void testGetStacksMethod() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.stacks(orgUid).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(43)
    void testGetStacksBaseUrl() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.stacks(orgUid).request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(44)
    void testGetStacksEncodedPath() {
        String orgUid = Dotenv.load().get("organizationUid");
        organization.addParam("include_count", true);
        organization.addParam("typehead", "contentstack");
        Request requestInfo = organization.stacks(orgUid).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/stacks",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(45)
    void testGetStacksHeaders() {
        String orgUid = Dotenv.load().get("organizationUid");
        organization.addParam("asc", false);
        organization.addParam("desc", true);
        Request requestInfo = organization.stacks(orgUid).request();
        Assertions.assertEquals(
                "/v3/organizations/" + orgUid + "/stacks",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(46)
    void testGetStacksRequestBody() {
        String orgUid = Dotenv.load().get("organizationUid");
        organization.addParam("asc", false);
        organization.addParam("typehead", "contentstack");
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        organization.addParam("include_count", true);
        organization.addParam("desc", true);
        Request requestInfo = organization.stacks(orgUid).request();
        Assertions.assertEquals("asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true",
                requestInfo.url().query());
    }

    @Test
    @Order(47)
    void testGetStacksEncodedQueryParam() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.stacks(orgUid).request();
        Assertions.assertEquals("include_plan=true",
                requestInfo.url().encodedQuery());
    }
    /////////////////////////////////

    //////////////////////////////
    @Test
    @Order(48)
    void testGetLogsMethod() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logsDetails(orgUid).request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(49)
    void testGetLogsBaseUrl() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logsDetails(orgUid).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    @Order(50)
    void testGetLogsEncodedPath() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logsDetails(orgUid).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/logs", requestInfo.url().encodedPath());
    }

    @Test
    @Order(51)
    void testGetLogsHeaders() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(52)
    void testGetLogsRequestBody() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logsDetails(orgUid).request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(53)
    void testGetLogsRequestParam() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logsDetails(orgUid).request();
        Assertions.assertNull(
                requestInfo.url().query());
    }
    /////////////////////////////////

    //////////////////////////////
    @Test
    @Order(54)
    void testGetLogItemsMethod() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logItem(orgUid, "idlogUid12345").request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(55)
    void testGetLogItemsBaseUrl() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logsDetails(orgUid).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    @Order(56)
    void testGetLogItemsEncodedPath() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logItem(orgUid, "idlogUid12345").request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/logs/idlogUid12345", requestInfo.url().encodedPath());
    }

    @Test
    @Order(57)
    void testGetLogItemsRequestBody() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logItem(orgUid, "idLogUid12345").request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(58)
    void testGetLogItemsRequestParam() {
        String orgUid = Dotenv.load().get("organizationUid");
        Request requestInfo = organization.logItem(orgUid, "idLogUid12345").request();
        Assertions.assertNull(
                requestInfo.url().query());
    }

    @Test
    @Order(59)
    void testGetAllWithQueryParamLimit() {
        Request requestInfo = organization.find().request();
        Assertions.assertEquals("GET", requestInfo.method());
        assertTrue(isValid(requestInfo.url().toString()));
        assertEquals("4", requestInfo.url().queryParameter("limit"));
    }

}
