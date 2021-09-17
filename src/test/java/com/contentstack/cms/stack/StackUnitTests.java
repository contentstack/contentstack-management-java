package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.user.User;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StackUnitTests {

    protected Stack stack;
    private Dotenv env;
    private String apiKey;

    @BeforeAll
    public void setUp() {
        env = Dotenv.load();
        apiKey = env.get("api_key");
        String authtoken = env.get("auth_token");
        stack = new Contentstack.Builder().setAuthtoken(authtoken).build().stack();
    }

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException {
        Constructor<User> constructor =
                User.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(
                constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void testStackWithoutAuthtokenExceptionExpected() {
        try {
            new Contentstack.Builder().build().stack();
        } catch (Exception e) {
            String STACK_EXCEPTION = "Please Login to access stack instance";
            Assertions.assertEquals(STACK_EXCEPTION,
                    e.getLocalizedMessage());
        }
    }

    @Test
    void testSingleStackMethod() {
        Request request = stack.singleStack(apiKey).request();
        Assertions.assertEquals("GET", request.method());
    }

    @Test
    void testSingleStackHeaders() {
        Request request = stack.singleStack(apiKey).request();
        Assertions.assertEquals(1, request.headers().size());
        Assertions.assertEquals(apiKey, request.headers("api_key").get(0));
    }

    @Test
    void testSingleStackHost() {
        Request request = stack.singleStack(apiKey).request();
        Assertions.assertEquals("api.contentstack.io", request.url().host());
    }

    @Test
    void testSingleStackPort() {
        Request request = stack.singleStack(apiKey).request();
        Assertions.assertEquals(443, request.url().port());
    }

    @Test
    void testSingleStackUrl() {
        Request request = stack.singleStack(apiKey).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks",
                request.url().toString());
    }

    @Test
    void testSingleStackPathSegment() {
        Request request = stack.singleStack(apiKey).request();
        Assertions.assertEquals(
                "/v3/stacks",
                request.url().encodedPath());
    }

    @Test
    void testSingleStackWithOrgUid() {
        String orgId = env.get("organizations_uid");
        Request request = stack.singleStack(apiKey, orgId).request();
        Assertions.assertEquals(
                2,
                request.headers().size());
    }

    @Test
    void testSingleStackWithHeaders() {
        String orgId = env.get("organizations_uid");
        Request request = stack.singleStack(apiKey, orgId).request();
        Set<String> headers = request.headers().names();
        Assertions.assertEquals(2, headers.size());
    }

    @Test
    void testSingleStackWithHeadersNames() {
        String orgId = env.get("organizations_uid");
        Request request = stack.singleStack(apiKey, orgId).request();
        String headerAPIKey = request.headers().name(0);
        String headerOrgKey = request.headers().name(1);
        Assertions.assertEquals("api_key", headerAPIKey);
        Assertions.assertEquals("organization_uid", headerOrgKey);
    }

    @Test
    void testSingleStackWithQueryParams() {
        String orgId = env.get("organizations_uid");
        HashMap<String, Boolean> queryParams = new HashMap<>();
        queryParams.put("include_collaborators", true);
        queryParams.put("include_stack_variables", true);
        queryParams.put("include_discrete_variables", true);
        queryParams.put("include_count", true);
        Request request = stack.singleStack(apiKey, orgId, queryParams).request();
        Assertions.assertEquals(
                "include_collaborators=true&include_discrete_variables=true&include_stack_variables=true&include_count=true",
                request.url().encodedQuery());
    }

    @Test
    void testSingleStackWithQueryIncludeCount() {
        String orgId = env.get("organizations_uid");
        HashMap<String, Boolean> queryParams = new HashMap<>();
        queryParams.put("include_count", true);
        Request request = stack.singleStack(apiKey, orgId, queryParams).request();
        Assertions.assertEquals(
                "include_count=true",
                request.url().encodedQuery());
    }

    @Test
    void testSingleStackWithQueryIncludeCountAndHeaders() {
        String orgId = env.get("organizations_uid");
        HashMap<String, Boolean> queryParams = new HashMap<>();
        queryParams.put("include_count", true);
        Request request = stack.singleStack(apiKey, orgId, queryParams).request();
        Assertions.assertEquals(
                "include_count=true",
                request.url().encodedQuery());
        Assertions.assertEquals(2, request.headers().size());
    }

//    @Test
//    void testAllStackMethod() {
//        String orgId = env.get("organizations_uid");
//        Request request = stack.allStacks().request();
//        Assertions.assertEquals("GET", request.method());
//    }


    @Test
    void testCreateStackMethod() {
        String orgId = env.get("organizations_uid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        Request request = stack.createStack(orgId, bodyString).request();
    }


    @Test
    void testCreateStackHeader() {
        String orgId = env.get("organizations_uid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        Request request = stack.createStack(orgId, bodyString).request();
        Assertions.assertEquals(orgId, request.headers().get("organization_uid"));
    }


    @Test
    void testCreateStackUrl() {
        String orgId = env.get("organizations_uid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        Request request = stack.createStack(orgId, bodyString).request();
        Assertions.assertEquals("https://api.contentstack.io/v3/stacks", request.url().toString());
    }

    @Test
    void testCreateStackRequestBodyCharset() {
        String orgId = env.get("organizations_uid");
        String bodyString = "{\n" +
                "  \"stack\": {\n" +
                "    \"name\": \"My New Stack\",\n" +
                "    \"description\": \"My new test stack\",\n" +
                "    \"master_locale\": \"en-us\"\n" +
                "  }\n" +
                "}";
        Request request = stack.createStack(orgId, bodyString).request();
        Assertions.assertEquals("application/json; charset=UTF-8", request.body().contentType().toString());
    }


    @Test
    void testUpdateStackMethod() {
        String bodyString = "{\n" +
                "\t\"stack\": {\n" +
                "\t\t\"name\": \"My New Stack\",\n" +
                "\t\t\"description\": \"My new test stack\"\n" +
                "\t}\n" +
                "}";
        Request request = stack.updateStack(apiKey, bodyString).request();
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
        Request request = stack.updateStack(apiKey, bodyString).request();
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
        Request request = stack.updateStack(apiKey, bodyString).request();
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
        Request request = stack.updateStack(apiKey, bodyString).request();
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
        Request request = stack.updateStack(apiKey, bodyString).request();
        Assertions.assertEquals(
                "application/json; charset=UTF-8",
                request.body().contentType().toString());
    }


    @Test
    void testTransferOwnership() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(apiKey, bodyString).request();
        Assertions.assertEquals(
                "POST",
                request.method());
    }

    @Test
    void testTransferOwnershipUrl() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(apiKey, bodyString).request();
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/stacks/transfer_ownership",
                request.url().toString());
    }

    @Test
    void testTransferOwnershipHeaders() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(apiKey, bodyString).request();
        Assertions.assertEquals(apiKey,
                request.headers().get("api_key"));
    }

    @Test
    void testTransferOwnershipRequestBody() {
        String bodyString = "{\n" +
                "\t\"transfer_to\": \"manager@example.com\"\n" +
                "}";
        Request request = stack.transferOwnership(apiKey, bodyString).request();
        Assertions.assertEquals("application/json; charset=UTF-8",
                request.body().contentType().toString());
    }


}
