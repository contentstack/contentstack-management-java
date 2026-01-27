package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * API tests for Role module.
 * A role is a collection of permissions that will be applicable to all users assigned this role.
 * 
 * Reference: https://www.contentstack.com/docs/developers/sdks/content-management-sdk/java/reference#role
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleAPITest {

    protected static String AUTHTOKEN = TestClient.AUTHTOKEN;
    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static Stack stack;
    private JSONParser parser = new JSONParser();
    private String existingRoleUid;

    @BeforeAll
    void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        stack = new Contentstack.Builder()
                .setAuthtoken(AUTHTOKEN)
                .setHost(TestClient.DEV_HOST)
                .build()
                .stack(headers);
    }

    // ==================== READ OPERATIONS (API TESTS) ====================

    @Test
    @Order(100)
    @DisplayName("Find all roles - should return roles array")
    void testFindAllRoles() throws IOException, ParseException {
        Roles roles = stack.roles();
        roles.addParam("include_rules", true);
        roles.addParam("include_permissions", true);
        
        Response<ResponseBody> response = roles.find().execute();

        assertTrue(response.isSuccessful(), 
                "Find roles failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("roles"), "Response should contain 'roles' key");
        JSONArray rolesArray = (JSONArray) result.get("roles");
        assertNotNull(rolesArray, "Roles array should not be null");
        assertFalse(rolesArray.isEmpty(), "Stack should have at least one role");
        
        // Validate role structure
        JSONObject firstRole = (JSONObject) rolesArray.get(0);
        assertTrue(firstRole.containsKey("uid"), "Role should have 'uid' field");
        assertTrue(firstRole.containsKey("name"), "Role should have 'name' field");
        
        // Store first role UID for fetch test
        existingRoleUid = (String) firstRole.get("uid");
        
        System.out.println("[Test] Found " + rolesArray.size() + " role(s)");
    }

    @Test
    @Order(101)
    @DisplayName("Fetch single role - should return role details")
    void testFetchSingleRole() throws IOException, ParseException {
        assumeTrue(existingRoleUid != null, "Skipping: No role UID available from previous test");
        
        Response<ResponseBody> response = stack.roles(existingRoleUid).fetch().execute();

        assertTrue(response.isSuccessful(), 
                "Fetch role failed: " + getErrorMessage(response));

        String body = response.body().string();
        JSONObject result = (JSONObject) parser.parse(body);
        
        assertTrue(result.containsKey("role"), "Response should contain 'role' key");
        JSONObject role = (JSONObject) result.get("role");
        assertEquals(existingRoleUid, role.get("uid"), "Role UID should match");
        assertNotNull(role.get("name"), "Role should have name");
    }

    private String getErrorMessage(Response<ResponseBody> response) {
        try {
            if (response.errorBody() != null) {
                return response.errorBody().string();
            }
        } catch (IOException e) {
            // Ignore
        }
        return "Unknown error (code: " + response.code() + ")";
    }

    // ==================== UNIT TESTS (Request Validation) ====================

    @Test
    @Tag("unit")
    void testCreateRoleRequest() {
        String testRoleUid = "test_role_uid";
        JSONObject body = new JSONObject();
        body.put("abcd", "abcd");
        Roles roles = stack.roles(testRoleUid);
        roles.addHeader(Util.API_KEY, API_KEY);
        roles.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = roles.create(body).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
    }

    @Test
    @Tag("unit")
    void testUpdateRoleRequest() {
        String testRoleUid = "test_role_uid";
        Roles roles = stack.roles(testRoleUid);
        roles.addHeader("authtoken", AUTHTOKEN);
        roles.addHeader("api_key", API_KEY);
        JSONObject object = new JSONObject();
        object.put("key", "value");

        Request request = roles.update(object).request();
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
    }

    @Test
    @Tag("unit")
    void testDeleteRoleRequest() {
        String testRoleUid = "test_role_uid";
        Roles roles = stack.roles(testRoleUid);
        roles.addHeader("authtoken", AUTHTOKEN);
        roles.addHeader("api_key", API_KEY);
        Request request = roles.delete().request();
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
    }

    @Test
    @Tag("unit")
    void testCreateRoleWithTaxonomyRequest() throws IOException {
        JSONObject requestBody = Utils.readJson("mockrole/createRole.json");
        Roles roles = stack.roles();
        roles.addHeader(Util.API_KEY, API_KEY);
        roles.addHeader(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Request request = roles.create(requestBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals(2, request.url().pathSegments().size());
        Assertions.assertEquals("v3", request.url().pathSegments().get(0));
        Assertions.assertEquals("roles", request.url().pathSegments().get(1));
    }
}
