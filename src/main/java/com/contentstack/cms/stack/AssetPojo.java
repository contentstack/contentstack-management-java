package com.contentstack.cms.stack;
import java.util.Map;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssetPojo {

    @SerializedName("uid")
    private String uid;

    @SerializedName("title")
    private String title;

    @SerializedName("content_type")
    private String contentType;

    @SerializedName("file_size")
    private String fileSize;

    @SerializedName("filename")
    private String filename;

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String description;

    @SerializedName("_version")
    private int version;

    @SerializedName("is_dir")
    private boolean isDir;

    @SerializedName("tags")
    private String[] tags;

    @SerializedName("name")
    private String name;

    // Store any unknown/dynamic fields
    @Expose(serialize = false, deserialize = false) 
    private transient Map<String, Object> additionalFields;

    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, Object> additionalFields) {
        this.additionalFields = additionalFields;
    }

    // Getters
    public String getUid() { return uid; }
    public String getTitle() { 
        if (contentType.equals("application/vnd.contenstack.folder")) {
            return name;
        }
        return title; }
    public String getContentType() { return contentType; }
    public String getFileSize() { return fileSize; }
    public String getFilename() { return filename; }
    public String getUrl() { return url; }
    public String getDescription() { return description; }
    public int getVersion() { return version; }
    public boolean isDir() { return isDir; }
    public String[] getTags() { return tags; }

    @Override
    public String toString() {
        return new com.google.gson.GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this);
    }
}

