package com.contentstack.cms.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthTest {

    @Test
    public void testAuthNotNull() {
        AuthInterceptor authInterceptor =
                new AuthInterceptor();
        Assertions.assertNotNull(authInterceptor);
    }

    @Test
    public void testAuthtokenNotNull() {
        AuthInterceptor authInterceptor =
                new AuthInterceptor("fake@authtoken");
        Assertions.assertNotNull(authInterceptor.authtoken);
    }

    @Test
    public void testAuthtoken() {
        AuthInterceptor authInterceptor =
                new AuthInterceptor("fake@authtoken");
        Assertions.assertEquals("fake@authtoken",
                authInterceptor.authtoken);
    }
}