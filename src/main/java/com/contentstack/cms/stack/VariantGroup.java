package com.contentstack.cms.stack;

import com.contentstack.cms.core.ErrorMessages;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.contentstack.cms.BaseImplementation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * The VariantGroup class provides functionality to manage variant groups in Contentstack.
 * Variant groups allow you to manage different versions of your content for various use cases,
 * such as A/B testing, localization, or personalization.
 */
public class VariantGroup implements BaseImplementation<VariantGroup> {
    protected final VariantsService service;
    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    private final Retrofit instance;
    private String variantGroupUid;
    private List<String> branches;

    /**
     * Creates a new VariantGroup instance without a specific variant group UID.
     * This constructor is used when creating new variant groups.
     *
     * @param instance The Retrofit instance for making API calls
     * @param headers The headers to be included in API requests
     */
    protected VariantGroup(Retrofit instance, Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.instance = instance;
        this.service = instance.create(VariantsService.class);
        this.branches = Arrays.asList("main"); // Default to main branch
    }

    /**
     * Creates a new VariantGroup instance with a specific variant group UID.
     * This constructor is used when working with existing variant groups.
     *
     * @param instance The Retrofit instance for making API calls
     * @param headers The headers to be included in API requests
     * @param variantGroupUid The unique identifier of the variant group
     */
    protected VariantGroup(Retrofit instance, Map<String, Object> headers, String variantGroupUid) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.instance = instance;
        this.variantGroupUid = variantGroupUid;
        this.service = instance.create(VariantsService.class);
        this.branches = Arrays.asList("main"); // Default to main branch
    }

    /**
     * Validates that the variant group UID is not null or empty.
     * This method is called before operations that require a valid variant group UID.
     *
     * @throws IllegalAccessError if the variant group UID is null or empty
     */
    void validate() {
        if (this.variantGroupUid == null || this.variantGroupUid.isEmpty())
            throw new IllegalAccessError(ErrorMessages.VARIANT_GROUP_UID_REQUIRED);
    }

    /**
     * Sets the branches for the variant group using a List of branch names.
     * These branches will be used when linking or unlinking content types to the variant group.
     * 
     * @param branches A List of String values representing the branch names
     * @return The current VariantGroup instance for method chaining
     */
    public VariantGroup setBranches(List<String> branches) {
        this.branches = branches;
        return this;
    }

    /**
     * Sets the branches for the variant group using varargs (variable number of arguments).
     * This is a convenience method that allows passing branch names directly as arguments.
     * These branches will be used when linking or unlinking content types to the variant group.
     * 
     * @param branches Variable number of String arguments representing branch names
     * @return The current VariantGroup instance for method chaining
     */
    public VariantGroup setBranches(String... branches) {
        this.branches = Arrays.asList(branches);
        return this;
    }

     /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of VariantGroup
     */
    @Override
    public VariantGroup addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * @param key   The key parameter is a string that represents the name or
     *              identifier of the header.
     *              It is used to specify the type of information being sent in the
     *              header.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return instance of VariantGroup
     */
    @Override
    public VariantGroup addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of VariantGroup
     */
    @Override
    public VariantGroup addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }


    /**
     * @param headers The "params" parameter is a HashMap that maps String keys to
     *                Object values. It is
     *                annotated with @NotNull, indicating that it cannot be null.
     * @return instance of VariantGroup
     */
    @Override
    public VariantGroup addParams(@NotNull HashMap<String, Object> headers) {
        this.params.putAll(headers);
        return this;
    }


    /**
     * clears all params in the request
     */
    protected void clearParams() {
        this.params.clear();
    }

    /**
     * Retrieves a list of all variant groups.
     * This method does not require a variant group UID to be set.
     * 
     * @return A Call object that can be executed to perform the API request to fetch all variant groups
     */
    public Call<ResponseBody> find() {
        return this.service.fetchVariantGroups(this.headers, this.params);
    }

    /**
     * Links content types to the variant group.
     * 
     * @param contentTypeUids Array of content type UIDs to link to the variant group
     * @return A Call object that can be executed to perform the API request
     * @throws IllegalAccessError if the variant group UID is not set
     * @throws IllegalArgumentException if contentTypeUids is empty
     */
    public Call<ResponseBody> linkContentTypes(@NotNull String... contentTypeUids) {
        if (contentTypeUids.length == 0) {
            throw new IllegalArgumentException("Content type UIDs cannot be empty");
        }
        return updateContentTypeLinks(contentTypeUids, true);
    }

    /**
     * Unlinks content types from the variant group.
     * 
     * @param contentTypeUids Array of content type UIDs to unlink from the variant group
     * @return A Call object that can be executed to perform the API request
     * @throws IllegalAccessError if the variant group UID is not set
     * @throws IllegalArgumentException if contentTypeUids is empty
     */
    public Call<ResponseBody> unlinkContentTypes(@NotNull String... contentTypeUids) {
        if (contentTypeUids.length == 0) {
            throw new IllegalArgumentException("Content type UIDs cannot be empty");
        }
        return updateContentTypeLinks(contentTypeUids, false);
    }

    /**
     * Updates the linking status of content types to a variant group.
     * This private method handles both linking and unlinking operations.
     * 
     * @param contentTypeUids Array of content type UIDs to update
     * @param isLink true to link content types, false to unlink
     * @return A Call object that can be executed to perform the API request
     * @throws IllegalAccessError if the variant group UID is not set
     */
    @SuppressWarnings("unchecked")
    private Call<ResponseBody> updateContentTypeLinks(String[] contentTypeUids, boolean isLink) {
        validate();
        
        // Construct the request body
        JSONObject requestBody = new JSONObject();
        JSONArray contentTypes = new JSONArray();
        JSONArray branches = new JSONArray();
        for (String branch : this.branches) {
            branches.add(branch);
        }
        
        for (String uid : contentTypeUids) {
            JSONObject contentType = new JSONObject();
            contentType.put("uid", uid);
            contentType.put("status", isLink ? "linked" : "unlinked");
            contentTypes.add(contentType);
        }
        requestBody.put("uid", this.variantGroupUid);
        requestBody.put("branches", branches);
        requestBody.put("content_types", contentTypes);
        return this.service.updateVariantGroupContentTypes(this.headers, this.variantGroupUid, this.params, requestBody);
    }
}