package com.contentstack.cms.marketplace.apps.oauth;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

public class Oauth {

    private final String ERROR_NO_ORGANIZATION_UID = "Organization uid could not be empty";

    private final OauthService service;
    protected HashMap<String, String> headers;
    protected HashMap<String, Object> params;
    private String appId;

    public Oauth(Retrofit client, String organizationId, @NotNull String appId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        if (appId.isEmpty()) {
            throw new NullPointerException("App Id is required");
        }
        this.appId = appId;
        if (organizationId.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_ORGANIZATION_UID);
        }
        this.headers.put("organization_uid", organizationId);
        this.service = client.create(OauthService.class);
    }

    public Call<ResponseBody> fetchOauthConfiguration(@NotNull String appId) {
        return service.getOauthConfiguration(this.headers, appId);
    }

    Call<ResponseBody> updateOauthConfiguration(JSONObject body) {
        return service.updateOauthConfiguration(this.headers, this.appId, body);
    }

    Call<ResponseBody> findScopes() {
        return service.findScopes(this.headers, this.appId);
    }

}
