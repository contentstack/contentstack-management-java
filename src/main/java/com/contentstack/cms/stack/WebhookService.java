package com.contentstack.cms.stack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

public interface WebhookService {

    @GET("webhooks")
    Call<ResponseBody> fetch(@HeaderMap Map<String, Object> headers,
                             @QueryMap HashMap<String, Object> params);

    @GET("webhooks/{webhook_uid}")
    Call<ResponseBody> single(@HeaderMap Map<String, Object> headers, @Path("webhook_uid") String executionUid);

    @POST("webhooks")
    Call<ResponseBody> create(@HeaderMap Map<String, Object> headers, @Body JSONObject requestBody);

    @PUT("webhooks/{webhook_uid}")
    Call<ResponseBody> update(@HeaderMap Map<String, Object> headers, @Path("webhook_uid") String webhookUid, @Body JSONObject requestBody);

    @DELETE("webhooks/{webhook_uid}")
    Call<ResponseBody> delete(@HeaderMap Map<String, Object> headers, @Path("webhook_uid") String webhookUid);

    @GET("webhooks/{webhook_uid}/export")
    Call<ResponseBody> export(@HeaderMap Map<String, Object> headers, @Path("webhook_uid") String webhookUid);

    @Multipart()
    @POST("webhooks/import")
    Call<ResponseBody> imports(@HeaderMap Map<String, Object> header, @Body RequestBody file);

    @POST("webhooks/import")
    Call<ResponseBody> importExisting(@HeaderMap Map<String, Object> headers);

    @GET("webhooks/{webhook_uid}/executions")
    Call<ResponseBody> getExecutions(
            @HeaderMap Map<String, Object> headers,
            @Path("webhook_uid") String executionUid,
            @QueryMap Map<String, Object> params);

    @POST("webhooks/{webhook_uid}/retry")
    Call<ResponseBody> retry(@HeaderMap Map<String, Object> headers, @Path("webhook_uid") String executionUid);

    @GET("webhooks/{execution_uid}/logs")
    Call<ResponseBody> getExecutionLog(@HeaderMap Map<String, Object> headers, @Path("execution_uid") String executionUid);
}
