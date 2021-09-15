package com.contentstack.cms.organization;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

public interface OrganizationService {


    @GET("organizations")
    Call<ResponseBody> get(
            @Header("authtoken") String authtoken
    );

    @GET("organizations")
    Call<ResponseBody> get(@Header("authtoken") String authtoken,
                           @QueryMap Map<String, Object> options);


    @GET("organizations/{organization_uid}")
    Call<ResponseBody> singleOrganization(
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, String> options);


    @GET("organizations/{organization_uid}/roles")
    Call<ResponseBody> getRoles(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @QueryMap HashMap<String, Object> options);

    @POST("organizations/{organization_uid}/share")
    Call<ResponseBody> inviteUser(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid);


    @DELETE("organizations/{organization_uid}/share")
    Call<ResponseBody> removeUser(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid);

    @GET("organizations/{organization_uid}/share/{share_uid}")
    Call<ResponseBody> resendInvitation(
            @Path("organization_uid") String _uid,
            @Path("share_uid") String share_uid);

    @GET("organizations/{organization_uid}/share")
    Call<ResponseBody> getAllInvitations(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("organizations/{organization_uid}/ownership")
    Call<ResponseBody> transferOwnership(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @Body String transfer_to);

    @GET("organizations/{organization_uid}/stacks")
    Call<ResponseBody> getStacks(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @QueryMap Map<String, String> options);


    @GET("organizations/{organization_uid}/logs/{log_uid}")
    Call<ResponseBody> getLogs(
            @Header("authtoken") String authtoken,
            @Path("organization_uid") String _uid,
            @Path("log_uid") String log_uid);


}
