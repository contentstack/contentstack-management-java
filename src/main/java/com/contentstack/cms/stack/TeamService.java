package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Allows all the methods related to Team API members
 */
public interface TeamService {

    @GET("teams")
    Call<ResponseBody> find(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> params);

    @POST("teams")
    Call<ResponseBody> create(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> params);

    @GET("teams/{teamId}")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> params);

    @PUT("teams/{teamId}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("teamId") String uid,
            @Body JSONObject body);

}
