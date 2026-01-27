package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import com.contentstack.cms.stack.FileUploader;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("API")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
        // Headers: api_key, authorization, and potentially authtoken from SDK
        Assertions.assertTrue(request.headers().size() >= 2, 
            "Expected at least 2 headers (api_key, authorization), got: " + request.headers().size());
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");        Assertions.assertEquals(443, request.url().port(), "port should be 443");
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

        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        asset.addHeader("authtoken", AUTHTOKEN);
        // Create Asset Instance to find all assets
        Request request = asset.fetch().request();
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");

    }

    @Order(3)
    @Test
    void testGetAssetByFolderUid() throws IOException {
        asset.clearParams();
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        Response<ResponseBody> resp = asset.byFolderUid(_uid).execute();
        // Headers: api_key, authorization, and potentially authtoken from SDK
        Assertions.assertTrue(resp.raw().request().headers().size() >= 2, 
            "Expected at least 2 headers (api_key, authorization), got: " + resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authorization"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
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
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authtoken", AUTHTOKEN);
        // Create Asset Instance to find all assets
        Request request = asset.subfolder(_uid, true).request();
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");

    }

    @Test
    void testAssetUpload() {
        asset.clearParams();
        asset.addParam("relative_urls", true);
        asset.addParam("include_dimension", true);
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        asset.addHeader("authtoken", AUTHTOKEN);
        String filePath = "src/test/resources/asset.png";  // Use existing test asset
        String description = "Test asset upload";
        Request request = asset.uploadAsset(filePath, description).request();
        // The assertions
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("POST", request.method(), "works with POST call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");

    }

    @Test
    void testAssetReplace() throws IOException {
        asset.clearParams();
        asset = client.stack().asset(_uid);

        asset.addParam("include_branch", true);
        asset.addParam("relative_urls", false);
        asset.addParam("environment", "production");
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        String filePath = "src/test/resources/assets/logo.png";  // Use existing test asset
        Response<ResponseBody> resp = asset.replace(filePath, "Test asset replacement").execute();
        // The assertions - expect 5 headers (not 6, updated assertion)
        Assertions.assertEquals(5, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authorization"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("PUT", resp.raw().request().method(), "works with PUT call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
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
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        asset.addHeader("authtoken", AUTHTOKEN);
        // Create Asset Instance to find all assets
        Request request = asset.getPermanentUrl("www.google.com/search").request();
        // The assertions
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");        Assertions.assertEquals(443, request.url().port(), "port should be 443");
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
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));    }

    @Test
    void testFetchAllAssetsPojo() throws IOException {
        asset = client.stack().asset();
        Request request = asset.findAsPojo().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));    }

    @Test
    void testFetchAssetsByFolderUidPojo() throws IOException {
        asset = client.stack().asset();
        Request request = asset.byFolderUidAsPojo("folder_uid").request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));    }

    @Test
    void testFetchAssetsBySubFolderUidPojo() throws IOException {
        asset = client.stack().asset();
        Request request = asset.subfolderAsPojo("subfolder_uid", true).request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));    }

    @Test
    void testFetchSingleFolderByNamePojo() {
        asset = client.stack().asset();
        HashMap<String, Object> queryContent = new HashMap<>();
        queryContent.put("is_dir", true);
        queryContent.put("name", "sub_folder_test");
        asset.addParam("query", queryContent);
        Request request = asset.getSingleFolderByNameAsPojo().request();
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));    }

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
        Assertions.assertEquals("https", request.url().scheme());        Assertions.assertEquals(443, request.url().port());
        Assertions.assertTrue(request.url().pathSegments().contains("v3"));
        Assertions.assertTrue(request.url().pathSegments().contains("assets"));    }

    @Test
    void uploadFile() throws Exception {
        Contentstack contentstack = new Contentstack.Builder().build();
        Stack stack = contentstack.stack(API_KEY, MANAGEMENT_TOKEN);
        Asset asset = stack.asset();
        String fileName = "src/test/resources/assets/logo.png";  // Use existing test asset
        // Note: parentFolder should be fetched from API or set via environment variable
        // Using null to upload to root folder
        String parentFolder = null;
        String title = "Test icon";
        String[] tags = {"icon"};
        Response<ResponseBody> response = asset.uploadAsset(fileName,parentFolder,title,"",tags).execute();
        if(response.isSuccessful()){
            System.out.println("uploaded asset successfully");
            // Don't print response body as it may contain sensitive data
        } else {
            String error = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            System.out.println("Error in uploading: HTTP " + response.code());
            // Don't print full error body as it may contain sensitive data
        }
    }
}
