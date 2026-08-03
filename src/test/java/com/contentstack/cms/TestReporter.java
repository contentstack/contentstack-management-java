package com.contentstack.cms;

import com.contentstack.cms.core.AuthInterceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Custom test reporter - the Java equivalent of the JS CMA SDK sanity suite's
 * mochawesome report (request capture plugin + addContext).
 *
 * <p>For every test it records:
 * <ul>
 *   <li>result, duration and failure details (expected vs actual when available)</li>
 *   <li>every SDK HTTP call made during the test: method, URL, status,
 *       a copy-paste-ready cURL command (sensitive headers masked),
 *       response body (truncated) and the detected SDK method</li>
 * </ul>
 *
 * <p>Registered automatically for all Jupiter tests via
 * {@code META-INF/services/org.junit.jupiter.api.extension.Extension} +
 * {@code junit.jupiter.extensions.autodetection.enabled=true}
 * (see {@code src/test/resources/junit-platform.properties}) - no test-class
 * changes required. HTTP capture rides {@link AuthInterceptor.RequestObserver}.
 *
 * <p>The HTML report is written to {@code target/custom-report/index.html} by a
 * JVM shutdown hook (and refreshed at every launcher-session close), entirely
 * self-contained - no external assets.
 */
public class TestReporter implements BeforeEachCallback, TestWatcher {

    // =============================================================================
    // Captured data model
    // =============================================================================

    static final class CapturedCall {
        String method;
        String url;
        int status;
        long durationMs;
        String curl;
        String sdkMethod;
        String responseBody;
    }

    static final class TestRecord {
        String className;
        String displayName;
        String status; // PASSED / FAILED / SKIPPED / DISABLED
        long durationMs;
        String failureMessage;
        String expected;
        String actual;
        String skipReason;
        List<CapturedCall> calls = new ArrayList<>();
    }

    private static final List<TestRecord> RECORDS = Collections.synchronizedList(new ArrayList<>());
    private static final List<CapturedCall> CURRENT_CALLS = Collections.synchronizedList(new ArrayList<>());
    private static final ThreadLocal<Long> TEST_START = new ThreadLocal<>();
    private static final int MAX_BODY_CHARS = 4000;
    private static volatile boolean initialized = false;
    private static long suiteStartMillis = System.currentTimeMillis();

    public TestReporter() {
        initOnce();
    }

    private static synchronized void initOnce() {
        if (initialized) {
            return;
        }
        initialized = true;
        suiteStartMillis = System.currentTimeMillis();
        AuthInterceptor.setRequestObserver(TestReporter::captureCall);
        Runtime.getRuntime().addShutdownHook(new Thread(TestReporter::writeReport, "custom-test-report"));
    }

    // =============================================================================
    // HTTP capture (AuthInterceptor.RequestObserver)
    // =============================================================================

    private static void captureCall(Request request, Response response, long durationMs) {
        try {
            CapturedCall call = new CapturedCall();
            call.method = request.method();
            call.url = request.url().toString();
            call.status = response.code();
            call.durationMs = durationMs;
            call.curl = toCurl(request);
            call.sdkMethod = detectSdkMethod(request.method(), request.url().encodedPath());
            try {
                String body = response.peekBody(MAX_BODY_CHARS + 1).string();
                body = redactSecrets(body);
                call.responseBody = body.length() > MAX_BODY_CHARS
                        ? body.substring(0, MAX_BODY_CHARS) + "\n... (truncated)" : body;
            } catch (Exception e) {
                call.responseBody = "(body unavailable: " + e.getMessage() + ")";
            }
            CURRENT_CALLS.add(call);
        } catch (Exception ignored) {
            // never break requests for reporting
        }
    }

    private static String toCurl(Request request) {
        StringBuilder curl = new StringBuilder("curl -X ").append(request.method())
                .append(" '").append(request.url()).append("'");
        for (String name : request.headers().names()) {
            String value = request.header(name);
            if (value == null) continue;
            curl.append(" \\\n  -H '").append(name).append(": ").append(mask(name, value)).append("'");
        }
        if (request.body() != null && !request.body().isDuplex() && !request.body().isOneShot()) {
            try {
                long len = request.body().contentLength();
                if (len > 0 && len < 65536) {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    String body = redactSecrets(buffer.readString(StandardCharsets.UTF_8)).replace("'", "'\\''");
                    curl.append(" \\\n  -d '").append(body).append("'");
                }
            } catch (Exception ignored) {
                // multipart/file bodies etc. - skip body in curl
            }
        }
        return curl.toString();
    }

    /**
     * Redacts credential values from JSON payloads before they enter the report:
     * passwords, auth/session tokens, management/delivery token values, OAuth
     * secrets and TOTP secrets. The report is shipped as a CI artifact, so no
     * live secret may survive into it.
     */
    private static final Pattern SECRET_JSON_FIELDS = Pattern.compile(
            "(\"(?:password|authtoken|token|management_token|secret|mfaSecret|tfaToken|tfa_token|"
                    + "access_token|refresh_token|client_secret|api_secret)\"\\s*:\\s*\")([^\"]*)(\")",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern CS_TOKEN_PATTERN = Pattern.compile("\\bcs[a-f0-9]{16,}\\b");

    static String redactSecrets(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String redacted = SECRET_JSON_FIELDS.matcher(text).replaceAll("$1***REDACTED***$3");
        redacted = CS_TOKEN_PATTERN.matcher(redacted).replaceAll("cs***REDACTED***");
        return redacted;
    }

    private static String mask(String headerName, String value) {
        String lower = headerName.toLowerCase();
        if ((lower.equals("authtoken") || lower.equals("authorization") || lower.equals("access_token"))
                && value.length() > 12) {
            return value.substring(0, 6) + "..." + value.substring(value.length() - 4);
        }
        return value;
    }

    // =============================================================================
    // SDK method detection (ported from the JS suite's detectSdkMethod)
    // =============================================================================

    private static final Object[][] SDK_PATTERNS = {
            {"POST", "/user-session$", "client.login()"},
            {"DELETE", "/user-session$", "client.logout()"},
            {"GET", "/user$", "client.getUser()"},
            {"POST", "/stacks$", "client.stack().create()"},
            {"GET", "/stacks$", "stack.fetch()/find()"},
            {"DELETE", "/stacks$", "stack.delete()"},
            {"POST", "/stacks/management_tokens$", "stack.managementToken().create()"},
            {"GET", "/stacks/management_tokens$", "stack.managementToken().find()"},
            {"POST", "/stacks/delivery_tokens$", "stack.deliveryToken().create()"},
            {"GET", "/stacks/delivery_tokens$", "stack.deliveryToken().find()"},
            {"POST", "/content_types$", "stack.contentType().create()"},
            {"GET", "/content_types$", "stack.contentType().find()"},
            {"GET", "/content_types/[^/]+$", "stack.contentType(uid).fetch()"},
            {"PUT", "/content_types/[^/]+$", "stack.contentType(uid).update()"},
            {"DELETE", "/content_types/[^/]+$", "stack.contentType(uid).delete()"},
            {"POST", "/content_types/[^/]+/entries$", "contentType.entry().create()"},
            {"GET", "/content_types/[^/]+/entries$", "contentType.entry().find()"},
            {"GET", "/content_types/[^/]+/entries/[^/]+$", "contentType.entry(uid).fetch()"},
            {"PUT", "/content_types/[^/]+/entries/[^/]+$", "contentType.entry(uid).update()"},
            {"DELETE", "/content_types/[^/]+/entries/[^/]+$", "contentType.entry(uid).delete()"},
            {"POST", "/content_types/[^/]+/entries/[^/]+/publish$", "entry.publish()"},
            {"POST", "/assets$", "stack.asset().uploadAsset()"},
            {"GET", "/assets$", "stack.asset().find()"},
            {"GET", "/assets/[^/]+$", "stack.asset(uid).fetch()"},
            {"DELETE", "/assets/[^/]+$", "stack.asset(uid).delete()"},
            {"POST", "/assets/[^/]+/publish$", "asset.publish()"},
            {"POST", "/global_fields$", "stack.globalField().create()"},
            {"GET", "/global_fields$", "stack.globalField().find()"},
            {"GET", "/global_fields/[^/]+$", "stack.globalField(uid).fetch()"},
            {"POST", "/environments$", "stack.environment().create()"},
            {"GET", "/environments$", "stack.environment().find()"},
            {"GET", "/environments/[^/]+$", "stack.environment(name).fetch()"},
            {"POST", "/locales$", "stack.locale().create()"},
            {"GET", "/locales$", "stack.locale().find()"},
            {"POST", "/taxonomies$", "stack.taxonomy().create()"},
            {"GET", "/taxonomies$", "stack.taxonomy().find()"},
            {"GET", "/taxonomies/[^/]+$", "stack.taxonomy(uid).fetch()"},
            {"DELETE", "/taxonomies/[^/]+$", "stack.taxonomy(uid).delete()"},
            {"POST", "/taxonomies/[^/]+/terms$", "taxonomy.terms().create()"},
            {"GET", "/roles$", "stack.roles().find()"},
            {"POST", "/releases$", "stack.releases().create()"},
            {"GET", "/releases$", "stack.releases().find()"},
            {"GET", "/organizations.*", "client.organization()...."},
            {"GET", "/stacks/branches.*", "stack.branch()...."},
            {"POST", "/variant_groups$", "stack.variantGroup().create()"},
            {"GET", "/variant_groups$", "stack.variantGroup().find()"},
    };

    private static String detectSdkMethod(String method, String path) {
        String cleaned = path.replaceFirst("^/v\\d+", "");
        for (Object[] p : SDK_PATTERNS) {
            if (p[0].equals(method) && Pattern.compile((String) p[1]).matcher(cleaned).find()) {
                return (String) p[2];
            }
        }
        return method + " " + cleaned;
    }

    // =============================================================================
    // JUnit lifecycle
    // =============================================================================

    @Override
    public void beforeEach(ExtensionContext context) {
        CURRENT_CALLS.clear();
        TEST_START.set(System.currentTimeMillis());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        addRecord(context, "PASSED", null);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        addRecord(context, "FAILED", cause);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        TestRecord record = addRecord(context, "SKIPPED", null);
        record.skipReason = cause != null ? cause.getMessage() : "assumption failed";
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        TestRecord record = addRecord(context, "DISABLED", null);
        record.skipReason = reason.orElse("@Disabled");
    }

    private TestRecord addRecord(ExtensionContext context, String status, Throwable cause) {
        TestRecord record = new TestRecord();
        record.className = context.getTestClass().map(Class::getSimpleName).orElse("?");
        record.displayName = context.getDisplayName();
        record.status = status;
        Long start = TEST_START.get();
        record.durationMs = start != null ? System.currentTimeMillis() - start : 0;
        if (cause != null) {
            record.failureMessage = String.valueOf(cause.getMessage());
            if (cause instanceof org.opentest4j.AssertionFailedError) {
                org.opentest4j.AssertionFailedError afe = (org.opentest4j.AssertionFailedError) cause;
                if (afe.isExpectedDefined()) {
                    record.expected = String.valueOf(afe.getExpected().getStringRepresentation());
                }
                if (afe.isActualDefined()) {
                    record.actual = String.valueOf(afe.getActual().getStringRepresentation());
                }
            }
        }
        synchronized (CURRENT_CALLS) {
            record.calls.addAll(CURRENT_CALLS);
            CURRENT_CALLS.clear();
        }
        RECORDS.add(record);
        return record;
    }

    // =============================================================================
    // HTML report
    // =============================================================================

    /** Writes/refreshes the report. Safe to call multiple times; last call wins. */
    public static synchronized void writeReport() {
        List<TestRecord> snapshot;
        synchronized (RECORDS) {
            if (RECORDS.isEmpty()) {
                return;
            }
            snapshot = new ArrayList<>(RECORDS);
        }
        try {
            Path dir = Paths.get("target", "custom-report");
            Files.createDirectories(dir);
            Files.write(dir.resolve("index.html"), buildHtml(snapshot).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("[TestReporter] could not write report: " + e.getMessage());
        }
    }

    private static String buildHtml(List<TestRecord> records) {
        long passed = records.stream().filter(r -> "PASSED".equals(r.status)).count();
        long failed = records.stream().filter(r -> "FAILED".equals(r.status)).count();
        long skipped = records.stream().filter(r -> "SKIPPED".equals(r.status) || "DISABLED".equals(r.status)).count();
        long durationSec = (System.currentTimeMillis() - suiteStartMillis) / 1000;

        Map<String, List<TestRecord>> byClass = new LinkedHashMap<>();
        for (TestRecord r : records) {
            byClass.computeIfAbsent(r.className, k -> new ArrayList<>()).add(r);
        }

        StringBuilder h = new StringBuilder();
        h.append("<!DOCTYPE html><html><head><meta charset='utf-8'><title>Java CMA SDK Test Report</title><style>")
                .append("*{box-sizing:border-box}body{font-family:-apple-system,'Segoe UI',Roboto,Arial,sans-serif;margin:0;background:#f4f6f8;color:#1f2933}")
                .append("header{background:#0d1b2a;color:#fff;padding:18px 28px}h1{font-size:20px;margin:0 0 8px}")
                .append(".stats{display:flex;gap:14px;flex-wrap:wrap}.stat{background:#1b263b;border-radius:8px;padding:8px 16px;font-size:14px}")
                .append(".stat b{font-size:18px;display:block}.ok b{color:#5dd39e}.bad b{color:#ff6b6b}.skip b{color:#ffd166}")
                .append(".filters{padding:12px 28px;display:flex;gap:8px}.filters button{border:1px solid #cbd2d9;background:#fff;border-radius:6px;padding:6px 14px;cursor:pointer;font-size:13px}")
                .append(".filters button.active{background:#0d1b2a;color:#fff;border-color:#0d1b2a}")
                .append("main{padding:0 28px 40px}h2{font-size:16px;margin:26px 0 10px;color:#334e68}")
                .append("details.test{background:#fff;border:1px solid #e0e5ea;border-left:5px solid #5dd39e;border-radius:8px;margin:8px 0;overflow:hidden}")
                .append("details.test.FAILED{border-left-color:#ff6b6b}details.test.SKIPPED,details.test.DISABLED{border-left-color:#ffd166}")
                .append("details.test>summary{cursor:pointer;padding:11px 16px;display:flex;align-items:center;gap:10px;font-size:14px;list-style:none}")
                .append("details.test>summary::-webkit-details-marker{display:none}")
                .append(".badge{font-size:11px;font-weight:700;border-radius:10px;padding:2px 10px;color:#fff;background:#5dd39e;flex-shrink:0}")
                .append(".badge.FAILED{background:#ff6b6b}.badge.SKIPPED,.badge.DISABLED{background:#e0a800}")
                .append(".dur{margin-left:auto;color:#829ab1;font-size:12px;flex-shrink:0}")
                .append(".body{padding:4px 16px 16px;border-top:1px solid #eef1f4}")
                .append(".sect{margin:12px 0 4px;font-size:12px;font-weight:700;text-transform:uppercase;letter-spacing:.04em;color:#627d98}")
                .append("pre{background:#0d1b2a;color:#d7e3f4;border-radius:6px;padding:12px;font-size:12px;overflow-x:auto;white-space:pre-wrap;word-break:break-all;margin:6px 0}")
                .append(".ea{display:grid;grid-template-columns:1fr 1fr;gap:8px}.ea pre{margin:0}.ea .lbl{font-size:11px;color:#627d98;margin-bottom:2px}")
                .append(".call{border:1px solid #e0e5ea;border-radius:6px;margin:8px 0;padding:10px 12px;background:#fafbfc}")
                .append(".call .line{display:flex;gap:8px;align-items:center;font-size:13px;flex-wrap:wrap}")
                .append(".m{font-weight:700;color:#0d1b2a}.code2xx{color:#0f9d58;font-weight:700}.code4xx,.code5xx{color:#d93025;font-weight:700}")
                .append(".sdk{background:#e8f0fe;color:#1a56db;border-radius:4px;font-size:11px;padding:2px 8px;font-family:monospace}")
                .append(".fail-msg{background:#fdecea;border:1px solid #f5c6c1;color:#8a1f11;padding:10px 12px;border-radius:6px;font-size:13px;white-space:pre-wrap;word-break:break-word}")
                .append(".skip-msg{background:#fff8e1;border:1px solid #ffe082;color:#7a5c00;padding:10px 12px;border-radius:6px;font-size:13px}")
                .append("</style></head><body>");

        h.append("<header><h1>Java CMA SDK &mdash; API Test Report</h1><div class='stats'>")
                .append("<div class='stat'><b>").append(records.size()).append("</b>Total</div>")
                .append("<div class='stat ok'><b>").append(passed).append("</b>Passed</div>")
                .append("<div class='stat bad'><b>").append(failed).append("</b>Failed</div>")
                .append("<div class='stat skip'><b>").append(skipped).append("</b>Skipped</div>")
                .append("<div class='stat'><b>").append(durationSec).append("s</b>Duration</div>")
                .append("</div></header>");

        h.append("<div class='filters'>")
                .append("<button class='active' onclick=\"filt('all',this)\">All</button>")
                .append("<button onclick=\"filt('FAILED',this)\">Failed</button>")
                .append("<button onclick=\"filt('PASSED',this)\">Passed</button>")
                .append("<button onclick=\"filt('SKIPPED',this)\">Skipped</button>")
                .append("</div><main>");

        for (Map.Entry<String, List<TestRecord>> entry : byClass.entrySet()) {
            h.append("<h2>").append(esc(entry.getKey())).append("</h2>");
            for (TestRecord r : entry.getValue()) {
                boolean open = "FAILED".equals(r.status);
                h.append("<details class='test ").append(r.status).append("'").append(open ? " open" : "").append(">")
                        .append("<summary><span class='badge ").append(r.status).append("'>").append(r.status)
                        .append("</span><span>").append(esc(r.displayName)).append("</span>")
                        .append("<span class='dur'>").append(r.durationMs).append(" ms &middot; ")
                        .append(r.calls.size()).append(" call").append(r.calls.size() == 1 ? "" : "s")
                        .append("</span></summary><div class='body'>");

                if (r.failureMessage != null) {
                    h.append("<div class='sect'>Failure</div><div class='fail-msg'>").append(esc(r.failureMessage)).append("</div>");
                }
                if (r.expected != null || r.actual != null) {
                    h.append("<div class='sect'>Expected vs Actual</div><div class='ea'>")
                            .append("<div><div class='lbl'>Expected</div><pre>").append(esc(String.valueOf(r.expected))).append("</pre></div>")
                            .append("<div><div class='lbl'>Actual</div><pre>").append(esc(String.valueOf(r.actual))).append("</pre></div>")
                            .append("</div>");
                }
                if (r.skipReason != null) {
                    h.append("<div class='sect'>Skip reason</div><div class='skip-msg'>").append(esc(r.skipReason)).append("</div>");
                }
                if (r.calls.isEmpty()) {
                    h.append("<div class='sect'>API Calls</div><div style='font-size:13px;color:#829ab1'>No HTTP calls (request-building / offline test)</div>");
                } else {
                    h.append("<div class='sect'>API Calls (").append(r.calls.size()).append(")</div>");
                    for (CapturedCall c : r.calls) {
                        String codeClass = c.status >= 500 ? "code5xx" : c.status >= 400 ? "code4xx" : "code2xx";
                        h.append("<div class='call'><div class='line'><span class='m'>").append(c.method)
                                .append("</span><span>").append(esc(shortUrl(c.url))).append("</span><span class='")
                                .append(codeClass).append("'>").append(c.status).append("</span><span class='sdk'>")
                                .append(esc(c.sdkMethod)).append("</span><span class='dur'>").append(c.durationMs).append(" ms</span></div>")
                                .append("<details><summary style='cursor:pointer;font-size:12px;color:#1a56db;margin-top:6px'>cURL (copy-paste ready)</summary><pre>")
                                .append(esc(c.curl)).append("</pre></details>")
                                .append("<details><summary style='cursor:pointer;font-size:12px;color:#1a56db'>Response body</summary><pre>")
                                .append(esc(c.responseBody == null ? "" : c.responseBody)).append("</pre></details>")
                                .append("</div>");
                    }
                }
                h.append("</div></details>");
            }
        }

        h.append("</main><script>")
                .append("function filt(s,btn){document.querySelectorAll('.filters button').forEach(b=>b.classList.remove('active'));btn.classList.add('active');")
                .append("document.querySelectorAll('details.test').forEach(d=>{var st=d.classList.contains('FAILED')?'FAILED':d.classList.contains('PASSED')?'PASSED':'SKIPPED';")
                .append("d.style.display=(s==='all'||st===s)?'':'none';});}")
                .append("</script></body></html>");
        return h.toString();
    }

    private static String shortUrl(String url) {
        return url.length() > 110 ? url.substring(0, 110) + "..." : url;
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
