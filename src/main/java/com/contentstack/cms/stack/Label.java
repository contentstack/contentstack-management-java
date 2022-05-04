package com.contentstack.cms.stack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

public class Label {

    protected final LabelService service;
    protected HashMap<String, Object> headers;

    protected Label(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(LabelService.class);
    }

    /**
     * @param key
     *         header key
     * @param value
     *         header value
     * @return Label
     */
    public Label addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
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
     * You can add queries to extend the functionality of this API call. Under the URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the <b>Queries</b> section of the Content Delivery API doc.
     *
     * @return Call
     */
    public Call<ResponseBody> get() {
        return this.service.get(this.headers);
    }


    /**
     * This call fetches all the existing labels of the stack.
     * <p>
     * When executing the API call, under the <b>Header</b> section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * You can add queries to extend the functionality of this API call. Under the URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the Query section of the Content Delivery API doc.
     *
     * @return Call
     */
    public Call<ResponseBody> get(@NotNull Map<String, Object> jsonRequest) {
        return this.service.get(this.headers, jsonRequest);
    }

    /**
     * The Get label call returns information about a particular label of a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the authtoken that you receive after
     * logging into your account.
     * <p>
     *
     * @param labelUid
     *         Provide the unique ID of the label that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> get(@NotNull String labelUid) {
        return this.service.get(this.headers, labelUid);
    }

    public Call<ResponseBody> get(String uid, Map<String, Object> queryParams) {
        return this.service.get(this.headers, uid, queryParams);
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
     */
    public Call<ResponseBody> add(@NotNull JSONObject requestBody) {
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
     *         the label uid
     * @return Call
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
     *         the label uid
     * @return Call
     */
    public Call<ResponseBody> delete(@NotNull String labelUid) {
        return this.service.delete(this.headers, labelUid);
    }


}
