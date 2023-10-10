package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

/**
 * The Get all items in a Release request retrieves a list of all items (entries
 * and assets) that are part of a specific
 * Release and perform CRUD operations on it.
 * <p>
 * Read more about <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#release-items">Release
 * Items</a>
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-22
 */
public class ReleaseItem implements BaseImplementation<ReleaseItem> {

    protected final ReleaseService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private final String releaseUid;

    protected ReleaseItem(Retrofit retrofit, String releaseUid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.releaseUid = releaseUid;
        this.service = retrofit.create(ReleaseService.class);
    }

    void validate() {
        if (this.releaseUid == null || this.releaseUid.isEmpty())
            throw new IllegalAccessError("Release Uid can not be null or empty");
    }


    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of {@link ReleaseItem}
     */
    @Override
    public ReleaseItem addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of {@link ReleaseItem}
     */
    @Override
    public ReleaseItem addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of {@link ReleaseItem}
     */
    @Override
    public ReleaseItem addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of {@link ReleaseItem}
     */
    @Override
    public ReleaseItem addHeaders(@NotNull HashMap<String, String> headers) {
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
     * The <b>Get all items in a Release</b> request retrieves a list of all items
     * (entries and assets) that are part of
     * a specific Release.
     * <p>
     * When executing the API request, you need to provide the Release UID
     *
     * @return Call
     */
    public Call<ResponseBody> find() {
        validate();
        return this.service.fetch(this.headers, this.releaseUid, this.params);
    }

    /**
     * Add a single item to a Release request allows you to add an item (entry or
     * asset) to a Release.
     * <p>
     * When executing the API request, you need to provide the Release UID. In the
     * 'Body' section, you need to provide
     * the details of the item such as the UID, version (of the entry), content type
     * UID (of an entry), the action to be
     * performed (publish/un-publish), and the locale of the item.
     *
     * @param jsonBody requestBody for create/add single Item
     * @return Call
     */
    public Call<ResponseBody> create(@NotNull JSONObject jsonBody) {
        validate();
        return this.service.addItem(this.headers, this.releaseUid, this.params, jsonBody);
    }

    /**
     * Add multiple items to a Release request allows you to add multiple items
     * (entries and/or assets) to a Release.
     * <p>
     * When executing the API request, you need to provide the Release UID. In the
     * 'Body' section, you need to provide
     * the details of the items such as their UIDs, versions (in case of entries),
     * content type UIDs (in case of
     * entries), the action to be performed (publish/un-publish), and the locales of
     * the items.
     *
     * @param jsonBody requestBody for create/add single Item
     * @return Call
     */
    public Call<ResponseBody> createMultiple(@NotNull JSONObject jsonBody) {
        validate();
        return this.service.addItems(this.headers, this.releaseUid, this.params, jsonBody);
    }

    /**
     * The Update Release items to their latest versions request let you update all
     * the release items (entries and
     * assets) to their latest versions before deployment
     * <p>
     * <b>Note:</b> You need to use either the stack's Management Token or the user
     * Authtoken (anyone is mandatory), along with the stack API key, to make a
     * valid Content Management API request.
     * Read more about authentication
     * <br>
     * <br>
     * In case an un-localized entry in the release has been localized later, this
     * request will update the entry to the
     * latest localized version. For example, if you add an un-localized entry to a
     * release and later localize it to the
     * French (France) language, this API request will update the release with the
     * localized French version of the
     * entry.
     * <p>
     * <b>Note:</b> You cannot update the release items under the following
     * scenarios:
     * <p>
     * If the updated version of an entry has new references, this API request
     * doesn't automatically add the references
     * to the release. You need to add them manually.
     * <p>
     * You cannot update the items in a release once you deploy it.
     * <p>
     * If the latest version of an entry is in the in-progress state, this API
     * request doesn't update the entry.
     *
     * <pre>
     * {
     * "items":[
     * "$all"
     * ]
     * }
     * </pre>
     *
     * @param jsonBody requestBody for create/add single Item, In the <b>Body</b>
     *                 section, you need to specify the following:
     * @return Call
     */
    public Call<ResponseBody> update(@NotNull JSONObject jsonBody) {
        validate();
        return this.service.updateItems(this.headers, this.releaseUid, this.params, jsonBody);
    }

    /**
     * The Remove an item from a Release request removes one or more items (entries
     * and/or assets) from a specific
     * Release.
     * <p>
     * When executing the API request, provide the Release UID. In the <b>Body</b>
     * section, you need to provide the
     * details of the item such as their UIDs, version (of the entry), content type
     * UID (of an entry), the action to be
     * performed (publish/un-publish), and the locale of the item.
     * <p>
     * OR
     * <p>
     * To Delete multiple items from a Release request deletes one or more items
     * (entries and/or assets) from a specific
     * Release.
     * <p>
     * When executing the API request, provide the Release UID. In the <b>Body</b>
     * section, you need to provide the UIDs
     * of the items along with details such as their locale, versions, the action to
     * be performed on the items
     * (publish/un-publish), and content type UID of entries (if any).
     *
     * @return Call
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.removeItem(this.headers, this.releaseUid);
    }

}
