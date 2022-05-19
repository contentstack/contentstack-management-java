package com.contentstack.cms.core;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;


/**
 * The ContentstackResponse is the response when we execute the API Response, ContentstackResponse contains different
 * response type
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class ContentstackResponse<T> {

    Call<ResponseBody> response;


    public Response<ResponseBody> fetch(Call<ResponseBody> response) {
        try {
            return response.execute();
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
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

}
