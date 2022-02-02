package com.contentstack.cms.global;

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
 * The type Global field.
 */
public class GlobalField {

    /**
     * The Service.
     */
    protected final GlobalFieldService service;
    /**
     * The Api key.
     */
    protected final String apiKey;
    /**
     * The Management token.
     */
    protected final String managementToken;

    /**
     * Instantiates a new Global field.
     *
     * @param retrofit
     *                      the retrofit
     * @param apiKey
     *                      the api key
     * @param authorization
     *                      the authorization
     */
    public GlobalField(Retrofit retrofit, String apiKey, String authorization) {
        this.apiKey = apiKey;
        this.managementToken = authorization;
        this.service = retrofit.create(GlobalFieldService.class);
    }

    /**
     * <b>Get All Global Fields</b>
     * <p>
     * The Get <b>All global fields</b> call returns comprehensive information of
     * all the global fields available in a
     * particular stack in your account
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (any one is mandatory),
     * along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @return retrofit2.Call
     */
    public Call<ResponseBody> fetch() {
        return this.service.fetch(apiKey, managementToken);
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
     * Authtoken (any one is mandatory),
     * along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param globalFiledUid
     *                       the global filed uid
     * @return retrofit2.Call
     */
    public Call<ResponseBody> single(@NotNull String globalFiledUid) {
        return this.service.single(globalFiledUid, apiKey, managementToken);
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
     * Authtoken (any one is mandatory),
     * along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param requestBody
     *                    the request body
     * @return retrofit2.Call
     */
    public Call<ResponseBody> create(String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return this.service.create(apiKey, managementToken, body);
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
     * Authtoken (any one is mandatory),
     * along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param globalFiledUid
     *                       the global filed uid
     * @param requestBody
     *                       the request body
     * @return retrofit2.Call
     */
    public Call<ResponseBody> update(@NotNull String globalFiledUid, String requestBody) {
        RequestBody body = Util.toRequestBody(requestBody);
        return this.service.update(globalFiledUid, apiKey, managementToken, body);
    }

    /**
     * <b>Delete global field</b>
     * <p>
     * The Delete global field request allows you to delete a specific global field.
     * <p>
     * <b>Warning:</b> If your Global field has been referred within a particular
     * content type, then you will need to
     * pass an additional query parameter force:true to delete the Global field.
     * <p>
     * <b>here</b>: force:true is already applied in this request call
     *
     * @param globalFiledUid
     *                       the global filed uid
     * @return retrofit2.Call
     */
    public Call<ResponseBody> delete(@NotNull String globalFiledUid) {
        return this.service.delete(globalFiledUid, apiKey, managementToken);
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
     * @param jsonGlobalField
     *                        The Json object to pass as body
     * @return retrofit2.Call
     */
    public Call<ResponseBody> imports(@NotNull Map<String, Object> jsonGlobalField) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("global_field", jsonGlobalField);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());
        return this.service.imports(apiKey, managementToken, body);
    }

    /**
     * <b>Export global field</b>
     * <p>
     * This request is used to export a specific global field and it's schema, The
     * data is exported in JSON format
     *
     * @param globalFiledUid
     *                       the global filed uid
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> export(@NotNull String globalFiledUid) {
        return this.service.export(globalFiledUid, apiKey, managementToken);
    }
}
