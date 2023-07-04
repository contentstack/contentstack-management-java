package com.contentstack.cms.marketplace.auths;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

import java.util.Map;


public interface AuthService {

    @GET("authorized-apps")
    Call<ResponseBody> findAuthorizedApps(@HeaderMap Map<String, String> headers,
                                          @QueryMap Map<String, Object> params );
}
