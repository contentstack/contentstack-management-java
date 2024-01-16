package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * Taxonomy is a system that classifies and organizes content in your collection or system.
 * It simplifies finding and retrieving information by categorizing items based on specific
 * criteria like their purpose, audience, or other relevant factors.
 * <p>
 * This hierarchical organization streamlines navigation and search processes.
 */
public class Taxonomy implements BaseImplementation<Taxonomy> {

    private String taxonomyId;
    /**
     * The Taxonomy service.
     */
    final TaxonomyService taxonomyService;
    /**
     * The Headers.
     */
    protected HashMap<String, Object> headers;
    /**
     * The Params.
     */
    protected HashMap<String, Object> params;


    /**
     * Instantiates a new Taxonomy.
     *
     * @param client  the client
     * @param headers the headers
     */
    protected Taxonomy(Retrofit client, HashMap<String, Object> headers) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(headers);
        this.taxonomyService = client.create(TaxonomyService.class);
    }

    /**
     * Instantiates a new Taxonomy.
     *
     * @param client     the client
     * @param headers    the headers
     * @param taxonomyId the taxonomy id
     */
    protected Taxonomy(Retrofit client, HashMap<String, Object> headers, @NotNull String taxonomyId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.taxonomyId = taxonomyId;
        this.headers.putAll(headers);
        this.taxonomyService = client.create(TaxonomyService.class);
    }

    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of Taxonomy
     */
    @Override
    public Taxonomy addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of Taxonomy
     */
    @Override
    public Taxonomy addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of Taxonomy
     */
    @Override
    public Taxonomy addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of Taxonomy
     */
    @Override
    public Taxonomy addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Get all taxonomies call.
     * <p>
     * Query parameters:
     * <ul>
     * <li>
     * include_terms_count: Whether to include the count of terms in the response. Values: true or false.
     * </li>
     * <li>
     * include_referenced_terms_count: Whether to include the count of referenced terms in the response. Values: true or false.
     * </li>
     * <li>
     * include_referenced_entries_count: Whether to include the count of referenced entries in the response. Values: true or false.
     * </li>
     * <li>
     * include_count: Whether to include the total count of taxonomies in the response. Values: true or false.
     * </li>
     * <li>
     * asc or desc: Sort order based on the UID field. Values: field_uid (replace with the actual field UID), or omit for no sorting.
     * </li>
     * <li>
     * typeahead: Search string for typeahead functionality. Values: Any search string.
     * </li>
     * <li>
     * include_deleted: Whether to include deleted taxonomies in the response. Values: true or false.
     * </li>
     * <li>
     * skip: Number of records to skip in pagination. Values: Any non-negative integer.
     * </li>
     * <li>
     * limit: Maximum number of records to return. Values: Any positive integer.
     * </li>
     * </ul>
     *
     * @return the call <b>Example</b> <pre>     {@code
     *     Response<ResponseBody> response = taxonomy.find().execute();
     *     } </pre>
     */
    public Call<ResponseBody> find() {
        return this.taxonomyService.find(this.headers, this.params);
    }

    /**
     * Fetch Single Taxonomy Call.
     * <p>
     * Query Parameters:
     * <ul>
     *     <li>
     *         include_terms_count: Whether to include the count of terms in the response. Values: true or false.
     *     </li>
     *     <li>
     *        include_referenced_terms_count: Whether to include the count of terms referred in at least 1 entry. Values: true or false.
     *     </li>
     *     <li>
     *         include_referenced_entries_count: Whether to include the count of entries where at least 1 term of this taxonomy is referred. Values: true or false
     *     </li>
     * </ul>
     *
     * @param taxonomyId the taxonomy id
     * @return the call <b>Example</b> <pre>     {@code
     *     Response<ResponseBody> response = taxonomy.fetch("taxonomyId").execute();
     *     } </pre>
     */
    public Call<ResponseBody> fetch(@NotNull String taxonomyId) {
        return this.taxonomyService.fetch(this.headers, taxonomyId, this.params);
    }

    /**
     * Create Taxonomy call.
     *
     * @param body the body
     * @return the call <b>Example</b> <pre>     {@code
     *     JSONObject body = new JSONObject
     *     Response<ResponseBody> response = taxonomy.create(body).execute();
     *     } </pre>
     */
    public Call<ResponseBody> create(@NotNull JSONObject body) {
        return this.taxonomyService.create(this.headers, body);
    }

    /**
     * Update Taxonomy call.
     *
     * @param taxonomyId - The taxonomy for which we need to update the details
     * @param body       the body
     * @return the call <b>Example</b> <pre>     {@code
     *     JSONObject body = new JSONObject();
     *     JSONObject bodyContent = new JSONObject();
     *     bodyContent.put("name", "Taxonomy 1");
     *     bodyContent.put("description", "Description updated for Taxonomy 1);
     *     body.put("taxonomy", bodyContent);
     *     Response<ResponseBody> response = taxonomy.update("taxonomyId", body).execute();
     *     } </pre>
     */
    public Call<ResponseBody> update(@NotNull String taxonomyId, @NotNull JSONObject body) {
        return this.taxonomyService.update(this.headers, taxonomyId, body);
    }

    /**
     * Delete Taxonomy call.
     *
     * @param taxonomyId - The taxonomy for which we need to update the details
     * @return the call <b>Example</b> <pre>     {@code
     *     Response<ResponseBody> response = taxonomy.delete("taxonomyId").execute();
     *     } </pre>
     */
    public Call<ResponseBody> delete(@NotNull String taxonomyId) {
        return this.taxonomyService.delete(this.headers, taxonomyId);
    }


    /**
     * Clear params for internal uses only for testing
     */
    protected void clearParams() {
        this.params.clear();
    }


    /**
     * Get terms information
     * <p>Examples</p>
     * <pre>
     *     {@code
     *     Term terms = stack("authtoken").taxonomy("taxonomyId").term();
     *     }
     * </pre>
     *
     * @return instance of {@link Terms}
     */
    public Terms terms() {
        return new Terms(this.taxonomyService, this.headers, this.taxonomyId);
    }


    /**
     * Get instance of  taxonomy search filter class instance through which we can query on taxonomy based on Entry
     * <p>Example usage:</p>
     * <pre>JSONObject object = new JSonObject();</pre>
     * <pre>object.put("taxonomies.color", Object)</pre>
     * <pre>Taxonomy taxonomy = stack("authtoken").taxonomy("taxonomyId").filterTaxonomy(object);</pre>
     */
    public Call<ResponseBody> query(JSONObject query) {
        return this.taxonomyService.filterTaxonomy(this.headers, query);
    }

}
