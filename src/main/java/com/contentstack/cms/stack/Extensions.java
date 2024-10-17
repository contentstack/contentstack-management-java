package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import com.contentstack.cms.core.Util;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Extensions let you create custom fields and custom widgets that lets you
 * customize Contentstack default UI and
 * behavior. Read more about Extensions.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage
 * modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter
 * to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the
 * concerned Contentstack module resides.
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-22
 */
public class Extensions implements BaseImplementation<Extensions> {

    protected final ExtensionsService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    protected String customFieldUid;

    protected Extensions(Retrofit retrofit,Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = retrofit.create(ExtensionsService.class);
    }

    protected Extensions(Retrofit retrofit,Map<String, Object> headers, String fieldUid) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.customFieldUid = fieldUid;
        this.service = retrofit.create(ExtensionsService.class);
    }

    void validate() {
        if (this.customFieldUid == null || this.customFieldUid.isEmpty())
            throw new IllegalAccessError("Custom Field UID Can Not Be Null OR Empty");
    }


    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of {@link Extensions}
     */
    @Override
    public Extensions addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of {@link Extensions}
     */
    @Override
    public Extensions addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of {@link Extensions}
     */
    @Override
    public Extensions addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return null;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of {@link Extensions}
     */
    @Override
    public Extensions addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * Set header for the request
     *
     * @param key Removes query param using key of request
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
     * The Get all custom fields request is used to get the information of all
     * custom fields created in a stack.
     *
     * <dl>
     * <dt>query</dt>
     * <dd>For custom fields <b>Example:"type":"field"</b></dd>
     * <dt>include_branch</dt>
     * <dd>Set this to 'true' to include the '_branch' top-level key in the
     * response. This key states the unique ID
     * of the branch where the concerned Contentstack module resides</dd>
     * </dl>
     *
     * @return Call call
     * @see #addHeader(String, String) #addHeader(String, Object)#addHeader(String,
     * Object)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)#addParam(String,
     * Object)to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.getAll(this.headers, this.params);
    }

    /**
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-custom-field">Get
     * a
     * Single Custom Field
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        this.headers.put(Util.CONTENT_TYPE, "multipart/form-data");
        this.validate();
        return this.service.getSingle(this.headers, this.customFieldUid, this.params);
    }

    /**
     * The Upload a custom field request is used to upload a custom field to
     * Contentstack.
     * <p>
     * - extension[upload]: Select the HTML file of the custom field that you want
     * to upload<br>
     * - extension[title]:
     * Enter the title of the custom field that you want to upload<br>
     * - extension[data_type]: Enter the data type for
     * the input field of the custom field<br>
     * - extension[tags]: Enter the tags that you want to assign to the custom
     * field<br>
     * - extension[multiple]: Enter ‘true’ if you want your custom field to store
     * multiple values<br>
     * -
     * extension[type]: Enter type as ‘field’, since this is a custom field
     * extension.<br>
     * <br>
     * <br>
     * <p>
     * {@link #addParam(String, Object)} Set this to 'true' to include the '_branch'
     * top-level key in the response. This
     * key states the unique ID of the branch where the concerned Contentstack
     * module resides.
     * <p>
     * <b>Example:false</b>
     *
     * @param body the request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#upload-a-custom-field">Upload
     * a custom
     * field
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> uploadCustomField(Map<String, RequestBody> body) {
        this.headers.put(Util.CONTENT_TYPE, "multipart/form-data");
        return this.service.uploadCustomField(this.headers, body, this.params);
    }

    /**
     * The Upload a custom field request is used to upload a custom field to
     * Contentstack.
     * <p>
     * - extension[upload]: Select the HTML file of the custom field that you want
     * to upload<br>
     * - extension[title]:
     * Enter the title of the custom field that you want to upload<br>
     * - extension[data_type]: Enter the data type for
     * the input field of the custom field<br>
     * - extension[tags]: Enter the tags that you want to assign to the custom
     * field<br>
     * - extension[multiple]: Enter ‘true’ if you want your custom field to store
     * multiple values<br>
     * -
     * extension[type]: Enter type as ‘field’, since this is a custom field
     * extension.<br>
     * <p>
     * {@link #addParam(String, Object)} Set this to 'true' to include the '_branch'
     * top-level key in the response. This
     * key states the unique ID of the branch where the concerned Contentstack
     * module resides.
     * <p>
     * <b>Example:false</b>
     *
     * @param body the request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#upload-a-custom-field">Upload
     * a custom
     * field
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> uploadCustomFieldUsingJSONObject(JSONObject body) {
        this.headers.put(Util.CONTENT_TYPE, "application/json");
        return this.service.uploadCustomField(this.headers, this.params, body);
    }

    /**
     * @param body JSON requestBody
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-a-custom-field">Update
     * a custom
     * field
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> update(JSONObject body) {
        Objects.requireNonNull(body, "Body Can Not Be Null");
        this.validate();
        return this.service.update(this.headers, this.customFieldUid, this.params, body);
    }

    /**
     * Delete custom field request is used to delete a specific custom field
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-custom-field">Delete
     * a custom
     * field
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        this.validate();
        return this.service.delete(this.headers, this.customFieldUid);
    }
}
