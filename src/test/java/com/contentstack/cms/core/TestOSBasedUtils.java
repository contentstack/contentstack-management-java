package com.contentstack.cms.core;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The type Test utils.
 */
 class TestOSBasedUtils {

    /**
     * Check version.
     */
    @Test
    @EnabledOnOs(OS.MAC)
     void checkVersion(){
        String defaultUserAgent = Util.defaultUserAgent();
        Assertions.assertEquals("Java1.8.0_261 OS: MAC OS X", defaultUserAgent);
    }

    /**
     * This test only runs with up to date jr es.
     */
    @Test
    @DisabledOnJre(JRE.OTHER)
     void thisTestOnlyRunsWithUpToDateJREs() {
        // this test will only run on Java 8, 9, 10, and 11.
    }

    /**
     * Only on mac os.
     */
    @Test
    @EnabledOnOs(OS.MAC)
    void onlyOnMacOs() {
        // ...
    }

    /**
     * Test on mac.
     */
    @TestOnMac
    void testOnMac() {
        // ...
    }

    /**
     * On linux or mac.
     */
    @Test
    @EnabledOnOs({ OS.LINUX, OS.MAC })
    void onLinuxOrMac() {
        // ...
    }

    /**
     * Not on windows.
     */
    @Test
    @DisabledOnOs(OS.WINDOWS)
    void notOnWindows() {
        // ...
    }

    /**
     * The interface Test on mac.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @EnabledOnOs(OS.MAC)
    @interface TestOnMac {
    }


}
