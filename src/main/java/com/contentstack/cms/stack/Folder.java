package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

public class Folder {


    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected final AssetService service;

    protected Folder(Retrofit instance, @NotNull Map<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.headers.putAll(stackHeaders);
        params = new HashMap<>();
        this.service = instance.create(AssetService.class);
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
     * The Get a single folder call gets the comprehensive details of a specific asset folder by means of folder UID.
     * <p>
     * When executing the API call to search for a subfolder, you need to provide the parent folder UID.
     *
     * @param folderUid
     *         folder uid provide query using <p>#addParam query parameters - include_path(optional) Set this parameter
     *         to ‘true’ to retrieve the complete path of the folder. The path will be displayed as an array of objects
     *         which includes the names and UIDs of each parent folder.
     *         <p>
     *         Example:false
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#unpublish-an-asset">
     * Unpublish An Asset</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch(
            @NotNull String folderUid) {
        return this.service.singleFolder(this.headers, folderUid, this.params);
    }

    /**
     * The Create a folder call is used to create an asset folder and/or add a parent folder to it (if required).
     * <p>
     * In the ‘Body’ section, you need to provide a name for the new folder.
     * <p>
     * If you want to place this folder within another folder, provide the UID of the parent folder in the Request body
     * as follows:
     * <p>
     * { "asset": { "name": "asset_folder_name", "parent_uid": "asset_parent_folder_uid" } }
     *
     * <b>Note:</b> Here are some points that needs to be considered when executing
     * this API request:
     * <p>
     * A maximum of 300 folders can be created. The maximum level of folder nesting is 5. When nesting folder, you
     * cannot nest a folder within the same folder or within its child folders
     *
     * @param requestBody
     *         JSONObject request body
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-folder"> Create
     * a folder</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@Nullable JSONObject requestBody) {
        return this.service.createFolder(this.headers, this.params, requestBody);
    }

    /**
     * The Update or move folder request can be used either to update the details of a folder or set the parent folder
     * if you want to move a folder under another folder.
     * <p>
     * When executing the API request, provide the UID of the folder that you want to move/update.
     * <p>
     * In the ‘Body’ section, you need to provide a new name for your folder, and if you want to move your folder within
     * another folder, then you need provide the UID of the parent folder.
     *
     * <b>Note:</b> Here are some points that needs to be considered when executing
     * this API request:
     * <p>
     * A maximum of 300 folders can be created. The maximum level of folder nesting is 5. When nesting folder, you
     * cannot nest a folder within the same folder or within its child folders.
     *
     * @param folderUid
     *         The UID of the folder that you want to either update or move
     * @param requestBody
     *         JSONObject request body { "asset": { "name": "Demo" } }
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-or-move-folder">
     * Update ORr Move Folder</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull String folderUid, @Nullable JSONObject requestBody) {
        return this.service.updateFolder(this.headers, folderUid, this.params, requestBody);
    }

    /**
     * Delete a folder call is used to delete an asset folder along with all the assets within that folder.
     * <p>
     * When executing the API call, provide the parent folder UID.
     *
     * @param folderUid
     *         The UID of the asset folder that you want to delete
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-a-folder">
     * Delete A Folder</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String folderUid) {
        return this.service.deleteFolder(this.headers, folderUid);
    }

}
