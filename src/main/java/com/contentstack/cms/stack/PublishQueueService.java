package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.HashMap;
import java.util.Map;

public interface PublishQueueService {

    @GET("publish-queue")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @QueryMap HashMap<String, Object> params);

    @GET("publish-queue/{publish_queue_uid}")
    Call<ResponseBody> fetchActivity(
            @HeaderMap Map<String, Object> headers,
            @Path("publish_queue_uid") String publishQueueUid,
            @QueryMap HashMap<String, Object> params);

    @GET("publish-queue/{publish_queue_uid}/unschedule")
    Call<ResponseBody> cancelScheduledAction(
            @HeaderMap Map<String, Object> headers,
            @Path("publish_queue_uid") String publishQueueUid,
            @QueryMap HashMap<String, Object> params);
}
