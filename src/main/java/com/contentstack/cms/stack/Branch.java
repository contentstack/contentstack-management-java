package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;


/**
 * Branches allows you to isolate and easily manage your “in-progress” work from your stable, live work in the
 * production environment. It helps multiple development teams to work in parallel in a more collaborative, organized,
 * and structured manner without impacting each other.
 *
 * @author ishaileshmishra
 * @version 1.0.0
 * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#branches">About Branches
 * </a>
 * @since 2022-05-19
 */
public class Branch {

    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected final BranchService service;
    private String branchUid;

    protected Branch(Retrofit instance) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.params = new HashMap<>();
        this.service = instance.create(BranchService.class);
    }

    protected Branch(Retrofit instance, String uid) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.branchUid = uid;
        this.params = new HashMap<>();
        this.service = instance.create(BranchService.class);
    }

    void validate() {
        if (this.branchUid == null || this.branchUid.isEmpty())
            throw new IllegalStateException("Branch uid can not be null or empty");
    }

    /**
     * Sets header for the request
     *
     * @param key
     *         header key for the request
     * @param value
     *         header value for the request
     */
    public void addHeader(@NotNull String key, @NotNull Object value) {
        this.headers.put(key, value);
    }

    /**
     * Sets header for the request
     *
     * @param key
     *         query param key for the request
     * @param value
     *         query param value for the request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
    }


    /**
     * Set header for the request
     *
     * @param key
     *         Removes query param using key of request
     */
    public void removeParam(@NotNull String key) {
        this.params.remove(key);
    }


    /**
     * To clear all the query params
     */
    protected void clearParams() {
        this.params.clear();
    }


    /**
     * The Get all branches request returns comprehensive information of all the branches available in a particular
     * stack in your account.
     * <p>
     * Example:file_size
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-branches">Get all
     * branches</a>
     * @since 1.0.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * The Get a single branch request returns information of a specific branch.
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-branch">
     * Get a single branch</a>
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.single(this.headers, this.branchUid);
    }

    /**
     * The Create a branch request creates a new branch in a particular stack of your organization.
     *
     * @param body
     *         the request body
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-branch">Create a
     * branch</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject body) {
        return this.service.create(this.headers, body);
    }

    /**
     * The Get assets and folders of a parent folder retrieves details of both assets and asset subfolders within a
     * specific parent asset folder.
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-a-branch">Delete a
     * branch</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.delete(this.headers, this.branchUid, this.params);
    }

}
