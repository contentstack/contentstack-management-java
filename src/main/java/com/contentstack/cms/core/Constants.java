package com.contentstack.cms.core;

// CAUTION: DO NOT MODIFY STRINGS KEYS/VALUES
public class Constants {

    public static Boolean RETRY_ON_FAILURE = true;

    // KEYS FROM CORE PACKAGE
    public static String BASE_URL = "https://api.contentstack.io/v3/";
    public static String PROTOCOL = "https";
    public static String HOST = "api.contentstack.io";
    public static String PORT = "443";
    public static String VERSION = "v3";
    public static int TIMEOUT = 30; // default timeout in seconds
    protected static String SDK_NAME = "contentstack-management-java";
    protected static String SDK_VERSION = "v0.0.1";

    // KEYS FROM HeaderInterceptor
    // CAUTION: DO NOT MODIFY STRINGS KEYS/VALUES
    protected static String AUTHTOKEN = "authtoken";
    protected static String X_USER_AGENT_KEY = "X-User-Agent";
    protected static String X_USER_AGENT_VALUE = SDK_NAME + "/" + SDK_VERSION;
    protected static String User_AGENT = "User-Agent";
    protected static String CONTENT_TYPE = "Content-Type";
    protected static String APPLICATION_JSON = "application/json";

}
