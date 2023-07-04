package com.contentstack.cms.marketplace.apps.hosting;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface HostingService {

    @PATCH("manifests/{appId}/hosting/disable")
    Call<ResponseBody> toggleDisableHosting(@HeaderMap Map<String, String> headers, @Path("appId") String appId);

    @PATCH("manifests/{appId}/hosting/enable")
    Call<ResponseBody> toggleEnableHosting(@HeaderMap Map<String, String> headers, @Path("appId") String appId);


    @POST(" /manifests/{uid}/hosting/signedUploadUrl")
    Call<ResponseBody> signedUploadUrl(@HeaderMap Map<String, String> headers, @Path("uid") String uid);

    @POST("/{url}")
    Call<ResponseBody> uploadFile(@Path(value = "url", encoded = true) String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> param);

    @POST("manifests/{appId}/hosting/deployments")
    Call<ResponseBody> createDeployments(@HeaderMap Map<String, String> headers, @Path("appId") String appId, @Body JSONObject body, @QueryMap Map<String, Object> queryParams);

    @POST("manifests/{appId}/hosting/signedDownloadUrl")
    Call<ResponseBody> createSignedDownloadUrl(@HeaderMap Map<String, String> headers, @Path("appId") String appId, @QueryMap Map<String, Object> params);

    @GET("manifests/{uid}/hosting")
    Call<ResponseBody> getHosting(@HeaderMap Map<String, String> headers, @Path("uid") String uid, @QueryMap Map<String, Object> params);

    @GET("manifests/{appId}/hosting/deployments")
    Call<ResponseBody> findDeployments(@HeaderMap Map<String, String> headers, @Path("appId") String appId, @QueryMap Map<String, Object> params);

    @GET("manifests/{appId}/hosting/deployments/{deploymentId}")
    Call<ResponseBody> fetchDeployment(@HeaderMap Map<String, String> headers, @Path("appId") String appId, @Path("deploymentId") String deploymentId, @QueryMap Map<String, Object> queryParams);

    @GET("manifests/{appId}/hosting/latestLiveDeployment")
    Call<ResponseBody> fetchLatestLiveDeployment(@HeaderMap Map<String, String> headers, @Path("appId") String appId, @QueryMap Map<String, Object> queryParams);

    @GET("manifests/{appId}/hosting/deployments/{deploymentId}/logs")
    Call<ResponseBody> findDeploymentLogs(@HeaderMap Map<String, String> headers, @Path("appId") String appId, @Path("deploymentId") String deploymentId, @QueryMap Map<String, Object> queryParams);

    @GET("{url}")
    Call<ResponseBody> downloadFile(@Path(value = "url", encoded = true) String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> queryParams);


}
