package com.contentstack.cms.stack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface StackService {

    @GET("stacks")
    Call<ResponseBody> singleStack(
            @Header("api_key") String apiKey);

    @GET("stacks")
    Call<ResponseBody> singleStack(
            @Header("api_key") String apiKey,
            @Header("organization_uid") String organizationUid);

    @GET("stacks")
    Call<ResponseBody> singleStack(
            @Header("api_key") String apiKey,
            @Header("organization_uid") String organizationUid,
            @QueryMap Map<String, Boolean> queryParams);

    @POST("stacks")
    Call<ResponseBody> createStack(
            @Header("organization_uid") String organizationUid,
            @Body RequestBody body);

    @PUT("stacks")
    Call<ResponseBody> updateStack(
            @Header("api_key") String apiKey,
            @Body RequestBody body);

    @POST("stacks/transfer_ownership")
    Call<ResponseBody> transferOwnership(
            @Header("api_key") String apiKey,
            @Body RequestBody body);
}
