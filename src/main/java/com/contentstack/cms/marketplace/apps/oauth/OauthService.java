package com.contentstack.cms.marketplace.apps.oauth;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface OauthService {

    @GET("manifests/{uid}/oauth")
    Call<ResponseBody> getOauthConfiguration(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid
    );

    @GET("manifests/oauth/scopes")
    Call<ResponseBody> findScopes(
            @HeaderMap Map<String, String> headers
    );

    @PUT("manifests/{uid}/oauth")
    Call<ResponseBody> updateOauthConfiguration(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body
    );

}
