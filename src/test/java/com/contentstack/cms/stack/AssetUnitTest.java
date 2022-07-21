package com.contentstack.cms.stack;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.core.Util;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Tag("unit")
class AssetUnitTest {

    protected static String AUTHTOKEN = Dotenv.load().get("authToken");
    protected static String API_KEY = Dotenv.load().get("apiKey");
    protected static String MANAGEMENT_TOKEN = Dotenv.load().get("authToken");
    protected static String _uid = Dotenv.load().get("authToken");
    static Asset asset;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
        asset = stack.asset(_uid);
    }

    @Test
    void testAssetFetchAll() {
        asset.clearParams();
        asset.addParam("folder", "folder_uid_some_example");
        asset.addParam("include_folders", true);
        asset.addParam("environment", "production");
        asset.addParam("version", 1);
        asset.addParam("include_publish_details", true);
        asset.addParam("include_count", true);
        asset.addParam("relative_urls", false);
        asset.addParam("asc_field_uid", "created_at");
        asset.addParam("desc_field_uid", 230);
        Request resp = asset.find().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals(
                "relative_urls=false&environment=production&folder=folder_uid_some_example&include_publish_details=true&include_folders=true&include_count=true&version=1&asc_field_uid=created_at&desc_field_uid=230",
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets?relative_urls=false&environment=production&folder=folder_uid_some_example&include_publish_details=true&include_folders=true&include_count=true&version=1&asc_field_uid=created_at&desc_field_uid=230",
                resp.url().toString());
    }

    @Test
    void testAssetSingle() {
        asset.clearParams();
        asset.addParam("include_path", false);
        asset.addParam("version", 1);
        asset.addParam("include_publish_details", true);
        asset.addParam("environment", "production");
        asset.addParam("relative_urls", false);

        Request resp = asset.fetch().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals(
                "relative_urls=false&environment=production&include_path=false&include_publish_details=true&version=1",
                resp.url().query());
        Assertions.assertNull(resp.body());

    }

    @Test
    void testAssetSpecificFolder() {
        asset.clearParams();
        Request resp = asset.byFolderUid(_uid).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals(
                "folder=" + _uid,
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets?folder=" + _uid,
                resp.url().toString());
    }

    @Test
    void testAssetSubFolder() {
        asset.clearParams();
        Request resp = asset.subfolder(_uid, true).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals(
                "folder=" + _uid + "&include_folders=true",
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets?folder=" + _uid + "&include_folders=true",
                resp.url().toString());
    }

    @Test
    void testAssetUpload() {
        asset.clearParams();
        asset.addParam("relative_urls", true);
        asset.addParam("include_dimension", true);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("asset[parent_uid]", "uid_parent_uid");
        jsonBody.put("asset[title]", "asset_heading");
        jsonBody.put("asset[description]", "some_description");
        jsonBody.put("asset[tags]", "portrait");

        File file = new File("src/test/resources/assets/pexels.jpg");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("asset[upload]", file.getName(),
                requestFile);

        String filePath = "src/test/resources/assets/pexels.jpg";
        Request resp = asset.uploadAsset(filePath, "the fake description").request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals(
                "relative_urls=true&include_dimension=true",
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets?relative_urls=true&include_dimension=true",
                resp.url().toString());
    }

    @Test
    void testAssetReplace() {
        asset.clearParams();
        asset.addParam("relative_urls", true);
        asset.addParam("include_dimension", true);

        String filePath = "/Users/shaileshmishra/Downloads/calendar.png";
        Request resp = asset.replace(filePath, "The calender has been placed to assets by ishaileshmishra").request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("PUT", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals(
                "relative_urls=true&include_dimension=true",
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid
                        + "?relative_urls=true&include_dimension=true",
                resp.url().toString());
    }

    @Test
    void testAssetGeneratePermanentUrl() {
        asset.clearParams();
        asset.addParam("relative_urls", true);
        asset.addParam("include_dimension", true);
        JSONObject body = new JSONObject();
        Request resp = asset.generatePermanentUrl(body).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("PUT", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid,
                resp.url().toString());
    }

    @Test
    void testAssetDownloadPermanentUrl() {
        asset.clearParams();
        Request resp = asset.getPermanentUrl("www.google.com/search").request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid + "/www.google.com%2Fsearch",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid + "/www.google.com%2Fsearch",
                resp.url().toString());
    }

    @Test
    void testAssetDelete() {
        asset.clearParams();
        Request resp = asset.delete().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("DELETE", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid,
                resp.url().toString());
    }

    @Test
    void testAssetRteInformation() {
        Request resp = asset.rteInformation().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/rt",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/rt",
                resp.url().toString());
    }

    @Test
    void testAssetSetVersionName() {
        JSONObject body = new JSONObject();
        JSONObject versionNameBody = new JSONObject();
        versionNameBody.put("_version_name", "versionName...");
        body.put("upload", versionNameBody);
        Request resp = asset.setVersionName(2, body).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid + "/versions/2/name",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid + "/versions/2/name",
                resp.url().toString());
    }

    @Test
    void testAssetGetDetailsVersionName() {
        asset.clearParams();
        asset.addParam("skip", 2);
        asset.addParam("limit", 5);
        asset.addParam("named", false);
        asset.addParam("include_count", false);
        Request resp = asset.getVersionNameDetails().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid + "/versions",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("named=false&limit=5&skip=2&include_count=false",
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid
                        + "/versions?named=false&limit=5&skip=2&include_count=false",
                resp.url().toString());
    }

    @Test
    void testAssetDeleteVersionName() {
        Request resp = asset.deleteVersionName(2).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("DELETE", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid + "/versions/2/name",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid + "/versions/2/name",
                resp.url().toString());
    }

    @Test
    void testAssetGetReference() {
        Request resp = asset.getReferences().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid + "/references",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid + "/references",
                resp.url().toString());
    }

    @Test
    void testAssetGetByTypeImages() {
        Request resp = asset.getByType("images").request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/images",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/images",
                resp.url().toString());
    }

    @Test
    void testAssetGetByTypeVideo() {
        Request resp = asset.getByType("videos").request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/videos",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/videos",
                resp.url().toString());
    }

    @Test
    void testAssetUpdateDetails() {
        JSONObject _body = new JSONObject();
        JSONObject _bodyContent = new JSONObject();
        _bodyContent.put("title", "title demo example");
        _bodyContent.put("description", "description demo example");
        _body.put("asset", _bodyContent);
        _body.put("version", 2);
        asset.clearParams();
        Request resp = asset.updateDetails(_body).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("PUT", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid,
                resp.url().toString());
    }

    @Test
    void testAssetPublish() {

        JSONObject _body = new JSONObject();
        JSONObject _bodyContent = new JSONObject();
        _bodyContent.put("locales", "en-us");
        _bodyContent.put("environments", "development");
        _body.put("asset", _bodyContent);

        Request resp = asset.publish(_body).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid + "/publish",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid + "/publish",
                resp.url().toString());
    }

    @Test
    void testAssetUnpublish() {

        JSONObject _body = new JSONObject();
        JSONObject _bodyContent = new JSONObject();
        _bodyContent.put("locales", "en-us");
        _bodyContent.put("environments", "development");
        _body.put("asset", _bodyContent);

        Request resp = asset.unpublish(_body).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/" + _uid + "/unpublish",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/" + _uid + "/unpublish",
                resp.url().toString());
    }

    @Test
    void testAssetSingleFolder() {
        asset.clearParams();
        Folder assetFolder = asset.folder(_uid);
        assetFolder.addParam("include_path", false);
        Request resp = assetFolder.fetch().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/folders/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("include_path=false", resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/folders/" + _uid + "?include_path=false",
                resp.url().toString());
    }

    @Test
    void testAssetSingleFolderByName() {
        asset.clearParams();
        JSONObject queryContent = new JSONObject();
        queryContent.put("is_dir", true);
        queryContent.put("name", "folder_name");
        asset.addParam("query", queryContent);
        Request resp = asset.getSingleFolderByName().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("query={\"is_dir\":true,\"name\":\"folder_name\"}",
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets?query={%22is_dir%22:true,%22name%22:%22folder_name%22}",
                resp.url().toString());
    }

    @Test
    void testAssetSubfolder() {

        JSONObject queryContent = new JSONObject();
        queryContent.put("is_dir", true);
        asset.addParam("query", queryContent);
        asset.addParam("folder", _uid);
        asset.addParam("include_folders", true);
        Request resp = asset.getSubfolder().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("GET", resp.method());
        Assertions.assertEquals(1, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertEquals("folder=" + _uid + "&query={\"is_dir\":true}&include_folders=true",
                resp.url().query());
        Assertions.assertNull(resp.body());

    }

    @Test
    void testAssetCreateFolder() {

        JSONObject _body = new JSONObject();
        JSONObject _bodyContent = new JSONObject();
        _bodyContent.put("locales", "en-us");
        _bodyContent.put("environments", "development");
        _body.put("asset", _bodyContent);
        asset.clearParams();
        Request resp = asset.folder().create(_body).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("POST", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/folders",
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/folders",
                resp.url().toString());
    }

    @Test
    void testAssetUpdateFolder() {

        JSONObject _body = new JSONObject();
        JSONObject _bodyContent = new JSONObject();
        _bodyContent.put("locales", "en-us");
        _bodyContent.put("environments", "development");
        _body.put("asset", _bodyContent);

        asset.clearParams();
        Request resp = asset.folder(_uid).update(_body).request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("PUT", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/folders/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/folders/" + _uid,
                resp.url().toString());
    }

    @Test
    void testAssetDeleteFolder() {
        Request resp = asset.folder(_uid).delete().request();
        Assertions.assertTrue(resp.isHttps());
        Assertions.assertEquals("DELETE", resp.method());
        Assertions.assertEquals(0, resp.headers().size());
        Collection<String> matcher = new ArrayList<>();
        matcher.add("api_key");
        matcher.add("authorization");
        boolean contains = resp.headers().names().containsAll(matcher);
        Assertions.assertEquals("/v3/assets/folders/" + _uid,
                resp.url().encodedPath());
        Assertions.assertEquals("api.contentstack.io", resp.url().host());
        Assertions.assertNull(
                resp.url().query());
        Assertions.assertNull(resp.body());
        Assertions.assertEquals(
                "https://api.contentstack.io/v3/assets/folders/" + _uid,
                resp.url().toString());
    }
}
