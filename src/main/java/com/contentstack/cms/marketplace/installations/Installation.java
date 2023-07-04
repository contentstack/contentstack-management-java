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


    /**
     * Instantiates a new Installation.
     *
     * @param client
     *         the client
     * @param organisationId
     *         the org id
     * @param installationId
     *         the installation id
     */
    public Installation(Retrofit client, @NotNull String organisationId, @NotNull String installationId) {
        checkOrganisationId(organisationId);
        init(client, organisationId, installationId);
    }

    private void checkOrganisationId(String organisationId) {
        if (organisationId.isEmpty()) {
            throw new NullPointerException(Util.MISSING_ORG_ID);
        }
    }

    private void validateInstallationId(String installationId) {
        if (installationId == null || installationId.isEmpty()) {
            throw new IllegalArgumentException("installationId is requirement");
        }
    }

    /**
     * Instantiates a new Installation.
     *
     * @param client
     *         the client
     * @param organisationId
     *         the organization uid
     */
    public Installation(@NotNull Retrofit client, @NotNull String organisationId) {
        checkOrganisationId(organisationId);
        init(client, organisationId, null);
    }


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
     * Find installed apps call.
     *
     * @return the call
     */
    Call<ResponseBody> findInstalledApps() {
        return this.service.listInstalledApps(this.headers, this.params);
    }

    /**
     * Find installations call.
     *
     * @return the call
     */
    Call<ResponseBody> findInstallations() {
        return this.service.listInstallations(this.headers, this.params);
    }


    /**
     * Find installation call.
     *
     * @return the call
     */
    Call<ResponseBody> fetchInstallation() {
        validateInstallationId(this.installationId);
        return this.service.getInstallations(this.headers, installationId, this.params);
    }


    /**
     * Fetch installation data call.
     *
     * @return the call
     */
    Call<ResponseBody> fetchInstallationData() {
        validateInstallationId(this.installationId);
        return this.service.getInstallationData(this.headers, this.installationId, this.params);
    }

    /**
     * Update installation call.
     *
     * @return the call
     */
    Call<ResponseBody> updateInstallation(JSONObject body) {
        validateInstallationId(this.installationId);
        return this.service.updateInstallation(this.headers, this.installationId, body, this.params);
    }


    /**
     * Find installed users call.
     *
     * @return the call
     */
    Call<ResponseBody> findInstalledUsers() {
        return this.service.listInstalledUsers(this.headers, this.params);
    }


    /**
     * Find installed stacks call.
     *
     * @return the call
     */
    Call<ResponseBody> findInstalledStacks() {
        return this.service.listInstalledStacks(this.headers, this.params);
    }

    /**
     * Uninstall call.
     *
     * @return the call
     */
    Call<ResponseBody> uninstall() {
        validateInstallationId(this.installationId);
        return this.service.uninstall(this.headers, this.installationId);
    }

    /**
     * Fetch app configuration call.
     *
     * @return the call
     */
    Call<ResponseBody> fetchAppConfiguration() {
        validateInstallationId(this.installationId);
        return this.service.getAppConfiguration(this.headers, this.installationId, this.params);
    }

    /**
     * Fetch server configuration call.
     *
     * @return the call
     */
    Call<ResponseBody> fetchServerConfiguration() {
        validateInstallationId(this.installationId);
        return this.service.getServerConfiguration(this.headers, this.installationId, this.params);
    }

    Call<ResponseBody> updateServerConfiguration(JSONObject body) {
        validateInstallationId(this.installationId);
        return this.service.updateServerConfiguration(this.headers, this.installationId, body, this.params);
    }

    /**
     * Update stack configuration call.
     *
     * @param body
     *         The request Body
     * @return the call
     */
    Call<ResponseBody> updateStackConfiguration(JSONObject body) {
        validateInstallationId(this.installationId);
        return this.service.updateStackConfiguration(this.headers, this.installationId, body, this.params);
    }


    /**
     * Create installation token call.
     *
     * @return the call
     */
    Call<ResponseBody> createInstallationToken() {
        validateInstallationId(this.installationId);
        return this.service.createInstallationToken(this.headers, this.installationId, this.params);
    }

    /**
     * Location.
     *
     * @return the location
     */
    public Location location() {
        validateInstallationId(this.installationId);
        return new Location(this.client, this.organisationId, this.installationId);
    }

    /**
     * Webhook webhook.
     *
     * @param webhookId
     *         the webhook id
     * @return the webhook
     */
    public Webhook webhook(@NotNull String webhookId) {
        validateInstallationId(this.installationId);
        return new Webhook(this.client, this.organisationId, webhookId, this.installationId);
    }

    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link Installation} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Installation addParam(@NotNull String key, @NotNull Object value) {
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
     * @return a new {@link Installation} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Installation addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param params
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Installation} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Installation addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param headers
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Installation} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Installation addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
