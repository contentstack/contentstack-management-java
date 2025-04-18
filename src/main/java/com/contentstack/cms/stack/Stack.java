package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * A stack is a space that stores the content of a project (a web or mobile
 * property). Within a stack, you can create
 * content structures, content entries, users, etc. related to the project.
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022 -10-22
 */
public class Stack implements BaseImplementation<Stack> {

    private final StackService service;
    protected Retrofit client;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    /**
     * Instantiates a new Stack.
     *
     * @param client the Retrofit instance
     */
    public Stack(@NotNull Retrofit client) {
        this.headers = new HashMap<>();
        this.client = client;
        this.params = new HashMap<>();
        this.service = client.create(StackService.class);
    }

    /**
     * The addParam function adds a key-value pair to a stack and returns the
     * updated stack.
     *
     * @param key   A string representing the key for the parameter.
     * @param value The value parameter is of type Object, which means it can accept
     *              any type of object as
     *              its value.
     * @return The method is returning a reference to the current instance of the
     * Stack object.
     */
    public Stack addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * @param key   The key parameter is a string that represents the name or
     *              identifier of the header.
     *              It is used to specify the type of information being sent in the
     *              header.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return instance of the Stack
     */
    @Override
    public Stack addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of the Stack
     */
    @Override
    public Stack addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of the Stack
     */
    @Override
    public Stack addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * The function removes a parameter from a stack and returns the modified stack.
     *
     * @param key The parameter "key" is of type String and is marked with
     *            the @NotNull annotation,
     *            indicating that it cannot be null.
     * @return The method is returning a Stack object.
     */
    public Stack removeParam(@NotNull String key) {
        this.params.remove(key);
        return this;
    }

    /**
     * The function clears the parameters in a stack and returns the modified stack.
     *
     * @return The method is returning a reference to the current instance of the
     * Stack object.
     */
    protected Stack clearParams() {
        this.params.clear();
        return this;
    }

    // The above code is defining a constructor for a class called "Stack". The
    // constructor takes two
    // parameters: a Retrofit client and a map of headers.
    public Stack(@NotNull Retrofit client, @NotNull Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.client = client;
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = client.create(StackService.class);
    }

    /**
     * <b>Content type</b>
     * <p>
     * Content type defines the structure or schema of a page or a section of your
     * web or mobile property. To create
     * content for your application, you are required to first create a content
     * type, and then create entries using the
     * content type.
     * <p>
     *
     * <b>Additional Resource</b>
     * <p>
     * To get an idea of building your content type as per webpage's layout, we
     * recommend you to check out our Content
     * Modeling guide
     *
     * @return ContentType
     */
    public ContentType contentType() {
        return new ContentType(this.client, this.headers);
    }

    /**
     * <b>Content type</b>
     * <p>
     * Content type defines the structure or schema of a page or a section of your
     * web or mobile property. To create
     * content for your application, you are required to first create a content
     * type, and then create entries using the
     * content type.
     * <p>
     *
     * <b>Additional Resource</b>
     * <p>
     * To get an idea of building your content type as per webpage's layout, we
     * recommend you to check out our Content
     * Modeling guide
     *
     * @param contentTypeUid Enter the unique ID of the content type of which you
     *                       want to retrieve the details. The UID is generated
     *                       based on the title of the content type. The unique ID
     *                       of a content type is unique across a stack
     * @return ContentType
     */
    public ContentType contentType(@NotNull String contentTypeUid) {
        return new ContentType(this.client, this.headers, contentTypeUid);
    }

    /**
     * <b>Assets</b><br>
     * Assets refer to all the media files (images, videos, PDFs, audio files, and
     * so on) uploaded in your Contentstack
     * repository for future use.
     * <p>
     * These files can be attached and used in multiple entries.
     * <ul>
     * <li>
     * folder(optional)
     * Enter either the UID of a specific folder to get the assets of that folder,
     * or enter ‘cs_root’ to get all assets and their folder details from the root
     * folder.
     * <p>
     * Example:your_uid
     * </li>
     * <li>
     * include_folders(optional)
     * Set this parameter to ‘true’ to include the details of the created folders
     * along with the details of the assets.
     * <p>
     * Example:true
     * </li>
     * <li>
     * environment(optional)
     * Enter the name of the environment to retrieve the assets published on them.
     * You can enter multiple environments.
     * <p>
     * Example:production
     * </li>
     * <li>
     * version(optional)
     * Specify the version number of the asset that you want to retrieve. If the
     * version is not specified, the details of the latest version will be
     * retrieved.
     * <p>
     * Example:1
     * </li>
     * <li>
     * include_publish_details(optional)
     * Enter 'true' to include the publish details of the entry.
     * <p>
     * Example:true
     * </li>
     * <li>
     * include_count(optional)
     * Set this parameter to 'true' to include the total number of assets available
     * in your stack in the response body.
     * <p>
     * Example:false
     * </li>
     * <li>
     * relative_urls(optional)
     * Set this to 'true' to display the relative URL of the asset.
     * <p>
     * Example:false
     * </li>
     * <li>
     * asc_field_uid(optional)
     * Enter the unique ID of the field for sorting the assets in ascending order by
     * that field.
     * <p>
     * Example:created_at
     * </li>
     * <li>
     * desc_field_uid(optional)
     * Enter the unique ID of the field for sorting the assets in descending order
     * by that field.
     * <p>
     * Example:file_size
     * </li>
     * <li>
     * include_branch(optional)
     * Set this to 'true' to include the <b>_branch</b> top-level key in the
     * response. This key states the unique ID of the branch where the concerned
     * Contentstack module resides.
     * <p>
     * Example:false
     * </ul>
     *
     * @return Asset
     */
    public Asset asset() {
        return new Asset(this.client, this.headers);
    }

    /**
     * <b>Assets</b><br>
     * Assets refer to all the media files (images, videos, PDFs, audio files, and
     * so on) uploaded in your Contentstack
     * repository for future use.
     * <p>
     * These files can be attached and used in multiple entries.
     * <ul>
     * <li>
     * folder(optional)
     * Enter either the UID of a specific folder to get the assets of that folder,
     * or enter ‘cs_root’ to get all assets and their folder details from the root
     * folder.
     * <p>
     * Example: uid899999999
     * </li>
     * <li>
     * include_folders(optional)
     * Set this parameter to ‘true’ to include the details of the created folders
     * along with the details of the assets.
     * <p>
     * Example:true
     * </li>
     * <li>
     * environment(optional)
     * Enter the name of the environment to retrieve the assets published on them.
     * You can enter multiple environments.
     * <p>
     * Example:production
     * </li>
     * <li>
     * version(optional)
     * Specify the version number of the asset that you want to retrieve. If the
     * version is not specified, the details of the latest version will be
     * retrieved.
     * <p>
     * Example:1
     * </li>
     * <li>
     * include_publish_details(optional)
     * Enter 'true' to include the publish details of the entry.
     * <p>
     * Example:true
     * </li>
     * <li>
     * include_count(optional)
     * Set this parameter to 'true' to include the total number of assets available
     * in your stack in the response body.
     * <p>
     * Example:false
     * </li>
     * <li>
     * relative_urls(optional)
     * Set this to 'true' to display the relative URL of the asset.
     * <p>
     * Example:false
     * </li>
     * <li>
     * asc_field_uid(optional)
     * Enter the unique ID of the field for sorting the assets in ascending order by
     * that field.
     * <p>
     * Example:created_at
     * </li>
     * <li>
     * desc_field_uid(optional)
     * Enter the unique ID of the field for sorting the assets in descending order
     * by that field.
     * <p>
     * Example:file_size
     * </li>
     * <li>
     * include_branch(optional)
     * Set this to 'true' to include the <b>_branch</b> top-level key in the
     * response. This key states the unique ID of the branch where the concerned
     * Contentstack module resides.
     * <p>
     * Example:false
     * </ul>
     *
     * @param assetUid the assetUid
     * @return Asset
     */
    public Asset asset(String assetUid) {
        return new Asset(this.client, this.headers, assetUid);
    }

    /**
     * A Global field is a reusable field (or group of fields) that you can define
     * once and reuse in any content type
     * within your stack. This eliminates the need (and thereby time and efforts) to
     * create the same set of fields
     * repeatedly in multiple content types.
     *
     * @return GlobalField
     */
    public GlobalField globalField() {
        return new GlobalField(this.client,this.headers);
    }

    /**
     * A Global field is a reusable field (or group of fields) that you can define
     * once and reuse in any content type
     * within your stack. This eliminates the need (and thereby time and efforts) to
     * create the same set of fields
     * repeatedly in multiple content types.
     *
     * @param globalFiledUid the globalField uid
     * @return GlobalField
     */
    public GlobalField globalField(@NotNull String globalFiledUid) {
        return new GlobalField(this.client,this.headers,globalFiledUid);
    }

    /**
     * Contentstack has a sophisticated multilingual capability. It allows you to
     * create and publish entries in any
     * language. This feature allows you to set up multilingual websites and cater
     * to a wide variety of audience by
     * serving content in their local language(s).
     * <p>
     *
     * @return Locale
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/multilingual-content">Languages</a>
     */
    public Locale locale() {
        return new Locale(this.client,this.headers);
    }

    /**
     * Contentstack has a sophisticated multilingual capability. It allows you to
     * create and publish entries in any
     * language. This feature allows you to set up multilingual websites and cater
     * to a wide variety of audience by
     * serving content in their local language(s).
     * <p>
     *
     * @param code the locale code.
     * @return Locale
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/multilingual-content">Languages</a>
     */
    public Locale locale(String code) {
        return new Locale(this.client,this.headers, code);
    }

    /**
     * A publishing environment corresponds to one or more deployment servers or a
     * content delivery destination where
     * the entries need to be published.
     * <p>
     * Read more about
     *
     * @return Environment
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/set-up-environments">Environments</a>
     */
    public Environment environment() {
        return new Environment(this.client,this.headers);
    }

    /**
     * A publishing environment corresponds to one or more deployment servers or a
     * content delivery destination where
     * the entries need to be published.
     * <p>
     * Read more about
     *
     * @param environment uid
     * @return Environment
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/set-up-environments">Environments</a>
     */
    public Environment environment(String environment) {
        return new Environment(this.client,this.headers, environment);
    }

    /**
     * Labels allow you to group a collection of content within a stack. Using
     * labels you can group content types that
     * need to work together. Read more about
     *
     * @return Label
     * <p>
     * You can now pass the branch header in the API request to fetch or
     * manage modules located within specific branches
     * of the stack. Additionally, you can also set the include_branch query
     * parameter to true to include the _branch
     * top-level key in the response. This key specifies the unique ID of
     * the branch where the concerned Contentstack
     * module resides.
     */
    public Label label() {
        return new Label(this.client,this.headers);
    }

    /**
     * Labels allow you to group a collection of content within a stack. Using
     * labels you can group content types that
     * need to work together. Read more about
     *
     * @param labelUid The label
     * @return Label
     * <p>
     * You can now pass the branch header in the API request to fetch or
     * manage modules located within specific branches
     * of the stack. Additionally, you can also set the include_branch query
     * parameter to true to include the _branch
     * top-level key in the response. This key specifies the unique ID of
     * the branch where the concerned Contentstack
     * module resides.
     */
    public Label label(String labelUid) {
        return new Label(this.client,this.headers, labelUid);
    }

    /**
     * Extensions let you create custom fields and custom widgets that lets you
     * customize Contentment's default UI and
     * behavior. Read more about Extensions.
     * <p>
     * You can now pass the branch header in the API request to fetch or manage
     * modules located within specific branches
     * of the stack. Additionally, you can also set the include_branch query
     * parameter to true to include the _branch
     * top-level key in the response. This key specifies the unique ID of the branch
     * where the concerned Contentstack
     * module resides.
     * <p>
     *
     * @return instance of {@link Extensions}
     */
    public Extensions extensions() {
        return new Extensions(this.client,this.headers);
    }

    /**
     * Extensions let you create custom fields and custom widgets that lets you
     * customize Contentment's default UI and
     * behavior. Read more about Extensions.
     * <p>
     * You can now pass the branch header in the API request to fetch or manage
     * modules located within specific branches
     * of the stack. Additionally, you can also set the include_branch query
     * parameter to true to include the _branch
     * top-level key in the response. This key specifies the unique ID of the branch
     * where the concerned Contentstack
     * module resides.
     * <p>
     *
     * @param customFieldUid The UID of the custom field that you want to update
     *                       {@link #addParam(String, Object)} Set this to 'true'
     *                       to include the '_branch' top-level key in the response.
     *                       This key states the unique ID of the branch where
     *                       the concerned Contentstack module resides.
     * @return instance of {@link Extensions}
     */
    public Extensions extensions(String customFieldUid) {
        return new Extensions(this.client,this.headers, customFieldUid);
    }

    /**
     * Contentstack provides different types of tokens to authorize API requests.
     * You can use Delivery Tokens to
     * authenticate Content Delivery API (CDA) requests and retrieve the published
     * content of an environment. To
     * authenticate Content Management API (CMA) requests over your stack content,
     * you can use Management Tokens.
     * <p>
     * Delivery tokens provide read-only access to the associated environments,
     * while management tokens provide
     * read-write access to the content of your stack. Use these tokens along with
     * the stack API key to make authorized
     * API requests
     * <p>
     *
     * @return Tokens
     */
    public Tokens tokens() {
        return new Tokens(this.client,this.headers);
    }

    /**
     * A role is a collection of permissions that will be applicable to all the
     * users who are assigned this role
     *
     * @return Role
     */
    public Roles roles() {
        return new Roles(this.client,this.headers);
    }

    /**
     * A role is a collection of permissions that will be applicable to all the
     * users who are assigned this role
     *
     * @param roleUid The unique ID of the role of which you want to retrieve the
     *                details
     * @return Role
     */
    public Roles roles(String roleUid) {
        return new Roles(this.client, this.headers,roleUid);
    }

    /**
     * You can pin a set of entries and assets (along with the deployment action, i.e.,
     * publish/un-publish) to a
     * <b>release</b>, and then deploy this release to an environment. This will
     * publish/un-publish all the items of the release to the specified environment.
     * Read more about Releases.
     *
     * @return Release
     */
    public Release releases() {
        return new Release(this.client,this.headers);
    }

    /**
     * You can pin a set of entries and assets (along with the deployment action, i.e.,
     * publish/un-publish) to a
     * <b>release</b>, and then deploy this release to an environment. This will
     * publish/un-publish all the items of the release to the specified environment.
     * Read more about Releases.
     *
     * @param releaseUid The unique ID of the release of which you want to retrieve
     *                   the details
     * @return Release
     */
    public Release releases(String releaseUid) {
        return new Release(this.client,this.headers,releaseUid);
    }

    /**
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#workflows">Workflow</a>
     * is a
     * tool that allows you to streamline the process of content creation and
     * publishing, and lets you manage the
     * content lifecycle of your project smoothly.
     *
     * @return Workflow
     */
    public Workflow workflow() {
        return new Workflow(this.client,this.headers);
    }

    /**
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#workflows">Workflow</a>
     * is a
     * tool that allows you to streamline the process of content creation and
     * publishing, and lets you manage the
     * content lifecycle of your project smoothly.
     *
     * @param workflowUid The UID of your workflow that you want to retrieve
     * @return Workflow
     */
    public Workflow workflow(@NotNull String workflowUid) {
        return new Workflow(this.client, this.headers,workflowUid);
    }

    /**
     * Audit log displays a record of all the activities performed in a stack and
     * helps you keep a track of all
     * published items, updates, deletes, and current status of the existing
     * content. Read more about <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#audit-log">AuditLog</a>.
     *
     * @return AuditLog
     */
    public AuditLog auditLog() {
        return new AuditLog(this.client,this.headers);
    }

    /**
     * Audit log displays a record of all the activities performed in a stack and
     * helps you keep a track of all
     * published items, updates, deletes, and current status of the existing
     * content. Read more about <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#audit-log">AuditLog</a>.
     *
     * @param logItemUid the logItemUid
     * @return AuditLog
     */
    public AuditLog auditLog(@NotNull String logItemUid) {
        return new AuditLog(this.client,this.headers, logItemUid);
    }

    /**
     * The Publishing Queue displays the historical and current details of
     * activities such as publish, un-publish, or
     * delete that can be performed on entries and/or assets. It also shows details
     * of Release deployments. These
     * details include time, entry, content type, version, language, user,
     * environment, and status.
     * <p>
     * For more details, refer the <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-queue">Publish
     * Queue</a>
     * documentation.
     *
     * @return PublishQueue
     */
    public PublishQueue publishQueue() {
        return new PublishQueue(this.client,this.headers);
    }

    /**
     * You can perform bulk operations such as Publish, Un-publish, and Delete on
     * multiple entries or assets, or Change
     * the Workflow Details of multiple entries or assets at the same time.
     * <p>
     * For more details, refer the <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#bulk-operations">Bulk
     * Operation</a>
     * documentation.
     *
     * @return BulkOperation
     */
    public BulkOperation bulkOperation() {
        return new BulkOperation(this.client,this.headers);
    }

    /**
     * The Publishing Queue displays the historical and current details of
     * activities such as publish, un-publish, or
     * delete that can be performed on entries and/or assets. It also shows details
     * of Release deployments. These
     * details include time, entry, content type, version, language, user,
     * environment, and status.
     * <p>
     * For more details, refer the <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-queue">Publish
     * Queue</a>
     * documentation.
     *
     * @param publishQueueUid The UID of a specific publish queue activity of which
     *                        you want to retrieve the details. Execute the Get
     *                        publish queue API request to retrieve the UID of a
     *                        particular publish queue activity.
     * @return PublishQueue
     */
    public PublishQueue publishQueue(@NotNull String publishQueueUid) {
        return new PublishQueue(this.client,this.headers, publishQueueUid);
    }

    /**
     * A webhook is a mechanism that sends real-time information to any third-party
     * app or service to keep your
     * application in sync with your Contentstack account. Webhooks allow you to
     * specify a URL to which you would like
     * Contentstack to post data when an event happens. Read more about <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#webhooks">Webhooks</a>
     *
     * <p>
     * <b>Note:</b> If any key name in the response data sent to a notification URL
     * begins with a dollar sign ($), it will be prefixed with the acronym "cs" as a
     * wildcard. For example, the key
     * named "$success" would be replaced with "cs$success." For more information,
     * refer to our API Change Log
     * documentation. The option to define stack-level scope for webhooks is not
     * available when using the classic
     * Contentstack interface.
     *
     * @return Webhook
     */
    public Webhook webhook() {
        return new Webhook(this.client,this.headers);
    }

    /**
     * A webhook is a mechanism that sends real-time information to any third-party
     * app or service to keep your
     * application in sync with your Contentstack account. Webhooks allow you to
     * specify a URL to which you would like
     * Contentstack to post data when an event happens. Read more about <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#webhooks">Webhooks</a>
     *
     * <p>
     * <b>Note:</b> If any key name in the response data sent to a notification URL
     * begins with a dollar sign ($), it will be prefixed with the acronym "cs" as a
     * wildcard. For example, the key
     * named "$success" would be replaced with "cs$success." For more information,
     * refer to our API Change Log
     * documentation. The option to define stack-level scope for webhooks is not
     * available when using the classic
     * Contentstack interface.
     *
     * @param webhookUid Enter the unique ID of the webhook of which you want to
     *                   retrieve the details. Execute the <b>Get all
     *                   webhooks</b> call to retrieve the UID of a webhook
     * @return Webhook
     */
    public Webhook webhook(String webhookUid) {
        return new Webhook(this.client,this.headers, webhookUid);
    }

    /**
     * <b>Branches</b>
     * <br>
     * <b>Branches</b> allows you to isolate and easily manage your
     * <b>in-progress</b> work from your stable, live work
     * in the production environment. It helps multiple development teams to work in
     * parallel in a more collaborative,
     * organized, and structured manner without impacting each other.
     * <br>
     *
     * @return Branch
     */
    public Branch branch() {
        return new Branch(this.client,this.headers);
    }

    /**
     * <b>Branches</b>
     * <br>
     * <b>Branches</b> allows you to isolate and easily manage your
     * <b>in-progress</b> work from your stable, live work
     * in the production environment. It helps multiple development teams to work in
     * parallel in a more collaborative,
     * organized, and structured manner without impacting each other.
     * <br>
     *
     * @param branchUid The unique ID of the branch of which you want to retrieve
     *                  the details.
     * @return Branch
     */
    public Branch branch(String branchUid) {
        return new Branch(this.client,this.headers, branchUid);
    }

    /**
     * An alias acts as a pointer to a particular branch. You can specify the alias
     * ID in your frontend code to pull
     * content from the target branch associated with an alias.
     *
     * @return Alias
     */
    public Alias alias() {
        return new Alias(this.client,this.headers);
    }

    /**
     * An alias acts as a pointer to a particular branch. You can specify the alias
     * ID in your frontend code to pull
     * content from the target branch associated with an alias.
     *
     * @param aliasUid The unique ID of the alias of which you want to retrieve the
     *                 details. The UID of an alias is unique
     *                 across a stack. Execute the Get all aliases call to retrieve
     *                 the UID of an alias
     * @return Alias
     */
    public Alias alias(String aliasUid) {
        return new Alias(this.client, this.headers,aliasUid);
    }


    /**
     * The taxonomy works on data where hierarchy is configured
     *
     * <pre>
     * {@code
     * Stack stack = new Contentstack.Builder().setAuthtoken("authtoken").build().stack();
     * Taxonomy taxonomy = stack.taxonomy();
     * Call<ResponseBody> response = taxonomy.fetch().execute();
     * }
     * </pre>
     *
     * @return instance of Taxonomy
     */
    public Taxonomy taxonomy() {
        return new Taxonomy(this.client, this.headers);
    }


    /**
     * The taxonomy works on data where hierarchy is configured
     *
     * @param taxonomyUid the taxonomy uid
     * @return instance of Taxonomy
     * <br>
     * <pre>
     * {@code
     * Stack stack = new Contentstack.Builder().setAuthtoken("authtoken").build().stack();
     * Taxonomy taxonomy = stack.taxonomy("taxonomyId");
     * taxonomy.terms();
     * taxonomy.fetch();
     * taxonomy.find();
     * }
     * </pre>
     */
    public Taxonomy taxonomy(@NotNull String taxonomyUid) {
        return new Taxonomy(this.client, this.headers, taxonomyUid);
    }

    /**
     * <b>Get stacks</b>
     * <br>
     * <b>All Stack:- </b>authtoken is required to fetch all the stacks
     * <br>
     * <br>
     * <b>Single Stack:- </b> : api_key and authtoken is required and
     * organization_uid is optional
     * <br>
     * <br>
     * <b>Example:</b>
     *
     * <pre>
     * {@code
     * Stack stack = new Contentstack.Builder().setAuthtoken("authtoken").build().stack();
     * stack.addParam("include_collaborators", true);
     * stack.addParam("include_stack_variables", true);
     * stack.addParam("include_discrete_variables", true);
     * stack.addParam("include_count", true);
     * stack.addHeader("organization_uid", orgId);
     * Request request = stack.fetch().request();
     * }
     * </pre>
     *
     * <b>For SSO-enabled organizations, it is mandatory to pass the organization
     * UID in the header.</b>
     * <br>
     * <br>
     * - Add headers using {@link #addHeader(String, String)}
     * <br>
     * - Add query parameters using {@link #addParam(String, Object)}
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-stacks">Get
     * all
     * Stacks</a>
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-stack">Get
     * single Stack</a>
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * <b>Create stack.</b>
     * <br>
     * The Create stack call creates a new stack in your Contentstack account.
     * <br>
     * In the 'Body' section, provide the schema of the stack in JSON format
     * <br>
     * <b>Note:</b>At any given point of time, an organization can create only one
     * stack per minute.
     *
     * @param organizationUid the organization uid
     * @param requestBody     The request body as JSONObject
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-stacks">Get
     * all
     * Stacks</a>
     * @since 0.1.0
     */
    public Call<ResponseBody> create(
            @NotNull String organizationUid, @NotNull JSONObject requestBody) {
        return service.create(organizationUid, requestBody);
    }

    /**
     * <b>Update Stack</b>
     * <br>
     * The Update stack call lets you update the name and description of an existing
     * stack.
     * <br>
     * In the 'Body' section, provide the updated schema of the stack in JSON
     * format.
     * <br>
     * <b>Note</b> Warning: The master locale cannot be changed once it is set
     * while stack creation. So, you cannot use this call to change/update the
     * master language.
     *
     * @param requestBody the Request body
     * @return the stack
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        return service.update(this.headers, requestBody);
    }

    /**
     * <b>Transfer Stack Ownership</b>
     * <br>
     * The Transfer stack ownership to other users call sends the specified user an
     * email invitation for accepting the
     * ownership of a particular stack.
     *
     * <br>
     * <br>
     * Once the specified user accepts the invitation by clicking on the link
     * provided in the email, the ownership of
     * the stack gets transferred to the new user. Subsequently, the previous owner
     * will no longer have any permission
     * on the stack.
     * <br>
     * <b>Note</b>
     * <br>
     * <b>
     * Warning: The master locale cannot be changed once it is set while stack
     * creation. So, you cannot use this call to
     * change/update the master language.
     * </b>
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#transfer-stack-ownership-to-other-users">
     * (Read more) </a>
     *
     * @param body The request body as JSONObject
     * @return the stack
     */
    public Call<ResponseBody> transferOwnership(@NotNull JSONObject body) {
        return service.transferOwnership(this.headers, body);
    }

    /**
     * <b>Accept Stack Ownership</b>
     * <br>
     * The Accept stack owned by other user call allows a user to accept the
     * ownership of a particular stack via an
     * email invitation.
     * <br>
     * <p>
     * Once the user accepts the invitation by clicking on the link, the ownership
     * is transferred to the new user
     * account. Subsequently, the user who transferred the stack will no longer have
     * any permission on the stack.
     * <br>
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#transfer-stack-ownership-to-other-users">
     * (Read more) </a>
     * <br>
     * <b>uid</b> and <b>api_key</b> is required to get the acceptOwnership
     * information
     * <ul>
     * <li>To Accept stack owned by other user, user
     * {@link #addParam(String, Object)} to add query parameters</li>
     * <li>To Add Header use {@link #addHeader(String, String)}</li>
     * </ul>
     *
     * @param ownershipToken the ownership token received via email by another user.
     * @return the stack
     */
    public Call<ResponseBody> acceptOwnership(
            @NotNull String ownershipToken) {
        return service.acceptOwnership(this.headers, ownershipToken, this.params);
    }

    /**
     * <b>Stack Settings</b>
     * <b>The Get stack settings call retrieves the
     * configuration settings of an existing stack.</b>
     * <br>
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#stack-settings">
     * *(Read
     * more)</a>
     *
     * @return the call
     */
    public Call<ResponseBody> setting() {
        return service.setting(this.headers);
    }

    /**
     * <b>Add/Update Stack Settings</b>
     * <p>
     * The Add stack settings request lets you add additional settings for your
     * existing stack.
     * </p>
     * <br>
     * <p>
     * You can add specific settings for your stack by passing any of the following
     * parameters within the
     * stack_variables section in the <b>Request Body</b>:
     * </p>
     * <p>
     * Additionally, you can pass <b>cs_only_break line</b>: true under the
     * <b>rte</b> parameter to ensure that only a
     * <br>
     * tag is added inside the <b>Rich Text Editor</b> field when the content
     * manager presses <b>Enter</b>. When this
     * parameter is set to false, the <br>
     * tag is replaced with
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
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#add-stack-settings">
     * *(Read
     * more)</a>
     * <br>
     * <br>
     * <b>Reset stack settings</b>
     * <p>
     * The Reset stack settings call resets your stack to default settings, and
     * additionally, lets you add parameters to
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
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#reset-stack-settings">
     * (Read
     * more)</a>
     *
     * @param requestBody the request body as JSONObject
     * @return the call
     */
    public Call<ResponseBody> updateSetting(@NotNull JSONObject requestBody) {
        return service.updateSetting(this.headers, requestBody);
    }

    /**
     * <b>Reset stack settings</b>
     * <p>
     * The Reset stack settings call resets your stack to default settings, and
     * additionally, lets you add parameters to
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
     * <p>
     * <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#reset-stack-settings">
     * (Read
     * more)</a>
     *
     * @param requestBody the request body
     * @return the call
     */
    public Call<ResponseBody> resetSetting(@NotNull JSONObject requestBody) {
        return service.updateSetting(this.headers, requestBody);
    }

    /**
     * <b>Share a stack</b>
     * <p>
     * The Share a stack call shares a stack with the specified user to collaborate
     * on the stack.
     * </p>
     * <br>
     * <p>
     * In the 'Body' section, you need to provide the email ID of the user with whom
     * you wish to share the stack along
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
     * @param requestBody the request body
     * @return the call
     */
    public Call<ResponseBody> share(@NotNull JSONObject requestBody) {
        return service.share(this.headers, requestBody);
    }

    /**
     * <b>Un-share a stack</b>
     * <p>
     * The Un-share a stack removes the user account from the list of collaborators.
     * Once this call is executed, the user
     * will not be able to view the stack in their account.
     * </p>
     * <br>
     * <p>
     * In the 'Body' section, you need to provide the email ID of the user from whom
     * you wish to un-share the stack.
     * </p>
     * <b>Here is a sample of the Request Body:</b>
     *
     * <pre>
     * {
     * "email": "manager@example.com"
     * }
     * </pre>
     *
     * @param requestBody the request body
     * @return the call
     */
    public Call<ResponseBody> unshare(@NotNull JSONObject requestBody) {
        return service.unshare(this.headers, requestBody);
    }

    /**
     * <b>Get all users of a stack</b>
     * <br>
     * <p>
     * The Get all users of a stack call fetches the list of all users of a
     * particular stack
     * </p>
     *
     * @return the call
     */
    public Call<ResponseBody> allUsers() {
        return service.allUsers(this.headers);
    }

    /**
     * <b>Update User Role</b>
     * <br>
     * <p>
     * The Update User Role API Request updates the roles of an existing user
     * account. This API Request will override
     * the existing roles assigned to a user. For example, we have an existing user
     * with the <b>Developer</b> role, and
     * if you execute this API request with "Content Manager" role, the user role
     * will lose <b>Developer</b> rights and
     * the user role be updated to just <b>Content Manager</b>.
     * </p>
     * <br>
     * <p>
     * When executing the API call, under the <b>Body</b> section, enter the UIDs of
     * roles that you want to assign a
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
     * @param body the request body
     * @return Call
     */
    public Call<ResponseBody> roles(@NotNull JSONObject body) {
        return service.updateUserRoles(this.headers, body);
    }


}
