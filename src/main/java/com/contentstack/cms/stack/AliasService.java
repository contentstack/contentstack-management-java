package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AliasService {

    @GET("stacks/branch_aliases")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> query);

    @GET("stacks/branch_aliases/{branch_alias_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, Object> headers, @Path("branch_alias_uid") String uid);

    @PUT("stacks/branch_aliases")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers, @Body JSONObject body);

    @DELETE("stacks/branch_aliases/{branch_alias_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("branch_alias_uid") String uid, @QueryMap Map<String, Object> query);

}
