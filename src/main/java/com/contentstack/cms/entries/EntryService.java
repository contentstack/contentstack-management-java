package com.contentstack.cms.entries;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface EntryService {

        @GET("content_types/{content_type_uid}/entries")
        Call<ResponseBody> fetch(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @QueryMap(encoded = true) Map<String, Object> queryParameter);

        @GET("content_types/{content_type_uid}/entries/{entry_uid}")
        Call<ResponseBody> single(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap(encoded = true) Map<String, Object> queryParameter);

        @POST("content_types/{content_type_uid}/entries")
        Call<ResponseBody> create(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Body JSONObject requestBody,
                        @QueryMap(encoded = true) Map<String, Object> queryParameter);

        @PUT("content_types/{content_type_uid}/entries/{entry_uid}")
        Call<ResponseBody> update(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Body JSONObject requestBody,
                        @QueryMap(encoded = true) Map<String, Object> queryParameter);

        // Push, Pull, Add, Sub Operations
        @PUT("content_types/{content_type_uid}/entries/{entry_uid}")
        Call<ResponseBody> atomicOperations(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Body JSONObject body);

        @DELETE("content_types/{content_type_uid}/entries/{entry_uid}")
        Call<ResponseBody> delete(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap(encoded = true) Map<String, Object> queryParameter,
                        @Body JSONObject requestBody);

        @POST("content_types/{content_type_uid}/entries/{entry_uid}/versions/{version_number}/name")
        Call<ResponseBody> versionName(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Path("version_number") String version,
                        @Body JSONObject body);

        @GET("content_types/{content_type_uid}/entries/{entry_uid}/versions")
        Call<ResponseBody> detailOfAllVersion(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap(encoded = true) Map<String, Object> queryOptions);

        @DELETE("content_types/{content_type_uid}/entries/{entry_uid}/versions/{version_number}/name")
        Call<ResponseBody> deleteVersionName(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Path("version_number") int version,
                        @Body JSONObject body);

        @GET("content_types/{content_type_uid}/entries/{entry_uid}/references")
        Call<ResponseBody> reference(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap(encoded = true) Map<String, Object> queryOptions);

        @GET("content_types/{content_type_uid}/entries/{entry_uid}/locales")
        Call<ResponseBody> language(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap(encoded = true) Map<String, Object> queryOptions);

        @PUT("content_types/{content_type_uid}/entries/{entry_uid}?locale={locale}")
        Call<ResponseBody> localize(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Path("locale") String localeCode,
                        @Body JSONObject body);

        @POST("content_types/{content_type_uid}/entries/{entry_uid}?unlocalize={locale}")
        Call<ResponseBody> unLocalize(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Path("locale") String localeCode);

        @GET("content_types/{content_type_uid}/entries/{entry_uid}/export")
        Call<ResponseBody> export(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap(encoded = true) Map<String, Object> queryOptions);

        @POST("content_types/{content_type_uid}/entries/import")
        Call<ResponseBody> imports(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @QueryMap(encoded = true) Map<String, Object> queryParameters);

        @POST("content_types/{content_type_uid}/entries/{entry_uid}/import")
        Call<ResponseBody> importExisting(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap(encoded = true) Map<String, Object> queryParameters);

        @POST("content_types/{content_type_uid}/entries/{entry_uid}/publish")
        Call<ResponseBody> publish(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Body JSONObject requestBody);

        @POST("bulk/publish?x-bulk-action=publish")
        Call<ResponseBody> publishWithReference(
                        @HeaderMap Map<String, String> headers,
                        @Body JSONObject requestBody,
                        @QueryMap(encoded = true) Map<String, Object> queryParameters);

        @POST("content_types/{content_type_uid}/entries/{entry_uid}/unpublish")
        Call<ResponseBody> unpublish(
                        @HeaderMap Map<String, String> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @Body JSONObject requestBody);
}
