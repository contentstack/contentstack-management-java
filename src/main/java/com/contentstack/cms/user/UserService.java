package com.contentstack.cms.user;

import com.contentstack.cms.models.LoginDetails;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;

public interface UserService {

    @POST("user-session")
    Call<LoginDetails> login(@Body RequestBody body);

    @GET("user")
    Call<ResponseBody> getUser();

    @PUT("user")
    Call<ResponseBody> updateUser(@Body RequestBody body);

    @POST("user/activate/{user_activation_token}")
    Call<ResponseBody> activateUser(
            @Path("user_activation_token") String activationToken,
            @Body RequestBody body);

    @POST("user/forgot_password")
    Call<ResponseBody> requestPassword(@Body RequestBody body);

    @POST("user/reset_password")
    Call<ResponseBody> resetPassword(@Body RequestBody body);

    @DELETE("user-session")
    Call<ResponseBody> logout(
            @Header("authtoken") String authtoken);

    @DELETE("user-session")
    Call<ResponseBody> logout();

    @GET("user")
    Call<ResponseBody> getUserOrganization(
            @QueryMap HashMap<String, Object> options);

}
