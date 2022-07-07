package com.contentstack.cms.stack;

import com.contentstack.cms.core.CMSRuntimeException;
import com.contentstack.cms.core.Util;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * Extensions let you create custom fields and custom widgets that lets you customize Contentstack default UI and
 * behavior. Read more about Extensions.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides.
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Extensions {

    protected final ExtensionsService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    protected Extensions(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(ExtensionsService.class);
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
     * The Get all custom fields request is used to get the information of all custom fields created in a stack.
     *
     * <dl>
     *   <dt>query</dt>
     *   <dd>For custom fields  <b>Example:"type":"field"</b></dd>
     *   <dt>include_branch</dt>
     *   <dd>Set this to 'true' to include the '_branch' top-level key in the response. This key states the unique ID
     *      of the branch where the concerned Contentstack module resides</dd>
     * </dl>
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-custom-fields">Get all
     * Custom Field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        return this.service.getAll(this.headers, this.params);
    }


    /**
     * @param customFieldUid
     *         Enter the UID of the custom field of which you want to retrieve the details.<br>
     *          <dl>
     *            <dt>include_branch</dt>
     *            <dd>Set this to 'true' to include the '_branch' top-level key in the response.
     *            This key states the unique ID of the branch where the concerned Contentstack module resides.</dd>
     *         </dl>
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-custom-field">Get a
     * Single Custom Field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> single(@NotNull String customFieldUid) {
        this.headers.put(Util.CONTENT_TYPE, "multipart/form-data");
        return this.service.getSingle(this.headers, customFieldUid, this.params);
    }

    /**
     * The Upload a custom field request is used to upload a custom field to Contentstack.
     * <p>
     * - extension[upload]: Select the HTML file of the custom field that you want to upload<br> - extension[title]:
     * Enter the title of the custom field that you want to upload<br> - extension[data_type]: Enter the data type for
     * the input field of the custom field<br> - extension[tags]: Enter the tags that you want to assign to the custom
     * field<br> - extension[multiple]: Enter ‘true’ if you want your custom field to store multiple values<br> -
     * extension[type]: Enter type as ‘field’, since this is a custom field extension.<br>
     * <br><br>
     * <p>
     * {@link #addParam(String, Object)} Set this to 'true' to include the '_branch' top-level key in the response. This
     * key states the unique ID of the branch where the concerned Contentstack module resides.
     * <p>
     * <b>Example:false</b>
     *
     * @param body
     *         the request body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#upload-a-custom-field">Upload a
     * custom field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> uploadCustomField(Map<String, RequestBody> body) {
        this.headers.put(Util.CONTENT_TYPE, "multipart/form-data");
        return this.service.uploadCustomField(this.headers, body, this.params);
    }

    /**
     * The Upload a custom field request is used to upload a custom field to Contentstack.
     * <p>
     * - extension[upload]: Select the HTML file of the custom field that you want to upload<br> - extension[title]:
     * Enter the title of the custom field that you want to upload<br> - extension[data_type]: Enter the data type for
     * the input field of the custom field<br> - extension[tags]: Enter the tags that you want to assign to the custom
     * field<br> - extension[multiple]: Enter ‘true’ if you want your custom field to store multiple values<br> -
     * extension[type]: Enter type as ‘field’, since this is a custom field extension.<br>
     * <p>
     * {@link #addParam(String, Object)} Set this to 'true' to include the '_branch' top-level key in the response. This
     * key states the unique ID of the branch where the concerned Contentstack module resides.
     * <p>
     * <b>Example:false</b>
     *
     * @param body
     *         the request body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#upload-a-custom-field">Upload a
     * custom field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> uploadCustomField(JSONObject body) {
        this.headers.put(Util.CONTENT_TYPE, "application/json");
        return this.service.uploadCustomField(this.headers, this.params, body);
    }


    /**
     * @param customFieldUid
     *         The UID of the custom field that you want to update {@link #addParam(String, Object)} Set this to 'true'
     *         to include the '_branch' top-level key in the response. This key states the unique ID of the branch where
     *         the concerned Contentstack module resides.
     *         <br>
     *         <b>Example:false</b>
     * @param body
     *         JSON requestBody
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-a-custom-field">Update a
     * custom field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull String customFieldUid, JSONObject body) {
        if (body == null) {
            try {
                throw new CMSRuntimeException("body can not be Null");
            } catch (CMSRuntimeException e) {
                throw new IllegalArgumentException(e.getLocalizedMessage());
            }
        }
        return this.service.update(this.headers, customFieldUid, this.params, body);
    }


    /**
     * Delete custom field request is used to delete a specific custom field
     *
     * @param customFieldUid
     *         UID of the custom field that you want to update
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-custom-field">Delete a
     * custom field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String customFieldUid) {
        return this.service.delete(this.headers, customFieldUid);
    }
}
