package com.contentstack.cms.marketplace;

import com.contentstack.cms.marketplace.apps.App;
import com.contentstack.cms.marketplace.auths.Auth;
import com.contentstack.cms.marketplace.installations.Installation;
import com.contentstack.cms.marketplace.request.AppRequest;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Marketplace.
 */
public class Marketplace {

    private Retrofit client;
    private final String orgId;
    private final static String ERR_MESSAGE = "The organization_uid is required";

    // The `Marketplace` constructor is initializing a new instance of the
    // `Marketplace` class. It takes
    // three parameters: `Retrofit client`, `String orgId`, and `String baseUrl`.
    public Marketplace(@NotNull Retrofit client, @NotNull String orgId, String baseUrl) {
        this.client = client;
        this.orgId = orgId;
        if (baseUrl == null) {
            String parentEndpoint = String.valueOf(this.client.baseUrl());
            if (parentEndpoint.contains("v3/")) {
                parentEndpoint = parentEndpoint.replaceFirst("v3/", "");
            }
            this.client = updateClient(this.client, parentEndpoint);
        } else {
            this.client = updateClient(this.client, baseUrl);
        }

        if (this.orgId.isEmpty()) {
            throw new NullPointerException(ERR_MESSAGE);
        }
    }

    /**
     * The function updates the base URL of a Retrofit client and returns the
     * updated client.
     * 
     * @param client  The `client` parameter is an instance of the `Retrofit` class.
     *                It represents the HTTP
     *                client used for making network requests.
     * @param baseUrl The `baseUrl` parameter is a string that represents the base
     *                URL of the API that the
     *                Retrofit client will communicate with.
     * @return The updated Retrofit client is being returned.
     */
    private Retrofit updateClient(Retrofit client, String baseUrl) {
        if (!baseUrl.startsWith("http")) {
            baseUrl = "https://" + baseUrl;
        }
        return client.newBuilder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
    }

    /**
     * The function returns a new instance of the App class with the specified
     * client and orgId.
     * 
     * @return An instance of the App class is being returned.
     */
    public App app() {
        return new App(this.client, this.orgId);
    }

    /**
     * The function creates and returns a new instance of the App class with the
     * given parameters.
     * 
     * @param uid The uid parameter is a String that represents a unique identifier
     *            for the app.
     * @return An instance of the App class is being returned.
     */
    public App app(@NotNull String uid) {
        return new App(this.client, this.orgId, uid);
    }

    /**
     * The function returns a new instance of the Auth class with the given client
     * and orgId.
     * 
     * @return An instance of the Auth class is being returned.
     */
    public Auth authorizations() {
        return new Auth(this.client, this.orgId);
    }

    /**
     * The function returns a new instance of the Installation class with the
     * specified client and orgId.
     * 
     * @return An instance of the Installation class.
     */
    public Installation installation() {
        return new Installation(this.client, this.orgId);
    }

    /**
     * The function returns a new Installation object with the specified
     * installation ID.
     * 
     * @param installationId The installationId parameter is a unique identifier for
     *                       an installation. It is
     *                       used to create a new Installation object with the
     *                       specified installationId.
     * @return An instance of the Installation class.
     */
    public Installation installation(String installationId) {
        return new Installation(this.client, this.orgId, installationId);
    }

    /**
     * The function returns a new instance of the AppRequest class with the
     * specified client and orgId.
     * 
     * @return An instance of the AppRequest class.
     */
    public AppRequest request() {
        return new AppRequest(this.client, this.orgId);
    }

}
