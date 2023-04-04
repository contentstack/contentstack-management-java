package com.contentstack.cms.app;

import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 *
 */
public class Installation {

    protected HashMap<String, String> headers;
    private final InstallationService service;
    protected String appUid;

    public Installation(Retrofit client, @NotNull String organizationUid, @NotNull String manifestUid) {
        this.headers = new HashMap<>();
        this.appUid = manifestUid;

        this.headers.put("organization_uid", organizationUid);
        this.service = client.create(InstallationService.class);
    }



}
