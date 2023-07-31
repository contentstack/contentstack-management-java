package com.contentstack.cms.stack;

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
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-22
 */
public class Extensions {

    protected final ExtensionsService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    protected String customFieldUid;

    protected Extensions(Retrofit retrofit) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = retrofit.create(ExtensionsService.class);
    }

    protected Extensions(Retrofit retrofit, String fieldUid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.customFieldUid = fieldUid;
        this.service = retrofit.create(ExtensionsService.class);
    }

    void validate() {
        if (this.customFieldUid == null || this.customFieldUid.isEmpty())
            throw new IllegalAccessError("Custom Field UID Can Not Be Null OR Empty");
    }

    /**
     * Sets header for the request
     *
     * @param key   header key for the request
     * @param value header value for the request
     */
    public void addHeader(@NotNull String key, @NotNull Object value) {
        this.headers.put(key, value);
    }

    /**
     * Sets header for the request
     *
     * @param key   query param key for the request
     * @param value query param value for the request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
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
     * @see #addHeader(String, Object) #addHeader(String, Object)#addHeader(String,
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
     * @see #addHeader(String, Object) to add headers
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
     * @param body the request body of type Map<String, RequestBody> body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#upload-a-custom-field">Upload
     * a custom
     * field
     * </a>
     * @see #addHeader(String, Object) to add headers
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
     * @see #addHeader(String, Object) to add headers
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
     * @see #addHeader(String, Object) to add headers
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
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        this.validate();
        return this.service.delete(this.headers, this.customFieldUid);
    }
}
