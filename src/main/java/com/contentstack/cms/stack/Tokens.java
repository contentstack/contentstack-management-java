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
public class Tokens {

    protected final TokenService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    protected Tokens(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(TokenService.class);
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
     * Sets header for the request
     *
     * @param key
     *         header key for the request
     * @param value
     *         header value for the request
     * @return Tokens
     */
    public Tokens addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * The Get all delivery tokens request returns the details of all the delivery tokens created in a stack.
     *
     * @return Call
     */
    public Call<ResponseBody> getDeliveryTokens() {
        return this.service.getDeliveryToken(this.headers);
    }

    /**
     * The Get a single delivery token request returns the details of all the delivery tokens created in a stack.
     *
     * @param tokenUid
     *         the UID of the token that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> getDeliveryToken(@NotNull String tokenUid) {
        return this.service.getDeliveryToken(this.headers, tokenUid);
    }

    /**
     * The Create delivery token request is used to create a delivery token in the stack.
     * <p>
     * In the Request Body, you need to pass the details of the delivery token in JSON format. The details include the
     * name, description, and the environment of the delivery token.
     *
     * @param requestBody
     *         details of the delivery token in @{@link JSONObject} format
     * @return Call
     */
    public Call<ResponseBody> createDeliveryToken(@NotNull JSONObject requestBody) {
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
     *         the UID of the token that you want to retrieve
     * @param requestBody
     *         the body should be of @{@link JSONObject} type
     * @return Call
     */
    public Call<ResponseBody> updateDeliveryToken(@NotNull String tokenUid, @NotNull JSONObject requestBody) {
        return this.service.updateDeliveryToken(this.headers, tokenUid, requestBody);
    }

    /**
     * The Delete delivery token request deletes a specific delivery token
     *
     * @param tokenUid
     *         the UID of the token that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> deleteDeliveryToken(@NotNull String tokenUid) {
        return this.service.deleteDeliveryToken(this.headers, tokenUid, false);
    }

    /**
     * The Delete delivery token request deletes a specific delivery token.
     *
     * @param tokenUid
     *         the UID of the token that you want to retrieve
     * @param isForce
     *         provide ‘true’ to force delete a delivery token
     * @return Call
     */
    public Call<ResponseBody> deleteDeliveryToken(@NotNull String tokenUid, @NotNull Boolean isForce) {
        return this.service.deleteDeliveryToken(this.headers, tokenUid, isForce);
    }

    /**
     * The Get all management tokens request returns the details of all the management tokens generated in a stack and
     * NOT the actual management tokens.
     *
     * @return Call
     */
    public Call<ResponseBody> getManagementTokens() {
        return this.service.getManagementToken(this.headers);
    }

    /**
     * The Get a single management token request returns the details of a specific management token generated in a stack
     * and NOT the actual management token.
     *
     * @param tokenUid
     *         the UID of the token that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> getManagementToken(@NotNull String tokenUid) {
        return this.service.getManagementToken(this.headers, tokenUid);
    }

    /**
     * The Create management token request is used to create a management token in a stack. This token provides you with
     * read-write access to the content of your stack.
     *
     * @param requestBody
     *         details of the management token in @{@link JSONObject} format
     * @return Call
     */
    public Call<ResponseBody> createManagementToken(@NotNull JSONObject requestBody) {
        return this.service.createManagementToken(this.headers, requestBody);
    }

    /**
     * The Update management token request lets you update the details of a management token. You can change the name
     * and description of the token; update the stack-level permissions assigned to the token; and change the expiry
     * date of the token (if set).
     * <p>
     * In the Request Body, you need to pass the updated details of the management token in JSON format.
     * <p>
     * To specify the updated branch and alias scope for your management token, use the following schema in the request
     * body:
     *
     * @param tokenUid
     *         the UID of the token that you want to retrieve
     * @param requestBody
     *         details of the management token in @{@link JSONObject} format
     * @return Call
     */
    public Call<ResponseBody> updateManagementToken(@NotNull String tokenUid, @NotNull JSONObject requestBody) {
        return this.service.updateManagementToken(this.headers, tokenUid, requestBody);
    }

    /**
     * The Delete management token request deletes a specific management token
     *
     * @param tokenUid
     *         the UID of the token that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> deleteManagementToken(@NotNull String tokenUid) {
        return this.service.deleteManagementToken(this.headers, tokenUid);
    }

}
