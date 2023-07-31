package com.contentstack.cms.stack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ExtensionsService {

    @GET("extensions")
    Call<ResponseBody> getAll(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query);

    @GET("extensions/{custom_field_uid}")
    Call<ResponseBody> getSingle(
            @HeaderMap Map<String, Object> headers,
            @Path("custom_field_uid") String customFieldUid,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @Multipart
    @POST("extensions")
    Call<ResponseBody> uploadCustomField(
            @HeaderMap Map<String, Object> headers,
            @PartMap Map<String, RequestBody> params,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @POST("extensions")
    Call<ResponseBody> uploadCustomField(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> queryParameter,
            @Body JSONObject body);

    @PUT("extensions/{custom_field_uid}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("custom_field_uid") String customFieldUid,
            @QueryMap(encoded = true) Map<String, Object> queryParameter,
            @Body JSONObject body);

    @DELETE("extensions/{custom_field_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("custom_field_uid") String customFieldUid);

    @POST("content_types")
    Call<ResponseBody> createContentTypeWithExtensionField(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> queryParameter,
            @Body JSONObject body);

}
