package com.contentstack.cms.organization;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.user.User;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import static com.contentstack.cms.user.UserUnitTests.isValid;
import static org.junit.jupiter.api.Assertions.*;

public class OrgUnitTests {

    private static Organization organization;
    private static String authtoken;

    @BeforeAll
    public static void setUp() {
        authtoken = Dotenv.load().get("auth_token");
        assert authtoken != null;
        organization = new Contentstack.Builder()
                .setAuthtoken(authtoken)
                .build().organization();
    }
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException {
        Constructor<User> constructor = User.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
    @Test
    void testConstructorIsPrivateInOrganization() throws NoSuchMethodException {
        Constructor<Organization> constructor = Organization.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void testConstructorWithRetrofitClientAndAuthtoken() {
        // create retrofit client
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.contentstack.io/v3/").build();
        Organization organization = new Organization(retrofit);
        assertEquals("https://api.contentstack.io/v3/", retrofit.baseUrl().toString());
    }

    @Test
    void testOrganizationGetAllRelativeUrl() {
        Request requestInfo = organization.getAll().request();
        Assertions.assertEquals("/v3/organizations", requestInfo.url().encodedPath());
    }


    //////////////////////////////
    @Test
    void testGetAllMethod() {
        Request requestInfo = organization.getAll().request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    void testGetAllBaseUrl() {
        Request requestInfo = organization.getAll().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations",
                requestInfo.url().toString());
    }

    @Test
    void testGetAllEncodedPath() {
        Request requestInfo = organization.getAll().request();
        Assertions.assertEquals("/v3/organizations",
                requestInfo.url().encodedPath());
    }

    @Test
    void testGetAllCompleteUrl() {
        HashMap<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("limit", 5);
        mapQuery.put("skip", 5);
        mapQuery.put("asc", "created_at");
        mapQuery.put("desc", "update_at");
        mapQuery.put("include_count", "true");
        mapQuery.put("typeahead", "contentstack");
        Request requestInfo = organization.getAll(mapQuery).request();
        Assertions.assertEquals("asc=created_at&limit=5&skip=5&include_count=true&typeahead=contentstack&desc=update_at",
                requestInfo.url().query());
    }

    @Test
    void testGetAllHeaders() {
        Request requestInfo = organization.getAll().request();
        Assertions.assertEquals("",
                requestInfo.url().queryParameterNames());
    }

    @Test
    void testGetAllRequestParam() {
        HashMap<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("limit", 5);
        mapQuery.put("skip", 5);
        Request requestInfo = organization.getAll(mapQuery).request();
        Assertions.assertEquals("limit=5&skip=5",
                requestInfo.url().query());
    }
    /////////////////////////////////


    //////////////////////////////
    @Test
    void testGetSingleMethod() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getSingleOrganization(organizationUid).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    void testGetSingleBaseUrl() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getSingleOrganization(organizationUid).request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    void testGetSingleEncodedPath() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getSingleOrganization(organizationUid).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid,
                requestInfo.url().encodedPath());
    }

    @Test
    void testGetSingleCompleteUrl() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> mapQuery = new HashMap<>();
        mapQuery.put("include_plan", true);
        Request requestInfo = organization.getSingleOrganization(organizationUid, mapQuery).request();
        Assertions.assertEquals("include_plan=true",
                requestInfo.url().query());
    }

    @Test
    void testGetSingleHeaders() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getSingleOrganization(organizationUid).request();
        Assertions.assertEquals("",
                requestInfo.headers());
    }

    /////////////////////////////////


    //////////////////////////////
    @Test
    void testGetRoleMethod() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getRoles(organizationUid).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    void testGetRoleBaseUrl() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getRoles(organizationUid).request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    void testGetRoleEncodedPath() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getRoles(organizationUid).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/roles",
                requestInfo.url().encodedPath());
    }

    @Test
    void testGetRoleCompleteUrl() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getRoles(organizationUid).request();
        Assertions.assertEquals("/v3/organizations/" + organizationUid + "/roles",
                requestInfo.url().encodedPath());
    }

    @Test
    void testGetRoleHeaders() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getRoles(organizationUid).request();
        Assertions.assertEquals("",
                requestInfo.headers("authtoken"));
    }

    @Test
    void testGetRoleRequestBody() {
        //limit={limit_value}&skip={skip_value}&asc={field_uid}&desc={field_uid}&include_count={boolean_value}&include_stack_roles={boolean_value}
        String organizationUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", 4);
        queryParams.put("skip", 4);
        Request requestInfo = organization.getRoles(organizationUid, queryParams).request();
        Assertions.assertEquals("limit=4&skip=4",
                requestInfo.url().encodedQuery());
    }

    @Test
    void testGetRoleEncodedPathWithOptParams() {
        //limit={limit_value}&skip={skip_value}&asc={field_uid}&desc={field_uid}&include_count={boolean_value}&include_stack_roles={boolean_value}
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", 4);
        queryParams.put("skip", 4);
        Request requestInfo = organization.getRoles(orgUid, queryParams).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/roles",
                requestInfo.url().encodedPath());
    }

    @Test
    void testGetRoleRequestParam() {
        String organizationUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", 4);
        queryParams.put("skip", 4);
        queryParams.put("asc", "uid128038438984");
        queryParams.put("desc", "uid128038438984");
        queryParams.put("include_count", true);
        queryParams.put("include_stack_roles", true);
        Request requestInfo = organization.getRoles(organizationUid, queryParams).request();
        Assertions.assertEquals("asc=uid128038438984&include_stack_roles=true&limit=4&skip=4&include_count=true&desc=uid128038438984",
                requestInfo.url().encodedQuery());
    }


    //////////////////////////////
    @Test
    void testInviteUserMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.inviteUser(orgUid).request();
        Assertions.assertEquals("POST", requestInfo.method());
    }

    @Test
    void testInviteUserBaseUrl() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.inviteUser(orgUid).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + orgUid + "/share", requestInfo.url().toString());
    }

    @Test
    void testInviteUserHeaders() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.inviteUser(orgUid).request();
        Assertions.assertEquals("", requestInfo.headers());
    }

    @Test
    void testInviteUserRequestBody() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.inviteUser(orgUid).request();
        Assertions.assertNull(requestInfo.url().encodedQuery());
    }

    @Test
    void testInviteUserRequestParam() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.inviteUser(orgUid).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share",
                requestInfo.url().encodedPath());
    }
    /////////////////////////////////


    //////////////////////////////
    @Test
    void testRemoveUsersMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.removeUsers(orgUid).request();
        Assertions.assertEquals("DELETE",
                requestInfo.method());
    }

    @Test
    void testRemoveUsersBaseUrl() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.removeUsers(orgUid).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share",
                requestInfo.url().encodedPath());
    }


    @Test
    void testRemoveUsersCompleteUrl() {
        String host = "https://api.contentstack.io";
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.removeUsers(orgUid).request();
        Assertions.assertEquals(host + "/v3/organizations/" + orgUid + "/share",
                requestInfo.url().toString());
    }

    @Test
    void testRemoveUsersHeaders() {
    }

    @Test
    void testRemoveUsersRequestBody() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.removeUsers(orgUid).request();
        Assertions.assertEquals(null,
                requestInfo.url().query());
    }

    /////////////////////////////////


    //////////////////////////////
    @Test
    void testResendInvitationMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    void testResendInvitationBaseUrl() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    void testResendInvitationEncodedPath() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share/invitation_uid/resend_invitation",
                requestInfo.url().encodedPath());
    }


    @Test
    void testResendInvitationHeaders() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertEquals("",
                requestInfo.headers());
    }

    @Test
    void testResendInvitationRequestBody() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertNull(
                requestInfo.url().query());
    }

    @Test
    void testResendInvitationsRequestParam() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.resendInvitation(orgUid, "invitation_uid").request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }
    /////////////////////////////////


    //////////////////////////////
    @Test
    void testGetAllInvitationMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", 4);
        queryParams.put("skip", 4);
        Request requestInfo = organization.getAllInvitations(orgUid, queryParams).request();
        Assertions.assertEquals("limit=4&skip=4",
                requestInfo.url().encodedQuery());
    }

    @Test
    void testGetAllInvitationBaseUrl() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getAllInvitations(orgUid).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    void testGetAllInvitationEncodedPath() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", 4);
        queryParams.put("skip", 4);
        Request requestInfo = organization.getAllInvitations(orgUid, queryParams).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/share",
                requestInfo.url().encodedPath());
    }

    @Test
    void testGetAllInvitationHeaders() {
    }


    @Test
    void testGetAllInvitationsRequestParam() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", 4);
        queryParams.put("skip", 4);
        Request requestInfo = organization.getAllInvitations(orgUid, queryParams).request();
        Assertions.assertEquals("limit=4&skip=4",
                requestInfo.url().encodedQuery());
    }
    /////////////////////////////////


    //////////////////////////////
    @Test
    void testTransferOwnershipMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        String transferToEmail = "ishaileshmishra@gmail.com";
        Request requestInfo = organization.transferOwnership(orgUid, transferToEmail).request();
        Assertions.assertEquals("POST",
                requestInfo.method());
    }

    @Test
    void testTransferOwnershipEncodedPath() {
        String orgUid = Dotenv.load().get("organizations_uid");
        String transferToEmail = "ishaileshmishra@gmail.com";
        Request requestInfo = organization.transferOwnership(orgUid, transferToEmail).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/transfer-ownership",
                requestInfo.url().encodedPath());
    }


    @Test
    void testTransferOwnershipHeaders() {
    }

    @Test
    void testTransferOwnershipRequestBody() {
        String orgUid = Dotenv.load().get("organizations_uid");
        String transferToEmail = "ishaileshmishra@gmail.com";
        Request requestInfo = organization.transferOwnership(orgUid, transferToEmail).request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }


    /////////////////////////////////

    //////////////////////////////
    @Test
    void testGetStacksMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("limit", 4);
        hashMap.put("skip", 4);
        Request requestInfo = organization.getStacks(orgUid, hashMap).request();
        Assertions.assertEquals("GET",
                requestInfo.method());
    }

    @Test
    void testGetStacksBaseUrl() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("limit", 4);
        hashMap.put("skip", 4);
        Request requestInfo = organization.getStacks(orgUid, hashMap).request();
        Assertions.assertEquals("api.contentstack.io",
                requestInfo.url().host());
    }

    @Test
    void testGetStacksEncodedPath() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("limit", 4);
        hashMap.put("skip", 4);
        hashMap.put("asc", false);
        hashMap.put("desc", true);
        hashMap.put("include_count", true);
        hashMap.put("typehead", "contentstack");
        Request requestInfo = organization.getStacks(orgUid, hashMap).request();
        Assertions.assertEquals("/v3/organizations/" + orgUid + "/stacks",
                requestInfo.url().encodedPath());
    }


    @Test
    void testGetStacksHeaders() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("limit", 4);
        hashMap.put("skip", 4);
        hashMap.put("asc", false);
        hashMap.put("desc", true);
        Request requestInfo = organization.getStacks(orgUid, hashMap).request();
        Assertions.assertEquals("",
                requestInfo.headers());
    }

    @Test
    void testGetStacksRequestBody() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("limit", 4);
        hashMap.put("skip", 4);
        hashMap.put("asc", false);
        hashMap.put("desc", true);
        hashMap.put("include_count", true);
        hashMap.put("typehead", "contentstack");
        Request requestInfo = organization.getStacks(orgUid, hashMap).request();
        Assertions.assertEquals("asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true",
                requestInfo.url().query());
    }

    @Test
    void testGetStacksEncodedQueryParam() {
        String orgUid = Dotenv.load().get("organizations_uid");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("limit", 4);
        hashMap.put("skip", 4);
        hashMap.put("asc", false);
        hashMap.put("desc", true);
        hashMap.put("include_count", true);
        hashMap.put("typehead", "contentstack");
        Request requestInfo = organization.getStacks(orgUid, hashMap).request();
        Assertions.assertEquals("asc=false&typehead=contentstack&limit=4&skip=4&include_count=true&desc=true",
                requestInfo.url().encodedQuery());
    }
    /////////////////////////////////

    //////////////////////////////
    @Test
    void testGetLogsMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsDetails(orgUid).request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    void testGetLogsBaseUrl() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsDetails(orgUid).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    void testGetLogsEncodedPath() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsDetails(orgUid).request();
        Assertions.assertEquals("/v3/organizations/blt4444c44ea4ddf444/logs", requestInfo.url().encodedPath());
    }

    @Test
    void testGetLogsHeaders() {
    }

    @Test
    void testGetLogsRequestBody() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsDetails(orgUid).request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    void testGetLogsRequestParam() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsDetails(orgUid).request();
        Assertions.assertNull(
                requestInfo.url().query());
    }
    /////////////////////////////////


    //////////////////////////////
    @Test
    void testGetLogItemsMethod() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsItem(orgUid, "idlogUid12345").request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    @Test
    void testGetLogItemsBaseUrl() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsDetails(orgUid).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    @Test
    void testGetLogItemsEncodedPath() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsItem(orgUid, "idlogUid12345").request();
        Assertions.assertEquals("/v3/organizations/blt4444c44ea4ddf444/logs/idlogUid12345", requestInfo.url().encodedPath());
    }

    @Test
    void testGetLogItemsHeaders() {
    }

    @Test
    void testGetLogItemsRequestBody() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsItem(orgUid, "idLogUid12345").request();
        Assertions.assertNull(
                requestInfo.url().encodedQuery());
    }

    @Test
    void testGetLogItemsRequestParam() {
        String orgUid = Dotenv.load().get("organizations_uid");
        Request requestInfo = organization.getLogsItem(orgUid, "idLogUid12345").request();
        Assertions.assertNull(
                requestInfo.url().query());
    }
    /////////////////////////////////

    @Test
    void testGetAllWithQueryParamLimit() {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("limit", 3);
        Request requestInfo = organization.getAll(queryMap).request();
        Assertions.assertEquals("GET", requestInfo.method());
        assertTrue(isValid(requestInfo.url().toString()));
        assertEquals("3", requestInfo.url().queryParameter("limit"));
    }


}
