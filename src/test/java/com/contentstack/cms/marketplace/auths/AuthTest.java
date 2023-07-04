package com.contentstack.cms.marketplace.auths;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@Tag("unit")
class AuthTest {

    static Auth auth;
    final static String ORG_UID = TestClient.ORGANIZATION_UID;

    @BeforeAll
    static void loadBeforeAll() {
        auth = TestClient.getClient().organization(ORG_UID).marketplace("api.contentstack.io").authorizations()
                .addParam("param1", "value1")
                .addHeader("authtoken", ORG_UID);
    }


    @Test
    void testFindAuthorizedApp() {
        Request request = auth.findAuthorizedApp().request();
        Assertions.assertEquals("/authorized-apps", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(4, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(1, request.url().pathSegments().size());
        Assertions.assertEquals("authorized-apps", request.url().pathSegments().get(0));
        Assertions.assertEquals("authorized-apps", request.url().pathSegments().get(0));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("paraOne=paraTwo&param1=value1", request.url().query());
    }

    @Test
    void testFindAuthorizedAppParameterized() {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, Object> params = new HashMap<>();
        headers.put("paraOne", "paraTwo");
        params.put("paraOne", "paraTwo");
        Request request = auth.addParams(params).addHeaders(headers).addHeader("AUTH", "TOKEN1234").
                findAuthorizedApp().request();
        Assertions.assertEquals("/authorized-apps", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(4, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(1, request.url().pathSegments().size());
        Assertions.assertEquals("authorized-apps", request.url().pathSegments().get(0));
        Assertions.assertEquals("authorized-apps", request.url().pathSegments().get(0));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("paraOne=paraTwo&param1=value1", request.url().query());

    }


}
