package com.contentstack.cms.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class TestUtils {

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    void testUtilConstructor() {
        try {
            new Util();
        } catch (IllegalAccessException e) {
            Assertions.assertEquals("private=modifier", e.getMessage());
        }
    }

    @Test
    @TestOSBasedUtils.TestOnMac
    void testDefaultUserAgent() {
        String result = Util.defaultUserAgent();
        Assertions.assertEquals("Java1.8.0_261 OS: MAC OS X", result);
    }

    @Test
    void testNullEmptyThrowsException() {
        try {
            Util.nullEmptyThrowsException("customField");
        } catch (Exception e) {
            Assertions.assertEquals(
                    "customField cannot take in an empty String or null value",
                    e.getMessage());
        }
    }

    @Test
    void testAssertionError() {
        try {
            Util.assertionError();
        } catch (AssertionError e) {
            Assertions.assertEquals(
                    "private constructor can't be accessed outside the class",
                    e.getMessage());
        }
    }

}
