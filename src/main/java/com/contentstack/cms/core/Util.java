package com.contentstack.cms.core;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The utility class that contains utility common functions
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-20
 */
public class Util {

    // The line `public static final String SDK_VERSION = "1.0.0";`
    // named `SDK_VERSION` of type `String`. The value of this constant is set to
    // "1.0.0".
    public static final String SDK_VERSION = "1.0.0";

    static final String PRIVATE_CONSTRUCTOR = "private constructor can't be accessed outside the class";
    public static final Boolean RETRY_ON_FAILURE = true;
    public static final String PROTOCOL = "https";
    public static final String HOST = "api.contentstack.io";
    public static final String PORT = "443";
    public static final String VERSION = "v3";
    public static final int TIMEOUT = 30;
    public static final String SDK_NAME = "contentstack-management-java";
    public static final String ILLEGAL_USER = "Please Login to access stack instance";
    public static final String USER_ALREADY_LOGGED_IN = "User is already loggedIn, "
            + "Please logout then try to login again";
    public static final String LOGIN_FLAG = "Please login to access user instance";
    public static final String PLEASE_LOGIN = "Please Login to access stack instance";

    // CONSTANT KEYS
    public static final String API_KEY = "api_key";
    public static final String AUTHORIZATION = "authorization";
    public static final String AUTHTOKEN = "authtoken";
    public static final String BRANCH = "branch";
    public static final String X_USER_AGENT = "X-User-Agent";
    public static final String USER_AGENT = "User-Agent";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String MULTIPART = "multipart/form-data";
    public static final String CONTENT_TYPE_VALUE = "application/json";

    // Error Messages
    public static final String MISSING_INSTALLATION_ID = "installation uid is required";
    public static final String ERROR_INSTALLATION = "installation uid is required";
    public static final String MISSING_ORG_ID = "organization uid is required";

    // The code `Util() throws IllegalAccessException` is a constructor for the
    // `Util` class that throws an
    // `IllegalAccessException` when called. This constructor is marked as private,
    // which means it cannot
    // be accessed outside the class. The purpose of throwing the
    // `IllegalAccessException` is to prevent
    // the instantiation of the `Util` class from outside the class itself.
    Util() throws IllegalAccessException {
        throw new IllegalAccessException("private=modifier");
    }

    /**
     * The function returns the default user agent string for a Java application,
     * including the Java
     * version and operating system.
     *
     * @return The method is returning a string value. The string value being
     * returned is either the value
     * of the "http.agent" system property if it is not null, or a default
     * string value "Java" concatenated
     * with the Java version and the operating system name.
     */
    protected static String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        String operatingSystem = System.getProperty("os.name").toUpperCase();
        return agent != null ? agent : ("Java" + System.getProperty("java.version") + " OS: " + operatingSystem);
    }

    /**
     * The function checks if a given string is null or empty and throws an
     * exception if it is.
     *
     * @param field The parameter "field" is of type String and is annotated with
     *              the "@NotNull"
     *              annotation.
     */
    public static void nullEmptyThrowsException(@NotNull String field) {
        Objects.requireNonNull(field);
        try {
            throw new CMARuntimeException(field + " cannot take in an empty String or null value");
        } catch (CMARuntimeException e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    /**
     * The function throws an AssertionError with a private constructor.
     */
    public static void assertionError() {
        throw new AssertionError(PRIVATE_CONSTRUCTOR);
    }

    /**
     * The function converts a string into a RequestBody object with a specified
     * media type.
     *
     * @param bodyString The `bodyString` parameter is a string that represents the
     *                   body of the request. It
     *                   can contain any data that you want to send in the request
     *                   body, such as JSON, XML, or plain text.
     * @return The method is returning a RequestBody object.
     */
    public static RequestBody toRequestBody(String bodyString) {
        return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), bodyString);
    }

}
