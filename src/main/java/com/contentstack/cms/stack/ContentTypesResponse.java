package com.contentstack.cms.stack;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class ContentTypesResponse {
    @SerializedName("content_types")
    private List<Map<String, Object>> rawJson;

    private transient List<ContentTypePojo> contentTypes;

    public List<ContentTypePojo> getContentTypes() {
        if (contentTypes == null && rawJson != null) {
            contentTypes = new Gson().fromJson(new Gson().toJsonTree(rawJson), new TypeToken<List<ContentTypePojo>>(){}.getType());
        }
        return contentTypes;
    }

    public List<Map<String, Object>> getRawJson() {
        return rawJson;
    }

    @Override
    public String toString() {
        return new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
