package com.contentstack.cms.stack;

import com.contentstack.cms.core.ErrorMessages;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

public class Folder implements BaseImplementation<Folder> {

    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected final AssetService service;
    private String folderUid;

    protected Folder(Retrofit instance,Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        params = new HashMap<>();
        this.service = instance.create(AssetService.class);
    }

    protected Folder(Retrofit instance,Map<String, Object> headers, String folderUid) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        params = new HashMap<>();
        this.folderUid = folderUid;
        this.service = instance.create(AssetService.class);
    }

    void validate() {
        if (this.folderUid == null || this.folderUid.isEmpty())
            throw new IllegalAccessError(ErrorMessages.FOLDER_UID_REQUIRED);
    }

    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of the {@link Folder}
     */
    @Override
    public Folder addParam(@NotNull String key, @NotNull Object value) {
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
     * @return instance of the {@link Folder}
     */
    @Override
    public Folder addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of the {@link Folder}
     */
    @Override
    public Folder addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of the {@link Folder}
     */
    @Override
    public Folder addHeaders(@NotNull HashMap<String, String> headers) {
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
     * The Get a single folder call gets the comprehensive details of a specific
     * asset folder by means of folder UID.
     * <p>
     * When executing the API call to search for a subfolder, you need to provide
     * the parent folder UID.
     *
     * <p>
     * #addParam query parameters - include_path(optional) Set this parameter to
     * ‘true’ to retrieve the complete path of
     * the folder. The path will be displayed as an array of objects which includes
     * the names and UIDs of each parent
     * folder.
     * <p>
     * Example:false
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#unpublish-an-asset">
     * Unpublish An Asset</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.singleFolder(this.headers, this.folderUid, this.params);
    }

    /**
     * The Create a folder call is used to create an asset folder and/or add a
     * parent folder to it (if required).
     * <p>
     * In the ‘Body’ section, you need to provide a name for the new folder.
     * <p>
     * If you want to place this folder within another folder, provide the UID of
     * the parent folder in the Request body
     * as follows:
     * <p>
     * { "asset": { "name": "asset_folder_name", "parent_uid":
     * "asset_parent_folder_uid" } }
     *
     * <b>Note:</b> Here are some points that needs to be considered when executing
     * this API request:
     * <p>
     * A maximum of 300 folders can be created. The maximum level of folder nesting
     * is 5. When nesting folder, you
     * cannot nest a folder within the same folder or within its child folders
     *
     * @param requestBody JSONObject request body
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-folder">
     * Create
     * a folder</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@Nullable JSONObject requestBody) {
        return this.service.createFolder(this.headers, this.params, requestBody);
    }

    /**
     * The Update or move folder request can be used either to update the details of
     * a folder or set the parent folder
     * if you want to move a folder under another folder.
     * <p>
     * When executing the API request, provide the UID of the folder that you want
     * to move/update.
     * <p>
     * In the ‘Body’ section, you need to provide a new name for your folder, and if
     * you want to move your folder within
     * another folder, then you need provide the UID of the parent folder.
     *
     * <b>Note:</b> Here are some points that needs to be considered when executing
     * this API request:
     * <p>
     * A maximum of 300 folders can be created. The maximum level of folder nesting
     * is 5. When nesting folder, you
     * cannot nest a folder within the same folder or within its child folders.
     *
     * @param requestBody JSONObject request body { "asset": { "name": "Demo" } }
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-or-move-folder">
     * Update ORr Move Folder</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@Nullable JSONObject requestBody) {
        validate();
        return this.service.updateFolder(this.headers, this.folderUid, this.params, requestBody);
    }

    /**
     * Delete a folder call is used to delete an asset folder along with all the
     * assets within that folder.
     * <p>
     * When executing the API call, provide the parent folder UID.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-a-folder">
     * Delete
     * A Folder</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        validate();
        return this.service.deleteFolder(this.headers, this.folderUid);
    }

}
