package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface LabelService {

    @GET("labels")
    Call<ResponseBody> get(
            @HeaderMap Map<String, Object> headers);

    @GET("labels")
    Call<ResponseBody> get(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @GET("labels/{label_uid}")
    Call<ResponseBody> get(
            @HeaderMap Map<String, Object> headers,
            @Path("label_uid") String labelUid);

    @GET("labels/{label_uid}")
    Call<ResponseBody> get(
            @HeaderMap Map<String, Object> headers,
            @Path("label_uid") String labelUid,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @POST("labels")
    Call<ResponseBody> add(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("labels/{label_uid}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("label_uid") String labelUid,
            @Body JSONObject body);

    @DELETE("labels/{label_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("label_uid") String labelUid);
}
