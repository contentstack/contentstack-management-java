package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface StackService {

        @GET("stacks")
        Call<ResponseBody> fetch(
                @HeaderMap Map<String, Object> headers);

        @GET("stacks")
        Call<ResponseBody> fetch(
                @HeaderMap Map<String, Object> headers,
                @Header("organization_uid") String organizationUid);

        @GET("stacks")
        Call<ResponseBody> fetch(
                @Header("api_key") String apiKey,
                @QueryMap Map<String, Boolean> queryParams);

        @GET("stacks")
        Call<ResponseBody> fetch(
                @HeaderMap Map<String, Object> headers,
                @Header("organization_uid") String organizationUid,
                @QueryMap Map<String, Boolean> queryParams);

        @POST("stacks")
        Call<ResponseBody> create(
                @Header("organization_uid") String orgUid,
                @Body JSONObject body);

        @PUT("stacks")
        Call<ResponseBody> update(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);

        @POST("stacks/transfer_ownership")
        Call<ResponseBody> transferOwnership(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);

        @GET("stacks/accept_ownership/{ownership_token}")
        Call<ResponseBody> acceptOwnership(
                @Path("ownership_token") String ownershipToken,
                @QueryMap Map<String, String> query);

        @GET("stacks/settings")
        Call<ResponseBody> setting(
                @HeaderMap Map<String, Object> headers);

        @POST("stacks/settings")
        Call<ResponseBody> updateSetting(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);

        @POST("stacks/share")
        Call<ResponseBody> share(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);

        @POST("stacks/unshare")
        Call<ResponseBody> unshare(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);

        @GET("stacks?include_collaborators=true")
        Call<ResponseBody> allUsers(
                @HeaderMap Map<String, Object> headers);

        @POST("stacks/users/roles")
        Call<ResponseBody> updateUserRoles(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);
}
