package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
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
        Assertions.assertEquals("api.contentstack.io", request.url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(request.url().query()).isEmpty(),
                "query params should not be empty");
    }

    @Order(2)
    @Test
    void testFetch() {
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
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", request.url().host(), "host should be anything but not null");
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
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        Response<ResponseBody> resp = asset.byFolderUid(_uid).execute();
        Assertions.assertEquals(5, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authorization"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(),
                "host should be anything but not null");
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
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authtoken", AUTHTOKEN);
        // Create Asset Instance to find all assets
        Request request = asset.subfolder(_uid, true).request();
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("GET", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", request.url().host(), "host should be anything but not null");
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
        // The assertions
        Assertions.assertEquals(3, request.headers().size());
        Assertions.assertTrue(request.headers().names().contains("api_key"));
        Assertions.assertTrue(request.headers().names().contains("authtoken"));
        Assertions.assertTrue(request.isHttps(), "always works on https");
        Assertions.assertEquals("POST", request.method(), "works with GET call");
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", request.url().host(), "host should be anything but not null");
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
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        String filePath = "/Users/shaileshmishra/Downloads/calendar.png";
        Response<ResponseBody> resp = asset.replace(filePath, "Assets created by ishaileshmishra").execute();
        // The assertions
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("PUT", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(),
                "host should be anything but not null");
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
        // The assertions
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("PUT", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(),
                "host should be anything but not null");
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
        Assertions.assertEquals("https", request.url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", request.url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, request.url().port(), "port should be 443");
        Assertions.assertTrue(request.url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(request.url().pathSegments().contains("assets"), "url segment should contain assets");
    }


    @Test
    void testAssetUploadWithMultipleParams() throws IOException {
        String description = "The calender has been placed to assets by ishaileshmishra";
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

}
