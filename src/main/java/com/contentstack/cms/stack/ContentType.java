package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * Content type defines the structure or schema of a page or a section of your web or mobile property. To create content
 * for your application, you are required to first create a content type, and then create entries using the content
 * type.
 * <br><br>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides.
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#content-types">Content
 * Types</a>
 */
public class ContentType {

    protected final ContentTypeService service;
    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected String contentTypeUid;
    protected Retrofit instance;

    /**
     * Instantiates a new Content type.
     *
     * @param instance
     *         the {@link Retrofit} instance
     * @param stackHeaders
     *         the headers
     */
    protected ContentType(@NotNull Retrofit instance, @NotNull Map<String, Object> stackHeaders) {
        this.instance = instance;
        this.headers = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.params = new HashMap<>();
        this.service = instance.create(ContentTypeService.class);
    }

    /**
     * Instantiates a new Content type.
     *
     * @param instance
     *         the {@link Retrofit} instance
     * @param headers
     *         headers for content type
     * @param contentTypeUid
     *         the contentTypeUid
     */
    public ContentType(@NotNull Retrofit instance, @NotNull Map<String, Object> headers, String contentTypeUid) {
        this.instance = instance;
        this.headers = new HashMap<>();
        this.contentTypeUid = contentTypeUid;
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = instance.create(ContentTypeService.class);
    }

    public Entry entry() {
        if (this.contentTypeUid == null) {
            throw new NullPointerException("Please provide content type uid");
        }
        return new Entry(this.instance, this.headers, this.contentTypeUid);
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
     *         key of query parameters of request
     * @param value
     *         value of query parameters of request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
    }


    /**
     * Sets header for the request
     *
     * @param key
     *         Remove query parameters of request by key
     */
    public void removeParam(@NotNull String key) {
        this.params.remove(key);
    }


    /**
     * To clear all the query parameters of request
     */
    protected void clearParams() {
        this.params.clear();
    }

    /**
     * <b>Fetch call.</b>
     * The Get all content types call returns comprehensive information of all the content types available in a
     * particular stack in your account.
     * <p>
     * <b>Note:</b>
     * <p>
     * You need to use either the stack's Management Token or the user Authtoken (any one is mandatory), along with the
     * stack API key, to make a valid Content Management API request.
     * <br>
     * <br>
     * Read more about <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-content-types">Content
     * Types</a>
     *
     * @return the call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-content-types">
     * Get all content types</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> find() {
        return service.fetch(this.headers, this.params);
    }

    /**
     * <b>Get Single Content Type</b>
     * <p>
     * The Get a single content type call returns information of a specific content type.
     * <p>
     * <b>Note:</b>
     * You need to use either the stack's Management Token or the user Authtoken (any one is mandatory), along with the
     * stack API key, to make a valid Content Management API request. Read more about authentication.
     * <p>
     * Enter the version of the content type of which you want to retrieve the details as a query parameter. If no
     * version is specified, you will get the latest version of the content type.
     * <p>
     * <b>Note:</b>
     * <p>
     * The schema of the content type returned will depend on the provided version. If no version is specified, you will
     * get the latest version of the content type.
     * <br>
     * <p>
     * <b>Example: </b>product
     * #addParam queryParam Query Parameters <b>include_global_field_schema</b>
     * <p>
     * the query param Tip: If any of your content types contains a Global field, and you wish to fetch the content
     * schema of the Global field, then you need to pass the include_global_field_schema:true parameter. This parameter
     * helps return the Global field's schema along with the content type schema.
     * <p>
     * <b>version</b>
     * <p>
     * version of the content type of which you want to retrieve the details. If no version is specified, you will get
     * the latest version of the content type.
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-content-type"> Get a
     * single content type</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return service.single(this.headers, this.contentTypeUid, this.params);
    }

    private void validate() {
        if (this.contentTypeUid == null) {
            throw new IllegalArgumentException("contentTypeUid is required");
        }
    }

    /**
     * <b>Create Content Type</b>
     * <p>
     * The <b>Create a content type</b> call creates a new content type in a particular stack of your Contentstack
     * account
     * <p>
     * In the <b>Body</b> section, you need to provide the complete schema of the content type. You can refer the
     * <p>
     * See <a
     * href=https://www.contentstack.com/docs/developers/create-content-types/json-schema-for-creating-a-content-type>JSON
     * schema for creating a content type</a>
     *
     * <p>
     * To mark a field as non-unique, you need to set the unique parameter to false. For example, to remove the unique
     * constraint on the default <b>title</b> field, you need to update the JSON schema of the title field as follows:
     * <p>
     * <a href=https://www.contentstack.com/docs/developers/apis/content-management-api/#create-content-type>Read
     * more</a>
     *
     * <b> Example: </b>
     *
     * <pre>
     *     String bodyStr = "";
     *     <br>
     *     {@link Call} response = contentType.create(managementToken, bodyStr).execute();
     * </pre>
     *
     * @param requestBody
     *         the request body
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-content-type">
     * Create A Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> create(JSONObject requestBody) {
        return service.create(this.headers, this.params, requestBody);
    }

    /**
     * <b>Update Content Type</b>
     * <p>
     * The <b>Update Content Type</b> call is used to update the schema of an existing content type.
     * <p>
     * <b>Note:</b> Whenever you update a content type, it will auto-increment the
     * content type version.
     * <p>
     * <a href=https://www.contentstack.com/docs/developers/apis/content-management-api/#update-content-type>Read
     * more</a>
     *
     * @param requestBody
     *         the request body
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-content-type">
     * Update Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> update(JSONObject requestBody) {
        validate();
        return service.update(this.contentTypeUid, this.headers, this.params, requestBody);
    }

    /**
     * <b>Field visibility rule.</b>
     * <p>
     * The Set Field Visibility Rule for Content Type API request lets you add Field Visibility Rules to existing
     * content types. These rules allow you to show and hide fields based on the state or value of certain fields.
     *
     * <p>
     * <a href=https://www.contentstack.com/docs/developers/apis/content-management-api/#create-content-type>Field
     * Visibility Rules</a> can be set while creating your content type (via UI, only after you've added all the
     * required fields to the content type and saved it) or while editing a content type (both via UI and API).
     *
     * @param requestBody
     *         the request body JSONBody
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#set-field-visibility-rule-for-content-type">
     * Set Field Visibility Rule for Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> fieldVisibilityRule(JSONObject requestBody) {
        validate();
        return service.visibilityRule(this.contentTypeUid, this.headers, this.params, requestBody);
    }


    /**
     * <b>Delete Content Type</b>.
     *
     * <p>
     * To Delete Content Type call deletes an existing content type and all the entries within it. When executing the
     * API call, in the <b>URI Parameters</b> section, provide the UID of your content type
     * <b>Note:</b>
     * Note: You need to use either the stack's Management Token or the user Authtoken (anyone is mandatory), along with
     * the stack API key, to make a valid Content Management API request. Read more about Authentication.
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-content-type">
     * Delete Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return service.delete(this.contentTypeUid, this.headers, this.params);
    }

    /**
     * <b>Content Type References.</b>
     * <p>
     * The Get all references of content type call will fetch all the content types in which a specified content type is
     * referenced.
     *
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory),along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication. You need to use either the stack's Management Token or the user Authtoken (any
     * one is mandatory), along with the stack API key, to make a valid Content Management API request. Read more about
     * authentication.
     *
     * @param isIncludeGlobalField
     *         Include Global Field true/false
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-references-of-content-type">
     * Get All References Of Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> reference( Boolean isIncludeGlobalField) {
        validate();
        return service.reference(this.contentTypeUid, this.headers, isIncludeGlobalField);
    }

    /**
     * <b>Content Type References</b>.
     * <p>
     * The Get all references of content type call will fetch all the content types in which a specified content type is
     * referenced.
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory),along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication. You need to use either the stack's Management Token or the user Authtoken (anyone
     * is mandatory), along with the stack API key, to make a valid Content Management API request. Read more about
     * authentication.
     *
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-references-of-content-type">
     * Get All References Of Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> referenceIncludeGlobalField() {
        validate();
        return service.referenceIncludeGlobalField(this.contentTypeUid, this.headers);
    }

    /**
     * <b>Export Content Type</b>.
     * <p>
     * This call is used to export a specific content type and its schema. The data is exported in JSON format. However,
     * please note that the entries of the specified content type are not exported through this call. The schema of the
     * content type returned will depend on the version number provided.
     *
     *
     * <b>Note: </b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory), along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#export-a-content-type">
     * Export A Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> export() {
        validate();
        return service.export(this.contentTypeUid, this.headers);
    }

    /**
     * <b>Export Content Type</b>.
     * <p>
     * This call is used to export a specific content type and its schema. The data is exported in JSON format. However,
     * please note that the entries of the specified content type are not exported through this call. The schema of the
     * content type returned will depend on the version number provided.
     *
     *
     * <b>Note: </b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory), along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param version
     *         The version of content type you want to retrieve. If no version is specified, you will get the latest
     *         version of the content type
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#export-a-content-type">
     * Export A Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> export( int version) {
        validate();
        return service.export(this.contentTypeUid, this.headers, version);
    }

    /**
     * <b>Import content type</b>
     * <br>
     * The Import a content type call imports a content type into a stack by uploading JSON file.
     * <br>
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#import-a-content-type">
     * Import A Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> imports() {
        return service.imports(this.headers, this.params);
    }

    /**
     * <b>Import content type</b>
     * <p>
     * The Import a content type call imports a content type into a stack by uploading JSON file.
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#import-a-content-type">
     * Import A Content Type</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> importOverwrite() {
        this.headers.put("Content-Type", "multipart/form-data");
        return service.importOverwrite(this.headers);
    }
}
