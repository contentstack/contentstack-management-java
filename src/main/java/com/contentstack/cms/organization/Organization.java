package com.contentstack.cms.organization;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#organizations">Organization</a>
 * is the top-level entity in the hierarchy of Contentstack,
 * consisting of stacks and stack resources, and users.
 * Organization allows easy management of projects as well as users within the Organization.
 * <p>
 */
public class Organization {

    private final OrganizationService orgService;

    /**
     * Instantiates a new Organization.
     *
     * @param client the client
     */
    public Organization(Retrofit client) {
        this.orgService = client.create(OrganizationService.class);
    }


    /**
     * Gets all organizations. The Get all organizations call lists all organizations
     * related to the system user in the order that they were created
     *
     * @param authtoken authtoken
     * @return the all organizations
     */
    public Call<ResponseBody> getAll(String authtoken) {
        return this.orgService.getAll(authtoken, new HashMap<>());
    }

    /**
     * Gets all organizations. <br>
     * The Get all organizations call lists all organizations
     * related to the system user in the order that they were created
     *
     * @param authtoken   the authtoken
     * @param queryParams the query params
     * @return the all organizations
     */
    public Call<ResponseBody> getAll(String authtoken, HashMap<String, String> queryParams) {
        return this.orgService.getAll(authtoken, queryParams);
    }

    /**
     * Gets organization.
     *
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @return the organization
     */
    public Call<ResponseBody> getSingle(String authtoken, String organization_uid) {
        // Provides blank parameters
        return orgService.getOne(authtoken, organization_uid, new HashMap<>());
    }

    /**
     * Gets organization.
     *
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @param queryParams      the query params<br>Example: In case developer wants to add include_plan=true in the query parameter
     *                         <pre>{@code Map<String, String> queryParam = new HashMap();
     *                                                 queryParam.put("include_plan", "true");
     *                                                 }</pre>
     * @return the organization
     */
    public Call<ResponseBody> getSingle(String authtoken, String organization_uid, HashMap<String, Object> queryParams) {
        // Provides queryParams parameters
        return orgService.getOne(authtoken, organization_uid, queryParams);
    }


    /**
     * Gets organization role.
     *
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @return the organization role
     */
    public Call<ResponseBody> getRoles(String authtoken, String organization_uid) {
        return orgService.getRoles(authtoken, organization_uid, new HashMap<>());
    }


    /**
     * Gets organization role.
     *
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @param queryParams      the query params
     * @return the organization role
     */
    public Call<ResponseBody> getRoles(String authtoken, String organization_uid, Map<String, String> queryParams) {
        return orgService.getRoles(authtoken, organization_uid, queryParams);
    }


    /**
     * Gets organization users.
     * <p>
     * The Add users to organization call allows you to send invitations to add users to your organization.
     * Only the owner or the admin of the organization can add users
     * <p>
     * When executing the API call, provide the Organization UID
     *
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @return the organization users
     */
    public Call<ResponseBody> inviteUser(String authtoken, String organization_uid) {
        return orgService.inviteUser(authtoken, organization_uid);
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
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @return the organization users
     */
    public Call<ResponseBody> removeUsers(String authtoken, String organization_uid) {
        return orgService.removeUser(authtoken, organization_uid);
    }

    /**
     * Resend pending organization invitations call.
     * <br>
     * The Resend pending organization invitation call allows you to resend
     * Organization invitations to users who have not yet accepted the earlier
     * invitation. Only the owner or the admin of the Organization can resend
     * the invitation to add users to an Organization
     *
     * @param organization_uid the organization uid
     * @param share_uid        the share uid
     * @return the call
     */
    public Call<ResponseBody> resendInvitation(String organization_uid, String share_uid) {
        return orgService.resendInvitation(organization_uid, share_uid);
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
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @return the call
     */
    public Call<ResponseBody> getAllInvitations(String authtoken, String organization_uid) {
        return orgService.getAllInvitations(authtoken, organization_uid, new HashMap<>());
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
     * @param authtoken        provide the authtoken
     * @param organization_uid provide the Organization UID
     * @param queryParam       provide the query param
     * @return the call
     */
    public Call<ResponseBody> getAllInvitations(String authtoken, String organization_uid,
                                                HashMap<String, String> queryParam) {
        return orgService.getAllInvitations(authtoken, organization_uid, queryParam);
    }

    /**
     * Transfer organizations ownership call.<br>
     * The Transfer organization ownership call transfers the ownership of an Organization to another user.
     * When the call is executed, an email invitation for accepting the ownership of a particular
     * Organization is sent to the specified user<br>
     *
     * @param authtoken        provide the authtoken
     * @param organization_uid provide the Organization UID
     * @return the call
     */
    public Call<ResponseBody> transferOwnership(String authtoken, String organization_uid) {
        return orgService.transferOwnership(authtoken, organization_uid);
    }

    /**
     * Gets all stacks in an organizations.
     * <br>
     * The Get all stacks in an organization call fetches the
     * list of all stacks in an Organization
     *
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @return the all stacks in an organizations
     */
    public Call<ResponseBody> getStacks(String authtoken, String organization_uid) {
        return orgService.getStacks(authtoken, organization_uid, new HashMap<>());
    }


    /**
     * Gets all stacks in an organizations.
     * <br>
     * The Get all stacks in an organization call fetches the
     * list of all stacks in an Organization
     *
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @param queryParam       the query param
     * @return the all stacks in an organizations
     */
    public Call<ResponseBody> getStacks(String authtoken, String organization_uid, HashMap<String, String> queryParam) {
        return orgService.getStacks(authtoken, organization_uid, queryParam);
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
     * @param authtoken        the authtoken
     * @param organization_uid the organization uid
     * @param log_uid          the log uid
     * @return the organization logs
     */
    public Call<ResponseBody> getLogs(String authtoken, String organization_uid, String log_uid) {
        return orgService.getLogs(authtoken, organization_uid, log_uid);
    }

}
