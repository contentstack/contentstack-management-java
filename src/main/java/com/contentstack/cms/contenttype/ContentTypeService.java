package com.contentstack.cms.contenttype;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ContentTypeService {

    @GET("content_types")
    Call<ResponseBody> fetch(@HeaderMap Map<String, String> headers,
                             @QueryMap Map<String, Object> query);

    @GET("content_types/{content_type_uid}")
    Call<ResponseBody> single(@HeaderMap Map<String, String> headers,
                              @Path("content_type_uid") String contentTypeUid,
                              @QueryMap Map<String, Object> queryParam);

    @POST("content_types")
    Call<ResponseBody> create(@HeaderMap Map<String, String> headers,
                              @Body JSONObject body);

    @PUT("content_types/{content_type_uid}")
    Call<ResponseBody> update(@Path("content_type_uid") String contentTypeUid,
                              @HeaderMap Map<String, String> headers,
                              @Body JSONObject body);

    @PUT("content_types/{content_type_uid}")
    Call<ResponseBody> visibilityRule(@Path("content_type_uid") String contentTypeUid,
                                      @HeaderMap Map<String, String> headers,
                                      @Body JSONObject body);

    @DELETE("content_types/{content_type_uid}")
    Call<ResponseBody> delete(@Path("content_type_uid") String contentTypeUid,
                              @HeaderMap Map<String, String> headers);

    @DELETE("content_types/{content_type_uid}")
    Call<ResponseBody> delete(@Path("content_type_uid") String contentTypeUid,
                              @HeaderMap Map<String, String> headers,
                              @Query("force") Boolean force);

    @GET("content_types/{content_type_uid}/references")
    Call<ResponseBody> reference(@Path("content_type_uid") String contentTypeUid,
                                 @HeaderMap Map<String, String> headers);

    @GET("content_types/{content_type_uid}/references?include_global_fields=true")
    Call<ResponseBody> referenceIncludeGlobalField(@Path("content_type_uid") String contentTypeUid,
                                                   @HeaderMap Map<String, String> headers);

    @GET("content_types/{content_type_uid}/export")
    Call<ResponseBody> export(@Path("content_type_uid") String contentTypeUid,
                              @HeaderMap Map<String, String> headers,
                              @Query("version") int version);

    @GET("content_types/{content_type_uid}/export")
    Call<ResponseBody> export(@Path("content_type_uid") String contentTypeUid,
                              @HeaderMap Map<String, String> headers);

    @POST("content_types/import")
    Call<ResponseBody> imports(@HeaderMap Map<String, String> headers);

    @POST("content_types/import?overwrite=true")
    Call<ResponseBody> importOverwrite(@HeaderMap Map<String, String> headers);

}
