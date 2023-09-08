package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An alias acts as a pointer to a particular branch. You can specify the alias
 * ID in your frontend code to pull content
 * from the target branch associated with an alias.
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @see <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#aliases">About
 * Aliases
 * </a>
 * @since 2022-10-20
 */
public class Alias implements BaseImplementation<Alias> {

    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected final AliasService service;
    private String uid;


    // The `protected Alias(Retrofit instance)` constructor is used to create an
    // instance of the `Alias`
    // class. It initializes the `headers` and `params` maps with default values. It
    // also creates an
    // instance of the `AliasService` interface using the provided `Retrofit`
    // instance.
    protected Alias(Retrofit instance) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        params = new HashMap<>();
        this.service = instance.create(AliasService.class);
    }

    // The `protected Alias(Retrofit instance, String aliasUid)` constructor is used
    // to create an
    // instance of the `Alias` class with a specific alias UID.
    protected Alias(Retrofit instance, String aliasUid) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        params = new HashMap<>();
        this.uid = aliasUid;
        this.service = instance.create(AliasService.class);
    }

    /**
     * The addHeader function adds a key-value pair to the headers map.
     *
     * @param key   A string representing the key of the header. This is used to
     *              identify the header when
     *              retrieving or modifying it.
     * @param value The value parameter is of type Object, which means it can accept
     *              any type of object as
     *              its value.
     * @return instance of the `Alias` class
     */
    @Override
    public Alias addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * The addParam function adds a key-value pair to a map.
     *
     * @param key   A string representing the key for the parameter. It is annotated
     *              with @NotNull,
     *              indicating that it cannot be null.
     * @param value The value parameter is of type Object, which means it can accept
     *              any type of object as
     *              its value.
     */
    @Override
    public Alias addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * The addParam function adds a key-value pair to Alias
     *
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of {@link Alias}
     */
    @Override
    public Alias addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * The removeParam function removes a key-value pair from
     *
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of Alias
     */
    @Override
    public Alias addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * The removeParam function removes a parameter from a map using the specified
     * key.
     *
     * @param key The parameter "key" is of type String and is used to specify the
     *            key of the parameter
     *            that needs to be removed from the "params" collection.
     */
    protected void removeParam(@NotNull String key) {
        this.params.remove(key);
    }

    protected void clearParams() {
        this.params.clear();
    }

    /**
     * The Get all aliases request returns comprehensive information of all the
     * aliases available in a particular stack in your account.
     * <p>
     *
     * @return Call
     * @author ***REMOVED***
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-aliases">Get
     * all
     * aliases</a>
     * @since 2022-10-20
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * The Get a single alias request returns information of a specific alias.
     *
     * @return Call
     * @author ***REMOVED***
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-branch">
     * Get a single branch</a>
     * @since 2022-10-20
     */
    public Call<ResponseBody> fetch() {
        Objects.requireNonNull(this.uid, "Global Field Uid can not be null or empty");
        return this.service.single(this.headers, this.uid);
    }

    /**
     * <b>Assign or Update an alias</b>
     * <br>
     * The Assign an alias request creates a new alias in a particular stack of your
     * organization. This alias can point
     * to any existing branch (target branch) of your stack.
     * <p>
     * You can use the same request to update the target branch of an alias. In the
     * “Body” section, you need to provide
     * the UID of the new target branch that will be associated with the alias.
     *
     * @param body the request body
     * @return Call
     * @author ***REMOVED***
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#assign-or-update-an-alias">Update
     * a
     * branch</a>
     * @see #addHeader(String, String) to add headers
     * @since 2022-10-20
     */
    public Call<ResponseBody> update(@NotNull JSONObject body) {
        return this.service.update(this.headers, body);
    }

    /**
     * Delete an alias request deletes an existing alias.
     * <p>
     * To confirm deletion of an alias, you need to specify the force=true query
     * parameter.
     * <p>
     * When executing the API call, in the “URL Parameters” section, provide the UID
     * of your alias.
     *
     * @return Call
     * @author ***REMOVED***
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-an-alias">Delete
     * a branch</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 2022-10-20
     */
    public Call<ResponseBody> delete() {
        Objects.requireNonNull(this.uid, "Global Field Uid can not be null or empty");
        return this.service.delete(this.headers, this.uid, this.params);
    }

}
