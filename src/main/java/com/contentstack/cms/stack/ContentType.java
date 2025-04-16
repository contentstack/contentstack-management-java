package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Content type defines the structure or schema of a page or a section of your
 * web or mobile property. To create content
 * for your application, you are required to first create a content type, and
 * then create entries using the content
 * type.
 * <br>
 * <br>
 * You can now pass the branch header in the API request to fetch or manage
 * modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter
 * to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the
 * concerned Contentstack module resides.
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @see <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#content-types">Content
 * Types</a>
 */
public class ContentType implements BaseImplementation<ContentType> {

    protected final ContentTypeService service;
    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected String contentTypeUid;
    protected Retrofit instance;

    /**
     * Instantiates a new Content type.
     *
     * @param instance the {@link Retrofit} instance
     * @param headers  the headers
     */
    protected ContentType(@NotNull Retrofit instance, Map<String, Object> headers) {
        this.instance = instance;
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = instance.create(ContentTypeService.class);
    }

    /**
     * Instantiates a new Content type.
     *
     * @param instance the {@link Retrofit} instance
     * @param headers  the headers
     * @param uid      the contentTypeUid
     */
    public ContentType(@NotNull Retrofit instance, Map<String, Object> headers, String uid) {
        this.instance = instance;
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.contentTypeUid = uid;
        this.params = new HashMap<>();
        this.service = instance.create(ContentTypeService.class);
    }


    //----------------------------------------------------------------
    // GETTERS
    //----------------------------------------------------------------

    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of ContentType
     */
    @Override
    public ContentType addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of ContentType
     */
    @Override
    public ContentType addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of ContentType
     */
    @Override
    public ContentType addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of ContentType
     */
    @Override
    public ContentType addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * Sets header for the request
     *
     * @param key remove query parameters of request by key
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
     * An entry is the actual piece of content created using one of the defined
     *
     * @return Entry
     */
    public Entry entry() {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return new Entry(this.instance, this.headers, this.contentTypeUid);
    }

    /**
     * An entry is the actual piece of content created using one of the defined
     *
     * @param entryUid The entry uid
     * @return Entry
     */
    public Entry entry(@NotNull String entryUid) {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return new Entry(this.instance, this.headers, this.contentTypeUid, entryUid);
    }

    /**
     * <b>Fetch call.</b>
     * The Get all content types call returns comprehensive information of all the
     * content types available in a
     * particular stack in your account.
     * <p>
     * <b>Note:</b>
     * <p>
     * You need to use either the stack's Management Token or the user Authtoken
     * (any one is mandatory), along with the
     * stack API key, to make a valid Content Management API request.
     * <br>
     * <br>
     * Read more about <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-content-types">Content
     * Types</a>
     *
     * @return the call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-content-types">
     * Get all content types</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return service.fetch(this.headers, this.params);
    }
    
    public Call<ContentTypesResponse> findAsPojo() {
        return service.fetchPojo(this.headers, this.params);
    }

    /**
     * <b>Get Single Content Type</b>
     * <p>
     * The Get a single content type call returns information of a specific content
     * type.
     * <p>
     * <b>Note:</b>
     * You need to use either the stack's Management Token or the user Authtoken
     * (any one is mandatory), along with the
     * stack API key, to make a valid Content Management API request. Read more
     * about authentication.
     * <p>
     * Enter the version of the content type of which you want to retrieve the
     * details as a query parameter. If no
     * version is specified, you will get the latest version of the content type.
     * <p>
     * <b>Note:</b>
     * <p>
     * The schema of the content type returned will depend on the provided version.
     * If no version is specified, you will
     * get the latest version of the content type.
     * <br>
     * <p>
     * <b>Example: </b>product
     * #addParam queryParam Query Parameters <b>include_global_field_schema</b>
     * <p>
     * the query param Tip: If any of your content types contains a Global field,
     * and you wish to fetch the content
     * schema of the Global field, then you need to pass the
     * include_global_field_schema:true parameter. This parameter
     * helps return the Global field's schema along with the content type schema.
     * <p>
     * <b>version</b>
     * <p>
     * version of the content type of which you want to retrieve the details. If no
     * version is specified, you will get
     * the latest version of the content type.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-content-type">
     * Get a
     * single content type</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.single(this.headers, this.contentTypeUid, this.params);
    }

    public Call<ContentTypeResponse> fetchAsPojo() {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.singlePojo(this.headers, this.contentTypeUid, this.params);
    }

    /**
     * <b>Create Content Type</b>
     * <p>
     * The <b>Create a content type</b> call creates a new content type in a
     * particular stack of your Contentstack
     * account
     * <p>
     * In the <b>Body</b> section, you need to provide the complete schema of the
     * content type. You can refer the
     * <p>
     * See <a
     * href=https://www.contentstack.com/docs/developers/create-content-types/json-schema-for-creating-a-content-type>JSON
     * schema for creating a content type</a>
     *
     * <p>
     * To mark a field as non-unique, you need to set the unique parameter to false.
     * For example, to remove the unique
     * constraint on the default <b>title</b> field, you need to update the JSON
     * schema of the title field as follows:
     * <p>
     * <a
     * href=https://www.contentstack.com/docs/developers/apis/content-management-api/#create-content-type>Read
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
     * @param requestBody the request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-content-type">
     * Create A Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> create(JSONObject requestBody) {
        return service.create(this.headers, this.params, requestBody);
    }

    /**
     * <b>Update Content Type</b>
     * <p>
     * The <b>Update Content Type</b> call is used to update the schema of an
     * existing content type.
     * <p>
     * <b>Note:</b> Whenever you update a content type, it will auto-increment the
     * content type version.
     * <p>
     * <a
     * href=https://www.contentstack.com/docs/developers/apis/content-management-api/#update-content-type>Read
     * more</a>
     *
     * @param requestBody the request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-content-type">
     * Update Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> update(JSONObject requestBody) {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.update(this.contentTypeUid, this.headers, this.params, requestBody);
    }

    /**
     * <b>Field visibility rule.</b>
     * <p>
     * The Set Field Visibility Rule for Content Type API request lets you add Field
     * Visibility Rules to existing
     * content types. These rules allow you to show and hide fields based on the
     * state or value of certain fields.
     *
     * <p>
     * <a
     * href=https://www.contentstack.com/docs/developers/apis/content-management-api/#create-content-type>Field
     * Visibility Rules</a> can be set while creating your content type (via UI,
     * only after you've added all the
     * required fields to the content type and saved it) or while editing a content
     * type (both via UI and API).
     *
     * @param requestBody the request body JSONBody
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#set-field-visibility-rule-for-content-type">
     * Set Field Visibility Rule for Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> fieldVisibilityRule(JSONObject requestBody) {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.visibilityRule(this.contentTypeUid, this.headers, this.params, requestBody);
    }

    /**
     * <b>Delete Content Type</b>.
     *
     * <p>
     * To Delete Content Type call deletes an existing content type and all the
     * entries within it. When executing the
     * API call, in the <b>URI Parameters</b> section, provide the UID of your
     * content type
     * <b>Note:</b>
     * Note: You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory), along with
     * the stack API key, to make a valid Content Management API request. Read more
     * about Authentication.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-content-type">
     * Delete Content Type</a>
     * @see #addHeader(String, String)  to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.delete(this.contentTypeUid, this.headers, this.params);
    }

    /**
     * <b>Content Type References.</b>
     * <p>
     * The Get all references of content type call will fetch all the content types
     * in which a specified content type is
     * referenced.
     *
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory),along with the stack API key, to make a valid
     * Content Management API request.
     * Read more about authentication. You need to use either the stack's Management
     * Token or the user Authtoken (any
     * one is mandatory), along with the stack API key, to make a valid Content
     * Management API request. Read more about
     * authentication.
     *
     * @param isIncludeGlobalField Include Global Field true/false
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-references-of-content-type">
     * Get All References Of Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> reference(Boolean isIncludeGlobalField) {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.reference(this.contentTypeUid, this.headers, isIncludeGlobalField);
    }

    /**
     * <b>Content Type References</b>.
     * <p>
     * The Get all references of content type call will fetch all the content types
     * in which a specified content type is
     * referenced.
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory),along with the stack API key, to make a valid
     * Content Management API request.
     * Read more about authentication. You need to use either the stack's Management
     * Token or the user Authtoken (anyone
     * is mandatory), along with the stack API key, to make a valid Content
     * Management API request. Read more about
     * authentication.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-references-of-content-type">
     * Get All References Of Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> referenceIncludeGlobalField() {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.referenceIncludeGlobalField(this.contentTypeUid, this.headers);
    }

    /**
     * <b>Export Content Type</b>.
     * <p>
     * This call is used to export a specific content type and its schema. The data
     * is exported in JSON format. However,
     * please note that the entries of the specified content type are not exported
     * through this call. The schema of the
     * content type returned will depend on the version number provided.
     *
     *
     * <b>Note: </b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#export-a-content-type">
     * Export A Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> export() {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.export(this.contentTypeUid, this.headers);
    }

    /**
     * <b>Export Content Type</b>.
     * <p>
     * This call is used to export a specific content type and its schema. The data
     * is exported in JSON format. However,
     * please note that the entries of the specified content type are not exported
     * through this call. The schema of the
     * content type returned will depend on the version number provided.
     *
     *
     * <b>Note: </b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication.
     *
     * @param version The version of content type you want to retrieve. If no
     *                version is specified, you will get the latest
     *                version of the content type
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#export-a-content-type">
     * Export A Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> export(int version) {
        Objects.requireNonNull(this.contentTypeUid, "Content Type Uid Is Required");
        return service.export(this.contentTypeUid, this.headers, version);
    }

    /**
     * <b>Import content type</b>
     * <br>
     * The Import a content type call imports a content type into a stack by
     * uploading JSON file.
     * <br>
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#import-a-content-type">
     * Import A Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> imports() {
        return service.imports(this.headers, this.params);
    }

    /**
     * <b>Import content type</b>
     * <p>
     * The Import a content type call imports a content type into a stack by
     * uploading JSON file.
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#import-a-content-type">
     * Import A Content Type</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> importOverwrite() {
        this.headers.put("Content-Type", "multipart/form-data");
        return service.importOverwrite(this.headers);
    }
}
