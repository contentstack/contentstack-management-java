package com.contentstack.cms.user;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * All accounts registered with Contentstack are known as Users.
 * A stack can have many users with varying permissions and roles.
 */
public class User {

    private final UserService userService;

    public User() {
        throw new UnsupportedOperationException("Can not initialise User Class");
    }

    public User(Retrofit client) {
        this.userService = client.create(UserService.class);
    }

    /**
     * The Get user call returns comprehensive information of an existing user account.
     * The information returned includes details of the stacks owned by and shared
     * with the specified user account.
     * <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> getUser() {
        return this.userService.getUser();
    }

    /**
     * The Get user call returns comprehensive information of an existing user account.
     * The information returned includes details of the stacks and organisation owned by and shared
     * with the specified user account.
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> getUserOrganizations() {
        Map<String, String> map = new HashMap<>();
        map.put("include_orgs", "true");
        map.put("include_orgs_roles", "true");
        return this.userService.getUserOrganization(map);
    }

    /**
     * The Update User API Request updates the details of an existing user account.
     * Only the information entered here will be updated, the existing data will remain unaffected.
     * <br>
     * When executing the API call, under the 'Body' section, enter the information of
     * the user that you wish to update. This information should be in JSON format.
     * <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> updateUser() {
        return userService.updateUser();
    }

    /**
     * The Activate a user account call activates the account of a user after signing up.
     * For account activation, you will require the token received in the activation email.
     * <br>
     *
     * @param activationToken Enter the activation token received on the
     *                        registered email address. You can find the activation token in the
     *                        activation URL sent to the email address used while signing up.
     *                        <br>
     *                        Example:bltf36705c7361d4734
     * @return {@link UserService}
     */
    public Call<ResponseBody> activateUser(String activationToken) {
        return userService.activateUser(activationToken);
    }

    /**
     * The Request for a password call sends a request for a temporary password to
     * log in to an account in case a user has forgotten the login password.
     * <br>
     * Using this temporary password, you can log in to your account and set
     * a new password for your Contentstack account.
     * <br>
     * In the 'Body' section, provide the user's email address in JSON format
     * <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> requestPassword() {
        return userService.requestPassword();
    }

    /**
     * The Reset password call sends a request for resetting the password of your
     * Contentstack account.
     * <br>
     * When executing the call, in the 'Body' section, you need to provide the
     * token that you receive via email, your new password, and password
     * confirmation in JSON format.
     * <br>
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> resetPassword() {
        return userService.resetPassword();
    }

    /**
     * The Log out of your account call is used to sign out the user of Contentstack account
     *
     * @param authtoken: authtoken
     * @return {@link UserService}
     */
    public Call<ResponseBody> logoutWithAuthtoken(String authtoken) {
        return userService.logout(authtoken);
    }

    /**
     * The Log out of your account call is used to sign out the user of Contentstack account
     *
     * @return {@link UserService}
     */
    public Call<ResponseBody> logout() {
        return userService.logout();
    }


}
