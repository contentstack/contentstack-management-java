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
 * The Merging Branches functionality enables you to merge two branches,
 * integrating the development changes made in the
 * compare branch into the base branch.
 * <br>
 * <b>Merge Branches</b>
 * <br>
 * The Merge branches request merges the specified two branches as per the merge
 * strategy selected.
 * <br>
 * You can pass ignore in the default_merge_strategy query parameter, and pass
 * the item_merge_strategies in the request
 * body to override the default strategy and use a different merge strategy for
 * specific content types or global fields
 * <br>
 * <b>Note:</b>
 * <ul>
 * <li>The merge branches feature is only available for the content types and
 * global fields modules.</li>
 * <li>You can create an additional revert branch beyond the established maximum
 * limit of branches per stack. For instance,
 * if you already have reached the maximum limit of branches in your stack, you
 * can perform a merge operation,
 * provided that you manually delete the backup branch or any other branch
 * before attempting the next merge.
 * </li>
 * </ul>
 *
 * @author ***REMOVED***
 * @version v1.0.0
 * @see <a href=
 *      "https://stag-www.contentstack.com/docs/developers/apis/content-management-api/#merge-branches">Merge
 *      Branches </a>
 * @since 2023 -June-30
 */
public class Merge implements BaseImplementation {

    /**
     * The Headers.
     */
    protected final Map<String, String> headers;
    /**
     * The Params.
     */
    protected Map<String, Object> params;
    /**
     * The Service.
     */
    protected final MergeService service;

    /**
     * Instantiates a new Merge.
     *
     * @param instance
     *                 the instance
     */
    protected Merge(Retrofit instance) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.params = new HashMap<>();
        this.service = instance.create(MergeService.class);
    }

    /**
     * Instantiates a new Merge.
     *
     * @param instance
     *                 the instance
     * @param uid
     *                 the uid
     */
    protected Merge(Retrofit instance, String uid) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.params = new HashMap<>();
        this.params.put("base_branch", uid);
        this.service = instance.create(MergeService.class);
    }

    /**
     * The Merge branches request merges the specified two branches as per the merge
     * strategy selected.
     * <br>
     * <b>Additional Resource:</b> To learn how to select and use the merge
     * strategies, refer to our documentation on
     * Merging Branches.
     * <br>
     * You can pass ignore in the default_merge_strategy query parameter, and pass
     * the item_merge_strategies in the
     * request body to override the default strategy and use a different merge
     * strategy for specific content types or
     * global fields.
     * <br>
     * <b>Note:</b>
     * <br>
     * <ul>
     * <li>
     * The merge branches feature is only available for the content types and global
     * fields modules.
     * </li>
     * <li>
     * You can create an additional revert branch beyond the established maximum
     * limit of branches per stack. For instance,
     * if you already have reached the maximum limit of branches in your stack, you
     * can perform a merge operation,
     * provided that you manually delete the backup branch or any other branch
     * before attempting the next merge.
     * </li>
     * </ul>
     *
     * @param compareBranch
     *                             The branch from which you want to merge changes
     *                             into the base branch.
     * @param requestBody
     *                             the request body
     * @param defaultMergeStrategy
     *                             Specify the merge strategy to apply for the merge
     *                             action. <b>Example:</b> merge_prefere_base
     * @param mergeComment
     *                             The comment to be displayed for the merge action
     * @return Call
     */
    public Call<ResponseBody> branch(@NotNull String compareBranch, JSONObject requestBody,
            @NotNull String defaultMergeStrategy, @NotNull String mergeComment) {
        this.params.put("compare_branch", compareBranch);
        this.params.put("default_merge_strategy", defaultMergeStrategy);
        this.params.put("merge_comment", mergeComment);
        return this.service.mergeBranches(this.headers, requestBody, this.params);
    }

    /**
     * The Get all merge jobs request returns a list of all the recent merge jobs
     * within a specific period.
     * <br>
     *
     * @return the call
     * @see <a href=
     *      "https://stag-www.contentstack.com/docs/developers/apis/content-management-api/#get-all-merge-jobs"></a>Get
     *      all
     *      Merge Jobs
     */
    public Call<ResponseBody> find() {
        return this.service.find(this.headers, this.params);
    }

    /**
     * The Get single merge job request returns the status and configuration details
     * of a particular merge job.
     * <br>
     *
     * @param mergeJobUid
     *                    the key of the header to be added
     * @return the call
     * @see <a href=
     *      "https://stag-www.contentstack.com/docs/developers/apis/content-management-api/#get-single-merge-job"></a>Get
     *      a
     *      Single Merge Job
     */
    public Call<ResponseBody> fetch(@NotNull String mergeJobUid) {
        return this.service.fetch(this.headers, mergeJobUid, this.params);
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key
     *              the key of the header to be added
     * @param value
     *              the value of the header to be added
     * @return a new {@link Merge} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @Override
    public Merge addParam(@NotNull String key, @NotNull Object value) {
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
     * @return a new {@link Merge} object with the specified header added
     * @throws NullPointerException
     *                              if the key or value argument is null
     */
    @Override
    public Merge addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param params
     *               a {@link HashMap} containing the parameters to be added
     * @return a new {@link Merge} object with the specified parameters added
     * @throws NullPointerException
     *                              if the params argument is null
     */
    @Override
    public Merge addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param headers a {@link HashMap} containing the parameters to be added
     * @throws NullPointerException if the params argument is null
     */
    @Override
    public void addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
    }

}
