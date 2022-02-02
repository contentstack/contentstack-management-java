package com.contentstack.cms.assets;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Asset {

    protected final Map<String, String> headers;
    protected final AssetService service;

    public Asset(Retrofit instance, @NotNull String apiKey, @NotNull String managementToken) {
        headers = new HashMap<>();
        // Adding headers to request
        headers.put("Content-Type", "application/json");
        headers.put("api_key", apiKey);
        headers.put("authorization", managementToken);
        this.service = instance.create(AssetService.class);
    }

    /**
     * The Get all assets request returns comprehensive information on all assets
     * available in a stack.
     * <p>
     * You can add queries to extend the functionality of this API call. Under the
     * URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the <b>Queries</b> section of the
     * Content Delivery API doc.
     *
     * @param query
     *              query params for the assets older(optional) Enter either the UID
     *              of a specific folder to get the assets
     *              of that folder, or enter ‘cs_root’ to get all assets and their
     *              folder details from the root folder.
     *              <p>
     *              <p>
     *              include_folders(optional) Set this parameter to ‘true’ to
     *              include the details of the created folders
     *              along with the details of the assets.
     *              <p>
     *              <p>
     *              environment(optional) Enter the name of the environment to
     *              retrieve the assets published on them. You can
     *              enter multiple environments.
     *              <p>
     *              <p>
     *              version(optional) Specify the version number of the asset that
     *              you want to retrieve. If the version is
     *              not specified, the details of the latest version will be
     *              retrieved.
     *              <p>
     *              <p>
     *              include_publish_details(optional) Enter 'true' to include the
     *              publishing details of the entry.
     *              <p>
     *              <p>
     *              include_count(optional) Set this parameter to 'true' to include
     *              the total number of assets available in
     *              your stack in the response body.
     *              <p>
     *              <p>
     *              relative_urls(optional) Set this to 'true' to display the
     *              relative URL of the asset.
     *              <p>
     *              <p>
     *              asc_field_uid(optional) Enter the unique ID of the field for
     *              sorting the assets in ascending order by
     *              that field. Example:created_at
     *              <p>
     *              <p>
     *              desc_field_uid(optional) Enter the unique ID of the field for
     *              sorting the assets in descending order by
     *              that field.
     *              <p>
     *              <p>
     *              Example:file_size
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> fetch(Map<String, Object> query) {
        return this.service.fetch(headers, query);
    }

    /**
     * The Get an asset call returns comprehensive information about a specific
     * version of an asset of a stack
     *
     * <p>
     * <b>Tip:</b> To include the publishing details in the response, make use of
     * the include_publish_details parameter and set its value to ‘true’. This query
     * will return the publishing details
     * of the entry in every environment along with the version number that is
     * published in each of the environment.
     *
     * @param uid
     *                    asset uid
     * @param queryParams
     *                    query parameters for the assets
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> single(
            @NotNull String uid, Map<String, Object> queryParams) {
        return this.service.single(this.headers, uid, queryParams);
    }

    /**
     * The Get assets of a specific folder retrieves all assets of a specific asset
     * folder; however, it doesn't retrieve
     * the details of sub-folders within it.
     *
     * @param folderUid
     *                  uid of specific folder
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> byFolderUid(@NotNull String folderUid) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("folder", folderUid);
        return this.service.specificFolder(this.headers, queryMap);
    }

    /**
     * The Get assets and folders of a parent folder retrieves details of both
     * assets and asset subfolders within a
     * specific parent asset folder.
     *
     * @param folderUid
     *                         folder uid
     * @param isIncludeFolders
     *                         provide true/false
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> subfolder(
            @NotNull String folderUid, Boolean isIncludeFolders) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("folder", folderUid);
        queryMap.put("include_folders", isIncludeFolders);
        return this.service.subfolder(this.headers, queryMap);
    }

    /**
     * The <b>Upload asset</b> request uploads an asset file to your stack.
     * <p>
     * To upload assets from your local system to Contentstack and manage their
     * details, you need to use the following
     * "form-data" parameters:
     *
     * @param requestBody
     *                    Request body for the asset file
     *                    <p>
     *                    asset[upload] (mandatory) Select the input type as 'File'.
     *                    Then, browse and select the asset file that
     *                    you want to import. Supported file types include JPG, GIF,
     *                    PNG, XML, WebP, BMP, TIFF, SVG, and PSD.
     *                    <p>
     *                    asset[parent_uid] (optional) If needed, assign a parent
     *                    folder to your asset by passing the UID of the
     *                    parent folder.
     *                    <p>
     *                    asset[title] (optional) Enter a title for your uploaded
     *                    asset.
     *                    <p>
     *                    asset[description] (optional) Enter a description for your
     *                    uploaded asset.
     *                    <p>
     *                    asset[tags] (optional) Assign a specific tag(s) to your
     *                    uploaded asset.
     * @param query
     *                    The query parameter for the asset to upload
     *                    <p>
     *                    relative_urls(optional) Set this to 'true' to display the
     *                    relative URL of the asset. Example:false
     *
     *                    <p>
     *                    include_dimension(optional) Set this to 'true' to include
     *                    the dimensions (height and width) of the image
     *                    in the response.
     *                    <p>
     *                    Example:true
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> uploadAsset(
            @NotNull String filePath, @NotNull RequestBody requestBody, Map<String, Object> query) {
        MultipartBody.Part assetPath = uploadFile(filePath);
        return this.service.uploadAsset(this.headers, assetPath, requestBody, query);
    }

    private MultipartBody.Part uploadFile(@NotNull String filePath) {
        if (!filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                return MultipartBody.Part.createFormData("asset[upload]", file.getName(), requestFile);
            }
        }
        return null;
    }

    /**
     * The Replace asset call will replace an existing asset with another file on
     * the stack.
     * <p>
     * <b>Tip:</b> You can try the call manually in any REST API client, such as
     * Postman. Under Body, pass a body parameter named asset[upload] and select the
     * input type as 'File'. This will
     * enable you to select the file that you wish to import. You can assign a
     * parent folder to your asset by using the
     * asset[parent_uid] parameter, where you can pass the UID of the parent folder.
     * Additionally, you can pass optional
     * parameters such as asset[title] and asset[description] which let you enter a
     * title and a description for the
     * uploaded asset, respectively.
     *
     * @param uid
     *              asset uid
     * @param query
     *              query parameter
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> replace(String uid, Map<String, Object> query) {
        return this.service.replace(this.headers, uid, query);
    }

    /**
     * Generate Permanent Asset URL request allows you to generate a permanent URL
     * for an asset. This URL remains
     * constant irrespective of any subsequent updates to the asset.
     * <p>
     * In the request body, you need to pass the permanent URL in the following
     * format:
     * <p>
     * { "asset": { "permanent_url":
     * "https://api.contentstack.io/v3...{stack_api_key}/{asset_uid}/{unique_identifier}"
     * <p>
     * } }
     * </p>
     *
     * @param uid
     *             asset uid
     * @param body
     *             the JSON request body
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> generatePermanentUrl(String uid, JSONObject body) {
        return this.service.generatePermanentUrl(this.headers, uid, body);
    }

    /**
     * The Download an asset with permanent URL request displays an asset in the
     * response. The asset returned to the
     * response can be saved to your local storage system. Make sure to specify the
     * unique identifier (slug) in the
     * request URL.
     * <p>
     * This request will return the most recent version of the asset, however, to
     * download the latest published version
     * of the asset, pass the environment query parameter with the environment name.
     * <p>
     * Note: Before executing this API request, ensure to create a permanent URL for
     * the asset you want to download.
     * <p>
     *
     * @param uid
     *                the uid of the asset you want to download the asset.
     * @param slugUrl
     *                the unique identifier of the asset.
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getPermanentUrl(String uid, String slugUrl) {
        return this.service.downloadPermanentUrl(this.headers, uid, slugUrl);
    }

    /**
     * Delete asset call will delete an existing asset from the stack.
     *
     * @param uid
     *            the UID of the asset you want to delete asset
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> delete(String uid) {
        return this.service.delete(this.headers, uid);
    }

    /**
     * The Get information on RTE assets call returns comprehensive information on
     * all assets uploaded through the Rich
     * Text Editor field.
     *
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> rteInformation() {
        return this.service.rteInfo(this.headers);
    }

    /**
     * Version naming allows you to assign a name to a version of an asset for easy
     * identification. For more information, refer the [Name Asset
     * Versions](https://www.contentstack.com/docs/content-managers/asset-versions/name-asset-versions)
     * documentation
     * <p>
     * <b>Set version name for asset</b>
     * <p>
     * The Set Version Name for Asset request allows you to assign a name to a
     * specific version of an asset.
     * <p>
     * In the request body, you need to specify the version name to be assigned to
     * the asset version.
     * <p>
     * 
     * @param assetUid      asset uid
     * @param versionNumber asset version number
     *
     * 
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> setVersionName(@NotNull String assetUid, int versionNumber,
            @NotNull JSONObject requestBody) {
        return this.service.setVersionName(this.headers, assetUid, versionNumber, requestBody);
    }

    /**
     * <b>Get Details of All Versions of an Asset</b>
     * <p>
     * Get Details of All Versions of an Asset request allows you to retrieve the
     * details of all the versions of an asset.
     *
     * The details returned include the actual version number of the asset; the
     * version name along with details such as the assigned version name, the UID of
     * the user who assigned the name, and the time when the version was assigned a
     * name; and the count of the versions.
     *
     * @param assetUid asset uid
     * @param query    request parameters are below
     *                 - skip(optional):Enter the number of version details to be
     *                 skipped.
     *                 Example:2
     *
     *                 - limit(optional): Enter the maximum number of version
     *                 details to be returned.
     *                 Example:5
     *
     *                 - named(optional):Set to ‘true’ if you want to retrieve only
     *                 the named versions
     *                 of your asset.
     *                 Example:false
     *
     *                 - include_count(optional):Enter 'true' to get the total count
     *                 of the asset version
     *                 details.
     *                 Example:false
     * 
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getVersionNameDetails(
            @NotNull String assetUid, @Nullable Map<String, Object> query) {
        if (query == null) {
            query = new HashMap<>();
        }
        return this.service.getVersionNameDetails(this.headers, assetUid, query);
    }

    /**
     * <b>Delete Version Name of Asset</b>
     * <p>
     * Delete Version Name of Asset request allows you to delete the name assigned
     * to a specific version of an asset. This request resets the name of the asset
     * version to the version number.
     *
     * @param assetUid      asset uid
     * @param versionNumber asset version
     * 
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> deleteVersionName(@NotNull String assetUid, int versionNumber) {
        return this.service.deleteVersionName(this.headers, assetUid, versionNumber);
    }

    /**
     * Get asset references request returns the details of the entries and the
     * content types in which the specified asset is referenced
     * 
     * @param assetUid asset uid
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getReferences(@NotNull String assetUid) {
        return this.service.getReferences(this.headers, assetUid);
    }

    /**
     * Get either only images or videos request retrieves assets that are either
     * image or video files, based on query request.
     *
     * You can add queries to extend the functionality of this API call. Under the
     * URI Parameters section, insert a parameter named query and provide a query in
     * JSON format as the value.
     *
     * @param assetType asset type that you want to retrieve. For example, "images"
     *                  or "videos".
     *
     *                  - For images, "images"
     *
     *                  - For videos, "videos"
     *
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getByType(@NotNull String assetType) {
        return this.service.getByType(this.headers, assetType);
    }

    /**
     * The Update asset revision call upgrades a specified version of an asset as
     * the latest version of that asset.
     *
     * Under 'Body', you need to specify the asset version number that you want to
     * make the latest in raw JSON format, and also provide a "Title" and a
     * "Description" for the asset. Another way to provide a "Title" and a
     * "Description" for the asset is to pass them as optional form-data parameters,
     * i.e., asset[title] and asset[description].
     *
     * Here's an example of the raw body:
     *
     * {
     * "asset": {
     * "title": "Title",
     * "description": "Description"
     * },
     * "version": 3
     * }
     * 
     * @param assetUid    asset uid
     * @param requestBody JSON Request body
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> updateDetails(@NotNull String assetUid, @NotNull JSONObject requestBody) {
        return this.service.updateDetails(this.headers, assetUid, requestBody);
    }

    /**
     * Publish an asset call is used to publish a specific version of an asset on
     * the desired environment either immediately or at a later date/time.
     *
     * In case of Scheduled Publishing, add the scheduled_at key and provide the
     * date/time in the ISO format as its value. Example:
     * "scheduled_at":"2016-10-07T12:34:36.000Z"
     *
     * In the 'Body' section, enter the asset details, such as locales and
     * environments, where the assets need to be published. These details should be
     * in JSON format.
     * 
     * @param assetUid    asset uid
     * @param requestBody JSON Request body
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> publish(@NotNull String assetUid, @NotNull JSONObject requestBody) {
        return this.service.publish(this.headers, assetUid, requestBody);
    }

    /**
     * Unpublish an asset call is used to unpublish a specific version of an asset
     * from a desired environment.
     *
     * In case of Scheduled Unpublished, add the scheduled_at key and provide the
     * date/time in the ISO format as its value. Example:
     * "scheduled_at":"2016-10-07T12:34:36.000Z"
     *
     * In the 'Body' section, enter the asset details, such as locales and
     * environments, from where the assets need to be unpublished. These details
     * should be in JSON format.
     * 
     * @param assetUid    asset uid
     * @param requestBody JSON Request body
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> unpublish(
            @NotNull String assetUid, @NotNull JSONObject requestBody) {
        return this.service.unpublish(this.headers, assetUid, requestBody);
    }

    /**
     * The Get a single folder call gets the comprehensive details of a specific
     * asset folder by means of folder UID.
     *
     * When executing the API call to search for a subfolder, you need to provide
     * the parent folder UID.
     *
     * @param folderUid folder uid
     * @param query     query parameters
     *                  - include_path(optional)
     *                  Set this parameter to ‘true’ to retrieve the complete path
     *                  of the folder. The path will be displayed as an array of
     *                  objects which includes the names and UIDs of each parent
     *                  folder.
     *
     *                  Example:false
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> singleFolder(
            @NotNull String folderUid, @Nullable Map<String, Object> query) {
        if (query == null) {
            query = new HashMap<>();
        }
        return this.service.singleFolder(this.headers, folderUid, query);
    }

    /**
     * The Get a single folder by name call retrieves a specific asset folder based
     * on the name provided.
     *
     * @param query query parameters
     *              - query(required)
     *              Enter the is_dir and name parameters to find the asset folder by
     *              name.
     *
     *              Example:{"is_dir": true, "name": "folder_name"}
     *              <p>
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getSingleFolderByName(
            @Nullable Map<String, Object> query) {
        if (query == null) {
            query = new HashMap<>();
        }
        return this.service.singleFolderByName(this.headers, query);
    }

    /**
     * Get subfolders of a parent folder request retrieves the details of only
     * the subfolders of a specific asset folder. This request does not retrieve
     * asset files.
     * 
     * @param query query parameters
     *
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getSubfolder(
            @Nullable Map<String, Object> query) {
        if (query == null) {
            query = new HashMap<>();
        }
        return this.service.getSubfolder(this.headers, query);
    }

    /**
     * The Create a folder call is used to create an asset folder and/or add a
     * parent folder to it (if required).
     *
     * In the ‘Body’ section, you need to provide a name for the new folder.
     *
     * If you want to place this folder within another folder, provide the UID of
     * the parent folder in the Request body as follows:
     *
     * {
     * "asset": {
     * "name": "asset_folder_name",
     * "parent_uid": "asset_parent_folder_uid"
     * }
     * }
     *
     * <b>Note:</b> Here are some points that needs to be considered when executing
     * this API request:
     *
     * A maximum of 300 folders can be created.
     * The maximum level of folder nesting is 5.
     * When nesting folder, you cannot nest a folder within the same folder or
     * within its child folders
     * 
     * @param requestBody JSONObject request body
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> createFolder(
            @Nullable JSONObject requestBody) {
        return this.service.createFolder(this.headers, requestBody);
    }

    /**
     * The Update or move folder request can be used either to update the details of
     * a folder or set the parent folder if you want to move a folder under another
     * folder.
     *
     * When executing the API request, provide the UID of the folder that you want
     * to move/update.
     *
     * In the ‘Body’ section, you need to provide a new name for your folder, and if
     * you want to move your folder within another folder, then you need provide the
     * UID of the parent folder.
     *
     * <b>Note:</b> Here are some points that needs to be considered when executing
     * this API request:
     *
     * A maximum of 300 folders can be created.
     * The maximum level of folder nesting is 5.
     * When nesting folder, you cannot nest a folder within the same folder or
     * within its child folders.
     *
     * @param folderUid   folder uid
     * @param requestBody JSONObject request body
     *                    {
     *                    "asset": {
     *                    "name": "Demo"
     *                    }
     *                    }
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> updateFolder(@NotNull String folderUid,
            @Nullable JSONObject requestBody) {
        return this.service.updateFolder(this.headers, folderUid, requestBody);
    }

    /**
     * Delete a folder call is used to delete an asset folder along with all the
     * assets within that folder.
     *
     * When executing the API call, provide the parent folder UID.
     * 
     * @param folderUid folder uid
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> deleteFolder(@NotNull String folderUid) {
        return this.service.deleteFolder(this.headers, folderUid);
    }

}
