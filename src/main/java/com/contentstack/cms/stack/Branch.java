package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * Branches allows you to isolate and easily manage your “in-progress” work from
 * your stable, live work in the
 * production environment. It helps multiple development teams to work in
 * parallel in a more collaborative, organized,
 * and structured manner without impacting each other.
 *
 * @author ***REMOVED***
 * @version v1.0.0
 * @see <a href=
 *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#branches">About
 *      Branches
 *      </a>
 * @since 2022-10-22
 */
public class Branch implements BaseImplementation {

    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected final BranchService service;
    private Retrofit instance;
    private String baseBranchId;

    protected Branch(Retrofit instance) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.params = new HashMap<>();
        this.service = instance.create(BranchService.class);
    }

    protected Branch(Retrofit instance, String uid) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.baseBranchId = uid;
        this.params = new HashMap<>();
        this.instance = instance;
        this.service = instance.create(BranchService.class);
    }

    void validate() {
        if (this.baseBranchId == null || this.baseBranchId.isEmpty())
            throw new IllegalStateException("Branch uid can not be null or empty");
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key
     *              the key of the header to be added
     * @param value
     *              the value of the header to be added
     * @return a new {@link Branch} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @Override
    public Branch addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key
     *              the key of the header to be added
     * @param value
     *              the value of the header to be added
     * @return a new {@link Branch} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @Override
    public Branch addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param params
     *               a {@link HashMap} containing the parameters to be added
     * @return a new {@link Branch} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @Override
    public Branch addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param headers headers of type {@link HashMap} will be added
     * @throws NullPointerException if the key or value argument is null
     */
    @Override
    public Branch addHeaders(HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * To clear all the query params
     */
    protected void clearParams() {
        this.params.clear();
    }

    /**
     * The Get all branches request returns comprehensive information of all the
     * branches available in a particular
     * stack in your account.
     * <p>
     * Example:file_size
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-branches">Get
     *      all branches</a>
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * The Get a single branch request returns information of a specific branch.
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-branch">
     *      Get a single branch</a>
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.single(this.headers, this.baseBranchId);
    }

    /**
     * The Create a branch request creates a new branch in a particular stack of
     * your organization.
     *
     * @param body
     *             the request body
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-branch">Create
     *      a branch</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject body) {
        return this.service.create(this.headers, body);
    }

    /**
     * The Get assets and folders of a parent folder retrieves details of both
     * assets and asset subfolders within a
     * specific parent asset folder.
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-a-branch">Delete
     *      a branch</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.delete(this.headers, this.baseBranchId, this.params);
    }

    /**
     * With the Comparing Branches functionality, you can compare and check the
     * differences between any two branches.
     * <br>
     * The <b>Compare branches</b> request returns a list of all the differences
     * between two branches
     * <br>
     * <b>Note:</b>
     * <br>
     * <ul>
     * <li>
     * The compare branches feature is only available for the content types and
     * global fields modules.
     * </li>
     * <li>
     * If the number of Content Types/Global Fields that need to be compared is more
     * than 100, you will receive a Next URL in the response body.
     * The comparison limit is set at 100, and for every comparison that goes beyond
     * this limit,
     * the process will be completed in segments of 100.
     * </li>
     * </ul>
     *
     * @param compareBranchId
     *                        the branch you want to compare with the base branch
     * @return instance of @{@link Compare}
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#compare-branches"></a>Compare
     *      Branches
     */
    public Compare compare(@NotNull String compareBranchId) {
        return new Compare(this.instance, this.baseBranchId, compareBranchId);
    }

    /**
     * <b>Merge Branches</b>
     * <br>
     * The Merge branches request merges the specified two branches as per the merge
     * strategy selected.
     * <br>
     * You can pass ignore in the default_merge_strategy query parameter, and pass
     * the item_merge_strategies in the
     * request body to override the default strategy and use a different merge
     * strategy for specific content types or
     * global fields.
     * <br>
     * <b>Note:</b>
     * <ul>
     * <li>
     * The merge branches feature is only available for the content types and global
     * fields modules
     * </li>
     * <li>
     * You can create an additional revert branch beyond the established limit of 10
     * branches per stack.
     * For instance, if you already have 10 branches in your stack, you can perform
     * a merge operation,
     * provided that you manually delete the backup branch or any other
     * branch before attempting the next merge.
     * </li>
     * </ul>
     *
     * @return instance of @{@link Merge}
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/branches/merging-branches/"></a>Merging
     *      Branches
     */
    public Merge mergeQueue() {
        return new Merge(this.instance, this.baseBranchId);
    }

}
