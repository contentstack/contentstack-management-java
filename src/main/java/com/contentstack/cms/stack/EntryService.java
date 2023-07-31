package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface EntryService {

    @GET("content_types/{content_type_uid}/entries")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @Headers("Content-Type: application/json")
    @GET("content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @Headers("Content-Type: application/json")
    @POST("content_types/{content_type_uid}/entries")
    Call<ResponseBody> create(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Body JSONObject requestBody,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @PUT("content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject requestBody,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    // Push, Pull, Add, Sub Operations
    @PUT("content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> atomicOperations(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject body);

    @HTTP(method = "DELETE", path = "content_types/{content_type_uid}/entries/{entry_uid}", hasBody = true)
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject requestBody,
            @QueryMap(encoded = true) Map<String, Object> queryParameter);

    @POST("content_types/{content_type_uid}/entries/{entry_uid}/versions/{version_number}/name")
    Call<ResponseBody> versionName(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Path("version_number") String version,
            @Body JSONObject body);

    @GET("content_types/{content_type_uid}/entries/{entry_uid}/versions")
    Call<ResponseBody> detailOfAllVersion(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap(encoded = true) Map<String, Object> queryOptions);

    @HTTP(method = "DELETE", path = "content_types/{content_type_uid}/entries/{entry_uid}/versions/{version_number}/name", hasBody = true)
    Call<ResponseBody> deleteVersionName(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Path("version_number") int version,
            @Body JSONObject body);

    @GET("content_types/{content_type_uid}/entries/{entry_uid}/references")
    Call<ResponseBody> reference(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap(encoded = true) Map<String, Object> queryOptions);

    @GET("content_types/{content_type_uid}/entries/{entry_uid}/locales")
    Call<ResponseBody> language(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap(encoded = true) Map<String, Object> queryOptions);

    @PUT("content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> localize(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Query("locale") String localeCode,
            @Body JSONObject body);

    @POST("content_types/{content_type_uid}/entries/{entry_uid}/unlocalize")
    Call<ResponseBody> unLocalize(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Query("locale") String localeCode);

    @GET("content_types/{content_type_uid}/entries/{entry_uid}/export")
    Call<ResponseBody> export(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap(encoded = true) Map<String, Object> queryOptions);

    @POST("content_types/{content_type_uid}/entries/import")
    Call<ResponseBody> imports(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @QueryMap(encoded = true) Map<String, Object> queryParameters);

    @POST("content_types/{content_type_uid}/entries/{entry_uid}/import")
    Call<ResponseBody> importExisting(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap(encoded = true) Map<String, Object> queryParameters);

    @POST("content_types/{content_type_uid}/entries/{entry_uid}/publish")
    Call<ResponseBody> publish(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject requestBody);

    @POST("bulk/publish?x-bulk-action=publish")
    Call<ResponseBody> publishWithReference(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject requestBody,
            @QueryMap(encoded = true) Map<String, Object> queryParameters);

    @POST("content_types/{content_type_uid}/entries/{entry_uid}/unpublish")
    Call<ResponseBody> unpublish(
            @HeaderMap Map<String, Object> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject requestBody);
}
