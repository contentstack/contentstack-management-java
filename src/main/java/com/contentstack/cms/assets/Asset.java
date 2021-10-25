package com.contentstack.cms.assets;

import com.contentstack.cms.entries.EntryService;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

public class Asset {

    protected Map<String, String> headers;
    protected EntryService service;

    public Asset(Retrofit instance, @NotNull String apiKey, String authorization) {
        headers = new HashMap<>(); // initialise the Hashmap
        headers.put("api_key", apiKey);
        if (authorization != null && !authorization.isEmpty()) {
            headers.put("authorization", authorization);
        }

        this.service = instance.create(EntryService.class);
    }
}
