package com.contentstack.cms.app;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface InstallationService {


    @POST("manifest")
    Call<ResponseBody> create(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, Object> query,
            @Body JSONObject body
    );
}
