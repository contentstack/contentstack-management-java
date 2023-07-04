package com.contentstack.cms.marketplace.installations.location;

import com.contentstack.cms.TestClient;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class LocationTests {

    static Location location;
    final static String ORG_UID = TestClient.ORGANIZATION_UID;

    @BeforeAll
    static void loadBeforeAll() {
        location = TestClient.getClient().organization(ORG_UID)
                .marketplace("api.contentstack.io")
                .installation(ORG_UID).location()
                .addParam("param1", "value1")
                .addHeader("authtoken", ORG_UID);
    }


    @Test
    void testFindAuthorizedApp() {
        Request request = location.fetchConfigurationLocation().request();
        Assertions.assertEquals("/installations/blte0fdf06879c18c1e/locations/configuration", request.url().encodedPath());
        Assertions.assertEquals("GET", request.method());
        Assertions.assertEquals(2, request.headers().names().size());
        Assertions.assertTrue(request.url().isHttps());
        Assertions.assertEquals("api.contentstack.io", request.url().host());
        Assertions.assertEquals(4, request.url().pathSegments().size());
        Assertions.assertEquals("installations", request.url().pathSegments().get(0));
        Assertions.assertEquals("blte0fdf06879c18c1e", request.url().pathSegments().get(1));
        Assertions.assertEquals("locations", request.url().pathSegments().get(2));
        Assertions.assertEquals("configuration", request.url().pathSegments().get(3));
        Assertions.assertNull(request.body());
        Assertions.assertNotNull(request.url().encodedQuery());
        Assertions.assertEquals("param1=value1", request.url().query());
    }


}
