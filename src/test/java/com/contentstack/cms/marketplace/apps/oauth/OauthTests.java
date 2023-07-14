package com.contentstack.cms.marketplace.apps.oauth;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;


@Tag("unit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OauthTests {

    static Oauth oauth;
    static String ORG_UID = TestClient.ORGANIZATION_UID;
    static String APP_UID = TestClient.AUTHTOKEN;

    @BeforeAll
    static void loadBeforeAll() {
        oauth = TestClient.getClient().organization(ORG_UID)
                .marketplace("api.contentstack.io")
                .app(APP_UID)
                .oauth();
    }


    @Test
    void testFetchOauthConfiguration() {
        Request request = oauth.addHeader("authtoken", ORG_UID)
        .fetchOauthConfiguration(ORG_UID).request();
        Assertions.assertEquals("/manifests/"+ORG_UID+"/oauth", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(ORG_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("oauth", request.url().pathSegments().get(2));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
        Assertions.assertNull( request.url().query());
    }

    @Test
    void testUpdateOauthConfiguration() {
        JSONObject body = new JSONObject();
        body.put("obj1", "objValue");
        oauth.addHeader("authtoken", ORG_UID);
        Request request = oauth.updateOauthConfiguration(body).request();
        Assertions.assertEquals("/manifests/"+APP_UID+"/oauth", request.url().encodedPath());
        Assertions.assertEquals("PUT", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals(APP_UID, request.url().pathSegments().get(1));
        Assertions.assertEquals("oauth", request.url().pathSegments().get(2));
        Assertions.assertNotNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
    }

    @Test
    void testFindScopes() {
        oauth.addHeader("authtoken", ORG_UID);
        Request request = oauth.findScopes().request();
        Assertions.assertEquals("/manifests/oauth/scopes", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(3, request.url().pathSegments().size());
        Assertions.assertEquals("manifests", request.url().pathSegments().get(0));
        Assertions.assertEquals("oauth", request.url().pathSegments().get(1));
        Assertions.assertNull(request.body());
        Assertions.assertNull(request.url().encodedQuery());
    }


}
