package com.contentstack.cms.stack;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Assets refer to all the media files (images, videos, PDFs, audio files, and
 * so on) uploaded in your Contentstack
 * repository for future use.
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-20
 */
public class Asset {

    protected final Map<String, Object> headers;
    protected Map<String, Object> params;
    protected final AssetService service;
    private final Retrofit instance;
    private String assetUid;

    protected Asset(Retrofit instance, Map<String, Object> header) {
        this.headers = new HashMap<>();
        this.headers.putAll(header);
        this.params = new HashMap<>();
        this.instance = instance;
        this.service = instance.create(AssetService.class);
    }

    protected Asset(Retrofit instance, Map<String, Object> header, String uid) {
        this.instance = instance;
        this.assetUid = uid;
        this.headers = new HashMap<>();
        this.headers.putAll(header);
        this.params = new HashMap<>();
        this.service = instance.create(AssetService.class);
    }

    void validate() {
        if (this.assetUid == null || this.assetUid.isEmpty())
            throw new IllegalStateException("Asset Uid can not be null or empty");
    }

    /**
     * Sets header for the request
     *
     * @param key
     *              header key for the request
     * @param value
     *              header value for the request
     */
    public void addHeader(@NotNull String key, @NotNull Object value) {
        this.headers.put(key, value);
    }

    /**
     * Sets header for the request
     *
     * @param key
     *              query param key for the request
     * @param value
     *              query param value for the request
     */
    public void addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
    }

    /**
     * Set header for the request
     *
     * @param key
     *            Removes query param using key of request
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
     * Gets folder instance
     *
     * @return Folder
     */
    public Folder folder() {
        return new Folder(this.instance);
    }

    /**
     * Gets folder instance
     *
     * @param folderUid
     *                  The UID of the folder that you want to either update or move
     * @return Folder
     */
    public Folder folder(@NotNull String folderUid) {
        return new Folder(this.instance, folderUid);
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
     * <p>
     * #addParam query params for the assets older(optional) Enter either the UID of
     * a specific folder to get the assets
     * of that folder, or enter ‘cs_root’ to get all assets and their folder details
     * from the root folder.
     *
     * <p>
     * include_folders(optional) Set this parameter to ‘true’ to include the details
     * of the created folders along with
     * the details of the assets.
     *
     * <p>
     * environment(optional) Enter the name of the environment to retrieve the
     * assets published on them. You can enter
     * multiple environments.
     *
     * <p>
     * version(optional) Specify the version number of the asset that you want to
     * retrieve. If the version is not
     * specified, the details of the latest version will be retrieved.
     * <p>
     * include_publish_details(optional) Enter 'true' to include the publishing
     * details of the entry.
     *
     * <p>
     * include_count(optional) Set this parameter to 'true' to include the total
     * number of assets available in your
     * stack in the response body.
     *
     * <p>
     * relative_urls(optional) Set this to 'true' to display the relative URL of the
     * asset.
     *
     * <p>
     * asc_field_uid(optional) Enter the unique ID of the field for sorting the
     * assets in ascending order by that field.
     * Example:created_at
     *
     * <p>
     * desc_field_uid(optional) Enter the unique ID of the field for sorting the
     * assets in descending order by that
     * field.
     *
     * <p>
     * Example:file_size
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-assets">Get
     *      all
     *      As</a>
     * @author ishaileshmishra
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
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
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-asset">Get
     *      a
     *      single asset</a>
     * @author ishaileshmishra
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> fetch() {
        this.validate();
        return this.service.single(this.headers, this.assetUid, this.params);
    }

    /**
     * The Get assets of a specific folder retrieves all assets of a specific asset
     * folder; however, it doesn't retrieve
     * the details of sub-folders within it.
     *
     * @param folderUid
     *                  The folderUid of specific folder
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-assets-of-a-specific-folder">Get
     *      Assets of a Specific Folder</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @author ishaileshmishra
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> byFolderUid(@NotNull String folderUid) {
        this.params.put("folder", folderUid);
        return this.service.specificFolder(this.headers, this.params);
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
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-assets-and-subfolders-of-a-parent-folder">Get
     *      Assets and Subfolders of a Parent Folder</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @author ishaileshmishra
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> subfolder(
            @NotNull String folderUid, Boolean isIncludeFolders) {
        this.params.put("folder", folderUid);
        this.params.put("include_folders", isIncludeFolders);
        return this.service.subfolder(this.headers, this.params);
    }

    /**
     * The <b>Upload asset</b> request uploads an asset file to your stack.
     * <p>
     * To upload assets from your local system to Contentstack and manage their
     * details, you need to use the following
     * "form-data" parameters:
     *
     * @param filePath
     *                    the file path
     * @param description
     *                    The description of the asset file
     *                    <ul>
     *                    <li>asset[upload] (mandatory) Select the input type as
     *                    'File'. Then, browse and select the asset file that
     *                    you want to import. Supported file types include JPG, GIF,
     *                    PNG, XML, WebP, BMP, TIFF, SVG, and PSD</li>
     *                    <li>asset[parent_uid] (optional) If needed, assign a
     *                    parent folder to your asset by passing the UID of the
     *                    parent folder.</li>
     *
     *                    <li>asset[title] (optional) Enter a title for your
     *                    uploaded asset.</li>
     *                    <li>asset[description] (optional) Enter a description for
     *                    your uploaded asset.</li>
     *                    <li>asset[tags] (optional) Assign a specific tag(s) to
     *                    your uploaded asset. {@link #addParam(String, Object)}
     *                    The query parameter for the asset to upload</li>
     *                    <li>relative_urls(optional) Set this to 'true' to display
     *                    the relative URL of the asset. Example:false</li>
     *                    <li>include_dimension(optional) Set this to 'true' to
     *                    include the dimensions (height and width) of the image
     *                    in the response.</li>
     *                    </ul>
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#upload-asset">
     *      Upload
     *      Asset</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @author ishaileshmishra
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> uploadAsset(@NotNull String filePath, String description) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), description);
        MultipartBody.Part assetPath = uploadFile(filePath);
        return this.service.uploadAsset(this.headers, assetPath, body, this.params);
    }

    private MultipartBody.Part uploadFile(@NotNull String filePath) {
        if (!filePath.isEmpty()) {
            File file = new File(filePath);
            URLConnection connection = null;
            try {
                connection = file.toURL().openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (file.exists()) {
                RequestBody body = RequestBody.create(MediaType.parse(connection.getContentType()), file);
                return MultipartBody.Part.createFormData("asset[upload]", file.getName(), body);
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
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#replace-asset">
     *      Replace
     *      Asset</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @param filePath the filepath
     * @param description the file description
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> replace(@NotNull String filePath, @NotNull String description) {
        this.validate();
        MultipartBody.Part assetPath = uploadFile(filePath);
        RequestBody body = RequestBody.create(MediaType.parse(String.valueOf(MultipartBody.FORM)), description);
        return this.service.replace(this.headers, this.assetUid, assetPath, body, this.params);
    }

    /**
     * It helps to replace data with custom file title, file description and path
     *
     * @param filePath
     *                        The filePath of the payload
     * @param fileTitle
     *                        Title of the payload
     * @param fileDescription
     *                        Description of the payload
     * @return RequestBody
     */
    private RequestBody createPayload(@NotNull String filePath, String fileTitle, String fileDescription) {
        if (!filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                fileTitle = (fileTitle != null) ? fileTitle : file.getName();
                return new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("asset[upload]", file.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"), file))
                        .addFormDataPart("asset[title]", fileTitle)
                        .addFormDataPart("asset[description]", fileDescription).build();
            }
            return null;
        }
        return null;
    }

    /**
     * Generate Permanent Asset URL request allows you to generate a permanent URL
     * for an asset. This URL remains
     * constant irrespective of any subsequent updates to the asset.
     * <p>
     * In the request body, you need to pass the permanent URL in the following
     * format:
     * <br>
     * <b>Example:</b>
     * 
     * <pre>
     * {
     * <code>
     *     { "asset": { "permanent_url": "<a href=
    "https://api.contentstack.io/v3">...</a>...{stack_api_key}/{asset_uid}/{unique_identifier}"} }}
     * </code>
     * }
     * </pre>
     *
     * @param body
     *             the JSONObject request body
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#generate-permanent-asset-url">
     *      Generate Permanent Asset URL </a>
     * @see #addHeader(String, Object) to add headers
     * @author ishaileshmishra
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> generatePermanentUrl(JSONObject body) {
        this.validate();
        return this.service.generatePermanentUrl(this.headers, this.assetUid, body);
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
     * <br>
     *
     * @param slugUrl
     *                The unique identifier of the asset.
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#generate-permanent-asset-url">
     *      Generate Permanent Asset Url</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @author ishaileshmishra
     * @version v0.1.0
     * @since 2022-10-20
     */
    public Call<ResponseBody> getPermanentUrl(String slugUrl) {
        this.validate();
        return this.service.downloadPermanentUrl(this.headers, this.assetUid, slugUrl, this.params);
    }

    /**
     * Delete asset call will delete an existing asset from the stack.
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-asset">
     *      Delete
     *      Asset
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        this.validate();
        return this.service.delete(this.headers, this.assetUid);
    }

    /**
     * The Get information on RTE assets call returns comprehensive information on
     * all assets uploaded through the Rich
     * Text Editor field.
     *
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-information-on-rte-assets">
     *      Get Information On RTE Assets</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> rteInformation() {
        return this.service.rteInfo(this.headers, this.params);
    }

    /**
     * Version naming allows you to assign a name to a version of an asset for easy
     * identification. For more
     * information, Read more about <a
     * href=
     * "https://www.contentstack.com/docs/content-managers/asset-versions/name-asset-versions">Asset</a>
     *
     * <p>
     * <b>Set version name for asset</b>
     * <p>
     * The Set Version Name for Asset request allows you to assign a name to a
     * specific version of an asset.
     * <p>
     * In the request body, you need to specify the version name to be assigned to
     * the asset version.
     * <br>
     *
     * @param versionNumber
     *                      asset version number
     * @param requestBody
     *                      the request body of {@link JSONObject}
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-information-on-rte-assets">
     *      Get Information On RTE Assets</a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> setVersionName(int versionNumber,
            @NotNull JSONObject requestBody) {
        return this.service.setVersionName(this.headers, this.assetUid, versionNumber, requestBody);
    }

    /**
     * <b>Get Details of All Versions of an Asset</b>
     * <p>
     * Get Details of All Versions of an Asset request allows you to retrieve the
     * details of all the versions of an
     * asset.
     * <p>
     * The details returned include the actual version number of the asset; the
     * version name along with details such as
     * the assigned version name, the UID of the user who assigned the name, and the
     * time when the version was assigned
     * a name; and the count of the versions.
     * <p>
     * #addParam request parameters are below - skip(optional):Enter the number of
     * version details to be skipped.
     * <br>
     * <b>Example:</b>
     * <p>
     * - limit(optional): Enter the maximum number of version details to be
     * returned. Example:5
     * <p>
     * - named(optional):Set to <b>true</b> if you want to retrieve only the named
     * versions of your asset.
     * Example:false
     * <p>
     * - include_count(optional):Enter 'true' to get the total count of the asset
     * version details. Example:false
     *
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-details-of-all-versions-of-an-asset">
     *      Get Details of All Versions of an Asset</a>
     * @see #addParam(String, Object) to add Query parameters
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> getVersionNameDetails() {
        this.validate();
        return this.service.getVersionNameDetails(this.headers, this.assetUid, this.params);
    }

    /**
     * <b>Delete Version Name of Asset</b>
     * <p>
     * Delete Version Name of Asset request allows you to delete the name assigned
     * to a specific version of an asset.
     * This request resets the name of the asset version to the version number.
     *
     * @param versionNumber
     *                      asset version
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-version-name-of-asset">
     *      Delete Version Name Of Asset</a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> deleteVersionName(int versionNumber) {
        this.validate();
        return this.service.deleteVersionName(this.headers, this.assetUid, versionNumber);
    }

    /**
     * Get asset references request returns the details of the entries and the
     * content types in which the specified
     * asset is referenced
     *
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-asset-references">
     *      Get Asset References</a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> getReferences() {
        validate();
        return this.service.getReferences(this.headers, this.assetUid);
    }

    /**
     * Get either only images or videos request retrieves assets that are either
     * image or video files, based on query
     * request.
     * <p>
     * You can add queries to extend the functionality of this API call. Under the
     * URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     *
     * @param assetType
     *                  asset type that you want to retrieve. For example,
     *                  <b>images</b> or <b>videos</b>.
     *                  <p>
     *                  - For images, "images"
     *                  <p>
     *                  - For videos, "videos"
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-either-only-images-or-videos">
     *      Get Either Only Images Or Videos
     *      </a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> getByType(@NotNull String assetType) {
        return this.service.getByType(this.headers, assetType);
    }

    /**
     * The Update asset revision call upgrades a specified version of an asset as
     * the latest version of that asset.
     * <p>
     * Under 'Body', you need to specify the asset version number that you want to
     * make the latest in raw JSON format,
     * and also provide a "Title" and a "Description" for the asset. Another way to
     * provide a "Title" and a
     * "Description" for the asset is to pass them as optional form-data parameters,
     * i.e., asset[title] and
     * asset[description].
     * <br>
     * <b>Here's an example of the body:</b>
     * <br>
     * 
     * <pre>
     *  { "asset": { "title": "Title", "description": "Description" }, "version": 3 }
     * </pre>
     *
     * @param requestBody
     *                    The request body
     * @return Call
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> updateDetails(JSONObject requestBody) {
        validate();
        return this.service.updateDetails(this.headers, this.assetUid, this.params, requestBody);
    }

    /**
     * Publish an asset call is used to publish a specific version of an asset on
     * the desired environment either
     * immediately or at a later date/time.
     * <p>
     * In case of Scheduled Publishing, add the scheduled_at key and provide the
     * date/time in the ISO format as its
     * value. Example: "scheduled_at":"2016-10-07T12:34:36.000Z"
     * <p>
     * In the 'Body' section, enter the asset details, such as locales and
     * environments, where the assets need to be
     * published. These details should be in JSON format.
     *
     * @param requestBody
     *                    JSON Request body
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-an-asset">
     *      Publish An Asset</a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> publish(@NotNull JSONObject requestBody) {
        validate();
        return this.service.publish(this.headers, this.assetUid, requestBody);
    }

    /**
     * Unpublish an asset call is used to unpublish a specific version of an asset
     * from a desired environment.
     * <p>
     * In case of Scheduled Unpublished, add the scheduled_at key and provide the
     * date/time in the ISO format as its
     * value. Example: "scheduled_at":"2016-10-07T12:34:36.000Z"
     * <p>
     * In the <b>Body</b> section, enter the asset details, such as locales and
     * environments, from where the assets need
     * to be unpublished. These details should be in JSON format.
     *
     * @param requestBody
     *                    JSON Request body
     * @return Call
     * @see <a href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#unpublish-an-asset">
     *      Unpublish An Asset</a>
     * @see #addHeader(String, Object) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> unpublish(
            @NotNull JSONObject requestBody) {
        this.validate();
        return this.service.unpublish(this.headers, this.assetUid, requestBody);
    }

    /**
     * The Get a single folder by name call retrieves a specific asset folder based
     * on the name provided.
     * <p>
     * $addParam query parameters - query(required) Enter the is_dir and name
     * parameters to find the asset folder by
     * name.
     * <p>
     * Example:{"is_dir": true, "name": "folder_name"}
     * <br>
     *
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-folder-by-name">
     *      Get
     *      A Single Folder By Name</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> getSingleFolderByName() {
        return this.service.singleFolderByName(this.headers, this.params);
    }

    /**
     * Get subfolders of a parent folder request retrieves the details of only the
     * subfolders of a specific asset
     * folder. This request does not retrieve asset files.
     * <p>
     * #addParam query parameters
     *
     * @return Call
     * @see <a
     *      href=
     *      "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-subfolders-of-a-parent-folder">
     *      Get subfolders of a parent folder</a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query params
     * @since 0.1.0
     */
    public Call<ResponseBody> getSubfolder() {
        return this.service.getSubfolder(this.headers, this.params);
    }

}
