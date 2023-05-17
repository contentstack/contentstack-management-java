package com.contentstack.cms.marketplace.installations.location;

import com.contentstack.cms.Parametron;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


public class Location implements Parametron {

    private String installationId;
    protected HashMap<String, String> headers;
    private final LocationService service;
    protected HashMap<String, Object> params;


    /**
     * Constructs a new {@link Location} object with the specified parameters.
     *
     * @param client
     *         a {@link Retrofit} object representing the client used to create the {@link LocationService} object
     * @param organizationUid
     *         a non-null {@link String} representing the organization UID
     * @param installationId
     *         a non-null {@link String} representing the installation ID
     * @throws NullPointerException
     *         if any of the arguments are null
     */
    public Location(Retrofit client, @NotNull String organizationUid, @NotNull String installationId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.installationId = installationId;
        this.headers.put("organization_uid", organizationUid);
        this.service = client.create(LocationService.class);
    }


    Call<ResponseBody> fetchConfigurationLocation() {
        return this.service.getConfigurationLocation(this.headers, this.installationId, this.params);
    }


    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link Location} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public Location addParam(@NotNull String key, @NotNull String value) {
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
     * @return a new {@link Location} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public Location addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param params
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Location} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public Location addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param headers
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Location} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public Location addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
