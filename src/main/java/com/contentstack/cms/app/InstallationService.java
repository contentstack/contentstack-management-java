package com.contentstack.cms.app;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface InstallationService {

    @POST("manifests/{uid}/install")
    Call<ResponseBody> create(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body
    );

    @PUT("manifests/{uid}/reinstall")
    Call<ResponseBody> update(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String manifestId,
            @Body JSONObject body
    );

    @GET("manifests/{uid}/installations")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid
    );

    @GET("installations/{uid}")
    Call<ResponseBody> find(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @QueryMap Map<String, Object> query
    );

    @PUT("installations/{uid}")
    Call<ResponseBody> updateById(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body
    );

    @DELETE("installations/{uid}")
    Call<ResponseBody> uninstall(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid
    );

    @POST("token")
    Call<ResponseBody> createToken(
            @Body JSONObject body
    );

    @GET("installations/{uid}/locations/configuration")
    Call<ResponseBody> getWebhook(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid
    );

    @GET("installations/{installationId}/webhooks/{webhookId}/executions")
    Call<ResponseBody> findExecutions(
            @HeaderMap Map<String, String> headers,
            @Path("installationId") String installationId,
            @Path("webhookId") String webhookId
    );

    @GET("/installations/{installationId}/webhooks/{webhookId}/executions/{executionId}")
    Call<ResponseBody> fetchExecution(
            @HeaderMap Map<String, String> headers,
            @Path("installationId") String installationId,
            @Path("webhookId") String webhookId,
            @Path("executionId") String executionId
    );
}
