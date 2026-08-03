package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import com.contentstack.cms.TestStackContext;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

/**
 * Asset scan status tests against a stack in the Asset-Management org
 * (AM_ORG_UID) - the Java port of the JS suite's assetScanStatus-test "Part 2".
 *
 * <p>The AM stack is created dynamically by {@link TestStackContext} when
 * AM_ORG_UID is configured; every test here skips when it isn't. All calls
 * use the session authtoken (no management token needed on the AM stack).
 */
@Tag("api")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssetScanAMAPITest {

    private static Contentstack client;
    private static String amApiKey;
    private static String uploadedAssetUid;
    private final JSONParser parser = new JSONParser();

    @BeforeAll
    void setup() {
        Assumptions.assumeTrue(TestStackContext.isAmStackCreated(),
                "Skipping AM scan tests: AM_ORG_UID not configured or AM stack creation failed");
        amApiKey = TestStackContext.getAmStackApiKey();
        client = new Contentstack.Builder()
                .setAuthtoken(TestClient.AUTHTOKEN)
                .setHost(TestClient.DEV_HOST)
                .build();
    }

    private Asset amAsset() {
        Asset asset = client.stack(amApiKey).asset();
        asset.addHeader("api_key", amApiKey);
        return asset;
    }

    @Test
    @Order(1)
    @DisplayName("AM org: upload with include_asset_scan_status returns pending status")
    void testAmUploadIncludesScanStatusPending() throws IOException, org.json.simple.parser.ParseException {
        Asset uploadAsset = amAsset();
        uploadAsset.addParam("include_asset_scan_status", true);
        Response<ResponseBody> response = uploadAsset
                .uploadAsset("src/test/resources/asset.png", "AM org scan test upload").execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 201 on AM-org upload, got " + response.code());
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "AM-org upload response should include _asset_scan_status when param is set");
        Assertions.assertTrue(body.contains("pending"),
                "Newly uploaded AM-org asset scan status should be pending");
        JSONObject json = (JSONObject) parser.parse(body);
        JSONObject assetObj = (JSONObject) json.get("asset");
        Assertions.assertNotNull(assetObj, "Upload response should contain an asset object");
        uploadedAssetUid = (String) assetObj.get("uid");
        Assertions.assertNotNull(uploadedAssetUid, "Could not extract AM asset uid");
    }

    @Test
    @Order(2)
    @DisplayName("AM org: fetch with include_asset_scan_status exposes a valid status")
    void testAmFetchScanStatusIsValid() throws IOException, org.json.simple.parser.ParseException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded AM asset");
        Asset fetchAsset = client.stack(amApiKey).asset(uploadedAssetUid);
        fetchAsset.addHeader("api_key", amApiKey);
        fetchAsset.addParam("include_asset_scan_status", true);
        Response<ResponseBody> response = fetchAsset.fetch().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 fetching AM asset, got " + response.code());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "AM-org fetch should include _asset_scan_status when param is set");
        JSONObject json = (JSONObject) parser.parse(body);
        JSONObject assetObj = (JSONObject) json.get("asset");
        String status = (String) assetObj.get("_asset_scan_status");
        java.util.Set<String> valid = java.util.Set.of("pending", "clean", "not_scanned", "quarantined");
        Assertions.assertTrue(valid.contains(status),
                "_asset_scan_status '" + status + "' must be one of " + valid);
    }

    @Test
    @Order(3)
    @DisplayName("AM org: fetch without the param must omit _asset_scan_status")
    void testAmFetchWithoutParamOmitsScanStatus() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded AM asset");
        Asset fetchAsset = client.stack(amApiKey).asset(uploadedAssetUid);
        fetchAsset.addHeader("api_key", amApiKey);
        Response<ResponseBody> response = fetchAsset.fetch().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 fetching AM asset, got " + response.code());
        String body = response.body().string();
        Assertions.assertFalse(body.contains("_asset_scan_status"),
                "_asset_scan_status must be absent when include_asset_scan_status is not passed");
    }

    @Test
    @Order(4)
    @DisplayName("AM org: find with include_asset_scan_status lists statuses")
    void testAmFindWithScanStatus() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded AM asset");
        Asset listAsset = amAsset();
        listAsset.addParam("include_asset_scan_status", true);
        Response<ResponseBody> response = listAsset.find().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 listing AM assets, got " + response.code());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "AM-org find should include _asset_scan_status when param is set");
        Assertions.assertTrue(body.contains(uploadedAssetUid),
                "Uploaded AM asset uid should appear in the list");
    }
}
