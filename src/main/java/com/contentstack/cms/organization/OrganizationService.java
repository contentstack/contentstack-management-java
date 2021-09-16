package com.contentstack.cms.organization;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

public interface OrganizationService {

    //@Header("authtoken") String authtoken

    @GET("organizations")
    Call<ResponseBody> get();

    @GET("organizations")
    Call<ResponseBody> get(
                           @QueryMap Map<String, Object> options);

    @GET("organizations/{organization_uid}")
    Call<ResponseBody> singleOrganization(
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, Object> options);


    @GET("organizations/{organization_uid}/roles")
    Call<ResponseBody> getRoles(
            @Path("organization_uid") String _uid,
            @QueryMap HashMap<String, Object> options);

    @POST("organizations/{organization_uid}/share")
    Call<ResponseBody> inviteUser(

            @Path("organization_uid") String _uid);


    @DELETE("organizations/{organization_uid}/share")
    Call<ResponseBody> removeUser(

            @Path("organization_uid") String _uid);

    @GET("organizations/{organization_uid}/share/{share_uid}/resend_invitation")
    Call<ResponseBody> resendInvitation(
            @Path("organization_uid") String _uid,
            @Path("share_uid") String share_uid);

    @GET("organizations/{organization_uid}/share")
    Call<ResponseBody> getAllInvitations(

            @Path("organization_uid") String _uid,
            @QueryMap Map<String, Object> options);

    @FormUrlEncoded
    @POST("organizations/{organization_uid}/transfer-ownership")
    Call<ResponseBody> transferOwnership(

            @Path("organization_uid") String _uid,
            @Field("transfer_to") String transfer_to);

    @GET("organizations/{organization_uid}/stacks")
    Call<ResponseBody> getStacks(

            @Path("organization_uid") String _uid,
            @QueryMap Map<String, Object> options);


    @GET("organizations/{organization_uid}/logs")
    Call<ResponseBody> getLogDetails(

            @Path("organization_uid") String _uid);

    @GET("organizations/{organization_uid}/logs/{log_uid}")
    Call<ResponseBody> getLogItems(

            @Path("organization_uid") String _uid,
            @Path("log_uid") String log_uid);


}
