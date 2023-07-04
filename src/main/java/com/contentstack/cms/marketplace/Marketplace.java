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
    private final static String ERR_MESSAGE = "To access marketplace instance, the organization_uid parameter is required. " +
            "Please ensure that you provide a valid organization_uid value when calling the marketplace() method";

    /**
     * Instantiates a new Marketplace.
     *
     * @param client
     *         the client
     * @param orgId
     *         the org id
     * @param baseUrl
     *         the base url
     */
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


    private Retrofit updateClient(Retrofit client, String baseUrl) {
        if (!baseUrl.startsWith("http")) {
            baseUrl = "https://" + baseUrl;
        }
        return client.newBuilder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
    }

    /**
     * App app.
     *
     * @return the instance of app
     */
    public App app() {
        return new App(this.client, this.orgId);
    }

    /**
     * App app.
     *
     * @param uid
     *         the uid
     * @return the instance of app
     */
    public App app(@NotNull String uid) {
        return new App(this.client, this.orgId, uid);
    }

    /**
     * Authorizations auth.
     *
     * @return the instance of authorizations
     */
    public Auth authorizations() {
        return new Auth(this.client, this.orgId);
    }

    /**
     * returns instance of Installation.
     *
     * @return the instance of {@link Installation}
     */
    public Installation installation() {
        return new Installation(this.client, this.orgId);
    }


    /**
     * returns instance of Installation.
     *
     * @param installationId
     *         the installation uid
     * @return instance of {@link Installation}
     */
    public Installation installation(String installationId) {
        return new Installation(this.client, this.orgId, installationId);
    }

    /**
     * Request app request.
     *
     * @return the instance of request
     */
    public AppRequest request() {
        return new AppRequest(this.client, this.orgId);
    }

}
