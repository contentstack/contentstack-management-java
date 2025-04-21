package com.contentstack.cms.stack;
import com.google.gson.annotations.SerializedName;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

public class EntryListResponse {

    @SerializedName("entries")
    private List<JsonObject> rawEntries;

    private transient List<EntryPojo> entries;

    public List<EntryPojo> getEntries() {
        if (entries == null && rawEntries != null) {
            Gson gson = new Gson();
            entries = new ArrayList<>();

            for (JsonObject entryJson : rawEntries) {
                EntryPojo entryPojo = gson.fromJson(entryJson, EntryPojo.class);
                // Map dynamic fields
                Map<String, Object> fields = new LinkedHashMap<>();
                for (Map.Entry<String, JsonElement> field : entryJson.entrySet()) {
                    String key = field.getKey();
                    if (!EntryPojo.SYSTEM_FIELDS.contains(key)) {
                        fields.put(key, gson.fromJson(field.getValue(), Object.class));
                    }
                }
                entryPojo.setFields(fields);
                entries.add(entryPojo);
            }
        }
        return entries;
    }

    public List<JsonObject> getRawJson() {
        return rawEntries;
    }
    
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
