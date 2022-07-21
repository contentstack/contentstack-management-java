package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.models.LoginDetails;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Tag("API")
class AssetAPITest {

    private static Contentstack client;
    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("managementToken");
    protected static String _uid = Dotenv.load().get("authToken");
    static Asset asset;

    @BeforeAll
    static void setup() throws IOException {
        String _USERNAME = Dotenv.load().get("username");
        String _PASSWORD = Dotenv.load().get("password");
        client = new Contentstack.Builder().build();
        Response<LoginDetails> response = client.login(_USERNAME, _PASSWORD);
        assert response.body() != null;
        _uid = response.body().getUser().getUid();
        asset = client.stack().asset();
    }

    @Order(1)
    @Test
    void testFindAssets() throws IOException {
        asset.clearParams();
        asset.addParam("include_folders", true);
        asset.addParam("environment", "production");
        asset.addParam("version", 1);
        asset.addParam("include_publish_details", true);
        asset.addParam("include_count", true);
        asset.addParam("relative_urls", false);
        asset.addParam("asc_field_uid", "created_at");
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        Response<ResponseBody> resp = asset.find().execute();
        if (resp.isSuccessful()) {
            assert resp.body() != null;
            JsonObject jsonObject = new JsonParser().parse(resp.body().string()).getAsJsonObject();
            String assetUid = jsonObject.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("uid").getAsString();
            System.out.println(assetUid);
            asset = client.stack().asset(assetUid);
        }

        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(), "query params should not be empty");
    }

    @Order(2)
    @Test
    void testFetch() throws IOException {
        asset.clearParams();
        asset.addParam("include_path", false);
        asset.addParam("version", 1);
        asset.addParam("include_publish_details", true);
        asset.addParam("environment", "production");
        asset.addParam("relative_urls", false);

        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        Response<ResponseBody> resp = asset.fetch().execute();
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(), "query params should not be empty");

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
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authorization"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(), "query params should not be empty");

    }

    @Test
    void testAssetSubFolder() throws IOException {
        asset.clearParams();
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        //asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        Response<ResponseBody> resp = asset.subfolder(_uid, true).execute();
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(), "query params should not be empty");

    }

    @Test
    @Disabled("disabled to avoid unnecessary asset creation, Tested working fine")
    void testAssetUpload() throws IOException {
        asset.clearParams();
        asset.addParam("relative_urls", true);
        asset.addParam("include_dimension", true);
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        String filePath = "/Users/shaileshmishra/Downloads/calendar.png";
        String description =
                "The calender has been placed to assets by ***REMOVED***";
        Response<ResponseBody> resp = asset.uploadAsset(filePath, description).execute();

        // The assertions
        Assertions.assertEquals(5, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(), "query params should not be empty");

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
        Response<ResponseBody> resp = asset.replace(filePath, "Assets created by ***REMOVED***").execute();
        // The assertions
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("PUT", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(), "query params should not be empty");

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
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
        Assertions.assertFalse(Objects.requireNonNull(resp.raw().request().url().query()).isEmpty(), "query params should not be empty");
    }

    @Test
    void testAssetDownloadPermanentUrl() throws IOException {
        asset = client.stack().asset(_uid);
        // Headers api_key(required) & authorization(required)
        asset.addHeader("api_key", API_KEY);
        asset.addHeader("authorization", MANAGEMENT_TOKEN);
        // Create Asset Instance to find all assets
        Response<ResponseBody> resp = asset.getPermanentUrl("www.google.com/search").execute();
        // The assertions
        Assertions.assertEquals(6, resp.raw().request().headers().size());
        Assertions.assertTrue(resp.raw().request().headers().names().contains("api_key"));
        Assertions.assertTrue(resp.raw().request().headers().names().contains("authtoken"));
        Assertions.assertTrue(resp.raw().request().isHttps(), "always works on https");
        Assertions.assertEquals("GET", resp.raw().request().method(), "works with GET call");
        Assertions.assertEquals("https", resp.raw().request().url().scheme(), "the scheme should be https");
        Assertions.assertEquals("api.contentstack.io", resp.raw().request().url().host(), "host should be anything but not null");
        Assertions.assertEquals(443, resp.raw().request().url().port(), "port should be 443");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("v3"), "the first segment of url should be v3");
        Assertions.assertTrue(resp.raw().request().url().pathSegments().contains("assets"), "url segment should contain assets");
    }


}
