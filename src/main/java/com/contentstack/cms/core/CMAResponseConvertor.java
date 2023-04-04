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
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-20
 */

public class CMAResponseConvertor {

    private static final String MODEL_NULL_CHECK = "model class == null";
    private static final String RESPONSE_NULL = "response == null";
    private final Response<ResponseBody> response;

    /**
     * CMAResponseConvertor accepts Response<ResponseBody>
     *
     * @param response
     *         this is response body
     */
    public CMAResponseConvertor(Response<ResponseBody> response) {
        this.response = response;
    }

    /**
     * It converts the response to string type
     *
     * @return stringfy the response
     * @throws IOException
     *         throws exception when invalid response received
     */
    public String asString() throws IOException {
        return this.response.body() != null ? this.response.body().string() : null;
    }

    /**
     * It converts the reponse to the json object
     *
     * @return string of json
     * @throws IOException
     *         the io exception
     */
    public String asJson() throws IOException {
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().toJson(body);
    }

    /**
     * It converts to json string to the
     *
     * @param string
     *         response as string
     * @return string type json
     */
    public String asJson(String string) {
        Objects.requireNonNull(string, "string == null");
        return new Gson().toJson(string);
    }

    /**
     * It accepts model class you want to convert the data response to the specified model class
     *
     * @param tClass
     *         the model class
     * @param <T>
     *         type of the model class
     * @return the model class
     * @throws IOException
     *         exception while parsing the response data
     */
    public <T> T toModel(Class<T> tClass) throws IOException {
        Objects.requireNonNull(tClass, MODEL_NULL_CHECK);
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().fromJson(body, tClass);
    }


    /**
     * It accepts the modal class and the string data response to return the model class
     *
     * @param tClass
     *         the class
     * @param response
     *         data response
     * @param <T>
     *         the model class type
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

    public Request requestBody(Call callInstance) {
        return callInstance.request();
    }

}
