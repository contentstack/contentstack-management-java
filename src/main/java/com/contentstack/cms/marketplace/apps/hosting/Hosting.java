package com.contentstack.cms.marketplace.apps.hosting;


import com.contentstack.cms.Parametron;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * The type Hosting.
 */
public class Hosting implements Parametron {
    private final HostingService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;
    private final String appId;

    /**
     * Instantiates a new Hosting.
     *
     * @param client
     *         the client
     * @param organizationId
     *         the organization id
     * @param appId
     *         the app id
     */
    public Hosting(Retrofit client, String organizationId, @NotNull String appId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        if (appId.isEmpty()) {
            throw new NullPointerException("app id/manifest uid is required");
        }
        this.appId = appId;
        if (organizationId.isEmpty()) {
            throw new IllegalArgumentException("Organization uid could not be empty");
        }
        this.headers.put("organization_uid", organizationId);
        this.service = client.create(HostingService.class);
    }

    /**
     * Get hosting configuration of your App Manifest
     *
     * @return the call
     */
    Call<ResponseBody> fetchHosting() {
        return this.service.getHosting(this.headers, this.appId, this.params);
    }

    /**
     * Create signed upload url call.
     *
     * @return the call
     */
    Call<ResponseBody> createSignedUploadUrl() {
        return this.service.signedUploadUrl(this.headers, this.appId);
    }


    /**
     * Upload file call.
     *
     * @param url
     *         the url
     * @return the call
     */
    Call<ResponseBody> uploadFile(@NotNull String url) {
        if (url.isEmpty()) {
            throw new NullPointerException("Url is required to upload the file");
        }
        return this.service.uploadFile(url, this.headers, this.params);
    }


    /**
     * Create deployment call.
     *
     * @param body
     *         the body
     * @return the call
     */
    Call<ResponseBody> createDeployment(@NotNull JSONObject body) {
        return this.service.createDeployments(this.headers, this.appId, body, this.params);
    }


    /**
     * List deployments call.
     *
     * @return the call
     */
    Call<ResponseBody> findDeployments() {
        return this.service.findDeployments(this.headers, this.appId, this.params);
    }


    /**
     * Fetch deployment call.
     *
     * @param deploymentId
     *         the deployment id
     * @return the call
     */
    Call<ResponseBody> fetchDeployment(@NotNull String deploymentId) {
        if (deploymentId.isEmpty()) {
            throw new NullPointerException("deploymentId is required to get deployment");
        }
        return this.service.fetchDeployment(this.headers, this.appId, deploymentId, this.params);
    }


    /**
     * Gets latest live deployment.
     *
     * @return the latest live deployment
     */
    Call<ResponseBody> getLatestLiveDeployment() {
        return this.service.fetchLatestLiveDeployment(this.headers, this.appId, this.params);
    }


    /**
     * List deployment logs call.
     *
     * @param deploymentId
     *         the deployment id
     * @return the call
     */
    Call<ResponseBody> findDeploymentLogs(@NotNull String deploymentId) {
        return this.service.findDeploymentLogs(this.headers, this.appId, deploymentId, this.params);
    }


    /**
     * Create signed download url call.
     *
     * @return the call
     */
    Call<ResponseBody> createSignedDownloadUrl() {
        return this.service.createSignedDownloadUrl(this.headers, this.appId, this.params);
    }


    /**
     * Download file call.
     *
     * @param url
     *         the url
     * @return the call
     */
    Call<ResponseBody> downloadFile(@NotNull String url) {
        return this.service.downloadFile(url, this.headers, this.params);
    }

    /**
     * The toggle hosting call is used to enable or disable the hosting of an app.
     * <br>
     *
     * <b>ACL:</b>
     * <p>
     * Organisation Admins
     * <p>
     * Organisation Owners
     * <p>
     * Stack Owners
     * <p>
     * Stack Admins
     *
     * @return the call
     */
    Call<ResponseBody> enableToggleHosting() {
        return this.service.toggleEnableHosting(this.headers, this.appId);
    }


    /**
     * The toggle hosting call is used to enable or disable the hosting of an app.
     * <br>
     *
     * <b>ACL:</b>
     * <p>
     * Organisation Admins
     * <p>
     * Organisation Owners
     * <p>
     * Stack Owners
     * <p>
     * Stack Admins
     *
     * @return the call
     */
    Call<ResponseBody> disableToggleHosting() {
        return this.service.toggleDisableHosting(this.headers, this.appId);
    }


    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link Hosting} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addParam(@NotNull String key, @NotNull Object value) {
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
     * @return a new {@link Hosting} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param params
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Hosting} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param headers
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Hosting} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }


}
