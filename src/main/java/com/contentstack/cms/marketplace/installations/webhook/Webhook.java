package com.contentstack.cms.marketplace.installations.webhook;

import com.contentstack.cms.Parametron;
import com.contentstack.cms.core.BadArgumentException;
import com.contentstack.cms.core.Util;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

public class Webhook implements Parametron {

    private final String installationId;
    protected HashMap<String, String> headers;
    private final WebhookService service;
    protected HashMap<String, Object> params;
    protected String webhookId;

    // The `Webhook` constructor is initializing the `Webhook` object with the
    // necessary parameters.
    public Webhook(Retrofit client, @NotNull String organizationUid, @NotNull String webhookId,
            @NotNull String installationId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.webhookId = webhookId;
        this.installationId = installationId;
        this.headers.put("organization_uid", organizationUid);
        this.service = client.create(WebhookService.class);
    }

    private void validateInstallationId() {
        if (this.installationId == null || this.installationId.isEmpty()) {
            throw new BadArgumentException(Util.ERROR_INSTALLATION);
        }
    }

    /**
     * The List Installations call is used to retrieve a list of installations
     * inside an organization.
     * <p>
     * Use the #addParams method to add any optional parameters to the request
     * <ul>
     * <li>(Optional) uid : String(comma separated): The app uid of which
     * installation need to be listed</li>
     * <li>(Optional) installation_uid : String(comma separated): List of
     * installations uid</li>
     * <li>(Optional) target_uid's : String(comma separated): List of stack api keys
     * or organization UID</li>
     * <li>(Optional) sort : String : Sort field name</li>
     * <li>(Optional) order : String : Sort order</li>
     * <li>(Optional) limit : Int : Page size</li>
     * <li>(Optional) skip : Int : Number of records to skip</li>
     * </ul>
     *
     * @return Call
     */
    public Call<ResponseBody> findExecutionLogs() {
        validateInstallationId();
        return this.service.findExecutionLogs(this.headers, this.installationId, webhookId, this.params);
    }

    /**
     * The function fetches execution logs for a given execution ID.
     * 
     * @param executionId The executionId parameter is a unique identifier for a
     *                    specific execution. It is
     *                    used to fetch the execution logs for that particular
     *                    execution.
     * @return The method is returning a Call object with a generic type of
     *         ResponseBody.
     */
    public Call<ResponseBody> fetchExecutionLogs(String executionId) {
        validateInstallationId();
        return this.service.fetchExecutionLog(this.headers, this.installationId, webhookId, executionId, this.params);
    }

    /**
     * The function retries the execution of a webhook with the given execution ID.
     * 
     * @param executionId The executionId parameter is a string that represents the
     *                    unique identifier of an
     *                    execution. It is used to identify a specific execution
     *                    that needs to be retried.
     * @return The method is returning a `Call<ResponseBody>` object.
     */
    public Call<ResponseBody> retryExecution(@NotNull String executionId) {
        validateInstallationId();
        return this.service.retryExecution(this.headers, this.installationId, webhookId, executionId, this.params);
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key
     *              the key of the header to be added
     * @param value
     *              the value of the header to be added
     * @return a new {@link Webhook} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Webhook addParam(@NotNull String key, @NotNull Object value) {
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
     * @return a new {@link Webhook} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Webhook addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param params
     *               a {@link HashMap} containing the parameters to be added
     * @return a new {@link Webhook} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Webhook addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param headers
     *                a {@link HashMap} containing the parameters to be added
     * @return a new {@link Webhook} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public Webhook addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
