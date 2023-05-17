package com.contentstack.cms.marketplace;

import com.contentstack.cms.marketplace.apps.App;
import com.contentstack.cms.marketplace.auths.Auth;
import com.contentstack.cms.marketplace.installations.Installation;
import com.contentstack.cms.marketplace.request.AppRequest;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;

/**
 * The type Marketplace.
 */
public class Marketplace {

    private final Retrofit client;
    private final String orgId;
    private final String ERR_MESSAGE = "To access the marketplace, the organization_uid parameter is required. " +
            "Please ensure that you provide a valid organization_uid value when calling the marketplace() method";

    /**
     * Instantiates a new Marketplace.
     *
     * @param client
     *         the client
     * @param orgId
     *         the org id
     */
    public Marketplace(@NotNull Retrofit client, @NotNull String orgId) {
        this.client = client;
        this.orgId = orgId;
        if (this.orgId.isEmpty()) {
            throw new NullPointerException(ERR_MESSAGE);
        }
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
     * Installation installation.
     *
     * @return the instance of installation
     */
    public Installation installation() {
        return new Installation(this.client, this.orgId);
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
