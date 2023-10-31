package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * You can pin a set of entries and assets (along with the deploy action, i.e.,
 * publish/unpublish) to a ‘release’, and
 * then deploy this release to an environment. This will publish/unpublish all
 * the items of the release to the
 * specified environment. Read more about Releases.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage
 * modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter
 * to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the
 * concerned Contentstack module resides
 * <p>
 * Read more about <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#releases">Releases</a>
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-22
 */
public class Release implements BaseImplementation<Release> {

    protected final ReleaseService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    protected String releaseUid;
    private final Retrofit retrofit;

    protected Release(Retrofit retrofit) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.retrofit = retrofit;
        this.service = this.retrofit.create(ReleaseService.class);
    }

    protected Release(Retrofit retrofit, String uid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.releaseUid = uid;
        this.retrofit = retrofit;
        this.service = this.retrofit.create(ReleaseService.class);
    }

    void validate() {
        if (this.releaseUid == null || this.releaseUid.isEmpty())
            throw new IllegalAccessError("Release Uid Can Not Be Null OR Empty");
    }


    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of Release
     */
    @Override
    public Release addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of Release
     */
    @Override
    public Release addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of Release
     */
    @Override
    public Release addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of Release
     */
    @Override
    public Release addHeaders(@NotNull HashMap<String, String> headers) {
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
     * The Get all Releases request retrieves a list of all Releases of a stack
     * along with details of each Release.
     * <p>
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-releases">Get
     * all Releases
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * The Get a single Release request gets the details of a specific Release in a
     * stack.
     * <p>
     * When executing the API request, provide the Release UID as parameter
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-release">Get
     * a singel
     * release
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.single(this.headers, this.releaseUid);
    }

    /**
     * To <b>Create a Release request</b> allows you to create a new Release in your
     * stack. To add entries/assets to a
     * Release, you need to provide the UIDs of the entries/assets in <b>items</b>
     * in the request body.
     *
     * @param requestBody The details of the delivery role in @{@link JSONObject}
     *                    format
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-release">Create
     * a release
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.create(this.headers, requestBody);
    }

    /**
     * To Update a Release call allows you to update the details of a Release, i.e.,
     * the ‘name’ and ‘description’.
     * <p>
     * When executing this API request, provide the Release UID as parameter. In the
     * 'Body' section, you need to provide
     * the new name and description of the Release that you want to update.
     *
     * @param requestBody The body should be of @{@link JSONObject} type
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-a-release">Update
     * a release
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        validate();
        return this.service.update(this.headers, this.releaseUid, requestBody);
    }

    /**
     * To Delete a Release request allows you to delete a specific Release from a
     * stack.
     * <p>
     * When executing the API request, provide the Release UID.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-a-release">Delete
     * a release
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.delete(this.headers, this.releaseUid);
    }

    /**
     * The Get all items in a Release request retrieves a list of all items (entries
     * and assets) that are part of a
     * specific Release and perform CRUD operations on it.
     * <p>
     * Read more about <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#release-items">Release
     * Items</a>
     *
     * @return ReleaseItem
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#release-items">Get
     * a
     * release item
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public ReleaseItem item() {
        validate();
        return new ReleaseItem(this.retrofit, this.releaseUid);
    }

    /**
     * The Deploy a Release request deploys a specific Release to specific
     * environment(s) and locale(s).
     * <p>
     * When executing the API request, provide the Release UID. In the <b>Body</b>
     * section, you need to provide the
     * details of the Release that you want to deploy. For example, you need to
     * provide the action, environment(s), and
     * the locale(s) on which the Release should be deployed.
     * <p>
     *
     * @param requestBody The JSONObject request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#deploy-a-release">Deploy
     * a release
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> deploy(@NotNull JSONObject requestBody) {
        validate();
        return this.service.deploy(this.headers, this.releaseUid, requestBody);
    }

    /**
     * The Clone a Release request allows you to clone (make a copy of) a specific
     * Release in a stack.
     * <p>
     * When executing the API request, provide the Release UID. In the <b>Body</b>
     * section, you need to provide the new
     * name and description of the cloned Release.
     *
     * @param requestBody The JSONObject request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#clone-a-release">Clone
     * all release
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> clone(@NotNull JSONObject requestBody) {
        validate();
        return this.service.clone(this.headers, this.releaseUid, this.params, requestBody);
    }

}
