package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface RolesService {

        @GET("roles")
        Call<ResponseBody> getRoles(
                        @HeaderMap Map<String, Object> headers,
                        @QueryMap Map<String, Object> params);

        @GET("roles/{role_uid}")
        Call<ResponseBody> getRole(
                        @HeaderMap Map<String, Object> headers,
                        @Path("role_uid") String roleUid);

        @POST("roles")
        Call<ResponseBody> createRole(
                        @HeaderMap Map<String, Object> headers,
                        @Body JSONObject body);

        @PUT("roles/{role_uid}")
        Call<ResponseBody> updateRole(
                        @HeaderMap Map<String, Object> headers,
                        @Path("role_uid") String roleUid,
                        @Body JSONObject body);

        @DELETE("roles/{role_uid}")
        Call<ResponseBody> deleteRole(
                        @HeaderMap Map<String, Object> headers,
                        @Path("role_uid") String roleUid);

}
