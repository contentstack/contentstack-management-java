package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class StackUnitTests {

    protected Stack stack;
    protected String ORG_ID = TestClient.ORGANIZATION_UID;
    protected String apiKey = TestClient.API_KEY;
    protected String authtoken = TestClient.AUTHTOKEN;
    protected String USER_ID = TestClient.USER_ID;
    protected JSONObject requestBody = Utils.readJson("mockstack/create_stack.json");

    @BeforeAll
    public void setUp() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("api_key", apiKey);
        headers.put("authtoken", authtoken);
        stack = TestClient.getClient().stack(headers);
    }

    @Test
    void testStackWithoutAuthtokenExceptionExpected() {
        try {
            HashMap<String, Object> headers = new HashMap<>();
            headers.put("api_key", apiKey);
            new Contentstack.Builder().build().stack(headers);
        } catch (Exception e) {
            String STACK_EXCEPTION = "Please Login to access stack instance";
            Assertions.assertEquals(STACK_EXCEPTION,
                    e.getLocalizedMessage());
        }
    }

    @Test
    void testSingleStackMethod() {
        Request request = stack.find().request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testSingleStackHeaders() {
        stack.clearParams();
        Request request = stack.find().request();
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertEquals(stack.headers.get("api_key").toString(), request.headers("api_key").get(0));
    }

    @Test
    void testSingleStackHost() {
        Request request = stack.find().request();
        Assertions.assertEquals("api.contentstack.io", request.url().host());
    }

    @Test
    void testSingleStackPort() {
        Request request = stack.find().request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testSingleStackUrl() {
        stack.clearParams();
        Request request = stack.find().request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks",
                request.url().toString());
    }

    @Test
    void testSingleStackPathSegment() {
        Request request = stack.find().request();
        Assertions.assertEquals("/v3/stacks", request.url().encodedPath());
    }

    @Test
    void testSingleStackWithOrgUid() {
        stack.addHeader("organization_uid", ORG_ID);
        Request request = stack.find().request();
        Assertions.assertEquals(3, request.headers().size());
    }

    @Test
    void testSingleStackWithHeaders() {
        stack.addHeader("organization_uid", ORG_ID);
        Request request = stack.find().request();
        Set<String> headers = request.headers().names();
        Assertions.assertEquals(3, headers.size());
    }

    @Test
    void testSingleStackWithHeadersNames() {
        stack.addHeader("organization_uid", ORG_ID);
        Request request = stack.find().request();
        String headerAPIKey = request.headers().name(0);
        String headerOrgKey = request.headers().name(1);
        Assertions.assertEquals("authtoken", headerAPIKey);
        Assertions.assertEquals("api_key", headerOrgKey);
    }

    @Test
    void testSingleStackWithQueryParams() {
        stack.addParam("include_collaborators", true);
        stack.addParam("include_stack_variables", true);
        stack.addParam("include_discrete_variables", true);
        stack.addParam("include_count", true);
        stack.addHeader("organization_uid", ORG_ID);
        // stack.clearParams();
        Request request = stack.find().request();
        Assertions.assertEquals(
                "include_collaborators=true&include_discrete_variables=true&include_stack_variables=true&include_count=true",
                request.url().encodedQuery());
    }

    @Test
    void testSingleStackWithQueryIncludeCount() {
        stack.clearParams();
        stack.addParam("include_count", true);
        stack.addHeader("organization_uid", ORG_ID);
        Request request = stack.find().request();
        Assertions.assertEquals(
                "include_count=true",
                request.url().encodedQuery());
    }

    @Test
    void testSingleStackWithQueryIncludeCountAndHeaders() {
        stack.clearParams();
        stack.addParam("include_count", true);
        stack.addHeader("organization_uid", ORG_ID);
        Request request = stack.find().request();
        Assertions.assertEquals(
                "include_count=true",
                request.url().encodedQuery());
        Assertions.assertEquals(3, request.headers().size());
    }

    @Test
    void testCreateStackMethod() {
        assert ORG_ID != null;
        stack.create(ORG_ID, requestBody).request();
        Assertions.assertTrue(true);
    }

    @Test
    void testCreateStackHeader() {
        assert ORG_ID != null;
        Request request = stack.create(ORG_ID, requestBody).request();
        Assertions.assertEquals(ORG_ID, request.headers().get("organization_uid"));
    }

    @Test
    void testCreateStackUrl() {

        assert ORG_ID != null;
        Request request = stack.create(ORG_ID, requestBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks", request.url().toString());
    }

    @Test
    void testCreateStackRequestBodyCharset() {
        assert ORG_ID != null;
        Request request = stack.create(ORG_ID, requestBody).request();
        assert request.body() != null;
        Assertions.assertEquals("application/json; charset=UTF-8",
                Objects.requireNonNull(request.body().contentType()).toString());
    }

    @Test
    void testUpdateStackMethod() {
        JSONObject updateRequestBody = Utils.readJson("mockstack/update.json");
        Request request = stack.update(updateRequestBody).request();
        Assertions.assertEquals("PUT", request.method());
    }

    @Test
    void testUpdateStackUrl() {
        JSONObject updateRequestBody = Utils.readJson("mockstack/update.json");
        Request request = stack.update(updateRequestBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks", request.url().toString());
    }

    @Test
    void testUpdateStackUrlEncodedPath() {
        JSONObject updateRequestBody = Utils.readJson("mockstack/update.json");
        Request request = stack.update(updateRequestBody).request();
        Assertions.assertEquals("/v3/stacks", request.url().encodedPath());
    }

    @Test
    void testUpdateStackPort() {
        JSONObject updateRequestBody = Utils.readJson("mockstack/update.json");
        Request request = stack.update(updateRequestBody).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testUpdateStackBodyContentType() {
        JSONObject updateRequestBody = Utils.readJson("mockstack/update.json");
        Request request = stack.update(updateRequestBody).request();
        assert request.body() != null;
        Assertions.assertEquals(
                "application/json; charset=UTF-8",
                Objects.requireNonNull(request.body().contentType()).toString());
    }

    @Test
    void testTransferOwnership() {
        JSONObject ownershipBody = Utils.readJson("mockstack/ownership.json");
        Request request = stack.transferOwnership(ownershipBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testTransferOwnershipUrl() {
        JSONObject ownershipBody = Utils.readJson("mockstack/ownership.json");
        Request request = stack.transferOwnership(ownershipBody).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/transfer_ownership",
                request.url().toString());
    }

    @Test
    void testTransferOwnershipHeaders() {
        JSONObject ownershipBody = Utils.readJson("mockstack/ownership.json");
        Request request = stack.transferOwnership(ownershipBody).request();
        Assertions.assertEquals(stack.headers.get("api_key").toString(),
                request.headers().get("api_key"));
    }

    @Test
    void testTransferOwnershipRequestBody() {
        JSONObject ownershipBody = Utils.readJson("mockstack/ownership.json");
        Request request = stack.transferOwnership(ownershipBody).request();
        assert request.body() != null;
        Assertions.assertEquals("application/json; charset=UTF-8",
                Objects.requireNonNull(request.body().contentType()).toString());
    }

    @Test
    void testStackSetting() {
        Request request = stack.setting().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings",
                request.url().toString());
        Assertions.assertEquals("/v3/stacks/settings",
                request.url().encodedPath());
        Assertions.assertNull(
                request.url().encodedQuery());
        Assertions.assertEquals(3,
                request.url().pathSegments().size());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testStackSettingEncodedPath() {
        Request request = stack.setting().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings",
                request.url().toString());
    }

    @Test
    void testStackSettingEncodedQuery() {
        Request request = stack.setting().request();
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Test
    void testStackSettingPathSegment() {
        Request request = stack.setting().request();
        Assertions.assertEquals(3,
                request.url().pathSegments().size());
    }

    @Test
    void testStackSettingRequestMethod() {
        Request request = stack.setting().request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testStackSettingPort() {
        Request request = stack.setting().request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testUpdateSettings() {
        JSONObject settingBody = Utils.readJson("mockstack/setting.json");
        Request request = stack.updateSetting(settingBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }

    @Test
    void testUpdateSettingMethod() {
        JSONObject settingBody = Utils.readJson("mockstack/setting.json");
        Request request = stack.updateSetting(settingBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testUpdateSettingPort() {
        JSONObject settingBody = Utils.readJson("mockstack/setting.json");
        Request request = stack.updateSetting(settingBody).request();
        Assertions.assertEquals(443, request.url().port());

    }

    @Test
    void testUpdateSettingEncodedPath() {
        JSONObject settingBody = Utils.readJson("mockstack/setting.json");
        Request request = stack.updateSetting(settingBody).request();
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());

    }

    @Test
    void testUpdateSettingRequestUrl() {
        JSONObject settingBody = Utils.readJson("mockstack/setting.json");
        Request request = stack.updateSetting(settingBody).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }

    @Test
    void testResetStackSettings() {
        JSONObject resetSettingBody = Utils.readJson("mockstack/reset_setting.json");
        Request request = stack.resetSetting(resetSettingBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }

    @Test
    void testResetStackSettingMethod() {
        JSONObject resetSettingBody = Utils.readJson("mockstack/reset_setting.json");
        Request request = stack.resetSetting(resetSettingBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testResetPort() {
        JSONObject resetSettingBody = Utils.readJson("mockstack/reset_setting.json");
        Request request = stack.resetSetting(resetSettingBody).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testResetEncodedPath() {
        JSONObject resetSettingBody = Utils.readJson("mockstack/reset_setting.json");
        Request request = stack.resetSetting(resetSettingBody).request();
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());
    }

    @Test
    void testResetRequestUrl() {
        JSONObject resetSettingBody = Utils.readJson("mockstack/reset_setting.json");
        Request request = stack.resetSetting(resetSettingBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }

    @Test
    void testShareStack() {
        JSONObject shareStackBody = Utils.readJson("mockstack/share_stack.json");
        Request request = stack.share(shareStackBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/share", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/share", request.url().url().toString());
    }

    @Test
    void testShareMethod() {
        JSONObject shareStackBody = Utils.readJson("mockstack/share_stack.json");
        Request request = stack.share(shareStackBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testShareStackPort() {
        JSONObject shareStackBody = Utils.readJson("mockstack/share_stack.json");
        Request request = stack.share(shareStackBody).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testShareStackEncodedUrl() {
        JSONObject shareStackBody = Utils.readJson("mockstack/share_stack.json");
        Request request = stack.share(shareStackBody).request();
        Assertions.assertEquals("/v3/stacks/share", request.url().encodedPath());
    }

    @Test
    void testShareStackCompleteUrl() {
        JSONObject shareStackBody = Utils.readJson("mockstack/share_stack.json");
        Request request = stack.share(shareStackBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/share", request.url().url().toString());
    }

    @Test
    void testUnshareStack() {
        JSONObject unshareRequestBody = Utils.readJson("mockstack/unshare.json");
        Request request = stack.unshare(unshareRequestBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/unshare", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/unshare", request.url().url().toString());

    }

    @Test
    void testUnshareStackMethod() {
        JSONObject unshareRequestBody = Utils.readJson("mockstack/unshare.json");
        Request request = stack.unshare(unshareRequestBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testUnshareStackPort() {
        JSONObject unshareRequestBody = Utils.readJson("mockstack/unshare.json");
        Request request = stack.unshare(unshareRequestBody).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testUnshareStackEncodedUrl() {
        JSONObject unshareRequestBody = Utils.readJson("mockstack/unshare.json");
        Request request = stack.unshare(unshareRequestBody).request();
        Assertions.assertEquals("/v3/stacks/unshare", request.url().encodedPath());
    }

    @Test
    void testUnshareStackCompleteUrl() {
        JSONObject unshareRequestBody = Utils.readJson("mockstack/unshare.json");
        Request request = stack.unshare(unshareRequestBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/unshare", request.url().url().toString());
    }

    @Test
    void testGetAllUsers() {
        Request request = stack.allUsers().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks?include_collaborators=true",
                request.url().url().toString());

    }

    @Test
    void testGetAllUsersMethod() {
        Request request = stack.allUsers().request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testGetAllUsersPort() {
        Request request = stack.allUsers().request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testGetAllUserEncodedPath() {
        Request request = stack.allUsers().request();
        Assertions.assertEquals("/v3/stacks", request.url().encodedPath());
    }

    @Test
    void testGetAllUserCompleteUrl() {
        Request request = stack.allUsers().request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks?include_collaborators=true",
                request.url().url().toString());

    }

    @Test
    void testUpdateUserRoles() {
        JSONObject updateUserRole = Utils.readJson("mockstack/update_user_role.json");
        Request request = stack.roles(updateUserRole).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/users/roles", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/users/roles", request.url().url().toString());

    }

    @Test
    void testUpdateUserRolesMethod() {
        JSONObject updateUserRole = Utils.readJson("mockstack/update_user_role.json");
        Request request = stack.roles(updateUserRole).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testUpdateUserRolesPort() {
        JSONObject updateUserRole = Utils.readJson("mockstack/update_user_role.json");
        Request request = stack.roles(updateUserRole).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testUpdateUserRolesEncodedUrl() {
        JSONObject updateUserRole = Utils.readJson("mockstack/update_user_role.json");
        Request request = stack.roles(updateUserRole).request();
        Assertions.assertEquals("/v3/stacks/users/roles", request.url().encodedPath());
    }

    @Test
    void testUpdateUserRolesCompleteUrl() {
        JSONObject updateUserRole = Utils.readJson("mockstack/update_user_role.json");
        Request request = stack.roles(updateUserRole).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/users/roles", request.url().url().toString());
    }

    @Test
    void testAcceptOwnership() {
        stack.clearParams();
        stack.addParam("uid", USER_ID);
        stack.addParam("api_key", apiKey);
        Request request = stack.acceptOwnership(ORG_ID).request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/accept_ownership/" + ORG_ID, request.url().encodedPath());
    }

    @Test
    void testAcceptOwnershipMethod() {
        Request request = stack.acceptOwnership(USER_ID).request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testAcceptOwnershipPort() {
        Request request = stack.acceptOwnership(ORG_ID).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testAcceptOwnershipEncodedPath() {
        Request request = stack.acceptOwnership(ORG_ID).request();
        Assertions.assertEquals("/v3/stacks/accept_ownership/" + ORG_ID, request.url().encodedPath());
    }

    @Test
    void testAcceptOwnershipCompleteUrl() {
        stack.clearParams();
        stack.addParam("api_key", apiKey);
        stack.addParam("uid", USER_ID);
        Request request = stack.acceptOwnership(ORG_ID).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/accept_ownership/" + ORG_ID + "?uid=" + USER_ID
                        + "&api_key=" + stack.headers.get("api_key").toString() + "",
                request.url().url().toString());
    }


    @Test
    void testMissedFunctions() {
        stack.removeParam("key");
        stack.globalField();
        stack.locale();
        stack.label();
        stack.extensions();
        stack.roles();
        stack.releases();
        stack.publishQueue();
        Assertions.assertNotNull(stack);

    }

}
