package com.contentstack.cms.stack;
import java.util.Map;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetPojo {

    @SerializedName("uid")
    protected String uid;

    @SerializedName("title")
    protected String title;

    @SerializedName("content_type")
    protected String contentType;

    @SerializedName("file_size")
    protected String fileSize;

    @SerializedName("filename")
    protected String filename;

    @SerializedName("url")
    protected String url;

    @SerializedName("description")
    protected String description;

    @SerializedName("_version")
    protected int version;

    @SerializedName("is_dir")
    protected boolean isDir;

    @SerializedName("tags")
    protected String[] tags;

    @SerializedName("name")
    protected String name;

    // Store any unknown/dynamic fields
    @Expose(serialize = false, deserialize = false) 
    protected transient Map<String, Object> additionalFields;

    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, Object> additionalFields) {
        this.additionalFields = additionalFields;
    }

    // Getters
    public String getTitle() { 
        if (contentType.equals("application/vnd.contenstack.folder")) {
            return name;
        }
        return title; }

    @Override
    public String toString() {
        return new com.google.gson.GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this);
    }
}

