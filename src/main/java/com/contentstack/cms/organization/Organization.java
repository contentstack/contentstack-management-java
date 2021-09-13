package com.contentstack.cms.organization;

import com.contentstack.cms.core.Util;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

import static com.contentstack.cms.core.Util.assertNotNull;

/**
 * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#organizations">Organization</a>
 * is the top-level entity in the hierarchy of Contentstack,
 * consisting of stacks and <a href="https://www.contentstack.com/docs/developers/set-up-stack/about-stack">stack</a>
 * resources, and users.<br/>
 * Organization allows easy management of projects as well
 * as users within the Organization.
 */
public class Organization {

    private OrganizationService orgService;
    protected String authtoken; // protected due to unit test access


    private Organization() {
        Util.assertionError();
    }


    public Organization(@NotNull String authtoken) {
        this.authtoken = authtoken;
    }

    /**
     * Instantiates a new Organization.
     *
     * @param client the client
     */
    public Organization(@NotNull Retrofit client) {
        this.orgService = client.create(OrganizationService.class);
    }


    /**
     * Instantiates a new Organization.
     *
     * @param client    retrofit client instance
     * @param authtoken authtoken
     */
    public Organization(@NotNull Retrofit client, @NotNull String authtoken) {
        this.authtoken = authtoken;
        this.orgService = client.create(OrganizationService.class);
    }


    /**
     * <b>Gets all organizations.</b><br>
     * The Get all organizations call lists all organizations
     * related to the system user in the order that they were created
     *
     * @return the all organizations
     */
    public Call<ResponseBody> getAll() {
        return this.orgService.get(this.authtoken);
    }


    /**
     * <b>Gets all organizations.</b><br>
     * The Get all organizations call lists all organizations
     * related to the system user in the order that they were created
     *
     * @param queryParams Below are the query parameters:<br/>
     *                    <p>
     *                    <br/><b>limit(optional):</b>
     *                    The ‘limit’ parameter will return a specific number of entries in the output.
     *                    <br/>Example, if there are 10 organizations and you wish to fetch only the first 2,
     *                    you need to specify '2' as the value in this parameter.
     *                    <p>
     *                    <p>
     *                    <br/><br/><b>skip(optional):</b>
     *                    The ‘skip’ parameter will skip a specific number of organizations in the output.
     *                    <br/>Example, if there are 12 organizations and you want to skip the first 2 to get
     *                    only the last 10 in the response body, you need to specify ‘2’ here.
     *                    <p>
     *                    <br/><br/><b/>asc(optional):</b>
     *                    The ‘asc’ parameter allows you to sort the list of organizations
     *                    in the ascending order with respect to the value of a specific field
     *                    <p>
     *                    <br/><br/><b/>desc(optional):</b>
     *                    The ‘desc’ parameter allows you to sort the list of Organizations
     *                    in the descending order with respect to the value of a specific field
     *                    <p>
     *                    <br/><br/><b/>include_count(optional):</b>
     *                    The ‘include_count’ parameter returns the total number of organizations related to the user.
     *                    <br/>Example: If you wish to know the total number of organizations,
     *                    you need to mention ‘true’.
     *                    <p>
     *                    <br/><br/><b/>typeahead(optional):</b>
     *                    The typeahead parameter is a type of filter that allows you to perform a
     *                    name-based search on all organizations based on the value provided.
     *                    <br/>Example, if we have four organizations named
     *                    ‘ABC’, ‘ABC1’, ‘XYZ’, and ‘ACC’, and we provide ‘ABC’ as
     *                    the value to this parameter, the search result will return the organizations
     *                    ‘ABC’ and ‘ABC1’ as the output
     * @return the all organizations
     */
    public Call<ResponseBody> getAll(
            HashMap<String, Object> queryParams) {
        return this.orgService.get(this.authtoken, queryParams);
    }


    /**
     * Get a single organization
     * <br>
     * The Get a single organization call gets the comprehensive details of a
     * specific organization related to the system user
     * <br>
     *
     * @param organizationUid the organization uid
     * @param options         the Query Parameters
     *                        include_plan(optional) = true
     * @return the organization users
     * @throws IllegalArgumentException if spaceId is null.
     */
    public Call<ResponseBody> getSingleOrganization(
            String organizationUid,
            HashMap<String, String> options) {
        assertNotNull(organizationUid, "organizationUid");
        return orgService.singleOrganization(organizationUid, options);
    }


//    /**
//     * Gets organization role.
//     *
//     * @param organizationUid the organization uid
//     * @param queryParams     the query params
//     * @return the organization role
//     */
//    public Call<ResponseBody> getRoles(String organizationUid, Map<String, String> queryParams) {
//        return orgService.getRoles(this.authtoken, organizationUid, queryParams);
//    }
//
//
//    /**
//     * Gets organization users.
//     * <p>
//     * The Add users to organization call allows you to send invitations to add users to your organization.
//     * Only the owner or the admin of the organization can add users
//     * <p>
//     * When executing the API call, provide the Organization UID
//     *
//     * @param organizationUid the organization uid
//     * @return the organization users
//     */
//    public Call<ResponseBody> inviteUser(String organizationUid) {
//        return orgService.inviteUser(this.authtoken, organizationUid);
//    }
//
//
//    /**
//     * Remove users from organization
//     * <br>
//     * Note: Only the owner or the admin of the organization can remove users
//     * <br>
//     * The Remove users from organization request allows you to
//     * remove existing users from your organization
//     * <br>
//     *
//     * @param organizationUid the organization uid
//     * @return the organization users
//     */
//    public Call<ResponseBody> removeUsers(String organizationUid) {
//        return orgService.removeUser(this.authtoken, organizationUid);
//    }
//
//    /**
//     * Resend pending organization invitations call.
//     * <br>
//     * The Resend pending organization invitation call allows you to resend
//     * Organization invitations to users who have not yet accepted the earlier
//     * invitation. Only the owner or the admin of the Organization can resend
//     * the invitation to add users to an Organization
//     *
//     * @param organizationUid the organization uid
//     * @param invitation_uid  the share uid
//     * @return the call
//     */
//    public Call<ResponseBody> resendInvitation(String organizationUid, String invitation_uid) {
//        return orgService.resendInvitation(organizationUid, invitation_uid);
//    }
//
//
//    /**
//     * Get all organization invitations
//     * <br>
//     * The Get all organization invitations call gives you a list of all the
//     * Organization invitations. Only the owner or the admin of the Organization
//     * can resend the invitation to add users to an Organization.
//     * <br>
//     * <p>
//     * When executing the API call, provide the Organization UID
//     *
//     * @param organizationUid provide the Organization UID
//     * @param queryParam      provide the query param
//     * @return the call
//     */
//    public Call<ResponseBody> getAllInvitations(String organizationUid, HashMap<String, String> queryParam) {
//        return orgService.getAllInvitations(this.authtoken, organizationUid, queryParam);
//    }
//
//    /**
//     * Transfer organizations ownership call.<br>
//     * The Transfer organization ownership call transfers the ownership of an Organization to another user.
//     * When the call is executed, an email invitation for accepting the ownership of a particular
//     * Organization is sent to the specified user<br>
//     *
//     * @param organizationUid provide the Organization UID
//     * @return the call
//     */
//    public Call<ResponseBody> transferOwnership(String organizationUid, String emailId) {
//        return orgService.transferOwnership(this.authtoken, organizationUid, emailId);
//    }
//
//
//    /**
//     * Gets all stacks in an organizations.
//     * <br>
//     * The Get all stacks in an organization call fetches the
//     * list of all stacks in an Organization
//     *
//     * @param organizationUid the organization uid
//     * @param queryParam      the query param
//     * @return the all stacks in an organizations
//     */
//    public Call<ResponseBody> getStacks(String organizationUid, HashMap<String, String> queryParam) {
//        return orgService.getStacks(this.authtoken, organizationUid, queryParam);
//    }
//
//    /**
//     * Gets organization logs.
//     * <br>
//     * The Get organization log details request is used to retrieve the audit log details of an organization
//     * <br>
//     * <b>
//     * Tip: This request returns only the first 25 audit log items of
//     * the specified organization. If you get more than 25 items in
//     * your response, refer the Pagination section to retrieve all
//     * the log items in paginated form
//     * </b>
//     * <br>
//     *
//     * @param organizationUid the organization uid
//     * @param log_uid         the log uid
//     * @return the organization logs
//     */
//    public Call<ResponseBody> getLogs(String organizationUid, String log_uid) {
//        return orgService.getLogs(this.authtoken, organizationUid, log_uid);
//    }

}
