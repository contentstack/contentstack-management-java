package com.contentstack.cms.stack;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ContentTypeResponse {
    @SerializedName("content_type")
    private Map<String, Object> rawJson; // Store the full contentType JSON

    private ContentTypePojo contentPojo; 

    public ContentTypePojo getContentPojo() {
        if (contentPojo == null && rawJson != null) {
            contentPojo = new Gson().fromJson(new Gson().toJson(rawJson), ContentTypePojo.class);
        }
        return contentPojo;
    }
    public Map<String, Object> getRawJson() {
        return rawJson;
    }
    @Override
    public String toString() {
        return new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
