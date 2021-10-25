package com.contentstack.cms.contenttype;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Content type.
 */
public class ContentType {

    protected ContentTypeService service;
    protected Map<String, String> headers;

    /**
     * Instantiates a new Content type.
     *
     * @param instance
     *         the {@link Retrofit} instance
     * @param apiKey
     *         the api key
     * @param authorization
     *         the management token
     */
    public ContentType(@NotNull Retrofit instance, @NotNull String apiKey, String authorization) {
        headers = new HashMap<>();
        headers.put("api_key", apiKey);
        if (authorization != null && !authorization.isEmpty()) {
            headers.put("authorization", authorization);
        }
        this.service = instance.create(ContentTypeService.class);
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
     * <p>
     * Read more about authentication. https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-content-types
     *
     * @param queryParam
     *         the query param
     * @return the call
     */
    public Call<ResponseBody> fetch(Map<String, Object> queryParam) {
        return service.fetch(
                headers,
                queryParam);
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
     * <p>
     *
     * @param contentTypeUid
     *         the content type uid of which you want to retrieve the details. The UID is generated based on the title
     *                     of the content type. The unique ID of a content type is unique across a stack.         <p>
     *         <b>Example: </b>product
     * @param queryParam
     *         Query Parameters         <b>include_global_field_schema</b> <p> the query param Tip: If any of your
     *           content types contains a         Global field, and you wish to fetch the content schema of the Global
     *               field, then you need to pass the         include_global_field_schema:true parameter. This parameter
     *         helps         return the Global field's schema along         with the content type schema.         <p>
     *              <b>version</b> <p>version of the content type of which you want to retrieve the details. If no
     *         version         is specified, you will get the latest version of the content type.
     * @return retrofit2.Call
     */
    public Call<ResponseBody> single(@NotNull String contentTypeUid,
                                     Map<String, Object> queryParam) {
        return service.single(
                headers,
                contentTypeUid,
                queryParam);
    }

    /**
     * <b>Create Content Type</b>
     * <p>
     * The <b>Create a content type</b> call creates a new content type in a particular stack of your Contentstack
     * account
     * <p>
     * In the <b>Body</b> section, you need to provide the complete schema of the content type. You can refer the
     * <p>
     * See <a href=https://www.contentstack.com/docs/developers/create-content-types/json-schema-for-creating-a-content-type>JSON
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
     * <pre>
     *     String bodyStr = "";
     *     <br>
     *     {@link Call} response = contentType.create(managementToken, bodyStr).execute();
     * </pre>
     *
     * @param requestBody
     *         the request body
     * @return retrofit2.Call
     */
    public Call<ResponseBody> create(JSONObject requestBody) {
        return service.create(headers, requestBody);
    }

    /**
     * <b>Update Content Type</b>
     * <p>
     * The <b>Update Content Type</b> call is used to update the schema of an existing content type.
     * <p>
     * <b>Note:</b> Whenever you update a content type, it will auto-increment the content type version.
     * <p>
     * <a href=https://www.contentstack.com/docs/developers/apis/content-management-api/#update-content-type>Read
     * more</a>
     *
     * @param contentTypeUid
     *         the content type uid
     * @param requestBody
     *         the request body
     * @return {@link Call} call
     */
    public Call<ResponseBody> update(@NotNull String contentTypeUid,
                                     JSONObject requestBody) {
        return service.update(contentTypeUid, headers, requestBody);
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
     * @param contentTypeUid
     *         the content type uid
     * @param requestBody
     *         the request body JSONBody
     * @return the call
     */
    public Call<ResponseBody> fieldVisibilityRule(@NotNull String contentTypeUid,
                                                  JSONObject requestBody) {
        return service.visibilityRule(contentTypeUid, headers, requestBody);
    }

    /**
     * <b>Delete Content Type</b>.
     * <p>
     * To Delete Content Type call deletes an existing content type and all the entries within it. When executing the
     * API call, in the <b>URI Parameters</b> section, provide the UID of your content type
     * <b>Note:</b>
     * Note: You need to use either the stack's Management Token or the user Authtoken (anyone is mandatory), along with
     * the stack API key, to make a valid Content Management API request. Read more about Authentication.
     *
     * @param contentTypeUid
     *         the content type uid
     * @return the call
     */
    public Call<ResponseBody> delete(@NotNull String contentTypeUid) {
        return service.delete(contentTypeUid, headers);
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
     * @param contentTypeUid
     *         the content type uid
     * @param isForce
     *         the is force
     * @return the call
     */
    public Call<ResponseBody> delete(@NotNull String contentTypeUid,
                                     @NotNull Boolean isForce) {
        return service.delete(contentTypeUid, headers, isForce);
    }

    /**
     * <b>Content Type References.</b>
     * <p>
     * The Get all references of content type call will fetch all the content types in which a specified content type is
     * referenced.
     *
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user Authtoken (anyone is
     * mandatory),along with the stack API key, to make a valid Content Management API request. Read more about
     * authentication. You need to use either the stack's Management Token or the user Authtoken (any one is mandatory),
     * along with the stack API key, to make a valid Content Management API request. Read more about authentication.
     *
     * @param contentTypeUid
     *         the content type uid
     * @return the call
     */
    public Call<ResponseBody> reference(@NotNull String contentTypeUid) {
        return service.reference(contentTypeUid, headers);
    }

    /**
     * <b>Content Type References</b>.
     * <p>
     * The Get all references of content type call will fetch all the content types in which a specified content type is
     * referenced.
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user Authtoken (anyone is
     * mandatory),along with the stack API key, to make a valid Content Management API request. Read more about
     * authentication. You need to use either the stack's Management Token or the user Authtoken (anyone is mandatory),
     * along with the stack API key, to make a valid Content Management API request. Read more about authentication.
     *
     * @param contentTypeUid
     *         the content type uid
     * @return the call
     */
    public Call<ResponseBody> referenceIncludeGlobalField(@NotNull String contentTypeUid) {
        return service.referenceIncludeGlobalField(contentTypeUid, headers);
    }

    /**
     * <b>Export Content Type</b>.
     * <p>
     * This call is used to export a specific content type and its schema. The data is exported in JSON format. However,
     * please note that the entries of the specified content type are not exported through this call. The schema of the
     * content type returned will depend on the version number provided.
     *
     *
     * <b>Note: </b> You need to use either the stack's Management Token or the user Authtoken (anyone is mandatory),
     * along with the stack API key, to make a valid Content Management API request. Read more about authentication.
     *
     * @param contentTypeUid
     *         the content type uid
     * @return the call
     */
    public Call<ResponseBody> export(@NotNull String contentTypeUid) {
        return service.export(contentTypeUid, headers);
    }

    /**
     * <b>Export Content Type</b>.
     * <p>
     * This call is used to export a specific content type and its schema. The data is exported in JSON format. However,
     * please note that the entries of the specified content type are not exported through this call. The schema of the
     * content type returned will depend on the version number provided.
     *
     *
     * <b>Note: </b> You need to use either the stack's Management Token or the user Authtoken (anyone is mandatory),
     * along with the stack API key, to make a valid Content Management API request. Read more about authentication.
     *
     * @param contentTypeUid
     *         the content type uid
     * @param version
     *         the version
     * @return the call
     */
    public Call<ResponseBody> export(@NotNull String contentTypeUid, int version) {
        return service.export(contentTypeUid, headers, version);
    }

    /**
     * Imports call.
     *
     * @return the call
     */
    public Call<ResponseBody> imports() {
        return service.imports(headers);
    }

    /**
     * <b>Import content type</b>
     * <p>
     * The Import a content type call imports a content type into a stack by uploading JSON file.
     * <p>
     * <b>Note:</b>  You need to use either the stack's Management Token or the user Authtoken (any one is mandatory),
     * along with the stack API key, to make a valid Content Management API request. Read more about authentication.
     *
     * @return the call
     */
    public Call<ResponseBody> importOverwrite() {
        return service.importOverwrite(headers);
    }
}
