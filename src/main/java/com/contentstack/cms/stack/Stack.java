package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#organizations">Organization</a>
 * is the top-level entity in the hierarchy of Contentstack,
 * consisting of stacks and stack resources, and users.
 * Organization allows easy management of projects as well
 * as users within the Organization.
 * <p>
 */
public class Stack {

    private final StackService orgService;
    private String authtoken;

    /**
     * Instantiates a new Organization.
     *
     * @param client the client
     */
    public Stack(Retrofit client) {
        this.orgService = client.create(StackService.class);
    }


    /**
     * Gets all organizations. <br>
     * The Get all organizations call lists all organizations
     * related to the system user in the order that they were created
     *
     * @param queryParams the query params
     * @return the all organizations
     */
    public Call<ResponseBody> fetch(HashMap<String, String> queryParams) {
        return this.orgService.get(this.authtoken, queryParams);
    }


    /**
     * Gets organization role.
     *
     * @param organizationUid the organization uid
     * @param queryParams     the query params
     * @return the organization role
     */
    public Call<ResponseBody> getRoles(String organizationUid, Map<String, String> queryParams) {
        return orgService.getRoles(this.authtoken, organizationUid, queryParams);
    }


    /**
     * Gets organization users.
     * <p>
     * The Add users to organization call allows you to send invitations to add users to your organization.
     * Only the owner or the admin of the organization can add users
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * @param organizationUid the organization uid
     * @return the organization users
     */
    public Call<ResponseBody> inviteUser(String organizationUid) {
        return orgService.inviteUser(this.authtoken, organizationUid);
    }


    /**
     * Remove users from organization
     * <br>
     * Note: Only the owner or the admin of the organization can remove users
     * <br>
     * The Remove users from organization request allows you to
     * remove existing users from your organization
     * <br>
     *
     * @param organizationUid the organization uid
     * @return the organization users
     */
    public Call<ResponseBody> removeUsers(String organizationUid) {
        return orgService.removeUser(this.authtoken, organizationUid);
    }

    /**
     * Resend pending organization invitations call.
     * <br>
     * The Resend pending organization invitation call allows you to resend
     * Organization invitations to users who have not yet accepted the earlier
     * invitation. Only the owner or the admin of the Organization can resend
     * the invitation to add users to an Organization
     *
     * @param organizationUid the organization uid
     * @param invitation_uid  the share uid
     * @return the call
     */
    public Call<ResponseBody> resendInvitation(String organizationUid, String invitation_uid) {
        return orgService.resendInvitation(organizationUid, invitation_uid);
    }


    /**
     * Get all organization invitations
     * <br>
     * The Get all organization invitations call gives you a list of all the
     * Organization invitations. Only the owner or the admin of the Organization
     * can resend the invitation to add users to an Organization.
     * <br>
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * @param organizationUid provide the Organization UID
     * @param queryParam      provide the query param
     * @return the call
     */
    public Call<ResponseBody> getAllInvitations(String organizationUid, HashMap<String, String> queryParam) {
        return orgService.getAllInvitations(this.authtoken, organizationUid, queryParam);
    }

    /**
     * Transfer organizations ownership call.<br>
     * The Transfer organization ownership call transfers the ownership of an Organization to another user.
     * When the call is executed, an email invitation for accepting the ownership of a particular
     * Organization is sent to the specified user<br>
     *
     * @param organizationUid provide the Organization UID
     * @return the call
     */
    public Call<ResponseBody> transferOwnership(String organizationUid, String emailId) {
        return orgService.transferOwnership(this.authtoken, organizationUid, emailId);
    }


    /**
     * Gets all stacks in an organizations.
     * <br>
     * The Get all stacks in an organization call fetches the
     * list of all stacks in an Organization
     *
     * @param organizationUid the organization uid
     * @param queryParam      the query param
     * @return the all stacks in an organizations
     */
    public Call<ResponseBody> getStacks(String organizationUid, HashMap<String, String> queryParam) {
        return orgService.getStacks(this.authtoken, organizationUid, queryParam);
    }

    /**
     * Gets organization logs.
     * <br>
     * The Get organization log details request is used to retrieve the audit log details of an organization
     * <br>
     * <b>
     * Tip: This request returns only the first 25 audit log items of
     * the specified organization. If you get more than 25 items in
     * your response, refer the Pagination section to retrieve all
     * the log items in paginated form
     * </b>
     * <br>
     *
     * @param organizationUid the organization uid
     * @param log_uid         the log uid
     * @return the organization logs
     */
    public Call<ResponseBody> getLogs(String organizationUid, String log_uid) {
        return orgService.getLogs(this.authtoken, organizationUid, log_uid);
    }

}
