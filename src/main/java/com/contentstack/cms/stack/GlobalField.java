package com.contentstack.cms.stack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;


/**
 * A Global field is a reusable field (or group of fields) that you can define once and reuse in any content type within
 * your stack. This eliminates the need (and thereby time and efforts) to create the same set of fields repeatedly in
 * multiple content types.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides.
 *
 * @author ishaileshmishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class GlobalField {

    /**
     * The Service.
     */
    protected final GlobalFieldService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    /**
     * Instantiates a new Global field.
     *
     * @param retrofit
     *         the retrofit
     * @param stackHeaders
     *         the stack headers
     */
    protected GlobalField(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.params = new HashMap<>();
        this.service = retrofit.create(GlobalFieldService.class);
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
     * <b>Get All Global Fields</b>
     * <p>
     * The Get <b>All global fields</b> call returns comprehensive information of all the global fields available in a
     * particular stack in your account
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#environment-collection">Get all
     * environments
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * <b>Get Single Global Field:</b>
     * <p>
     * The Get a <b>Single global field</b> request allows you to fetch comprehensive details of a specific global
     * field.
     * <p>
     * When executing the API call, in the <b>URI Parameters</b> section, provide the unique ID of your global field.
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param globalFiledUid
     *         The unique ID of the global field that you wish to update. The UID is generated based on the title of the
     *         global field. The unique ID of a global field is unique across a stack
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-global-field">Get a
     * single global field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch(@NotNull String globalFiledUid) {
        return this.service.single(this.headers, globalFiledUid, this.params);
    }

    /**
     * <b>Create Global Field</b>
     * <p>
     * The <b>Create a global field</b> request allows you to create a new global field in a particular stack of your
     * Contentstack account. You can use this global field in any content type within your stack.
     * <p>
     * <b>Note:</b> Only the stack owner, administrator and developer can create
     * global fields
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param requestBody
     *         the request body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-global-field">Create a
     * global field
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.create(this.headers, requestBody);
    }

    /**
     * <b>Update a global field </b>
     * <p>
     * The <b>Update a global field</b> request allows you to update the schema of an existing global field.
     * <p>
     * When executing the API call, in the <b>URI Parameters</b> section, provide the unique ID of your global field.
     *
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param globalFiledUid
     *         The unique ID of the global field that you wish to update. The UID is generated based on the title of the
     *         global field. The unique ID of a global field is unique across a stack
     * @param requestBody
     *         the request body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-a-global-field">Update a
     * global field
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull String globalFiledUid, @NotNull JSONObject requestBody) {
        return this.service.update(this.headers, globalFiledUid, requestBody);
    }

    /**
     * <b>Delete global field</b>
     * <p>
     * The Delete global field request allows you to delete a specific global field.
     * <p>
     * <b>Warning:</b> If your Global field has been referred within a particular
     * content type, then you will need to pass an additional query parameter force:true to delete the Global field.
     * <p>
     * <b>here</b>: force:true is already applied in this request call
     *
     * @param globalFiledUid
     *         The unique ID of the global field that you wish to update. The UID is generated based on the title of the
     *         global field. The unique ID of a global field is unique across a stack
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-global-field">Delete
     * global field
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String globalFiledUid) {
        return this.service.delete(this.headers, globalFiledUid);
    }

    /**
     * <b>Import a global field</b>
     * The <br> Import global field <br> call imports global fields into <b>Stack</b>
     * <br>
     * <b>[Note]</b>
     * <p>
     * You need to use either the stack's Management Token or the user Authtoken (any one is mandatory), along with the
     * stack API key, to make a valid Content Management API request.
     *
     * @param jsonGlobalField
     *         The Json object to pass as body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#import-a-global-field">Import a
     * global field
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> imports(@NotNull Map<String, Object> jsonGlobalField) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("global_field", jsonGlobalField);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        return this.service.imports(this.headers, body);
    }

    /**
     * <b>Export global field</b>
     * <p>
     * This request is used to export a specific global field and it's schema, The data is exported in JSON format
     *
     * @param globalFiledUid
     *         The unique ID of the global field that you wish to update. The UID is generated based on the title of the
     *         global field. The unique ID of a global field is unique across a stack
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#export-a-global-field">Export a
     * global field
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> export(@NotNull String globalFiledUid) {
        return this.service.export(this.headers, globalFiledUid);
    }
}
