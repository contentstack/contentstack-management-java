package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface BranchService {

    @GET("stacks/branches")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> query);

    @GET("stacks/branches/{branch_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, Object> headers, @Path("branch_uid") String uid);

    @POST("stacks/branches")
    Call<ResponseBody> create(
            @HeaderMap Map<String, Object> headers, @Body JSONObject body);

    @DELETE("stacks/branches/{branch_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("branch_uid") String uid, @QueryMap Map<String, Object> query);

}
