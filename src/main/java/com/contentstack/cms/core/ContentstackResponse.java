package com.contentstack.cms.core;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;


/**
 * The ContentstackResponse is the response when we execute the API Response, ContentstackResponse contains different
 * response type
 * <p>
 * Since: 1.0.0
 */
public class ContentstackResponse<T> {

    Call<ResponseBody> response;


    public Response<ResponseBody> fetch(Call<ResponseBody> response) {
        try {
            return response.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return Response
     */
    Response<T> getResponse() {
        return null;
    }

    /**
     * @return ResponseBody
     */
    ResponseBody getResponseBody() {
        return null;
    }

    // Synchronously send the request and return its response

}
