package com.contentstack.cms;

import com.contentstack.cms.core.Util;
import com.contentstack.cms.stack.Stack;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TestClient {

    final static Dotenv env = Dotenv.load();

    public final static String ORGANIZATION_UID = (env.get("organizationUid") != null) ? env.get("organizationUid")
            : "orgId999999999";
    public final static String AUTHTOKEN = (env.get("authToken") != null) ? env.get("authToken") : "auth999999999";
    public final static String USER_ID = (env.get("userId") != null) ? env.get("userId") : "c11e668e0295477f";
    public final static String OWNERSHIP = (env.get("ownershipToken") != null) ? env.get("ownershipToken")
            : "ownershipTokenId";
    public final static String API_KEY = (env.get("apiKey") != null) ? env.get("apiKey") : "apiKey99999999";
    public final static String MANAGEMENT_TOKEN = (env.get("managementToken") != null) ? env.get("managementToken")
            : "managementToken99999999";

    public final static String DEV_HOST = "api.contentstack.io";
    // (env.get("dev_host") != null) ? env.get("dev_host") : "api.contentstack.io";
    private static Contentstack instance;
    private static Stack stackInstance;

    private TestClient() {
        // Private constructor to prevent direct instantiation
    }

    public static Contentstack getClient() {
        if (instance == null) {
            synchronized (Contentstack.class) {
                if (instance == null) {
                    instance = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
                }
            }
        }
        return instance;
    }

    public static Contentstack getCustomClient() {
        if (instance == null) {
            synchronized (Contentstack.class) {
                if (instance == null) {
                    instance = new Contentstack.Builder()
                            .setAuthtoken(AUTHTOKEN)
                            .setConnectionPool(5, 400, TimeUnit.MILLISECONDS)
                            .setHost("kpm.ishaileshmishra.io/path/another").build();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        getCustomClient();
    }

    public static Stack getStack() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        if (stackInstance == null) {
            synchronized (Stack.class) {
                if (stackInstance == null) {
                    stackInstance = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build().stack(headers);
                }
            }
        }
        return stackInstance;
    }

}
