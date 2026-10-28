package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Live checks for {@code publish_config.environments} on branch create (DX-31564).
 * <p>
 * Needs a real stack in {@code .env}; without credentials the whole class is skipped.
 * The environment names {@value #ENV_DEVELOPMENT} and {@value #ENV_PRODUCTION} must exist
 * on the test stack. The source branch {@value #SOURCE} must exist too.
 */
@Tag("api")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BranchAPITest {

    static final String ENV_DEVELOPMENT = "development";
    static final String ENV_PRODUCTION = "production";
    static final String UNKNOWN_ENV = "no_such_env_dx31564";
    static final String SOURCE = "main";

    /**
     * Unique per run, so reruns on the same stack do not collide. Branch UIDs allow at most
     * 15 characters, lowercase letters and digits only: "b" plus the last 5 base-36 digits of
     * the current time, then one letter per UID below (7 characters in total).
     */
    private static final String RUN = runId();
    private static final String UID_TWO = RUN + "a";
    private static final String UID_EMPTY = RUN + "b";
    private static final String UID_UNKNOWN = RUN + "c";
    private static final String UID_MIXED = RUN + "d";

    private static String runId() {
        String time = Long.toString(System.currentTimeMillis(), 36);
        return "b" + time.substring(time.length() - 5);
    }

    /** Branches this class created, deleted in {@link #cleanUp()}. */
    private static final List<String> CREATED = new ArrayList<>();

    private static Stack stack;

    @BeforeAll
    static void setup() {
        try {
            stack = TestClient.getStack();
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            // TestClient loads .env in its static initializer; without it the class cannot load.
            Assumptions.assumeTrue(false, "Skipping: TestClient could not load .env");
        }
        Assumptions.assumeFalse(TestClient.isUsingDefaultStackCredentials(),
                "Skipping: stack credentials not set in .env");
    }

    @AfterAll
    static void cleanUp() {
        for (String uid : CREATED) {
            try {
                stack.branch(uid).delete().execute();
            } catch (IOException e) {
                System.out.println("Cleanup failed for branch " + uid + ": " + e.getLocalizedMessage());
            }
        }
    }

    @Test
    @Order(1)
    void createWithTwoEnvironmentNames() throws IOException {
        Response<ResponseBody> response = stack.branch()
                .create(body(UID_TWO, Arrays.asList(ENV_DEVELOPMENT, ENV_PRODUCTION))).execute();
        CREATED.add(UID_TWO);
        assertTrue(response.isSuccessful(), "create with two environment names should succeed");
    }

    @Test
    @Order(2)
    void fetchBranchCarriesTwoEnvironmentNames() throws Exception {
        Response<ResponseBody> response = stack.branch(UID_TWO).fetch().execute();
        assertTrue(response.isSuccessful(), "fetch of the created branch should succeed");
        assertEquals(new HashSet<>(Arrays.asList(ENV_DEVELOPMENT, ENV_PRODUCTION)),
                new HashSet<>(environmentsOf(response)));
    }

    @Test
    @Order(3)
    void createWithEmptyListClonesNoEnvironments() throws Exception {
        Response<ResponseBody> response = stack.branch()
                .create(body(UID_EMPTY, Collections.<String>emptyList())).execute();
        CREATED.add(UID_EMPTY);
        assertTrue(response.isSuccessful(), "create with an empty list should succeed");

        Response<ResponseBody> fetched = stack.branch(UID_EMPTY).fetch().execute();
        assertTrue(fetched.isSuccessful(), "fetch of the created branch should succeed");
        assertTrue(environmentsOf(fetched).isEmpty(), "an empty list must come back empty");
    }

    @Test
    @Order(4)
    void createWithUnknownNameIsRejectedAndCreatesNoBranch() throws Exception {
        Response<ResponseBody> response = stack.branch()
                .create(body(UID_UNKNOWN, Collections.singletonList(UNKNOWN_ENV))).execute();
        CREATED.add(UID_UNKNOWN);
        assertEquals(422, response.code(), "an unknown environment name should give the API's 422");
        assertNotNull(response.errorBody(), "the API's error body should be present");
        assertTrue(response.errorBody().string().contains("publish_config.environments"),
                "the 422 body should name publish_config.environments");

        Response<ResponseBody> fetched = stack.branch(UID_UNKNOWN).fetch().execute();
        assertFalse(fetched.isSuccessful(), "no branch should exist after a rejected create");
    }

    @Test
    @Order(5)
    void createWithMixedNamesIsRejectedAndCreatesNoBranch() throws Exception {
        Response<ResponseBody> response = stack.branch()
                .create(body(UID_MIXED, Arrays.asList(ENV_DEVELOPMENT, UNKNOWN_ENV))).execute();
        CREATED.add(UID_MIXED);
        assertEquals(422, response.code(), "a mix with an unknown name should give the API's 422");
        assertNotNull(response.errorBody(), "the API's error body should be present");
        assertTrue(response.errorBody().string().contains("publish_config.environments"),
                "the 422 body should name publish_config.environments");

        Response<ResponseBody> fetched = stack.branch(UID_MIXED).fetch().execute();
        assertFalse(fetched.isSuccessful(), "no branch should exist after a rejected create");
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

    /** Reads {@code branch.publish_config.environments} from a fetch response. */
    private static List<String> environmentsOf(Response<ResponseBody> response) throws Exception {
        JSONObject json = (JSONObject) new JSONParser().parse(response.body().string());
        JSONObject branch = (JSONObject) json.get("branch");
        assertNotNull(branch, "the fetch response should carry a 'branch' object");
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
