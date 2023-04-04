package com.contentstack.cms.app;

import com.contentstack.cms.core.BadArgumentException;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * App/Manifest is used  for creating/updating/deleting app in your Contentstack organization.
 *
 * @author ishaileshmishra
 * @version v1.0.0
 * @since 2023-APR-15
 */
public class App {

    private final AppService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;
    private String appUid;
    private Retrofit client;

    /**
     * Instantiates a new Organization.
     *
     * @param client
     *         The retrofit client
     */
    public App(Retrofit client, @NotNull String organizationUid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        if (organizationUid.isEmpty()) {
            throw new IllegalArgumentException("Organization UID could not be empty");
        }
        this.headers.put("organization_uid", organizationUid);
        this.client = client;
        this.service = this.client.create(AppService.class);
    }

    /**
     * Instantiates a new App/Manifest.
     *
     * @param client
     *         The retrofit client
     * @param organizationUid
     *         The uid of the organization
     * @param uid
     *         The app uid
     */
    public App(Retrofit client, @NotNull String organizationUid, @NotNull String uid) {
        this.headers = new HashMap<>();
        if (organizationUid.isEmpty()) {
            throw new IllegalArgumentException("Organization UID could not be empty for app/manifest operations");
        }
        this.headers.put("organization_uid", organizationUid);
        this.appUid = uid;
        this.params = new HashMap<>();
        this.client = client;
        this.service = this.client.create(AppService.class);
    }

    public Installation installation() {
        if (this.appUid == null || this.appUid.isEmpty()) {
            throw new BadArgumentException("app uid/Manifest uid is required");
        }
        return new Installation(this.client, this.headers.get("organization_uid"), this.appUid);
    }

    /**
     * HTTP header parameters are key-value pairs that are included in the header to provide additional information
     * about the request or response:
     *
     * @param key
     *         header key are being sent in the request
     * @param value
     *         header value are being sent in the request against to the header key
     */
    public App addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Params in a request refer to the key-value pairs that are sent along with the request to provide additional
     * information or data:
     *
     * @param key
     *         key for the request param
     * @param value
     *         value for the request param
     */
    public App addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * (protected) Clear all params
     */
    protected App clearParams() {
        this.params.clear();
        return this;
    }

    /**
     * Removes parameter from the request already available.
     *
     * @param key
     *         parameter key of the request
     */
    public App removeParam(@NotNull String key) {
        this.params.remove(key);
        return this;
    }


    /**
     * The list manifests call is used to fetch details of all apps in a particular organization
     * {@link #addParam(String, Object)} Add Request Query Parameters:
     * <p>(Optional) search: (String) Search for app details in its name and description.</p>
     * <p>(Optional) limit (Number): Helps you set the response limit. default: 50</p>
     * <p>(Optional) skip (Number): Set this parameter to skip the response by offset. default: 0</p>
     * <p>(Optional) order (String): Define the response order by using this parameter.</p>
     * <p>(Optional) sort (String): Perform sorting in the response based on the passed option.</p>
     * <p>
     *
     * @return Call<ResponseBody> the response
     */
    public Call<ResponseBody> find() {
        return this.service.find(this.headers, this.params);
    }

    /**
     * The get manifest call is used to fetch details of a particular app with its ID.
     * <br>
     *
     * @param manifestUid
     *         The ID of the app that you want to fetch details of.
     * @return Call
     */
    public Call<ResponseBody> fetch(@NotNull String manifestUid) {
        validate();
        this.appUid = manifestUid;
        return service.fetch(this.headers, manifestUid, this.params);
    }

    private void validate() {
        if (!this.headers.containsKey("organization_uid"))
            throw new IllegalStateException("organization uid can not be null or empty");
    }

    /**
     * An unchecked exception that is thrown to indicate an illegal or unsuitable argument passed to a method.
     *
     * @param body
     *         The request body of type JSONObject
     */
    private void isValidJSON(JSONObject body) {
        if (!body.containsKey("name") || !body.containsKey("target_type")) {
            throw new BadArgumentException("The JSON Request body does not contain name or target_type parameter");
        }
    }

    /**
     * The create manifest call is used for creating a new app/manifest in your Contentstack
     *
     * @param body
     *         <b>The request body, should contain params like</b>
     *         <p><p>
     *         <p> Required param name (String): Name of the app that should be MinLength of 3 and maximum MaxLength -
     *         20</p>
     *         <p> Required param target_type (String): Type of the app to be installed</p>
     *         <p> Optional icon (String) : Icon of the app <p>
     *         <p> Optional webhook (Object with channels, signed, target_url, any extra params required to hit the
     *         given target_url) : Notify the user for the required changes <p>
     *         <p> Optional ui_location (Object with signed, base_url, locations object which have type, meta ):
     *         Location of the app in the UI flow <p>
     *         <p> Optional oauth (Object with enabled, redirect_uri, bot_scopes): Enable the OAuth while creating the
     *         app <p>
     * @return Call
     */
    public Call<ResponseBody> create(JSONObject body) {
        validate();
        isValidJSON(body);
        return service.create(this.headers, this.params, body);
    }


    /**
     * The update manifest call is used to update the app details such as name, description, icon, and so on.
     *
     * @param body
     *         <b>The request body, should contain params like</b>
     *         <p><p>
     *         <p> Required param name (String): Name of the app that should be MinLength of 3 and maximum MaxLength -
     *         20</p>
     *         <p> Required param target_type (String): Type of the app to be installed</p>
     *         <p> Optional icon (String) : Icon of the app <p>
     *         <p> Optional webhook (Object with channels, signed, target_url, any extra params required to hit the
     *         given target_url) : Notify the user for the required changes <p>
     *         <p> Optional ui_location (Object with signed, base_url, locations object which have type, meta ):
     *         Location of the app in the UI flow <p>
     *         <p> Optional oauth (Object with enabled, redirect_uri, bot_scopes): Enable the OAuth while creating the
     *         app <p>
     * @return Call
     */
    public Call<ResponseBody> update(JSONObject body) {
        validate();
        isValidJSON(body);
        if (this.appUid == null || this.appUid.isEmpty()) {
            throw new BadArgumentException("appUid/ManifestUid is required");
        }
        return service.updateById(this.headers, this.appUid, body);
    }

    public Call<ResponseBody> update(@NotNull String id, JSONObject body) {
        validate();
        isValidJSON(body);
        this.appUid = id;
        return service.updateById(this.headers, this.appUid, body);
    }


    /**
     * The delete an app call is used to delete the app.
     *
     * @return Call
     */
    public Call<ResponseBody> delete() {
        validate();
        if (this.appUid == null || this.appUid.isEmpty()) {
            throw new BadArgumentException("appUid/ManifestUid is required");
        }
        return service.delete(this.headers, this.appUid);
    }

    /**
     * The delete an app call is used to delete the app.
     *
     * @param uid
     *         The ID of the app to be deleted
     * @return Call
     */
    public Call<ResponseBody> delete(@NotNull String uid) {
        validate();
        this.appUid = uid;
        return service.delete(this.headers, this.appUid);
    }


    /**
     * The get oauth call is used to fetch the OAuth details of the app.
     *
     * @param uid
     *         The ID of the app for which OAuth details are required.
     * @return Call
     */
    public Call<ResponseBody> getOauth(@NotNull String uid) {
        validate();
        return service.getAuthConfiguration(this.headers, uid);
    }


    /**
     * The update oauth configuration call is used to update the OAuth details, (redirect url and permission scope) of
     * an app.
     *
     * @param uid
     *         The app ID for which the OAuth details are required
     * @param body
     *         <b>The request body, should contain params like</b>
     *         <p>
     *         <p> Required param target_type (String): Type of the app to be installed</p>
     *         <p> Optional app_token_config (Object with enabled & scopes) : App Token configuration <p>
     *         <p> Optional webhook (Object with channels, signed, target_url, any extra params required to hit the
     *         given target_url) : Notify the user for the required changes <p>
     *         <p> Optional user_token_config (ObjectObject with enabled & scopes):
     *         User token configuration
     * @return Call
     */
    public Call<ResponseBody> updateOauth(@NotNull String uid, JSONObject body) {
        validate();
        this.appUid = uid;
        return service.updateAuthConfiguration(this.headers, this.appUid, body);
    }

    /**
     * The update oauth configuration call is used to update the OAuth details, (redirect url and permission scope) of
     * an app.
     *
     * @param body
     *         <b>The request body, should contain params like</b>
     *         <p>
     *         <p> Required param target_type (String): Type of the app to be installed</p>
     *         <p> Optional app_token_config (Object with enabled & scopes) : App Token configuration <p>
     *         <p> Optional webhook (Object with channels, signed, target_url, any extra params required to hit the
     *         given target_url) : Notify the user for the required changes <p>
     *         <p> Optional user_token_config (ObjectObject with enabled & scopes):
     *         User token configuration
     * @return Call
     */
    public Call<ResponseBody> updateOauth(JSONObject body) {
        validate();
        if (this.appUid == null || this.appUid.isEmpty()) {
            throw new BadArgumentException("app uid / manifest uid required");
        }
        return service.updateAuthConfiguration(this.headers, this.appUid, body);
    }


    /**
     * The get hosting call is used to fetch to know whether the hosting is enabled or not.
     *
     * @param uid
     *         The ID of the app for which OAuth details are required.
     * @return Call
     */
    public Call<ResponseBody> hetHosting(@NotNull String uid) {
        validate();
        return service.getHosting(this.headers, appUid);
    }

}
