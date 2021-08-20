package com.contentstack.cms.core;

/**
 * The Utility class that contains utility common functions
 */
public class Util {

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
}
