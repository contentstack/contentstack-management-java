package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StackUnitTests {

    protected Stack stack;
    private Dotenv env;


    @BeforeAll
    public void setUp() {
        env = Dotenv.load();
        String apiKey = env.get("api_key");
        String authtoken = env.get("auth_token");
        assert apiKey != null;
        stack = new Contentstack.Builder().setAuthtoken(authtoken).build().stack(apiKey);
    }

    @Test
    void testStackWithoutAuthtokenExceptionExpected() {
        try {
            String apiKey = env.get("api_key");
            assert apiKey != null;
            new Contentstack.Builder().build().stack(apiKey);
        } catch (Exception e) {
            String STACK_EXCEPTION = "Please Login to access stack instance";
            Assertions.assertEquals(STACK_EXCEPTION,
                    e.getLocalizedMessage());
        }
    }

    @Test
    void testSingleStackMethod() {
        Request request = stack.fetch().request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testSingleStackHeaders() {
        Request request = stack.fetch().request();
        Assertions.assertEquals(1, request.headers().size());
        Assertions.assertEquals(stack.apiKey, request.headers("api_key").get(0));
    }

    @Test
    void testSingleStackHost() {
        Request request = stack.fetch().request();
        Assertions.assertEquals("api.contentstack.io", request.url().host());
    }

    @Test
    void testSingleStackPort() {
        Request request = stack.fetch().request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testSingleStackUrl() {
        Request request = stack.fetch().request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks",
                request.url().toString());
    }

    @Test
    void testSingleStackPathSegment() {
        Request request = stack.fetch().request();
        Assertions.assertEquals(
                "/v3/stacks",
                request.url().encodedPath());
    }

    @Test
    void testSingleStackWithOrgUid() {
        String orgId = env.get("organizationUid");
        assert orgId != null;
        Request request = stack.fetch(orgId).request();
        Assertions.assertEquals(
                2,
                request.headers().size());
    }

    @Test
    void testSingleStackWithHeaders() {
        String orgId = env.get("organizationUid");
        assert orgId != null;
        Request request = stack.fetch(orgId).request();
        Set<String> headers = request.headers().names();
        Assertions.assertEquals(2, headers.size());
    }

    @Test
    void testSingleStackWithHeadersNames() {
        String orgId = env.get("organizationUid");
        assert orgId != null;
        Request request = stack.fetch(orgId).request();
        String headerAPIKey = request.headers().name(0);
        String headerOrgKey = request.headers().name(1);
        Assertions.assertEquals("api_key", headerAPIKey);
        Assertions.assertEquals("organization_uid", headerOrgKey);
    }

    @Test
    void testSingleStackWithQueryParams() {
        String orgId = env.get("organizationUid");
        HashMap<String, Boolean> queryParams = new HashMap<>();
        queryParams.put("include_collaborators", true);
        queryParams.put("include_stack_variables", true);
        queryParams.put("include_discrete_variables", true);
        queryParams.put("include_count", true);
        assert orgId != null;
        Request request = stack.fetch(orgId, queryParams).request();
        Assertions.assertEquals(
                "include_collaborators=true&include_discrete_variables=true&include_stack_variables=true&include_count=true",
                request.url().encodedQuery());
    }

    @Test
    void testSingleStackWithQueryIncludeCount() {
        String orgId = env.get("organizationUid");
        HashMap<String, Boolean> queryParams = new HashMap<>();
        queryParams.put("include_count", true);
        assert orgId != null;
        Request request = stack.fetch(orgId, queryParams).request();
        Assertions.assertEquals(
                "include_count=true",
                request.url().encodedQuery());
    }

    @Test
    void testSingleStackWithQueryIncludeCountAndHeaders() {
        String orgId = env.get("organizationUid");
        HashMap<String, Boolean> queryParams = new HashMap<>();
        queryParams.put("include_count", true);
        assert orgId != null;
        Request request = stack.fetch(orgId, queryParams).request();
        Assertions.assertEquals(
                "include_count=true",
                request.url().encodedQuery());
        Assertions.assertEquals(2, request.headers().size());
    }


    @Test
    void testCreateStackMethod() {
        String orgId = env.get("organizationUid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        assert orgId != null;
        stack.create(orgId, bodyString).request();
    }


    @Test
    void testCreateStackHeader() {
        String orgId = env.get("organizationUid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        assert orgId != null;
        Request request = stack.create(orgId, bodyString).request();
        Assertions.assertEquals(orgId, request.headers().get("organization_uid"));
    }


    @Test
    void testCreateStackUrl() {
        String orgId = env.get("organizationUid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        assert orgId != null;
        Request request = stack.create(orgId, bodyString).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks", request.url().toString());
    }

    @Test
    void testCreateStackRequestBodyCharset() {
        String orgId = env.get("organizationUid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        assert orgId != null;
        Request request = stack.create(orgId, bodyString).request();
        assert request.body() != null;
        Assertions.assertEquals("application/json; charset=UTF-8", Objects.requireNonNull(request.body().contentType()).toString());
    }


    @Test
    void testUpdateStackMethod() {
        String bodyString = "{\n" +
                "\t\"stack\": {\n" +
                "\t\t\"name\": \"My New Stack\",\n" +
                "\t\t\"description\": \"My new test stack\"\n" +
                "\t}\n" +
                "}";
        Request request = stack.update(bodyString).request();
        Assertions.assertEquals("PUT", request.method());
    }

    @Test
    void testUpdateStackUrl() {
        String bodyString = "{\n" +
                "\t\"stack\": {\n" +
                "\t\t\"name\": \"My New Stack\",\n" +
                "\t\t\"description\": \"My new test stack\"\n" +
                "\t}\n" +
                "}";
        Request request = stack.update(bodyString).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks", request.url().toString());
    }

    @Test
    void testUpdateStackUrlEncodedPath() {
        String bodyString = "{\n" +
                "\t\"stack\": {\n" +
                "\t\t\"name\": \"My New Stack\",\n" +
                "\t\t\"description\": \"My new test stack\"\n" +
                "\t}\n" +
                "}";
        Request request = stack.update(bodyString).request();
        Assertions.assertEquals("/v3/stacks", request.url().encodedPath());
    }


    @Test
    void testUpdateStackPort() {
        String bodyString = "{\n" +
                "\t\"stack\": {\n" +
                "\t\t\"name\": \"My New Stack\",\n" +
                "\t\t\"description\": \"My new test stack\"\n" +
                "\t}\n" +
                "}";
        Request request = stack.update(bodyString).request();
        Assertions.assertEquals(443, request.url().port());
    }


    @Test
    void testUpdateStackBodyContentType() {
        String bodyString = "{\n" +
                "\t\"stack\": {\n" +
                "\t\t\"name\": \"My New Stack\",\n" +
                "\t\t\"description\": \"My new test stack\"\n" +
                "\t}\n" +
                "}";
        Request request = stack.update(bodyString).request();
        assert request.body() != null;
        Assertions.assertEquals(
                "application/json; charset=UTF-8",
                Objects.requireNonNull(request.body().contentType()).toString());
    }


    @Test
    void testTransferOwnership() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(bodyString).request();
        Assertions.assertEquals(
                "POST",
                request.method());
    }

    @Test
    void testTransferOwnershipUrl() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(bodyString).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/transfer_ownership",
                request.url().toString());
    }

    @Test
    void testTransferOwnershipHeaders() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(bodyString).request();
        Assertions.assertEquals(stack.apiKey,
                request.headers().get("api_key"));
    }

    @Test
    void testTransferOwnershipRequestBody() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(bodyString).request();
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
        String reqStr = "{\n" +
                "  \"stack_settings\":{\n" +
                "    \"stack_variables\":{\n" +
                "      \"enforce_unique_urls\":true,\n" +
                "      \"sys_rte_allowed_tags\":\"style | figure | script\"\n" +
                "    },\n" +
                "    \"rte\":{\n" +
                "      \"ishaileshmishra\":true\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Request request = stack.updateSetting(reqStr).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }


    @Test
    void testUpdateSettingMethod() {
        String reqStr = "{\n" +
                "  \"stack_settings\":{\n" +
                "    \"stack_variables\":{\n" +
                "      \"enforce_unique_urls\":true,\n" +
                "      \"sys_rte_allowed_tags\":\"style | figure | script\"\n" +
                "    },\n" +
                "    \"rte\":{\n" +
                "      \"ishaileshmishra\":true\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Request request = stack.updateSetting(reqStr).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testUpdateSettingPort() {
        String reqStr = "{\n" +
                "  \"stack_settings\":{\n" +
                "    \"stack_variables\":{\n" +
                "      \"enforce_unique_urls\":true,\n" +
                "      \"sys_rte_allowed_tags\":\"style | figure | script\"\n" +
                "    },\n" +
                "    \"rte\":{\n" +
                "      \"ishaileshmishra\":true\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Request request = stack.updateSetting(reqStr).request();
        Assertions.assertEquals(443, request.url().port());

    }

    @Test
    void testUpdateSettingEncodedPath() {
        String reqStr = "{\n" +
                "  \"stack_settings\":{\n" +
                "    \"stack_variables\":{\n" +
                "      \"enforce_unique_urls\":true,\n" +
                "      \"sys_rte_allowed_tags\":\"style | figure | script\"\n" +
                "    },\n" +
                "    \"rte\":{\n" +
                "      \"ishaileshmishra\":true\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Request request = stack.updateSetting(reqStr).request();
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());

    }

    @Test
    void testUpdateSettingRequestUrl() {
        String reqStr = "{\n" +
                "  \"stack_settings\":{\n" +
                "    \"stack_variables\":{\n" +
                "      \"enforce_unique_urls\":true,\n" +
                "      \"sys_rte_allowed_tags\":\"style | figure | script\"\n" +
                "    },\n" +
                "    \"rte\":{\n" +
                "      \"ishaileshmishra\":true\n" +
                "    }\n" +
                "  }\n" +
                "}";
        Request request = stack.updateSetting(reqStr).request();
        Assertions.
                assertEquals(
                        "https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }

    @Test
    void testResetStackSettings() {
        String strBody = "{\n" +
                "    \"stack_settings\":{\n" +
                "        \"discrete_variables\":{},\n" +
                "        \"stack_variables\":{}\n" +
                "    }\n" +
                "}";
        Request request = stack.resetSetting(strBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }


    @Test
    void testResetStackSettingMethod() {
        String strBody = "{\n" +
                "    \"stack_settings\":{\n" +
                "        \"discrete_variables\":{},\n" +
                "        \"stack_variables\":{}\n" +
                "    }\n" +
                "}";
        Request request = stack.resetSetting(strBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testResetPort() {
        String strBody = "{\n" +
                "    \"stack_settings\":{\n" +
                "        \"discrete_variables\":{},\n" +
                "        \"stack_variables\":{}\n" +
                "    }\n" +
                "}";
        Request request = stack.resetSetting(strBody).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testResetEncodedPath() {
        String strBody = "{\n" +
                "    \"stack_settings\":{\n" +
                "        \"discrete_variables\":{},\n" +
                "        \"stack_variables\":{}\n" +
                "    }\n" +
                "}";
        Request request = stack.resetSetting(strBody).request();
        Assertions.assertEquals("/v3/stacks/settings", request.url().encodedPath());
    }

    @Test
    void testResetRequestUrl() {
        String strBody = "{\n" +
                "    \"stack_settings\":{\n" +
                "        \"discrete_variables\":{},\n" +
                "        \"stack_variables\":{}\n" +
                "    }\n" +
                "}";
        Request request = stack.resetSetting(strBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/settings", request.url().url().toString());

    }

    @Test
    void testShareStack() {
        String strBody = "{\n" +
                "\t\"emails\": [\n" +
                "\t\t\"manager@example.com\"\n" +
                "\t],\n" +
                "\t\"roles\": {\n" +
                "\t\t\"manager@example.com\": [\n" +
                "\t\t\t\"ishaileshmishra\"\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        Request request = stack.share(strBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/share", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/share", request.url().url().toString());
    }


    @Test
    void testShareMethod() {
        String strBody = "{\n" +
                "\t\"emails\": [\n" +
                "\t\t\"manager@example.com\"\n" +
                "\t],\n" +
                "\t\"roles\": {\n" +
                "\t\t\"manager@example.com\": [\n" +
                "\t\t\t\"ishaileshmishra\"\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        Request request = stack.share(strBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testShareStackPort() {
        String strBody = "{\n" +
                "\t\"emails\": [\n" +
                "\t\t\"manager@example.com\"\n" +
                "\t],\n" +
                "\t\"roles\": {\n" +
                "\t\t\"manager@example.com\": [\n" +
                "\t\t\t\"ishaileshmishra\"\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        Request request = stack.share(strBody).request();
        Assertions.assertEquals(443, request.url().port());
    }


    @Test
    void testShareStackEncodedUrl() {
        String strBody = "{\n" +
                "\t\"emails\": [\n" +
                "\t\t\"manager@example.com\"\n" +
                "\t],\n" +
                "\t\"roles\": {\n" +
                "\t\t\"manager@example.com\": [\n" +
                "\t\t\t\"ishaileshmishra\"\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        Request request = stack.share(strBody).request();
        Assertions.assertEquals("/v3/stacks/share", request.url().encodedPath());
    }

    @Test
    void testShareStackCompleteUrl() {
        String strBody = "{\n" +
                "\t\"emails\": [\n" +
                "\t\t\"manager@example.com\"\n" +
                "\t],\n" +
                "\t\"roles\": {\n" +
                "\t\t\"manager@example.com\": [\n" +
                "\t\t\t\"ishaileshmishra\"\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        Request request = stack.share(strBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/share", request.url().url().toString());
    }

    @Test
    void testUnshareStack() {
        String strBody = "{\n" +
                "\t\"email\": \"ishaileshmishra@example.com\"\n" +
                "}";
        Request request = stack.unshare(strBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/unshare", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/unshare", request.url().url().toString());

    }

    @Test
    void testUnshareStackMethod() {
        String strBody = "{\n" +
                "\t\"email\": \"ishaileshmishra@example.com\"\n" +
                "}";
        Request request = stack.unshare(strBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testUnshareStackPort() {
        String strBody = "{\n" +
                "\t\"email\": \"ishaileshmishra@example.com\"\n" +
                "}";
        Request request = stack.unshare(strBody).request();

        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testUnshareStackEncodedUrl() {
        String strBody = "{\n" +
                "\t\"email\": \"ishaileshmishra@example.com\"\n" +
                "}";
        Request request = stack.unshare(strBody).request();
        Assertions.assertEquals("/v3/stacks/unshare", request.url().encodedPath());
    }

    @Test
    void testUnshareStackCompleteUrl() {
        String strBody = "{\n" +
                "\t\"email\": \"ishaileshmishra@example.com\"\n" +
                "}";
        Request request = stack.unshare(strBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/unshare", request.url().url().toString());
    }

    @Test
    void testGetAllUsers() {
        Request request = stack.allUsers().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks?include_collaborators=true", request.url().url().toString());

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
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks?include_collaborators=true", request.url().url().toString());

    }

    @Test
    void testUpdateUserRoles() {
        String strBody = "{\n" +
                "\t\"users\": {\n" +
                "\t\t\"user_uid\": [\"role_uid1\", \"role_uid2\"]\n" +
                "\t}\n" +
                "}";
        Request request = stack.roles(strBody).request();
        Assertions.assertEquals("POST", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/users/roles", request.url().encodedPath());
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/users/roles", request.url().url().toString());

    }


    @Test
    void testUpdateUserRolesMethod() {
        String strBody = "{\n" +
                "\t\"users\": {\n" +
                "\t\t\"user_uid\": [\"role_uid1\", \"role_uid2\"]\n" +
                "\t}\n" +
                "}";
        Request request = stack.roles(strBody).request();
        Assertions.assertEquals("POST", request.method());
    }

    @Test
    void testUpdateUserRolesPort() {
        String strBody = "{\n" +
                "\t\"users\": {\n" +
                "\t\t\"user_uid\": [\"role_uid1\", \"role_uid2\"]\n" +
                "\t}\n" +
                "}";
        Request request = stack.roles(strBody).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testUpdateUserRolesEncodedUrl() {
        String strBody = "{\n" +
                "\t\"users\": {\n" +
                "\t\t\"user_uid\": [\"role_uid1\", \"role_uid2\"]\n" +
                "\t}\n" +
                "}";
        Request request = stack.roles(strBody).request();
        Assertions.assertEquals("/v3/stacks/users/roles", request.url().encodedPath());
    }

    @Test
    void testUpdateUserRolesCompleteUrl() {
        String strBody = "{\n" +
                "\t\"users\": {\n" +
                "\t\t\"user_uid\": [\"role_uid1\", \"role_uid2\"]\n" +
                "\t}\n" +
                "}";
        Request request = stack.roles(strBody).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks/users/roles", request.url().url().toString());
    }

    @Test
    void testAcceptOwnership() {
        String userId = env.get("userId");
        String ownershipToken = env.get("ownershipToken");
        assert ownershipToken != null;
        assert userId != null;
        Request request = stack.acceptOwnership(ownershipToken, userId).request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertEquals("/v3/stacks/accept_ownership/" + ownershipToken, request.url().encodedPath());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/accept_ownership/" + ownershipToken + "?uid=" + userId + "&api_key=" + stack.apiKey + "", request.url().url().toString());

    }

    @Test
    void testAcceptOwnershipMethod() {
        String userId = env.get("userId");
        String ownershipToken = env.get("ownershipToken");
        assert ownershipToken != null;
        assert userId != null;
        Request request = stack.acceptOwnership(ownershipToken, userId).request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testAcceptOwnershipPort() {
        String userId = env.get("userId");
        String ownershipToken = env.get("ownershipToken");
        assert ownershipToken != null;
        assert userId != null;
        Request request = stack.acceptOwnership(ownershipToken, userId).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testAcceptOwnershipEncodedPath() {
        String userId = env.get("userId");
        String ownershipToken = env.get("ownershipToken");
        assert ownershipToken != null;
        assert userId != null;
        Request request = stack.acceptOwnership(ownershipToken, userId).request();
        Assertions.assertEquals("/v3/stacks/accept_ownership/" + ownershipToken, request.url().encodedPath());
    }

    @Test
    void testAcceptOwnershipCompleteUrl() {
        String userId = env.get("userId");
        String ownershipToken = env.get("ownershipToken");
        assert ownershipToken != null;
        assert userId != null;
        Request request = stack.acceptOwnership(ownershipToken, userId).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/accept_ownership/" + ownershipToken + "?uid=" + userId + "&api_key=" + stack.apiKey + "", request.url().url().toString());
    }


}
