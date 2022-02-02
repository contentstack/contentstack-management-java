package com.contentstack.cms.global;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface GlobalFieldService {

        @GET("global_fields")
        Call<ResponseBody> fetch(
                        @Header("api_key") String apiKey,
                        @Header("authorization") String authorization);

        @GET("global_fields/{global_field_uid}")
        Call<ResponseBody> single(
                        @Path("global_field_uid") String globalFieldUid,
                        @Header("api_key") String apiKey,
                        @Header("authorization") String authorization);

        @POST("global_fields")
        Call<ResponseBody> create(
                        @Header("api_key") String apiKey,
                        @Header("authorization") String authorization,
                        @Body RequestBody body);

        @PUT("global_fields/{global_field_uid}")
        Call<ResponseBody> update(
                        @Path("global_field_uid") String globalFieldUid,
                        @Header("api_key") String apiKey,
                        @Header("authorization") String authorization,
                        @Body RequestBody body);

        @DELETE("global_fields/{global_field_uid}?force=true")
        Call<ResponseBody> delete(
                        @Path("global_field_uid") String globalFieldUid,
                        @Header("api_key") String apiKey,
                        @Header("authorization") String authorization);

        @Multipart
        @POST("global_fields/import")
        // @Headers("Content-Type: multipart/form-data")
        @Headers({ "Content-Type: application/json;charset=UTF-8", "Content-Type: multipart/form-data" })
        Call<ResponseBody> imports(
                        @Header("api_key") String apiKey,
                        @Header("authorization") String authorization,
                        @Body RequestBody jsonBody);

        @GET("global_fields/{global_field_uid}/export")
        Call<ResponseBody> export(
                        @Path("global_field_uid") String globalFieldUid,
                        @Header("api_key") String apiKey,
                        @Header("authorization") String authorization);
}
