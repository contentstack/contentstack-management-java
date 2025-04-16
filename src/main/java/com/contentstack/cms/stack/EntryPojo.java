package com.contentstack.cms.stack;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import com.google.gson.annotations.SerializedName;

public class EntryPojo {

    @SerializedName("title")
    public String title;

    @SerializedName("uid")
    public String uid;

    @SerializedName("_version")
    public int version;

    @SerializedName("locale")
    public String locale;

    // Additional fields captured dynamically from content type schema
    protected Map<String, Object> fields;

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public String getStringField(String key) {
        Object value = fields.get(key);
        return value != null ? value.toString() : null;
    }
    
    public Map<String, Object> getJsonObjectField(String key) {
        Object value = fields.get(key);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return null;
    }
    // Define system fields to exclude from dynamic mapping
    public static final Set<String> SYSTEM_FIELDS = new HashSet<>(Arrays.asList(
            "title","uid", "_version", "locale", "tags", "ACL", "created_by", "updated_by", 
            "created_at", "updated_at", "_in_progress"
    ));
}


