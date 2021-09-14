package com.contentstack.cms.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;


public class TestUtils {

    /**/
    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Test
    public void testUtilConstructor() {
        try {
            new Util();
        } catch (IllegalAccessException e) {
            Assertions.assertEquals("private=modifier", e.getMessage());
        }
    }


    @Test
    @TestOSBasedUtils.TestOnMac
    public void testDefaultUserAgent() {
        String result = Util.defaultUserAgent();
        Assertions.assertEquals("Java1.8.0_261 OS: MAC OS X", result);
    }

    @Test
    public void testNullEmptyThrowsException() {
        try {
            Util.nullEmptyThrowsException("customField");
        } catch (Exception e) {
            Assertions.assertEquals("customField cannot take in an empty String or null value", e.getMessage());
        }
    }

    @Test
    public void testAssertionError() {
        try {
            Util.assertionError();
        } catch (AssertionError e) {
            Assertions.assertEquals("private constructor can't be accessed outside the class", e.getMessage());
        }
    }


    @Test
    public void testAssertNotNull() {
        try {
            Util.assertNotNull(null, "String content");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("String content may not be null.", e.getMessage());
        }
    }

}
