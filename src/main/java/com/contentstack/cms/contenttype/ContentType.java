package com.contentstack.cms.contenttype;

import com.contentstack.cms.core.Util;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.Map;

public class ContentType {
    protected ContentTypeService contentTypeService;
    protected String apiKey;

    public ContentType(@NotNull Retrofit instance, @NotNull String apiKey) {
        this.apiKey = apiKey;
        this.contentTypeService = instance.create(ContentTypeService.class);
    }


    /**
     * <b>Fetch call.</b>
     * The Get all content types call returns comprehensive information of all the content types available in a
     * particular stack in your account.
     * <p>
     * <b>Note:</b>
     * <p>
     * You need to use either the stack’s Management Token or the user Authtoken (any one is mandatory), along with the
     * stack API key, to make a valid Content Management API request.
     * <p>
     * Read more about authentication. https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-content-types
     *
     * @param authorization
     *         the authorization
     * @param queryParam
     *         the query param
     * @return the call
     */
    public Call<ResponseBody> fetch(
            @NotNull String authorization,
            Map<String, Boolean> queryParam) {
        return contentTypeService.fetch(
                this.apiKey,
                authorization,
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
     *         of the content type. The unique ID of a content type is unique across a stack.
     *         <p>
     *         <b>Example: </b>product
     * @param authorization
     *         the authorization
     * @param queryParam
     *         Query Parameters
     *         <b>include_global_field_schema</b> <p> the query param Tip: If any of your content types contains a
     *         Global field and you wish to fetch the content schema of the Global field, then you need to pass the
     *         include_global_field_schema:true parameter. This parameter helps return the Global field's schema along
     *         with the content type schema.
     *         <p>
     *         <b>version</b> <p>version of the content type of which you want to retrieve the details. If no version
     *         is specified, you will get the latest version of the content type.
     * @return retrofit2.Call<ResponseBody> call
     */
    public Call<ResponseBody> single(
            @NotNull String contentTypeUid,
            @NotNull String authorization,
            Map<String, Boolean> queryParam) {
        return contentTypeService.single(
                this.apiKey,
                contentTypeUid,
                authorization,
                queryParam);
    }

    /**
     * <b>Create Content Type</b>
     * <p>
     * The <b>Create a content type</b> call creates a new content type in a particular stack of your Contentstack
     * account
     * <p>
     * <p>
     * In the <b>Body</b> section, you need to provide the complete schema of the content type. You can refer the
     * <p>
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
     * @param authorization
     *         the authorization
     * @param requestBody
     *         the request body
     * @return Call<ResponseBody> call
     */
    public Call<ResponseBody> create(
            @NotNull String authorization,
            String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return contentTypeService.create
                (this.apiKey, authorization, body);
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
     * @return call
     */
    public Call<ResponseBody> update(
            @NotNull String contentTypeUid,
            @NotNull String authorization,
            String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return contentTypeService.update(this.apiKey, contentTypeUid, authorization, body);
    }

    public Call<ResponseBody> fieldVisibilityRule(
            @NotNull String contentTypeUid,
            @NotNull String authorization,
            String bodyString) {
        RequestBody body = Util.toRequestBody(bodyString);
        return contentTypeService.visibilityRule(contentTypeUid, this.apiKey, authorization, body);
    }

    public Call<ResponseBody> delete(
            @NotNull String authorization) {
        return contentTypeService.delete(this.apiKey, authorization);
    }

    public Call<ResponseBody> delete(
            @NotNull String authorization,
            @NotNull Boolean isForce) {
        return contentTypeService
                .delete(this.apiKey, authorization, isForce);
    }

    public Call<ResponseBody> reference(
            @NotNull String contentTypeUid,
            @NotNull String authorization) {
        return contentTypeService.reference(contentTypeUid,
                this.apiKey, authorization);
    }

    public Call<ResponseBody> reference(
            @NotNull String contentTypeUid,
            @NotNull String authorization,
            @NotNull boolean includeGlobalField) {
        return contentTypeService.reference(contentTypeUid,
                this.apiKey, authorization, includeGlobalField);
    }

    public Call<ResponseBody> export(
            @NotNull String authorization) {
        return contentTypeService.export(this.apiKey, authorization);
    }

    public Call<ResponseBody> export
            (@NotNull String authorization,
             @NotNull int version) {
        return contentTypeService.export(this.apiKey, authorization, version);
    }

    public Call<ResponseBody> imports(
            @NotNull String authorization) {
        return contentTypeService.imports(this.apiKey, authorization);
    }

    public Call<ResponseBody> imports(
            @NotNull String authorization,
            @NotNull boolean isOverwrite) {
        return contentTypeService.imports(this.apiKey, authorization, isOverwrite);
    }
}
