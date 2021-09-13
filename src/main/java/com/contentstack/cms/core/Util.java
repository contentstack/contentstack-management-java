package com.contentstack.cms.core;

/**
 * The Utility class that contains utility common functions
 */
public class Util {

    static final String PRIVATE_CONSTRUCTOR = "private constructor can't be accessed outside the class";

    private Util() {
    }

    /**
     * Default user agent string.
     *
     * @return the string
     */
    protected static String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        String operatingSystem = System.getProperty("os.name").toUpperCase();
        return agent != null ? agent : ("Java" + System.getProperty("java.version") + " OS: " + operatingSystem);
    }

    /**
     * @param field throws an exception for not providing a valid input
     */
    public static void nullEmptyThrowsException(String field) {
        throw new RuntimeException(field + " cannot take in an empty String or null value");
    }

    /**
     * When private constructor to be initialized
     * {@link AssertionError} otherwise.
     */
    public static void assertionError() {
        throw new AssertionError(PRIVATE_CONSTRUCTOR);
    }

    /**
     * Asserts that the given {@code object} with name {@code param} is not null, throws
     * {@link IllegalArgumentException} otherwise.
     */
    public static void assertNotNull(Object object, String param) {
        if (object == null) {
            throw new IllegalArgumentException(String.format(
                    "%s may not be null.", param));
        }
    }
}
