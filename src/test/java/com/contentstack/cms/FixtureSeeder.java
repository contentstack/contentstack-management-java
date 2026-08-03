package com.contentstack.cms;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Seeds the dynamically created test stack with baseline fixture data
 * (Phase 2 of the dynamic-fixtures plan — see docs/DYNAMIC-TEST-FIXTURES.md).
 *
 * <p>Fixtures live in {@code src/test/resources/fixtures/core/} and are
 * sanitized schema extracted from {@code docs/CDA-SDK-v10} (no real UIDs).
 * Seeding order follows module dependencies, mirroring the JS sanity suite:
 *
 * <pre>
 * locales → global fields (deepest nesting first) → content types
 *         → taxonomies (+terms) → environments → assets → entries
 * </pre>
 *
 * <p>All calls use the <b>authtoken</b> (org-admin rights), so seeding is
 * unaffected by management-token scope limitations. Entry fixtures may use
 * placeholders resolved against {@link TestDataRegistry} at seed time:
 * <ul>
 *   <li>{@code {{ref:<content_type_uid>:<index>}}} → Nth created entry UID</li>
 *   <li>{@code {{asset:<index>}}} → Nth uploaded asset UID</li>
 *   <li>{@code {{env:<name>}}} → environment UID</li>
 * </ul>
 *
 * <p>Failures are logged and non-fatal: an individual bad fixture must not
 * take down the run — affected tests will fail/skip on their own and point
 * at the gap.
 */
final class FixtureSeeder {

    private static final Pattern PLACEHOLDER = Pattern.compile("\\{\\{(ref|asset|env):([^:}]+)(?::(\\d+))?}}");
    private static final MediaType JSON_MEDIA = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient http = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    private final JSONParser parser = new JSONParser();

    private final String host;
    private final String apiKey;
    private final String authtoken;
    private final Path fixturesDir;

    private int created = 0;
    private int failed = 0;

    FixtureSeeder(String host, String apiKey, String authtoken) {
        this.host = host;
        this.apiKey = apiKey;
        this.authtoken = authtoken;
        this.fixturesDir = Paths.get("src", "test", "resources", "fixtures", "core");
    }

    /** Runs the full seeding sequence. Never throws. */
    void seedAll() {
        if (!Files.isDirectory(fixturesDir)) {
            System.err.println("[FixtureSeeder] fixtures dir not found: " + fixturesDir.toAbsolutePath());
            return;
        }
        long start = System.currentTimeMillis();
        System.out.println("[FixtureSeeder] Seeding fixtures from " + fixturesDir + " ...");
        seedLocales();
        seedGlobalFields();
        // Taxonomies BEFORE content types: CT schemas with taxonomy fields
        // validate the taxonomy uids at creation time (422 otherwise)
        seedTaxonomies();
        seedContentTypes();
        seedEnvironments();
        seedAssets();
        seedEntries();
        System.out.println("[FixtureSeeder] Done in " + (System.currentTimeMillis() - start) / 1000 + "s"
                + " (created=" + created + ", failed=" + failed + ") -> " + TestDataRegistry.summary());
    }

    // =============================================================================
    // Module seeding
    // =============================================================================

    @SuppressWarnings("unchecked")
    private void seedLocales() {
        JSONObject root = readJson(fixturesDir.resolve("locales.json"));
        if (root == null) return;
        for (Object o : (JSONArray) root.get("locales")) {
            JSONObject locale = (JSONObject) o;
            JSONObject body = new JSONObject();
            body.put("locale", locale);
            JSONObject res = post("/v3/locales", body, "locale " + locale.get("code"));
            if (res != null) {
                JSONObject created = (JSONObject) res.get("locale");
                TestDataRegistry.recordLocale((String) locale.get("code"),
                        created != null ? (String) created.get("uid") : null);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void seedGlobalFields() {
        for (Path file : listSorted("global_fields")) {
            JSONObject body = readJson(file);
            if (body == null) continue;
            String uid = (String) ((JSONObject) body.get("global_field")).get("uid");
            JSONObject res = post("/v3/global_fields", body, "global field " + uid);
            if (res != null) {
                TestDataRegistry.recordGlobalField(uid);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void seedContentTypes() {
        for (Path file : listSorted("content_types")) {
            JSONObject body = readJson(file);
            if (body == null) continue;
            String uid = (String) ((JSONObject) body.get("content_type")).get("uid");
            JSONObject res = post("/v3/content_types", body, "content type " + uid);
            if (res != null) {
                TestDataRegistry.recordContentType(uid);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void seedTaxonomies() {
        for (Path file : listSorted("taxonomies")) {
            JSONObject root = readJson(file);
            if (root == null) continue;
            JSONObject taxonomy = (JSONObject) root.get("taxonomy");
            String uid = (String) taxonomy.get("uid");
            JSONObject body = new JSONObject();
            body.put("taxonomy", taxonomy);
            JSONObject res = post("/v3/taxonomies", body, "taxonomy " + uid);
            if (res == null) continue;
            TestDataRegistry.recordTaxonomy(uid);
            JSONArray terms = (JSONArray) root.get("terms");
            if (terms == null) continue;
            for (Object o : terms) {
                JSONObject term = (JSONObject) o;
                JSONObject termBody = new JSONObject();
                termBody.put("term", term);
                post("/v3/taxonomies/" + uid + "/terms", termBody,
                        "term " + uid + "/" + term.get("uid"));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void seedEnvironments() {
        JSONObject root = readJson(fixturesDir.resolve("environments.json"));
        if (root == null) return;
        for (Object o : (JSONArray) root.get("environments")) {
            JSONObject environment = (JSONObject) o;
            JSONObject body = new JSONObject();
            body.put("environment", environment);
            String name = (String) environment.get("name");
            JSONObject res = post("/v3/environments", body, "environment " + name);
            if (res != null) {
                JSONObject created = (JSONObject) res.get("environment");
                TestDataRegistry.recordEnvironment(name,
                        created != null ? (String) created.get("uid") : null);
            }
        }
    }

    /** Uploads the repo's standard test asset so entries/tests have one available. */
    private void seedAssets() {
        File assetFile = new File("src/test/resources/asset.png");
        if (!assetFile.exists()) {
            System.err.println("[FixtureSeeder] asset file missing: " + assetFile.getPath());
            return;
        }
        try {
            RequestBody fileBody = RequestBody.create(assetFile, MediaType.parse("image/png"));
            MultipartBody multipart = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("asset[upload]", assetFile.getName(), fileBody)
                    .addFormDataPart("asset[title]", "Seeded test asset")
                    .addFormDataPart("asset[description]", "Uploaded by FixtureSeeder")
                    .build();
            Request request = new Request.Builder()
                    .url("https://" + host + "/v3/assets")
                    .header("api_key", apiKey)
                    .header("authtoken", authtoken)
                    .post(multipart)
                    .build();
            try (Response response = http.newCall(request).execute()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                if (response.isSuccessful()) {
                    JSONObject json = (JSONObject) parser.parse(responseBody);
                    JSONObject asset = (JSONObject) json.get("asset");
                    TestDataRegistry.recordAsset((String) asset.get("uid"));
                    created++;
                    System.out.println("[FixtureSeeder]   + asset " + asset.get("uid"));
                } else {
                    failed++;
                    System.err.println("[FixtureSeeder]   ! asset upload failed (" + response.code() + "): "
                            + truncate(responseBody));
                }
            }
        } catch (Exception e) {
            failed++;
            System.err.println("[FixtureSeeder]   ! asset upload error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void seedEntries() {
        for (Path file : listSorted("entries")) {
            JSONObject root = readJson(file);
            if (root == null) continue;
            String contentType = (String) root.get("content_type");
            for (Object o : (JSONArray) root.get("entries")) {
                JSONObject entry = (JSONObject) o;
                String resolved = resolvePlaceholders(entry.toJSONString());
                JSONObject body = new JSONObject();
                try {
                    body.put("entry", parser.parse(resolved));
                } catch (Exception e) {
                    failed++;
                    System.err.println("[FixtureSeeder]   ! bad entry fixture in " + file + ": " + e.getMessage());
                    continue;
                }
                JSONObject res = post("/v3/content_types/" + contentType + "/entries?locale=en-us",
                        body, "entry " + contentType + "/" + entry.get("title"));
                if (res != null) {
                    JSONObject created = (JSONObject) res.get("entry");
                    if (created != null) {
                        TestDataRegistry.recordEntry(contentType, (String) created.get("uid"));
                    }
                }
            }
        }
    }

    // =============================================================================
    // Helpers
    // =============================================================================

    /** Resolves {{ref:ct:n}}, {{asset:n}} and {{env:name}} placeholders. */
    private String resolvePlaceholders(String json) {
        Matcher m = PLACEHOLDER.matcher(json);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String kind = m.group(1);
            String key = m.group(2);
            String idx = m.group(3);
            String value = null;
            switch (kind) {
                case "ref":
                    value = TestDataRegistry.entryUid(key, idx != null ? Integer.parseInt(idx) : 0);
                    break;
                case "asset":
                    value = TestDataRegistry.assetUid(Integer.parseInt(key));
                    break;
                case "env":
                    value = TestDataRegistry.environmentUid(key);
                    break;
                default:
                    break;
            }
            m.appendReplacement(sb, Matcher.quoteReplacement(value != null ? value : m.group(0)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /** POST helper: returns parsed response JSON on 2xx, null otherwise (logged). */
    private JSONObject post(String path, JSONObject body, String label) {
        try {
            Request request = new Request.Builder()
                    .url("https://" + host + path)
                    .header("api_key", apiKey)
                    .header("authtoken", authtoken)
                    .post(RequestBody.create(body.toJSONString(), JSON_MEDIA))
                    .build();
            try (Response response = http.newCall(request).execute()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                if (response.isSuccessful()) {
                    created++;
                    System.out.println("[FixtureSeeder]   + " + label);
                    return (JSONObject) parser.parse(responseBody);
                }
                failed++;
                System.err.println("[FixtureSeeder]   ! " + label + " failed (" + response.code() + "): "
                        + truncate(responseBody));
                return null;
            }
        } catch (Exception e) {
            failed++;
            System.err.println("[FixtureSeeder]   ! " + label + " error: " + e.getMessage());
            return null;
        }
    }

    private JSONObject readJson(Path file) {
        try (FileReader reader = new FileReader(file.toFile())) {
            return (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            failed++;
            System.err.println("[FixtureSeeder]   ! cannot read " + file + ": " + e.getMessage());
            return null;
        }
    }

    /** Lists *.json files in a fixtures subdirectory, sorted by filename (numeric prefixes = order). */
    private List<Path> listSorted(String subdir) {
        Path dir = fixturesDir.resolve(subdir);
        if (!Files.isDirectory(dir)) {
            return new ArrayList<>();
        }
        try (Stream<Path> stream = Files.list(dir)) {
            return stream.filter(p -> p.toString().endsWith(".json"))
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("[FixtureSeeder]   ! cannot list " + dir + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static String truncate(String s) {
        return s.length() > 160 ? s.substring(0, 160) + "..." : s;
    }
}
