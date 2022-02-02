package com.contentstack.cms.organization;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface OrganizationService {

        @GET("organizations")
        Call<ResponseBody> getAll(
                        @QueryMap Map<String, Object> options);

        @GET("organizations/{organization_uid}")
        Call<ResponseBody> getSingle(
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @GET("organizations/{organization_uid}/roles")
        Call<ResponseBody> getRoles(
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @POST("organizations/{organization_uid}/share")
        Call<ResponseBody> inviteUser(
                        @Path("organization_uid") String uid,
                        @Body RequestBody body);

        // @DELETE("organizations/{organization_uid}/share")
        @HTTP(method = "DELETE", path = "organizations/{organization_uid}/share", hasBody = true)
        Call<ResponseBody> removeUser(
                        @Path("organization_uid") String uid,
                        @Body RequestBody body);

        @GET("organizations/{organization_uid}/share/{share_uid}/resend_invitation")
        Call<ResponseBody> resendInvitation(
                        @Path("organization_uid") String uid,
                        @Path("share_uid") String shareUid);

        @GET("organizations/{organization_uid}/share")
        Call<ResponseBody> getAllInvitations(
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @POST("organizations/{organization_uid}/transfer-ownership")
        Call<ResponseBody> transferOwnership(
                        @Path("organization_uid") String uid,
                        @Body RequestBody body);

        @GET("organizations/{organization_uid}/stacks")
        Call<ResponseBody> getStacks(
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @GET("organizations/{organization_uid}/logs")
        Call<ResponseBody> getLogDetails(
                        @Path("organization_uid") String uid);

        @GET("organizations/{organization_uid}/logs/{log_uid}")
        Call<ResponseBody> getLogItems(
                        @Path("organization_uid") String uid,
                        @Path("log_uid") String logUid);

}
