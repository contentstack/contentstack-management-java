package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * The Publish Queue displays the historical and current details of activities
 * such as publish, unpublish, or delete
 * that can be performed on entries and/or assets. It also shows details of
 * Release deployments. These details include
 * time, entry, content type, version, language, user, environment, and status.
 * <br>
 * <br>
 * For more details, refer the <b>Publish Queue documentation</b>.
 * <br>
 * <br>
 * You can now pass the branch header in the API request to fetch or manage
 * modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter
 * to true to include the branch top-level
 * key in the response. This key specifies the unique ID of the branch where the
 * concerned Contentstack module resides.
 * <br>
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @see <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-queue">
 * Publish
 * Queue
 * </a>
 * @since 2022-10-22
 */
public class PublishQueue implements BaseImplementation<PublishQueue> {

    protected final PublishQueueService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private String publishQueueUid;
    private final Retrofit retrofit;

    protected PublishQueue(Retrofit retrofit) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.retrofit = retrofit;
        this.service = this.retrofit.create(PublishQueueService.class);
    }

    protected PublishQueue(Retrofit retrofit, String uid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.retrofit = retrofit;
        this.publishQueueUid = uid;
        this.service = this.retrofit.create(PublishQueueService.class);
    }

    void validate() {
        if (this.publishQueueUid == null || this.publishQueueUid.isEmpty())
            throw new IllegalAccessError("Publish Queue Uid can not be null or empty");
    }


    /**
     * Sets header for the request
     *
     * @param key   query param key for the request
     * @param value query param value for the request
     */
    @Override
    public PublishQueue addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of the {@link PublishQueue}
     */
    @Override
    public PublishQueue addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of the {@link PublishQueue}
     */
    @Override
    public PublishQueue addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of the {@link PublishQueue}
     */
    @Override
    public PublishQueue addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * Set header for the request
     *
     * @param key Removes query param using key of request
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
     * The Get publish queue request returns comprehensive information on activities
     * such as publish, unpublish, and
     * delete that have performed on entries and/or assets. This request also
     * includes the details of the release
     * deployments in the response body.
     * <p>
     * <b>Note:</b> You can retrieve the publish queue details for activities
     * performed on entries and/or assets of your stack in the last 30 days.
     * <p>
     * You can apply queries to filter the results. Refer to the Queries section for
     * more details.
     * <p>
     * Note: In the Header, you need to pass either the stacks Management Token
     * (highly recommended) or the user
     * Authtoken, along with the stack API key, to make valid Content Management API
     * requests. For more information,
     * refer to Authentication.
     * <p>
     * You can add headers to the request by using {@link #addParam(String, Object)}
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-publish-queue">Get
     * publish queue
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * Get publish queue activity request returns comprehensive information on a
     * specific publish, unpublish, or delete
     * action that was performed on an entry and/or asset. You can also retrieve
     * details of a specific release
     * deployment.
     * <p>
     * Add addional query parameters to the request using
     * {@link #addParam(String, Object)}
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-publish-queue-activity">Get
     * publish queue activity
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetchActivity() {
        validate();
        return this.service.fetchActivity(this.headers, this.publishQueueUid, this.params);
    }

    /**
     * The <b>Cancel Scheduled Action</b> request will allow you to cancel any
     * scheduled publishing or unpublishing
     * activity of entries and/or assets and also cancel the deployment of releases
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#cancel-scheduled-action">Cancel
     * scheduled action
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> cancelScheduledAction() {
        validate();
        return this.service.cancelScheduledAction(this.headers, this.publishQueueUid, this.params);
    }
}
