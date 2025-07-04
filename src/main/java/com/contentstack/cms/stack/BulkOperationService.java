package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.HashMap;
import java.util.Map;

public interface BulkOperationService {

    @POST("bulk/publish")
    Call<ResponseBody> publish(
            @HeaderMap Map<String, Object> headers,
            @QueryMap HashMap<String, Object> params,
            @Body JSONObject body);

    @POST("bulk/unpublish")
    Call<ResponseBody> unpublish(
            @HeaderMap Map<String, Object> headers,
            @QueryMap HashMap<String, Object> params,
            @Body JSONObject body);

    @POST("bulk/delete")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @QueryMap HashMap<String, Object> params,
            @Body JSONObject body);

    @POST("bulk/workflow")
    Call<ResponseBody> updateWorkflowDetails(
            @HeaderMap Map<String, Object> headers,
            @QueryMap HashMap<String, Object> params,
            @Body JSONObject body);

        @POST("bulk/release/items")
    Call<ResponseBody> addBulkItems(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body);

    @PUT("bulk/release/update_items")
    Call<ResponseBody> updateBulkItems(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body);

    @GET("bulk/jobs/{job_id}")
    Call<ResponseBody> getJobStatus(
            @HeaderMap Map<String, Object> headers,
            @Path("job_id") String jobUid,
            @QueryMap Map<String, Object> params);

}
