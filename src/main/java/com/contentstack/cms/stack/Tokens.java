package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

public class Tokens {


    protected final TokenService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    protected Tokens(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(TokenService.class);
    }

    public Tokens addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    // Delivery Tokens
    public Call<ResponseBody> getDeliveryTokens() {
        return this.service.getDeliveryToken(this.headers);
    }

    public Call<ResponseBody> getDeliveryToken(@NotNull String tokenUid) {
        return this.service.getDeliveryToken(this.headers, tokenUid);
    }

    public Call<ResponseBody> createDeliveryToken(@NotNull JSONObject requestBody) {
        return this.service.createDeliveryToken(this.headers, requestBody);
    }

    public Call<ResponseBody> updateDeliveryToken(@NotNull String tokenUid, @NotNull JSONObject requestBody) {
        return this.service.updateDeliveryToken(this.headers, tokenUid, requestBody);
    }

    public Call<ResponseBody> deleteDeliveryToken(@NotNull String tokenUid) {
        return this.service.deleteDeliveryToken(this.headers, tokenUid, false);
    }

    public Call<ResponseBody> deleteDeliveryToken(@NotNull String tokenUid, @NotNull Boolean isForce) {
        return this.service.deleteDeliveryToken(this.headers, tokenUid, isForce);
    }


    // Management Tokens
    public Call<ResponseBody> getManagementTokens() {
        return this.service.getManagementToken(this.headers);
    }

    public Call<ResponseBody> getManagementToken(@NotNull String tokenUid) {
        return this.service.getManagementToken(this.headers, tokenUid);
    }

    public Call<ResponseBody> createManagementToken(@NotNull JSONObject requestBody) {
        return this.service.createManagementToken(this.headers, requestBody);
    }

    public Call<ResponseBody> updateManagementToken(@NotNull String tokenUid, @NotNull JSONObject requestBody) {
        return this.service.updateManagementToken(this.headers, tokenUid, requestBody);
    }

    public Call<ResponseBody> deleteManagementToken(@NotNull String tokenUid) {
        return this.service.deleteManagementToken(this.headers, tokenUid);
    }

}
