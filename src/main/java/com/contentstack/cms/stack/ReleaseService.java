package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ReleaseService {

    @GET("releases")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> params);

    @GET("releases/{release_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid);

    @POST("releases")
    Call<ResponseBody> create(@HeaderMap Map<String, Object> headers,
                              @Body JSONObject body);

    @PUT("releases/{release_uid}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @Body JSONObject body);

    @DELETE("releases/{release_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid);

    @GET("releases/{release_uid}/items")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @QueryMap Map<String, Object> params);

    @POST("releases/{release_uid}/items")
    Call<ResponseBody> addItems(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body);

    @POST("releases/{release_uid}/item")
    Call<ResponseBody> addItem(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body);

    @PUT("releases/{release_uid}/update_items")
    Call<ResponseBody> updateItems(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body);

    @DELETE("releases/{release_uid}/items")
    Call<ResponseBody> removeItem(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid);

    @POST("releases/{release_uid}/deploy")
    Call<ResponseBody> deploy(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @Body JSONObject body);

    @POST("releases/{release_uid}/clone")
    Call<ResponseBody> clone(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body);

    @POST("/releases/{source_releaseUid}/items/move")
    Call<ResponseBody> moveItems(
            @HeaderMap Map<String, Object> headers,
            @Path("source_releaseUid") String releaseUid,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body); //required

    @DELETE("/releases/{release_uid}/items")
    Call<ResponseBody> deleteItems(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body); //required

    @DELETE("/releases/{release_uid}/items")
    Call<ResponseBody> deleteItem(
            @HeaderMap Map<String, Object> headers,
            @Path("release_uid") String releaseUid,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body); //required
}
