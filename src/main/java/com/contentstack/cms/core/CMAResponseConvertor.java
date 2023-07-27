package com.contentstack.cms.core;

import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * Contentstack Response:
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-20
 */

public class CMAResponseConvertor {

    private static final String MODEL_NULL_CHECK = "model class == null";
    private static final String RESPONSE_NULL = "response == null";
    private final Response<ResponseBody> response;

    // The `CMAResponseConvertor` class has a constructor that takes a
    // `Response<ResponseBody>` object as a
    // parameter. This constructor initializes the `response` field of the class
    // with the provided
    // `Response<ResponseBody>` object. This allows the `CMAResponseConvertor` class
    // to access and work
    // with the response data in its methods.
    public CMAResponseConvertor(Response<ResponseBody> response) {
        this.response = response;
    }

    /**
     * The function returns the response body as a string if it is not null,
     * otherwise it returns null.
     *
     * @return The method is returning a String.
     * @throws IOException if an I/O error occurs
     */
    public String asString() throws IOException {
        return this.response.body() != null ? this.response.body().string() : null;
    }

    /**
     * The function converts the response body to a JSON string using the Gson
     * library.
     *
     * @return The method is returning a JSON string representation of the response
     * body.
     * @throws IOException if an I/O error occurs
     */
    public String asJson() throws IOException {
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().toJson(body);
    }

    /**
     * The function takes a string as input and converts it to a JSON string using
     * the Gson library.
     *
     * @param string The parameter "string" is a String object that represents the
     *               input string that you
     *               want to convert to JSON format.
     * @return The method is returning a JSON representation of the input string.
     */
    public String asJson(String string) {
        Objects.requireNonNull(string, "string == null");
        return new Gson().toJson(string);
    }

    /**
     * The function converts a JSON response body into a Java object of the
     * specified class using the Gson
     * library.
     *
     * @param <T>    the type of the parameter
     * @param tClass The `tClass` parameter is a `Class` object that represents the
     *               type of the model class
     *               that you want to convert the response body to.
     * @return The method is returning an object of type T, which is determined by
     * the input parameter
     * tClass.
     * @throws IOException if an I/O error occurs
     */
    public <T> T toModel(Class<T> tClass) throws IOException {
        Objects.requireNonNull(tClass, MODEL_NULL_CHECK);
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().fromJson(body, tClass);
    }

    /**
     * It accepts the modal class and the string data response to return the model
     * class
     *
     * @param tClass   the class
     * @param response data response
     * @param <T>      the model class type
     * @return the model class
     */
    public <T> T toModel(Class<T> tClass, String response) {
        Objects.requireNonNull(tClass, MODEL_NULL_CHECK);
        Objects.requireNonNull(response, RESPONSE_NULL);
        return new Gson().fromJson(response, tClass);
    }

    public <T> T toModel(Class<T> tClass, Response<ResponseBody> response) throws IOException {
        Objects.requireNonNull(tClass, MODEL_NULL_CHECK);
        Objects.requireNonNull(response, RESPONSE_NULL);
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().fromJson(body, tClass);
    }

    /**
     * The function `requestBody` returns the request object associated with a given
     * call instance.
     *
     * @param callInstance An instance of the Call class, which represents a network
     *                     request in OkHttp.
     * @return The method is returning a Request object.
     */
    public Request requestBody(Call callInstance) {
        return callInstance.request();
    }

}
