package com.contentstack.cms.marketplace.auths;

import com.contentstack.cms.Contentstack;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class AuthTest {

    private static Auth auth;


    @BeforeAll
    public static void setUp() {
        final String ORG_ID = Dotenv.load().get("organizationUid");
        String authToken = Dotenv.load().get("authToken");
        final Contentstack client = new Contentstack.Builder().setAuthtoken(Dotenv.load().get("authToken")).build();
        auth = client.organization(ORG_ID).marketplace().authorizations().addHeader("authtoken", authToken);
    }

    @Test
    void testInstallationFetch() {
        Request requestInfo = auth.findAuthorizedApp().request();
        Assertions.assertEquals("GET", requestInfo.method());
        Assertions.assertNull(requestInfo.body());
        Assertions.assertNotNull(requestInfo.headers().get("organization_uid"));
        Assertions.assertNotNull(requestInfo.headers().get("authtoken"));
        Assertions.assertEquals("/authorized-apps", requestInfo.url().toString().split("v3")[1]);
        Assertions.assertTrue(requestInfo.isHttps());
    }

}
