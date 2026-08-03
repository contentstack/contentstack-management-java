package com.contentstack.cms;

import com.contentstack.cms.models.LoginDetails;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Dynamic Test Stack Lifecycle Manager (Phase 1 of dynamic-fixtures plan).
 *
 * <p>Mirrors the JS CMA SDK's {@code test/sanity-check/utility/testSetup.js}:
 * when {@code DYNAMIC_STACK=true} in the environment/.env, the test run
 * becomes fully self-contained:
 * <ol>
 *   <li>Logs in with EMAIL/PASSWORD to obtain an authtoken</li>
 *   <li>Creates a brand-new test stack (no pre-existing stack required)</li>
 *   <li>Creates a Management Token for that stack (broad scope, with fallback)</li>
 *   <li>Exposes the generated API key / token to {@link TestClient}</li>
 *   <li>On JVM/test-session end: deletes the stack (unless
 *       {@code DELETE_DYNAMIC_RESOURCES=false}, which preserves it for debugging)</li>
 * </ol>
 *
 * <p>Environment variables used (same names as the JS SDK sanity suite):
 * <ul>
 *   <li>{@code DYNAMIC_STACK}      – "true" enables dynamic mode (default: off)</li>
 *   <li>{@code EMAIL}, {@code PASSWORD} – login credentials (required in dynamic mode)</li>
 *   <li>{@code HOST}               – CMA host (default: api.contentstack.io)</li>
 *   <li>{@code ORGANIZATION}       – org UID for stack creation (required in dynamic mode)</li>
 *   <li>{@code DELETE_DYNAMIC_RESOURCES} – "false" preserves the stack after the run</li>
 * </ul>
 *
 * <p>This class is intentionally self-contained (does NOT reference
 * {@link TestClient}) so it can run during TestClient's static initialization
 * without circular dependency.
 */
public final class TestStackContext {

    private static final Dotenv env = Dotenv.load();

    // ---- resolved configuration -------------------------------------------------
    private static final String HOST = getEnv("HOST", "dev_host", "api.contentstack.io").trim();
    private static final String EMAIL = getEnv("EMAIL", "email", null);
    private static final String PASSWORD = getEnv("PASSWORD", "password", null);
    private static final String ORGANIZATION = getEnv("ORGANIZATION", "organizationUid", null);

    // ---- dynamic state ----------------------------------------------------------
    private static String authtoken;
    private static String stackApiKey;
    private static String stackUid;
    private static String stackName;
    private static String managementToken;
    private static String managementTokenUid;

    private static boolean setupAttempted = false;
    private static boolean stackCreated = false;
    private static boolean tornDown = false;

    private static final OkHttpClient http = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    private static final MediaType JSON_MEDIA = MediaType.parse("application/json; charset=utf-8");
    private static final JSONParser parser = new JSONParser();

    private TestStackContext() {
    }

    // =============================================================================
    // Public API
    // =============================================================================

    /**
     * @return true when DYNAMIC_STACK=true is set (env var or .env entry)
     */
    public static boolean isDynamicMode() {
        String flag = getEnv("DYNAMIC_STACK", "dynamicStack", "false");
        return "true".equalsIgnoreCase(flag.trim());
    }

    /**
     * Idempotent setup: login, create stack, create management token.
     * Safe to call from multiple places; only the first call does work.
     * Failures are logged and leave the context empty so callers can fall
     * back to static .env credentials.
     */
    public static synchronized void ensureSetup() {
        if (setupAttempted) {
            return;
        }
        setupAttempted = true;

        if (!isDynamicMode()) {
            return;
        }

        System.out.println("============================================================");
        System.out.println("[TestStackContext] DYNAMIC_STACK=true - dynamic setup starting");
        System.out.println("[TestStackContext] Host: " + HOST);
        System.out.println("[TestStackContext] Organization: " + ORGANIZATION);
        System.out.println("============================================================");

        if (EMAIL == null || PASSWORD == null || ORGANIZATION == null) {
            System.err.println("[TestStackContext] EMAIL, PASSWORD and ORGANIZATION are required for dynamic mode. Falling back to static credentials.");
            return;
        }

        try {
            login();
            createStack();
            createManagementToken(); // non-fatal on failure
            // Delete the stack even if the run is interrupted (Ctrl+C, surefire kill)
            Runtime.getRuntime().addShutdownHook(new Thread(TestStackContext::teardown, "dynamic-stack-teardown"));

            // Phase 2: seed baseline fixture data (SEED_FIXTURES=false to skip)
            String seedFlag = getEnv("SEED_FIXTURES", "seedFixtures", "true");
            if (!"false".equalsIgnoreCase(seedFlag.trim())) {
                new FixtureSeeder(HOST, stackApiKey, authtoken).seedAll();
            }

            System.out.println("============================================================");
            System.out.println("[TestStackContext] Dynamic setup complete");
            System.out.println("[TestStackContext]   Stack: " + stackName + " (" + stackApiKey + ")");
            System.out.println("[TestStackContext]   Management token: " + (managementToken != null ? "created" : "NOT created (tests fall back to authtoken)"));
            System.out.println("============================================================");
        } catch (Exception e) {
            System.err.println("[TestStackContext] Dynamic setup FAILED: " + e.getMessage());
            System.err.println("[TestStackContext] Falling back to static .env credentials.");
        }
    }

    /**
     * Idempotent teardown: deletes the dynamic stack unless
     * DELETE_DYNAMIC_RESOURCES=false. Invoked by the JUnit
     * LauncherSessionListener and a JVM shutdown hook (whichever fires first).
     */
    public static synchronized void teardown() {
        if (tornDown || !stackCreated) {
            return;
        }
        tornDown = true;

        String deleteFlag = getEnv("DELETE_DYNAMIC_RESOURCES", "deleteDynamicResources", "true");
        if ("false".equalsIgnoreCase(deleteFlag.trim())) {
            System.out.println("[TestStackContext] DELETE_DYNAMIC_RESOURCES=false - preserving resources for debugging:");
            System.out.println("[TestStackContext]   Stack: " + stackName);
            System.out.println("[TestStackContext]   API key: " + stackApiKey);
            System.out.println("[TestStackContext]   Management token: " + managementToken);
            System.out.println("[TestStackContext]   Remember to delete it manually when done!");
            writeStatusFile("preserved " + stackName + " " + stackApiKey);
            return;
        }

        try {
            Request request = new Request.Builder()
                    .url("https://" + HOST + "/v3/stacks")
                    .header("api_key", stackApiKey)
                    .header("authtoken", authtoken)
                    .delete()
                    .build();
            try (Response response = http.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    System.out.println("[TestStackContext] Deleted test stack: " + stackName);
                    writeStatusFile("deleted " + stackName);
                } else {
                    System.err.println("[TestStackContext] Stack deletion failed with " + response.code()
                            + " - delete manually: " + stackName + " (" + stackApiKey + ")");
                    writeStatusFile("delete-failed " + stackName + " " + stackApiKey);
                }
            }
        } catch (Exception e) {
            System.err.println("[TestStackContext] Stack deletion error: " + e.getMessage()
                    + " - delete manually: " + stackName + " (" + stackApiKey + ")");
            writeStatusFile("delete-failed " + stackName + " " + stackApiKey);
        }
    }

    /**
     * Writes the teardown outcome to a file in the working directory. The
     * shutdown hook's stdout doesn't reliably reach the surefire/maven log
     * (observed both locally and on GoCD), so pipelines read this file to
     * report whether the dynamic stack was cleaned up.
     */
    private static void writeStatusFile(String status) {
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get("dynamic-stack-status.txt"),
                    (status + System.lineSeparator()).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            // best effort only
        }
    }

    public static String getAuthtoken() {
        return authtoken;
    }

    public static String getStackApiKey() {
        return stackApiKey;
    }

    public static String getStackUid() {
        return stackUid;
    }

    public static String getStackName() {
        return stackName;
    }

    public static String getManagementToken() {
        return managementToken;
    }

    public static String getManagementTokenUid() {
        return managementTokenUid;
    }

    /** @return true once the dynamic stack has been created successfully */
    public static boolean isStackCreated() {
        return stackCreated;
    }

    // =============================================================================
    // Setup steps
    // =============================================================================

    private static void login() throws Exception {
        System.out.println("[TestStackContext] Logging in as " + EMAIL + " ...");
        Contentstack loginClient = new Contentstack.Builder().setHost(HOST).build();
        retrofit2.Response<LoginDetails> response = loginClient.login(EMAIL, PASSWORD);
        if (!response.isSuccessful() || response.body() == null
                || response.body().user == null || response.body().user.authtoken == null) {
            String error = response.errorBody() != null ? response.errorBody().string() : "unknown";
            throw new IllegalStateException("Login failed (" + response.code() + "): " + error);
        }
        authtoken = response.body().user.authtoken;
        System.out.println("[TestStackContext] Login successful");
    }

    @SuppressWarnings("unchecked")
    private static void createStack() throws Exception {
        stackName = "SDK_Test_Java_" + shortId();
        System.out.println("[TestStackContext] Creating test stack: " + stackName + " ...");

        JSONObject stack = new JSONObject();
        stack.put("name", stackName);
        stack.put("description", "Automated Java CMA SDK test stack");
        stack.put("master_locale", "en-us");
        JSONObject body = new JSONObject();
        body.put("stack", stack);

        Request request = new Request.Builder()
                .url("https://" + HOST + "/v3/stacks")
                .header("authtoken", authtoken)
                .header("organization_uid", ORGANIZATION)
                .post(RequestBody.create(body.toJSONString(), JSON_MEDIA))
                .build();

        try (Response response = http.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";
            if (!response.isSuccessful()) {
                throw new IllegalStateException("Stack creation failed (" + response.code() + "): " + responseBody);
            }
            JSONObject json = (JSONObject) parser.parse(responseBody);
            JSONObject stackObj = (JSONObject) json.get("stack");
            stackApiKey = (String) stackObj.get("api_key");
            stackUid = (String) stackObj.get("uid");
            stackCreated = true;
        }

        System.out.println("[TestStackContext] Created stack " + stackName + " (api_key: " + stackApiKey + ")");
        // Stack provisioning is asynchronous (branch creation etc.) - same wait as JS suite
        System.out.println("[TestStackContext] Waiting 5s for stack provisioning ...");
        Thread.sleep(5000);
    }

    /**
     * Creates a management token. Tries a broad scope first (covers taxonomy,
     * global fields, releases etc. used by the Java tests); if the API rejects
     * it, falls back to the minimal scope proven by the JS sanity suite.
     * Failure is non-fatal: tests can still run with the authtoken.
     */
    private static void createManagementToken() {
        String tokenName = "SDK_Test_Java_Token_" + shortId();
        // Empirically verified valid scope modules (probe on dev11, 2026-08-04).
        // NOT valid: taxonomy, delivery_token, audit_log, publish_queue, stack.
        // Note: branch + branch_alias scopes are MANDATORY on branches-enabled orgs
        // (added below); taxonomy APIs cannot be scoped into a management token at
        // all - taxonomy tests must use the authtoken instead.
        String[] broadModules = {"content_type", "entry", "asset", "environment", "locale",
                "global_field", "webhook", "workflow", "release", "label", "extension", "role"};
        String[] minimalModules = {"content_type", "entry", "asset", "environment", "locale"};

        if (createManagementTokenWithScope(tokenName, broadModules)) {
            return;
        }
        System.out.println("[TestStackContext] Broad-scope token rejected; retrying with minimal scope ...");
        sleepQuietly(5000);
        createManagementTokenWithScope(tokenName + "_min", minimalModules);
    }

    @SuppressWarnings("unchecked")
    private static boolean createManagementTokenWithScope(String tokenName, String[] modules) {
        try {
            JSONArray scope = new JSONArray();
            for (String module : modules) {
                JSONObject acl = new JSONObject();
                acl.put("read", true);
                acl.put("write", true);
                JSONObject entry = new JSONObject();
                entry.put("module", module);
                entry.put("acl", acl);
                scope.add(entry);
            }
            // Branch scopes (read-only) - required on branches-enabled organizations
            JSONObject branchAcl = new JSONObject();
            branchAcl.put("read", true);
            JSONObject branchScope = new JSONObject();
            branchScope.put("module", "branch");
            JSONArray branches = new JSONArray();
            branches.add("main");
            branchScope.put("branches", branches);
            branchScope.put("acl", branchAcl);
            scope.add(branchScope);
            JSONObject branchAliasScope = new JSONObject();
            branchAliasScope.put("module", "branch_alias");
            branchAliasScope.put("branch_aliases", new JSONArray());
            branchAliasScope.put("acl", branchAcl);
            scope.add(branchAliasScope);

            JSONObject token = new JSONObject();
            token.put("name", tokenName);
            token.put("description", "Auto-generated Java SDK test token");
            token.put("scope", scope);
            token.put("expires_on", isoDatePlusDays(30));
            JSONObject body = new JSONObject();
            body.put("token", token);

            Request request = new Request.Builder()
                    .url("https://" + HOST + "/v3/stacks/management_tokens")
                    .header("api_key", stackApiKey)
                    .header("authtoken", authtoken)
                    .post(RequestBody.create(body.toJSONString(), JSON_MEDIA))
                    .build();

            try (Response response = http.newCall(request).execute()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                if (!response.isSuccessful()) {
                    System.err.println("[TestStackContext] Management token creation failed ("
                            + response.code() + "): " + responseBody);
                    return false;
                }
                JSONObject json = (JSONObject) parser.parse(responseBody);
                JSONObject tokenObj = (JSONObject) json.get("token");
                managementToken = (String) tokenObj.get("token");
                managementTokenUid = (String) tokenObj.get("uid");
                System.out.println("[TestStackContext] Created management token: " + tokenName);
                waitForTokenReadiness();
                return true;
            }
        } catch (Exception e) {
            System.err.println("[TestStackContext] Management token creation error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Freshly created management tokens are propagated asynchronously on some
     * environments (observed on dev11: intermittent 412s when the token is used
     * immediately). Polls a cheap read with {@code authorization: <token>} until
     * it succeeds, up to ~30s. Non-fatal on timeout - just logs loudly.
     */
    private static void waitForTokenReadiness() {
        final int maxAttempts = 10;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                Request request = new Request.Builder()
                        .url("https://" + HOST + "/v3/environments")
                        .header("api_key", stackApiKey)
                        .header("authorization", managementToken)
                        .get()
                        .build();
                try (Response response = http.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        System.out.println("[TestStackContext] Management token verified usable (attempt " + attempt + ")");
                        return;
                    }
                    System.out.println("[TestStackContext] Token not ready yet (attempt " + attempt
                            + ", HTTP " + response.code() + ") - waiting 3s ...");
                }
            } catch (Exception e) {
                System.out.println("[TestStackContext] Token readiness check error (attempt " + attempt + "): " + e.getMessage());
            }
            sleepQuietly(3000);
        }
        System.err.println("[TestStackContext] WARNING: management token never validated after " + maxAttempts
                + " attempts - tests using it may see 401/412 failures");
    }

    // =============================================================================
    // Helpers
    // =============================================================================

    private static String getEnv(String primaryKey, String legacyKey, String defaultValue) {
        // Real environment variables win over .env entries (matches CI usage)
        String value = System.getenv(primaryKey);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        value = env.get(primaryKey);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        value = env.get(legacyKey);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        return defaultValue;
    }

    private static String shortId() {
        final String charset = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            id.append(charset.charAt(random.nextInt(charset.length())));
        }
        return id.toString();
    }

    private static String isoDatePlusDays(int days) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_MONTH, days);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(new Date(calendar.getTimeInMillis()));
    }

    private static void sleepQuietly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
