package com.contentstack.cms.stack;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AssetService {

    @GET("assets")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query);

    @GET("assets/{asset_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String uid,
            @QueryMap(encoded = true) Map<String, Object> query);

    @GET("assets")
    Call<ResponseBody> specificFolder(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query);

    @GET("assets")
    Call<ResponseBody> subfolder(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query);

    @Multipart
    @POST("assets")
    Call<ResponseBody> uploadAsset(
            @HeaderMap Map<String, Object> headers,
            @Part MultipartBody.Part file,
            @Part("asset[description]") RequestBody description,
            @QueryMap(encoded = true) Map<String, Object> query);

    @Multipart
    @PUT("assets/{asset_uid}")
    Call<ResponseBody> replace(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String uid,
            @Part MultipartBody.Part file,
            @Part("asset[description]") RequestBody description,
            @QueryMap(encoded = true) Map<String, Object> query);

    @PUT("assets/{asset_uid}")
    Call<ResponseBody> generatePermanentUrl(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String uid,
            @Body JSONObject body);

    @GET("assets/{asset_uid}/{slug}")
    Call<ResponseBody> downloadPermanentUrl(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String uid,
            @Path("slug") String slugUrl,
            @QueryMap(encoded = true) Map<String, Object> query);

    @DELETE("assets/{asset_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String uid);

    @GET("assets/rt")
    Call<ResponseBody> rteInfo(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query);

    @POST("assets/{asset_uid}/versions/{version_number}/name")
    Call<ResponseBody> setVersionName(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String assetUid,
            @Path("version_number") int versionNumber,
            @Body JSONObject requestBody);

    @GET("assets/{asset_uid}/versions")
    Call<ResponseBody> getVersionNameDetails(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String assetUid,
            @QueryMap(encoded = true) Map<String, Object> query);

    @DELETE("assets/{asset_uid}/versions/{version_number}/name")
    Call<ResponseBody> deleteVersionName(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String assetUid,
            @Path("version_number") int versionNumber);

    @GET("assets/{asset_uid}/references")
    Call<ResponseBody> getReferences(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String assetUid);

    @GET("assets/{asset_type}")
    Call<ResponseBody> getByType(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_type") String assetType);

    @PUT("assets/{asset_uid}")
    Call<ResponseBody> updateDetails(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String assetUid,
            @QueryMap(encoded = true) Map<String, Object> query,
            @Body JSONObject requestBody);

    @POST("assets/{asset_uid}/publish")
    Call<ResponseBody> publish(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String assetUid,
            @Body JSONObject requestBody);

    @POST("assets/{asset_uid}/unpublish")
    Call<ResponseBody> unpublish(
            @HeaderMap Map<String, Object> headers,
            @Path("asset_uid") String assetUid,
            @Body JSONObject requestBody);

    @GET("assets/folders/{folder_uid}")
    Call<ResponseBody> singleFolder(
            @HeaderMap Map<String, Object> headers,
            @Path("folder_uid") String folderUid,
            @QueryMap Map<String, Object> query);

    @GET("assets")
    Call<ResponseBody> singleFolderByName(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query);

    @GET("assets")
    Call<ResponseBody> getSubfolder(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query);

    @POST("assets/folders")
    Call<ResponseBody> createFolder(
            @HeaderMap Map<String, Object> headers,
            @QueryMap(encoded = true) Map<String, Object> query,
            @Body JSONObject requestBody);

    @PUT("assets/folders/{folder_uid}")
    Call<ResponseBody> updateFolder(
            @HeaderMap Map<String, Object> headers,
            @Path("folder_uid") String folderUid,
            @QueryMap(encoded = true) Map<String, Object> query,
            @Body JSONObject requestBody);

    @DELETE("assets/folders/{folder_uid}")
    Call<ResponseBody> deleteFolder(
            @HeaderMap Map<String, Object> headers,
            @Path("folder_uid") String folderUid);

}
