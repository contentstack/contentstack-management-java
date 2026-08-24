package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;

import okhttp3.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Request-shape checks for entry-variant endpoints (no HTTP — same style as {@link ContentTypeAPITest}).
 */
@Tag("unit")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntryVariantAPITest {

    private static final String CREATE_VARIANT_BODY = "entry_variant/create_entry_variant.json";
    private static final String UPDATE_VARIANT_BODY = "entry_variant/update_entry_variant.json";
    private static final String PUBLISH_VARIANT_BODY = "entry_variant/publish_entry_variant.json";
    private static final String UNPUBLISH_VARIANT_BODY = "entry_variant/unpublish_entry_variant.json";

    private final String contentTypeUid = TestClient.ENTRY_VARIANT_CONTENT_TYPE_UID;
    private final String entryUid = "entry_uid";
    private final String variantUid = "variant_uid";
    private final String stackBranch = TestClient.ENTRY_VARIANT_BRANCH == null
            ? "develop"
            : TestClient.ENTRY_VARIANT_BRANCH.trim();

    private Stack stack;
    private int headerCount;

    @BeforeAll
    void setUp() {
        /*
         * Fresh Stack from the client — avoids mutating {@link TestClient#getStack()} singleton headers
         * (which broke other suites such as {@link ReleaseAPITest} when branch was added globally).
         */
        stack = TestClient.getClient().stack(
                TestClient.API_KEY,
                TestClient.MANAGEMENT_TOKEN,
                stackBranch);
        headerCount = stack.headers.size();
    }

    private Entry freshEntry() {
        return stack.contentType(contentTypeUid).entry(entryUid);
    }

    private String variantPathSuffix() {
        return "/v3/content_types/" + contentTypeUid + "/entries/" + entryUid + "/variants/" + variantUid;
    }

    private String variantsCollectionPath() {
        return "/v3/content_types/" + contentTypeUid + "/entries/" + entryUid + "/variants";
    }

    /**
     * Publish/unpublish fixtures omit {@code variants[].uid}; inject placeholder for request body construction.
     */
    @SuppressWarnings("unchecked")
    private static void injectVariantUidIntoVariantsArray(JSONObject body, String uid) {
        JSONObject ent = (JSONObject) body.get("entry");
        Assertions.assertNotNull(ent, "Body missing entry");
        JSONArray variants = (JSONArray) ent.get("variants");
        Assertions.assertNotNull(variants, "entry.variants missing");
        Assertions.assertFalse(variants.isEmpty(), "entry.variants empty");
        JSONObject v0 = (JSONObject) variants.get(0);
        v0.put("uid", uid);
    }

    @Test
    @Order(1)
    void createEntryVariant_requestShape() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);
        JSONObject body = Utils.readJson(CREATE_VARIANT_BODY);
        Assertions.assertNotNull(body);

        Request req = entry.createEntryVariant(variantUid, body).request();
        Assertions.assertEquals(headerCount, req.headers().names().size());
        Assertions.assertEquals("PUT", req.method());
        Assertions.assertTrue(req.url().isHttps());
        Assertions.assertEquals(variantPathSuffix(), req.url().encodedPath());
        Assertions.assertEquals("locale=" + TestClient.ENTRY_VARIANT_LOCALE, req.url().encodedQuery());
        Assertions.assertEquals(stackBranch, req.header(Util.BRANCH));
    }

    @Test
    @Order(2)
    void updateEntryVariant_sameUrlAndMethodAsCreate() {
        Entry entry = freshEntry();
        entry.clearParams();
        JSONObject createBody = Utils.readJson(CREATE_VARIANT_BODY);
        JSONObject updateBody = Utils.readJson(UPDATE_VARIANT_BODY);
        Assertions.assertNotNull(createBody);
        Assertions.assertNotNull(updateBody);

        Request createReq = entry.createEntryVariant(variantUid, createBody).request();
        Entry entry2 = freshEntry();
        Request updateReq = entry2.updateEntryVariant(variantUid, updateBody).request();

        Assertions.assertEquals(createReq.method(), updateReq.method());
        Assertions.assertEquals(createReq.url(), updateReq.url());
    }

    @Test
    @Order(3)
    void fetchSingleEntryVariant_requestShape() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);
        entry.addParam("include_publish_details", true);

        Request req = entry.fetchEntryVariant(variantUid).request();
        Assertions.assertEquals(headerCount, req.headers().names().size());
        Assertions.assertEquals("GET", req.method());
        Assertions.assertTrue(req.url().isHttps());
        Assertions.assertEquals(variantPathSuffix(), req.url().encodedPath());
        Assertions.assertNotNull(req.url().encodedQuery());
        Assertions.assertTrue(req.url().encodedQuery().contains("locale=" + TestClient.ENTRY_VARIANT_LOCALE));
        Assertions.assertTrue(req.url().encodedQuery().contains("include_publish_details=true"));
    }

    @Test
    @Order(4)
    void fetchAllEntryVariants_requestShape() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);

        Request req = entry.fetchEntryVariants().request();
        Assertions.assertEquals(headerCount, req.headers().names().size());
        Assertions.assertEquals("GET", req.method());
        Assertions.assertEquals(variantsCollectionPath(), req.url().encodedPath());
        Assertions.assertEquals("locale=" + TestClient.ENTRY_VARIANT_LOCALE, req.url().encodedQuery());
    }

    @Test
    @Order(5)
    void publishEntryVariants_requestShape() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);

        JSONObject publishBody = Utils.readJson(PUBLISH_VARIANT_BODY);
        Assertions.assertNotNull(publishBody);
        injectVariantUidIntoVariantsArray(publishBody, variantUid);

        Request req = entry.publishEntryVariants(publishBody).request();
        Assertions.assertEquals("POST", req.method());
        Assertions.assertTrue(req.url().isHttps());
        Assertions.assertEquals(
                "/v3/content_types/" + contentTypeUid + "/entries/" + entryUid + "/publish",
                req.url().encodedPath());
        Assertions.assertEquals("locale=" + TestClient.ENTRY_VARIANT_LOCALE, req.url().encodedQuery());
        Assertions.assertEquals(Util.API_VERSION_ENTRY_VARIANTS_PUBLISH, req.header(Util.API_VERSION));
    }

    @Test
    @Order(6)
    void unpublishEntryVariants_requestShape() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);

        JSONObject unpublishBody = Utils.readJson(UNPUBLISH_VARIANT_BODY);
        Assertions.assertNotNull(unpublishBody);
        injectVariantUidIntoVariantsArray(unpublishBody, variantUid);

        Request req = entry.unpublishEntryVariants(unpublishBody).request();
        Assertions.assertEquals("POST", req.method());
        Assertions.assertEquals(
                "/v3/content_types/" + contentTypeUid + "/entries/" + entryUid + "/unpublish",
                req.url().encodedPath());
        Assertions.assertEquals("locale=" + TestClient.ENTRY_VARIANT_LOCALE, req.url().encodedQuery());
        Assertions.assertEquals(Util.API_VERSION_ENTRY_VARIANTS_PUBLISH, req.header(Util.API_VERSION));
    }

    @Test
    @Order(7)
    void fetchSingleEntryVariant_implicitVsExplicitBranchHeader() {
        Entry plain = freshEntry();
        plain.clearParams();
        plain.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);

        Request implicit = plain.fetchEntryVariant(variantUid).request();
        Assertions.assertEquals(stackBranch, implicit.header(Util.BRANCH));

        Entry withPerCall = freshEntry();
        withPerCall.clearParams();
        withPerCall.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);
        Request explicit = withPerCall.fetchEntryVariant(variantUid, stackBranch).request();
        Assertions.assertEquals(stackBranch, explicit.header(Util.BRANCH));
        Assertions.assertEquals(implicit.url(), explicit.url());
    }

    @Test
    @Order(8)
    void fetchSingleEntryVariant_afterAddBranch() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addBranch(stackBranch);
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);

        Request req = entry.fetchEntryVariant(variantUid).request();
        Assertions.assertEquals(stackBranch, req.header(Util.BRANCH));
        Assertions.assertEquals(variantPathSuffix(), req.url().encodedPath());
    }

    @Test
    @Order(9)
    void fetchSingleEntryVariant_withPerCallBranchOverload() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);

        Request req = entry.fetchEntryVariant(variantUid, stackBranch).request();
        Assertions.assertEquals(stackBranch, req.header(Util.BRANCH));
        Assertions.assertEquals("GET", req.method());
        Assertions.assertEquals(variantPathSuffix(), req.url().encodedPath());
    }

    @Test
    @Order(10)
    void deleteEntryVariant_requestShape() {
        Entry entry = freshEntry();
        entry.clearParams();
        entry.addParam("locale", TestClient.ENTRY_VARIANT_LOCALE);

        Request req = entry.deleteEntryVariant(variantUid).request();
        Assertions.assertEquals("DELETE", req.method());
        Assertions.assertEquals(variantPathSuffix(), req.url().encodedPath());
        Assertions.assertEquals("locale=" + TestClient.ENTRY_VARIANT_LOCALE, req.url().encodedQuery());
    }
}
