package com.contentstack.cms.entries;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

// Provide it under the ContentType class
public class Entry {

    protected final HashMap<String, String> headers;
    protected final EntryService service;
    // Enter the unique ID of the content type of which you wish to retrieve the
    // details.
    // The uid is generated based on the title of the content type, and it is unique
    // across a stack.
    protected final String contentTypeUid;

    public Entry(Retrofit instance, @NotNull String apiKey, String managementToken, String contentTypeUid) {
        this.contentTypeUid = contentTypeUid;
        headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("api_key", apiKey);
        if (managementToken != null && !managementToken.isEmpty()) {
            headers.put("authorization", managementToken);
        }
        this.service = instance.create(EntryService.class);
    }

    /**
     * <b>Fetches the list of all the entries of a particular content type.</b>
     * It also returns the content of each entry in JSON format. You can also
     * specify the environment and locale of
     * which you wish to get the entries.
     * <br>
     * <br>
     * <b>Tip:</b> This request returns only the first 100 entries of the specified
     * content type. If you want to fetch
     * entries other than the first 100 in your response, refer the Pagination
     * section to retrieve all your entries in
     * paginated form. Also, to include the publishing details in the response, make
     * use of the include_publish_details
     * parameter and set its value to <b>true</b>. This query will return the
     * publishing
     * details of the entry in every
     * environment along with the version number that is published in each of the
     * environment.
     *
     * @param queryParameter
     *                       The Query parameters are:
     *                       <br>
     *                       -locale={language_code}<br>
     *                       -include_workflow={boolean_value}<br>
     *                       -include_publish_details={boolean_value}
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> fetch(Map<String, Object> queryParameter) {
        return this.service.fetch(headers, this.contentTypeUid, queryParameter);
    }

    /**
     * <b>The Get a single entry request fetches a particular entry of a content
     * type.</b>
     * <p>
     * The content of the entry is returned in JSON format. You can also specify the
     * environment and locale of which you
     * wish to retrieve the entries.
     *
     * @param entryUid
     *                       Entry uid
     * @param queryParameter
     *                       The Query Parameters are <br>
     *                       - version={version_number} <br>
     *                       - locale={language_code} <br>
     *                       -
     *                       include_workflow={boolean_value} <br>
     *                       - include_publish_details={boolean_value}
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> single(@NotNull String entryUid, Map<String, Object> queryParameter) {
        return this.service.single(headers, this.contentTypeUid, entryUid, queryParameter);
    }

    /**
     * The Create an entry call creates a new entry for the selected content type.
     * <br>
     * When executing the API call, in
     * the 'Body' section, you need to provide the content of your entry based on
     * the content type created.
     * <br>
     * <p>
     * Here are some important scenarios when creating an entry.
     * </p>
     * <br>
     * <b>Scenario 1:</b> If you have a reference
     * field in your content type, here's the format you need to follow to add the
     * data in the "Body" section
     * <br>
     * <b>[Read
     * more](https://www.contentstack.com/docs/developers/apis/content-management-api/#create-an-entry)</b>
     *
     * @param requestBody
     *                       Provide the Json Body to create entry: ```json {
     *                       "entry": { "title": "example", "url": "/example" } }
     *                       ```
     * @param queryParameter
     *                       locale: Enter the code of the language in which you
     *                       want your entry to be localized in.
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> create(JSONObject requestBody, @Nullable Map<String, Object> queryParameter) {
        if (queryParameter == null) {
            queryParameter = new HashMap<>();
        }
        return this.service.create(this.headers, this.contentTypeUid, requestBody, queryParameter);
    }

    /**
     * The Update an entry call lets you update the content of an existing entry.
     * <p>
     * Passing the locale parameter will cause the entry to be localized in the
     * specified locale.
     * </p>
     * <br>
     * <p>
     * <b>Note:</b> The Update an entry call does not allow you to update the
     * workflow stage for an entry.
     * To update the workflow stage for the entry, use the Set Entry Workflow Stage
     * call.
     * </p>
     *
     * @param entryUId
     *                       The entry you want to update
     * @param queryParameter
     *                       locale: Enter the code of the language in which you
     *                       want your entry to be localized in.
     * @param requestBody
     *                       request body for the entry update ```json{ "entry": {
     *                       "title": "example", "url": "/example" } }```
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> update(String entryUId, JSONObject requestBody, Map<String, Object> queryParameter) {
        return this.service.update(this.headers, this.contentTypeUid, entryUId, requestBody, queryParameter);
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
     * @param entryUId
     *                    The entry you want to update
     * @param requestBody
     *                    request body <br>
     *                    <b>PUSH operation:</b> The PUSH operation allows you to
     *                    "push" (or append) data into an array without overriding
     *                    an existing value.
     *                    ```
     *
     *                    {
     *                    "entry":
     *                    {
     *                    "multiple_group":
     *                    {
     *                    "PUSH":
     *                    { "data": { "demo_field":
     *                    "abc" }
     *                    }
     *                    }}
     *                    }
     *                    <p>
     *                    ```
     *                    <br>
     *                    <b>PULL operation:</b> The PULL operation allows you to
     *                    pull data from an array field based on a query passed.
     *                    ```
     *                    { "entry":
     *                    {
     *                    "multiple_number": {
     *                    "PULL": {
     *                    "query":
     *                    { "$in": [ 2, 3 ] } } }
     *                    } }
     *                    ```
     *                    <p>
     *
     *                    <br>
     *                    <b>UPDATE Operation: </b> The UPDATE operation allows you
     *                    to update data at a specific index. This operation works
     *                    for both singular fields and fields marked
     *                    "Multiple".
     *                    """
     *                    {
     *                    "entry":
     *                    {
     *                    "multiple_number":
     *                    {
     *                    "UPDATE": { "index": 0, "data": 1 } } } }
     *                    """
     *                    <p>
     *                    Add, SUB and Delete will be executed like the above. for
     *                    more details [read
     *                    here](https://www.contentstack.com/docs/developers/apis/content-management-api/#atomic-updates-to-entries)
     *                    </p>
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> atomicOperation(String entryUId, JSONObject requestBody) {
        return this.service.atomicOperations(this.headers, this.contentTypeUid, entryUId, requestBody);
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
     * @param entryUId
     *                       The entry you want to update
     * @param queryParameter
     *                       - Delete specific localized entry: <br>
     *                       <p>
     *                       For this request, you need to only specify the locale
     *                       code of the language in the locale query parameter.
     *                       If the locale parameter is not been specified, by
     *                       default, the master language entry will be deleted.
     *                       </p>
     *                       <br>
     *                       <b>Delete master language along with all its localized:
     *                       </b>For this request, instead of the locale
     *                       query parameter, you need to pass the
     *                       delete_all_localized:true query parameter
     *                       <br>
     * @return the retrofit2.Call
     *         <p>
     *         [Read
     *         More]{https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-an-entry}
     */
    public Call<ResponseBody> delete(String entryUId, Map<String, Object> queryParameter) {
        return this.service.delete(this.headers, this.contentTypeUid, entryUId, new JSONObject(), queryParameter);
    }

    /**
     * To Delete an entry request allows you to delete a specific entry from a
     * content type. This API request also
     * allows you to delete single and/or multiple localized entries.
     * <br>
     * <p>
     * <b>Note:</b> In the Header, you need to use either the stack Management Token
     * (recommended) or the user
     * Authtoken, along with the stack API key, to make valid Content Management API
     * requests. For more information,
     * refer to Authentication.
     * </p>
     *
     * @param entryUId
     *                       The entry you want to update
     * @param queryParameter
     *                       - Delete specific localized entry: <br>
     *                       <p>
     *                       For this request, you need to only specify the locale
     *                       code of
     *                       the language in the locale query parameter. If the
     *                       locale parameter is not been specified, by default,
     *                       the master language entry will be deleted.
     *                       </p>
     *                       <br>
     *                       <b>Delete master language along with all its localized:
     *                       </b>For this request, instead of the locale
     *                       query parameter, you need to pass the
     *                       delete_all_localized:true query parameter
     *                       <br>
     *                       <b>Delete multiple localized entry:</b>Additionally,
     *                       you can delete specific localized entries by
     *                       passing the locale codes in the Request body using the
     *                       locales key as follows:
     * @param requestBody    you can delete specific localized entries by passing
     *                       the locale codes in the Request body using the locales
     *                       key as follows
     *                       ```
     *                       {
     *                       "entry": {
     *                       "locales": ["hi-in", "mr-in", "es"]
     *                       }
     *                       }
     *                       ```
     * @return the retrofit2.Call
     *         <p>
     *         [Read
     *         More]{https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-an-entry}
     */
    public Call<ResponseBody> delete(String entryUId, JSONObject requestBody, Map<String, Object> queryParameter) {
        return this.service.delete(this.headers, this.contentTypeUid, entryUId, requestBody, queryParameter);
    }

    /**
     * Version naming allows you to assign a name to a version of an entry for easy
     * identification. For more
     * information, refer to the Name Entry Version documentation.
     * <br>
     *
     * @param entryUId
     *                    The entry Uid
     * @param version
     *                    Enter the version number of the entry to which you want to
     *                    assign a name.
     * @param requestBody
     *                    RequestBody like below.
     *                    ```
     *                    { "entry": { "_version_name":
     *                    "Test version", "locale": "fr-fr", "force": true
     *                    }
     *                    }
     *                    ```
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> versionName(String entryUId, int version, JSONObject requestBody) {
        return this.service.versionName(this.headers, this.contentTypeUid, entryUId, String.valueOf(version),
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
     * published in the master locale will be
     * returned.
     *
     * @param entryUId::
     *                       The UID of the entry to which you want to assign a
     *                       specific
     *                       version name.
     * @param queryParameter
     *                       - skip(optional) Enter the number of version details to
     *                       be
     *                       skipped.
     *                       - limit(optional): Enter the maximum number of version
     *                       details to be returned.
     *                       - named(optional): Set to ‘true’ if you want to
     *                       retrieve
     *                       only the named versions of your entry.
     *                       - include_count(optional): Enter 'true' to get the
     *                       total
     *                       count of the entry version details.
     *                       - locale(optional): Enter the code of the language of
     *                       which
     *                       the entries need to
     *                       be included. Only the version details of entries
     *                       published
     *                       in this locale will be displayed
     *
     *
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> detailOfAllVersion(String entryUId, Map<String, Object> queryParameter) {
        return this.service.detailOfAllVersion(this.headers, this.contentTypeUid, entryUId, queryParameter);
    }

    /**
     *
     * @param entryUId       The UID of the entry of which you want to delete the
     *                       version name.
     * @param versionNumber: Enter the version number of the entry that you want to
     *                       delete.
     * @param requestBody    Request body for the delete operation
     *                       ```
     *                       {
     *                       "entry": {
     *                       "locale": "en-us"
     *                       }
     *                       }
     *                       ```
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> deleteVersionName(String entryUId, int versionNumber, JSONObject requestBody) {
        return this.service.deleteVersionName(this.headers, this.contentTypeUid, entryUId, versionNumber, requestBody);
    }

    /**
     * The Get references of an entry call returns all the entries of content types
     * that are referenced by a particular
     * entry.
     * <br>
     *
     * @param entryUId
     *                       The entry uid
     * @param queryParameter
     *                       The Query parameter: Locale: Enter the code of the
     *                       language of which the entries need to be included.
     *                       Only the entries published in this locale will be
     *                       displayed
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getReference(String entryUId, Map<String, Object> queryParameter) {
        return this.service.reference(this.headers, this.contentTypeUid, entryUId, queryParameter);
    }

    /**
     * The Get languages of an entry call returns the details of all the languages
     * that an entry exists in
     *
     * @param entryUId
     *                       the entry uid
     * @param queryParameter
     *                       the query parameter The Query parameter: Locale: Enter
     *                       the code of the language of which the entries need
     *                       to be included. Only the entries published in this
     *                       locale will be displayed
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getLanguage(String entryUId, Map<String, Object> queryParameter) {
        return this.service.language(this.headers, this.contentTypeUid, entryUId, queryParameter);
    }

    /**
     * To Localize an entry request allows you to localize an entry i.e., the entry
     * will cease to fetch data from its
     * fallback language and possess independent content specific to the selected
     * locale.
     * <br>
     * <p>
     * <b>Note:</b> This request will only create the localized version of your
     * entry and not publish it. To publish
     * your localized entry, you need to use the Publish an entry request and pass
     * the respective locale code in the
     * locale={locale_code} parameter.
     * </p>
     *
     * @param entryUId
     *                    The entry uid
     * @param requestBody
     *                    In the "Body" parameter, you need to provide the content
     *                    of your entry based on the content type.
     * @param localeCode
     *                    Enter the code of the language to localize the entry of
     *                    that particular language
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> localize(@NotNull String entryUId, @NotNull JSONObject requestBody,
            @NotNull String localeCode) {
        return this.service.localize(this.headers, this.contentTypeUid, entryUId, localeCode, requestBody);
    }

    /**
     * The Unlocalize an entry request is used to unlocalize an existing entry. Read
     * more about Localization.
     *
     * @param entryUId
     *                   The entry uid
     * @param localeCode
     *                   Enter the code of the language to localize the entry of
     *                   that particular language
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> unLocalize(@NotNull String entryUId, @NotNull String localeCode) {
        return this.service.unLocalize(this.headers, this.contentTypeUid, entryUId, localeCode);
    }

    /**
     * The Export an entry call is used to export an entry. The exported entry data
     * is saved in a downloadable JSON
     * file.
     *
     * @param entryUId
     *                       The entry uid
     * @param queryParameter
     *                       Locale: Enter the code of the language to localize the
     *                       entry of that particular language
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> export(@NotNull String entryUId, Map<String, Object> queryParameter) {
        return this.service.export(this.headers, this.contentTypeUid, entryUId, queryParameter);
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
     *
     * @param queryParameter
     *                       the query parameter
     *                       <br>
     *                       locale (optional): Enter the code of the language to
     *                       localize the entry of that particular
     *                       <br>
     *                       overwrite (optional): Select 'true' to replace an
     *                       existing entry with the imported entry file.
     *                       language
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> imports(Map<String, Object> queryParameter) {
        return this.service.imports(this.headers, this.contentTypeUid, queryParameter);
    }

    /**
     * The Import an existing entry call will import a new version of an existing
     * entry. You can create multiple
     * versions of an entry.
     *
     * @param entryUId
     *                       the entry uid
     * @param queryParameter
     *                       the query parameter
     *                       <br>
     *                       locale (optional): Enter the code of the language to
     *                       localize the entry of that particular
     *                       <br>
     *                       overwrite (optional): Select 'true' to replace an
     *                       existing entry with the imported entry file.
     *                       language
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> importExisting(@NotNull String entryUId, Map<String, Object> queryParameter) {
        return this.service.importExisting(this.headers, this.contentTypeUid, entryUId, queryParameter);
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
     * parameter. However, if you do not specify
     * any source locale(s), it will be published in the master locale
     * automatically.
     * <br>
     * Along with the above details, you also need to mention the master locale and
     * the version number of your entry
     * that you want to publish.
     *
     * @param entryUId
     *                    The entry uid
     * @param requestBody
     *                    The requestBody in JSONObject
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> publish(@NotNull String entryUId, @NotNull JSONObject requestBody) {
        return this.service.publish(this.headers, this.contentTypeUid, entryUId, requestBody);
    }

    /**
     * The Publishing an Entry With References request allows you to publish an
     * entry along with all its references at
     * the same time.
     *
     * @param requestBody
     *                       The request body in JSONObject format
     * @param queryParameter
     *                       Below are the query parameters <br>
     *                       - approvals:Set this to <b>true</b> to publish the
     *                       entries
     *                       that do not
     *                       require an approval to be published.<br>
     *                       skip_workflow_stage_check - Set this to <b>true</b> to
     *                       publish the
     *                       entries that are at a workflow stage where they satisfy
     *                       the applied to publish rules.
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> publishWithReference(@NotNull JSONObject requestBody,
            Map<String, Object> queryParameter) {
        return this.service.publishWithReference(this.headers, requestBody, queryParameter);
    }

    /**
     * To Unpublish an entry call will unpublish an entry at once, and also, gives
     * you the provision to unpublish an
     * entry automatically at a later date/time.
     * <p>
     * In the 'Body' section, you can specify the locales and environments from
     * which you want to unpublish the entry.
     * These details should be specified in the <b>entry</b> parameter. However, if
     * you do not specify a locale, it will
     * be unpublished from the master locale automatically.
     * <p>
     * You also need to mention the master locale and the version number of your
     * entry that you want to publish.
     * <p>
     * In case of Scheduled Unpublished, add the scheduled_at key and provide the
     * date/time in the ISO format as its
     * value. Example: "scheduled_at":"2016-10-07T12:34:36.000Z"
     *
     * @param entryUid
     *                    The entry uid
     * @param requestBody
     *                    The requestBody in JSONObject
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> unpublish(@NotNull String entryUid, @NotNull JSONObject requestBody) {
        return this.service.unpublish(this.headers, this.contentTypeUid, entryUid, requestBody);
    }

}
