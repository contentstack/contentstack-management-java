package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface CompareService {

    @GET("stacks/branches_compare")
    Call<ResponseBody> all(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> query);

    @GET("stacks/branches_compare/content_types")
    Call<ResponseBody> compareContentTypesBetweenBranches(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> queryParam);

    @GET("stacks/branches_compare/global_fields")
    Call<ResponseBody> compareGlobalFieldBetweenBranches(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> queryParam);

    @GET("stacks/branches_compare/content_types/{content_type_uid}")
    Call<ResponseBody> compareSpecificContentTypeBetweenBranches(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String fieldId,
            @QueryMap Map<String, Object> queryParam);

    @GET("stacks/branches_compare/global_fields/{global_field_uid}")
    Call<ResponseBody> compareSpecificGlobalFieldBetweenBranches(
            @HeaderMap Map<String, String> headers,
            @Path("global_field_uid") String fieldId,
            @QueryMap Map<String, Object> queryParam);
}
