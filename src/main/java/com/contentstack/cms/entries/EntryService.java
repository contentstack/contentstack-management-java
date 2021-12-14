package com.contentstack.cms.entries;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface EntryService {

    @GET("v3/content_types/{content_type_uid}/entries")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @QueryMap JSONObject query);

    @GET("v3/content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap JSONObject query);

    @POST("v3/content_types/{content_type_uid}/entries")
    Call<ResponseBody> create(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Body JSONObject body,
            @QueryMap JSONObject query);

    @PUT("v3/content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject body,
            @QueryMap JSONObject query);

    // Push, Pull, Add, Sub Operations
    @PUT("v3/content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> operations(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject body);

    @DELETE("v3/content_types/{content_type_uid}/entries/{entry_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject body,
            @QueryMap JSONObject query);

    @POST("v3/content_types/{content_type_uid}/entries/{entry_uid}/versions/{version_number}/name")
    Call<ResponseBody> versionName(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Path("version_number") int version,
            @Body JSONObject body);

    @GET("v3/content_types/{content_type_uid}/entries/{entry_uid}/versions")
    Call<ResponseBody> detailOfAllVersion(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap JSONObject body);

    @DELETE("v3/content_types/{content_type_uid}/entries/{entry_uid}/versions/{version_number}/name")
    Call<ResponseBody> deleteVersionName(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Path("version_number") int version,
            @Body JSONObject body);

    @GET("v3/content_types/{content_type_uid}/entries/{entry_uid}/references")
    Call<ResponseBody> reference(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap JSONObject body);

    @GET("v3/content_types/{content_type_uid}/entries/{entry_uid}/locales")
    Call<ResponseBody> language(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid);

    @PUT("v3/content_types/{content_type_uid}/entries/{entry_uid}?locale={locale}")
    Call<ResponseBody> localize(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Path("locale") String localeCode,
            @Body JSONObject body);

    @POST("v3/content_types/{content_type_uid}/entries/{entry_uid}?unlocalize={locale}")
    Call<ResponseBody> unLocalize(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Path("locale") String localeCode);

    @GET("v3/content_types/{content_type_uid}/entries/{entry_uid}/export")
    Call<ResponseBody> export(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Path("locale") String localeCode);

    @POST("v3/content_types/{content_type_uid}/entries/import")
    Call<ResponseBody> imports(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @QueryMap JSONObject query);

    @POST("v3/content_types/{content_type_uid}/entries/{entry_uid}/import")
    Call<ResponseBody> importExisting(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @QueryMap JSONObject query);

    @POST("v3/content_types/{content_type_uid}/entries/{entry_uid}/publish")
    Call<ResponseBody> publish(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject query);

    @POST("v3/bulk/publish?x-bulk-action=publish")
    Call<ResponseBody> publishWithReference(
            @HeaderMap Map<String, String> headers,
            @QueryMap JSONObject query,
            @Body JSONObject body);

    @POST("v3/content_types/{content_type_uid}/entries/{entry_uid}/unpublish")
    Call<ResponseBody> unpublish(
            @HeaderMap Map<String, String> headers,
            @Path("content_type_uid") String contentTypeUid,
            @Path("entry_uid") String entryUid,
            @Body JSONObject query);
}
