package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * A publishing environment corresponds to one or more deployment servers or a
 * content delivery destination where the
 * entries need to be published.
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-22
 */
public class Environment {

    protected final Map<String, Object> headers;
    protected final Map<String, Object> params;
    protected final EnvironmentService service;
    protected String environment;

    protected Environment(Retrofit instance) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = instance.create(EnvironmentService.class);
    }

    protected Environment(Retrofit instance, String environment) {
        this.environment = environment;
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = instance.create(EnvironmentService.class);
    }

    void validate() {
        if (this.environment == null || this.environment.isEmpty())
            throw new IllegalStateException("Environment can not be null or empty");
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
     * The Get all environments call fetches the list of all environments available
     * in a stack.
     * <p>
     * You can add queries to extend the functionality of this API call. Under the
     * URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * {@link #addParam(String, Object)} Use addParams to include Query parameters
     * <p>
     * <b>Example: </b>
     * 
     * <pre>
     * environment.addParams("someKey", "someValue').fetch()
     * </pre>
     *
     * @return Call
     * @see <a
     *      href=
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
     * The Get a single environment call returns more details about the specified
     * environment of a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter
     * the authtoken that you receive after
     * logging into your account.
     *
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-environment">Get
     *      a
     *      single environments
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.getEnv(this.headers, this.environment);
    }

    /**
     * The Add an environment call will add a publishing environment for a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter
     * the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * In the 'Body' section, mention the environment name, server names that are
     * part of the environment, the urls
     * (which include the language code and the URL of the server), and the option
     * to deploy content to a server.
     *
     * @param body
     *             The {@link JSONObject} request body<br>
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#environment-collection">Get
     *      all
     *      environments
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject body) {
        return this.service.add(this.headers, body);
    }

    /**
     * The Update environment call will update the details of an existing publishing
     * environment for a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter
     * the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * In the 'Body' section, enter the updated details of the environment. You can
     * modify the environment name, server
     * names that are part of the environment, the urls (which include the language
     * code and the URL of the server), and
     * the option to deploy content to a server.
     *
     * @param requestBody
     *                    request body of type @{@link JSONObject}
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-environment">Update
     *      Environment</a>
     * @see #addHeader(String, Object) to add headers to the request
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        this.validate();
        return this.service.update(this.headers, this.environment, requestBody);
    }

    /**
     * Delete environment call will delete an existing publishing environment from
     * your stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter
     * the API key of your stack and the
     * authtoken that you receive after logging into your account.
     *
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-environment">Delete
     *      Environment</a>
     * @see #addHeader(String, Object) to add headers to the request
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        this.validate();
        return this.service.delete(this.headers, this.environment);
    }
}
