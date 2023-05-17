package com.contentstack.cms.marketplace.apps;

import com.contentstack.cms.Parametron;
import com.contentstack.cms.marketplace.apps.hosting.Hosting;
import com.contentstack.cms.marketplace.apps.oauth.Oauth;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * App/Manifest is used for creating/updating/deleting app in your Contentstack organization.
 *
 * @author ishaileshmishra
 * @version v1.0.0
 * @since 2023 -APR-15
 */
public class App implements Parametron {

    private final AppService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;
    private String appUid;
    private final Retrofit client;
    private final String errorMessage = "App/Manifest uid is required";
    private final String ORGANIZATION_UID = "organization_uid";
    private final String ERROR_NO_ORGANIZATION_UID = "Organization uid could not be empty";

    /**
     * Instantiates a new Organization.
     *
     * @param client
     *         The retrofit client
     * @param organizationUid
     *         the organization uid
     */
    public App(Retrofit client, @NotNull String organizationUid) {
        this.client = client;
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        if (organizationUid.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_ORGANIZATION_UID);
        }
        this.headers.put(ORGANIZATION_UID, organizationUid);
        this.service = client.create(AppService.class);
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
        this.client = client;
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        if (organizationUid.isEmpty()) {
            throw new IllegalArgumentException("Organization uid could not be empty");
        }
        this.headers.put(ORGANIZATION_UID, organizationUid);
        if (uid.isEmpty()) {
            throw new NullPointerException(errorMessage);
        }
        this.appUid = uid;
        this.service = client.create(AppService.class);
    }


    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link App} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public App addParam(@NotNull String key, @NotNull String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link App} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public App addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param params
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link App} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public App addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param headers
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link App} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public App addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }



    /**
     * Create installation call.
     *
     * @param body
     *         the body
     * @return the call
     */
    public Call<ResponseBody> createInstallation(@NotNull JSONObject body) {
        return this.service.createInstallation(this.headers, this.appUid, body);
    }

    /**
     * Update version call.
     *
     * @param body
     *         the body
     * @return the call
     */
    public Call<ResponseBody> updateVersion(@NotNull JSONObject body) {
        return this.service.updateVersion(this.headers, this.appUid, body, this.params);
    }

    /**
     * Find app authorizations call.
     *
     * @return the call
     */
    public Call<ResponseBody> findAppAuthorizations() {
        return service.findAppAuthorizations(this.headers, this.appUid);
    }

    /**
     * Delete authorization call.
     *
     * @param authorizationUid
     *         the authorization uid
     * @return the call
     */
    public Call<ResponseBody> deleteAuthorization(@NotNull String authorizationUid) {
        return service.deleteAuthorization(this.headers, this.appUid, authorizationUid);
    }

    /**
     * Find app installations call.
     *
     * @return the call
     */
    public Call<ResponseBody> findAppInstallations() {
        return service.listAppInstallations(this.headers, this.appUid, this.params);
    }

    /**
     * Find apps call.
     *
     * @return the call
     */
    public Call<ResponseBody> findApps() {
        return service.listApps(this.headers, this.params);
    }

    /**
     * Create app call.
     *
     * @param body
     *         the body
     * @return the call
     */
    public Call<ResponseBody> createApp(@NotNull JSONObject body) {
        return service.createApp(this.headers, this.params, body);
    }

    /**
     * Fetch app call.
     *
     * @return the call
     */
    public Call<ResponseBody> fetchApp() {
        return service.fetchApp(this.headers, this.appUid, this.params);
    }

    /**
     * Update app call.
     *
     * @param body
     *         the body
     * @return the call
     */
    public Call<ResponseBody> updateApp(@NotNull JSONObject body) {
        return service.updateApp(this.headers, this.appUid, body);
    }

    /**
     * Delete app call.
     *
     * @return the call
     */
    public Call<ResponseBody> deleteApp() {
        return service.delete(this.headers, this.appUid);
    }

    /**
     * Find app requests call.
     *
     * @return the call
     */
    public Call<ResponseBody> findAppRequests() {
        return service.listAppRequests(this.headers, this.appUid);
    }

    /**
     * Oauth oauth.
     *
     * @return the oauth
     */
    public Oauth oauth() {
        String orgId = this.headers.get(ORGANIZATION_UID);
        return new Oauth(this.client, orgId, this.appUid);
    }

    /**
     * Oauth oauth.
     *
     * @param appId
     *         the app id
     * @return the oauth
     */
    public Oauth oauth(@NotNull String appId) {
        String orgId = this.headers.get(ORGANIZATION_UID);
        return new Oauth(this.client, orgId, appId);
    }

    /**
     * Hosting hosting.
     *
     * @return the instance of hosting
     */
    public Hosting hosting() {
        String orgId = this.headers.get(ORGANIZATION_UID);
        return new Hosting(this.client, orgId, this.appUid);
    }

    /**
     * Hosting hosting.
     *
     * @param appId
     *         the app id
     * @return the instance of hosting
     */
    public Hosting hosting(@NotNull String appId) {
        String orgId = this.headers.get(ORGANIZATION_UID);
        return new Hosting(this.client, orgId, appId);
    }


}
