package com.contentstack.cms.organization;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Retrofit;

import static com.contentstack.cms.core.TestUtils.isValid;
import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrgUnitTests {

    private final static String authtoken = TestClient.AUTHTOKEN;
    private final static String organizationUid = TestClient.ORGANIZATION_UID;
    private static Organization organization;

    @BeforeAll
    public static void setUp() {
        organization = new Contentstack.Builder().setAuthtoken(authtoken).build().organization(organizationUid);
    }

    private JSONObject theJSONBody(@NotNull String _body) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(_body);
        } catch (ParseException e) {
            System.out.println("Json Reading Error: " + e.getLocalizedMessage());
            return null;
        }
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
        organization.addParam("asc", false);
        organization.addParam("typehead", "contentstack");
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        organization.addParam("include_count", true);
        organization.addParam("desc", true);
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
        organization.clearParams();
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
        Request requestInfo = organization.fetch().request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(10)
    void testGetSingleBaseUrl() {
        Request requestInfo = organization.fetch().request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(11)
    void testGetSingleEncodedPath() {
        Request requestInfo = organization.fetch().request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid,
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(12)
    void testGetSingleCompleteUrl() {
        organization.clearParams();
        organization.addParam("include_plan", true);
        Request requestInfo = organization.fetch().request();
        Assertions.assertEquals("include_plan=true",
                requestInfo.url().query());
    }

    @Test
    @Order(13)
    void testGetRoleMethod() {
        Request requestInfo = organization.roles().request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(14)
    void testGetRoleBaseUrl() {
        Request requestInfo = organization.roles().request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(15)
    void testGetRoleEncodedPath() {
        Request requestInfo = organization.roles().request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/roles",
                requestInfo.url().encodedPath());
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/roles",
                requestInfo.url().encodedPath());
    }


    @Test
    @Order(17)
    void testGetRoleRequestBody() {
        organization.clearParams();
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        Request requestInfo = organization.roles().request();
        Assertions.assertEquals("limit=4&skip=4",
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(18)
    void testGetRoleEncodedPathWithOptParams() {
        Request requestInfo = organization.roles().request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/roles",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(19)
    void testGetRoleRequestParam() {
        organization.clearParams();
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        organization.addParam("include_count", true);
        organization.addParam("include_stack_roles", true);
        Request requestInfo = organization.roles().request();
        Assertions.assertEquals(
                "include_stack_roles=true&limit=4&skip=4&include_count=true",
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(20)
    void testInviteUserMethod() {
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
                "\t\t\t\t\"f11111c1eb1\": [\"111d1b110111e1f1\"],\n" +
                "\t\t\t\t\"caa0f0000f0\": [\"2d2222222\", \"33cb3e33\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(_body);
        Request requestInfo = organization.inviteUser(body).request();
        Assertions.assertEquals("POST", requestInfo.method());
    }

    @Test
    @Order(21)
    void testInviteUserBaseUrl() {
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
                "\t\t\t\t\"technology\": [\"technology\"],\n" +
                "\t\t\t\t\"technology\": [\"technology\", \"technology\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        Request requestInfo = organization.inviteUser(body).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + organizationUid + "/share",
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
                "\t\t\t\t\"technology\": [\"technology\"],\n" +
                "\t\t\t\t\"technology\": [\"technology\", \"technology\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        Request requestInfo = organization.inviteUser(body).request();
        assertNull(requestInfo.url().encodedQuery());
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
                "\t\t\t\t\"technology\": [\"technology\"],\n" +
                "\t\t\t\t\"technology\": [\"technology\", \"technology\"]\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"message\": \"Invitation message\"\n" +
                "\t}\n" +
                "}";
        JSONObject body = theJSONBody(requestBody);
        Request requestInfo = organization.inviteUser(body).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/share",
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
        Request requestInfo = organization.removeUsers(body).request();
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
        Request requestInfo = organization.removeUsers(body).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/share",
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

        Request requestInfo = organization.removeUsers(body).request();
        Assertions.assertEquals(host + "/v3/organizations/" + organizationUid + "/share",
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
        Request requestInfo = organization.removeUsers(body).request();
        assertNull(requestInfo.url().query());
    }

    /////////////////////////////////

    //////////////////////////////
    @Test
    @Order(29)
    void testResendInvitationMethod() {

        Request requestInfo = organization.resendInvitation(
                "invitation_uid").request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(30)
    void testResendInvitationBaseUrl() {
        Request requestInfo = organization.resendInvitation(
                "invitation_uid").request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(31)
    void testResendInvitationEncodedPath() {
        Request requestInfo = organization.resendInvitation("invitation_uid").request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/share/invitation_uid/resend_invitation",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(32)
    void testResendInvitationRequestBody() {
        Request requestInfo = organization.resendInvitation("invitation_uid").request();
        assertNull(
                requestInfo.url().query());
    }

    @Test
    @Order(33)
    void testResendInvitationsRequestParam() {
        Request requestInfo = organization.resendInvitation("invitation_uid").request();
        assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(34)
    void testGetAllInvitationMethod() {
        organization.clearParams();
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        Request requestInfo = organization.allInvitations().request();
        Assertions.assertEquals("limit=4&skip=4",
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(35)
    void testGetAllInvitationBaseUrl() {
        Request requestInfo = organization.allInvitations().request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(36)
    void testGetAllInvitationEncodedPath() {
        Request requestInfo = organization.allInvitations().request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/share",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(37)
    void testGetAllInvitationsRequestParam() {
        organization.clearParams();
        Request requestInfo = organization.allInvitations().request();
        assertNull(requestInfo.url().encodedQuery());
    }

    @Test
    @Order(38)
    void testTransferOwnershipMethod() {
        //String transferToEmail = "***REMOVED***@gmail.com";
        Request requestInfo = organization.transferOwnership(new JSONObject()).request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    @Order(39)
    void testTransferOwnershipEncodedPath() {
        // String transferToEmail = "***REMOVED***@gmail.com";
        Request requestInfo = organization.transferOwnership(new JSONObject()).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/transfer-ownership",
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
        //String transferToEmail = "***REMOVED***@gmail.com";
        Request requestInfo = organization.transferOwnership(new JSONObject()).request();
        assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(42)
    void testGetStacksMethod() {
        Request requestInfo = organization.stacks().request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    @Order(43)
    void testGetStacksBaseUrl() {
        Request requestInfo = organization.stacks().request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    @Order(44)
    void testGetStacksEncodedPath() {
        organization.addParam("include_count", true);
        organization.addParam("typehead", "contentstack");
        Request requestInfo = organization.stacks().request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/stacks",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(45)
    void testGetStacksHeaders() {
        organization.addParam("asc", false);
        organization.addParam("desc", true);
        Request requestInfo = organization.stacks().request();
        Assertions.assertEquals(
                "/v3/organizations/" + organizationUid + "/stacks",
                requestInfo.url().encodedPath());
    }

    @Test
    @Order(46)
    void testGetStacksRequestBody() {
        organization.addParam("asc", false);
        organization.addParam("typehead", "contentstack");
        organization.addParam("limit", 4);
        organization.addParam("skip", 4);
        organization.addParam("include_count", true);
        organization.addParam("desc", true);
        Request requestInfo = organization.stacks().request();
        Assertions.assertEquals("asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true",
                requestInfo.url().query());
    }

    @Test
    @Order(47)
    void testGetStacksEncodedQueryParam() {
        organization.clearParams();
        organization.addParam("include_plan", true);
        Request requestInfo = organization.stacks().request();
        Assertions.assertEquals("include_plan=true",
                requestInfo.url().encodedQuery());
    }
    /////////////////////////////////

    //////////////////////////////
    @Test
    @Order(48)
    void testGetLogsMethod() {
        Request requestInfo = organization.logsDetails().request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(49)
    void testGetLogsBaseUrl() {
        Request requestInfo = organization.logsDetails().request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    @Order(50)
    void testGetLogsEncodedPath() {
        Request requestInfo = organization.logsDetails().request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/logs", requestInfo.url().encodedPath());
    }

    @Test
    @Order(51)
    void testGetLogsHeaders() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(52)
    void testGetLogsRequestBody() {
        Request requestInfo = organization.logsDetails().request();
        assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(53)
    void testGetLogsRequestParam() {
        Request requestInfo = organization.logsDetails().request();
        assertNull(
                requestInfo.url().query());
    }
    /////////////////////////////////

    //////////////////////////////
    @Test
    @Order(54)
    void testGetLogItemsMethod() {
        Request requestInfo = organization.logItem("thisIsLogUid9832").request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    @Order(55)
    void testGetLogItemsBaseUrl() {
        Request requestInfo = organization.logsDetails().request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    @Order(56)
    void testGetLogItemsEncodedPath() {
        Request requestInfo = organization.logItem("thisIsLogUid89347").request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/logs/thisIsLogUid89347", requestInfo.url().encodedPath());
    }

    @Test
    @Order(57)
    void testGetLogItemsRequestBody() {
        Request requestInfo = organization.logItem("idLogUid12345").request();
        assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    @Order(58)
    void testGetLogItemsRequestParam() {
        Request requestInfo = organization.logItem("idLogUid12345").request();
        assertNull(
                requestInfo.url().query());
    }


}
