package com.contentstack.cms.core;

/**
 * The type Constants.
 */
// CAUTION: DO NOT MODIFY STRINGS KEYS/VALUES
public class Constants {

    /**
     * The constant BASE_URL.
     */
// KEYS FROM CORE PACKAGE
    public static String BASE_URL = "https://api.contentstack.io/v3/";
    /**
     * The constant PROTOCOL.
     */
    public static String PROTOCOL = "https";
    /**
     * The constant HOST.
     */
    public static String HOST = "api.contentstack.io";
    /**
     * The constant PORT.
     */
    public static String PORT = "443";
    /**
     * The constant VERSION.
     */
    public static String VERSION = "v3";
    /**
     * The constant TIMEOUT.
     */
    public static int TIMEOUT = 30; // default timeout in seconds
    /**
     * The constant SDK_NAME.
     */
    protected static String SDK_NAME = "contentstack-management-java";
    /**
     * The constant SDK_VERSION.
     */
// Update everytime when new build of package uploaded
    protected static String SDK_VERSION = "0.0.1";


    /**
     * The constant AUTHTOKEN.
     */
// KEYS FROM HeaderInterceptor
    // CAUTION: DO NOT MODIFY STRINGS KEYS/VALUES
    protected static String AUTHTOKEN = "authtoken";
    /**
     * The constant X_USER_AGENT_KEY.
     */
    protected static String X_USER_AGENT_KEY = "X-User-Agent";
    /**
     * The constant X_USER_AGENT_VALUE.
     */
    protected static String X_USER_AGENT_VALUE = SDK_NAME + "/v" + SDK_VERSION;
    /**
     * The constant User_AGENT.
     */
    protected static String User_AGENT = "User-Agent";
    /**
     * The constant CONTENT_TYPE.
     */
    protected static String CONTENT_TYPE = "Content-Type";
    /**
     * The constant APPLICATION_JSON.
     */
    protected static String APPLICATION_JSON = "application/json";

}
