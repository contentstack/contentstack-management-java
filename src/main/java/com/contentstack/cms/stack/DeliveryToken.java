package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * <b>Delivery tokens:</b>Delivery tokens provide read-only access to the associated environments, while management
 * tokens provide read-write access to the content of your stack. Use these tokens along with the stack API key to make
 * authorized API requests.
 * <br>
 * <b>Management tokens: </b> To authenticate Content Management API (CMA) requests over your stack content, you can
 * use Management Tokens
 * <br>
 *
 * @author Shailesh Mishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class DeliveryToken {

    protected final TokenService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    protected DeliveryToken(TokenService tokenService, HashMap<String, Object> stack) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stack);
        this.service = tokenService;
    }


    /**
     * Sets header for the request
     *
     * @param key
     *         header key for the request
     * @param value
     *         header value for the request
     */
    public void addHeader(@NotNull String key, @NotNull Object value) {
        this.headers.put(key, value);
    }

    /**
     * Sets header for the request
     *
     * @param key
     *         query param key for the request
     * @param value
     *         query param value for the request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
    }


    /**
     * Set header for the request
     *
     * @param key
     *         Removes query param using key of request
     */
    public void removeParam(@NotNull String key) {
        this.params.remove(key);
    }


    /**
     * To clear all the query params
     */
    protected void clearParams() {
        this.params.clear();
    }


    /**
     * The Get all delivery tokens request returns the details of all the delivery tokens created in a stack.
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-delivery-tokens">Get all
     * Delivery Tokens
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        return this.service.getDeliveryToken(this.headers);
    }

    /**
     * The Get a single delivery token request returns the details of all the delivery tokens created in a stack.
     *
     * @param tokenUid
     *         The UID of the token that you want to retrieve a delivery token
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-delivery-token">Get
     * a Single Delivery Token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> single(@NotNull String tokenUid) {
        return this.service.getDeliveryToken(this.headers, tokenUid);
    }

    /**
     * The Create delivery token request is used to create a delivery token in the stack.
     * <p>
     * In the Request Body, you need to pass the details of the delivery token in JSON format. The details include the
     * name, description, and the environment of the delivery token.
     *
     * @param requestBody
     *         The request body to create a delivery token
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-delivery-token">Create
     * Delivery Token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.createDeliveryToken(this.headers, requestBody);
    }

    /**
     * The Update delivery token request lets you update the details of a delivery token.
     * <p>
     * In the Request Body, you need to pass the updated details of the delivery token in JSON format. The details
     * include the updated name, description, and/or the environment of the delivery token.
     * <p>
     * You need to specify the branch and alias scope for your delivery token through the following schema in the
     * request body:
     *
     * @param tokenUid
     *         The UID of the token that you want to update
     * @param requestBody
     *         the body should be of @{@link JSONObject} type
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-delivery-token">Update
     * Delivery Token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull String tokenUid, @NotNull JSONObject requestBody) {
        return this.service.updateDeliveryToken(this.headers, tokenUid, requestBody);
    }

    /**
     * The Delete delivery token request deletes a specific delivery token
     *
     * @param tokenUid
     *         The UID of the token that you want to delete
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-delivery-token">Delete
     * Delivery Token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String tokenUid) {
        return this.service.deleteDeliveryToken(this.headers, tokenUid, false);
    }

    /**
     * The Delete delivery token request deletes a specific delivery token.
     *
     * @param tokenUid
     *         The UID of the token that you want to delete
     * @param isForce
     *         provide ‘true’ to force delete a delivery token
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-delivery-token">Delete
     * Delivery Token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String tokenUid, @NotNull Boolean isForce) {
        return this.service.deleteDeliveryToken(this.headers, tokenUid, isForce);
    }


}
