package com.contentstack.cms.marketplace.installations;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * The interface Installation service.
 */
public interface InstallationService {

        @GET("installations/view/apps")
        Call<ResponseBody> listInstalledApps(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap Map<String, Object> queryParams);

        @GET("installations")
        Call<ResponseBody> listInstallations(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap Map<String, Object> queryParams);

        @GET("installations/{id}")
        Call<ResponseBody> getInstallations(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @QueryMap Map<String, Object> queryParams);

        @GET("installations/{id}/installationData")
        Call<ResponseBody> getInstallationData(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @QueryMap Map<String, Object> queryParams);

        @PUT("installations/{id}")
        Call<ResponseBody> updateInstallation(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @Body JSONObject requestBody,
                        @QueryMap Map<String, Object> queryParams);

        @GET("installations/view/users")
        Call<ResponseBody> listInstalledUsers(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap Map<String, Object> queryParams);

        @GET("installations/view/stacks")
        Call<ResponseBody> listInstalledStacks(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap Map<String, Object> queryParams);

        @DELETE("installations/{id}")
        Call<ResponseBody> uninstall(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id);

        @GET("installations/{id}/configuration")
        Call<ResponseBody> getAppConfiguration(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @QueryMap Map<String, Object> queryParams);

        @GET("installations/{id}/server-configuration")
        Call<ResponseBody> getServerConfiguration(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @QueryMap Map<String, Object> queryParams);

        @PUT("installations/{id}/server-configuration")
        Call<ResponseBody> updateServerConfiguration(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @Body JSONObject body,
                        @QueryMap Map<String, Object> queryParams);

        @PUT("installations/{id}/configuration")
        Call<ResponseBody> updateStackConfiguration(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @Body JSONObject body,
                        @QueryMap Map<String, Object> queryParams);

        @POST("installations/{id}/token")
        Call<ResponseBody> createInstallationToken(
                        @HeaderMap Map<String, String> headers,
                        @Path("id") String id,
                        @QueryMap Map<String, Object> queryParams);

}
