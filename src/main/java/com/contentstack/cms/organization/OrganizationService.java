package com.contentstack.cms.organization;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface OrganizationService {

        @GET("organizations")
        Call<ResponseBody> fetch(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap Map<String, Object> query);

        @GET("organizations/{organization_uid}")
        Call<ResponseBody> getSingle(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @GET("organizations/{organization_uid}/roles")
        Call<ResponseBody> getRoles(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @POST("organizations/{organization_uid}/share")
        Call<ResponseBody> inviteUser(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @Body JSONObject body);

        @HTTP(method = "DELETE", path = "organizations/{organization_uid}/share", hasBody = true)
        Call<ResponseBody> removeUser(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @Body JSONObject body);

        @GET("organizations/{organization_uid}/share/{share_uid}/resend_invitation")
        Call<ResponseBody> resendInvitation(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @Path("share_uid") String shareUid);

        @GET("organizations/{organization_uid}/share")
        Call<ResponseBody> getAllInvitations(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @POST("organizations/{organization_uid}/transfer-ownership")
        Call<ResponseBody> transferOwnership(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @Body JSONObject body);

        @GET("organizations/{organization_uid}/stacks")
        Call<ResponseBody> getStacks(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @QueryMap Map<String, Object> query);

        @GET("organizations/{organization_uid}/logs")
        Call<ResponseBody> getLogDetails(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid);

        @GET("organizations/{organization_uid}/logs/{log_uid}")
        Call<ResponseBody> getLogItems(
                        @HeaderMap Map<String, String> headers,
                        @Path("organization_uid") String uid,
                        @Path("log_uid") String logUid);

}
