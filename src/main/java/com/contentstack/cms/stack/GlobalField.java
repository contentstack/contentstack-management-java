package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * A Global field is a reusable field (or group of fields) that you can define
 * once and reuse in any content type within
 * your stack. This eliminates the need (and thereby time and efforts) to create
 * the same set of fields repeatedly in
 * multiple content types.
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
public class GlobalField {

    /**
     * The Service.
     */
    protected final GlobalFieldService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    protected String globalFiledUid;

    protected GlobalField(Retrofit retrofit) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = retrofit.create(GlobalFieldService.class);
    }

    protected GlobalField(Retrofit retrofit, String uid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.globalFiledUid = uid;
        this.service = retrofit.create(GlobalFieldService.class);
    }

    void validate() {
        if (this.globalFiledUid == null)
            throw new IllegalStateException("Global Field Uid can not be null or empty");
    }

    /**
     * Sets header for the request
     *
     * @param key
     *              header key for the request
     * @param value
     *              header value for the request
     */
    public void addHeader(@NotNull String key, @NotNull Object value) {
        this.headers.put(key, value);
    }

    /**
     * Sets header for the request
     *
     * @param key
     *              query param key for the request
     * @param value
     *              query param value for the request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
    }

    /**
     * Set header for the request
     *
     * @param key
     *            Removes query param using key of request
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
     * The Get <b>All global fields</b> call returns comprehensive information of
     * all the global fields available in a
     * particular stack in your account
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication.
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#environment-collection">Get
     *      all
     *      environments
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * <b>Get Single Global Field:</b>
     * <p>
     * The Get a <b>Single global field</b> request allows you to fetch
     * comprehensive details of a specific global
     * field.
     * <p>
     * When executing the API call, in the <b>URI Parameters</b> section, provide
     * the unique ID of your global field.
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication.
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-global-field">Get
     *      a
     *      single global field
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.single(this.headers, this.globalFiledUid, this.params);
    }

    /**
     * <b>Create Global Field</b>
     * <p>
     * The <b>Create a global field</b> request allows you to create a new global
     * field in a particular stack of your
     * Contentstack account. You can use this global field in any content type
     * within your stack.
     * <p>
     * <b>Note:</b> Only the stack owner, administrator and developer can create
     * global fields
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication.
     *
     * @param requestBody
     *                    the request body
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-global-field">Create
     *      a global
     *      field
     *
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.create(this.headers, requestBody);
    }

    /**
     * <b>Update a global field </b>
     * <p>
     * The <b>Update a global field</b> request allows you to update the schema of
     * an existing global field.
     * <p>
     * When executing the API call, in the <b>URI Parameters</b> section, provide
     * the unique ID of your global field.
     *
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication.
     *
     * @param requestBody
     *                    the request body
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-a-global-field">Update
     *      a global
     *      field
     *
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        validate();
        return this.service.update(this.headers, this.globalFiledUid, requestBody);
    }

    /**
     * <b>Delete global field</b>
     * <p>
     * The Delete global field request allows you to delete a specific global field.
     * <p>
     * <b>Warning:</b> If your Global field has been referred within a particular
     * content type, then you will need to pass an additional query parameter
     * force:true to delete the Global field.
     * <p>
     * <b>here</b>: force:true is already applied in this request call
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-global-field">Delete
     *      global
     *      field
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.delete(this.headers, this.globalFiledUid);
    }

    /**
     * <b>Import a global field</b>
     * The <br>
     * Import global field <br>
     * call imports global fields into <b>Stack</b>
     * <br>
     * <b>[Note]</b>
     * <p>
     * You need to use either the stack's Management Token or the user Authtoken
     * (any one is mandatory), along with the
     * stack API key, to make a valid Content Management API request.
     *
     * @param body
     *             The request body
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#import-a-global-field">Import
     *      a global
     *      field
     *
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> imports(@NotNull JSONObject body) {
        return this.service.imports(this.headers, body);
    }

    /**
     * <b>Export global field</b>
     * <p>
     * This request is used to export a specific global field and it's schema, The
     * data is exported in JSON format
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#export-a-global-field">Export
     *      a global
     *      field
     *
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> export() {
        validate();
        return this.service.export(this.headers, this.globalFiledUid);
    }
}
