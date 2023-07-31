package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ContentTypeService {

    @GET("content_types")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> query);

    @GET("content_types/{content_type_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @QueryMap Map<String, Object> queryParam);

    @POST("content_types")
    Call<ResponseBody> create(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> queryParam,
            @Body JSONObject body);

    @PUT("content_types/{content_type_uid}")
    Call<ResponseBody> update(
            @Path("content_type_uid") String contentTypeUid,
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> queryParam,
            @Body JSONObject body);

    @PUT("content_types/{content_type_uid}")
    Call<ResponseBody> visibilityRule(
            @Path("content_type_uid") String contentTypeUid,
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> queryParam,
            @Body JSONObject body);

    @DELETE("content_types/{content_type_uid}")
    Call<ResponseBody> delete(
            @Path("content_type_uid") String contentTypeUid,
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> param);

    @GET("content_types/{content_type_uid}/references")
    Call<ResponseBody> reference(
            @Path("content_type_uid") String contentTypeUid,
            @HeaderMap Map<String, Object> headers,
            @Query("include_global_fields") Boolean includeGlobalFields);

    @GET("content_types/{content_type_uid}/references?include_global_fields=true")
    Call<ResponseBody> referenceIncludeGlobalField(
            @Path("content_type_uid") String contentTypeUid,
            @HeaderMap Map<String, Object> headers);

    @GET("content_types/{content_type_uid}/export")
    Call<ResponseBody> export(
            @Path("content_type_uid") String contentTypeUid,
            @HeaderMap Map<String, Object> headers,
            @Query("version") int version);

    @GET("content_types/{content_type_uid}/export")
    Call<ResponseBody> export(
            @Path("content_type_uid") String contentTypeUid,
            @HeaderMap Map<String, Object> headers);

    @POST("content_types/import")
    Call<ResponseBody> imports(
            @HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> param);

    @POST("content_types/import?overwrite=true")
    Call<ResponseBody> importOverwrite(
            @HeaderMap Map<String, Object> headers);

}
