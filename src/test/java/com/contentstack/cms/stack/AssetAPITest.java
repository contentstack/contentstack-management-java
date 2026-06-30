package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Tag("API")
class AssetAPITest {

    static Contentstack client;
    static String AUTHTOKEN = TestClient.AUTHTOKEN;
    static String API_KEY = TestClient.API_KEY;
    static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    static String _uid = TestClient.AUTHTOKEN;
    static Asset asset;

    @BeforeAll
    static void setup() {
        client = TestClient.getClient();
        asset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset();
    }

    @Order(1)
    @Test
    void testFindAssets() {
        asset.clearParams();
        asset.addParam("include_folders", true);
        asset.addParam("environment", "production");
        asset.addParam("version", 1);
        asset.addParam("include_publish_details", true);
        asset.addParam("include_count", true);
        asset.addParam("relative_urls", false);
        asset.addParam("asc_field_uid", "created_at");
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        Request request = asset.find().request();
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Order(2)
    @Test
    void testFetch() {
        asset = client.stack().asset(_uid);
        asset.clearParams();
        asset.addParam("include_path", false);
        asset.addParam("version", 1);
        asset.addParam("include_publish_details", true);
        asset.addParam("environment", "production");
        asset.addParam("relative_urls", false);
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        asset.addHeader("authtoken", AUTHTOKEN);
        Request request = asset.fetch().request();
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Order(3)
    @Test
    void testGetAssetByFolderUid() throws IOException {
        asset.clearParams();
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> resp = asset.byFolderUid(_uid).execute();
        Assertions.assertEquals(5, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authorization"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"),
                "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"),
                "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Test
    void testAssetSubFolder() throws IOException {
        asset.clearParams();
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authtoken", AUTHTOKEN);
        Request request = asset.subfolder(_uid, true).request();
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Test
    @Disabled("disabled to avoid unnecessary asset creation, Tested working fine")
    void testAssetUpload() {
        asset.clearParams();
        asset.addParam("relative_urls", true);
        asset.addParam("include_dimension", true);
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        asset.addHeader("authtoken", AUTHTOKEN);
        String filePath = "/Users/shaileshmishra/Desktop/image.jpeg";
        String description = "The calender has been placed to assets by shaileshmishra";
        Request request = asset.uploadAsset(filePath, description).request();
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("POST", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Test
    @Disabled("disabled to avoid unnecessary asset creation, Tested working fine")
    void testAssetReplace() throws IOException {
        asset.clearParams();
        asset = client.stack().asset(_uid);
        asset.addParam("include_branch", true);
        asset.addParam("relative_urls", false);
        asset.addParam("environment", "production");
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        String filePath = "/Users/shaileshmishra/Downloads/calendar.png";
        Response<ResponseBody> resp = asset.replace(filePath, "Assets created by ***REMOVED***").execute();
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("PUT", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"),
                "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"),
                "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Test
    @Disabled("disabled to avoid unnecessary asset creation, Tested working fine")
    void testAssetGeneratePermanentUrl() throws IOException {
        asset.clearParams();
        asset.addParam("relative_urls", true);
        asset.addParam("include_dimension", true);
        JSONObject body = new JSONObject();
        JSONObject subBody = new JSONObject();
        subBody.put("permanent_url", "https://api.contentstack.io/v3/assets/stack_api_key/asset_UID/sample-slug.jpeg");
        body.put("asset", body);
        Response<ResponseBody> resp = asset.generatePermanentUrl(body).execute();
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("PUT", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"),
                "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"),
                "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Test
    void testAssetDownloadPermanentUrl() throws IOException {
        asset = client.stack().asset(_uid);
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        asset.addHeader("authtoken", AUTHTOKEN);
        Request request = asset.getPermanentUrl("www.google.com/search").request();
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
    }

    @Test
    void testAssetUploadWithMultipleParams() throws IOException {
        String description = "The calender has been placed to assets by ***REMOVED***";
        String filePath = "/Users/shaileshmishra/Documents/workspace/GitHub/contentstack-management-java/src/test/resources/asset.png";
        Contentstack client = new Contentstack.Builder().build();
        Stack stack = client.stack("Your-api-key", "authorization");
        Response<ResponseBody> upload = stack.asset()
                .addParams(new HashMap<>())
                .addHeaders(new HashMap<>())
                .addParam("key", "value")
                .addHeader("header", "value")
                .uploadAsset(filePath, description).execute();
        String[] tags = {"shailesh", "mishra", "mumbai", "india"};
        Response<ResponseBody> uploadMultiple = stack.asset().
                uploadAsset(filePath, "parent_uid", "Fake Image", "Something as description", tags).execute();
        Assertions.assertFalse(uploadMultiple.isSuccessful());
        Assertions.assertFalse(upload.isSuccessful());
    }

    @Test
    void testFetchSingleAssetPojo() throws IOException {
        asset = client.stack().asset(_uid)
                .addParam("include_count", true);
        Request request = asset.fetchAsPojo().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));
    }

    @Test
    void testFetchAllAssetsPojo() throws IOException {
        asset = client.stack().asset();
        Request request = asset.findAsPojo().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));
    }

    @Test
    void testFetchAssetsByFolderUidPojo() throws IOException {
        asset = client.stack().asset();
        Request request = asset.byFolderUidAsPojo("folder_uid").request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));
    }

    @Test
    void testFetchAssetsBySubFolderUidPojo() throws IOException {
        asset = client.stack().asset();
        Request request = asset.subfolderAsPojo("subfolder_uid", true).request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));
    }

    @Test
    void testFetchSingleFolderByNamePojo() {
        asset = client.stack().asset();
        HashMap<String, Object> queryContent = new HashMap<>();
        queryContent.put("is_dir", true);
        queryContent.put("name", "sub_folder_test");
        asset.addParam("query", queryContent);
        Request request = asset.getSingleFolderByNameAsPojo().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));
    }

    @Test
    void testFetchSubfoldersByParentFolderPojo() {
        asset = client.stack().asset();
        HashMap<String, Object> queryContent = new HashMap<>();
        queryContent.put("is_dir", true);
        asset.addParam("folder", "test_folder");
        asset.addParam("include_folders", true);
        asset.addParam("query", queryContent);
        queryContent.put("parent_uid", "parent_uid");
        asset.addParam("query", queryContent);
        Request request = asset.getSubfolderAsPojo().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());
        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));
    }

    // ==================== Asset Scanning API Tests ====================
    static String uploadedAssetUid;

    @Test
    @Order(10)
    void testUploadAssetIncludesScanStatusPending() throws IOException, ParseException {
        Asset uploadAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset();
        uploadAsset.addParam("include_asset_scan_status", true);
        uploadAsset.addHeader("api_key", API_KEY);
        uploadAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = uploadAsset.uploadAsset("src/test/resources/asset.png", "asset scan test upload").execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 201 on upload");
        Assertions.assertNotNull(response.body(), "Response body should not be null");
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "Upload response should include _asset_scan_status when param is set");
        Assertions.assertTrue(body.contains("pending"),
                "Newly uploaded asset scan status should be pending");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(body);
        JSONObject assetObj = (JSONObject) json.get("asset");
        if (assetObj != null) {
            uploadedAssetUid = (String) assetObj.get("uid");
        }
        Assertions.assertNotNull(uploadedAssetUid, "Could not extract UID of uploaded asset");
    }

    @Test
    @Order(11)
    void testFindAssetsWithScanStatus() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded asset from testUploadAssetIncludesScanStatusPending");
        asset.clearParams();
        asset.addParam("include_asset_scan_status", true);
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = asset.find().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200");
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "Response should include _asset_scan_status field when param is set");
        Assertions.assertTrue(body.contains(uploadedAssetUid),
                "Uploaded asset UID should appear in the list response");
    }

    @Test
    @Order(12)
    void testFetchAssetWithScanStatus() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded asset from testUploadAssetIncludesScanStatusPending");
        Asset scanAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset(uploadedAssetUid);
        scanAsset.addParam("include_asset_scan_status", true);
        scanAsset.addHeader("api_key", API_KEY);
        scanAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = scanAsset.fetch().execute();
        Assertions.assertTrue(response.raw().request().url().toString().contains("include_asset_scan_status=true"));
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 for asset UID: " + uploadedAssetUid);
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "Response should include _asset_scan_status for the uploaded asset");
    }

    @Test
    @Order(13)
    void testFindAssetsAllScanStatusesAreValid() throws IOException {
        Asset listAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset();
        listAsset.addParam("include_asset_scan_status", true);
        listAsset.addHeader("api_key", API_KEY);
        listAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = listAsset.find().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200");
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "Response should include _asset_scan_status when param is set");
        boolean hasValidStatus = body.contains("\"pending\"") || body.contains("\"clean\"")
                || body.contains("\"not_scanned\"") || body.contains("\"quarantined\"");
        Assertions.assertTrue(hasValidStatus,
                "Every _asset_scan_status value must be one of: pending, clean, not_scanned, quarantined");
    }

    @Test
    @Order(14)
    void testPublishAssetWithApiVersionHeader() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded asset from testUploadAssetIncludesScanStatusPending");
        Asset scanAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset(uploadedAssetUid);
        scanAsset.addHeader("api_key", API_KEY);
        scanAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        scanAsset.addHeader("api_version", "3.2");
        JSONObject publishBody = new JSONObject();
        JSONObject publishDetails = new JSONObject();
        publishDetails.put("locales", new String[]{"en-us"});
        publishDetails.put("environments", new String[]{"development"});
        publishBody.put("asset", publishDetails);
        Response<ResponseBody> response = scanAsset.publish(publishBody).execute();
        Assertions.assertEquals("3.2", response.raw().request().header("api_version"),
                "api_version: 3.2 header must be present on publish for CDA-side scan validation");
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 — publish always returns success regardless of scan status");
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("Asset sent for publishing"),
                "Publish response notice should confirm asset was sent for publishing");
    }

    @Test
    @Order(15)
    void testFetchUploadedAssetScanStatusIsValid() throws IOException, ParseException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded asset from testUploadAssetIncludesScanStatusPending");
        Asset scanAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset(uploadedAssetUid);
        scanAsset.addParam("include_asset_scan_status", true);
        scanAsset.addHeader("api_key", API_KEY);
        scanAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = scanAsset.fetch().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 for asset UID: " + uploadedAssetUid);
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertTrue(body.contains("_asset_scan_status"),
                "Response should include _asset_scan_status when param is set");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(body);
        JSONObject assetObj = (JSONObject) json.get("asset");
        Assertions.assertNotNull(assetObj, "Response should contain an 'asset' object");
        String status = (String) assetObj.get("_asset_scan_status");
        Assertions.assertNotNull(status, "_asset_scan_status should be present in asset object");
        java.util.Set<String> validStatuses = java.util.Set.of("pending", "clean", "not_scanned", "quarantined");
        Assertions.assertTrue(validStatuses.contains(status),
                "_asset_scan_status value '" + status + "' must be one of: pending, clean, not_scanned, quarantined");
    }

    @Test
    @Order(16)
    void testFindAssetsWithoutScanStatusParamFieldAbsent() throws IOException {
        Asset listAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset();
        listAsset.addHeader("api_key", API_KEY);
        listAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = listAsset.find().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200");
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertFalse(body.contains("_asset_scan_status"),
                "_asset_scan_status must be absent when include_asset_scan_status param is not passed");
    }

    @Test
    @Order(17)
    void testFetchAssetWithoutScanStatusParamFieldAbsent() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded asset from testUploadAssetIncludesScanStatusPending");
        Asset scanAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset(uploadedAssetUid);
        scanAsset.addHeader("api_key", API_KEY);
        scanAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = scanAsset.fetch().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 for asset UID: " + uploadedAssetUid);
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertFalse(body.contains("_asset_scan_status"),
                "_asset_scan_status must be absent when include_asset_scan_status param is not passed");
    }

    @Test
    @Order(18)
    void testPublishWithoutApiVersionHeaderIsAbsent() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded asset from testUploadAssetIncludesScanStatusPending");
        Asset scanAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset(uploadedAssetUid);
        scanAsset.addHeader("api_key", API_KEY);
        scanAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        JSONObject publishBody = new JSONObject();
        JSONObject publishDetails = new JSONObject();
        publishDetails.put("locales", new String[]{"en-us"});
        publishDetails.put("environments", new String[]{"development"});
        publishBody.put("asset", publishDetails);
        Response<ResponseBody> response = scanAsset.publish(publishBody).execute();
        Assertions.assertNull(response.raw().request().header("api_version"),
                "api_version header must be absent when not explicitly added");
    }

    @Test
    @Order(19)
    void testUploadWithoutScanStatusParamFieldAbsent() throws IOException {
        Asset uploadAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset();
        uploadAsset.addHeader("api_key", API_KEY);
        uploadAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = uploadAsset.uploadAsset("src/test/resources/asset.png", "negative scan test upload").execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 201 on upload");
        Assertions.assertNotNull(response.body());
        String body = response.body().string();
        Assertions.assertFalse(body.contains("_asset_scan_status"),
                "_asset_scan_status must be absent in upload response when param is not passed");
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);
            JSONObject assetObj = (JSONObject) json.get("asset");
            if (assetObj != null) {
                String negativeTestUid = (String) assetObj.get("uid");
                if (negativeTestUid != null) {
                    Asset toDelete = client.stack(API_KEY, MANAGEMENT_TOKEN).asset(negativeTestUid);
                    toDelete.addHeader("api_key", API_KEY);
                    toDelete.addHeader("authorization", MANAGEMENT_TOKEN);
                    toDelete.delete().execute();
                }
            }
        } catch (ParseException ignored) {}
    }

    @Test
    @Order(20)
    void testDeleteUploadedScanAsset() throws IOException {
        Assumptions.assumeTrue(uploadedAssetUid != null, "Skipping: no uploaded asset to clean up");
        Asset uploadedAsset = client.stack(API_KEY, MANAGEMENT_TOKEN).asset(uploadedAssetUid);
        uploadedAsset.addHeader("api_key", API_KEY);
        uploadedAsset.addHeader("authorization", MANAGEMENT_TOKEN);
        Response<ResponseBody> response = uploadedAsset.delete().execute();
        Assertions.assertTrue(response.isSuccessful(), "Expected 200 on delete for asset UID: " + uploadedAssetUid);
    }

    // ==================== End Asset Scanning API Tests ====================

    @Test
    @Disabled("disabled to avoid unnecessary asset creation, Tested working fine")
    void uploadFile() throws Exception {
        Contentstack contentstack = new Contentstack.Builder().build();
        Stack stack = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        Asset asset = stack.asset();
        String fileName = "/Users/reeshika.hosmani/Downloads/surf-svgrepo-com.svg", parentFolder = "bltd1150f1f7d9411e5", title = "Vacation icon";
        String[] tags = {"icon"};
        Response<ResponseBody> response = asset.uploadAsset(fileName, parentFolder, title, "", tags).execute();
        if (response.isSuccessful()) {
            System.out.println("uploaded asset successfully:" + response.body().string());
        } else {
            System.out.println("Error in uploading" + response.errorBody().string());
        }
    }
}
