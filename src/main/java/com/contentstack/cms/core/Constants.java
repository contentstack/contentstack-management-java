package com.contentstack.cms.core;

// CAUTION: DO NOT MODIFY STRINGS KEYS/VALUES
public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException();
    }

    public static final Boolean RETRY_ON_FAILURE = true;
    public static final String PROTOCOL = "https"; // protocol
    public static final String HOST = "api.contentstack.io"; // host of the url
    public static final String PORT = "443"; // https port
    public static final String VERSION = "v3"; // api version
    public static int TIMEOUT = 30; // default timeout in seconds
    public static final String SDK_NAME = "contentstack-management-java";
    public static final String SDK_VERSION = "v0.0.1";
    public static String BASE_URL = PROTOCOL + "://" + HOST + "/" + VERSION + "/";

    public static final String USER_ALREADY_LOGGED_IN = "User is already loggedIn, "
            + "Please logout then try to login again";
    public static final String LOGIN_FLAG = "Please login to access user instance";

}
