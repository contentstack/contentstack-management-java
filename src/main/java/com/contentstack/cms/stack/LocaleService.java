package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface LocaleService {

        @GET("locales?include_count=true")
        Call<ResponseBody> locales(
                @HeaderMap Map<String, Object> headers);

        @POST("locales")
        Call<ResponseBody> addLocale(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);

        @GET("locales/{code}")
        Call<ResponseBody> getLocale(
                @HeaderMap Map<String, Object> headers,
                @Path("code") String code);

        @PUT("locales/{code}")
        Call<ResponseBody> updateLocale(
                @HeaderMap Map<String, Object> headers,
                @Path("code") String code,
                @Body JSONObject body);

        @DELETE("locales/{code}")
        Call<ResponseBody> deleteLocale(
                @HeaderMap Map<String, Object> headers, @Path("code") String code);

        @POST("locales")
        Call<ResponseBody> setFallbackLocale(
                @HeaderMap Map<String, Object> headers,
                @Body JSONObject body);

        @PUT("locales/{locale_uid}")
        Call<ResponseBody> updateFallbackLocale(
                @HeaderMap Map<String, Object> headers,
                @Path("locale_uid") String localeUid,
                @Body JSONObject body);

}
