package com.contentstack.cms.marketplace.request;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface RequestService {

        @POST("requests")
        Call<ResponseBody> create(
                        @HeaderMap Map<String, String> headers,
                        @Body JSONObject body);

        @GET("requests")
        Call<ResponseBody> listRequests(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap Map<String, Object> parameters);

        @GET("requests/view/stacks")
        Call<ResponseBody> listRequestedStacks(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap Map<String, Object> parameters);

        @DELETE("requests/{uid}")
        Call<ResponseBody> deleteRequest(
                        @HeaderMap Map<String, String> headers,
                        @Path("uid") String uid);
}
