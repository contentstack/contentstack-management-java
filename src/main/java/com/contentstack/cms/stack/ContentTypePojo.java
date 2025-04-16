package com.contentstack.cms.stack;
import java.util.Map;
import org.json.simple.JSONArray;
import com.google.gson.annotations.SerializedName;

public class ContentTypePojo {

    @SerializedName("title")
    protected String title;

    @SerializedName("uid")
    protected String uid;

    @SerializedName("schema")
    protected JSONArray schema;

    @SerializedName("field_rules")
    protected JSONArray fieldRules;

    @SerializedName("_version")
    protected int version;

    @SerializedName("description")
    protected String description;

    @SerializedName("options")
    protected Map<String, Object> options;

}
