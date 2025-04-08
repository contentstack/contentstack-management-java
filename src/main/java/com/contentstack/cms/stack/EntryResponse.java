package com.contentstack.cms.stack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntryResponse {

    @SerializedName("entry")
    private JsonObject entryJson;

    private EntryPojo entryPojo;

    public EntryPojo getEntryPojo() {
        if (entryPojo == null && entryJson != null) {
            Gson gson = new Gson();
            // Deserialize system fields automatically
            entryPojo = gson.fromJson(entryJson, EntryPojo.class);  
            // Manually extract and map only non-system (dynamic) fields
            Map<String, Object> fields = new LinkedHashMap<>();
            for (Map.Entry<String, JsonElement> entry : entryJson.entrySet()) {
                String key = entry.getKey();
                if (!EntryPojo.SYSTEM_FIELDS.contains(key)) {
                    fields.put(key, gson.fromJson(entry.getValue(), Object.class));
                }
            }
            entryPojo.setFields(fields);
        }
        return entryPojo;
    }

    public JsonObject getRawJson() {
        return entryJson;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
