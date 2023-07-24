package com.contentstack.cms.marketplace.request;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

@Tag("unit")
public class AppRequestTest {

        static AppRequest appRequest;
        static String requestId = TestClient.USER_ID;

        @BeforeAll
        static void setUp() {
                Contentstack client = TestClient.getClient();
                appRequest = client.organization(TestClient.ORGANIZATION_UID).marketplace().request();
        }

        @Test
        void testCreateRequest() {
                JSONObject payload = new JSONObject();
                payload.put("app_uid", "fake_app_uid");
                payload.put("target_uid", "fake_target_uid");
                Request request = appRequest.addHeader("authtoken", "fake@token").create(payload).request();
                Assertions.assertTrue(request.isHttps());
                Assertions.assertEquals("POST", request.method());
                Assertions.assertEquals(2, request.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("authtoken");
                matcher.add("organization_uid");
                boolean contains = request.headers().names().containsAll(matcher);
                Assertions.assertEquals("/requests",
                                request.url().encodedPath());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertNull(request.url().query());
                Assertions.assertNotNull(request.body());
                Assertions.assertEquals(
                                "https://api.contentstack.io/requests",
                                request.url().toString());
        }

        @Test
        void testListRequestedStacks() {
                Request request = appRequest.addHeader("authtoken", "fake@token").findRequestedStacks().request();
                Assertions.assertTrue(request.isHttps());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertEquals(2, request.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("authtoken");
                matcher.add("organization_uid");
                boolean contains = request.headers().names().containsAll(matcher);
                Assertions.assertEquals("/requests/view/stacks",
                                request.url().encodedPath());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertNull(request.url().query());
                Assertions.assertNull(request.body());
                Assertions.assertEquals(
                                "https://api.contentstack.io/requests/view/stacks",
                                request.url().toString());
        }

        @Test
        void testFindRequestedStacks() {
                Request request = appRequest.addHeader("authtoken", "fake@token").find().request();
                Assertions.assertTrue(request.isHttps());
                Assertions.assertEquals("GET", request.method());
                Assertions.assertEquals(2, request.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("authtoken");
                matcher.add("organization_uid");
                boolean contains = request.headers().names().containsAll(matcher);
                Assertions.assertEquals("/requests",
                                request.url().encodedPath());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertNull(request.url().query());
                Assertions.assertNull(request.body());
                Assertions.assertEquals(
                                "https://api.contentstack.io/requests",
                                request.url().toString());
        }

        @Test
        void testDeleteRequest() {
                
                Request request = appRequest.addHeader("authtoken", "fake@token").delete(requestId).request();
                Assertions.assertTrue(request.isHttps());
                Assertions.assertEquals("DELETE", request.method());
                Assertions.assertEquals(2, request.headers().size());
                Collection<String> matcher = new ArrayList<>();
                matcher.add("authtoken");
                matcher.add("organization_uid");
                Assertions.assertEquals("/requests/"+requestId,
                                request.url().encodedPath());
                Assertions.assertEquals("api.contentstack.io", request.url().host());
                Assertions.assertNull(request.url().query());
                Assertions.assertNull(request.body());
                Assertions.assertEquals(
                                "https://api.contentstack.io/requests/"+requestId,
                                request.url().toString());
        }

}