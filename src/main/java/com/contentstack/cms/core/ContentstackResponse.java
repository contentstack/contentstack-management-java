package com.contentstack.cms.core;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * The ContentstackResponse is the response when we execute the API Response,
 * ContentstackResponse contains different
 * response type
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-20
 */
class ContentstackResponse<T> {

    // The line `Call<ResponseBody> response;` declares a variable named `response`
    // of type
    // `Call<ResponseBody>`. This variable is used to store an instance of the
    // `Call` class, which
    // represents a network request that has been enqueued for execution. The
    // generic type
    // `ResponseBody` specifies the type of the response body that will be returned
    // by the network
    // request.
    Call<ResponseBody> response;

    /**
     * The function fetches a response from a network call and returns it, or throws
     * a CMARuntimeException
     * if an IOException occurs.
     * 
     * @param response The "response" parameter is an instance of the
     *                 `Call<ResponseBody>` class, which
     *                 represents a network request that has been enqueued for
     *                 execution. It is used to make the actual
     *                 network call and retrieve the response.
     * @return The method is returning a Response object with a generic type of
     *         ResponseBody.
     */
    public Response<ResponseBody> fetch(Call<ResponseBody> response) throws CMARuntimeException {
        try {
            return response.execute();
        } catch (IOException e) {
            throw new CMARuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * The function returns a Response object of type T.
     * 
     * @return The method is returning a generic type `Response<T>`. However, in
     *         this specific
     *         implementation, it is returning `null`.
     */
    Response<T> getResponse() {
        return null;
    }

    /**
     * The function returns a null value of type ResponseBody.
     * 
     * @return The method is returning a null value.
     */
    ResponseBody getResponseBody() {
        return null;
    }

}
