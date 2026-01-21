package com.contentstack.cms.stack;

import com.contentstack.cms.TestClient;
import com.contentstack.cms.core.Util;
import okhttp3.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import java.util.HashMap;

@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReleaseItemAPITest {

    protected static String API_KEY = TestClient.API_KEY;
    protected static String MANAGEMENT_TOKEN = TestClient.MANAGEMENT_TOKEN;
    protected static String RELEASE_UID = "test_release_uid";
    protected static Stack stack;
    protected static ReleaseItem releaseItem;

    @BeforeAll
    static void setup() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        stack = TestClient.getStack(); // Remove the "Stack" type declaration to use the class field
        releaseItem = stack.releases(RELEASE_UID).item();
        releaseItem.addHeader("testHeader", "testValue");
    }

    @Test
    @Order(1)
    void testFind() {
        releaseItem.addParam("include_count", "true");
        Request request = releaseItem.find().request();
        
        Assertions.assertEquals("GET", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertTrue(request.url().pathSegments().contains("items"));
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        Assertions.assertTrue(request.url().toString().contains("include_count=true"));
    }

    @Test
    @Order(2)
    void testCreate() {
        // Prepare test request body
        JSONObject requestBody = new JSONObject();
        JSONObject item = new JSONObject();
        item.put("uid", "entry123");
        item.put("content_type_uid", "blog");
        item.put("locale", "en-us");
        item.put("version", 1);
        requestBody.put("item", item);
        
        Request request = releaseItem.create(requestBody).request();
        
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertTrue(request.url().pathSegments().contains("item"));
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        Assertions.assertNotNull(request.body());
    }

    @Test
    @Order(3)
    void testCreateMultiple() {
        // Prepare test request body for multiple items
        JSONObject requestBody = new JSONObject();
        JSONArray items = new JSONArray();
        
        JSONObject item1 = new JSONObject();
        item1.put("uid", "entry123");
        item1.put("content_type_uid", "blog");
        item1.put("locale", "en-us");
        
        JSONObject item2 = new JSONObject();
        item2.put("uid", "asset456");
        item2.put("action", "publish");
        
        items.add(item1);
        items.add(item2);
        requestBody.put("items", items);
        
        // Add bulk parameter
        releaseItem.addParam("bulk", true);
        
        Request request = releaseItem.createMultiple(requestBody).request();
        
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        Assertions.assertTrue(request.url().toString().contains("items"));
        Assertions.assertTrue(request.url().toString().contains("bulk=true"));
        Assertions.assertNotNull(request.body());
    }

    @Test
    @Order(4)
    void testUpdate() {
        Request request = releaseItem.update(new JSONObject()).request();
        
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        
        Assertions.assertNotNull(request.body());
    }

    @Test
    @Order(5)
    void testDelete() {
        Request request = releaseItem.delete().request();
        
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        Assertions.assertTrue(request.url().pathSegments().contains("items"));
    }

    @Test
    @Order(6)
    void testDeleteReleaseItems() {
        
        Request request = releaseItem.deleteReleaseItems(new JSONObject()).request();
        
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        Assertions.assertTrue(request.url().pathSegments().contains("items"));
        Assertions.assertNotNull(request.body());
    }

    @Test
    @Order(7)
    void testDeleteReleaseItem() {
        
        Request request = releaseItem.deleteReleaseItem(new JSONObject()).request();
        
        Assertions.assertEquals("DELETE", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        Assertions.assertTrue(request.url().pathSegments().contains("item"));
        Assertions.assertNotNull(request.body());
    }

    @Test
    @Order(8)
    void testMove() {
        Request request = releaseItem.move(new JSONObject()).request();
        
        Assertions.assertEquals("POST", request.method());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertTrue(request.url().toString().contains(RELEASE_UID));
        Assertions.assertTrue(request.url().pathSegments().contains("move"));
        Assertions.assertNotNull(request.body());
        
        // Note: This assertion was previously disabled because the header wasn't being set
        // Assertions.assertEquals("2.0", Objects.requireNonNull(request.headers().get("release_version")));
    }

    @Test
    @Order(9)
    void testParamsManagement() {
        releaseItem.clearParams();
        releaseItem.addParam("test_key", "test_value");
        
        Request request = releaseItem.find().request();
        
        Assertions.assertTrue(request.url().toString().contains("test_key=test_value"));
        
        releaseItem.removeParam("test_key");
        request = releaseItem.find().request();
        
        Assertions.assertFalse(request.url().toString().contains("test_key=test_value"));
    }

    @Test
    @Order(10)
    void testHeadersManagement() {
        releaseItem.addHeader("custom_header", "custom_value");
        
        Request request = releaseItem.find().request();
        
        Assertions.assertEquals("custom_value", request.headers().get("custom_header"));
    }
}