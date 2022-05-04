package com.contentstack.cms.stack;

import com.contentstack.cms.core.Util;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Stack.
 */
public class Stack {

    private final StackService stackService;
    protected Retrofit client;
    protected HashMap<String, Object> headers;


    /**
     * Instantiates a new Stack.
     *
     * @param client
     *         the Retrofit instance
     */
    public Stack(@NotNull Retrofit client) {
        this.headers = new HashMap<>();
        this.client = client;
        this.stackService = client.create(StackService.class);
    }

    /**
     * Instantiates a new Stack.
     *
     * @param client
     *         the client
     * @param stackHeaders
     *         the headers
     */
    public Stack(@NotNull Retrofit client, @NotNull Map<String, Object> stackHeaders) {
        headers = new HashMap<>();
        this.client = client;
        headers.putAll(stackHeaders);
        this.stackService = client.create(StackService.class);
    }


    /**
     * <b>Content type</b>
     * <p>
     * Content type defines the structure or schema of a page or a section of your web or mobile property. To create
     * content for your application, you are required to first create a content type, and then create entries using the
     * content type.
     * <p>
     *
     * <b>Additional Resource</b>
     * <p>
     * To get an idea of building your content type as per webpage's layout, we recommend you to check out our Content
     * Modeling guide
     *
     * @return the {@link ContentType}
     */
    public ContentType contentType() {
        return new ContentType(this.client, this.headers);
    }

    /**
     * <b>Content type</b>
     * <p>
     * Content type defines the structure or schema of a page or a section of your web or mobile property. To create
     * content for your application, you are required to first create a content type, and then create entries using the
     * content type.
     * <p>
     *
     * <b>Additional Resource</b>
     * <p>
     * To get an idea of building your content type as per webpage's layout, we recommend you to check out our Content
     * Modeling guide
     *
     * @param contentTypeUid
     *         the content type uid
     * @return the content type
     */
    public ContentType contentType(@NotNull String contentTypeUid) {
        return new ContentType(this.client, this.headers, contentTypeUid);
    }

    /**
     * <b>Assets</b><br>
     * Assets refer to all the media files (images, videos, PDFs, audio files, and so on) uploaded in your Contentstack
     * repository for future use.
     * <p>
     * These files can be attached and used in multiple entries.
     *
     * @return {@link Asset} Asset instance
     */
    public Asset asset() {
        return new Asset(this.client, this.headers);
    }

    /**
     * An entry is the actual piece of content created using one of the defined
     *
     * @return {@link Entry} Entry instance
     * @see <a href="https://www.contentstack.com/docs/developers/create-content-types/about-content-types">Content
     * Type</a>
     */
    public Entry entry(@NotNull String contentTypeUid) {
        return new Entry(this.client, this.headers, contentTypeUid);
    }

    /**
     * Instantiates a new Global field.
     */
    public GlobalField globalField() {
        return new GlobalField(this.client, this.headers);
    }


    /**
     * Contentstack has a sophisticated multilingual capability. It allows you to create and publish entries in any
     * language. This feature allows you to set up multilingual websites and cater to a wide variety of audience by
     * serving content in their local language(s).
     * <p>
     *
     * @return Locale
     * @see <a href="https://www.contentstack.com/docs/developers/multilingual-content">Languages</a>
     */
    public Locale locale() {
        return new Locale(client, headers);
    }


    /**
     * A publishing environment corresponds to one or more deployment servers or a content delivery destination where
     * the entries need to be published.
     * <p>
     * Read more about
     *
     * @return Environment
     * @see <a href="https://www.contentstack.com/docs/developers/set-up-environments">Environments</a>
     */
    public Environment environment() {
        return new Environment(client, this.headers);
    }


    /**
     * Labels allow you to group a collection of content within a stack. Using labels you can group content types that
     * need to work together. Read more about
     *
     * @return @{@link Label}
     * <p>
     * @see <a href="https://www.contentstack.com/docs/developers/create-content-types/manage-labels">Labels</a>
     *
     * <p>
     * You can now pass the branch header in the API request to fetch or manage modules located within specific branches
     * of the stack. Additionally, you can also set the include_branch query parameter to true to include the _branch
     * top-level key in the response. This key specifies the unique ID of the branch where the concerned Contentstack
     * module resides.
     * <p>
     */
    public Label label() {
        return new Label(client, this.headers);
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
        return this.stackService.fetch(this.headers);
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
    public Call<ResponseBody> fetch(@NotNull String organizationUid) {
        return this.stackService.fetch(this.headers, organizationUid);
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
    public Call<ResponseBody> fetch(@NotNull String orgUID,
                                    @Nullable Map<String, Boolean> query) {
        if (query == null) query = new HashMap<>();
        return this.stackService.fetch(this.headers, orgUID, query);
    }

    /**
     * <b>Create stack.</b>
     * <p>
     * The Create stack call creates a new stack in your Contentstack account.
     * <p>
     * In the 'Body' section, provide the schema of the stack in JSON format
     * <p>
     * <b>Note:</b>At any given point of time, an organization can create only one
     * stack per minute.
     *
     * @param organizationUid
     *         the organization uid
     * @param requestBody
     *         The request body as JSONObject
     * @return the stack
     */
    public Call<ResponseBody> create(
            @NotNull String organizationUid, @NotNull JSONObject requestBody) {
        return stackService.create(organizationUid, requestBody);
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
     * @param requestBody
     *         the Request body
     * @return the stack
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        return stackService.update(this.headers, requestBody);
    }

    /**
     * <b>Transfer Stack Ownership</b>
     * <br>
     * The Transfer stack ownership to other users call sends the specified user an email invitation for accepting the
     * ownership of a particular stack.
     *
     * <br>
     * <br>
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
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#transfer-stack-ownership-to-other-users">
     * (Read more) </a>
     *
     * @param requestBody
     *         The request body as JSONObject
     * @return the stack
     */
    public Call<ResponseBody> transferOwnership(@NotNull JSONObject requestBody) {
        return stackService.transferOwnership(this.headers, requestBody);
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
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#transfer-stack-ownership-to-other-users">
     * (Read more) </a>
     *
     * @param ownershipToken
     *         the ownership token received via email by another user.
     * @param uid
     *         the user uid.
     * @return the stack
     */
    public Call<ResponseBody> acceptOwnership(
            @NotNull String ownershipToken, @NotNull String uid) {
        Map<String, String> queryMap = new HashMap<>();

        queryMap.put("api_key", this.headers.get(Util.API_KEY).toString());
        queryMap.put("uid", uid);
        return stackService.acceptOwnership(ownershipToken, queryMap);
    }

    /**
     * <b>Stack Settings</b>
     * <b>The Get stack settings call retrieves the
     * configuration settings of an existing stack.</b>
     * <br>
     *
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#stack-settings"> *(Read
     * more)</a>
     *
     * @return the call
     */
    public Call<ResponseBody> setting() {
        return stackService.setting(this.headers);
    }

    /**
     * <b>Add/Update Stack Settings</b>
     * <p>
     * The Add stack settings request lets you add additional settings for your existing stack.
     * </p>
     * <br>
     * <p>
     * You can add specific settings for your stack by passing any of the following parameters within the
     * stack_variables section in the <b>Request Body</b>:
     * </p>
     * <p>
     * Additionally, you can pass <b>cs_only_break line</b>: true under the
     * <b>rte</b> parameter to ensure that only a
     * <br>
     * tag is added inside the <b>Rich Text Editor</b> field when the content manager presses <b>Enter</b>. When this
     * parameter is set to false, the <br> tag is replaced with
     * <p>
     * <br>
     * </p>
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
     *      "cs_only_line":true
     *      }
     *    }
     *  }
     * </pre>
     *
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#add-stack-settings"> *(Read
     * more)</a>
     * <br>
     * <br>
     * <b>Reset stack settings</b>
     * <p>
     * The Reset stack settings call resets your stack to default settings, and additionally, lets you add parameters to
     * or modify the settings of an existing stack.
     * </p>
     * <br>
     * <b>Here is a sample of the Request Body:</b>
     *
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
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#reset-stack-settings"> (Read
     * more)</a>
     *
     * @param requestBody
     *         the request body as JSONObject
     * @return the call
     */
    public Call<ResponseBody> updateSetting(@NotNull JSONObject requestBody) {
        return stackService.updateSetting(this.headers, requestBody);
    }

    /**
     * <b>Reset stack settings</b>
     * <p>
     * The Reset stack settings call resets your stack to default settings, and additionally, lets you add parameters to
     * or modify the settings of an existing stack.
     * </p>
     * <br>
     * <b>Here is a sample of the Request Body:</b>
     *
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
     * <a href= "https://www.contentstack.com/docs/developers/apis/content-management-api/#reset-stack-settings"> (Read
     * more)</a>
     *
     * @param requestBody
     *         the request body
     * @return the call
     */
    public Call<ResponseBody> resetSetting(@NotNull JSONObject requestBody) {
        return stackService.updateSetting(this.headers, requestBody);
    }

    /**
     * <b>Share a stack</b>
     * <p>
     * The Share a stack call shares a stack with the specified user to collaborate on the stack.
     * </p>
     * <br>
     * <p>
     * In the 'Body' section, you need to provide the email ID of the user with whom you wish to share the stack along
     * with the role uid that you wish to assign the user.
     * </p>
     * <b>Here is a sample of the Request Body:</b>
     *
     * <pre>
     *    {
     * 	"emails": [
     * 		"manager@example.com"
     * 	],
     * 	"roles": {
     * 		"manager@example.com": [
     * 			"some_example_s"
     * 		]
     *        }
     * }
     * </pre>
     *
     * @param requestBody
     *         the request body
     * @return the call
     */
    public Call<ResponseBody> share(@NotNull JSONObject requestBody) {
        return stackService.share(this.headers, requestBody);
    }

    /**
     * <b>Unshare a stack</b>
     * <p>
     * The Unshare a stack removes the user account from the list of collaborators. Once this call is executed, the user
     * will not be able to view the stack in their account.
     * </p>
     * <br>
     * <p>
     * In the 'Body' section, you need to provide the email ID of the user from whom you wish to unshare the stack.
     * </p>
     * <b>Here is a sample of the Request Body:</b>
     *
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
    public Call<ResponseBody> unshare(@NotNull JSONObject requestBody) {
        return stackService.unshare(this.headers, requestBody);
    }

    /**
     * <b>Get all users of a stack</b>
     * <br>
     * <p>
     * The Get all users of a stack call fetches the list of all users of a particular stack
     * </p>
     *
     * @return the call
     */
    public Call<ResponseBody> allUsers() {
        return stackService.allUsers(this.headers);
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
     *
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
    public Call<ResponseBody> roles(@NotNull JSONObject requestBody) {
        return stackService.updateUserRoles(this.headers, requestBody);
    }


}
