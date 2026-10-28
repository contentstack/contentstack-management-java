package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import com.contentstack.cms.models.LoginDetails;

import okhttp3.ResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Live checks for {@code publish_config.environments} on branch create (DX-31564), run as a real user.
 * <p>
 * The class logs in with {@code EMAIL} / {@code PASSWORD} and uses the stack's {@code apiKey} header only;
 * it does not send the management token. Before the tests it creates two environments whose names are
 * unique to the run, and after them it deletes every branch it created (force) and those environments,
 * then logs out. The stack ends as it started.
 * <p>
 * Needs {@code EMAIL}, {@code PASSWORD} and {@code apiKey} in {@code .env} (or the environment). When any of
 * them is missing the whole class is skipped with an "not run" message, which is not a pass.
 * <p>
 * Branch creation is asynchronous: after each successful create the test polls the branch until fetch
 * succeeds. A rejected create leaves no branch, which the API reports as 422 with error_code 905 (the same
 * answer as a branch that does not exist).
 */
@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BranchAPITest {

    static final String SOURCE = "main";
    static final String UNKNOWN_ENV = "no_such_env_dx31579";

    /** Base-36 time stamp, 5 characters, shared by the run's branch UIDs and environment names. */
    private static final String TIME = runTime();

    /**
     * Unique per run, so reruns on the same stack do not collide. Branch UIDs allow at most 15 characters,
     * lowercase letters and digits only: "b" plus {@link #TIME}, then one letter per UID below (7 characters).
     */
    private static final String RUN = "b" + TIME;
    private static final String UID_TWO = RUN + "a";
    private static final String UID_EMPTY = RUN + "b";
    private static final String UID_UNKNOWN = RUN + "c";
    private static final String UID_MIXED = RUN + "d";
    private static final String UID_NO_PUBLISH_CONFIG = RUN + "e";
    private static final String UID_CASE_VARIANT = RUN + "f";

    /** Environments this class creates; names unique to the run, deleted in {@link #cleanUp()}. */
    static final String ENV_ONE = "dxenv1" + TIME;
    static final String ENV_TWO = "dxenv2" + TIME;

    /** Creation is asynchronous: fetch answers 422 with this error_code until the branch is ready. */
    private static final long PENDING_ERROR_CODE = 905L;
    private static final long WAIT_MS = 60_000L;
    private static final long POLL_MS = 2_000L;

    /** Branches this class created (successful create only), deleted in {@link #cleanUp()}. */
    private static final List<String> CREATED_BRANCHES = new ArrayList<>();
    /** Environments this class created, deleted in {@link #cleanUp()}. */
    private static final List<String> CREATED_ENVIRONMENTS = new ArrayList<>();

    private static Contentstack client;
    private static Stack stack;
    private static boolean loggedIn;

    private static String runTime() {
        String time = Long.toString(System.currentTimeMillis(), 36);
        return time.substring(time.length() - 5);
    }

    @BeforeAll
    static void setup() throws Exception {
        String email;
        String password;
        boolean apiKeyConfigured;
        try {
            email = TestClient.EMAIL;
            password = TestClient.PASSWORD;
            apiKeyConfigured = TestClient.isApiKeyConfigured();
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            // TestClient loads .env in its static initializer; without it the class cannot load.
            Assumptions.assumeTrue(false, "not run: TestClient could not load .env");
            return;
        }
        Assumptions.assumeTrue(isSet(email), "not run: EMAIL is not set in .env");
        Assumptions.assumeTrue(isSet(password), "not run: PASSWORD is not set in .env");
        Assumptions.assumeTrue(apiKeyConfigured, "not run: apiKey is not set in .env");

        client = new Contentstack.Builder().setHost(TestClient.DEV_HOST).build();
        Response<LoginDetails> login = client.login(email, password);
        assertTrue(login.isSuccessful(), "login with EMAIL / PASSWORD should succeed");
        loggedIn = true;

        // Api key header only. stack(String) would treat a key that does not start with "blt" as a branch.
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, TestClient.API_KEY);
        stack = client.stack(headers);

        for (String name : Arrays.asList(ENV_ONE, ENV_TWO)) {
            Response<ResponseBody> created = stack.environment().create(environmentBody(name)).execute();
            if (created.isSuccessful()) {
                CREATED_ENVIRONMENTS.add(name);
            }
            assertTrue(created.isSuccessful(), "creating environment " + name + " should succeed (HTTP "
                    + created.code() + ")");
        }
    }

    @AfterAll
    static void cleanUp() {
        if (stack != null) {
            for (String uid : CREATED_BRANCHES) {
                try {
                    Response<ResponseBody> deleted = stack.branch(uid).addParam("force", true).delete().execute();
                    if (!deleted.isSuccessful()) {
                        System.out.println("Cleanup: branch " + uid + " was not deleted (HTTP " + deleted.code() + ")");
                    }
                } catch (IOException e) {
                    System.out.println("Cleanup failed for branch " + uid + ": " + e.getLocalizedMessage());
                }
            }
            for (String name : CREATED_ENVIRONMENTS) {
                try {
                    Response<ResponseBody> deleted = stack.environment(name).delete().execute();
                    if (!deleted.isSuccessful()) {
                        System.out.println("Cleanup: environment " + name + " was not deleted (HTTP "
                                + deleted.code() + ")");
                    }
                } catch (IOException e) {
                    System.out.println("Cleanup failed for environment " + name + ": " + e.getLocalizedMessage());
                }
            }
        }
        if (client != null && loggedIn) {
            try {
                Response<ResponseBody> out = client.user().logout().execute();
                if (!out.isSuccessful()) {
                    System.out.println("Cleanup: logout was not successful (HTTP " + out.code() + ")");
                }
            } catch (IOException e) {
                System.out.println("Cleanup: logout failed: " + e.getLocalizedMessage());
            }
        }
    }

    @Test
    @Order(1)
    void createWithTwoEnvironmentNames() throws Exception {
        Response<ResponseBody> response = createBranch(UID_TWO, body(UID_TWO, Arrays.asList(ENV_ONE, ENV_TWO)));
        assertTrue(response.isSuccessful(), "create with two environment names should succeed");
        assertEquals(UID_TWO, branchUidIn(response.body().string()),
                "the create response should carry the branch uid");
        awaitBranch(UID_TWO);
    }

    @Test
    @Order(2)
    void fetchBranchCarriesTwoEnvironmentNames() throws Exception {
        Response<ResponseBody> response = awaitBranch(UID_TWO);
        assertEquals(new HashSet<>(Arrays.asList(ENV_ONE, ENV_TWO)),
                new HashSet<>(environmentsOf(response)));
    }

    @Test
    @Order(3)
    void createWithEmptyListClonesNoEnvironments() throws Exception {
        Response<ResponseBody> response = createBranch(UID_EMPTY,
                body(UID_EMPTY, Collections.<String>emptyList()));
        assertTrue(response.isSuccessful(), "create with an empty list should succeed");
        assertEquals(UID_EMPTY, branchUidIn(response.body().string()),
                "the create response should carry the branch uid");

        Response<ResponseBody> fetched = awaitBranch(UID_EMPTY);
        assertTrue(environmentsOf(fetched).isEmpty(), "an empty list must come back empty");
    }

    @Test
    @Order(4)
    void createWithUnknownNameIsRejectedAndCreatesNoBranch() throws Exception {
        Response<ResponseBody> response = stack.branch()
                .create(body(UID_UNKNOWN, Collections.singletonList(UNKNOWN_ENV))).execute();
        assertEquals(422, response.code(), "an unknown environment name should give the API's 422");
        String error = errorBodyOf(response);
        assertTrue(error.contains("publish_config.environments"),
                "the 422 body should name publish_config.environments");

        assertNoBranch(UID_UNKNOWN);
    }

    @Test
    @Order(5)
    void createWithMixedNamesIsRejectedAndCreatesNoBranch() throws Exception {
        Response<ResponseBody> response = stack.branch()
                .create(body(UID_MIXED, Arrays.asList(ENV_ONE, UNKNOWN_ENV))).execute();
        assertEquals(422, response.code(), "a mix with an unknown name should give the API's 422");
        String error = errorBodyOf(response);
        assertTrue(error.contains("publish_config.environments"),
                "the 422 body should name publish_config.environments");

        assertNoBranch(UID_MIXED);
    }

    @Test
    @Order(6)
    void createWithoutPublishConfigCreatesBranchWithoutEnvironments() throws Exception {
        Response<ResponseBody> response = createBranch(UID_NO_PUBLISH_CONFIG, bodyWithoutPublishConfig(UID_NO_PUBLISH_CONFIG));
        assertTrue(response.isSuccessful(), "create without a publish_config list should succeed");
        assertEquals(UID_NO_PUBLISH_CONFIG, branchUidIn(response.body().string()),
                "the create response should carry the branch uid");

        Response<ResponseBody> fetched = awaitBranch(UID_NO_PUBLISH_CONFIG);
        JSONObject branch = branchOf(fetched.body().string());
        Object publishConfig = branch.get("publish_config");
        if (publishConfig != null) {
            assertNull(((JSONObject) publishConfig).get("environments"),
                    "a branch created without a list should carry no environments");
        }
    }

    @Test
    @Order(7)
    void createWithEnvironmentNameInOtherCaseIsRejectedAndCreatesNoBranch() throws Exception {
        String variant = ENV_ONE.toUpperCase(Locale.ROOT);
        assertNotEquals(ENV_ONE, variant, "the case variant must differ from the real environment name");

        Response<ResponseBody> response = stack.branch()
                .create(body(UID_CASE_VARIANT, Collections.singletonList(variant))).execute();
        assertEquals(422, response.code(), "an environment name in a different case should give the API's 422");
        String error = errorBodyOf(response);
        assertTrue(error.contains("publish_config.environments"),
                "the 422 body should name publish_config.environments");

        assertNoBranch(UID_CASE_VARIANT);
    }

    /**
     * Creates a branch and records its uid for cleanup only when the API accepted it.
     */
    private static Response<ResponseBody> createBranch(String uid, JSONObject body) throws IOException {
        Response<ResponseBody> response = stack.branch().create(body).execute();
        if (response.isSuccessful()) {
            CREATED_BRANCHES.add(uid);
        }
        return response;
    }

    /**
     * Polls fetch until the branch is ready. Only the pending answer (422 with error_code 905) is retried;
     * any other failure, or running past {@link #WAIT_MS}, fails the test with a message.
     */
    private static Response<ResponseBody> awaitBranch(String uid) throws Exception {
        long deadline = System.currentTimeMillis() + WAIT_MS;
        while (true) {
            Response<ResponseBody> fetched = stack.branch(uid).fetch().execute();
            if (fetched.isSuccessful()) {
                return fetched;
            }
            String error = errorBodyOf(fetched);
            boolean pending = fetched.code() == 422 && errorCodeOf(error) == PENDING_ERROR_CODE;
            if (!pending) {
                fail("fetch of branch " + uid + " failed with HTTP " + fetched.code() + ": " + error);
            }
            if (System.currentTimeMillis() >= deadline) {
                fail("branch " + uid + " was not ready within " + (WAIT_MS / 1000)
                        + "s (still HTTP 422, error_code " + PENDING_ERROR_CODE + ")");
            }
            Thread.sleep(POLL_MS);
        }
    }

    /**
     * A rejected create leaves no branch: fetch answers 422 with error_code 905, the same as a branch that
     * does not exist.
     */
    private static void assertNoBranch(String uid) throws IOException {
        Response<ResponseBody> fetched = stack.branch(uid).fetch().execute();
        String error = errorBodyOf(fetched);
        assertEquals(422, fetched.code(), "no branch should exist after a rejected create (fetch of " + uid + ")");
        assertEquals(PENDING_ERROR_CODE, errorCodeOf(error),
                "a missing branch should answer error_code 905 after a rejected create");
    }

    private static boolean isSet(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /** Reads an error body once; empty when the response has none. */
    private static String errorBodyOf(Response<ResponseBody> response) throws IOException {
        return response.errorBody() == null ? "" : response.errorBody().string();
    }

    /** The error_code of a Contentstack error body, or -1 when it is absent or not JSON. */
    private static long errorCodeOf(String body) {
        try {
            Object json = new JSONParser().parse(body);
            if (json instanceof JSONObject) {
                Object code = ((JSONObject) json).get("error_code");
                if (code instanceof Number) {
                    return ((Number) code).longValue();
                }
            }
        } catch (ParseException e) {
            // not JSON: fall through
        }
        return -1L;
    }

    private static JSONObject branchOf(String body) throws ParseException {
        JSONObject json = (JSONObject) new JSONParser().parse(body);
        JSONObject branch = (JSONObject) json.get("branch");
        assertNotNull(branch, "the response should carry a 'branch' object");
        return branch;
    }

    private static String branchUidIn(String body) throws ParseException {
        return (String) branchOf(body).get("uid");
    }

    /** Builds {@code {"branch": {uid, source, publish_config: {environments}}}}. */
    private static JSONObject body(String uid, List<String> environments) {
        JSONObject publishConfig = new JSONObject();
        publishConfig.put("environments", environments);
        JSONObject branch = new JSONObject();
        branch.put("uid", uid);
        branch.put("source", SOURCE);
        branch.put("publish_config", publishConfig);
        JSONObject root = new JSONObject();
        root.put("branch", branch);
        return root;
    }

    /** Builds {@code {"branch": {uid, source}}}: no publish_config at all. */
    private static JSONObject bodyWithoutPublishConfig(String uid) {
        JSONObject branch = new JSONObject();
        branch.put("uid", uid);
        branch.put("source", SOURCE);
        JSONObject root = new JSONObject();
        root.put("branch", branch);
        return root;
    }

    /** Builds the environment create body: one en-us URL, content deployed. */
    private static JSONObject environmentBody(String name) {
        JSONObject url = new JSONObject();
        url.put("locale", "en-us");
        url.put("url", "http://example.com");
        JSONArray urls = new JSONArray();
        urls.add(url);
        JSONObject environment = new JSONObject();
        environment.put("name", name);
        environment.put("servers", new JSONArray());
        environment.put("urls", urls);
        environment.put("deploy_content", true);
        JSONObject root = new JSONObject();
        root.put("environment", environment);
        return root;
    }

    /** Reads {@code branch.publish_config.environments} from a fetch response. */
    private static List<String> environmentsOf(Response<ResponseBody> response) throws Exception {
        JSONObject branch = branchOf(response.body().string());
        JSONObject publishConfig = (JSONObject) branch.get("publish_config");
        assertNotNull(publishConfig, "the branch should carry a 'publish_config' object");
        Object environments = publishConfig.get("environments");
        assertNotNull(environments, "publish_config should carry an 'environments' list");
        List<String> names = new ArrayList<>();
        for (Object name : (List<?>) environments) {
            names.add((String) name);
        }
        return names;
    }
}
