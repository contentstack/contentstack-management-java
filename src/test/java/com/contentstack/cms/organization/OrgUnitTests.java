package com.contentstack.cms.organization;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.user.User;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.*;
import retrofit2.Retrofit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import static com.contentstack.cms.user.UserUnitTests.isValid;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrgUnitTests {

    private Organization organization;
    private static String DEFAULT_AUTHTOKEN;

    @BeforeAll
    public void setUp() {
        // Loads the environment variables from .env file
        // from the root folder
        // loads the authtoken from the .env file
        // loads the organizations_uid from the .env file
        // creates own retrofit builder with base url
        // create organization instance
        Dotenv dotenv = Dotenv.load();
        DEFAULT_AUTHTOKEN = dotenv.get("auth_token");
        // String DEFAULT_ORG_UID = dotenv.get("organizations_uid");
        // Retrofit retrofit = new
        // Retrofit.Builder().baseUrl("https://api.contentstack.io/v3/").build();
        assert DEFAULT_AUTHTOKEN != null;
        organization = new Contentstack.Builder().setAuthtoken(DEFAULT_AUTHTOKEN).build().organization();
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
    @DisplayName("init org constructor with authtoken")
    void testConstructorWithAuthtoken() {
        Organization organization = new Organization(DEFAULT_AUTHTOKEN);
        assertEquals(DEFAULT_AUTHTOKEN, organization.authtoken);
    }

    @Test
    @DisplayName("init org constructor with retrofit")
    void testConstructorWithRetrofitClient() {
        organization = new Organization(DEFAULT_AUTHTOKEN);
        assertEquals(DEFAULT_AUTHTOKEN, organization.authtoken);
    }

    @Test
    @DisplayName("init org constructor with retrofit and authtoken")
    void testConstructorWithRetrofitClientAndAuthtoken() {
        // create retrofit client
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.contentstack.io/v3/").build();
        Organization organization = new Organization(retrofit, DEFAULT_AUTHTOKEN);
        assertEquals("https://api.contentstack.io/v3/", retrofit.baseUrl().toString());
        assertEquals(DEFAULT_AUTHTOKEN, organization.authtoken);
    }

    @Test
    void testOrganizationGetAllRelativeUrl() {
        Request requestInfo = organization.getAll().request();
        Assertions.assertEquals("/v3/organizations", requestInfo.url().encodedPath());
        Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size());
        Assertions.assertNull(requestInfo.url().query());
        Assertions.assertTrue(requestInfo.url().isHttps());
    }

    @Test
    void testGetAllWithQueryParamLimit() {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("limit", 3);
        Request requestInfo = organization.getAll(queryMap).request();
        Assertions.assertEquals("GET", requestInfo.method());
        assertTrue(isValid(requestInfo.url().toString()));
        assertEquals("3", requestInfo.url().queryParameter("limit"));
        Assertions.assertEquals(1, requestInfo.url().queryParameterNames().size());
    }

    @Test
    void testGetAllOrganizationsWithAllParameters() {
        // Adding parameters to hashmap
        HashMap<String, Object> param = new HashMap<>();
        param.put("limit", 5);
        param.put("skip", 5);
        param.put("asc", "created_at");
        param.put("desc", "update_at");
        param.put("include_count", "true");
        param.put("typeahead", "Contentstack");
        Request requestInfo = organization.getAll(param).request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertEquals(1, requestInfo.headers().names().size());
        assertTrue(requestInfo.isHttps());
        assertTrue(isValid(requestInfo.url().toString()));
        Assertions.assertEquals(param.size(), requestInfo.url().queryParameterNames().size());
    }

    // @Test
    // @DisplayName("Tests get all organizations url host")
    // void testGetAllOrganizationsUrlHost() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    // }
    //
    // @Test
    // @DisplayName("Tests get all organizations url request method")
    // void testGetAllOrganizationsUrlRequestMethod() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // }
    //
    // @Test
    // @DisplayName("Tests get all organizations url request")
    // void testGetAllOrganizationsUrlRequestHeaders() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // }
    //
    // @Test
    // @DisplayName("Test get all organizations url request for single
    // organisation")
    // void testGetAllOrganizationsUrlRequestFewQueryParameters() {
    // HashMap<String, String> queryParam = new HashMap<>();
    // queryParam.put("limit", "1");
    // queryParam.put("skip", "1");
    // Request requestInfo = organization.getAll(queryParam).request();
    // Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size());
    // }
    //
    // // ======================================================================//
    // // ======================================================================//
    //
    // @Test
    // @DisplayName("Test get single organizations with no query parameters")
    // void testGetSingleWithoutQueryParameter() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
    // only");
    // Assertions.assertTrue(isValid(requestInfo.url().toString()));
    // Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
    // "executes without parameter");
    // }
    //
    // @Test
    // @DisplayName("Test get single organizations with all parameters")
    // void testGetSingleOrganizationsWithAllParameters() {
    // // Adding parameters to hashmap
    // HashMap<String, String> queryParam = new HashMap<>();
    // queryParam.put("include_plan", "true");
    // Request requestInfo = organization.getAll(queryParam).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
    // only");
    // Assertions.assertTrue(isValid(requestInfo.url().toString()));
    // Assertions.assertEquals(queryParam.size(),
    // requestInfo.url().queryParameterNames().size(),
    // "executes with parameter");
    // Assertions.assertEquals("include_plan=true",
    // requestInfo.url().encodedQuery());
    // Assertions.assertEquals(
    // "https://" + requestInfo.url().host() + "/v3/organizations/" +
    // DEFAULT_ORG_UID + "?include_plan=true",
    // requestInfo.url().toString());
    // }
    //
    // @Test
    // @DisplayName("Tests get single organizations url host")
    // void testGetSingleOrganizationsUrlHost() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    // }
    //
    // @Test
    // @DisplayName("Tests get single organizations url request method")
    // void testGetSingleOrganizationsUrlRequestMethod() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // }
    //
    // @Test
    // @DisplayName("Tests get single organizations url request")
    // void testGetSingleOrganizationsUrlRequestHeaders() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // }
    //
    // @Test
    // @DisplayName("Tests get single organizations relative url")
    // void testGetSingleOrganizationsRelativeUrl() {
    // Request requestInfo = organization.getAll(new HashMap<>()).request();
    // Assertions.assertEquals("https://" + requestInfo.url().host() +
    // "/v3/organizations/" + DEFAULT_ORG_UID,
    // requestInfo.url().toString());
    // }
    //
    // // ======================================================================//
    // // ======================================================================//
    //
    // @Test
    // @DisplayName("Test get organizations role with no query parameters")
    // void testGetRoleWithoutQueryParameter() {
    // Request requestInfo = organization.getRoles(DEFAULT_ORG_UID, new
    // HashMap<>()).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
    // only");
    // Assertions.assertTrue(isValid(requestInfo.url().toString()));
    // Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
    // "executes without parameter");
    // }
    //
    // @Test
    // @DisplayName("Test get organizations role with all parameters")
    // void testGetRoleOrganizationsWithAllParameters() {
    // // Adding parameters to hashmap
    // HashMap<String, String> queryParam = new HashMap<>();
    // queryParam.put("include_plan", "true");
    // Request requestInfo = organization.getRoles(DEFAULT_ORG_UID,
    // queryParam).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
    // only");
    // Assertions.assertTrue(isValid(requestInfo.url().toString()));
    // Assertions.assertEquals(queryParam.size(),
    // requestInfo.url().queryParameterNames().size(),
    // "executes with parameter");
    // Assertions.assertEquals("include_plan=true",
    // requestInfo.url().encodedQuery());
    // }
    //
    // @Test
    // @DisplayName("Tests get organizations role url host")
    // void testGetRoleOrganizationsUrlHost() {
    // Request requestInfo = organization.getRoles(DEFAULT_AUTHTOKEN, new
    // HashMap<>()).request();
    // Assertions.assertEquals("api.contentstack.io", requestInfo.url().host());
    // }
    //
    // @Test
    // @DisplayName("Tests get organizations role url request method")
    // void testGetRoleOrganizationsUrlRequestMethod() {
    // Request requestInfo = organization.getRoles("org_uid", new
    // HashMap<>()).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // }
    //
    // @Test
    // @DisplayName("Tests get organizations role url request")
    // void testGetRoleOrganizationsUrlRequestHeaders() {
    // Request requestInfo = organization.getRoles("org_uid", new
    // HashMap<>()).request();
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // }
    //
    // @Test
    // @DisplayName("Tests get organizations role relative url")
    // void testGetRoleOrganizationsRelativeUrl() {
    // Request requestInfo = organization.getRoles("org_uid", new
    // HashMap<>()).request();
    // Assertions.assertEquals(
    // "https://" + requestInfo.url().host() + "/v3/organizations/" +
    // DEFAULT_ORG_UID + "/roles",
    // requestInfo.url().toString());
    // }
    //
    // @Test
    // @DisplayName("Test get role with query parameters")
    // void testGetRoleWithQueryParameter() {
    // Map<String, String> mapQueryParam = new HashMap<>();
    // mapQueryParam.putIfAbsent("limit", "10");
    // mapQueryParam.putIfAbsent("skip", "2");
    // Request requestInfo = organization.getRoles("org_uid",
    // mapQueryParam).request();
    // Assertions.assertEquals("GET", requestInfo.method());
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
    // only");
    // Assertions.assertTrue(isValid(requestInfo.url().toString()));
    // Assertions.assertEquals(2, requestInfo.url().queryParameterNames().size(),
    // "executes with query parameter");
    // }
    //
    // // Class<T> type
    //
    // // ======================================================================//
    // // ======================================================================//
    //
    // @Test
    // @DisplayName("Test Invite user with no query parameters")
    // void testInviteUserWithNoParameter() {
    //
    // Request requestInfo = organization.inviteUser(DEFAULT_ORG_UID).request();
    //
    // Assertions.assertEquals("POST", requestInfo.method());
    // Assertions.assertEquals(1, requestInfo.headers().names().size());
    // Assertions.assertTrue(requestInfo.isHttps(), "contentstack works with https
    // only");
    // Assertions.assertTrue(isValid(requestInfo.url().toString()));
    // Assertions.assertEquals(0, requestInfo.url().queryParameterNames().size(),
    // "executes without parameter");
    // Assertions.assertEquals("https://api.contentstack.io/v3/organizations/" +
    // DEFAULT_ORG_UID + "/share",
    // requestInfo.url().toString());
    //
    // try {
    // Response<ResponseBody> response =
    // organization.inviteUser(DEFAULT_ORG_UID).execute();
    // boolean isAvail = response.isSuccessful();
    // if (isAvail) {
    // System.out.println(" response: " + response.body());
    // } else {
    // System.out.println(" response: " + response.errorBody());
    // }
    // } catch (IOException e) {
    //
    // e.printStackTrace();
    // }
    //
    //
    // }

}
