package com.contentstack.cms.user;

import com.contentstack.cms.models.LoginDetails;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface UserService {

    @POST("user-session")
    Call<LoginDetails> login(@HeaderMap Map<String, String> headers, @Body JSONObject body);

    @DELETE("user-session")
    Call<ResponseBody> logout();

    @DELETE("user-session")
    Call<ResponseBody> logout(@Header("authtoken") String authtoken);

    @GET("user")
    Call<ResponseBody> getUser();

    @PUT("user")
    Call<ResponseBody> update(@HeaderMap Map<String, String> headers, @Body JSONObject body);

    @POST("user/activate/{user_activation_token}")
    Call<ResponseBody> activateAccount(@Path("user_activation_token") String token, @Body JSONObject body);

    @POST("user/forgot_password")
    Call<ResponseBody> requestPassword(@Body JSONObject body);

    @POST("user/reset_password")
    Call<ResponseBody> resetPassword(@Body JSONObject body);


}
