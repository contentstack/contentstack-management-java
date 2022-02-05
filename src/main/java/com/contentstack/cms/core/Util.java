package com.contentstack.cms.core;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The Utility class that contains utility common functions
 */
public class Util {

    static final String PRIVATE_CONSTRUCTOR = "private constructor can't be accessed outside the class";
    public static final Boolean RETRY_ON_FAILURE = true;
    public static final String PROTOCOL = "https"; // protocol
    public static final String HOST = "api.contentstack.io"; // host of the url
    public static final String PORT = "443"; // https port
    public static final String VERSION = "v3"; // api version
    public static final int TIMEOUT = 30; // default timeout in seconds
    public static final String SDK_NAME = "contentstack-management-java";
    public static final String SDK_VERSION = "0.0.1";

    public static final String USER_ALREADY_LOGGED_IN = "User is already loggedIn, "
            + "Please logout then try to login again";
    public static final String LOGIN_FLAG = "Please login to access user instance";

    Util() throws IllegalAccessException {
        throw new IllegalAccessException("private=modifier");
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
    public static void nullEmptyThrowsException(@NotNull String field) {
        Objects.requireNonNull(field);
        throw new RuntimeException(field + " cannot take in an empty String or null value");
    }

    /**
     * When private constructor to be initialized {@link AssertionError} otherwise.
     */
    public static void assertionError() {
        throw new AssertionError(PRIVATE_CONSTRUCTOR);
    }

    public static RequestBody toRequestBody(String bodyString) {
        return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"),
                bodyString);
    }

}
