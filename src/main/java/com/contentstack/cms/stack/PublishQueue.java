package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * The Publish Queue displays the historical and current details of activities such as publish, unpublish, or delete
 * that can be performed on entries and/or assets. It also shows details of Release deployments. These details include
 * time, entry, content type, version, language, user, environment, and status.
 * <p>
 * For more details, refer the Publish Queue documentation.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides.
 * <p>
 * Read more about <a
 * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-queue">Publish Queue</a>
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class PublishQueue {

    protected final PublishQueueService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private final Retrofit retrofit;

    protected PublishQueue(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.retrofit = retrofit;
        this.service = this.retrofit.create(PublishQueueService.class);
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
     *         header key for the request
     * @param value
     *         header value for the request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
    }


    /**
     * Sets header for the request
     *
     * @param key
     *         header key for the request
     */
    public void removeParam(@NotNull String key) {
        this.params.remove(key);
    }


    /**
     * To clear all the params
     */
    protected void clearParams() {
        this.params.clear();
    }


    /**
     * The Get publish queue request returns comprehensive information on activities such as publish, unpublish, and
     * delete that have performed on entries and/or assets. This request also includes the details of the release
     * deployments in the response body.
     * <p>
     * <b>Note:</b> You can retrieve the publish queue details for activities performed on entries and/or assets of
     * your stack in the last 30 days.
     * <p>
     * You can apply queries to filter the results. Refer to the Queries section for more details.
     * <p>
     * Note: In the Header, you need to pass either the stacks Management Token (highly recommended) or the user
     * Authtoken, along with the stack API key, to make valid Content Management API requests. For more information,
     * refer to Authentication.
     * <p>
     * You can add headers to the request by using {@link #addParam(String, Object)}
     *
     * @return Call
     */
    public Call<ResponseBody> fetch() {
        return this.service.fetch(this.headers, this.params);
    }


    /**
     * Get publish queue activity request returns comprehensive information on a specific publish, unpublish, or delete
     * action that was performed on an entry and/or asset. You can also retrieve details of a specific release
     * deployment.
     * <p>
     * Add addional query parameters to the request using {@link #addParam(String, Object)}
     *
     * @param publishQueueUid
     *         the UID of a specific publish queue activity of which you want to retrieve the details. Execute the Get
     *         publish queue API request to retrieve the UID of a particular publish queue activity.
     * @return Call
     */
    public Call<ResponseBody> fetchActivity(@NotNull String publishQueueUid) {
        return this.service.fetchActivity(this.headers, publishQueueUid, this.params);
    }


    /**
     * The <b>Cancel Scheduled Action</b> request will allow you to cancel any scheduled publishing or unpublishing
     * activity of entries and/or assets and also cancel the deployment of releases
     *
     * @param publishQueueUid
     *         The UID of the event to be cancelled in the publish queue.
     * @return Call
     */
    public Call<ResponseBody> cancelScheduledAction(String publishQueueUid) {
        return this.service.cancelScheduledAction(this.headers, publishQueueUid, this.params);
    }
}
