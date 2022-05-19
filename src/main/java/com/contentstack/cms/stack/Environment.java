package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * A publishing environment corresponds to one or more deployment servers or a content delivery destination where the
 * entries need to be published.
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Environment {

    protected final Map<String, Object> headers;
    protected final EnvironmentService service;

    protected Environment(Retrofit instance, @NotNull Map<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.headers.putAll(stackHeaders);
        this.service = instance.create(EnvironmentService.class);
    }

    /**
     * The Get all environments call fetches the list of all environments available in a stack.
     * <p>
     * You can add queries to extend the functionality of this API call. Under the URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     *
     * @param query
     *         query parameter
     * @return Call<ResponseBody>
     */
    public Call<ResponseBody> fetch(Map<String, Object> query) {
        if (query == null) {
            query = new HashMap<>();
        }
        return this.service.fetch(this.headers, query);
    }


    /**
     * The Get a single environment call returns more details about the specified environment of a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the authtoken that you receive after
     * logging into your account.
     *
     * @param environment
     *         name of the environment
     * @return Call<ResponseBody>
     */
    public Call<ResponseBody> get(@NotNull String environment) {
        return this.service.getEnv(this.headers, environment);
    }


    /**
     * The Add an environment call will add a publishing environment for a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * In the 'Body' section, mention the environment name, server names that are part of the environment, the urls
     * (which include the language code and the URL of the server), and the option to deploy content to a server.
     *
     * @param requestBody
     *         request body of type @{@link JSONObject}
     * @return Call<ResponseBody>
     */
    public Call<ResponseBody> add(@NotNull JSONObject requestBody) {
        return this.service.add(this.headers, requestBody);
    }


    /**
     * The Update environment call will update the details of an existing publishing environment for a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * In the 'Body' section, enter the updated details of the environment. You can modify the environment name, server
     * names that are part of the environment, the urls (which include the language code and the URL of the server), and
     * the option to deploy content to a server.
     *
     * @param environmentName
     *         name of the environment
     * @param requestBody
     *         request body of type @{@link JSONObject}
     * @return Call<ResponseBody>
     */
    public Call<ResponseBody> update(@NotNull String environmentName, @NotNull JSONObject requestBody) {
        return this.service.update(this.headers, environmentName, requestBody);
    }


    /**
     * Delete environment call will delete an existing publishing environment from your stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     *
     * @param environmentName
     *         name of the environment
     * @return Call<ResponseBody>
     */
    public Call<ResponseBody> delete(@NotNull String environmentName) {
        return this.service.delete(this.headers, environmentName);
    }
}
