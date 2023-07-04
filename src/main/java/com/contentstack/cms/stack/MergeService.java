package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface MergeService {


    @POST("stacks/branches_merge")
    Call<ResponseBody> mergeBranches(
            @HeaderMap Map<String, String> headers,
            @Body JSONObject requestBody,
            @QueryMap Map<String, Object> params);

    @GET("stacks/branches_queue")
    Call<ResponseBody> find(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> params);

    @GET("stacks/branches_queue/{merge_job_uid}")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, String> headers,
            @Path("merge_job_uid") String jobId,
            @QueryMap Map<String, Object> params);

}
