package com.contentstack.cms.organization;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import retrofit2.Response;

import java.io.IOException;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrgApiTests {

    private Organization organization;
    String emailId = Dotenv.load().get("username");
    String password = Dotenv.load().get("password");
    String authtoken = Dotenv.load().get("authtoken");

    @BeforeAll
    public void setUp() throws IOException {
        Contentstack contentstack = new Contentstack.Builder().build();
        contentstack.login(emailId, password);
        organization = contentstack.organization();
    }

    @Test
    @DisplayName("organization fetch all")
    void testOrganizationFetchAll() throws IOException {
        Response<ResponseBody> response = organization.getAll().execute();
    }

//    @Test
//    void testGetAllOrganizastionsUrlRequestHeaders() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//    }
//
//    @Test
//    @DisplayName("Test get all organizations url request for single
//            organisation")
//            void testGetAllOrganizationsUrlRequestFewQueryParameters(){
//            HashMap<String, String>queryParam = new HashMap<>();
//            queryParam.put("limit", "1");
//     queryParam.put("skip","1");
//    Request requestInfo = organization.getAll(queryParam).request();
//     Assertions.assertEquals(2,requestInfo.url().
//
//    queryParameterNames().
//
//    size());
//}
//
//    // ======================================================================//
//    // ======================================================================//
//
//    @Test
//    @DisplayName("Test get single organizations with no query parameters")
//    void testGetSingleWithoutQueryParameter() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
//                only");
//                Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
//                "executes without parameter");
//    }
//
//    @Test
//    @DisplayName("Test get single organizations with all parameters")
//    void testGetSingleOrganizationsWithAllParameters() {
//        // Adding parameters to hashmap
//        HashMap<String, String> queryParam = new HashMap<>();
//        queryParam.put("include_plan", "true");
//        Request requestInfo = organization.getAll(queryParam).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
//                only");
//                Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(queryParam.size(),
//                requestInfo.url().queryParameterNames().size(),
//                "executes with parameter");
//        Assertions.assertEquals("include_plan=true",
//                requestInfo.url().encodedQuery());
//        Assertions.assertEquals(
//                "https://" + requestInfo.url().host() + "/v3/organizations/" +
//                        DEFAULT_ORG_UID + "?include_plan=true",
//                requestInfo.url().toString());
//    }
//
//    @Test
//    @DisplayName("Tests get single organizations url host")
//    void testGetSingleOrganizationsUrlHost() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
//    }
//
//    @Test
//    @DisplayName("Tests get single organizations url request method")
//    void testGetSingleOrganizationsUrlRequestMethod() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//    }
//
//    @Test
//    @DisplayName("Tests get single organizations url request")
//    void testGetSingleOrganizationsUrlRequestHeaders() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//    }
//
//    @Test
//    @DisplayName("Tests get single organizations relative url")
//    void testGetSingleOrganizationsRelativeUrl() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals("https://" + requestInfo.url().host() +
//                        "/v3/organizations/" + DEFAULT_ORG_UID,
//                requestInfo.url().toString());
//    }
//
//    // ======================================================================//
//    // ======================================================================//
//
//    @Test
//    @DisplayName("Test get organizations role with no query parameters")
//    void testGetRoleWithoutQueryParameter() {
//        Request requestInfo = organization.getRoles(DEFAULT_ORG_UID, new
//                HashMap<>()).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
//                only");
//                Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
//                "executes without parameter");
//    }
//
//    @Test
//    @DisplayName("Test get organizations role with all parameters")
//    void testGetRoleOrganizationsWithAllParameters() {
//        // Adding parameters to hashmap
//        HashMap<String, String> queryParam = new HashMap<>();
//        queryParam.put("include_plan", "true");
//        Request requestInfo = organization.getRoles(DEFAULT_ORG_UID,
//                queryParam).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
//                only");
//                Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(queryParam.size(),
//                requestInfo.url().queryParameterNames().size(),
//                "executes with parameter");
//        Assertions.assertEquals("include_plan=true",
//                requestInfo.url().encodedQuery());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role url host")
//    void testGetRoleOrganizationsUrlHost() {
//        Request requestInfo = organization.getRoles(DEFAULT_AUTHTOKEN, new
//                HashMap<>()).request();
//        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role url request method")
//    void testGetRoleOrganizationsUrlRequestMethod() {
//        Request requestInfo = organization.getRoles("org_uid", new
//                HashMap<>()).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role url request")
//    void testGetRoleOrganizationsUrlRequestHeaders() {
//        Request requestInfo = organization.getRoles("org_uid", new
//                HashMap<>()).request();
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role relative url")
//    void testGetRoleOrganizationsRelativeUrl() {
//        Request requestInfo = organization.getRoles("org_uid", new
//                HashMap<>()).request();
//        Assertions.assertEquals(
//                "https://" + requestInfo.url().host() + "/v3/organizations/" +
//                        DEFAULT_ORG_UID + "/roles",
//                requestInfo.url().toString());
//    }
//
//    @Test
//    @DisplayName("Test get role with query parameters")
//    void testGetRoleWithQueryParameter() {
//        Map<String, String> mapQueryParam = new HashMap<>();
//        mapQueryParam.putIfAbsent("limit", "10");
//        mapQueryParam.putIfAbsent("skip", "2");
//        Request requestInfo = organization.getRoles("org_uid",
//                mapQueryParam).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
//                only");
//                Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size(),
//                "executes with query parameter");
//    }
//
//    // Class<T> type
//
//    // ======================================================================//
//    // ======================================================================//
//
//    @Test
//    @DisplayName("Test Invite user with no query parameters")
//    void testInviteUserWithNoParameter() {
//
//        Request requestInfo = organization.inviteUser(DEFAULT_ORG_UID).request();
//
//        Assertions.assertEquals("POST", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
//                only");
//                Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
//                "executes without parameter");
//        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" +
//                        DEFAULT_ORG_UID + "/share",
//                requestInfo.url().toString());
//
//        try {
//            Response<ResponseBody> response =
//                    organization.inviteUser(DEFAULT_ORG_UID).execute();
//            boolean isAvail = response.isSuccessful();
//            if (isAvail) {
//                System.out.println(" response: " + response.body());
//            } else {
//                System.out.println(" response: " + response.errorBody());
//            }
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//
//    }

}
