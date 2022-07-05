package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface StackService {

    @GET("stacks")
    Call<ResponseBody> fetch(@HeaderMap Map<String, Object> headers,
                             @QueryMap Map<String, Object> params);

    @POST("stacks")
    Call<ResponseBody> create(@Header("organization_uid") String orgUid, @Body JSONObject body);

    @PUT("stacks")
    Call<ResponseBody> update(@HeaderMap Map<String, Object> headers, @Body JSONObject body);

    @POST("stacks/transfer_ownership")
    Call<ResponseBody> transferOwnership(@HeaderMap Map<String, Object> headers, @Body JSONObject body);

    @GET("stacks/accept_ownership/{ownership_token}")
    Call<ResponseBody> acceptOwnership(@HeaderMap Map<String, Object> headers,
                                       @Path("ownership_token") String ownershipToken,
                                       @QueryMap Map<String, Object> query);

    @GET("stacks/settings")
    Call<ResponseBody> setting(@HeaderMap Map<String, Object> headers);

    @POST("stacks/settings")
    Call<ResponseBody> updateSetting(@HeaderMap Map<String, Object> headers, @Body JSONObject body);

    @POST("stacks/share")
    Call<ResponseBody> share(@HeaderMap Map<String, Object> headers, @Body JSONObject body);

    @POST("stacks/unshare")
    Call<ResponseBody> unshare(@HeaderMap Map<String, Object> headers, @Body JSONObject body);

    @GET("stacks?include_collaborators=true")
    Call<ResponseBody> allUsers(@HeaderMap Map<String, Object> headers);

    @POST("stacks/users/roles")
    Call<ResponseBody> updateUserRoles(@HeaderMap Map<String, Object> headers, @Body JSONObject body);
}
