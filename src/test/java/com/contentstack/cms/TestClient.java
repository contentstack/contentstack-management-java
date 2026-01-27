package com.contentstack.cms;

import com.contentstack.cms.core.Util;
import com.contentstack.cms.models.LoginDetails;
import com.contentstack.cms.stack.Stack;
import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestClient {

    final static Dotenv env = Dotenv.load();

    // Support both JS SDK format (SCREAMING_SNAKE_CASE) and legacy Java format (camelCase)
    public final static String ORGANIZATION_UID = getEnvValue("ORGANIZATION", "organizationUid", "orgId999999999");
    public final static String USER_ID = getEnvValue("USER_ID", "userId", "c11e668e0295477f");
    public final static String OWNERSHIP = getEnvValue("OWNERSHIP_TOKEN", "ownershipToken", "ownershipTokenId");
    // file deepcode ignore NonCryptoHardcodedSecret/test: <please specify a reason of ignoring this>
    public final static String API_KEY = getEnvValue("API_KEY", "apiKey", "apiKey99999999");
    public final static String MANAGEMENT_TOKEN = getEnvValue("MANAGEMENT_TOKEN", "managementToken", "managementToken99999999");

    public final static String DEV_HOST = getEnvValue("HOST", "dev_host", "api.contentstack.io").trim();
    public final static String VARIANT_GROUP_UID = getEnvValue("VARIANT_GROUP_UID", "variantGroupUid", "variantGroupUid99999999");
    
    // Credentials for normal login (without 2FA)
    public final static String EMAIL = getEnvValue("EMAIL", "email", null);
    public final static String PASSWORD = getEnvValue("PASSWORD", "password", null);
    
    // Credentials for 2FA/TOTP login
    public final static String TFA_EMAIL = getEnvValue("TFA_EMAIL", "tfaEmail", null);
    public final static String TFA_PASSWORD = getEnvValue("TFA_PASSWORD", "tfaPassword", null);
    public final static String MFA_SECRET = getEnvValue("MFA_SECRET", "mfaSecret", null);
    
    // Cached auth token - obtained via login
    private static String cachedAuthToken = null;
    private static boolean loginAttempted = false;
    
    // Static auth token from env (fallback)
    private static final String ENV_AUTHTOKEN = getEnvValue("AUTHTOKEN", "authToken", null);
    
    /**
     * Get the auth token. First tries to use cached token from login,
     * then falls back to static AUTHTOKEN from env, then tries to login.
     * NOTE: This is non-final so it can be updated after login completes.
     */
    public static String AUTHTOKEN = getAuthToken();
    
    private static Contentstack instance;
    private static Stack stackInstance;

    private TestClient() {
        // Private constructor to prevent direct instantiation
    }
    
    /**
     * Helper method to get environment variable with fallback names.
     * Tries primary key first (JS SDK format), then legacy key (Java format), then default.
     */
    private static String getEnvValue(String primaryKey, String legacyKey, String defaultValue) {
        String value = env.get(primaryKey);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        value = env.get(legacyKey);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        return defaultValue;
    }
    
    /**
     * Get auth token - either from cache, env, or by logging in.
     */
    private static String getAuthToken() {
        // 1. Check if we have a cached token
        if (cachedAuthToken != null && !cachedAuthToken.isEmpty()) {
            return cachedAuthToken;
        }
        
        // 2. Check if there's a static token in env
        if (ENV_AUTHTOKEN != null && !ENV_AUTHTOKEN.isEmpty()) {
            System.out.println("[TestClient] Using AUTHTOKEN from environment");
            cachedAuthToken = ENV_AUTHTOKEN;
            return cachedAuthToken;
        }
        
        // 3. Try to login with normal EMAIL/PASSWORD (TFA credentials are only for 2FA-specific tests)
        if (!loginAttempted) {
            loginAttempted = true;
            try {
                if (EMAIL != null && PASSWORD != null) {
                    System.out.println("[TestClient] Attempting normal login with EMAIL and PASSWORD");
                    cachedAuthToken = performLogin(EMAIL, PASSWORD, null);
                    
                    if (cachedAuthToken != null) {
                        System.out.println("[TestClient] Successfully obtained auth token via login");
                        return cachedAuthToken;
                    }
                } else {
                    System.err.println("[TestClient] EMAIL and PASSWORD not found in .env file");
                }
            } catch (Exception e) {
                System.err.println("[TestClient] Login failed: " + e.getMessage());
            }
        }
        
        // 4. Return placeholder if nothing works
        System.err.println("[TestClient] WARNING: No valid auth token available. Tests may fail.");
        return "auth_token_not_available";
    }
    
    /**
     * Perform login using email and password to obtain auth token.
     * Uses the SDK's direct login method on Contentstack class.
     * 
     * @param email The email address
     * @param password The password
     * @param mfaSecret Optional MFA secret for TOTP generation (null if not using 2FA)
     * @return Auth token or null if login fails
     */
    private static String performLogin(String email, String password, String mfaSecret) {
        try {
            System.out.println("[TestClient] Attempting login with EMAIL: " + email);
            
            // Create a temporary Contentstack instance for login (no auth token needed for login)
            Contentstack loginClient = new Contentstack.Builder()
                    .setHost(DEV_HOST)
                    .build();
            
            Response<LoginDetails> response;
            
            // Check if MFA is needed
            if (mfaSecret != null && !mfaSecret.isEmpty()) {
                System.out.println("[TestClient] Using MFA secret for 2FA/TOTP authentication");
                Map<String, String> params = new HashMap<>();
                params.put("mfaSecret", mfaSecret);
                // Use the direct login method on Contentstack class
                response = loginClient.login(email, password, params);
            } else {
                System.out.println("[TestClient] Using normal authentication (no 2FA)");
                // Use the direct login method on Contentstack class
                response = loginClient.login(email, password);
            }
            
            if (response.isSuccessful() && response.body() != null) {
                LoginDetails loginDetails = response.body();
                if (loginDetails.user != null && loginDetails.user.authtoken != null) {
                    System.out.println("[TestClient] Login successful for user: " + loginDetails.user.email);
                    String token = loginDetails.user.authtoken;
                    // Update the static AUTHTOKEN field so all references get the fresh token
                    AUTHTOKEN = token;
                    return token;
                } else {
                    System.err.println("[TestClient] Login response missing auth token");
                }
            } else {
                String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                System.err.println("[TestClient] Login failed with code " + response.code() + ": " + errorBody);
                
                // Check if TFA/MFA is required
                if (errorBody.contains("tfa") || errorBody.contains("mfa") || errorBody.contains("two-factor")) {
                    System.err.println("[TestClient] Two-factor authentication required. Please provide TFA_EMAIL, TFA_PASSWORD, and MFA_SECRET in .env");
                }
            }
        } catch (IOException e) {
            System.err.println("[TestClient] Login request failed: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("[TestClient] SDK state error: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Refresh the auth token by logging in again.
     * Call this if the current token has expired.
     */
    public static String refreshAuthToken() {
        cachedAuthToken = null;
        loginAttempted = false;
        AUTHTOKEN = getAuthToken();
        
        // Update existing instances
        instance = null;
        stackInstance = null;
        
        return AUTHTOKEN;
    }

    public static Contentstack getClient() {
        if (instance == null) {
            synchronized (Contentstack.class) {
                if (instance == null) {
                    instance = new Contentstack.Builder()
                            .setAuthtoken(AUTHTOKEN)
                            .setHost(DEV_HOST)
                            .build();
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
                            .setHost("kpm.***REMOVED***.io/path/another").build();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        // Test login
        System.out.println("Testing login...");
        System.out.println("Auth Token: " + AUTHTOKEN);
    }

    public static Stack getStack() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(Util.API_KEY, API_KEY);
        headers.put(Util.AUTHORIZATION, MANAGEMENT_TOKEN);
        if (stackInstance == null) {
            synchronized (Stack.class) {
                if (stackInstance == null) {
                    stackInstance = new Contentstack.Builder()
                            .setAuthtoken(AUTHTOKEN)
                            .setHost(DEV_HOST)
                            .build()
                            .stack(headers);
                }
            }
        }
        return stackInstance;
    }

}
