package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * Labels allow you to group a collection of content within a stack. Using
 * labels you can group content types that need
 * to work together. Read more about Labels.
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
public class Label implements BaseImplementation<Label> {

    protected final LabelService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    protected String labelUid;

    protected Label(Retrofit retrofit,Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = retrofit.create(LabelService.class);
    }

    protected Label(Retrofit retrofit,Map<String, Object> headers, String labelUid) {
        this.labelUid = labelUid;
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = retrofit.create(LabelService.class);
    }

    void validate() {
        if (this.labelUid == null || this.labelUid.isEmpty())
            throw new IllegalAccessError("Label Uid can not be null or empty");
    }

    @Override
    public Label addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }


    /**
     *
     * @param key   The key parameter is a string that represents the name or
     *              identifier of the header.
     *              It is used to specify the type of information being sent in the
     *              header.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return instance of the  Label
     */
    @Override
    public Label addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     *
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of the  Label
     */
    @Override
    public Label addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of the  Label
     */
    @Override
    public Label addHeaders(@NotNull HashMap<String, String> headers) {
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
     * Enter your branch unique ID
     *
     * @param value branch unique ID
     * @return Label
     */
    public Label addBranch(@NotNull String value) {
        this.headers.put("branch", value);
        return this;
    }

    /**
     * This call fetches all the existing labels of the stack.
     * <p>
     * When executing the API call, under the <b>Header</b> section, you need to
     * enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * Using {@link #addParam(String, Object)} you can add queries to extend the
     * functionality of this API call. Under
     * the URI Parameters section, insert a parameter named query and provide a
     * query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the Query section of the Content
     * Delivery API doc.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-labels">Get
     * all
     * labels
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.get(this.headers, this.params);
    }

    /**
     * The Get label call returns information about a particular label of a stack.
     * <p>
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-label">Get
     * a single label
     *
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.get(this.headers, this.labelUid, this.params);
    }

    /**
     * This call is used to create a label.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter
     * the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * In the 'Body' section, enter the label details, such as the name of the
     * label, the uid of the parent label, and
     * the content types that need to be included in the label. These details need
     * to be provided in JSON format.
     * <p>
     *
     * @param requestBody JSONObject request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#add-label">Add
     * label
     *
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.add(this.headers, requestBody);
    }

    /**
     * The Update label call is used to update an existing label.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter
     * the authtoken that you receive after
     * logging into your account.
     * <p>
     * In the 'Body' section, enter the updated details of your label, which include
     * the name of the label, the uid of
     * the parent label, and the content types that need to be included in the
     * label. These details need to be provided
     * in JSON format
     * <p>
     *
     * @param body the request body to update the {@link Label}
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-label">Update
     * label
     *
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject body) {
        validate();
        return this.service.update(this.headers, this.labelUid, body);
    }

    /**
     * Delete label call is used to delete a specific label.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter
     * the authtoken that you receive after
     * logging into your account
     * <p>
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-label">Delete
     * label
     *
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.delete(this.headers, this.labelUid);
    }

}
