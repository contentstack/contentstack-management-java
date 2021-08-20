package com.contentstack.cms.organization;

import com.contentstack.cms.user.User;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Organization unit tests.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("UNIT")
@DisplayName("Up and running unit testcases for organization class")
public class StackUnitTests {

    private Organization organization;
    private String DEFAULT_AUTHTOKEN;
    private String DEFAULT_ORG_UID;

    /**
     * Is valid boolean.
     *
     * @param url the url
     * @return the boolean
     */
    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets all.
     */
    @BeforeAll
    public void setupAll() {
        Dotenv dotenv = Dotenv.load();
        DEFAULT_AUTHTOKEN = dotenv.get("auth_token");
        DEFAULT_ORG_UID = dotenv.get("organizations_uid");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.contentstack.io/v3/")
                .build();

        organization = new Organization(retrofit);
    }

    /**
     * Test user class with get user constructor.
     */
    @Test
    @DisplayName("organization class default constructor throws exception")
    void testUserClassWithGetUserConstructor() {
        Assertions.assertThrows(UnsupportedOperationException.class, User::new);
    }

    /**
     * Test organization class with get all method containing relative url.
     */
    @Test
    @DisplayName("Test embedded url path from getAllOrganizations method")
    void testOrganizationClassWithGetAllMethodContainingRelativeUrl() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("/v3/organizations", requestInfo.url().encodedPath());
    }

    // ======================================================================//
    // ======================================================================//

    /**
     * Test get all without query parameter.
     */
    @Test
    @Order(1)
    @DisplayName("Test get all organizations with no query parameters")
    void testGetAllWithoutQueryParameter() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    /**
     * Test get all organizations with all parameters.
     */
    @Test
    @Order(2)
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

        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(queryParam.size(), requestInfo.url().queryParameterNames().size(),
                "executes without parameter");
    }

    /**
     * Test get all organizations url host.
     */
    @Test
    @Order(3)
    @DisplayName("Tests get all organizations url host")
    void testGetAllOrganizationsUrlHost() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    /**
     * Test get all organizations url request method.
     */
    @Test
    @Order(4)
    @DisplayName("Tests get all organizations url request method")
    void testGetAllOrganizationsUrlRequestMethod() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    /**
     * Test get all organizations url request headers.
     */
    @Test
    @Order(5)
    @DisplayName("Tests get all organizations url request")
    void testGetAllOrganizationsUrlRequestHeaders() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals(1, requestInfo.headers().names().size());
    }

    /**
     * Test get all organizations url request few query parameters.
     */
    @Test
    @Order(6)
    @DisplayName("Test get all organizations url request for single organisation")
    void testGetAllOrganizationsUrlRequestFewQueryParameters() {
        HashMap<String, String> queryParam = new HashMap<>();
        queryParam.put("limit", "1");
        queryParam.put("skip", "1");
        Request requestInfo = organization.fetch(queryParam).request();
        Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size());
    }

    // ======================================================================//
    // ======================================================================//

    /**
     * Test get all without query parameter.
     */
    @Test
    @Order(7)
    @DisplayName("Test get single organizations with no query parameters")
    void testGetSingleWithoutQueryParameter() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /**
     * Test get all organizations with all parameters.
     */
    @Test
    @Order(8)
    @DisplayName("Test get single organizations with all parameters")
    void testGetSingleOrganizationsWithAllParameters() {
        // Adding parameters to hashmap
        HashMap<String, String> queryParam = new HashMap<>();
        queryParam.put("include_plan", "true");
        Request requestInfo = organization.fetch(queryParam).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(queryParam.size(), requestInfo.url().queryParameterNames().size(),
                "executes with parameter");
        Assertions.assertEquals("include_plan=true", requestInfo.url().encodedQuery());
        Assertions.assertEquals(
                "https://" + requestInfo.url().host() + "/v3/organizations/" + DEFAULT_ORG_UID + "?include_plan=true",
                requestInfo.url().toString());
    }

    /**
     * Test get all organizations url host.
     */
    @Test
    @Order(9)
    @DisplayName("Tests get single organizations url host")
    void testGetSingleOrganizationsUrlHost() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    /**
     * Test get all organizations url request method.
     */
    @Test
    @Order(10)
    @DisplayName("Tests get single organizations url request method")
    void testGetSingleOrganizationsUrlRequestMethod() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    /**
     * Test get all organizations url request headers.
     */
    @Test
    @Order(11)
    @DisplayName("Tests get single organizations url request")
    void testGetSingleOrganizationsUrlRequestHeaders() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals(1, requestInfo.headers().names().size());
    }

    @Test
    @Order(12)
    @DisplayName("Tests get single organizations relative url")
    void testGetSingleOrganizationsRelativeUrl() {
        Request requestInfo = organization.fetch(new HashMap<>()).request();
        Assertions.assertEquals("https://" + requestInfo.url().host() + "/v3/organizations/" + DEFAULT_ORG_UID,
                requestInfo.url().toString());
    }

    // ======================================================================//
    // ======================================================================//

    /**
     * Test get all without query parameter.
     */
    @Test
    @Order(13)
    @DisplayName("Test get organizations role with no query parameters")
    void testGetRoleWithoutQueryParameter() {
        Request requestInfo = organization.getRoles(DEFAULT_ORG_UID, new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
    }

    /**
     * Test get all organizations with all parameters.
     */
    @Test
    @Order(14)
    @DisplayName("Test get organizations role with all parameters")
    void testGetRoleOrganizationsWithAllParameters() {
        // Adding parameters to hashmap
        HashMap<String, String> queryParam = new HashMap<>();
        queryParam.put("include_plan", "true");
        Request requestInfo = organization.getRoles(DEFAULT_ORG_UID, queryParam).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(queryParam.size(), requestInfo.url().queryParameterNames().size(),
                "executes with parameter");
        Assertions.assertEquals("include_plan=true", requestInfo.url().encodedQuery());
    }

    /**
     * Test get all organizations url host.
     */
    @Test
    @Order(15)
    @DisplayName("Tests get organizations role url host")
    void testGetRoleOrganizationsUrlHost() {
        Request requestInfo = organization.getRoles(DEFAULT_AUTHTOKEN, new HashMap<>()).request();
        Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    }

    /**
     * Test get all organizations url request method.
     */
    @Test
    @Order(16)
    @DisplayName("Tests get organizations role url request method")
    void testGetRoleOrganizationsUrlRequestMethod() {
        Request requestInfo = organization.getRoles("org_uid", new HashMap<>()).request();
        Assertions.assertEquals("GET", requestInfo.method());
    }

    /**
     * Test get all organizations url request headers.
     */
    @Test
    @Order(17)
    @DisplayName("Tests get organizations role url request")
    void testGetRoleOrganizationsUrlRequestHeaders() {
        Request requestInfo = organization.getRoles("org_uid", new HashMap<>()).request();
        Assertions.assertEquals(1, requestInfo.headers().names().size());
    }

    @Test
    @Order(18)
    @DisplayName("Tests get organizations role relative url")
    void testGetRoleOrganizationsRelativeUrl() {
        Request requestInfo = organization.getRoles("org_uid", new HashMap<>()).request();
        Assertions.assertEquals(
                "https://" + requestInfo.url().host() + "/v3/organizations/" + DEFAULT_ORG_UID + "/roles",
                requestInfo.url().toString());
    }

    @Test
    @Order(19)
    @DisplayName("Test get role with query parameters")
    void testGetRoleWithQueryParameter() {
        Map<String, String> mapQueryParam = new HashMap<>();
        mapQueryParam.putIfAbsent("limit", "10");
        mapQueryParam.putIfAbsent("skip", "2");
        Request requestInfo = organization.getRoles("org_uid", mapQueryParam).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size(), "executes with query parameter");
    }

    // Class<T> type

    // ======================================================================//
    // ======================================================================//

    /**
     * Test get all without query parameter.
     */
    @Test
    @Order(20)
    @DisplayName("Test Invite user with no query parameters")
    void testInviteUserWithNoParameter() {

        Request requestInfo = organization.inviteUser(DEFAULT_ORG_UID).request();

        Assertions.assertEquals("POST", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https only");
        Assertions.assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(), "executes without parameter");
        Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" + DEFAULT_ORG_UID + "/share",
                requestInfo.url().toString());

        try {
            Response<ResponseBody> response = organization.inviteUser(DEFAULT_ORG_UID).execute();
            boolean isAvail = response.isSuccessful();
            if (isAvail) {
                System.out.println(" response: " + response.body());
            } else {
                System.out.println(" response: " + response.errorBody());
            }
        } catch (IOException e) {

            e.printStackTrace();
        }


    }

}
