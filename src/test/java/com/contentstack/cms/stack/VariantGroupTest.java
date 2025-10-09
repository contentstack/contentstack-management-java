package com.contentstack.cms.stack;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.contentstack.cms.TestClient;

import okhttp3.Request;
import okio.Buffer;

class VariantGroupTest {

    private static final String API_KEY = TestClient.API_KEY;
    private static final String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    private static final String VARIANT_GROUP_UID = TestClient.VARIANT_GROUP_UID;
    private VariantGroup variantGroup;

    @BeforeEach
    void setUp() {
        variantGroup = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).variantGroup(VARIANT_GROUP_UID);
    }

    @Test
    void testValidate_WithValidUID() {
        Assertions.assertDoesNotThrow(() -> variantGroup.validate());
    }

    @Test
    void testValidate_WithNullUID() {
        VariantGroup group = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).variantGroup();
        IllegalAccessError exception = Assertions.assertThrows(IllegalAccessError.class, group::validate);
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void testValidate_WithEmptyUID() {
        VariantGroup group = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).variantGroup("");
        IllegalAccessError exception = Assertions.assertThrows(IllegalAccessError.class, group::validate);
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void testLinkContentTypes_WithEmptyArray() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, 
            () -> variantGroup.linkContentTypes());
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void testUnlinkContentTypes_WithEmptyArray() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, 
            () -> variantGroup.unlinkContentTypes());
        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    void testLinkContentTypes_SingleContentType() throws IOException {
        Request request = variantGroup.linkContentTypes("test_content_type").request();
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(
            "https://api.contentstack.io/v3/variant_groups/" + VARIANT_GROUP_UID + "/variants", 
            request.url().toString()
        );
        
        Assertions.assertNotNull(request.body(), "Request body should not be null");
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String requestBody = buffer.readUtf8();
        
        // Verify the request body contains all required fields
        Assertions.assertTrue(requestBody.contains("\"uid\":\"" + VARIANT_GROUP_UID + "\""), "Request body should contain variant group UID");
        Assertions.assertTrue(requestBody.contains("\"content_types\":["), "Request body should contain content_types array");
        Assertions.assertTrue(requestBody.contains("\"uid\":\"test_content_type\""), "Request body should contain content type UID");
        Assertions.assertTrue(requestBody.contains("\"status\":\"linked\""), "Request body should contain linked status");
        Assertions.assertTrue(requestBody.contains("\"branches\":["), "Request body should contain branches array");
        Assertions.assertTrue(requestBody.contains("\"main\""), "Request body should contain main branch");
    }

    @Test
    void testUnlinkContentTypes_MultipleContentTypes() throws IOException {
        Request request = variantGroup.unlinkContentTypes("type1", "type2", "type3").request();
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(
            "https://api.contentstack.io/v3/variant_groups/" + VARIANT_GROUP_UID + "/variants", 
            request.url().toString()
        );
        
        Assertions.assertNotNull(request.body(), "Request body should not be null");
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String requestBody = buffer.readUtf8();
        
        // Verify the request body contains all required fields
        Assertions.assertTrue(requestBody.contains("\"uid\":\"" + VARIANT_GROUP_UID + "\""), "Request body should contain variant group UID");
        Assertions.assertTrue(requestBody.contains("\"content_types\":["), "Request body should contain content_types array");
        
        // Verify each content type is included with unlinked status
        Assertions.assertTrue(requestBody.contains("\"uid\":\"type1\""), "Request body should contain first content type");
        Assertions.assertTrue(requestBody.contains("\"uid\":\"type2\""), "Request body should contain second content type");
        Assertions.assertTrue(requestBody.contains("\"uid\":\"type3\""), "Request body should contain third content type");
        Assertions.assertTrue(requestBody.contains("\"status\":\"unlinked\""), "Request body should contain unlinked status");
        
        // Verify branches
        Assertions.assertTrue(requestBody.contains("\"branches\":["), "Request body should contain branches array");
        Assertions.assertTrue(requestBody.contains("\"main\""), "Request body should contain main branch");
    }

    @Test
    void testFind_CallsCorrectEndpoint() throws IOException {
        variantGroup.addParam("include_count", true);
        Request request = variantGroup.find().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(
            request.url().toString().contains("include_count=true"),
            "URL should include the added parameter"
        );
    }

    @Test
    void testSetBranches_WithList() throws IOException {
        List<String> testBranches = Arrays.asList("main", "development", "staging");
        variantGroup.setBranches(testBranches);
        
        Request request = variantGroup.linkContentTypes("test_content_type").request();
        Assertions.assertNotNull(request.body(), "Request body should not be null");
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String requestBody = buffer.readUtf8();
        
        for (String branch : testBranches) {
            Assertions.assertTrue(
                requestBody.contains(branch),
                "Request body should contain branch: " + branch
            );
        }
    }

    @Test
    void testSetBranches_WithVarargs() throws IOException {
        variantGroup.setBranches("main", "feature-1", "feature-2");
        
        Request request = variantGroup.linkContentTypes("test_content_type").request();
        Assertions.assertNotNull(request.body(), "Request body should not be null");
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String requestBody = buffer.readUtf8();
        
        Assertions.assertTrue(requestBody.contains("main"));
        Assertions.assertTrue(requestBody.contains("feature-1"));
        Assertions.assertTrue(requestBody.contains("feature-2"));
    }

    @Test
    void testDefaultBranch() throws IOException {
        VariantGroup newGroup = TestClient.getClient().stack(API_KEY, MANAGEMENT_TOKEN).variantGroup("test_uid");
        Request request = newGroup.linkContentTypes("test_content_type").request();
        Assertions.assertNotNull(request.body(), "Request body should not be null");
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String requestBody = buffer.readUtf8();
        
        Assertions.assertTrue(requestBody.contains("main"), "Default branch should be 'main'");
    }

}