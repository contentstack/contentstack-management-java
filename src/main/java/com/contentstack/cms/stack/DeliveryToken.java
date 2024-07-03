package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <b>Delivery tokens:</b>Delivery tokens provide read-only access to the
 * associated environments, while management tokens provide read-write access to
 * the content of your stack. Use these
 * tokens along with the stack API key to make authorized API requests.
 * <br>
 * <b>Management tokens: </b> To authenticate Content Management API (CMA)
 * requests over your stack content, you can use Management Tokens
 * <br>
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-22
 */
public class DeliveryToken implements BaseImplementation<DeliveryToken> {

    protected final TokenService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private String tokenUid;
    String ERROR = "Token UID Can Not Be Null OR Empty";

    protected DeliveryToken(TokenService service, Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = service;
    }

    protected DeliveryToken(TokenService service, Map<String, Object> headers, @NotNull String tokenUid) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.tokenUid = tokenUid;
        this.service = service;
    }


    /**
     * The addParam function adds a key-value pair to a map.
     *
     * @param key   A string representing the key for the parameter. It is annotated
     *              with @NotNull,
     *              indicating that it cannot be null.
     * @param value The value parameter is of type Object, which means it can accept
     *              any type of object as
     *              its value.
     * @return instance of {@link DeliveryToken}
     */
    @Override
    public DeliveryToken addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * @param key   The key parameter is a string that represents the name or
     *              identifier of the header.
     *              It is used to specify the type of information being sent in the
     *              header.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return instance of {@link DeliveryToken}
     */
    @Override
    public DeliveryToken addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of {@link DeliveryToken}
     */
    @Override
    public DeliveryToken addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of {@link DeliveryToken}
     */
    @Override
    public DeliveryToken addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * The function removes a parameter from a map using the specified key.
     *
     * @param key The key parameter is a String that represents the key of the
     *            parameter to be removed.
     */
    protected void removeParam(@NotNull String key) {
        this.params.remove(key);
    }

    protected DeliveryToken clearParams() {
        this.params.clear();
        return this;
    }

    /**
     * The Get all delivery tokens request returns the details of all the delivery
     * tokens created in a stack.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-delivery-tokens">Get
     * all
     * Delivery Tokens
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.getDeliveryToken(this.headers);
    }

    /**
     * The Get a single delivery token request returns the details of all the
     * delivery tokens created in a stack.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-delivery-token">Get
     * a
     * Single Delivery Token
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        Objects.requireNonNull(this.tokenUid, ERROR);
        return this.service.getDeliveryToken(this.headers, this.tokenUid);
    }

    /**
     * The Create delivery token request is used to create a delivery token in the
     * stack.
     * <p>
     * In the Request Body, you need to pass the details of the delivery token in
     * JSON format. The details include the
     * name, description, and the environment of the delivery token.
     *
     * @param requestBody The request body to create a delivery token
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-delivery-token">Create
     * Delivery
     * Token
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.createDeliveryToken(this.headers, requestBody);
    }

    /**
     * The Update delivery token request lets you update the details of a delivery
     * token.
     * <p>
     * In the Request Body, you need to pass the updated details of the delivery
     * token in JSON format. The details
     * include the updated name, description, and/or the environment of the delivery
     * token.
     * <p>
     * You need to specify the branch and alias scope for your delivery token
     * through the following schema in the
     * request body:
     *
     * @param requestBody the body should be of @{@link JSONObject} type
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-delivery-token">Update
     * Delivery
     * Token
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        Objects.requireNonNull(this.tokenUid, ERROR);
        return this.service.updateDeliveryToken(this.headers, this.tokenUid, requestBody);
    }

    /**
     * The Delete delivery token request deletes a specific delivery token
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-delivery-token">Delete
     * Delivery
     * Token
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        Objects.requireNonNull(this.tokenUid, ERROR);
        return this.service.deleteDeliveryToken(this.headers, this.tokenUid, false);
    }

    /**
     * The Delete delivery token request deletes a specific delivery token.
     *
     * @param isForce provide ‘true’ to force delete a delivery token
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-delivery-token">Delete
     * Delivery
     * Token
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete(@NotNull Boolean isForce) {
        return this.service.deleteDeliveryToken(this.headers, this.tokenUid, isForce);
    }

}
