package com.contentstack.cms.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class AuthTest {

    @Test
    void testAuthNotNull() {
        AuthInterceptor authInterceptor = new AuthInterceptor();
        Assertions.assertNotNull(authInterceptor);
    }

    @Test
    void testAuthtokenNotNull() {
        AuthInterceptor authInterceptor = new AuthInterceptor("fake@authtoken");
        Assertions.assertNotNull(authInterceptor.authtoken);
    }

    @Test
    void testAuthtoken() {
        AuthInterceptor authInterceptor = new AuthInterceptor("fake@authtoken");
        Assertions.assertEquals("fake@authtoken",
                authInterceptor.authtoken);
    }
}