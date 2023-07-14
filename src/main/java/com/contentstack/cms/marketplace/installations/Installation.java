package com.contentstack.cms.marketplace.installations;

import com.contentstack.cms.Parametron;
import com.contentstack.cms.core.Util;
import com.contentstack.cms.marketplace.installations.location.Location;
import com.contentstack.cms.marketplace.installations.webhook.Webhook;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * The type Installation.
 */
public class Installation implements Parametron {

    private String installationId;
    private String organisationId;
    private InstallationService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;
    private Retrofit client;

    // The `public Installation(Retrofit client, @NotNull String organisationId,
    // @NotNull String
    // installationId)` is a constructor for the `Installation` class. It takes
    // three parameters: `client`,
    // `organisationId`, and `installationId`.
    public Installation(Retrofit client, @NotNull String organisationId, @NotNull String installationId) {
        checkOrganisationId(organisationId);
        init(client, organisationId, installationId);
    }

    /**
     * The function checks if the organisationId is empty and throws a
     * NullPointerException if it is.
     * 
     * @param organisationId The parameter `organisationId` is a string that
     *                       represents the ID of an
     *                       organization.
     */
    private void checkOrganisationId(String organisationId) {
        if (organisationId.isEmpty()) {
            throw new NullPointerException(Util.MISSING_ORG_ID);
        }
    }

    /**
     * The function validates if the installationId is not null or empty.
     * 
     * @param installationId The installationId parameter is a string that
     *                       represents the unique identifier
     *                       for an installation.
     */
    private void validateInstallationId(String installationId) {
        if (installationId == null || installationId.isEmpty()) {
            throw new IllegalArgumentException("installationId is requirement");
        }
    }

    // The `public Installation(@NotNull Retrofit client, @NotNull String
    // organisationId)` is a constructor
    // for the `Installation` class. It takes two parameters: `client` and
    // `organisationId`.
    public Installation(@NotNull Retrofit client, @NotNull String organisationId) {
        checkOrganisationId(organisationId);
        init(client, organisationId, null);
    }

    /**
     * The function initializes the headers, parameters, installation ID,
     * organization ID, Retrofit client,
     * and service for an installation.
     * 
     * @param client         The `client` parameter is an instance of the Retrofit
     *                       class. It is used to make HTTP
     *                       requests to a RESTful API.
     * @param organisationId The `organisationId` parameter is a string that
     *                       represents the unique
     *                       identifier of an organization.
     * @param installationId The installationId parameter is a String that
     *                       represents the unique identifier
     *                       for an installation.
     */
    private void init(Retrofit client, @NotNull String organisationId, String installationId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.installationId = installationId;
        this.organisationId = organisationId;
        this.client = client;
        this.headers.put("organization_uid", organisationId);
        this.service = this.client.create(InstallationService.class);
    }

    /**
     * The function returns a list of installed apps by making a network call.
     * 
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> findInstalledApps() {
        return this.service.listInstalledApps(this.headers, this.params);
    }

    Call<ResponseBody> findInstallations() {
        return this.service.listInstallations(this.headers, this.params);
    }

    /**
     * The function fetches an installation by validating the installation ID and
     * making a network request.
     * 
     * @return The method `fetchInstallation()` is returning a `Call<ResponseBody>`
     *         object.
     */
    Call<ResponseBody> fetchInstallation() {
        validateInstallationId(this.installationId);
        return this.service.getInstallations(this.headers, installationId, this.params);
    }

    /**
     * The function fetches installation data by validating the installation ID and
     * making a network
     * request.
     * 
     * @return The method is returning a `Call<ResponseBody>` object.
     */
    Call<ResponseBody> fetchInstallationData() {
        validateInstallationId(this.installationId);
        return this.service.getInstallationData(this.headers, this.installationId, this.params);
    }

    /**
     * The function updates an installation with a given installation ID and returns
     * the response body.
     * 
     * @param body A JSONObject containing the data to be updated for the
     *             installation.
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> updateInstallation(JSONObject body) {
        validateInstallationId(this.installationId);
        return this.service.updateInstallation(this.headers, this.installationId, body, this.params);
    }

    /**
     * The function returns a list of installed users by making a network call.
     * 
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> findInstalledUsers() {
        return this.service.listInstalledUsers(this.headers, this.params);
    }

    /**
     * The function returns a list of installed stacks by making a network call.
     * 
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> findInstalledStacks() {
        return this.service.listInstalledStacks(this.headers, this.params);
    }

    /**
     * The function `uninstall()` validates the installation ID and then calls the
     * `uninstall()` method of
     * a service, returning the response body.
     * 
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> uninstall() {
        validateInstallationId(this.installationId);
        return this.service.uninstall(this.headers, this.installationId);
    }

    /**
     * The function fetches the app configuration by validating the installation ID
     * and making a network
     * request.
     * 
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> fetchAppConfiguration() {
        validateInstallationId(this.installationId);
        return this.service.getAppConfiguration(this.headers, this.installationId, this.params);
    }

    /**
     * The function fetches the server configuration by making a network request
     * with the installation ID
     * and parameters.
     * 
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    Call<ResponseBody> fetchServerConfiguration() {
        validateInstallationId(this.installationId);
        return this.service.getServerConfiguration(this.headers, this.installationId, this.params);
    }

    /**
     * The function updates the server configuration with the given JSON object and
     * returns the response
     * body.
     * 
     * @param body A JSONObject containing the updated server configuration data.
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> updateServerConfiguration(JSONObject body) {
        validateInstallationId(this.installationId);
        return this.service.updateServerConfiguration(this.headers, this.installationId, body, this.params);
    }

    /**
     * The function updates the stack configuration using the provided JSON object.
     * 
     * @param body A JSONObject containing the updated stack configuration data.
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> updateStackConfiguration(JSONObject body) {
        validateInstallationId(this.installationId);
        return this.service.updateStackConfiguration(this.headers, this.installationId, body, this.params);
    }

    /**
     * The function creates an installation token by validating the installation ID
     * and making a request to
     * the service.
     * 
     * @return The method is returning a `Call` object with a generic type of
     *         `ResponseBody`.
     */
    Call<ResponseBody> createInstallationToken() {
        validateInstallationId(this.installationId);
        return this.service.createInstallationToken(this.headers, this.installationId, this.params);
    }

    /**
     * The function returns a new Location object with the specified client,
     * organisationId, and
     * installationId.
     * 
     * @return An instance of the Location class.
     */
    public Location location() {
        validateInstallationId(this.installationId);
        return new Location(this.client, this.organisationId, this.installationId);
    }

    /**
     * The function creates and returns a new Webhook object with the given
     * parameters.
     * 
     * @param webhookId A string representing the ID of the webhook.
     * @return An instance of the Webhook class.
     */
    public Webhook webhook(@NotNull String webhookId) {
        validateInstallationId(this.installationId);
        return new Webhook(this.client, this.organisationId, webhookId, this.installationId);
    }

    /**
     * The function adds a parameter to a map and returns the updated map.
     * 
     * @param key   The key parameter is a String that represents the key for the
     *              parameter being added.
     * @param value The value parameter is of type Object, which means it can accept
     *              any type of object as
     *              its value.
     * @return The method is returning an instance of the `Installation` class.
     */

    @SuppressWarnings("unchecked")
    @Override
    public Installation addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * The function adds a header to a map and returns the updated map.
     * 
     * @param key   A string representing the key of the header to be added.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return The method is returning an instance of the `Installation` class.
     */

    @SuppressWarnings("unchecked")
    @Override
    public Installation addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param params
     *               a {@link HashMap} containing the parameters to be added
     * @return a new {@link Installation} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Installation addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param headers
     *                a {@link HashMap} containing the parameters to be added
     * @return a new {@link Installation} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Installation addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
