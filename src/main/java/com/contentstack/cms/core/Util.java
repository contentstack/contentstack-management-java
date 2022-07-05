package com.contentstack.cms.core;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * The Utility class that contains utility common functions
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Util {

    static final String PRIVATE_CONSTRUCTOR = "private constructor can't be accessed outside the class";
    public static final Boolean RETRY_ON_FAILURE = true;
    public static final String PROTOCOL = "https";
    public static final String HOST = "api.contentstack.io";
    public static final String PORT = "443";
    public static final String VERSION = "v3";
    public static final int TIMEOUT = 30;
    public static final String SDK_NAME = "contentstack-management-java";
    public static final String SDK_VERSION = "1.0.0";
    public static final String ILLEGAL_USER = "Please Login to access stack instance";
    public static final String USER_ALREADY_LOGGED_IN = "User is already loggedIn, "
            + "Please logout then try to login again";
    public static final String LOGIN_FLAG = "Please login to access user instance";
    public static final String PLEASE_LOGIN = "Please Login to access stack instance";


    // KEYS
    public static final String API_KEY = "api_key";
    public static final String AUTHORIZATION = "authorization";
    public static final String AUTHTOKEN = "authtoken";
    public static final String BRANCH = "branch";
    public static final String X_USER_AGENT = "X-User-Agent";
    public static final String USER_AGENT = "User-Agent";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String MULTIPART = "multipart/form-data";
    public static final String CONTENT_TYPE_VALUE = "application/json";


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
     * @param field
     *         throws an exception for not providing a valid input
     */
    public static void nullEmptyThrowsException(@NotNull String field) {
        Objects.requireNonNull(field);
        try {
            throw new CMSRuntimeException(field + " cannot take in an empty String or null value");
        } catch (CMSRuntimeException e) {
            e.printStackTrace();
        }
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
