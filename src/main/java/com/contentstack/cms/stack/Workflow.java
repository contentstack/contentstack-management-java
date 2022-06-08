package com.contentstack.cms.stack;

import com.contentstack.cms.core.Util;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.List;


/**
 * Workflow is a tool that allows you to streamline the process of content creation and publishing, and lets you manage
 * the content lifecycle of your project smoothly.
 * <p>
 * Read more about <a
 * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#workflows">Workflow</a>
 *
 * <b>Note:</b> You cannot create workflows in a stack that supports branches when using the classic Contentstack
 * interface.
 *
 * @author Shailesh Mishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Workflow {

    protected final WorkflowService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private final Retrofit retrofit;

    protected Workflow(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.retrofit = retrofit;
        this.service = this.retrofit.create(WorkflowService.class);
    }

    /**
     * Sets header for the request
     *
     * @param key
     *         header key for the request
     * @param value
     *         header value for the request
     */
    public void addHeader(@NotNull String key, @NotNull Object value) {
        this.headers.put(key, value);
    }

    /**
     * Sets header for the request
     *
     * @param key
     *         header key for the request
     * @param value
     *         header value for the request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
    }


    /**
     * Sets header for the request
     *
     * @param key
     *         header key for the request
     */
    public void removeParam(@NotNull String key) {
        this.params.remove(key);
    }


    /**
     * Get all Workflows request retrieves the details of all the Workflows of a stack.
     *
     * @return Call
     */
    public Call<ResponseBody> fetch() {
        return this.service.fetch(this.headers);
    }


    /**
     * Get a Single Workflow request retrieves the comprehensive details of a specific Workflow of a stack.
     *
     * @param workflowUid
     *         the UID of your workflow that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> fetch(@NotNull String workflowUid) {
        return this.service.fetch(this.headers, workflowUid);
    }

    /**
     * The Create a Workflow request allows you to create a Workflow.
     * <p>
     * In the 'Body' section, you can provide the details of the workflow that includes name, content types, owners,
     * description, and workflow stages of your Workflow. To define the branch scope, specify the unique IDs of the
     * branches for which the workflow will be applicable in the following schema in the request body:
     * <pre>
     *     "branches":[
     *     "main",
     *     "development"
     * ]
     * </pre>
     * To control who can edit an entry at different stages of the workflow, you can pass the entry_lock parameter
     * inside each workflow stage.
     *
     * <b>Note:</b> Workflow superusers, organization owners, and stack owners/admins can edit or delete the entry in
     * any workflow stage, irrespective of the stage access rules set for that stage.
     * <p>
     * Read more about <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-workflow">Create-Workflow</a>
     *
     * @param requestBody
     *         The details of the workflow in @{@link JSONObject}
     * @return Call
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.create(this.headers, requestBody);
    }

    /**
     * The Add or Update Workflow request allows you to add a workflow stage or update the details of the existing
     * stages of a workflow.
     * <p>
     * In the 'Body' section, you can provide the updated details of the name, content types, owners, description, and
     * workflow stages of your Workflow. To define the branch scope, specify the unique IDs of the branches for which
     * the workflow will be applicable in the following schema in the request body:
     *
     * <pre>
     *     "branches":[
     *     "main",
     *     "development"
     * ]
     * </pre>
     * To control who can edit an entry at different stages of the workflow, you can pass the entry_lock parameter
     * inside each workflow stage.
     * <p>
     * Note: Workflow superusers, organization owners, and stack owners/admins can edit or delete the entry in any
     * workflow stage, irrespective of the stage access rules set for that stage.
     *
     * <p>Read more about <a
     * *
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#add-or-update-workflow-details">Update-Workflow</a>
     *
     * @param workflowUid
     *         Enter the UID of your workflow whose details you want to update
     * @param requestBody
     *         The body should be of @{@link JSONObject} type
     * @return Call
     */
    public Call<ResponseBody> update(
            @NotNull String workflowUid, @NotNull JSONObject requestBody) {
        return this.service.update(this.headers, workflowUid, requestBody);
    }

    /**
     * Disable Workflow request allows you to disable a workflow.
     *
     * @param workflowUid
     *         The UID of your workflow that you want to disable
     * @return Call
     */
    public Call<ResponseBody> disable(@NotNull String workflowUid) {
        return this.service.disable(this.headers, workflowUid);
    }


    /**
     * Enable Workflow request allows you to enable a workflow.
     *
     * @param workflowUid
     *         The UID of the workflow that you want to enable
     * @return Call
     */
    public Call<ResponseBody> enable(@NotNull String workflowUid) {
        return this.service.enable(this.headers, workflowUid);
    }

    /**
     * Delete Workflow request allows you to delete a workflow.
     *
     * @param workflowUid
     *         The UID of the workflow that you want to delete
     * @return Call
     */
    public Call<ResponseBody> delete(@NotNull String workflowUid) {
        return this.service.delete(this.headers, workflowUid);
    }


    /**
     * The Set Entry Workflow Stage request allows you to either set a particular workflow stage of an entry or update
     * the workflow stage details of an entry.
     * <p>
     * In the <b>Body</b> section, you need to provide the details of the workflow stage. Enter a comment for the
     * assigned user, if needed; provide the due date; set notification settings to <b>true</b>, so that the specified
     * user will be notified of it; enter the UID of the workflow stage; and finally, enter the user details, such as
     * UID, name, and email address of the user.
     * <pre>
     *     {
     * 	"workflow": {
     * 		"workflow_stage": {
     * 			"comment": "Workflow Comment",
     * 			"due_date": "Thu Dec 01 2018",
     * 			"notify": false,
     * 			"uid": "workflow_stage_uid",
     * 			"assigned_to": [{
     * 					"uid": "user_uid",
     * 					"name": "Username",
     * 					"email": "user_email_id"
     *                                        }],
     * 			"assigned_by_roles": [{
     * 				"uid": "role_uid",
     * 				"name": "Role name"
     *            }]
     *        }
     *    }
     * }
     * </pre>
     * <p>
     * <p>
     * <b>Note:</b> To set or update the workflow stage details of an entry, you need to pass the Authtoken in the
     * header. This API request does not support the use of the Management Token.
     *
     * @param contentTypeUid
     *         the content type UID of the entry of which you want to change the workflow stage
     * @param entryUid
     *         The the UID of the entry of which you want to change the workflow stage
     * @param requestBody
     *         The requestBody of type @JSONObject
     * @return Call
     */
    public Call<ResponseBody> updateWorkflowStage(@NotNull String contentTypeUid,
                                                  @NotNull String entryUid,
                                                  @NotNull JSONObject requestBody) {
        return this.service.updateWorkflowStage(this.headers, contentTypeUid, entryUid, this.params, requestBody);
    }


    /**
     * The Create Publish Rules request allows you to create publish rules for the workflow of a stack.
     * <p>
     * To define the branch scope, specify the unique IDs of the branches for which the publishing rule will be
     * applicable in the following schema in the request body:
     * <pre>
     *     "branches":[
     *     "main",
     *     "development"
     * ]
     * </pre>
     * <b>Note:</b> You cannot create publish rules in a stack that supports branches when using the classic
     * Contentstack interface.
     *
     * @param requestBody
     *         Specify the unique IDs of the branches for which the publishing rule will be applicable in the schema in
     *         the request body
     * @return Call
     */
    public Call<ResponseBody> createPublishRule(@NotNull JSONObject requestBody) {
        return this.service.createPublishRules(this.headers, requestBody);
    }


    /**
     * Add or Update Publish Rules request allows you to add a publish rule or update the details of the existing
     * publish rules of a workflow.
     * <p>
     * To define the branch scope, specify the unique IDs of the branches for which the publishing rule will be
     * applicable in the following schema in the request body:
     *
     * @param ruleUid
     *         The UID of the publish rule that you want to update
     * @param requestBody
     *         The request body
     * @return Call
     */
    public Call<ResponseBody> updatePublishRule(@NotNull String ruleUid,
                                                @NotNull JSONObject requestBody) {
        return this.service.updatePublishRules(this.headers, ruleUid, requestBody);
    }


    /**
     * The Delete Publish Rules request allows you to delete an existing publish rule.
     *
     * @param ruleUid
     *         The UID of the publish rule that you want to delete
     * @return Call
     */
    public Call<ResponseBody> deletePublishRule(@NotNull String ruleUid) {
        return this.service.deletePublishRules(this.headers, ruleUid);
    }

    /**
     * The Get all Publish Rules request retrieves the details of all the Publish rules of a workflow.
     *
     * @param contentTypes
     *         comma-separated list of content type UIDs for filtering publish rules on its basis
     * @return Call
     */
    public Call<ResponseBody> fetchPublishRules(@NotNull List<String> contentTypes) {
        this.params.put("content_types", contentTypes);
        this.params.put("include_count", true);
        return this.service.fetchPublishRules(this.headers, this.params);
    }

    /**
     * The Get a Single Publish Rule request retrieves the comprehensive details of a specific publish rule of a
     * Workflow.
     *
     * @param ruleUid
     *         The UID of the publish rule that you want to fetch
     * @return Call
     */
    public Call<ResponseBody> fetchPublishRule(@NotNull String ruleUid) {
        return this.service.fetchPublishRules(this.headers, ruleUid);
    }


    /**
     * The Get Publish Rules by Content Types request allows you to retrieve details of a Publish Rule applied to a
     * specific content type of your stack.
     * <p>
     * When executing the API request, in the 'Header' section, you need to provide the API Key of your stack and the
     * authtoken that you receive after logging into your account.
     *
     * @param contentTypeUid
     *         The UID of the content type of which you want to retrieve the Publishing Rule.
     * @param action
     *         The action that has been set in the Publishing Rule. Example:publish/unpublish
     * @return Call
     */
    public Call<ResponseBody> fetchPublishRuleContentType(@NotNull String contentTypeUid, String action) {
        this.params.put("action", action);
        return this.service.fetchPublishRuleContentType(this.headers, contentTypeUid, this.params);
    }


    /**
     * This multipurpose request allows you to either send a publish request or accept/reject a received publish
     * request.
     * <p>
     * When executing the API request, in the <b>Header</b> section, you need to provide the API Key of your stack and
     * the authtoken that you receive after logging into your account.
     * <p>
     * In the <b>Body</b> section, you need to provide the details of the publish rule, such as its UID, action
     * <b>publish, unpublish, or both</b>, status (this could be <b>0</b> for Approval Requested, <b>1</b> for
     * <b>Approval
     * Accepted</b>, and <b>-1</b> for <b>Approval Rejected</b>), notification setting, and comment for the approver.
     *
     * @param authtoken
     *         authtoken for the request
     * @param contentTypeUid
     *         The unique ID of the content type to which the entry belongs
     * @param entryUid
     *         The unique ID of the entry on which the Publishing Rule is applicable
     * @param requestBody
     *         The request body
     * @return Call
     */
    public Call<ResponseBody> publishRequestApproval(@NotNull String authtoken,
                                                     @NotNull String contentTypeUid,
                                                     @NotNull String entryUid,
                                                     @NotNull JSONObject requestBody) {
        this.headers.put("authtoken", authtoken);
        return this.service.publishRequestApproval(this.headers, contentTypeUid, entryUid, this.params, requestBody);
    }


    /**
     * The Get all Tasks request retrieves a list of all tasks assigned to you.
     * <p>
     * When executing the API request, in the 'Header' section, you need to provide the API Key of your stack and the
     * authtoken that you receive after logging into your account.
     *
     * @param authtoken
     *         authtoken for the request
     * @return Call
     */
    public Call<ResponseBody> fetchTasks(@NotNull String authtoken) {
        this.headers.put("authtoken", authtoken);
        this.headers.put(Util.CONTENT_TYPE, Util.CONTENT_TYPE_VALUE);
        return this.service.fetchTasks(this.headers, this.params);
    }


}
