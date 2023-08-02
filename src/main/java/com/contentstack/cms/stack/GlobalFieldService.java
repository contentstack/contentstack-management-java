package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface GlobalFieldService {

    @GET("global_fields")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> param);

    @GET("global_fields/{global_field_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, Object> headers,
            @Path("global_field_uid") String globalFieldUid,
            @QueryMap Map<String, Object> param);

    @POST("global_fields")
    Call<ResponseBody> create(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("global_fields/{global_field_uid}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("global_field_uid") String globalFieldUid,
            @Body JSONObject body);

    @DELETE("global_fields/{global_field_uid}?force=true")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("global_field_uid") String globalFieldUid);

    @POST("global_fields/import")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Content-Type: multipart/form-data"})
    Call<ResponseBody> imports(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject jsonBody);

    @GET("global_fields/{global_field_uid}/export")
    Call<ResponseBody> export(
            @HeaderMap Map<String, Object> headers,
            @Path("global_field_uid") String globalFieldUid);
}
