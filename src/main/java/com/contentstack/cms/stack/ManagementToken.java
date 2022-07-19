package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;

import java.util.HashMap;


/**
 * <b>Management tokens: </b> <br>To authenticate Content Management API (CMA) requests over your stack content, you
 * can use Management Tokens
 * <br>
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class ManagementToken {

    protected final TokenService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private String tokenUid;

    protected ManagementToken(TokenService service) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = service;
    }

    protected ManagementToken(TokenService service, String tokenUid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = service;
        this.tokenUid = tokenUid;
    }


    void validate() {
        if (this.tokenUid == null || this.tokenUid.isEmpty())
            throw new IllegalStateException("Token uid can not be null or empty");
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
     * The Get all management tokens request returns the details of all the management tokens generated in a stack and
     * NOT the actual management tokens.
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-management-tokens">Get
     * all Management Tokens
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetchManagementToken(this.headers, this.params);
    }

    /**
     * The Get a single management token request returns the details of a specific management token generated in a stack
     * and NOT the actual management token.
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-management-token">Get
     * a single management token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.getSingleManagementToken(this.headers, this.tokenUid);
    }

    /**
     * The Create management token request is used to create a management token in a stack. This token provides you with
     * read-write access to the content of your stack.
     *
     * @param requestBody
     *         details of the management token in @{@link JSONObject} format
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-management-token">Create a
     * management token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
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
     * @param requestBody
     *         details of the management token in @{@link JSONObject} format
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-management-token">Update
     * management token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        validate();
        return this.service.updateManagementToken(this.headers, this.tokenUid, requestBody);
    }

    /**
     * The Delete management token request deletes a specific management token
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-management-token">Delete
     * management token
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.deleteManagementToken(this.headers, this.tokenUid);
    }

}
