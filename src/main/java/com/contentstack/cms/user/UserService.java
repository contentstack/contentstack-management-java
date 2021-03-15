package com.contentstack.cms.user;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService<T> {

    @Headers({
            "Cache-Control: max-age=640000",
            "Content-Type: application/json",
            "content_encoding:   UTF-8"
    })
    @GET("user")
    Call<ResponseBody> getUser();

    @PUT("user")
    Call<ResponseBody> updateUser();

    @FormUrlEncoded
    @POST("user/activate/{user_activation_token}")
    Call<ResponseBody> activateUser(@Path("user_activation_token") String user_activation_token);

    @FormUrlEncoded
    @POST("user/forgot_password")
    Call<ResponseBody> requestPassword();

    @PUT("user/reset_password")
    Call<ResponseBody> resetPassword();

    @DELETE("user/user-session")
    // ACCEPT PARAM AUTHTOKEN
    Call<ResponseBody> logout(@Header("authtoken") String authtoken);

    @DELETE("user/user-session")
    Call<ResponseBody> logout();

}
