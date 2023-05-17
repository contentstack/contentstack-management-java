package com.contentstack.cms.marketplace.installations.webhook;

import com.contentstack.cms.Contentstack;
import com.contentstack.cms.marketplace.installations.Installation;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class WebhookTests {

    private static Installation installation;
    private static Contentstack client;
    private final static String authtoken = Dotenv.load().get("authToken");

    @BeforeAll
    public static void setUp() {
        client = new Contentstack.Builder().setAuthtoken(authtoken).build();
        String orgId = Dotenv.load().get("organizationUid");
        String manifestId = Dotenv.load().get("userId");
        String installationId = Dotenv.load().get("userId");
    }

    @Test
    void testWebhook() {

    }


}
