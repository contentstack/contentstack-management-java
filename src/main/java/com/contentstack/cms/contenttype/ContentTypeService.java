package com.contentstack.cms.contenttype;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ContentTypeService {

    @GET("content_types")
    Call<ResponseBody> fetch(
            @Header("api_key") String apiKey,
            @Header("authorization") String auth,
            @QueryMap Map<String, Boolean> query);

    @GET("content_types/{content_type_uid}")
    Call<ResponseBody> single(
            @Header("api_key") String apiKey,
            @Path("content_type_uid") String contentTypeUid,
            @Header("authorization") String authorization,
            Map<String, Boolean> queryParam);

    @POST("content_types")
    Call<ResponseBody> create(
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization,
            @Body RequestBody body);

    @PUT("content_types/{content_type_uid}")
    Call<ResponseBody> update(
            @Path("content_type_uid") String contentTypeUid,
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization,
            @Body RequestBody body);

    @PUT("content_types/{content_type_uid}")
    Call<ResponseBody> visibilityRule(
            @Path("content_type_uid") String contentTypeUid,
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization,
            @Body RequestBody body);

    @DELETE("content_types/{content_type_uid}")
    Call<ResponseBody> delete(
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization);

    @DELETE("content_types/{content_type_uid}")
    Call<ResponseBody> delete(
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization,
            @Query("force") Boolean force);

    @GET("content_types/{content_type_uid}/references")
    Call<ResponseBody> reference(
            @Path("content_type_uid") String contentTypeUid,
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization);

    @GET("content_types/{content_type_uid}/references")
    Call<ResponseBody> reference(
            @Path("content_type_uid") String contentTypeUid,
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization,
            @Query("include_global_fields") Boolean isIncludeGlobalFields);

    @GET("content_types/{content_type_uid}/export")
    Call<ResponseBody> export(
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization,
            @Query("version") int version);

    @GET("content_types/{content_type_uid}/export")
    Call<ResponseBody> export(
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization);

    @POST("content_types/{content_type_uid}/import")
    Call<ResponseBody> imports(
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization);

    @POST("content_types/{content_type_uid}/import")
    Call<ResponseBody> imports(
            @Header("api_key") String apiKey,
            @Header("authorization") String authorization,
            @Query("overwrite") boolean overwrite);

}
