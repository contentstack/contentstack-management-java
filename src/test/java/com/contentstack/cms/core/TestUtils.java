package com.contentstack.cms.core;

import static com.contentstack.cms.core.ErrorMessages.PRIVATE_CONSTRUCTOR;

import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URL;

@Tag("unit")
public class TestUtils {

    static CMALogger logger = new CMALogger(TestUtils.class);

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            logger.fine("uri is valid");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    @Contract(value = " -> fail", pure = true)
    void testUtilConstructor() {
        try {
            new Util();
        } catch (IllegalAccessException e) {
            Assertions.assertEquals("private=modifier", e.getMessage());
        }
    }

    @Test
    void testDefaultUserAgent() {
        String result = Util.defaultUserAgent();
        String version = System.getProperty("java.version");
        if (version.equalsIgnoreCase("Java1.8.0_261")) {
            Assertions.assertEquals("Java1.8.0_261 OS: MAC OS X", result);
        }
        if (version.equalsIgnoreCase("Java14.0.2")) {
            Assertions.assertEquals("Java14.0.2 OS: MAC OS X", result);
        }

    }

    @Test
    void testNullEmptyThrowsException() {
        try {
            Util.nullEmptyThrowsException("customField");
        } catch (Exception e) {
            Assertions.assertEquals("customField cannot take in an empty String or null value",
                    e.getMessage());
        }
    }

    @Test
    void testAssertionError() {
        try {
            Util.assertionError();
        } catch (AssertionError e) {
            Assertions.assertEquals(
                    PRIVATE_CONSTRUCTOR,
                    e.getMessage());
        }
    }

    @Test
    void testLogger() {
        CMALogger logger = new CMALogger(StringIndexOutOfBoundsException.class);
        logger.getLogger();
        logger.info("This is testing");
        logger.fine("testing for fine");
        logger.finer("test for finer");
        logger.warning("test for warning");
        logger.severe("test for severe");

    }
}
