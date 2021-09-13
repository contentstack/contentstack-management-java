package com.contentstack.cms.user;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The interface User service.
 */
public interface UserService {


    @POST("user-session")
    Call<ResponseBody> login(@Body HashMap<String, HashMap<String, String>> body);

    /**
     * Gets user.
     *
     * @return the user
     */
    @GET("user")
    Call<ResponseBody> getUser();

    /**
     * Update user call.
     *
     * @return the call
     */
    @PUT("user")
    Call<ResponseBody> updateUser();

    /**
     * Activate user call.
     *
     * @param token the token
     * @return the call
     */
    @POST("user/activate/{user_activation_token}")
    Call<ResponseBody> activateUser(@Path("user_activation_token") String token);

    /**
     * Request password call.
     *
     * @return the call
     */
    @POST("user/forgot_password")
    Call<ResponseBody> requestPassword();

    /**
     * Reset password call.
     *
     * @return the call
     */
    @PUT("user/reset_password")
    Call<ResponseBody> resetPassword();

    /**
     * Logout call.
     *
     * @param authtoken the authtoken
     * @return the call
     */
    @DELETE("user/user-session")
    Call<ResponseBody> logout(@Header("authtoken") String authtoken);

    /**
     * Logout call.
     *
     * @return the call
     */
    @DELETE("user/user-session")
    Call<ResponseBody> logout();

    /**
     * Gets user organization.
     *
     * @param options the options
     * @return the user organization
     */
    @GET("user")
    Call<ResponseBody> getUserOrganization(@QueryMap HashMap<String, Object> options);
}
