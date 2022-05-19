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
 * Extensions let you create custom fields and custom widgets that lets you customize Contentstack's default UI and
 * behavior. Read more about Extensions.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides.
 *
 * @author Shailesh Mishra
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

    public Extensions addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    public Extensions addParam(@NotNull String key, @NotNull String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * The Get all custom fields request is used to get the information of all custom fields created in a stack.
     *
     * @param query
     *         For custom fields  <b>Example:"type":"field"</b>
     * @param isIncludeBranch
     *         Set this to 'true' to include the '_branch' top-level key in the response. This key states the unique ID
     *         of the branch where the concerned Contentstack module resides.
     *         <b>Example:false</b>
     * @return @{@link Call}
     */
    public Call<ResponseBody> getAll(@NotNull String query, boolean isIncludeBranch) {
        return this.service.getAll(this.headers, query, isIncludeBranch);
    }


    /**
     * @param customFieldUid
     *         Enter the UID of the custom field of which you want to retrieve the details.
     * @param queryParam
     *         Set this to 'true' to include the '_branch' top-level key in the response. This key states the unique ID
     *         of the branch where the concerned Contentstack module resides.
     *         <p>
     *         <b>Example:false</b>
     * @return Call
     */
    public Call<ResponseBody> get(@NotNull String customFieldUid, Map<String, Object> queryParam) {
        if (queryParam == null) {
            queryParam = new HashMap<>();
            this.headers.put(Util.CONTENT_TYPE, "multipart/form-data");
        }
        return this.service.getSingle(this.headers, customFieldUid, queryParam);
    }

    /**
     * The Upload a custom field request is used to upload a custom field to Contentstack.
     * <p>
     * - extension[upload]: Select the HTML file of the custom field that you want to upload<br> - extension[title]:
     * Enter the title of the custom field that you want to upload<br> - extension[data_type]: Enter the data type for
     * the input field of the custom field<br> - extension[tags]: Enter the tags that you want to assign to the custom
     * field<br> - extension[multiple]: Enter ‘true’ if you want your custom field to store multiple values<br> -
     * extension[type]: Enter type as ‘field’, since this is a custom field extension.<br>
     *
     * @param queryParam
     *         Set this to 'true' to include the '_branch' top-level key in the response. This key states the unique ID
     *         of the branch where the concerned Contentstack module resides.
     *         <p>
     *         <b>Example:false</b>
     * @param body
     *         In the ‘Body’ section, you need to provide the following ‘Body’ parameters under ‘form-data’
     *         <pre>
     *                                         Use like: Map<String, RequestBody> params = new HashMap<>();
     *                                         prepare RequestBody RequestBody someDataBody = ....;
     *                                         add it Map object params.put("DYNAMIC_PARAM_NAME", someDataBody);
     *                                         </pre>
     * @return Call
     */
    public Call<ResponseBody> uploadCustomField(Map<String, RequestBody> body, Map<String, Object> queryParam) {
        if (queryParam == null) {
            queryParam = new HashMap<>();
        }
        this.headers.put(Util.CONTENT_TYPE, "multipart/form-data");
        return this.service.uploadCustomField(this.headers, body, queryParam);
    }

    /**
     * The Upload a custom field request is used to upload a custom field to Contentstack.
     * <p>
     * - extension[upload]: Select the HTML file of the custom field that you want to upload<br> - extension[title]:
     * Enter the title of the custom field that you want to upload<br> - extension[data_type]: Enter the data type for
     * the input field of the custom field<br> - extension[tags]: Enter the tags that you want to assign to the custom
     * field<br> - extension[multiple]: Enter ‘true’ if you want your custom field to store multiple values<br> -
     * extension[type]: Enter type as ‘field’, since this is a custom field extension.<br>
     *
     * @param queryParam
     *         Set this to 'true' to include the '_branch' top-level key in the response. This key states the unique ID
     *         of the branch where the concerned Contentstack module resides.
     *         <p>
     *         <b>Example:false</b>
     * @param body
     *         In the ‘Body’ section, you need to provide the following ‘Body’ parameters under ‘form-data’
     *         <pre>
     *                                                                                                                                                                                 Use like: Map<String, RequestBody> params = new HashMap<>();
     *                                                                                                                                                                                 //prepare RequestBody RequestBody someDataBody = ....;
     *                                                                                                                                                                                 //add it Map object params.put("DYNAMIC_PARAM_NAME", someDataBody);
     *                                                                                                                                                                          </pre>
     * @return Call
     */
    public Call<ResponseBody> uploadCustomField(Map<String, Object> queryParam, JSONObject body) {
        if (queryParam != null) {
            queryParam = new HashMap<>();
            if (!this.headers.containsKey(Util.CONTENT_TYPE)) {
                this.headers.put(Util.CONTENT_TYPE, "application/json");
            }
        }
        return this.service.uploadCustomField(this.headers, queryParam, body);
    }


    /**
     * @param customFieldUid
     *         The UID of the custom field that you want to update
     * @param queryParam
     *         Set this to 'true' to include the '_branch' top-level key in the response. This key states the unique ID
     *         of the branch where the concerned Contentstack module resides.
     *         <br>
     *         <b>Example:false</b>
     * @param body
     *         JSON requestBody
     * @return Call
     */
    public Call<ResponseBody> update(@NotNull String customFieldUid, Map<String, Object> queryParam, JSONObject body) {
        if (body == null) {
            try {
                throw new CMSRuntimeException("body can not be Null");
            } catch (CMSRuntimeException e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
        }
        if (queryParam == null) {
            queryParam = new HashMap<>();
        }
        return this.service.update(this.headers, customFieldUid, queryParam, body);
    }


    /**
     * Delete custom field request is used to delete a specific custom field
     *
     * @param customFieldUid
     *         UID of the custom field that you want to update
     * @return Call
     */
    public Call<ResponseBody> delete(@NotNull String customFieldUid) {
        return this.service.delete(this.headers, customFieldUid);
    }
}
