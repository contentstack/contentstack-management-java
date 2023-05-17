package com.contentstack.cms.marketplace.installations.webhook;

import com.contentstack.cms.Parametron;
import com.contentstack.cms.core.BadArgumentException;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


public class Webhook implements Parametron {

    private String installationId;
    protected HashMap<String, String> headers;
    private final WebhookService service;
    protected HashMap<String, Object> params;
    protected String webhookId;
    private final String ERROR_INSTALLATION = "installation uid is required";

    /**
     * Instantiates a new Installation.
     *
     * @param client
     *         the client
     * @param organizationUid
     *         the organization uid
     * @param webhookId
     *         the webhookId
     */
    public Webhook(Retrofit client, @NotNull String organizationUid, @NotNull String webhookId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.webhookId = webhookId;
        this.headers.put("organization_uid", organizationUid);
        this.service = client.create(WebhookService.class);
    }

    /**
     * Instantiates a new Installation.
     *
     * @param client
     *         the client
     * @param organizationUid
     *         the organization uid
     * @param webhookId
     *         the webhook uid
     * @param installationId
     *         the installation id
     */
    public Webhook(Retrofit client, @NotNull String organizationUid, @NotNull String webhookId, @NotNull String installationId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.webhookId = webhookId;
        this.installationId = installationId;
        this.headers.put("organization_uid", organizationUid);
        this.service = client.create(WebhookService.class);
    }


    /**
     * The List Installations call is used to retrieve a list of installations inside an organization.
     * <p>
     * Use the #addParams method to add any optional parameters to the request
     * <ul>
     * <li>(Optional) uid : String(comma separated): The app uid of which installation need to be listed</li>
     * <li>(Optional) installation_uid : String(comma separated): List of installations uid</li>
     * <li>(Optional) target_uid's : String(comma separated): List of stack api keys or organization UID</li>
     * <li>(Optional) sort : String : Sort field name</li>
     * <li>(Optional) order : String : Sort order</li>
     * <li>(Optional) limit : Int : Page size</li>
     * <li>(Optional) skip : Int : Number of records to skip</li>
     * </ul>
     *
     * @return Call
     */
    public Call<ResponseBody> findExecutionLogs() {
        if (this.installationId == null || this.installationId.isEmpty()) {
            throw new BadArgumentException(ERROR_INSTALLATION);
        }
        return this.service.findExecutionLogs(this.headers, this.installationId, webhookId, this.params);
    }


    public Call<ResponseBody> fetchExecutionLogs(String installationUid, String executionId) {
        return this.service.fetchExecutionLog(this.headers, installationUid, webhookId, executionId, this.params);
    }

    public Call<ResponseBody> retryExecution(@NotNull String executionId) {
        return this.service.retryExecution(this.headers, installationId, webhookId, executionId, this.params);
    }


    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link Webhook} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public Webhook addParam(@NotNull String key, @NotNull String value) {
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
     * @return a new {@link Webhook} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public Webhook addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param params
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Webhook} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public Webhook addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param headers
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Webhook} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public Webhook addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
