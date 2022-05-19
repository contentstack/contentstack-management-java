package com.contentstack.cms.organization;

import com.contentstack.cms.core.Util;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * Organization allows easy management of projects as well as users within the Organization.
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Organization {

    private final OrganizationService orgService;

    /**
     * Instantiates a new Organization.
     *
     * @param client
     *         the client
     */
    public Organization(Retrofit client) {
        this.orgService = client.create(OrganizationService.class);
    }

    /**
     * <b>Gets all organizations.</b><br>
     * The Get all organizations call lists all organizations related to the system user in the order that they were
     * created
     *
     * @return the all organizations
     */
    public Call<ResponseBody> getAll() {
        return this.orgService.getAll(new HashMap<>());
    }

    /**
     * <b>Gets all organizations.</b><br>
     * The Get all organizations call lists all organizations related to the system user in the order that they were
     * created
     *
     * @param queryParams
     *         the query params query parameters are below <br>
     *         <ul>
     *         <li>limit(optional) The
     *         <b>limit</b> parameter will return a specific number of
     *         Organization roles in the output.
     *         Example, if there are 10 organization roles and you wish
     *         to fetch only the first 2, you need to
     *         specify '2' as the value in this parameter. <br>
     *         <li>skip(optional) The <b>skip</b>
     *         parameter will skip a specific number of Organization
     *         roles in the output. For example, if there
     *         are 12 organization roles and you want to skip the first 2
     *         to get only the last 10 in the
     *         response body, you need to specify '2' here. <br>
     *         <li>asc(optional) The <b>asc</b>
     *         parameter allows you to sort the list of organization
     *         roles in ascending order on the basis of a
     *         parameter. <br>
     *         <li>desc(optional) The <b>desc</b> parameter allows you to
     *         sort the list
     *         of organization roles in descending order on the basis of
     *         a parameter. <br>
     *         <li>
     *         include_count(optional) The <b>include_count</b> parameter
     *         returns the total number of roles in
     *         an organization. For example: If you want to know the
     *         total number of roles in an organization,
     *         you need to mention 'true'. <br>
     *         <li>typehead(optional) = "contentstack"
     *         </ul>
     * @return the all organizations
     */
    public Call<ResponseBody> getAll(Map<String, Object> queryParams) {
        return this.orgService.getAll(queryParams);
    }

    /**
     * Get a single organization
     * <br>
     * The Get a single organization call gets the comprehensive details of a specific organization related to the
     * system user
     * <br>
     *
     * @param organizationUid
     *         the organization uid
     * @return the organization users throws {@link java.io.IOException} if spaceId is null.
     */
    public Call<ResponseBody> getSingleOrganization(@NotNull String organizationUid) {
        return orgService.getSingle(organizationUid, new HashMap<>());
    }

    /**
     * Get a single organization
     * <br>
     * The Get a single organization call gets the comprehensive details of a specific organization related to the
     * system user
     * <br>
     *
     * @param organizationUid
     *         the organization uid
     * @param query
     *         the Query Parameters include_plan(optional) = true
     * @return the organization users throws {@link java.io.IOException} when execute is called.
     */
    public Call<ResponseBody> getSingleOrganization(@NotNull String organizationUid, Map<String, Object> query) {

        return orgService.getSingle(organizationUid, query);
    }

    /**
     * Gets organization role.
     *
     * @param organizationUid
     *         the organization uid
     * @return the organization role
     */
    public Call<ResponseBody> getRoles(String organizationUid) {
        return orgService.getRoles(organizationUid, new HashMap<>());
    }

    /**
     * Gets organization role.
     *
     * @param organizationUid
     *         the organization uid
     * @param queryParams
     *         the query params query parameters are below <br>
     *         <ul>
     *         <li>limit(optional) The
     *         <b>limit</b> parameter will return a specific number
     *         of Organization roles in the output.
     *         Example, if there are 10 organization roles, and you
     *         wish to fetch only the first 2, you need to
     *         specify '2' as the value in this parameter. <br>
     *         <li>skip(optional) The <b>skip</b>
     *         parameter will skip a specific number of Organization
     *         roles in the output. For example, if there
     *         are 12 organization roles and you want to skip the
     *         first 2 to get only the last 10 in the
     *         response body, you need to specify '2' here. <br>
     *         <li>asc(optional) The <b>asc</b>
     *         parameter allows you to sort the list of organization
     *         roles in an ascending order on the basis of
     *         a parameter. <br>
     *         <li>desc(optional) The <b>desc</b> parameter allows
     *         you to sort the
     *         list of organization roles in descending order on the
     *         basis of a parameter. <br>
     *         <li>include_count(optional) The <b>include_count</b>
     *         parameter returns the total number of
     *         roles
     *         in an organization. For example: If you want to know
     *         the total number of roles in an
     *         organization, you need to mention <b>true</b>. <br>
     *         <li>include_stack_roles(optional)
     *         The <b>include_stack_roles</b> parameter, when set to
     *         <b>true</b>, includes the details of
     *         stack-level roles in the Response body.
     *         </ul>
     * @return the organization role
     */
    public Call<ResponseBody> getRoles(String organizationUid, Map<String, Object> queryParams) {
        return orgService.getRoles(organizationUid, queryParams);
    }

    /**
     * Gets organization users.
     * <p>
     * The Add users to organization call allows you to send invitations to add users to your organization. Only the
     * owner or the admin of the organization can add users
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * @param organizationUid
     *         the organization uid
     * @param bodyString
     *         the body string
     * @return the organization users
     */
    public Call<ResponseBody> inviteUser(String organizationUid, String bodyString) {
        RequestBody body = Util.toRequestBody(bodyString);
        return orgService.inviteUser(organizationUid, body);
    }

    /**
     * Remove users from organization
     * <br>
     * Note: Only the owner or the admin of the organization can remove users
     * <br>
     * The Remove users from organization request allows you to remove existing users from your organization
     * <br>
     *
     * @param organizationUid
     *         the organization uid
     * @param bodyString
     *         the body string
     * @return {@link okhttp3.Call}
     */
    public Call<ResponseBody> removeUsers(String organizationUid, String bodyString) {
        RequestBody body = Util.toRequestBody(bodyString);
        return orgService.removeUser(organizationUid, body);
    }

    /**
     * Resend pending organization invitations call.
     * <br>
     * Resend pending organization invitation call allows you to resend Organization invitations to users who have not
     * yet accepted the earlier invitation. Only the owner or the admin of the Organization can resend the invitation to
     * add users to an Organization
     *
     * @param organizationUid
     *         the organization uid
     * @param shareUid
     *         the share uid
     * @return the {@link okhttp3.Call}
     */
    public Call<ResponseBody> resendInvitation(String organizationUid, String shareUid) {
        return orgService.resendInvitation(organizationUid, shareUid);
    }

    /**
     * Get all organization invitations
     * <br>
     * The Get all organization invitations call gives you a list of all the Organization invitations. Only the owner or
     * the admin of the Organization can resend the invitation to add users to an Organization.
     * <br>
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * @param organizationUid
     *         provide the Organization UID
     * @return the {@link okhttp3.Call}
     */
    public Call<ResponseBody> getAllInvitations(String organizationUid) {
        return orgService.getAllInvitations(organizationUid, new HashMap<>());
    }

    /**
     * Get all organization invitations
     * <br>
     * The Get all organization invitations call gives you a list of all the Organization invitations. Only the owner or
     * the admin of the Organization can resend the invitation to add users to an Organization.
     * <br>
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * @param organizationUid
     *         provide the Organization UID
     * @param queryParam
     *         the query params query parameters are below <br>
     *         <ul>
     *         <li>limit(optional) The
     *         'limit' parameter will return a specific number of
     *         sent organization invitations in the output.
     *         Example, if 10 invitations were sent out and you wish
     *         to fetch only the first 8, you need to
     *         specify '2' as the value in this parameter. <br>
     *         <li>skip(optional) The 'skip' parameter
     *         will skip a specific number of organization roles in
     *         the output. Example, if there are 12
     *         organization roles and you want to skip the last 2 to
     *         get only the first 10 in the response body,
     *         you need to specify '2' here. <br>
     *         <li>asc(optional) The 'asc' parameter allows you to
     *         sort the list of organization invitations in ascending
     *         order on the basis of a specific
     *         parameter. <br>
     *         <li>desc(optional) The 'desc' parameter allows you to
     *         sort the list of
     *         organization invitations in descending order on the
     *         basis of a specific parameter. <br>
     *         <li>include_count(optional) The 'include_count'
     *         parameter returns the total number of organization
     *         invitations sent out. Example: If you wish to know the
     *         total number of organization invitations,
     *         you need to mention 'true'. <br>
     *         <li>include_roles(optional) The 'include_roles'
     *         parameter, when set to 'true', will display the
     *         details of the roles that are assigned to the
     *         user in an organization. <br>
     *         <li>include_invited_by(optional) The
     *         'include_invited_by'
     *         parameter, when set to 'true', includes the details of
     *         the user who sent out the organization
     *         invitation. <br>
     *         <li>include_user_details(optional) The
     *         'include_user_details'
     *         parameter, when set to 'true', lets you know whether
     *         the user who has been sent the organization
     *         invitation has enabled Two-factor Authentication or
     *         not. <br>
     *         <li>
     *         typeahead(optional) The 'typeahead' parameter allows
     *         you to perform a name-based search on all the
     *         stacks on an organization based on the value provided.
     *         For example, it allows you to perform an
     *         email-ID-based search on all users based on the email
     *         ID provided.
     *         </ul>
     * @return the {@link okhttp3.Call}
     */
    public Call<ResponseBody> getAllInvitations(String organizationUid, Map<String, Object> queryParam) {
        return orgService.getAllInvitations(organizationUid, queryParam);
    }

    /**
     * Transfer organizations ownership call.<br> The Transfer organization ownership call transfers the ownership of an
     * Organization to another user. When the call is executed, an email invitation for accepting the ownership of a
     * particular Organization is sent to the specified user<br>
     *
     * @param organizationUid
     *         provide the Organization UID
     * @param bodyString
     *         = { "transfer_to": "abc@sample.com" }
     * @return the call
     */
    public Call<ResponseBody> transferOwnership(String organizationUid, String bodyString) {
        RequestBody body = Util.toRequestBody(bodyString);
        return orgService.transferOwnership(organizationUid, body);
    }

    /**
     * Gets all stacks in an organizations.
     * <br>
     * The Get all stacks in an organization call fetches the list of all stacks in an Organization
     *
     * @param organizationUid
     *         the organization uid
     * @param queryParam
     *         the query params query parameters are below <br>
     *         <ul>
     *         <li>limit(optional) The
     *         'limit' parameter will return a specific number of
     *         sent organization invitations in the output.
     *         Example, if 10 invitations were sent out and you wish
     *         to fetch only the first 8, you need to
     *         specify '2' as the value in this parameter. <br>
     *         <li>skip(optional) The 'skip' parameter
     *         will skip a specific number of organization roles in
     *         the output. Example, if there are 12
     *         organization roles and you want to skip the last 2 to
     *         get only the first 10 in the response body,
     *         you need to specify '2' here. <br>
     *         <li>asc(optional) The 'asc' parameter allows you to
     *         sort the list of organization invitations in ascending
     *         order on the basis of a specific
     *         parameter. <br>
     *         <li>desc(optional) The 'desc' parameter allows you to
     *         sort the list of
     *         organization invitations in descending order on the
     *         basis of a specific parameter. <br>
     *         <li>include_count(optional) The 'include_count'
     *         parameter returns the total number of organization
     *         invitations sent out. Example: If you wish to know the
     *         total number of organization invitations,
     *         you need to mention 'true'.
     *         <li>typeahead(optional) The 'typeahead' parameter
     *         allows you
     *         to perform a name-based search on all the stacks on an
     *         organization based on the value provided.
     *         </ul>
     * @return the all stacks in an organizations
     */
    public Call<ResponseBody> getStacks(String organizationUid, Map<String, Object> queryParam) {
        return orgService.getStacks(organizationUid, queryParam);
    }

    /**
     * Gets organization logs.
     * <br>
     * The Get organization log details request is used to retrieve the audit log details of an organization
     * <br>
     * <b>
     * Tip: This request returns only the first 25 audit log items of the specified organization. If you get more than
     * 25 items in your response, refer the Pagination section to retrieve all the log items in paginated form
     * </b>
     * <br>
     *
     * @param organizationUid
     *         the organization uid
     * @return the organization logs
     */
    public Call<ResponseBody> getLogsDetails(String organizationUid) {
        return orgService.getLogDetails(organizationUid);
    }

    /**
     * Gets organization logs.
     * <br>
     * The Get organization log details request is used to retrieve the audit log details of an organization
     * <br>
     * <b>
     * Tip: This request returns only the first 25 audit log items of the specified organization. If you get more than
     * 25 items in your response, refer the Pagination section to retrieve all the log items in paginated form
     * </b>
     * <br>
     *
     * @param organizationUid
     *         the organization uid
     * @param logUid
     *         the log uid
     * @return the organization logs
     */
    public Call<ResponseBody> getLogsItem(String organizationUid, String logUid) {
        return orgService.getLogItems(organizationUid, logUid);
    }


}
