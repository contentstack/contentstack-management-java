package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;


/**
 * An alias acts as a pointer to a particular branch. You can specify the alias ID in your frontend code to pull content
 * from the target branch associated with an alias.
 *
 * @author ishaileshmishra
 * @version 1.0.0
 * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#aliases">About Aliases
 * </a>
 * @since 2022-05-19
 */
public class Alias {

    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected final AliasService service;

    protected Alias(Retrofit instance, @NotNull Map<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.headers.putAll(stackHeaders);
        params = new HashMap<>();
        this.service = instance.create(AliasService.class);
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
     * The Get all aliases request returns comprehensive information of all the aliases available in a particular stack
     * in your account.
     * <p>
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-aliases">Get all
     * aliases</a>
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * The Get a single alias request returns information of a specific alias.
     *
     * @param uid
     *         The unique ID of the alias of which you want to retrieve the details. The UID of an alias is unique
     *         across a stack. Execute the Get all aliases call to retrieve the UID of an alias
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-branch">
     * Get a single branch</a>
     * @since 1.0.0
     */
    public Call<ResponseBody> single(@NotNull String uid) {
        return this.service.single(this.headers, uid);
    }

    /**
     * <b>Assign or Update an alias</b>
     * <br>
     * The Assign an alias request creates a new alias in a particular stack of your organization. This alias can point
     * to any existing branch (target branch) of your stack.
     * <p>
     * You can use the same request to update the target branch of an alias. In the “Body” section, you need to provide
     * the UID of the new target branch that will be associated with the alias.
     *
     * @param body
     *         the request body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#assign-or-update-an-alias">Update
     * a branch</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject body) {
        return this.service.update(this.headers, body);
    }

    /**
     * The Delete an alias request deletes an existing alias.
     * <p>
     * To confirm deletion of an alias, you need to specify the force=true query parameter.
     * <p>
     * When executing the API call, in the “URL Parameters” section, provide the UID of your alias.
     *
     * @param branchUid
     *         the branch uid
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-an-alias">Delete a
     * branch</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String branchUid) {
        return this.service.delete(this.headers, branchUid, this.params);
    }

}