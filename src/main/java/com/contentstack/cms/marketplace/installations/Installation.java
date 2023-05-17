package com.contentstack.cms.marketplace.installations;

import com.contentstack.cms.Parametron;
import com.contentstack.cms.marketplace.installations.location.Location;
import com.contentstack.cms.marketplace.installations.webhook.Webhook;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * The type Installation.
 */
public class Installation implements Parametron {

    private String installationId;
    protected HashMap<String, String> headers;
    private InstallationService service;
    protected HashMap<String, Object> params;
    private Retrofit client;
    private final String MISSING_INSTALLATION_ID = "installation uid is required";
    private final String MISSING_ORG_ID = "organization uid is required";


    /**
     * Instantiates a new Installation.
     *
     * @param client
     *         the client
     * @param orgId
     *         the org id
     * @param installationId
     *         the installation id
     */
    public Installation(Retrofit client, @NotNull String orgId, @NotNull String installationId) {
        if (orgId.isEmpty()) {
            throw new NullPointerException(MISSING_ORG_ID);
        }
        this.headers.put("organization_uid", orgId);
        init(client, installationId);
    }

    private void init(Retrofit client, String installationId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.installationId = installationId;
        this.client = client;
        this.service = this.client.create(InstallationService.class);
    }

    /**
     * Instantiates a new Installation.
     *
     * @param client
     *         the client
     * @param organizationUid
     *         the organization uid
     */
    public Installation(@NotNull Retrofit client, @NotNull String organizationUid) {
        if (organizationUid.isEmpty()) {
            throw new NullPointerException(MISSING_ORG_ID);
        }
        this.headers.put("organization_uid", organizationUid);
        init(client, null);
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
    Call<ResponseBody> findInstallation() {
        return this.service.getInstallations(this.headers, installationId, this.params);
    }

    /**
     * Fetch installation data call.
     *
     * @return the call
     */
    Call<ResponseBody> fetchInstallationData() {
        return this.service.getInstallationData(this.headers, installationId, this.params);
    }

    /**
     * Update installation call.
     *
     * @param installationId
     *         the installation id
     * @return the call
     */
    Call<ResponseBody> updateInstallation(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        return this.service.updateInstallation(this.headers, installationId, this.params);
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
     * @param installationId
     *         the installation id
     * @return the call
     */
    Call<ResponseBody> uninstall(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        return this.service.uninstall(this.headers, installationId);
    }

    /**
     * Fetch app configuration call.
     *
     * @param installationId
     *         the installation id
     * @return the call
     */
    Call<ResponseBody> fetchAppConfiguration(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        return this.service.getAppConfiguration(this.headers, installationId, this.params);
    }

    /**
     * Fetch server configuration call.
     *
     * @param installationId
     *         the installation id
     * @return the call
     */
    Call<ResponseBody> fetchServerConfiguration(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        return this.service.getServerConfiguration(this.headers, installationId, this.params);
    }

    /**
     * Update server configuration call.
     *
     * @param installationId
     *         the installation id
     * @return the call
     */
    Call<ResponseBody> updateServerConfiguration(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        return this.service.updateServerConfiguration(this.headers, installationId, this.params);
    }

    /**
     * Update stack configuration call.
     *
     * @param installationId
     *         the installation id
     * @return the call
     */
    Call<ResponseBody> updateStackConfiguration(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        return this.service.updateStackConfiguration(this.headers, installationId, this.params);
    }

    /**
     * Create installation token call.
     *
     * @param installationId
     *         the installation id
     * @return the call
     */
    Call<ResponseBody> createInstallationToken(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        return this.service.createInstallationToken(this.headers, installationId, this.params);
    }

    /**
     * Location location.
     *
     * @param installationId
     *         the installation id
     * @return the location
     */
    public Location location(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        String organizationId = this.headers.get("organization_id");
        return new Location(this.client, organizationId, installationId);
    }

    /**
     * Webhook webhook.
     *
     * @param installationId
     *         the installation id
     * @param webhookId
     *         the webhook id
     * @return the webhook
     */
    public Webhook webhook(@NotNull String installationId, @NotNull String webhookId) {
        if (installationId.isEmpty()) {
            throw new NullPointerException(MISSING_INSTALLATION_ID);
        }
        String organizationId = this.headers.get("organization_id");
        return new Webhook(this.client, organizationId, installationId);
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
    @Override
    public Installation addParam(@NotNull String key, @NotNull String value) {
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
    @Override
    public Installation addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
