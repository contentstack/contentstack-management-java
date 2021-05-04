package com.contentstack.cms;

import com.contentstack.cms.core.Constants;
import com.contentstack.cms.core.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ContentstackUnitTest {


    @Test
    void testDefaultClientInstance() {
        Contentstack contentstack = new Contentstack.Builder().build();
        Assertions.assertEquals("api.contentstack.io", contentstack.host);
        Assertions.assertEquals("443", contentstack.port);
        Assertions.assertEquals("v3", contentstack.version);
        Assertions.assertEquals(30, contentstack.timeout);
        Assertions.assertNull(contentstack.authtoken);
        Assertions.assertNotNull(contentstack);
    }

    @Test
    void testDefaultConstantInstance() {
        Assertions.assertNotNull(new Constants());
    }

    @Test
    void testDefaultUtilInstance() {
        Assertions.assertNotNull(new Util());
    }

    @Test
    void testDefaultClientInstanceWithoutAuthtoken() {
        Contentstack contentstack = new Contentstack.Builder().build();
        Assertions.assertNotNull(contentstack);
    }

    @Test
    void testDefaultClientPort() {
        Contentstack contentstack = new Contentstack.Builder().setPort("400").build();
        Assertions.assertEquals("400", contentstack.port);
    }

    @Test
    void testDefaultClientVersion() {
        Contentstack contentstack = new Contentstack.Builder().setVersion("v10").build();
        Assertions.assertEquals("v10", contentstack.version);
    }

    @Test
    void testDefaultClientTimeout() {
        Contentstack contentstack = new Contentstack.Builder().setTimeout(10).build();
        Assertions.assertEquals(10, contentstack.timeout);
    }

    @Test
    void testDefaultClientInstanceWithAuthtoken() {
        Contentstack contentstack = new Contentstack.Builder().setAuthtoken("fake_authtoken").build();
        Assertions.assertNotNull(contentstack.authtoken);
        Assertions.assertEquals("fake_authtoken", contentstack.authtoken);
    }

    @Test
    void testClientSetPortMethod() {
        Contentstack contentstack = new Contentstack.Builder()
                .setPort("420")
                .setHost("cdn.contentstack.io")
                .setTimeout(10)
                .setVersion("v2")
                .build();
        Assertions.assertEquals(10, contentstack.timeout);
    }


}
