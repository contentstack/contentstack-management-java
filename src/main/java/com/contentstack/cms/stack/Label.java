package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * Labels allow you to group a collection of content within a stack. Using labels you can group content types that need
 * to work together. Read more about Labels.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides.
 *
 * @author Shailesh Mishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Label {

    protected final LabelService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    protected Label(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(LabelService.class);
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
     * Enter your branch unique ID
     *
     * @param value
     *         branch unique ID
     * @return Label
     */
    public Label addBranch(@NotNull String value) {
        this.headers.put("branch", value);
        return this;
    }


    /**
     * This call fetches all the existing labels of the stack.
     * <p>
     * When executing the API call, under the <b>Header</b> section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * Using {@link #addParam(String, Object)} you can add queries to extend the functionality of this API call. Under
     * the URI Parameters section, insert a parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the Query section of the Content Delivery API doc.
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-labels">Get all
     * labels
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        return this.service.get(this.headers, this.params);
    }

    /**
     * The Get label call returns information about a particular label of a stack.
     * <p>
     *
     * @param labelUid
     *         Provide the unique ID of the label that you want to retrieve
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-label">Get a
     * single label
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> single(@NotNull String labelUid) {
        return this.service.get(this.headers, labelUid, this.params);
    }


    /**
     * This call is used to create a label.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * In the 'Body' section, enter the label details, such as the name of the label, the uid of the parent label, and
     * the content types that need to be included in the label. These details need to be provided in JSON format.
     * <p>
     *
     * @param requestBody
     *         JSONObject request body
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#add-label">Add label
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.add(this.headers, requestBody);
    }

    /**
     * The Update label call is used to update an existing label.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the authtoken that you receive after
     * logging into your account.
     * <p>
     * In the 'Body' section, enter the updated details of your label, which include the name of the label, the uid of
     * the parent label, and the content types that need to be included in the label. These details need to be provided
     * in JSON format
     * <p>
     *
     * @param labelUid
     *         The unique ID of the label that you want to retrieve.
     * @param body
     *         the request body to update the {@link Label}
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-label">Update
     * label
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull String labelUid, @NotNull JSONObject body) {
        return this.service.update(this.headers, labelUid, body);
    }

    /**
     * Delete label call is used to delete a specific label.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the authtoken that you receive after
     * logging into your account
     * <p>
     *
     * @param labelUid
     *         The unique ID of the label that you want to retrieve.
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-label">Delete
     * label
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String labelUid) {
        return this.service.delete(this.headers, labelUid);
    }


}
