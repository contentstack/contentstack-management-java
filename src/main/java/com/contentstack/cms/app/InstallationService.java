package com.contentstack.cms.app;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.Map;

public interface InstallationService {


    @POST("manifests/{uid}/install")
    Call<ResponseBody> create(
            @HeaderMap Map<String, String> headers,
            @Path("uid") String uid,
            @Body JSONObject body
    );
}
