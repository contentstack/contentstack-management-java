package com.contentstack.cms.stack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface StackService {

    @GET("stacks")
    Call<ResponseBody> fetch(
            @Header("api_key") String apiKey);

    @GET("stacks")
    Call<ResponseBody> fetch(
            @Header("api_key") String apiKey,
            @Header("organization_uid") String organizationUid);

    @GET("stacks")
    Call<ResponseBody> fetch(
            @Header("api_key") String apiKey,
            @QueryMap Map<String, Boolean> queryParams);

    @GET("stacks")
    Call<ResponseBody> fetch(
            @Header("api_key") String apiKey,
            @Header("organization_uid") String organizationUid,
            @QueryMap Map<String, Boolean> queryParams);

    @POST("stacks")
    Call<ResponseBody> create(
            @Header("organization_uid") String organizationUid,
            @Body RequestBody body);

    @PUT("stacks")
    Call<ResponseBody> update(
            @Header("api_key") String apiKey,
            @Body RequestBody body);

    @POST("stacks/transfer_ownership")
    Call<ResponseBody> transferOwnership(
            @Header("api_key") String apiKey,
            @Body RequestBody body);

    @GET("stacks/accept_ownership/{ownership_token}")
    Call<ResponseBody> acceptOwnership(
            @Path("ownership_token") String ownershipToken,
            @QueryMap Map<String, String> map
    );

    @GET("stacks/settings")
    Call<ResponseBody> setting(
            @Header("api_key") String apiKey);

    @POST("stacks/settings")
    Call<ResponseBody> updateSetting(
            @Header("api_key") String apiKey,
            @Body RequestBody body);

    @POST("stacks/share")
    Call<ResponseBody> share(
            @Header("api_key") String apiKey,
            @Body RequestBody body);

    @POST("stacks/unshare")
    Call<ResponseBody> unshare(
            @Header("api_key") String apiKey,
            @Body RequestBody body);

    @GET("stacks?include_collaborators=true")
    Call<ResponseBody> allUsers(
            @Header("api_key") String apiKey);

    @POST("stacks/users/roles")
    Call<ResponseBody> updateUserRoles(
            @Header("api_key") String apiKey,
            @Body RequestBody body);
}
