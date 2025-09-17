package com.contentstack.cms.user;

import com.contentstack.cms.BaseImplementation;
import com.contentstack.cms.core.Util;
import com.contentstack.cms.models.LoginDetails;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;
import com.warrenstrange.googleauth.GoogleAuthenticator;

import java.util.HashMap;
import java.util.Map;

/**
 * All accounts registered with Contentstack are known as Users.
 * A stack can have many users with varying permissions and roles.
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-22
 */
public class User implements BaseImplementation<User> {

    protected final UserService userService;
    protected final HashMap<String, String> headers;
    protected final HashMap<String, Object> params;

    /**
     * @param client Retrofit adapts a Java interface to HTTP calls by using
     *               annotations on the declared methods to define how
     *               requests are made. Create instances using
     *               {@linkplain Retrofit.Builder the builder} and pass your
     *               interface to to generate an implementation.
     *               <br>
     *               For example<br>
     *               Contentstack contentstack = new
     *               Contentstack.Builder().build();<br>
     *               contentstack.login("EMAIL_ID", "PASSWORD");
     *               <br>
     *               Response&lt;LoginDetails&gt; user =
     *               contentstack.user().execute();<br>
     *
     *               <br>
     * @author ***REMOVED***
     */
    public User(Retrofit client) {
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.userService = client.create(UserService.class);
    }

    /**
     * The Log in to your account request is used to sign in to your Contentstack
     * account and obtain the authtoken.
     * <br>
     * <b>Note:</b> The authtoken is a mandatory parameter when executing Content
     * Management API calls.
     *
     * @param email    email for user to login
     * @param password password for user to login
     * @return Call
     */
    public Call<LoginDetails> login(@NotNull String email, @NotNull String password) {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        HashMap<String, HashMap<String, String>> userSession = new HashMap<>();
        userSession.put("user", credentials);
        
        JSONObject userDetail = new JSONObject(userSession);
        return this.userService.login(loginHeader(), userDetail);
    }

    private HashMap<String, String> loginHeader() {
        HashMap<String, String> loginHeader = new HashMap<>();
        loginHeader.put(Util.CONTENT_TYPE, Util.CONTENT_TYPE_VALUE);
        return loginHeader;
    }

    /**
     * Login with two-factor authentication. This method provides flexibility to use either:
     * 1. A direct 2FA token using params.put("tfaToken", "123456"), OR
     * 2. An MFA secret to generate TOTP using params.put("mfaSecret", "YOUR_SECRET")
     * 
     * Note: Do not provide both tfaToken and mfaSecret. Choose one authentication method.
     *
     * @param email     email for user to login
     * @param password  password for user to login
     * @param params    Map containing either tfaToken or mfaSecret
     * @return Call containing login details
     * @throws IllegalArgumentException if validation fails or if both tfaToken and mfaSecret are provided
     * 
     * Example:
     * <pre>
     * // Login with direct token
     * Map<String, String> params = new HashMap<>();
     * params.put("tfaToken", "123456");
     * Call<LoginDetails> call = user.login(email, password, params);
     * 
     * // OR login with MFA secret
     * Map<String, String> params = new HashMap<>();
     * params.put("mfaSecret", "YOUR_SECRET");
     * Call<LoginDetails> call = user.login(email, password, params);
     * </pre>
     */
    public Call<LoginDetails> login(@NotNull String email, @NotNull String password, @NotNull Map<String, String> params) {
        // Validate basic inputs
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (params.isEmpty()) {
            throw new IllegalArgumentException("Authentication parameters are required");
        }

        String tfaToken = params.get("tfaToken");
        String mfaSecret = params.get("mfaSecret");
        
        // Check for mutual exclusivity
        boolean hasTfaToken = tfaToken != null && !tfaToken.trim().isEmpty();
        boolean hasMfaSecret = mfaSecret != null && !mfaSecret.trim().isEmpty();
        
        if (hasTfaToken && hasMfaSecret) {
            throw new IllegalArgumentException("Cannot provide both tfaToken and mfaSecret. Use either one.");
        }
        if (!hasTfaToken && !hasMfaSecret) {
            throw new IllegalArgumentException("Must provide either tfaToken or mfaSecret");
        }

        // Generate TOTP if needed
        String finalTfaToken = tfaToken;
        if (!hasTfaToken && hasMfaSecret) {
            finalTfaToken = generateTOTP(mfaSecret);
        }

        // Perform login
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);
        credentials.put("tfa_token", finalTfaToken);

        HashMap<String, HashMap<String, String>> userSession = new HashMap<>();
        userSession.put("user", credentials);
        
        JSONObject userDetail = new JSONObject(userSession);
        return this.userService.login(loginHeader(), userDetail);
    }

    private String generateTOTP(String secret) {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("MFA secret cannot be null or empty");
        }

        try {
            GoogleAuthenticator gAuth = new GoogleAuthenticator();
            String totp = String.format("%06d", gAuth.getTotpPassword(secret));

            // Validate the generated token
            if (!totp.matches("\\d{6}")) {
                throw new IllegalArgumentException("Generated TOTP token is invalid (not 6 digits)");
            }

            return totp;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to generate TOTP token: " + e.getMessage(), e);
        }
    }

    /**
     * The Get user call returns comprehensive information of an existing user
     * account. The information returned
     * includes details of the stacks owned by and shared with the specified user
     * account. <br>
     *
     * @return Call
     */
    public Call<ResponseBody> getUser() {
        return this.userService.getUser();
    }

    /**
     * The Update User API Request updates the details of an existing user account.
     * Only the information entered here
     * will be updated, the existing data will remain unaffected. <br>
     * When executing the API call, under the 'Body'
     * section, enter the information of the user that you wish to update. This
     * information should be in JSON format.
     * <br>
     *
     * @param body The request body
     * @return Call
     */
    public Call<ResponseBody> update(JSONObject body) {
        return userService.update(new HashMap<>(), body);
    }

    /**
     * The activate_token a user account call activates the account of a user after
     * signing up. For account activation,
     * you will require the token received in the activation email. <br>
     *
     * @param activationToken The activation token received on the registered email
     *                        address. You can find the activation token in the
     *                        activation URL sent to the email address used while
     *                        signing up
     * @param body            the {@link JSONObject} body
     * @return Call
     */
    public Call<ResponseBody> activateAccount(@NotNull String activationToken, JSONObject body) {
        return userService.activateAccount(activationToken, body);
    }

    /**
     * The Request for a password call sends a request for a temporary password to
     * log in to an account in case a user
     * has forgotten the login password. <br>
     * Using this temporary password, you can log in to your account and set a
     * new password for your Contentstack account. <br>
     * <ul>
     * <li>Provide the user's email address in JSON format</li>
     * </ul>
     *
     * @param body the request body
     * @return Call
     */
    public Call<ResponseBody> requestPassword(JSONObject body) {
        return userService.requestPassword(body);
    }

    /**
     * The Reset password call sends a request for resetting the password of your
     * Contentstack account. <br>
     * When
     * executing the call, in the 'Body' section, you need to provide the token that
     * you receive via email, your new
     * password, and password confirmation in JSON format. <br>
     *
     * @param body the request body
     * @return Call
     */
    public Call<ResponseBody> resetPassword(@NotNull JSONObject body) {
        return userService.resetPassword(body);
    }

    /**
     * The Log out of your account call is used to sign out the user of Contentstack
     * account
     *
     * @param authtoken The authtoken of the user
     * @return Call
     */
    public Call<ResponseBody> logoutWithAuthtoken(String authtoken) {
        return userService.logout(authtoken);
    }

    /**
     * The Log out of your account call is used to sign out the user of Contentstack
     * account
     *
     * @return Call
     */
    public Call<ResponseBody> logout() {
        return userService.logout();
    }

    @SuppressWarnings("unchecked")
    @Override
    public User addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public User addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public User addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public User addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }

}
