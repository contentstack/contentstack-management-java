package com.contentstack.cms.user;

import com.contentstack.cms.models.LoginDetails;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/*
 * All accounts registered with Contentstack are known as Users. A stack can
 * have many users with varying permissions and roles.
 */
public class User {

    private final UserService userService;

    /*
     * Instantiates a new User.
     *
     * @param client the client
     */
    public User(Retrofit client) {
        this.userService = client.create(UserService.class);
    }

    /*
     * The Log in to your account request is used to sign in to your Contentstack
     * account and obtain the authtoken.
     *
     * @param emailId  the email id
     * @param password the password
     * @return the call
     */
    public Call<LoginDetails> login(@NotNull String emailId, @NotNull String password) {
        HashMap<String, HashMap<String, String>> userSession = new HashMap<>();
        userSession.put("user", setCredentials(emailId, password));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(userSession)).toString());
        return this.userService.login(body);
    }

    /*
     * Login call.
     *
     * @param emailId  the email id
     * @param password the password
     * @param tfaToken the tfa token
     * @return the call
     */
    public Call<LoginDetails> login(@NotNull String emailId, @NotNull String password, @NotNull String tfaToken) {
        HashMap<String, HashMap<String, String>> userSession = new HashMap<>();
        userSession.put("user", setCredentials(emailId, password, tfaToken));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(userSession)).toString());
        return this.userService.login(body);
    }

    private HashMap<String, String> setCredentials(@NotNull String... arguments) {
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", arguments[0]);
        credentials.put("password", arguments[1]);
        if (arguments.length > 2) {
            credentials.put("tfa_token", arguments[2]);
        }
        return credentials;
    }

    /*
     * The Get user call returns comprehensive information of an existing user
     * account. The information returned includes details of the stacks owned by and
     * shared with the specified user account. <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> getUser() {
        return this.userService.getUser();
    }

    /*
     * The Get user call returns comprehensive information of an existing user
     * account. The information returned includes details of the stacks and
     * organisation owned by and shared with the specified user account.
     *
     * @param query parameters
     * @return {@link UserService}
     */
    public Call<ResponseBody> getUserOrganizations(@NotNull HashMap<String, Object> query) {
        return this.userService.getUserOrganization(query);
    }

    /*
     * The Update User API Request updates the details of an existing user account.
     * Only the information entered here will be updated, the existing data will
     * remain unaffected. <br>
     * When executing the API call, under the 'Body' section, enter the information
     * of the user that you wish to update. This information should be in JSON
     * format. <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> updateUser() {
        return userService.updateUser();
    }

    /*
     * The activate_token a user account call activates the account of a user after
     * signing up. For account activation, you will require the token received in
     * the activation email. <br>
     *
     * @param activationToken Enter the activation token received on the registered
     *                        email address. You can find the activation token in
     *                        the activation URL sent to the email address used
     *                        while signing up. <br>
     *                        Example:[activationToken]
     * @return {@link UserService}
     */
    public Call<ResponseBody> activateUser(@NotNull String activationToken) {
        return userService.activateUser(activationToken);
    }

    /*
     * The Request for a password call sends a request for a temporary password to
     * log in to an account in case a user has forgotten the login password. <br>
     * Using this temporary password, you can log in to your account and set a new
     * password for your Contentstack account. <br>
     * In the 'Body' section, provide the user's email address in JSON format <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> requestPassword() {
        return userService.requestPassword();
    }

    /*
     * The Reset password call sends a request for resetting the password of your
     * Contentstack account. <br>
     * When executing the call, in the 'Body' section, you need to provide the token
     * that you receive via email, your new password, and password confirmation in
     * JSON format. <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> resetPassword() {
        return userService.resetPassword();
    }

    /*
     * The Log out of your account call is used to sign out the user of Contentstack
     * account
     *
     * @param authtoken : authtoken
     * @return {@link UserService}
     */
    public Call<ResponseBody> logoutWithAuthtoken(String authtoken) {
        return userService.logout(authtoken);
    }

    /*
     * The Log out of your account call is used to sign out the user of Contentstack
     * account
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> logout() {
        return userService.logout();
    }

}
