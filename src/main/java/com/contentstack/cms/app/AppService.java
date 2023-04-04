package com.contentstack.cms.app;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AppService {

    @POST("manifest")
    Call<ResponseBody> create(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> query,
            @Body JSONObject body
    );

    @GET("manifest/{uid}")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @QueryMap Map<String, Object> query
    );

    @GET("manifests")
    Call<ResponseBody> find(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> query
    );

    @PUT("manifests/{uid}")
    Call<ResponseBody> updateById(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body
    );

    @DELETE("manifests/{uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid
    );

    @GET("manifests/{uid}/oauth")
    Call<ResponseBody> getAuthConfiguration(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid
    );

    @PUT("manifests/{uid}/oauth")
    Call<ResponseBody> updateAuthConfiguration(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body
    );

    @GET("manifests/{uid}/hosting")
    Call<ResponseBody> getHosting(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid
    );

}
