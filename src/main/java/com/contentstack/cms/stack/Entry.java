package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An entry is the actual piece of content created using one of the defined
 * content types.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage
 * modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter
 * to true to include the _branch top-level
 * key in the response. This key specifies the unique ID of the branch where the
 * concerned Contentstack module resides.
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-22
 */
public class Entry implements BaseImplementation<Entry> {

    final String ERROR_ENTRY_UID = "Entry UID Is Required";
    final String ERROR_CT_UID = "Content Type UID Is Required";
    protected final HashMap<String, Object> headers;
    protected final HashMap<String, Object> params;
    protected final EntryService service;
    protected final String contentTypeUid;
    protected final String entryUid;
    private int includeCounter = 1;

    protected Entry(Retrofit instance, Map<String, Object> headers, String contentTypeUid) {
        this.contentTypeUid = contentTypeUid;
        this.entryUid = "";
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = instance.create(EntryService.class);
    }

    protected Entry(Retrofit instance, Map<String, Object> headers, String contentType, String uid) {
        this.contentTypeUid = contentType;
        this.entryUid = uid;
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(headers);
        this.service = instance.create(EntryService.class);
    }

    private void validateEntry() {
        Objects.requireNonNull(this.entryUid, ERROR_ENTRY_UID);
    }

    private void validateCT() {
        Objects.requireNonNull(this.contentTypeUid, ERROR_CT_UID);
    }

    /**
     * Sets header for the request
     *
     * @param key   header key for the request
     * @param value header value for the request
     * @return instance of {@link Entry}
     */
    @Override
    public Entry addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Sets header for the request
     *
     * @param key   query param key for the request
     * @param value query param value for the request
     * @return instance of {@link Entry}
     */
    @Override
    public Entry addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }


    @Override
    public Entry addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public Entry addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * Set header for the request
     *
     * @param key Removes query param using key of request
     * @return instance of {@link Entry}
     */
    public Entry removeParam(@NotNull String key) {
        this.params.remove(key);
        return this;
    }

    /**
     * To clear all the query params
     *
     * @return instance of {@link Entry}
     */
    protected Entry clearParams() {
        this.params.clear();
        return this;
    }

    public Call<ResponseBody> includeReference(@NotNull Object referenceFields) {
        List<String> referenceList = new ArrayList<>();
        if (referenceFields instanceof String) {
            referenceList.add((String) referenceFields);
        } else if (referenceFields instanceof String[]) {
            referenceList.addAll(Arrays.asList((String[]) referenceFields));
        } else {
            throw new IllegalArgumentException("Reference fields must be a String or an array of Strings");
        }
        validateCT();
        validateEntry();
        return this.service.referCall(this.headers, this.contentTypeUid, referenceList);
}
    /**
     * <b>Fetches the list of all the entries of a particular content type.</b>
     * It also returns the content of each entry in JSON format. You can also
     * specify the environment and locale of
     * which you wish to get the entries.
     * <br>
     * <br>
     * <b>Tip:</b> This request returns only the first 100 entries of the specified
     * content type. If you want to fetch entries other than the first 100 in your
     * response, refer the Pagination
     * section to retrieve all your entries in paginated form. Also, to include the
     * publishing details in the response,
     * make use of the include_publish_details parameter and set its value to
     * <b>true</b>. This query will return the
     * publishing details of the entry in every environment along with the version
     * number that is published in each of
     * the environment.
     * <p>
     * {@link #addParam(String, Object)} You can add Query params, the Query
     * parameters are:
     * <br>
     *
     * <pre>
     *     -locale={language_code}
     *     -include_workflow={boolean_value}
     *     -include_publish_details={boolean_value}
     * </pre>
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-entries">Get
     * All
     * Entry</a>
     */
    public Call<ResponseBody> find() {
        validateCT();
        return this.service.fetch(this.headers, this.contentTypeUid, this.params);
    }

    /**
     * <b>The Get a single entry request fetches a particular entry of a content
     * type.</b>
     * <p>
     * The content of the entry is returned in JSON format. You can also specify the
     * environment and locale of which you
     * wish to retrieve the entries.
     * <p>
     * Use: #addParam(String, Object)} To add Query Parameters in the request <br>
     * - version={version_number} <br>
     * -
     * locale={language_code} <br>
     * - include_workflow={boolean_value} <br>
     * - include_publish_details={boolean_value}
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-entry">Get
     * A Single Entry</a>
     */
    public Call<ResponseBody> fetch() {
        validateCT();
        validateEntry();
        return this.service.single(headers, this.contentTypeUid, this.entryUid, this.params);
    }

    /**
     * The Create an entry call creates a new entry for the selected content type.
     * <br>
     * When executing the API call, in the 'Body' section, you need to provide the
     * content of your entry based on the
     * content type created.
     * <br>
     * <p>
     * Here are some important scenarios when creating an entry.
     * </p>
     * <br>
     * <b>Scenario 1:</b> If you have a reference
     * field in your content type, here's the format you need to follow to add the
     * data in the "Body" section
     * <br>
     *
     * @param requestBody Provide the Json Body to create entry:
     *                    <p>
     *                    { "entry": { "title": "Entry title", "url": "Entry URL",
     *                    "reference_field_uid": [{ "uid": "the_uid",
     *                    "_content_type_uid": "referred_content_type_uid" }] } }
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-an-entry">Create
     * A Entry</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> create(JSONObject requestBody) {
        validateCT();
        return this.service.create(this.headers, this.contentTypeUid, requestBody, this.params);
    }

    /**
     * The Update an entry call lets you update the content of an existing entry.
     * <br>
     * Passing the locale parameter will cause the entry to be localized in the
     * specified locale.
     * <br>
     * <b>Note:</b> The Update an entry call does not allow you to update the
     * workflow stage for an entry. To update the workflow stage for the entry, use
     * the Set Entry Workflow Stage call.
     * <br>
     *
     * @param requestBody request body for the entry update
     *                    <code>{ "entry": { "title": "example", "url": "/example" } } </code>
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-an-entry">
     * Update
     * an entry</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> update(JSONObject requestBody) {
        validateCT();
        validateEntry();
        return this.service.update(this.headers, this.contentTypeUid, this.entryUid, requestBody, this.params);
    }

    /**
     * Atomic operations are particularly useful when we do not want content
     * collaborators to overwrite data. Though it
     * works efficiently for singular fields, these operations come handy especially
     * in case of fields that are marked
     * as "Multiple".
     * <br>
     * <p>
     * To achieve data atomicity, we have provided support for following atomic
     * operators:
     * </p>
     * <br>
     * <b>PUSH, PULL, UPDATE, ADD, and SUB</b>.
     *
     * @param requestBody request body <br>
     *                    <b>PUSH operation:</b> The PUSH operation allows you to
     *                    "push" (or append) data into an array without overriding
     *                    an existing value. ```
     *                    <p>
     *                    { "entry": { "multiple_group": { "PUSH": { "data": {
     *                    "demo_field": "abc" } } }} }
     *                    <p>
     *                    ```
     *                    <br>
     *                    <b>PULL operation:</b> The PULL operation allows you to
     *                    pull data from an array field based on a query passed. ```
     *                    { "entry": { "multiple_number": { "PULL": {
     *                    "query": { "$in": [ 2, 3 ] } } } } } ```
     *                    <p>
     *
     *                    <br>
     *                    <b>UPDATE Operation: </b> The UPDATE operation allows you
     *                    to update data at a specific index. This operation works
     *                    for both singular fields and fields marked
     *                    "Multiple". """ { "entry": { "multiple_number": {
     *                    "UPDATE": { "index": 0, "data": 1 } } } } """
     *                    <br>
     *                    Add, SUB and Delete will be executed like the above. for
     *                    more details
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#atomic-updates-to-entries">
     * Atomic
     * Operation</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> atomicOperation(JSONObject requestBody) {
        validateCT();
        validateEntry();
        return this.service.atomicOperations(this.headers, this.contentTypeUid, this.entryUid, requestBody);
    }

    /**
     * To Delete an entry request allows you to delete a specific entry from a
     * content type. This API request also
     * allows you to delete single and/or multiple localized entries.
     * <br>
     * <p>
     * <b>Note:</b> In the Header, you need to use either the stack Management Token
     * (recommended) or the user Authtoken, along with the stack API key, to make
     * valid Content Management API requests.
     * For more information, refer to Authentication.
     * </p>
     *
     * <p>
     * The entry you want to update {@link #addParam(String, Object)} - Delete
     * specific localized entry: <br>
     * <p>
     * For this request, you need to only specify the locale code of the language in
     * the locale query parameter. If the
     * locale parameter is not been specified, by default, the master language entry
     * will be deleted.
     * </p>
     * <br>
     * <b>Delete master language along with all its localized:
     * </b>For this request, instead of the locale
     * query parameter, you need to pass the delete_all_localized:true query
     * parameter
     * <br>
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-an-entry">Get
     * Delete An Entry</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        validateCT();
        validateEntry();
        return this.service.delete(this.headers, this.contentTypeUid, this.entryUid, new JSONObject(), this.params);
    }

    /**
     * To Delete an entry request allows you to delete a specific entry from a
     * content type. This API request also
     * allows you to delete single and/or multiple localized entries.
     * <br>
     * <p>
     * <b>Note:</b> In the Header, you need to use either the stack Management Token
     * (recommended) or the user Authtoken, along with the stack API key, to make
     * valid Content Management API requests.
     * For more information, refer to Authentication.
     * </p>
     * <p>
     * The entry you want to update {@link #addParam(String, Object)} - Delete
     * specific localized entry: <br>
     * <p>
     * For this request, you need to only specify the locale code of the language in
     * the locale query parameter. If the
     * locale parameter is not been specified, by default, the master language entry
     * will be deleted.
     * </p>
     * <br>
     * <b>Delete master language along with all its localized:
     * </b>For this request, instead of the locale
     * query parameter, you need to pass the delete_all_localized:true query
     * parameter
     * <br>
     * <b>Delete multiple localized entry:</b>Additionally,
     * you can delete specific localized entries by passing the locale codes in the
     * Request body using the locales key
     * as follows:
     *
     * @param requestBody you can delete specific localized entries by passing the
     *                    locale codes in the Request body using the
     *                    locales key as follows ``` { "entry": { "locales":
     *                    ["hi-in", "mr-in", "es"] } } ```
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-an-entry">
     * Delete
     * An Entry</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> delete(JSONObject requestBody) {
        validateCT();
        validateEntry();
        return this.service.delete(this.headers, this.contentTypeUid, this.entryUid, requestBody, this.params);
    }

    /**
     * Version naming allows you to assign a name to a version of an entry for easy
     * identification. For more
     * information, refer to the Name Entry Version documentation.
     * <br>
     *
     * @param version     Enter the version number of the entry to which you want to
     *                    assign a name.
     * @param requestBody RequestBody like below. ``` { "entry": { "_version_name":
     *                    "Test version", "locale": "fr-fr", "force":
     *                    true } } ```
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#set-version-name-for-entry">
     * Set
     * Version Name for Entry</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> versionName(int version, JSONObject requestBody) {
        validateCT();
        validateEntry();
        return this.service.versionName(this.headers, this.contentTypeUid, this.entryUid, String.valueOf(version),
                requestBody);
    }

    /**
     * The Get Details of All Versions of an Entry request allows you to retrieve
     * the details of all the versions of an
     * entry.
     * <p>
     * The version details returned include the actual version number of the entry;
     * the version name along with details
     * such as the assigned version name, the UID of the user who assigned the name,
     * and the time when the version was
     * assigned a name; and the locale of the entry.
     * <p>
     *
     * <b>Note:</b> If an entry is unlocalized, the version details of entries
     * published in the master locale will be returned.
     *
     * <p>
     * {@link #addParam(String, Object)} - skip(optional) Enter the number of
     * version details to be skipped. -
     * limit(optional): Enter the maximum number of version details to be returned.
     * - named(optional): Set to ‘true’ if
     * you want to retrieve only the named versions of your entry. -
     * include_count(optional): Enter 'true' to get the
     * total count of the entry version details. - locale(optional): Enter the code
     * of the language of which the entries
     * need to be included. Only the version details of entries published in this
     * locale will be displayed
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-details-of-all-versions-of-an-entry">
     * Get Details of All Versions of an Entry</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> detailOfAllVersion() {
        validateCT();
        validateEntry();
        return this.service.detailOfAllVersion(this.headers, this.contentTypeUid, this.entryUid, this.params);
    }

    /**
     * @param versionNumber: Enter the version number of the entry that you want to
     *                       delete.
     * @param requestBody    Request body for the delete operation ``` { "entry": {
     *                       "locale": "en-us" } } ```
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-version-name-of-entry">
     * Delete
     * Version Name of Entry</a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> deleteVersionName(int versionNumber, JSONObject requestBody) {
        validateCT();
        validateEntry();
        return this.service.deleteVersionName(this.headers, this.contentTypeUid, this.entryUid, versionNumber,
                requestBody);
    }

    /**
     * The Get references of an entry call returns all the entries of content types
     * that are referenced by a particular
     * entry.
     * <br>
     *
     *
     * <p>
     * {@link #addParam(String, Object)} The Query parameter: Locale: Enter the code
     * of the language of which the
     * entries need to be included. Only the entries published in this locale will
     * be displayed
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-references-of-an-entry">
     * Get
     * references of an entry</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> getReference() {
        validateCT();
        validateEntry();
        return this.service.reference(this.headers, this.contentTypeUid, this.entryUid, this.params);
    }

    /**
     * The Get languages of an entry call returns the details of all the languages
     * that an entry exists in
     * <p>
     * {@link #addParam(String, Object)} the query parameter The Query parameter:
     * Locale: Enter the code of the language
     * of which the entries need to be included. Only the entries published in this
     * locale will be displayed
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-languages-of-an-entry">
     * Get
     * language of an entry</a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> getLanguage() {
        validateCT();
        validateEntry();
        return this.service.language(this.headers, this.contentTypeUid, this.entryUid, this.params);
    }

    /**
     * To Localize an entry request allows you to localize an entry i.e., the entry
     * will cease to fetch data from its
     * fallback language and possess independent content specific to the selected
     * locale.
     * <br>
     * <p>
     * <b>Note:</b> This request will only create the localized version of your
     * entry and not publish it. To publish your localized entry, you need to use
     * the Publish an entry request and pass
     * the respective locale code in the locale={locale_code} parameter.
     * </p>
     *
     * @param requestBody In the "Body" parameter, you need to provide the content
     *                    of your entry based on the content type.
     * @param localeCode  Enter the code of the language to localize the entry of
     *                    that particular language
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#localize-an-entry">
     * Localize an entry
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> localize(@NotNull JSONObject requestBody,
                                       @NotNull String localeCode) {
        validateCT();
        validateEntry();
        return this.service.localize(this.headers, this.contentTypeUid, this.entryUid, localeCode, requestBody);
    }

    /**
     * The Un-localize an entry request is used to un-localize an existing entry. Read
     * more about Localization.
     *
     * @param localeCode Enter the code of the language to localize the entry of
     *                   that particular language
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#unlocalize-an-entry">
     * unlocalize an entry
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> unLocalize(@NotNull String localeCode) {
        validateCT();
        validateEntry();
        return this.service.unLocalize(this.headers, this.contentTypeUid, this.entryUid, localeCode);
    }

    /**
     * The Export an entry call is used to export an entry. The exported entry data
     * is saved in a downloadable JSON
     * file.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#export-an-entry">
     * Export
     * an entry
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> export() {
        validateCT();
        validateEntry();
        return this.service.export(this.headers, this.contentTypeUid, this.entryUid, this.params);
    }

    /**
     * The Import an entry call is used to import an entry. To import an entry, you
     * need to upload a JSON file that has
     * entry data in the format that fits the schema of the content type it is being
     * imported to.
     *
     * <p>
     * The Import an existing entry call will import a new version of an existing
     * entry. You can create multiple
     * versions of an entry.
     * </p>
     * <p>
     * {@link #addParam(String, Object)} the query parameter
     * <br>
     * locale (optional): Enter the code of the language to localize the entry of
     * that particular
     * <br>
     * overwrite (optional): Select 'true' to replace an existing entry with the
     * imported entry file. language
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#import-an-entry">
     * Import
     * an entry
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> imports() {
        validateCT();
        return this.service.imports(this.headers, this.contentTypeUid, this.params);
    }

    /**
     * The Import an existing entry call will import a new version of an existing
     * entry. You can create multiple
     * versions of an entry.
     * <p>
     * {@link #addParam(String, Object)} the query parameter
     * <br>
     * locale (optional): Enter the code of the language to localize the entry of
     * that particular
     * <br>
     * overwrite (optional): Select 'true' to replace an existing entry with the
     * imported entry file. language
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#import-an-entry">
     * Import
     * an entry
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> importExisting() {
        validateCT();
        validateEntry();
        return this.service.importExisting(this.headers, this.contentTypeUid, this.entryUid, this.params);
    }

    /**
     * To Publish an entry request lets you publish an entry either immediately or
     * schedule it for a later date/time.
     * <br>
     * In the 'Body' section, you can specify the locales and environments to which
     * you want to publish the entry. When
     * you pass locales in the "Body", the following actions take place:
     * <br>
     * <p>
     * If you have not localized your entry in any of your stack locales, the Master
     * Locale entry gets localized in
     * those locales and are published
     * </p>
     * <p>
     * If you have localized any or all of your entries in these locales, the
     * existing localized content of those
     * locales will NOT be published. However, if you need to publish them all, you
     * need to perform a Bulk Publish
     * operation.
     * </p>
     * <br>
     * The locale and environment details should be specified in the <b>entry</b>
     * parameter. However, if you do not
     * specify any source locale(s), it will be published in the master locale
     * automatically.
     * <br>
     * Along with the above details, you also need to mention the master locale and
     * the version number of your entry
     * that you want to publish.
     *
     * @param requestBody The requestBody in JSONObject
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-an-entry">
     * Publish an entry
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> publish(@NotNull JSONObject requestBody) {
        validateCT();
        validateEntry();
        return this.service.publish(this.headers, this.contentTypeUid, this.entryUid, requestBody);
    }

    /**
     * The Publishing an Entry With References request allows you to publish an
     * entry along with all its references at
     * the same time.
     *
     * @param requestBody The request body in JSONObject format
     *                    {@link #addParam(String, Object)} Below are the query
     *                    parameters
     *                    <br>
     *                    - approvals:Set this to <b>true</b> to publish the entries
     *                    that do not require an approval to be
     *                    published.<br>
     *                    skip_workflow_stage_check - Set this to <b>true</b> to
     *                    publish the entries that are at a
     *                    workflow stage where they satisfy the applied to publish
     *                    rules.
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#publish-an-entry-with-references">
     * Publish an entry With reference
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> publishWithReference(@NotNull JSONObject requestBody) {
        return this.service.publishWithReference(this.headers, requestBody, this.params);
    }

    /**
     * To Un-publish an entry call will un-publish an entry at once, and also, gives
     * you the provision to un-publish an
     * entry automatically at a later date/time.
     * <p>
     * In the 'Body' section, you can specify the locales and environments from
     * which you want to un-publish the entry.
     * These details should be specified in the
     * <p>
     * <b>entry</b> parameter. However, if
     * you do not specify a locale, it will be unpublished from the master locale
     * automatically.
     * <p>
     * You also need to mention the master locale and the version number of your
     * entry that you want to publish.
     * <p>
     * In case of Scheduled Unpublished, add the scheduled_at key and provide the
     * date/time in the ISO format as its
     * value. Example: "scheduled_at":"2016-10-07T12:34:36.000Z"
     *
     * @param requestBody The requestBody in JSONObject
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#unpublish-an-entry">
     * Unpublish an entry
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> unpublish(@NotNull JSONObject requestBody) {
        validateCT();
        validateEntry();
        return this.service.unpublish(this.headers, this.contentTypeUid, this.entryUid, requestBody);
    }


    /**
     * Get instance of  taxonomy search filter class instance through which we can query on taxonomy based on content type
     * <p><b>Examples</b></p>
     * <pre>
     *           JSONObject query = new JSONObject();
     *         query.put("taxonomies.taxonomy_uid", "{ \"$in\" : [\"term_uid1\" , \"term_uid2\" ] }");
     *         Call response = entry.query(query).execute();
     *     </pre>
     *
     * @param query the request body of  type {@link JSONObject}
     * @return instance of {@link Terms}
     */
    public Call<ResponseBody> query(@NotNull JSONObject query) {
        validateCT();
        return this.service.filterTaxonomy(this.headers, this.contentTypeUid, query);
    }

}
