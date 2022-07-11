package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface LocaleService {

    @GET("locales")
    Call<ResponseBody> locales(
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> params);

    @POST("locales")
    Call<ResponseBody> create(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @GET("locales/{code}")
    Call<ResponseBody> singel(
            @HeaderMap Map<String, Object> headers,
            @Path("code") String code,
            @QueryMap Map<String, Object> params);

    @PUT("locales/{code}")
    Call<ResponseBody> update(
            @HeaderMap Map<String, Object> headers,
            @Path("code") String code,
            @QueryMap Map<String, Object> params,
            @Body JSONObject body);

    @DELETE("locales/{code}")
    Call<ResponseBody> delete(
            @HeaderMap Map<String, Object> headers, @Path("code") String code);

    @POST("locales")
    Call<ResponseBody> setFallback(
            @HeaderMap Map<String, Object> headers,
            @Body JSONObject body);

    @PUT("locales/{locale_uid}")
    Call<ResponseBody> updateFallback(
            @HeaderMap Map<String, Object> headers,
            @Path("locale_uid") String localeUid,
            @Body JSONObject body);

}
