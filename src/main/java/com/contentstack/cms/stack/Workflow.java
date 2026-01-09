package com.contentstack.cms.stack;

import com.contentstack.cms.core.ErrorMessages;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Workflow is a tool that allows you to streamline the process of content
 * creation and publishing, and lets you manage
 * the content lifecycle of your project smoothly.
 * <p>
 * Read more about <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#workflows">Workflow</a>
 * <b>Note:</b> You cannot create workflows in a stack that supports branches
 * when using the classic Contentstack interface.
 *
 * @author ***REMOVED***
 * @version v01.0
 * @since 2022-10-22
 */
public class Workflow implements BaseImplementation<Workflow> {

    protected final WorkflowService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private String workflowUid;

    protected Workflow(Retrofit retrofit,Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = retrofit.create(WorkflowService.class);
    }

    protected Workflow(Retrofit retrofit,Map<String, Object> headers, @NotNull String uid) {
        this.workflowUid = uid;
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = retrofit.create(WorkflowService.class);
    }

    void validate() {
        if (this.workflowUid == null || this.workflowUid.isEmpty())
            throw new IllegalAccessError(ErrorMessages.WORKFLOW_UID_REQUIRED);
    }


    /**
     * Set header for the request
     *
     * @param key Removes query param using key of request
     */
    public void removeParam(@NotNull String key) {
        this.params.remove(key);
    }

    /**
     * To clear all the query params
     */
    protected void clearParams() {
        this.params.clear();
    }

    /**
     * Get a Single Workflow request retrieves the comprehensive details of a
     * specific Workflow of a stack.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-workflows">Get
     * all workflow
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * Get a Single Workflow request retrieves the comprehensive details of a
     * specific Workflow of a stack.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-workflow">Get
     * a singel
     * workflow
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        this.validate();
        return this.service.single(this.headers, this.workflowUid);
    }

    /**
     * The Create a Workflow request allows you to create a Workflow.
     * <p>
     * In the 'Body' section, you can provide the details of the workflow that
     * includes name, content types, owners,
     * description, and workflow stages of your Workflow. To define the branch
     * scope, specify the unique IDs of the
     * branches for which the workflow will be applicable in the following schema in
     * the request body:
     *
     * <pre>
     *     "branches":[
     *     "main",
     *     "development"
     * ]
     * </pre>
     * <p>
     * To control who can edit an entry at different stages of the workflow, you can
     * pass the entry_lock parameter
     * inside each workflow stage.
     * <b>Note:</b> Workflow superusers, organization owners, and stack
     * owners/admins can edit or delete the entry in any workflow stage,
     * irrespective of the stage access rules set for
     * that stage.
     * <p>
     * Read more about <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-workflow">Create-Workflow</a>
     *
     * @param requestBody The details of the workflow in @{@link JSONObject}
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-workflow">Create
     * workflow
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.create(this.headers, requestBody);
    }

    /**
     * The Add or Update Workflow request allows you to add a workflow stage or
     * update the details of the existing
     * stages of a workflow.
     * <p>
     * In the 'Body' section, you can provide the updated details of the name,
     * content types, owners, description, and
     * workflow stages of your Workflow. To define the branch scope, specify the
     * unique IDs of the branches for which
     * the workflow will be applicable in the following schema in the request body:
     *
     * <pre>
     *     "branches":[
     *     "main",
     *     "development"
     * ]
     * </pre>
     * <p>
     * To control who can edit an entry at different stages of the workflow, you can
     * pass the entry_lock parameter
     * inside each workflow stage.
     * <p>
     * Note: Workflow superusers, organization owners, and stack owners/admins can
     * edit or delete the entry in any
     * workflow stage, irrespective of the stage access rules set for that stage.
     *
     * @param requestBody The body should be of @{@link JSONObject} type
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#add-or-update-workflow-details">Update
     * Workflow
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        this.validate();
        return this.service.update(this.headers, this.workflowUid, requestBody);
    }

    /**
     * Disable Workflow request allows you to disable a workflow.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#disable-workflow">Disable
     * workflow
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> disable() {
        this.validate();
        return this.service.disable(this.headers, this.workflowUid);
    }

    /**
     * Enable Workflow request allows you to enable a workflow.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#enable-workflow">Enable
     * Workflow
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> enable() {
        this.validate();
        return this.service.enable(this.headers, this.workflowUid);
    }

    /**
     * Delete Workflow request allows you to delete a workflow.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-workflow">Delete
     * Workflow
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        this.validate();
        return this.service.delete(this.headers, this.workflowUid);
    }

    /**
     * The Create Publish Rules request allows you to create publish rules for the
     * workflow of a stack.
     * <p>
     * To define the branch scope, specify the unique IDs of the branches for which
     * the publishing rule will be
     * applicable in the following schema in the request body:
     *
     * <pre>
     *     "branches":[
     *     "main",
     *     "development"
     * ]
     * </pre>
     *
     * <b>Note:</b> You cannot create publish rules in a stack that supports
     * branches when using the classic Contentstack interface.
     *
     * @param requestBody Specify the unique IDs of the branches for which the
     *                    publishing rule will be applicable in the schema in
     *                    the request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-publish-rules">Create
     * Publish
     * Rule
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> createPublishRule(@NotNull JSONObject requestBody) {
        return this.service.createPublishRules(this.headers, requestBody);
    }

    /**
     * Add or Update Publish Rules request allows you to add a publishing rule or
     * update the details of the existing
     * publishing rules of a workflow.
     * <p>
     * To define the branch scope, specify the unique IDs of the branches for which
     * the publishing rule will be
     * applicable in the following schema in the request body:
     *
     * @param ruleUid     The UID of the publishing rule that you want to update
     * @param requestBody The request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-publish-rules">Update
     * Publish
     * Rules
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> updatePublishRule(@NotNull String ruleUid, @NotNull JSONObject requestBody) {
        return this.service.updatePublishRules(this.headers, ruleUid, requestBody);
    }

    /**
     * To Delete Publish Rules request allows you to delete an existing publish
     * rule.
     *
     * @param ruleUid The UID of the publishing rule that you want to delete
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-publish-rules">Delete
     * Publish
     * Rules
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> deletePublishRule(@NotNull String ruleUid) {
        return this.service.deletePublishRules(this.headers, ruleUid);
    }

    /**
     * The Get all Publish Rules request retrieves the details of all the Publishing
     * rules of a workflow.
     *
     * @param contentTypes comma-separated list of content type UIDs for filtering
     *                     publish rules on its basis
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-publish-rules">Get
     * all publish
     * rule
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetchPublishRules(@NotNull List<String> contentTypes) {
        this.params.put("content_types", contentTypes);
        this.params.put("include_count", true);
        return this.service.fetchPublishRules(this.headers, this.params);
    }

    /**
     * The Get a Single Publish Rule request retrieves the comprehensive details of
     * a specific publish rule of a
     * Workflow.
     *
     * @param ruleUid The UID of the publishing rule that you want to fetch
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-publish-rules">Get
     * all publish
     * rules
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetchPublishRule(@NotNull String ruleUid) {
        return this.service.fetchPublishRules(this.headers, ruleUid);
    }

    /**
     * The Get Publish Rules by Content Types request allows you to retrieve details
     * of a Publish Rule applied to a
     * specific content type of your stack.
     * <p>
     * When executing the API request, in the 'Header' section, you need to provide
     * the API Key of your stack and the
     * authtoken that you receive after logging into your account.
     *
     * @param contentTypeUid The UID of the content type of which you want to
     *                       retrieve the Publishing Rule.
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-publish-rules-by-content-types">Get
     * Publish Rules By Content Types
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetchPublishRuleContentType(@NotNull String contentTypeUid) {
        if (contentTypeUid.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.CONTENT_TYPE_REQUIRED);
        }
        return this.service.fetchPublishRuleContentType(this.headers, contentTypeUid, this.params);
    }

    /**
     * The Get all Tasks request retrieves a list of all tasks assigned to you.
     * <p>
     * When executing the API request, in the 'Header' section, you need to provide
     * the API Key of your stack and the
     * authtoken that you receive after logging into your account.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-tasks">Get
     * all
     * Tasks</a>
     * @since 0.1.0
     */
    public Call<ResponseBody> fetchTasks() {
        return this.service.fetchTasks(this.headers, this.params);
    }

    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of the {@link Workflow}
     */
    @Override
    public Workflow addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of the {@link Workflow}
     */
    @Override
    public Workflow addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of the {@link Workflow}
     */
    @Override
    public Workflow addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of the {@link Workflow}
     */
    @Override
    public Workflow addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }
}
