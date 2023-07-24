package com.contentstack.cms.marketplace.auths;

import com.contentstack.cms.Parametron;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

public class Auth implements Parametron {
    private final AuthService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;

    public Auth(Retrofit clientInstance, String organizationUid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        if (organizationUid.isEmpty()) {
            throw new IllegalArgumentException("Organization uid could not be empty");
        }
        this.headers.put("organization_uid", organizationUid);
        this.service = clientInstance.create(AuthService.class);
    }

    /**
     * The function returns a call to find authorized apps using the provided
     * headers and parameters.
     * 
     * @return The method is returning a Call object with a ResponseBody type.
     */
    Call<ResponseBody> findAuthorizedApp() {
        return this.service.findAuthorizedApps(this.headers, this.params);
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key
     *              the key of the header to be added
     * @param value
     *              the value of the header to be added
     * @return a new {@link Auth} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    public Auth addParam(@NotNull String key, @NotNull Object value) {
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
     * @return a new {@link Auth} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @SuppressWarnings("unchecked")
    public Auth addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param params
     *               a {@link HashMap} containing the parameters to be added
     * @return a new {@link Auth} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    public Auth addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param headers
     *                a {@link HashMap} containing the parameters to be added
     * @return a new {@link Auth} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @SuppressWarnings("unchecked")
    public Auth addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
