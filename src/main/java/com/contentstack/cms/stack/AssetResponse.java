package com.contentstack.cms.stack;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class AssetResponse {
    @SerializedName("asset")
    private Map<String, Object> rawJson; // Store the full asset JSON

    private AssetPojo assetPojo; 

    public AssetPojo getAssetPojo() {
        if (assetPojo == null && rawJson != null) {
            assetPojo = new Gson().fromJson(new Gson().toJson(rawJson), AssetPojo.class);
        }
        return assetPojo;
    }
    public Map<String, Object> getRawJson() {
        return rawJson;
    }
    @Override
    public String toString() {
        return new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}


