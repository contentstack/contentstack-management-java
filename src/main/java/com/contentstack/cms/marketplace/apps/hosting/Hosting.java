package com.contentstack.cms.marketplace.apps.hosting;

import com.contentstack.cms.Parametron;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

public class Hosting implements Parametron {
    // The code snippet is defining instance variables for the `Hosting` class.
    private final HostingService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;
    private final String appId;

    // Instantiates a new Hosting
    // The `public Hosting(Retrofit client, String organizationId, @NotNull String
    // appId)` constructor
    // is used to initialize the `Hosting` object. It takes three parameters:
    // `client`,
    // `organizationId`, and `appId`.
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
     * The function fetches hosting data using the provided headers, app ID, and
     * parameters. It Gets hosting configuration of your App Manifest
     * 
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> fetchHosting() {
        return this.service.getHosting(this.headers, this.appId, this.params);
    }

    /**
     * Create signed upload url call.
     * The function returns a signed upload URL by making a request to a service
     * with the provided headers and app ID.
     * 
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> createSignedUploadUrl() {
        return this.service.signedUploadUrl(this.headers, this.appId);
    }

    /**
     * The function uploads a file to a specified URL using the provided headers and
     * parameters.
     * 
     * @param url The URL where the file will be uploaded to.
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> uploadFile(@NotNull String url) {
        if (url.isEmpty()) {
            throw new NullPointerException("Url is required to upload the file");
        }
        return this.service.uploadFile(url, this.headers, this.params);
    }

    /**
     * The function creates a deployment using the provided JSON body.
     * 
     * @param body The `body` parameter is a `JSONObject` that contains the data
     *             needed to create a
     *             deployment.
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> createDeployment(@NotNull JSONObject body) {
        return this.service.createDeployments(this.headers, this.appId, body, this.params);
    }

    /**
     * The function `findDeployments()` returns a `Call` object that makes a request
     * to find deployments
     * using the provided headers, app ID, and parameters.
     * 
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> findDeployments() {
        return this.service.findDeployments(this.headers, this.appId, this.params);
    }

    /**
     * The function fetches a deployment using the provided deployment ID.
     * 
     * @param deploymentId The deploymentId is a unique identifier for a deployment.
     *                     It is used to retrieve
     *                     information about a specific deployment.
     * @return The method is returning a `Call<ResponseBody>` object.
     */
    Call<ResponseBody> fetchDeployment(@NotNull String deploymentId) {
        if (deploymentId.isEmpty()) {
            throw new NullPointerException("deploymentId is required to get deployment");
        }
        return this.service.fetchDeployment(this.headers, this.appId, deploymentId, this.params);
    }

    /**
     * The function returns the latest live deployment by making an API call.
     * 
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> getLatestLiveDeployment() {
        return this.service.fetchLatestLiveDeployment(this.headers, this.appId, this.params);
    }

    /**
     * The function `findDeploymentLogs` returns a `Call` object that retrieves
     * deployment logs based on
     * the provided deployment ID.
     * 
     * @param deploymentId The deployment ID is a unique identifier for a specific
     *                     deployment. It is used
     *                     to identify and retrieve the logs for a particular
     *                     deployment.
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> findDeploymentLogs(@NotNull String deploymentId) {
        return this.service.findDeploymentLogs(this.headers, this.appId, deploymentId, this.params);
    }

    /**
     * The function creates a signed download URL using the provided headers, app
     * ID, and parameters.
     * 
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> createSignedDownloadUrl() {
        return this.service.createSignedDownloadUrl(this.headers, this.appId, this.params);
    }

    /**
     * The function `downloadFile` downloads a file from a given URL using the
     * provided headers and
     * parameters.
     * 
     * @param url The URL of the file that you want to download.
     * @return The method is returning a `Call<ResponseBody>` object.
     */
    Call<ResponseBody> downloadFile(@NotNull String url) {
        return this.service.downloadFile(url, this.headers, this.params);
    }

    /**
     * The function enables or disables hosting for a specific application.
     * It is used to enable or disable the hosting of an app.
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
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> enableToggleHosting() {
        return this.service.toggleEnableHosting(this.headers, this.appId);
    }

    /**
     * The function `disableToggleHosting()` makes a network call to disable hosting
     * for a specific app.
     * The toggle hosting call is used to disable the hosting of an app.
     * <br>
     *
     * <p>
     * Organisation Admins
     * <p>
     * Organisation Owners
     * <p>
     * Stack Owners
     * <p>
     * Stack Admins
     * 
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> disableToggleHosting() {
        return this.service.toggleDisableHosting(this.headers, this.appId);
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key
     *              the key of the header to be added
     * @param value
     *              the value of the header to be added
     * @return a new {@link Hosting} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key
     *              the key of the header to be added
     * @param value
     *              the value of the header to be added
     * @return a new {@link Hosting} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param params
     *               a {@link HashMap} containing the parameters to be added
     * @return a new {@link Hosting} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param headers
     *                a {@link HashMap} containing the parameters to be added
     * @return a new {@link Hosting} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Hosting addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }

}
