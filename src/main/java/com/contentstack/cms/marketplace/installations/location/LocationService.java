package com.contentstack.cms.marketplace.installations.location;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface LocationService {

    @GET("installations/{uid}/locations/configuration")
    Call<ResponseBody> getConfigurationLocation(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @QueryMap Map<String, Object> queryParams);
}
