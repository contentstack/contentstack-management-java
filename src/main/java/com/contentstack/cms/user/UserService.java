package com.contentstack.cms.user;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface UserService {

    @GET("user")
    Call<ResponseBody> getUser();

    @PUT("user")
    Call<ResponseBody> updateUser();

    @POST("user/activate/{user_activation_token}")
    Call<ResponseBody> activateUser(@Path("user_activation_token") String token);

    @POST("user/forgot_password")
    Call<ResponseBody> requestPassword();

    @PUT("user/reset_password")
    Call<ResponseBody> resetPassword();

    @DELETE("user/user-session")
    Call<ResponseBody> logout(@Header("authtoken") String authtoken);

    @DELETE("user/user-session")
    Call<ResponseBody> logout();

    @GET("user")
    Call<ResponseBody> getUserOrganization(@QueryMap Map<String, String> options);
}
