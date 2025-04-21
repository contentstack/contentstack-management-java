package com.contentstack.cms.stack;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class AssetListResponse {
    
    @SerializedName("assets")
    private List<Map<String, Object>> rawJson;

    private transient List<AssetPojo> assets;

    public List<AssetPojo> getAssets() {
        if (assets == null && rawJson != null) {
            assets = new Gson().fromJson(new Gson().toJsonTree(rawJson), new TypeToken<List<AssetPojo>>(){}.getType());
        }
        return assets;
    }

    public List<Map<String, Object>> getRawJson() {
        return rawJson;
    }

    @Override
    public String toString() {
        return new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
