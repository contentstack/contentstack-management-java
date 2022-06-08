package com.contentstack.cms.stack;

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

    @GET("webhooks/{execution_uid}")
    Call<ResponseBody> fetch(@HeaderMap Map<String, Object> headers,
                             @Path("execution_uid") String executionUid);

    @POST("webhooks/{execution_uid}/retry")
    Call<ResponseBody> create(@HeaderMap Map<String, Object> headers, @Body JSONObject requestBody);

    @PUT("webhooks/{webhook_uid}")
    Call<ResponseBody> update(@HeaderMap Map<String, Object> headers, @Path("execution_uid") String webhookUid,
                              @Body JSONObject requestBody);

    @DELETE("webhooks/{execution_uid}")
    Call<ResponseBody> delete(@HeaderMap Map<String, Object> headers, @Path("execution_uid") String webhookUid);

    @GET("webhooks/{webhook_uid}/export")
    Call<ResponseBody> export(@HeaderMap Map<String, Object> headers);

    @GET("webhooks/import")
    Call<ResponseBody> importExisting(@HeaderMap Map<String, Object> headers);

    @GET("webhooks/{webhook_uid}/executions")
    Call<ResponseBody> getExecutions(HashMap<String, Object> headers, String webhookUid);

    @POST("webhooks/{execution_uid}/retry")
    Call<ResponseBody> retry(HashMap<String, Object> headers, @Path("execution_uid") String executionUid);

    @GET("webhooks/{execution_uid}/logs")
    Call<ResponseBody> getExecutionLog(HashMap<String, Object> headers, @Path("execution_uid") String executionUid);
}
