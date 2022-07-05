package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * You can pin a set of entries and assets (along with the deploy action, i.e., publish/unpublish) to a ‘release’, and
 * then deploy this release to an environment. This will publish/unpublish all the the items of the release to the
 * specified environment. Read more about Releases.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides
 * <p>
 * Read more about <a
 * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#releases">Releases</a>
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Release {

    protected final ReleaseService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private final Retrofit retrofit;

    protected Release(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.retrofit = retrofit;
        this.service = this.retrofit.create(ReleaseService.class);
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
     * The Get all Releases request retrieves a list of all Releases of a stack along with details of each Release.
     * <p>
     *
     * @return Call
     */
    public Call<ResponseBody> getAll() {
        return this.service.getReleases(this.headers, this.params);
    }

    /**
     * The Get a single Release request gets the details of a specific Release in a stack.
     * <p>
     * When executing the API request, provide the Release UID as parameter
     *
     * @param releaseUid
     *         The unique ID of the release of which you want to retrieve the details
     * @return Call
     */
    public Call<ResponseBody> fetch(@NotNull String releaseUid) {
        return this.service.getRelease(this.headers, releaseUid);
    }

    /**
     * To <b>Create a Release request</b> allows you to create a new Release in your stack. To add entries/assets to a
     * Release, you need to provide the UIDs of the entries/assets in <b>items</b> in the request body.
     *
     * @param requestBody
     *         The details of the delivery role in @{@link JSONObject} format
     * @return Call
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.createRelease(this.headers, requestBody);
    }

    /**
     * To Update a Release call allows you to update the details of a Release, i.e., the ‘name’ and ‘description’.
     * <p>
     * When executing this API request, provide the Release UID as parameter. In the 'Body' section, you need to provide
     * the new name and description of the Release that you want to update.
     *
     * @param releaseUid
     *         The UID of the role that you want to retrieve
     * @param requestBody
     *         The body should be of @{@link JSONObject} type
     * @return Call
     */
    public Call<ResponseBody> update(
            @NotNull String releaseUid, @NotNull JSONObject requestBody) {
        return this.service.updateRelease(this.headers, releaseUid, requestBody);
    }

    /**
     * To Delete a Release request allows you to delete a specific Release from a stack.
     * <p>
     * When executing the API request, provide the Release UID.
     *
     * @param releaseUid
     *         The UID of the role that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> delete(@NotNull String releaseUid) {
        return this.service.deleteRelease(this.headers, releaseUid);
    }


    /**
     * The Get all items in a Release request retrieves a list of all items (entries and assets) that are part of a
     * specific Release and perform CRUD operations on it.
     * <p>
     * Read more about <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#release-items">Release Items</a>
     *
     * @param releaseUid
     *         The UID of the role that you want to retrieve
     * @return ReleaseItem
     */
    public ReleaseItem item(@NotNull String releaseUid) {
        return new ReleaseItem(this.retrofit, this.headers, releaseUid);
    }


    /**
     * The Deploy a Release request deploys a specific Release to specific environment(s) and locale(s).
     * <p>
     * When executing the API request, provide the Release UID. In the <b>Body</b> section, you need to provide the
     * details of the Release that you want to deploy. For example, you need to provide the action, environment(s), and
     * the locale(s) on which the Release should be deployed.
     * <p>
     *
     * @param releaseUid
     *         unique ID of the release that you want to deploy
     * @param requestBody
     *         The JSONObject request body
     * @return Call
     */
    public Call<ResponseBody> deploy(
            @NotNull String releaseUid, @NotNull JSONObject requestBody) {
        return this.service.deploy(this.headers, releaseUid, requestBody);
    }

    /**
     * The Clone a Release request allows you to clone (make a copy of) a specific Release in a stack.
     * <p>
     * When executing the API request, provide the Release UID. In the <b>Body</b> section, you need to provide the new
     * name and description of the cloned Release.
     *
     * @param releaseUid
     *         unique ID of the release that you want to clone
     * @param requestBody
     *         The JSONObject request body
     * @return Call
     */
    public Call<ResponseBody> clone(
            @NotNull String releaseUid, @NotNull JSONObject requestBody) {
        return this.service.clone(this.headers, releaseUid, requestBody);
    }


}
