package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.Utils;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Request-shape checks for entry variants (no HTTP — safe without dev18 / prod variant APIs).
 */
@Tag("unit")
public class EntryVariantUnitTest {

    private static final String CREATE_VARIANT_BODY = "entry_variant/create_entry_variant.json";
    private static final String UPDATE_VARIANT_BODY = "entry_variant/update_entry_variant.json";

    private static Entry entry;

    @BeforeAll
    static void setup() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN);
        entry = stack.contentType("blog").entry("entryUid123");
    }

    @Test
    void createEntryVariant_requestIsPutWithVariantSegment() {
        entry.clearParams();
        entry.addParam("locale", "en-us");
        JSONObject body = Utils.readJson(CREATE_VARIANT_BODY);
        Assertions.assertNotNull(body, "Missing " + CREATE_VARIANT_BODY);

        Request req = entry.createEntryVariant("variantUid456", body).request();
        Assertions.assertEquals("PUT", req.method());
        Assertions.assertEquals(
                "/v3/content_types/blog/entries/entryUid123/variants/variantUid456",
                req.url().encodedPath());
        Assertions.assertEquals("locale=en-us", req.url().encodedQuery());
    }

    @Test
    void updateEntryVariant_samePathAndMethodAsCreate() {
        entry.clearParams();
        JSONObject createBody = Utils.readJson(CREATE_VARIANT_BODY);
        JSONObject updateBody = Utils.readJson(UPDATE_VARIANT_BODY);
        Assertions.assertNotNull(createBody, "Missing " + CREATE_VARIANT_BODY);
        Assertions.assertNotNull(updateBody, "Missing " + UPDATE_VARIANT_BODY);

        Request createReq = entry.createEntryVariant("sameUid", createBody).request();
        Request updateReq = entry.updateEntryVariant("sameUid", updateBody).request();

        Assertions.assertEquals(createReq.method(), updateReq.method());
        Assertions.assertEquals(createReq.url(), updateReq.url());
    }

    @Test
    void fetchEntryVariants_requestIsGetOnVariantsCollection() {
        entry.clearParams();
        Request req = entry.fetchEntryVariants().request();
        Assertions.assertEquals("GET", req.method());
        Assertions.assertEquals(
                "/v3/content_types/blog/entries/entryUid123/variants",
                req.url().encodedPath());
    }

    @Test
    void fetchEntryVariant_requestIsGetOnSingleVariant() {
        entry.clearParams();
        Request req = entry.fetchEntryVariant("v1").request();
        Assertions.assertEquals(
                "/v3/content_types/blog/entries/entryUid123/variants/v1",
                req.url().encodedPath());
    }

    @Test
    void publishEntryVariants_addsApiVersionHeaderWhenNotAlreadySet() {
        entry.clearParams();
        JSONObject body = new JSONObject();
        Request req = entry.publishEntryVariants(body).request();
        Assertions.assertEquals(Util.API_VERSION_ENTRY_VARIANTS_PUBLISH, req.header(Util.API_VERSION));
    }

    @Test
    void unpublishEntryVariants_addsApiVersionHeaderWhenNotAlreadySet() {
        entry.clearParams();
        JSONObject body = new JSONObject();
        Request req = entry.unpublishEntryVariants(body).request();
        Assertions.assertEquals(Util.API_VERSION_ENTRY_VARIANTS_PUBLISH, req.header(Util.API_VERSION));
    }

    @Test
    void deleteEntryVariant_requestIsDeleteOnVariantPath() {
        entry.clearParams();
        Request req = entry.deleteEntryVariant("toRemove").request();
        Assertions.assertEquals("DELETE", req.method());
        Assertions.assertEquals(
                "/v3/content_types/blog/entries/entryUid123/variants/toRemove",
                req.url().encodedPath());
    }

    @Test
    void variantOperations_forwardStackBranchFromThreeArgStack() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN, "feature-branch");
        Entry e = stack.contentType("blog").entry("entryUid123");
        e.clearParams();
        JSONObject body = Utils.readJson(CREATE_VARIANT_BODY);
        Assertions.assertNotNull(body, "Missing " + CREATE_VARIANT_BODY);

        Assertions.assertEquals(
                "feature-branch",
                e.createEntryVariant("variantUid456", body).request().header(Util.BRANCH));
        Assertions.assertEquals(
                "feature-branch",
                e.fetchEntryVariants().request().header(Util.BRANCH));
        Assertions.assertEquals(
                "feature-branch",
                e.fetchEntryVariant("v1").request().header(Util.BRANCH));
        Assertions.assertEquals(
                "feature-branch",
                e.publishEntryVariants(new JSONObject()).request().header(Util.BRANCH));
        Assertions.assertEquals(
                "feature-branch",
                e.unpublishEntryVariants(new JSONObject()).request().header(Util.BRANCH));
    }

    @Test
    void fetchEntryVariant_branchArgumentOverridesStackForThatCallOnly() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN, "stack-branch");
        Entry e = stack.contentType("blog").entry("entryUid123");
        e.clearParams();

        Request overridden = e.fetchEntryVariant("v1", "call-branch").request();
        Assertions.assertEquals("call-branch", overridden.header(Util.BRANCH));

        Request defaultBranch = e.fetchEntryVariant("v1").request();
        Assertions.assertEquals("stack-branch", defaultBranch.header(Util.BRANCH));

        Assertions.assertEquals("stack-branch", e.fetchEntryVariant("v1", null).request().header(Util.BRANCH));
    }

    @Test
    void fetchEntryVariant_branchArgumentOverridesEntryLevelBranch() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN, "stack-branch");
        Entry e = stack.contentType("blog").entry("entryUid123").addBranch("entry-branch");
        e.clearParams();

        Assertions.assertEquals(
                "per-call-branch",
                e.fetchEntryVariant("v1", "per-call-branch").request().header(Util.BRANCH));
        Assertions.assertEquals(
                "entry-branch",
                e.fetchEntryVariant("v1").request().header(Util.BRANCH));
    }

    @Test
    void variantOperations_entryAddBranchOverridesStackBranch() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN, "stack-branch");
        Entry e = stack.contentType("blog").entry("entryUid123").addBranch("override-branch");
        e.clearParams();
        JSONObject body = Utils.readJson(CREATE_VARIANT_BODY);
        Assertions.assertNotNull(body, "Missing " + CREATE_VARIANT_BODY);

        Request req = e.createEntryVariant("variantUid456", body).request();
        Assertions.assertEquals("override-branch", req.header(Util.BRANCH));
    }

    @Test
    void deleteEntryVariant_forwardsStackBranchHeader() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN, "branch-for-delete");
        Entry e = stack.contentType("blog").entry("entryUid123");
        e.clearParams();
        Assertions.assertEquals(
                "branch-for-delete",
                e.deleteEntryVariant("toRemove").request().header(Util.BRANCH));
    }

    @Test
    void updateEntryVariant_forwardsStackBranchHeader() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN, "branch-for-update");
        Entry e = stack.contentType("blog").entry("entryUid123");
        e.clearParams();
        JSONObject body = Utils.readJson(UPDATE_VARIANT_BODY);
        Assertions.assertNotNull(body, "Missing " + UPDATE_VARIANT_BODY);
        Assertions.assertEquals(
                "branch-for-update",
                e.updateEntryVariant("uid", body).request().header(Util.BRANCH));
    }

    @Test
    void addHeaderBranch_matchesAddBranchForVariantRequests() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN);
        Entry viaAddHeader = stack.contentType("blog").entry("e1").addHeader(Util.BRANCH, "hdr-branch");
        Entry viaAddBranch = stack.contentType("blog").entry("e2").addBranch("hdr-branch");
        viaAddHeader.clearParams();
        viaAddBranch.clearParams();
        JSONObject body = Utils.readJson(CREATE_VARIANT_BODY);
        Assertions.assertNotNull(body, "Missing " + CREATE_VARIANT_BODY);
        Assertions.assertEquals(
                viaAddBranch.createEntryVariant("v", body).request().header(Util.BRANCH),
                viaAddHeader.createEntryVariant("v", body).request().header(Util.BRANCH));
    }

    @Test
    void fetchEntryVariant_blankSecondArgUsesStackBranchLikeNull() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN, "stack-branch");
        Entry e = stack.contentType("blog").entry("entryUid123");
        e.clearParams();
        Assertions.assertEquals(
                "stack-branch",
                e.fetchEntryVariant("v1", "").request().header(Util.BRANCH));
    }

    @Test
    void variantOperations_noStackBranch_omitsBranchHeaderWhenNotSetOnEntry() {
        Contentstack cms = new Contentstack.Builder().setAuthtoken(TestClient.AUTHTOKEN).build();
        Stack stack = cms.stack(TestClient.API_KEY, TestClient.MANAGEMENT_TOKEN);
        Entry e = stack.contentType("blog").entry("entryUid123");
        e.clearParams();
        Assertions.assertNull(e.fetchEntryVariant("v1").request().header(Util.BRANCH));
    }
}
