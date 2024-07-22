package com.contentstack.cms.organization;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Objects;

/**
 * Organization is the top-level entity in the hierarchy of Contentstack,
 * consisting of stacks and stack resources, and
 * users. Organization allows easy management of projects as well as users
 * within the Organization.
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-20
 */
public class Organization implements BaseImplementation<Organization> {

    private final OrganizationService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;
    private String organizationUid;
    final String ERROR_MSG = "OrganizationUid Can Not Be Null OR Empty";


    /**
     * Instantiates a new Organization.
     *
     * @param client The retrofit client
     */
    public Organization(Retrofit client) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = client.create(OrganizationService.class);
    }

    /**
     * Instantiates a new Organization.
     *
     * @param client The retrofit client
     * @param uid    The uid of the organisation
     */
    public Organization(Retrofit client, String uid) {
        this.headers = new HashMap<>();
        this.organizationUid = uid;
        this.params = new HashMap<>();
        this.service = client.create(OrganizationService.class);
    }

    /**
     * Sets header for the request
     *
     * @param key   header key for the request
     * @param value header value for the request
     *              return Organization the instance of Organization
     * @return Organization instance of organization
     */
    public Organization addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public Organization addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public Organization addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * Sets header for the request
     *
     * @param key   header key for the request
     * @param value header value for the request
     * @return instance of Organization
     */
    public Organization addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * The function clears the parameters of an organization object and returns the
     * object itself.
     *
     * @return The method is returning an instance of the Organization class.
     */

    protected Organization clearParams() {
        this.params.clear();
        return this;
    }

    /**
     * <b>Gets all organizations.</b><br>
     * The <b>Get all organizations</b> call lists all organizations related to the
     * system user in the order that they
     * were created
     * <br>
     * {@link #addParam(String, Object)} queryParams the query params query
     * parameters are below <br>
     * <ul>
     * <li>limit(optional) The
     * <b>limit</b> parameter will return a specific number of
     * Organization roles in the output.
     * Example, if there are 10 organization roles and you wish
     * to fetch only the first 2, you need to
     * specify '2' as the value in this parameter. <br>
     * <li>skip(optional) The <b>skip</b>
     * parameter will skip a specific number of Organization
     * roles in the output. For example, if there
     * are 12 organization roles and you want to skip the first 2
     * to get only the last 10 in the
     * response body, you need to specify '2' here. <br>
     * <li>asc(optional) The <b>asc</b>
     * parameter allows you to sort the list of organization
     * roles in ascending order on the basis of a
     * parameter. <br>
     * <li>desc(optional) The <b>desc</b> parameter allows you to
     * sort the list
     * of organization roles in descending order on the basis of
     * a parameter. <br>
     * <li>
     * include_count(optional) The <b>include_count</b> parameter
     * returns the total number of roles in
     * an organization. For example: If you want to know the
     * total number of roles in an organization,
     * you need to mention 'true'. <br>
     * <li>typehead(optional) = "contentstack"
     * </ul>
     *
     * @return Call
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * Get a single organization
     * <br>
     * The Get a single organization call gets the comprehensive details of a
     * specific organization related to the
     * system user
     * <br>
     *
     * <br>
     * {@link #addParam(String, Object)} the Query Parameters include_plan(optional)
     * = true
     *
     * @return Call
     */
    public Call<ResponseBody> fetch() {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.getSingle(this.headers, organizationUid, this.params);
    }

    /**
     * Gets organization role.
     *
     * <br>
     * {@link #addParam(String, Object)} the query params query parameters are below
     * <br>
     * <ul>
     * <li>limit(optional) The
     * <b>limit</b> parameter will return a specific number
     * of Organization roles in the output.
     * Example, if there are 10 organization roles, and you
     * wish to fetch only the first 2, you need to
     * specify '2' as the value in this parameter. <br>
     * <li>skip(optional) The <b>skip</b>
     * parameter will skip a specific number of Organization
     * roles in the output. For example, if there
     * are 12 organization roles and you want to skip the
     * first 2 to get only the last 10 in the
     * response body, you need to specify '2' here. <br>
     * <li>asc(optional) The <b>asc</b>
     * parameter allows you to sort the list of organization
     * roles in an ascending order on the basis of
     * a parameter. <br>
     * <li>desc(optional) The <b>desc</b> parameter allows
     * you to sort the
     * list of organization roles in descending order on the
     * basis of a parameter. <br>
     * <li>include_count(optional) The <b>include_count</b>
     * parameter returns the total number of
     * roles
     * in an organization. For example: If you want to know
     * the total number of roles in an
     * organization, you need to mention <b>true</b>. <br>
     * <li>include_stack_roles(optional)
     * The <b>include_stack_roles</b> parameter, when set to
     * <b>true</b>, includes the details of
     * stack-level roles in the Response body.
     * </ul>
     *
     * @return Call
     */
    public Call<ResponseBody> roles() {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.getRoles(this.headers, this.organizationUid, this.params);
    }

    /**
     * Gets organization users.
     * <p>
     * The Add users to organization call allows you to send invitations to add
     * users to your organization. Only the
     * owner or the admin of the organization can add users
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * @param body the request body JSONObject
     * @return Call
     */
    public Call<ResponseBody> inviteUser(JSONObject body) {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.inviteUser(this.headers, this.organizationUid, body);
    }

    /**
     * Remove users from organization
     * <br>
     * Note: Only the owner or the admin of the organization can remove users
     * <br>
     * The Remove users from organization request allows you to remove existing
     * users from your organization
     * <br>
     *
     * @param body the request body JSONObject
     * @return Call
     */
    public Call<ResponseBody> removeUsers(JSONObject body) {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.removeUser(this.headers, this.organizationUid, body);
    }

    /**
     * Resend pending organization invitations call.
     * <br>
     * Resend pending organization invitation call allows you to resend Organization
     * invitations to users who have not
     * yet accepted the earlier invitation. Only the owner or the admin of the
     * Organization can resend the invitation to
     * add users to an Organization
     *
     * @param shareUid the share uid
     * @return Call
     */
    public Call<ResponseBody> resendInvitation(String shareUid) {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.resendInvitation(this.headers, this.organizationUid, shareUid);
    }

    /**
     * Get all organization invitations
     * <br>
     * The Get all organization invitations call gives you a list of all the
     * Organization invitations. Only the owner or
     * the admin of the Organization can resend the invitation to add users to an
     * Organization.
     * <br>
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * <br>
     * {@link #addParam(String, Object)} the query params query parameters are below
     * <br>
     * <ul>
     * <li>limit(optional) The
     * 'limit' parameter will return a specific number of
     * sent organization invitations in the output.
     * Example, if 10 invitations were sent out and you wish
     * to fetch only the first 8, you need to
     * specify '2' as the value in this parameter. <br>
     * <li>skip(optional) The 'skip' parameter
     * will skip a specific number of organization roles in
     * the output. Example, if there are 12
     * organization roles and you want to skip the last 2 to
     * get only the first 10 in the response body,
     * you need to specify '2' here. <br>
     * <li>asc(optional) The 'asc' parameter allows you to
     * sort the list of organization invitations in ascending
     * order on the basis of a specific
     * parameter. <br>
     * <li>desc(optional) The 'desc' parameter allows you to
     * sort the list of
     * organization invitations in descending order on the
     * basis of a specific parameter. <br>
     * <li>include_count(optional) The 'include_count'
     * parameter returns the total number of organization
     * invitations sent out. Example: If you wish to know the
     * total number of organization invitations,
     * you need to mention 'true'. <br>
     * <li>include_roles(optional) The 'include_roles'
     * parameter, when set to 'true', will display the
     * details of the roles that are assigned to the
     * user in an organization. <br>
     * <li>include_invited_by(optional) The
     * 'include_invited_by'
     * parameter, when set to 'true', includes the details of
     * the user who sent out the organization
     * invitation. <br>
     * <li>include_user_details(optional) The
     * 'include_user_details'
     * parameter, when set to 'true', lets you know whether
     * the user who has been sent the organization
     * invitation has enabled Two-factor Authentication or
     * not. <br>
     * <li>
     * typeahead(optional) The 'typeahead' parameter allows
     * you to perform a name-based search on all the
     * stacks on an organization based on the value provided.
     * For example, it allows you to perform an
     * email-ID-based search on all users based on the email
     * ID provided.
     * </ul>
     *
     * @return Call
     */
    public Call<ResponseBody> allInvitations() {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.getAllInvitations(this.headers, this.organizationUid, this.params);
    }

    /**
     * Transfer organizations ownership call.<br>
     * The Transfer organization ownership call transfers the ownership of an
     * Organization to another user. When the call is executed, an email invitation
     * for accepting the ownership of a
     * particular Organization is sent to the specified user<br>
     *
     * @param body The request body @codes { "transfer_to": "abc@sample.com" }
     * @return Call
     */
    public Call<ResponseBody> transferOwnership(JSONObject body) {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.transferOwnership(this.headers, this.organizationUid, body);
    }

    /**
     * Gets all stacks in an organizations.
     * <br>
     * The Get all stacks in an organization call fetches the list of all stacks in
     * an Organization
     *
     * <br>
     * {@link #addParam(String, Object)} the query params query parameters are below
     * <br>
     * <ul>
     * <li>limit(optional) The
     * 'limit' parameter will return a specific number of
     * sent organization invitations in the output.
     * Example, if 10 invitations were sent out and you wish
     * to fetch only the first 8, you need to
     * specify '2' as the value in this parameter. <br>
     * <li>skip(optional) The 'skip' parameter
     * will skip a specific number of organization roles in
     * the output. Example, if there are 12
     * organization roles and you want to skip the last 2 to
     * get only the first 10 in the response body,
     * you need to specify '2' here. <br>
     * <li>asc(optional) The 'asc' parameter allows you to
     * sort the list of organization invitations in ascending
     * order on the basis of a specific
     * parameter. <br>
     * <li>desc(optional) The 'desc' parameter allows you to
     * sort the list of
     * organization invitations in descending order on the
     * basis of a specific parameter. <br>
     * <li>include_count(optional) The 'include_count'
     * parameter returns the total number of organization
     * invitations sent out. Example: If you wish to know the
     * total number of organization invitations,
     * you need to mention 'true'.
     * <li>typeahead(optional) The 'typeahead' parameter
     * allows you
     * to perform a name-based search on all the stacks on an
     * organization based on the value provided.
     * </ul>
     *
     * @return Call
     */
    public Call<ResponseBody> stacks() {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.getStacks(this.headers, this.organizationUid, this.params);
    }

    /**
     * Gets organization logs.
     * <br>
     * The Get organization log details request is used to retrieve the audit log
     * details of an organization
     * <br>
     * <b>
     * Tip: This request returns only the first 25 audit log items of the specified
     * organization. If you get more than
     * 25 items in your response, refer the Pagination section to retrieve all the
     * log items in paginated form
     * </b>
     * <br>
     *
     * @return Call
     */
    public Call<ResponseBody> logsDetails() {
        return service.getLogDetails(this.headers, this.organizationUid);
    }

    /**
     * Gets organization logs.
     * <br>
     * The Get organization log details request is used to retrieve the audit log
     * details of an organization
     * <br>
     * <b>
     * Tip: This request returns only the first 25 audit log items of the specified
     * organization. If you get more than
     * 25 items in your response, refer the Pagination section to retrieve all the
     * log items in paginated form
     * </b>
     * <br>
     *
     * @param logUid the log uid
     * @return Call
     */
    public Call<ResponseBody> logItem(String logUid) {
        Objects.requireNonNull(this.organizationUid, ERROR_MSG);
        return service.getLogItems(this.headers, this.organizationUid, logUid);
    }

}
