package com.contentstack.cms.assets;

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
                        @HeaderMap Map<String, String> headers,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @GET("assets/{asset_uid}")
        Call<ResponseBody> single(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String uid,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @GET("assets")
        Call<ResponseBody> specificFolder(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @GET("assets")
        Call<ResponseBody> subfolder(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @Multipart
        @POST("assets")
        Call<ResponseBody> uploadAsset(
                        @HeaderMap Map<String, String> headers,
                        @Part MultipartBody.Part file,
                        @Part("asset[description]") RequestBody description,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @PUT("assets/{asset_uid}")
        Call<ResponseBody> replace(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String uid,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @PUT("assets/{asset_uid}")
        Call<ResponseBody> generatePermanentUrl(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String uid,
                        @Body JSONObject body);

        @GET("assets/{asset_uid}/{slug}")
        Call<ResponseBody> downloadPermanentUrl(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String uid,
                        @Path("slug") String slugUrl);

        @DELETE("assets/{asset_uid}")
        Call<ResponseBody> delete(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String uid);

        @GET("assets/rt")
        Call<ResponseBody> rteInfo(@HeaderMap Map<String, String> headers);

        @POST("assets/{asset_uid}/versions/{version_number}/name")
        Call<ResponseBody> setVersionName(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String assetUid,
                        @Path("version_number") int versionNumber,
                        @Body JSONObject requestBody);

        @GET("assets/{asset_uid}/versions")
        Call<ResponseBody> getVersionNameDetails(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String assetUid,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @DELETE("assets/{asset_uid}/versions/{version_number}/name")
        Call<ResponseBody> deleteVersionName(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String assetUid,
                        @Path("version_number") int versionNumber);

        @GET("assets/{asset_uid}/references")
        Call<ResponseBody> getReferences(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String assetUid);

        @GET("assets/{asset_type}")
        Call<ResponseBody> getByType(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_type") String assetType);

        @PUT("assets/{asset_uid}")
        Call<ResponseBody> updateDetails(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String assetUid,
                        @Body JSONObject requestBody);

        @POST("assets/{asset_uid}/publish")
        Call<ResponseBody> publish(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String assetUid,
                        @Body JSONObject requestBody);

        @POST("assets/{asset_uid}/unpublish")
        Call<ResponseBody> unpublish(
                        @HeaderMap Map<String, String> headers,
                        @Path("asset_uid") String assetUid,
                        @Body JSONObject requestBody);

        @GET("assets/folders/{folder_uid}")
        Call<ResponseBody> singleFolder(
                        @HeaderMap Map<String, String> headers,
                        @Path("folder_uid") String folderUid,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @GET("assets")
        Call<ResponseBody> singleFolderByName(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @GET("assets")
        Call<ResponseBody> getSubfolder(
                        @HeaderMap Map<String, String> headers,
                        @QueryMap(encoded = true) Map<String, Object> query);

        @POST("assets/folders")
        Call<ResponseBody> createFolder(
                        @HeaderMap Map<String, String> headers,
                        @Body JSONObject requestBody);

        @PUT("assets/folders/{folder_uid}")
        Call<ResponseBody> updateFolder(
                        @HeaderMap Map<String, String> headers,
                        @Path("folder_uid") String folderUid,
                        @Body JSONObject requestBody);

        @DELETE("assets/folders/{folder_uid}")
        Call<ResponseBody> deleteFolder(
                        @HeaderMap Map<String, String> headers,
                        @Path("folder_uid") String folderUid);

}
