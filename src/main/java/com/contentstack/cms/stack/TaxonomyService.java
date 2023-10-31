package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

public interface TaxonomyService {

    @GET("taxonomies")
    Call<ResponseBody> find(@HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> params);

    @GET("taxonomies/{taxonomy_uid}")
    Call<ResponseBody> fetch(@HeaderMap Map<String, Object> headers, @Path("taxonomy_uid") String uid, @QueryMap Map<String, Object> query);

    @POST("taxonomies")
    Call<ResponseBody> create(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("taxonomies/{taxonomy_uid}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("taxonomy_uid") String uid,
            @Body JSONObject body);

    @DELETE("taxonomies/{taxonomy_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("taxonomy_uid") String uid);

    //--Terms--
    @POST("taxonomies/{taxonomy_uid}/terms")
    Call<ResponseBody> createTerm(
            @HeaderMap HashMap<String, Object> headers,
            @Path("taxonomy_uid") String taxonomyId,
            @Body JSONObject body);

    @GET("taxonomies/{taxonomy_uid}/terms")
    Call<ResponseBody> findTerm(
            @HeaderMap HashMap<String, Object> headers,
            @Path("taxonomy_uid") String taxonomyId,
            @QueryMap Map<String, Object> queryParams);

    @GET("taxonomies/{taxonomy_uid}/terms/{term_id}")
    Call<ResponseBody> fetchTerm(
            @HeaderMap HashMap<String, Object> headers,
            @Path("taxonomy_uid") String taxonomyId,
            @Path("term_id") String termId,
            @QueryMap Map<String, Object> queryParams);

    @GET("taxonomies/{taxonomy_uid}/terms/{term_id}/descendants")
    Call<ResponseBody> descendantsTerm(
            @HeaderMap HashMap<String, Object> headers,
            @Path("taxonomy_uid") String taxonomyId,
            @Path("term_id") String termId,
            @QueryMap Map<String, Object> queryParams);

    @GET("taxonomies/{taxonomy_uid}/terms/{term_id}/ancestors")
    Call<ResponseBody> ancestorsTerm(
            @HeaderMap HashMap<String, Object> headers,
            @Path("taxonomy_uid") String taxonomyId,
            @Path("term_id") String termId,
            @QueryMap Map<String, Object> queryParams);

    @PUT("taxonomies/{taxonomy_uid}/terms/{term_id}")
    Call<ResponseBody> updateTerm(
            @HeaderMap HashMap<String, Object> headers,
            @Path("taxonomy_uid") String taxonomyId,
            @Path("term_id") String termId,
            @Body JSONObject body);


    @GET("taxonomies/all/terms")
    Call<ResponseBody> searchTerm(
            @HeaderMap HashMap<String, Object> headers,
            @Query("term") String termString
    );
}
