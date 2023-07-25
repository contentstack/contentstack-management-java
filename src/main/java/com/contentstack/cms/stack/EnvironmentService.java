package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface EnvironmentService {

        @GET("environments")
        Call<ResponseBody> fetch(
                        @HeaderMap Map<String, Object> headers,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @GET("environments/{environment_name}")
        Call<ResponseBody> getEnv(
                        @HeaderMap Map<String, Object> headers,
                        @Path("environment_name") String environment);

        @POST("environments")
        Call<ResponseBody> add(
                        @HeaderMap Map<String, Object> headers,
                        @Body JSONObject requestBody);

        @PUT("environments/{environment_name}")
        Call<ResponseBody> update(
                        @HeaderMap Map<String, Object> headers,
                        @Path("environment_name") String environment,
                        @Body JSONObject requestBody);

        @DELETE("environments/{environment_name}")
        Call<ResponseBody> delete(
                        @HeaderMap Map<String, Object> headers,
                        @Path("environment_name") String environment);
}
