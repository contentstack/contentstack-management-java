package com.contentstack.cms.assets;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AssetService {

    @GET("v3/assets")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, String> headers,
            @QueryMap JSONObject query);

    @GET("v3/assets/{asset_uid}")
    Call<ResponseBody> single(
            @HeaderMap Map<String, String> headers,
            @Path("asset_uid") String uid,
            @QueryMap JSONObject query);

    @GET("v3/assets?folder={folder_uid}")
    Call<ResponseBody> specificFolder(
            @HeaderMap Map<String, String> headers,
            @Path("folder_uid") String uid,
            @QueryMap JSONObject query);

    @GET("v3/assets?include_folders={boolean_value}&folder={folder_uid}")
    Call<ResponseBody> subfolder(
            @HeaderMap Map<String, String> headers,
            @Path("boolean_value") Boolean includeFolder,
            @Path("folder_uid") String uid);

    @Multipart
    @POST("v3/assets")
    Call<ResponseBody> uploadFile(
            @HeaderMap Map<String, String> headers,
            @PartMap JSONObject partFile,
            @QueryMap JSONObject query);

    @PUT("v3/assets/{asset_uid}")
    Call<ResponseBody> replace(
            @HeaderMap Map<String, String> headers,
            @Path("asset_uid") String uid,
            @QueryMap JSONObject query);

    @PUT("v3/assets/{asset_uid}")
    Call<ResponseBody> generatePermanentUrl(
            @HeaderMap Map<String, String> headers,
            @Path("asset_uid") String uid,
            @Body JSONObject query);

    @GET("v3/assets/{asset_uid}/{slug}")
    Call<ResponseBody> downloadPermanentUrl(
            @HeaderMap Map<String, String> headers,
            @Path("asset_uid") String uid,
            @Path("slug") String slugUrl,
            @Body JSONObject query);

    @DELETE("v3/assets/{asset_uid}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, String> headers,
            @Path("asset_uid") String uid);
}
