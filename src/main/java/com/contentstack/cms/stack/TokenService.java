package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface TokenService {

    // Delivery Tokens
    @GET("delivery_tokens")
    Call<ResponseBody> getDeliveryToken(
            @HeaderMap Map<String, Object> headers);

    @GET("delivery_tokens/{token_uid}")
    Call<ResponseBody> getDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid);

    @POST("delivery_tokens")
    Call<ResponseBody> createDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("delivery_tokens/{token_uid}")
    Call<ResponseBody> updateDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid,
            @Body JSONObject body);

    @DELETE("delivery_tokens/{token_uid}")
    Call<ResponseBody> deleteDeliveryToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid,
            @Query("force") Boolean force);


    // Management Tokens
    @GET("management_tokens")
    Call<ResponseBody> fetchManagementToken(
            @HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> params);

    @GET("management_tokens/{token_uid}")
    Call<ResponseBody> getSingleManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid);

    @POST("management_tokens")
    Call<ResponseBody> createManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("management_tokens/{token_uid}")
    Call<ResponseBody> updateManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid, @Body JSONObject body);

    @DELETE("management_tokens/{token_uid}")
    Call<ResponseBody> deleteManagementToken(
            @HeaderMap Map<String, Object> headers,
            @Path("token_uid") String tokenUid);

}
