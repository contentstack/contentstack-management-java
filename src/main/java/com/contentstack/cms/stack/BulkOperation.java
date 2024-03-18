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
 * You can perform bulk operations such as Publish, Unpublished, and Delete on
 * multiple entries or assets, or Change the
 * Workflow Details of multiple entries or assets at the same time
 * <br>
 * <b>Additional Resource:</b> You can also learn how to <a href=
 * "https://www.contentstack.com/docs/content-managers/search-content/about-bulk-operations-on-search-results"></a>
 * Perform
 * bulk operations on search results
 * <br>
 *
 * @author ***REMOVED***
 * @version v1.0.0
 * @see <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#bulk-publish-operation">
 * Bulk Operations Queue </a>
 * @since 2023 -08-23
 */
public class BulkOperation implements BaseImplementation<BulkOperation> {

    /**
     * The Service.
     */
    protected final BulkOperationService service;
    /**
     * The Headers.
     */
    protected HashMap<String, Object> headers;
    /**
     * The Params.
     */
    protected HashMap<String, Object> params;
    private final Retrofit retrofit;

    /**
     * Instantiates a new Bulk operation.
     *
     * @param retrofit the retrofit
     */
    protected BulkOperation(Retrofit retrofit,Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.retrofit = retrofit;
        this.service = this.retrofit.create(BulkOperationService.class);
    }

    /**
     * The Publish entries and assets in bulk request allows you to publish multiple
     * entries and assets at the same
     * time.
     *
     * <p>
     * <b>Note:</b> At a time, you can publish 10 entries in 10 languages and on 10
     * environments.
     * </p>
     *
     * <p>
     * You can apply queries to filter the results. Refer to the Queries section for
     * more details.
     * </p>
     *
     * <p>
     * In the <b>Request Body Param</b>, you need to specify the locales (mention
     * the locale codes) and environments
     * (mention the names) to which you want to publish the entries or assets. If
     * you do not specify a source locale,
     * the entries or assets will be published in the master locale automatically.
     * </p>
     *
     * <p>
     * <b>api_version:</b> You can publish data specific to api_version using
     * {@link #addHeader(String, String)}.
     * </p>
     *
     * @param body The JSON object containing the data to be published.
     * @return Call object for the API request.
     * @see <a
     * href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-entries-and-assets-in-bulk">
     * Publish entries and assets in bulk </a>
     * @see #addHeader(String, String) #addHeader(String, String)to add headers in
     * @see #addParam(String, Object) #addParam(String, Object)to add query
     * @since 1.0.0
     */
    public Call<ResponseBody> publish(@NotNull JSONObject body) {
        return this.service.publish(this.headers, this.params, body);
    }

    /**
     * The Unpublish entries and assets in bulk request allows you to unpublish
     * multiple entries and assets at the same
     * time.
     * <p>
     * <b>Note:</b> At a time, you can unpublish 10 entries in 10 languages and on
     * 10 environments. Add additional
     * query parameters to the request using {@link #addParam(String, Object)}
     * <p>
     * In the <b>body</b>, you need to specify the locales (mention the locale
     * codes) and environments (mention the
     * names) to which you want to unpublish the entries or assets. If you do not
     * specify a source locale, the entries
     * or assets will be unpublished in the master locale automatically.
     * <p>
     * <b>Tip</b> To schedule the unpublished of multiple entries and/or assets, you
     * can make use of the <b>Create a
     * Release</b> request. Then, you can deploy this Release and all the pinned
     * items can be unpublished together
     * either immediately or at a scheduled time to whatever environment you choose.
     * <p>
     * If some of the entries added to the bulk unpublish request do not satisfy the
     * applied to publish rules, then all
     * the items will not be unpublished. To unpublish at least the items that
     * satisfy the publish rules, pass
     * additional query parameters, skip_workflow_stage_check=true and
     * approvals=true.
     *
     * @param body the body
     * @return Call call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#bulk-unpublish-operation">Bulk
     * Unpublish Operation </a>
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query
     * parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> unpublish(@NotNull JSONObject body) {
        return this.service.unpublish(this.headers, this.params, body);
    }

    /**
     * The Delete entries and assets in bulk request allows you to delete multiple
     * entries and assets at the same time.
     * <p>
     * <b>Note:</b> At a time, you can delete 10 entries in a bulk delete request.
     * <p>
     * In the 'Body' section, you need to specify the content type UIDs, entry UIDs
     * or asset UIDs, and locales of which
     * the entries or assets you want to delete.
     * <p>
     * <b>Note:</b> You need to use either the stack Management Token or the user
     * Authtoken (any one is mandatory),
     * along with the stack API key, to make a valid Content Management API request.
     * Read more about authentication.
     *
     * @param body the body
     * @return Call call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-entries-and-assets-in-bulk">Bulk
     * Delete Operation </a>
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query
     * parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete(JSONObject body) {
        return this.service.delete(this.headers, this.params, body);
    }

    /**
     * The <b>Change Workflow Details</b> action is a new option that allows you to
     * change workflow details (such as
     * stage, assignee, due date, and comments) of multiple entries at the same
     * time.
     * <p>
     * <b>Note:</b> At a time, you can delete 10 entries in a bulk delete request.
     * <p>
     * The Update workflow details in bulk request allows you to update the workflow
     * details for multiple entries at the
     * same time.
     * <p>
     * <b>Note:</b> Note: You can change the workflow stage of multiple entries only
     * if all the entries have been
     * assigned the same workflow stage and are associated with the same workflow.
     * <p>
     *
     * @param body the body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-entries-and-assets-in-bulk">Bulk
     * Delete Operation </a>
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query
     * parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> updateWorkflow(@NotNull JSONObject body) {
        return this.service.updateWorkflowDetails(this.headers, this.params, body);
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key   the key of the header to be added
     * @param value the value of the header to be added
     * @return a new {@link BulkOperation} object with the specified param added
     * @throws NullPointerException if the key or value argument is null
     */
    @Override
    public BulkOperation addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * Adds a header with the specified key and value to this location and returns
     * the updated location.
     *
     * @param key   the key of the header to be added
     * @param value the value of the header to be added
     * @return a new {@link BulkOperation} object with the specified header added
     * @throws NullPointerException if the key or value argument is null
     */
    @Override
    public BulkOperation addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated
     * location.
     *
     * @param params a {@link HashMap} containing the parameters to be added
     * @return a new {@link BulkOperation} object with the specified parameters added
     * @throws NullPointerException if the params argument is null
     */
    @Override
    public BulkOperation addParams(@NotNull HashMap params) {
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
    public BulkOperation addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }

}
