package com.contentstack.cms.stack;

import com.contentstack.cms.core.Util;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Stack.
 */
public class Stack {

    private final StackService stackService;
    protected String apiKey;

    /**
     * Instantiates a new Stack.
     *
     * @param client
     *         the client
     * @param apiKey
     *         the api key
     */
    public Stack(@NotNull Retrofit client, String apiKey) {
        this.apiKey = apiKey;
        this.stackService = client.create(StackService.class);
    }


    /**
     * <b>Get a single stack</b>
     * <br>
     * The Get a single stack call fetches comprehensive details of a specific stack
     * <br>
     * <b>Note:</b> For SSO-enabled organizations,
     * it is mandatory to pass the organization UID in the header.
     *
     * @return the stack
     */
    public Call<ResponseBody> fetch() {
        return this.stackService.fetch(this.apiKey);
    }


    /**
     * <b>Get a single stack</b>
     * <br>
     * The Get a single stack call fetches comprehensive details of a specific stack
     * <br>
     * <b>Note:</b> For SSO-enabled organizations,
     * it is mandatory to pass the organization UID in the header.
     *
     * @param organizationUid
     *         the organization uid
     * @return the stack
     */
    public Call<ResponseBody> fetch(
            @NotNull String organizationUid) {
        return this.stackService.fetch(this.apiKey, organizationUid);
    }


    /**
     * <b>Get a single stack</b>
     * <br>
     * The Get a single stack call fetches comprehensive details of a specific stack
     * <br>
     * <b>Note:</b> For SSO-enabled organizations,
     * it is mandatory to pass the organization UID in the header.
     *
     * @param orgUID
     *         the organization uid
     * @param query
     *         the query param
     * @return the stack
     */
    public Call<ResponseBody> fetch(
            @NotNull String orgUID,
            @NotNull Map<String, Boolean> query) {
        return this.stackService.fetch(this.apiKey, orgUID, query);
    }


    /**
     * <b>Create stack.</b>
     * <p>
     * The Create stack call creates a new stack in your Contentstack account.
     * <p>
     * In the 'Body' section, provide the schema of the stack in JSON format
     * <p>
     * <b>Note:</b>At any given point of time, an organization can create only one stack per minute.
     *
     * @param organizationUid
     *         the organization uid
     * @param bodyString
     *         the body string
     * @return the stack
     */
    public Call<ResponseBody> create(@NotNull String organizationUid, @NotNull String bodyString) {
        RequestBody body = Util.toRequestBody(bodyString);
        return stackService.create(body, organizationUid);
    }

    /**
     * <b>Update Stack</b>
     * <br>
     * The Update stack call lets you update the name and description of an existing stack.
     * <br>
     * In the 'Body' section, provide the updated schema of the stack in JSON format.
     * <br>
     * <b>Note</b> Warning: The master locale cannot be changed once it is set
     * while stack creation. So, you cannot use this call to change/update the master language.
     *
     * @param bodyString
     *         the body string
     * @return the stack
     */
    public Call<ResponseBody> update(String bodyString) {
        RequestBody body = Util.toRequestBody(bodyString);
        return stackService.update(this.apiKey, body);
    }

    /**
     * <b>Transfer Stack Ownership</b>
     * <br>
     * The Transfer stack ownership to other users call sends the specified user an email invitation for accepting the
     * ownership of a particular stack.
     *
     * <br><br>
     * Once the specified user accepts the invitation by clicking on the link provided in the email, the ownership of
     * the stack gets transferred to the new user. Subsequently, the previous owner will no longer have any permission
     * on the stack.
     * <br>
     * <b>Note</b>
     * <br>
     * <b>
     * Warning: The master locale cannot be changed once it is set while stack creation. So, you cannot use this call to
     * change/update the master language.
     * </b>
     *
     * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#transfer-stack-ownership-to-other-users">
     * (Read more) </a>
     * @param bodyString
     *         the body string
     * @return the stack
     */
    public Call<ResponseBody> transferOwnership(String bodyString) {
        RequestBody body = Util.toRequestBody(bodyString);
        return stackService.transferOwnership(this.apiKey, body);
    }


    /**
     * <b>Accept Stack Ownership</b>
     * <br>
     * The Accept stack owned by other user call allows a user to accept the ownership of a particular stack via an
     * email invitation.
     * <br>
     * <p>
     * Once the user accepts the invitation by clicking on the link, the ownership is transferred to the new user
     * account. Subsequently, the user who transferred the stack will no longer have any permission on the stack.
     * <br>
     *
     * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#transfer-stack-ownership-to-other-users">
     * (Read more) </a>
     *
     * @param ownershipToken
     *         the ownership token received via email by another user.
     * @param uid
     *         the user uid.
     * @return the stack
     */
    public Call<ResponseBody> acceptOwnership(@NotNull String ownershipToken, @NotNull String uid) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("api_key", this.apiKey);
        queryMap.put("uid", uid);
        return stackService.acceptOwnership(ownershipToken, queryMap);
    }


    /**
     * <b>Stack Settings</b>
     * <b>The Get stack settings call retrieves the
     * configuration settings of an existing stack.</b>
     * <br>
     *
     * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#stack-settings">
     * *(Read more)</a>
     *
     * @return the call
     */
    public Call<ResponseBody> setting() {
        return stackService.setting(this.apiKey);
    }

    /**
     * <b>Add/Update Stack Settings</b>
     * <p>The Add stack settings request lets you add additional settings for your existing stack.</p>
     * <br>
     * <p>You can add specific settings for your stack by passing any of the following parameters within the
     * stack_variables section in the <b>Request Body</b>: </p>
     * <p>
     * Additionally, you can pass <b>cs_only_breakline</b>: true under the <b>rte</b> parameter to ensure that only a
     * <br> tag is added inside the <b>Rich Text Editor</b> field when the content manager presses <b>Enter</b>. When
     * this parameter is set to false, the <br> tag is replaced with <p><br></p>
     * <br>
     * <b>Here is a sample of the Request Body:</b>
     *
     * <pre>
     * {"stack_settings":{
     *    "stack_variables":{
     *      "enforce_unique_urls":true,
     *       "sys_rte_allowed_tags":"style | figure | script"
     *    },
     *    "rte":{
     *      "cs_only_breakline":true
     *      }
     *    }
     *  }
     * </pre>
     *
     * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#add-stack-settings">
     * *(Read more)</a>
     * <br>
     * <br>
     * <b>Reset stack settings</b>
     * <p>The Reset stack settings call resets your stack to default settings, and additionally, lets you add
     * parameters to or modify the settings of an existing stack.</p>
     * <br>
     * <b>Here is a sample of the Request Body:</b>
     * <pre>
     *
     * {
     *     "stack_settings":{
     *         "discrete_variables":{},
     *         "stack_variables":{}
     *     }
     * }
     * </pre>
     *
     * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#reset-stack-settings">
     * (Read more)</a>
     *
     * @param requestBody
     *         the request body
     * @return the call
     */
    public Call<ResponseBody> updateSetting(String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return stackService.updateSetting(this.apiKey, body);
    }

    /**
     * <b>Reset stack settings</b>
     * <p>The Reset stack settings call resets your stack to default settings, and additionally, lets you add
     * parameters to or modify the settings of an existing stack.</p>
     * <br>
     * <b>Here is a sample of the Request Body:</b>
     * <pre>
     *
     * {
     *     "stack_settings":{
     *         "discrete_variables":{},
     *         "stack_variables":{}
     *     }
     * }
     * </pre>
     *
     * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#reset-stack-settings">
     * (Read more)</a>
     *
     * @param requestBody
     *         the request body
     * @return the call
     */
    public Call<ResponseBody> resetSetting(String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return stackService.updateSetting(this.apiKey, body);
    }

    /**
     * <b>Share a stack</b>
     * <p>The Share a stack call shares a stack with the specified user to collaborate on the stack.</p>
     * <br>
     * <p>
     * In the 'Body' section, you need to provide the email ID of the user with whom you wish to share the stack along
     * with the role uid that you wish to assign the user.
     * </p>
     * <b>Here is a sample of the Request Body:</b>
     * <pre>
     *    {
     * 	"emails": [
     * 		"manager@example.com"
     * 	],
     * 	"roles": {
     * 		"manager@example.com": [
     * 			"abcdefhgi1234567890"
     * 		]
     *        }
     * }
     * </pre>
     *
     * @param requestBody
     *         the request body
     * @return the call
     */
    public Call<ResponseBody> share(String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return stackService.share(this.apiKey, body);
    }


    /**
     * <b>Unshare a stack</b>
     * <p>The Unshare a stack call unshares a stack with a user and removes the user account from the list of
     * collaborators. Once this call is executed, the user will not be able to view the stack in their account.</p>
     * <br>
     * <p>
     * In the 'Body' section, you need to provide the email ID of the user from whom you wish to unshare the stack.
     * </p>
     * <b>Here is a sample of the Request Body:</b>
     * <pre>
     * {
     * "email": "manager@example.com"
     * }
     * </pre>
     *
     * @param requestBody
     *         the request body
     * @return the call
     */
    public Call<ResponseBody> unshare(String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return stackService.unshare(this.apiKey, body);
    }

    /**
     * <b>Get all users of a stack</b>
     * <br>
     * <p>The Get all users of a stack call fetches
     * the list of all users of a particular stack</p>
     *
     * @return the call
     */
    public Call<ResponseBody> allUsers() {
        return stackService.allUsers(this.apiKey);
    }

    /**
     * <b>Update User Role</b>
     * <br>
     * <p>
     * The Update User Role API Request updates the roles of an existing user account. This API Request will override
     * the existing roles assigned to a user. For example, we have an existing user with the <b>Developer</b> role, and
     * if you execute this API request with "Content Manager" role, the user role will lose <b>Developer</b> rights and
     * the user role be updated to just <b>Content Manager</b>.
     * </p>
     * <br>
     * <p>
     * When executing the API call, under the <b>Body</b> section, enter the UIDs of roles that you want to assign a
     * user. This information should be in JSON format.
     * <b>Here is a sample of the Request Body:</b>
     * <pre>
     * {
     * "users": {
     * "user_uid": ["role_uid1", "role_uid2"]
     * }
     * }
     * </pre>
     *
     * @param requestBody
     *         the request body
     * @return the {@link okhttp3.Call}
     */
    public Call<ResponseBody> roles(String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return stackService.updateUserRoles(this.apiKey, body);
    }
}
