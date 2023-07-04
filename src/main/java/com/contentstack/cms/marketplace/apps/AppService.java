package com.contentstack.cms.marketplace.apps;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AppService {


    @POST("manifests/{uid}/install")
    Call<ResponseBody> createInstallation(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body,
            @QueryMap Map<String, Object> param
            );

    @PUT("manifests/{id}/reinstall")
    Call<ResponseBody> updateVersion(
            @HeaderMap Map<String, String> headers,
            @Path("id") String id,
            @Body JSONObject body,
            @QueryMap Map<String, Object> queryParams);


    @GET("manifests/{uid}/authorizations")
    Call<ResponseBody> findAppAuthorizations(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid, @QueryMap Map<String, Object> queryParams);

    @DELETE("manifests/{uid}/authorizations/{uid}")
    Call<ResponseBody> deleteAuthorization(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Path("uid") String orgId);


    @GET("manifests/{uid}/installations")
    Call<ResponseBody> listAppInstallations(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @QueryMap Map<String, Object> queryParameters);


    @GET("manifests")
    Call<ResponseBody> listApps(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> queryParameters);

    @POST("manifests")
    Call<ResponseBody> createApp(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> queryParameters,
            @Body JSONObject body);

    @GET("manifests/{uid}")
    Call<ResponseBody> fetchApp(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @QueryMap Map<String, Object> queryParameters);


    @PUT("manifests/{uid}")
    Call<ResponseBody> updateApp(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body);

    @DELETE("manifests/{uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid);


    @GET("manifests/{uid}/requests")
    Call<ResponseBody> listAppRequests(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid, @QueryMap Map<String, Object> params);

}
