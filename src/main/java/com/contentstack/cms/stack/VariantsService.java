package com.contentstack.cms.stack;

import java.util.Map;

import org.json.simple.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Service interface for variant group related API endpoints.
 */
public interface VariantsService {

    @GET("variant_groups")
    Call<ResponseBody> fetchVariantGroups(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> queryParam);

    @PUT("variant_groups/{variant_group_uid}/variants")
    Call<ResponseBody> updateVariantGroupContentTypes(
            @HeaderMap Map<String, Object> headers,
            @Path("variant_group_uid") String variantGroupUid,
            @QueryMap Map<String, Object> queryParam,
            @Body JSONObject body);
}