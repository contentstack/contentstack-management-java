package com.contentstack.cms.user;

import retrofit2.Call;
import retrofit2.http.*;

public interface UserService<T> {

    @Headers({
            "Cache-Control: max-age=640000",
            "Content-Type: application/json",
            "content_encoding:   UTF-8"
    })
    @GET("user")
    Call<T> getUser();

    @PUT("user")
    Call<T> updateUser();

    @FormUrlEncoded
    @POST("user/activate/{user_activation_token}")
    Call<T> activateUser(@Path("user_activation_token") String user_activation_token);

    @FormUrlEncoded
    @POST("user/forgot_password")
    Call<T> requestPassword();

    @PUT("user/reset_password")
    Call<T> resetPassword();

    @DELETE("user/user-session")
    // ACCEPT PARAM AUTHTOKEN
    Call<T> logout(@Header("authtoken") String authtoken);

    @DELETE("user/user-session")
        // ACCEPT PARAM AUTHTOKEN
    Call<T> logout();


//    @FormUrlEncoded
//    @POST("user/user_session")
//    Call<T> login(@Path("user_session") String reset_password);



}
