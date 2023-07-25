package com.contentstack.cms.marketplace.request;

import com.contentstack.cms.Parametron;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

public class AppRequest implements Parametron {

    private final RequestService service;
    private final Map<String, String> headers;
    private final Map<String, Object> queryParams;

    // The `public AppRequest(@NotNull Retrofit client, @NotNull String orgId)`
    // constructor is initializing
    // the `AppRequest` object.
    public AppRequest(@NotNull Retrofit client, @NotNull String orgId) {
        this.service = client.create(RequestService.class);
        this.headers = new HashMap<>();
        this.headers.put("organization_uid", orgId);
        this.queryParams = new HashMap<>();
    }

    /**
     * The function creates a network call to create a resource using the provided
     * JSON data.
     * 
     * @param data The `data` parameter is a `JSONObject` that contains the data to
     *             be sent in the request
     *             body.
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> create(JSONObject data) {
        return this.service.create(this.headers, data);
    }

    /**
     * The function returns a call to list requests with specified headers and query
     * parameters.
     * 
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> find() {
        return this.service.listRequests(this.headers, this.queryParams);
    }

    /**
     * The function returns a call to list requested stacks with the provided
     * headers and query parameters.
     * 
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> findRequestedStacks() {
        return this.service.listRequestedStacks(this.headers, this.queryParams);
    }

    /**
     * The function `delete` sends a request to delete a specific request identified
     * by its `requestId`.
     * 
     * @param requestId The `requestId` parameter is a unique identifier for the
     *                  request that needs to be
     *                  deleted.
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> delete(String requestId) {
        return this.service.deleteRequest(this.headers, requestId);
    }

    /**
     * The function adds a parameter to the query parameters map and returns the
     * updated AppRequest object.
     * 
     * @param key   A string representing the key of the parameter.
     * @param value The "value" parameter is of type Object and represents the value
     *              associated with the
     *              given key in the queryParams map.
     * @return The method is returning an instance of the `AppRequest` class.
     */
    @SuppressWarnings("unchecked")
    @Override
    public AppRequest addParam(@NotNull String key, @NotNull Object value) {
        this.queryParams.put(key, value);
        return this;
    }

    /**
     * The function adds a header to the headers map and returns the updated
     * AppRequest object.
     * 
     * @param key   The key parameter is a String that represents the name of the
     *              header.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return The method is returning an instance of the AppRequest class.
     */
    @SuppressWarnings("unchecked")
    @Override
    public AppRequest addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * The function adds all the key-value pairs from the given HashMap to the
     * queryParams HashMap and
     * returns the updated AppRequest object.
     * 
     * @param params A HashMap object named "params"
     * @return The method is returning an instance of the `AppRequest` class.
     */
    @SuppressWarnings("unchecked")
    @Override
    public AppRequest addParams(@NotNull HashMap params) {
        this.queryParams.putAll(params);
        return this;
    }

    /**
     * The function adds headers to a HashMap and returns the updated AppRequest
     * object.
     * 
     * @param headers The "headers" parameter is a HashMap object that contains
     *                key-value pairs
     *                representing the headers to be added to an AppRequest object.
     * @return The method is returning an instance of the `AppRequest` class.
     */

    @SuppressWarnings("unchecked")
    @Override
    public AppRequest addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
