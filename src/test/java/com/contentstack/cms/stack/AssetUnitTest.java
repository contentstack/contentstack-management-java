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
import java.util.Map;

@Tag("unit")
class AssetUnitTest {

        protected static String AUTHTOKEN = Dotenv.load().get("authToken");
        protected static String API_KEY = Dotenv.load().get("api_key");
        protected static String _uid = Dotenv.load().get("auth_token");
        protected static String MANAGEMENT_TOKEN = Dotenv.load().get("auth_token");
        static Asset asset;

        @BeforeAll
        static void setup() {
                HashMap<String, Object> headers = new HashMap<>();
                headers.put(Util.API_KEY, API_KEY);
                headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
                Stack stack = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
                asset = stack.asset();
        }

        @Test
        void testAssetFetchAll() {
                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("folder", "folder_uid_some_example");
                queryParams.put("include_folders", true);
                queryParams.put("environment", "production");
                queryParams.put("version", 1);
                queryParams.put("include_publish_details", true);
                queryParams.put("include_count", true);
                queryParams.put("relative_urls", false);
                queryParams.put("asc_field_uid", "created_at");
                queryParams.put("desc_field_uid", 230);

                Request resp = asset.fetch(queryParams).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("include_path", false);
                queryParams.put("version", 1);
                queryParams.put("include_publish_details", true);
                queryParams.put("environment", "production");
                queryParams.put("relative_urls", false);

                Request resp = asset.single(_uid, queryParams).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Request resp = asset.byFolderUid(_uid).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Request resp = asset.subfolder(_uid, true).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("relative_urls", true);
                queryParams.put("include_dimension", true);

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("asset[parent_uid]", "uid_parent_uid");
                jsonBody.put("asset[title]", "asset_heading");
                jsonBody.put("asset[description]", "some_description");
                jsonBody.put("asset[tags]", "portrait");

                File file = new File("src/test/resources/assets/pexels.jpg");
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body = MultipartBody.Part.createFormData("asset[upload]", file.getName(),
                                requestFile);

                String filePath = "src/test/resources/assets/pexels.jpg";
                RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"),
                                "descriptionString");

                Request resp = asset.uploadAsset(filePath, description, queryParams).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("POST", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Map<String, Object> queryParams = new HashMap<>();
                queryParams.put("relative_urls", true);
                queryParams.put("include_dimension", true);

                Request resp = asset.replace(_uid, queryParams).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("PUT", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                JSONObject body = new JSONObject();

                body.put("relative_urls", true);
                body.put("include_dimension", true);

                Request resp = asset.generatePermanentUrl(_uid, body).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("PUT", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Request resp = asset.getPermanentUrl(_uid, "www.google.com/search").request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Request resp = asset.delete(_uid).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("DELETE", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Request resp = asset.setVersionName(_uid, 2, body).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("POST", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Map<String, Object> query = new HashMap<>();
                query.put("skip", 2);
                query.put("limit", 5);
                query.put("named", false);
                query.put("include_count", false);
                Request resp = asset.getVersionNameDetails(_uid, query).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Request resp = asset.deleteVersionName(_uid, 2).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("DELETE", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
                Assertions.assertEquals("/v3/assets/" + _uid + "/versions/2/name",
                                resp.url().encodedPath());
                Assertions.assertEquals("api.contentstack.io", resp.url().host());
                Assertions.assertNull(
                                resp.url().query());
                Assertions.assertNull(resp.body());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/assets/" + _uid + "/versions/2/name",
                                resp.url().toString());
        }

        @Test
        void testAssetGetReference() {
                Request resp = asset.getReferences(_uid).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Request resp = asset.updateDetails(_uid, _body).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("PUT", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Request resp = asset.publish(_uid, _body).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("POST", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Request resp = asset.unpublish(_uid, _body).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("POST", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                // JSONObject _body = new JSONObject();
                // JSONObject _bodyContent = new JSONObject();
                // _bodyContent.put("locales", "en-us");
                // _bodyContent.put("environments", "development");
                // _body.put("asset", _bodyContent);
                Map<String, Object> query = new HashMap<>();
                query.put("include_path", false);

                Request resp = asset.singleFolder(_uid, query).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
                Assertions.assertEquals("/v3/assets/folders/" + _uid,
                                resp.url().encodedPath());
                Assertions.assertEquals("api.contentstack.io", resp.url().host());
                Assertions.assertEquals("include_path=false",
                                resp.url().query());
                Assertions.assertNull(resp.body());
                Assertions.assertEquals(
                                "https://api.contentstack.io/v3/assets/folders/" + _uid + "?include_path=false",
                                resp.url().toString());
        }

        @Test
        void testAssetSingleFolderByName() {

                Map<String, Object> query = new HashMap<>();
                JSONObject queryContent = new JSONObject();
                queryContent.put("is_dir", true);
                queryContent.put("name", "folder_name");
                query.put("query", queryContent);

                Request resp = asset.getSingleFolderByName(query).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Map<String, Object> query = new HashMap<>();
                JSONObject queryContent = new JSONObject();
                queryContent.put("is_dir", true);
                query.put("query", queryContent);
                query.put("folder", _uid);
                query.put("include_folders", true);

                Request resp = asset.getSubfolder(query).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("GET", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Request resp = asset.createFolder(_body).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("POST", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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

                Request resp = asset.updateFolder(_uid, _body).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("PUT", resp.method());
                Assertions.assertEquals(2, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
                Request resp = asset.deleteFolder(_uid).request();
                Assertions.assertTrue(resp.isHttps());
                Assertions.assertEquals("DELETE", resp.method());
                Assertions.assertEquals(3, resp.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("api_key");
                matcher.add("authorization");
                boolean contains = resp.headers().names().containsAll(matcher);
                Assertions.assertTrue(contains);
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
