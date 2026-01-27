package com.contentstack.cms.stack;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.contentstack.cms.TestClient;

import okhttp3.Request;

public class VariantGroupAPITest {

    private static final String API_KEY = TestClient.API_KEY;
    private static final String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    private static final String VARIANT_GROUP_UID = TestClient.VARIANT_GROUP_UID;
    private final VariantGroup variantGroup = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).variantGroup();

    @Test
    void testFetchVariantGroups() throws IOException, InterruptedException {
        variantGroup.addParam("include_count", true);
        variantGroup.addParam("include_variant_info", true);
        Request request = variantGroup.find().request();
        Assertions.assertEquals("GET", request.method());    }

    @Test
    void testLinkContentTypes() throws IOException, InterruptedException {
        VariantGroup variantGroupWithUID = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).variantGroup(VARIANT_GROUP_UID);
        Request request = variantGroupWithUID.linkContentTypes("author", "page").request();
        Assertions.assertEquals("PUT", request.method());    }

    @Test
    void testUnlinkContentTypes() throws IOException, InterruptedException {
        VariantGroup variantGroupWithUID = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).variantGroup(VARIANT_GROUP_UID);
        Request request = variantGroupWithUID.unlinkContentTypes("author", "page").request();
        Assertions.assertEquals("PUT", request.method());    }
}