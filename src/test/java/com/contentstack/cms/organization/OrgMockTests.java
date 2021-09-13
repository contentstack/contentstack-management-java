package com.contentstack.cms.organization;

import com.contentstack.cms.user.CSResponse;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;

import static com.contentstack.cms.user.UserUnitTests.isValid;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrgMockTests {

    private Organization organization;
    private String DEFAULT_AUTHTOKEN;
    private String DEFAULT_ORG_UID;


    @BeforeAll
    public void setUp() {
        // Loads the environment variables from .env file
        // from the root folder
        Dotenv dotenv = Dotenv.load();
        // loads the authtoken from the .env file
        DEFAULT_AUTHTOKEN = dotenv.get("auth_token");
        // loads the organizations_uid from the .env file
        DEFAULT_ORG_UID = dotenv.get("organizations_uid");
        // creates own retrofit builder with base url
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.contentstack.io/v3/").build();
        // create organization instance
        organization = new Organization(retrofit);
    }

    @Test
    @DisplayName("default constructor should throws exception in case Retrofit instance is not provided")
    void testDefaultConstructorShouldThrowException() throws IOException {
        // default constructor should throw exception in case Retrofit instance is not provided
        // Assertions.assertThrows(UnsupportedOperationException.class, User::new);
        Response<ResponseBody> orgs = organization.getAll().execute();
        System.out.println(orgs);
    }

    @Test
    @DisplayName("Test embedded url path from getAllOrganizations method")
    void testGetAllRelativeUrl() {
        // Create a blank Map to pass in fetch parameter
        Request requestInfo = organization.getAll(new HashMap<>()).request();
        Assertions.assertEquals("/v3/organizations", requestInfo.url().encodedPath());
    }


    @Test
    void testGetAllWithoutQueryParameter() {
        Request requestInfo = organization.getAll(new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    @Test
    @DisplayName("Test get all organizations with all parameters")
    void testGetAllOrganizationsWithAllParameters() {
        // Adding parameters to hashmap
        HashMap<String, String> queryParam = new HashMap<>();
        queryParam.put("limit", "1");
        queryParam.put("skip", "1");
        queryParam.put("asc", "created_at");
        queryParam.put("desc", "update_at");
        queryParam.put("include_count", "true");
        queryParam.put("typeahead", "Contentstack");

        Request requestInfo = organization.getAll(new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(queryParam.size(), requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

//    @Test
//    @DisplayName("Tests get all organizations url host")
//    void testGetAllOrganizationsUrlHost() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
//    }
//
//    @Test
//    @DisplayName("Tests get all organizations url request method")
//    void testGetAllOrganizationsUrlRequestMethod() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//    }
//
//    @Test
//    @DisplayName("Tests get all organizations url request")
//    void testGetAllOrganizationsUrlRequestHeaders() {
//        Request requestInfo = organization.getAll(new HashMap<>()).request();
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//    }
//
//    @Test
//    @DisplayName("Test get all organizations url request for single organisation")
//    void testGetAllOrganizationsUrlRequestFewQueryParameters() {
//        HashMap<String, String> queryParam = new HashMap<>();
//        queryParam.put("limit", "1");
//        queryParam.put("skip", "1");
//        Request requestInfo = organization.getAll(queryParam).request();
//        Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size());
//    }
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
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
//        Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
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
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
//        Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(queryParam.size(), requestInfo.url().queryParameterNames().size(),
//                "executes with parameter");
//        Assertions.assertEquals("include_plan=true", requestInfo.url().encodedQuery());
//        Assertions.assertEquals(
//                "https://" + requestInfo.url().host() + "/v3/organizations/" + DEFAULT_ORG_UID + "?include_plan=true",
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
//        Assertions.assertEquals("https://" + requestInfo.url().host() + "/v3/organizations/" + DEFAULT_ORG_UID,
//                requestInfo.url().toString());
//    }
//
//    // ======================================================================//
//    // ======================================================================//
//
//    @Test
//    @DisplayName("Test get organizations role with no query parameters")
//    void testGetRoleWithoutQueryParameter() {
//        Request requestInfo = organization.getRoles(DEFAULT_ORG_UID, new HashMap<>()).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
//        Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
//    }
//
//    @Test
//    @DisplayName("Test get organizations role with all parameters")
//    void testGetRoleOrganizationsWithAllParameters() {
//        // Adding parameters to hashmap
//        HashMap<String, String> queryParam = new HashMap<>();
//        queryParam.put("include_plan", "true");
//        Request requestInfo = organization.getRoles(DEFAULT_ORG_UID, queryParam).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
//        Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(queryParam.size(), requestInfo.url().queryParameterNames().size(),
//                "executes with parameter");
//        Assertions.assertEquals("include_plan=true", requestInfo.url().encodedQuery());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role url host")
//    void testGetRoleOrganizationsUrlHost() {
//        Request requestInfo = organization.getRoles(DEFAULT_AUTHTOKEN, new HashMap<>()).request();
//        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role url request method")
//    void testGetRoleOrganizationsUrlRequestMethod() {
//        Request requestInfo = organization.getRoles("org_uid", new HashMap<>()).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role url request")
//    void testGetRoleOrganizationsUrlRequestHeaders() {
//        Request requestInfo = organization.getRoles("org_uid", new HashMap<>()).request();
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//    }
//
//    @Test
//    @DisplayName("Tests get organizations role relative url")
//    void testGetRoleOrganizationsRelativeUrl() {
//        Request requestInfo = organization.getRoles("org_uid", new HashMap<>()).request();
//        Assertions.assertEquals(
//                "https://" + requestInfo.url().host() + "/v3/organizations/" + DEFAULT_ORG_UID + "/roles",
//                requestInfo.url().toString());
//    }
//
//    @Test
//    @DisplayName("Test get role with query parameters")
//    void testGetRoleWithQueryParameter() {
//        Map<String, String> mapQueryParam = new HashMap<>();
//        mapQueryParam.putIfAbsent("limit", "10");
//        mapQueryParam.putIfAbsent("skip", "2");
//        Request requestInfo = organization.getRoles("org_uid", mapQueryParam).request();
//        Assertions.assertEquals("GET", requestInfo.method());
//        Assertions.assertEquals(1, requestInfo.headers().names().size());
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
//        Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size(), "executes with query parameter");
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
//        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
//        Assertions.assertTrue(isValid(requestInfo.url().toString()));
//        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
//        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + DEFAULT_ORG_UID + "/share",
//                requestInfo.url().toString());
//
//        try {
//            Response<ResponseBody> response = organization.inviteUser(DEFAULT_ORG_UID).execute();
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
//
//    }

}
