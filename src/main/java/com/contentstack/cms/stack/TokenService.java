package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface TokenService {

    // Delivery Tokens
    @GET("stacks/delivery_tokens")
    Call<ResponseBody> getDeliveryToken(
            @HeaderMap Map<String, Object> headers);

    @GET("stacks/delivery_tokens/{token_uid}")
    Call<ResponseBody> getDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid);

    @POST("stacks/delivery_tokens")
    Call<ResponseBody> createDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("stacks/delivery_tokens/{token_uid}")
    Call<ResponseBody> updateDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid,
            @Body JSONObject body);

    @DELETE("stacks/delivery_tokens/{token_uid}")
    Call<ResponseBody> deleteDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid,
            @Query("force") Boolean force);

    // Management Tokens
    @GET("stacks/management_tokens")
    Call<ResponseBody> fetchManagementToken(
            @HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> params);

    @GET("stacks/management_tokens/{token_uid}")
    Call<ResponseBody> getSingleManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid);

    @POST("stacks/management_tokens")
    Call<ResponseBody> createManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("stacks/management_tokens/{token_uid}")
    Call<ResponseBody> updateManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid, @Body JSONObject body);

    @DELETE("stacks/management_tokens/{token_uid}")
    Call<ResponseBody> deleteManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid);

}
