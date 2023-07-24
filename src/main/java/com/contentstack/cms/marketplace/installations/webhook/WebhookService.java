package com.contentstack.cms.marketplace.installations.webhook;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface WebhookService {

        @GET("installations/{installationId}/webhooks/{webhookId}/executions")
        Call<ResponseBody> findExecutionLogs(
                        @HeaderMap Map<String, String> headers,
                        @Path("installationId") String installationId,
                        @Path("webhookId") String webhookId,
                        @QueryMap Map<String, Object> queryParams);

        @GET("/installations/{installationId}/webhooks/{webhookId}/executions/{executionId}")
        Call<ResponseBody> fetchExecutionLog(
                        @HeaderMap Map<String, String> headers,
                        @Path("installationId") String installationId,
                        @Path("webhookId") String webhookId,
                        @Path("executionId") String executionId,
                        @QueryMap Map<String, Object> queryParams);

        @POST("/installations/{installationId}/webhooks/{webhookId}/executions/{executionId}/retry")
        Call<ResponseBody> retryExecution(
                        @HeaderMap Map<String, String> headers,
                        @Path("installationId") String installationId,
                        @Path("webhookId") String webhookId,
                        @Path("executionId") String executionId,
                        @QueryMap Map<String, Object> queryParams);
}
