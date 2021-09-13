package com.contentstack.cms.organization;

import com.contentstack.cms.user.CSResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * The interface Organization service.
 */
public interface OrganizationService {


    /**
     * Gets all organizations.
     *
     * @param authtoken the authtoken
     * @return the all
     */
    @GET("organizations")
    Call<ResponseBody> get(
            @Header("authtoken") String authtoken
    );

    /**
     * s
     * Gets all organizations.
     *
     * @param authtoken the authtoken
     * @param options   the query params
     * @return the all
     */
    @GET("organizations")
    Call<ResponseBody> get(@Header("authtoken") String authtoken,
                           @QueryMap Map<String, Object> options);


    @GET("organizations/{organization_uid}")
    Call<ResponseBody> singleOrganization(
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, String> options);


    /**
     * Gets roles.
     *
     * @param authtoken the authtoken
     * @param _uid      the uid
     * @param options   the query params
     * @return the roles
     */
    @GET("organizations/{organization_uid}/roles")
    Call<ResponseBody> getRoles(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, String> options);

    /**
     * Invite user call.
     *
     * @param authtoken the authtoken
     * @param _uid      the uid
     * @return the call
     */
    @POST("organizations/{organization_uid}/share")
    Call<ResponseBody> inviteUser(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid);


    /**
     * Remove user call.
     *
     * @param authtoken the authtoken
     * @param _uid      the uid
     * @return the call
     */
    @DELETE("organizations/{organization_uid}/share")
    Call<ResponseBody> removeUser(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid);

    /**
     * Resend invitation call.
     *
     * @param _uid      the uid
     * @param share_uid the share uid
     * @return the call
     */
    @GET("organizations/{organization_uid}/share/{share_uid}")
    Call<ResponseBody> resendInvitation(
            @Path("organization_uid") String _uid,
            @Path("share_uid") String share_uid);

    /**
     * Gets all invitations.
     *
     * @param authtoken the authtoken
     * @param _uid      the uid
     * @param options   the query params
     * @return the all invitations
     */
    @GET("organizations/{organization_uid}/share")
    Call<ResponseBody> getAllInvitations(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, String> options);

    /**
     * Transfer ownership call.
     *
     * @param authtoken the authtoken
     * @param _uid      the uid
     * @return the call
     */
    @FormUrlEncoded
    @POST("organizations/{organization_uid}/ownership")
    Call<ResponseBody> transferOwnership(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @Body String transfer_to);

    /**
     * The Get all stacks in an organization call
     * fetches the list of all stacks in an Organization.
     * <br>
     * When executing the API call, provide the Organization UID.
     *
     * @param authtoken the authtoken
     * @param _uid      the uid
     * @param options   the query params
     * @return the stacks
     */
    @GET("organizations/{organization_uid}/stacks")
    Call<ResponseBody> getStacks(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, String> options);


    /**
     * The Get organization log details request is used to retrieve the audit log details of an organization.
     * <br>
     * You can apply queries to filter the results. Refer to the Queries section for more details
     * The Get organization log item request is used to retrieve a specific item from the audit log of an organization.
     *
     * @param authtoken the authtoken
     * @param _uid      the uid
     * @param log_uid   the log uid
     * @return the logs
     */
    @GET("organizations/{organization_uid}/logs/{log_uid}")
    Call<ResponseBody> getLogs(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @Path("log_uid") String log_uid);


}
