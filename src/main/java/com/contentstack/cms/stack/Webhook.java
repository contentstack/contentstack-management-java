package com.contentstack.cms.stack;

import com.contentstack.cms.core.Util;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.File;
import java.util.HashMap;


/**
 * A webhook is a mechanism that sends real-time information to any third-party app or service to keep your application
 * in sync with your Contentstack account. Webhooks allow you to specify a URL to which you would like Contentstack to
 * post data when an event happens. Read more about Webhooks.
 * <p>
 * Read more about <a
 * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#webhooks">Webhooks</a>
 *
 * @author Shailesh Mishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Webhook {

    protected final WebhookService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private final Retrofit retrofit;

    protected Webhook(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.retrofit = retrofit;
        this.service = this.retrofit.create(WebhookService.class);
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
     * The Get audit log request is used to retrieve the audit log of a stack.
     * <p>
     * You can apply queries to filter the results. Refer to the Queries section for more details.
     *
     * @return Call
     */
    public Call<ResponseBody> fetch() {
        return this.service.fetch(this.headers, this.params);
    }


    /**
     * The Get webhook request returns comprehensive information on a specific webhook.
     * <p>
     * When executing the API call, under the <b>Header</b> using {@link #addHeader(String, Object)} section, you need
     * to enter the authtoken that you receive after logging into your account.
     *
     * @param webhookUid
     *         The unique ID of the webhook of which you want to retrieve the details. Execute the <b>Get all
     *         webhooks</b> call to retrieve the UID of a webhook
     * @return Call
     */
    public Call<ResponseBody> fetch(@NotNull String webhookUid) {
        return this.service.fetch(this.headers, webhookUid);
    }


    /**
     * The Create a webhook request allows you to create a new webhook in a specific stack.
     * <p>
     * In the “Body” section, you need to enter the name of the webhook; the destination details i.e., target urls,
     * basic authentication details, and custom headers; and the channels; and set the disabled and concise_payload
     * parameters as per requirement.
     * <p>
     * The disabled parameter allows you to enable or disable the webhook. You can set its value to either false to
     * enable the webhook and true to disable the webhook.
     * <p>
     * The concise_payload parameter allows you to send a concise JSON payload to the target URL when a specific event
     * occurs. To send a comprehensive JSON payload, you can set its value to false. However, to send a concise payload,
     * set the value of the concise_payload parameter to true.
     * <p>
     * When creating a webhook, you need to specify the branch scope through the following schema in the request body:
     * <pre>
     *     "branches":[
     *     "main"
     * ]
     * </pre>
     * <b>Note:</b> In the Header, you need to use either the stack Management Token (recommended) or the user
     * Authtoken, along with the stack API key, to make valid Content Management API requests. For more information,
     * refer to Authentication.
     *
     * @param requestBody
     *         The @JSONObject you want to post
     * @return Call
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        this.headers.put(Util.CONTENT_TYPE, Util.CONTENT_TYPE_VALUE);
        return this.service.create(this.headers, requestBody);
    }

    /**
     * The Update webhook request allows you to update the details of an existing webhook in the stack.
     * <p>
     * In the <b>Body</b> section, you need to enter new details such as the name of the webhook; the destination
     * details i.e., target urls, basic authentication details, and custom headers; and the channels; or reset the
     * disabled or concise_payload parameters as per requirement.
     * <p>
     * The disabled parameter allows you to enable or disable the webhook. You can set its value to either false to
     * enable the webhook and true to disable the webhook.
     * <p>
     * The concise_payload parameter allows you to send a concise JSON payload to the target URL when a specific event
     * occurs. To send a comprehensive JSON payload, you can set its value to false. However, to send a concise payload,
     * set the value of the concise_payload parameter to true.
     * <p>
     * When updating a webhook, you need to specify the branch scope through the following schema in the request body:
     *
     * <pre>
     *     "branches":[
     *     "main"
     * ]
     * </pre>
     * <p>
     * Note: In the Header, you need to use either the stack Management Token (recommended) or the user Authtoken, along
     * with the stack API key, to make valid Content Management API requests. For more information, refer to
     * Authentication
     *
     * @param webhookUid
     *         The unique ID of the webhook of which you want to retrieve the details.
     * @param requestBody
     *         the request body @JSONObject
     * @return Call
     */
    public Call<ResponseBody> update(@NotNull String webhookUid, JSONObject requestBody) {
        return this.service.update(this.headers, webhookUid, requestBody);
    }

    /**
     * The Delete webhook call deletes an existing webhook from a stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account
     *
     * @param webhookUid
     *         The unique ID of the webhook of which you want to retrieve the details.
     * @return Call
     */
    public Call<ResponseBody> delete(@NotNull String webhookUid) {
        return this.service.delete(this.headers, webhookUid);
    }


    /**
     * The Export a Webhook request exports an existing webhook. The exported webhook data is saved in a downloadable
     * JSON file.
     *
     * @param webhookUid
     *         The unique ID of the webhook of which you want to retrieve the details.
     * @return Call
     */
    public Call<ResponseBody> export(@NotNull String webhookUid) {
        return this.service.export(this.headers, webhookUid);
    }


    /**
     * The <b>Import Webhook</b> section consists of the following two requests that will help you to import new
     * Webhooks or update existing ones by uploading JSON files.
     * <p>
     * <b>Note:</b> You can try the call manually in any REST API client, such as Postman, by passing a <b>Body</b>
     * parameter named webhook under form-data. Select the input type as <b>File</b> and select the JSON file of the
     * webhook that you want to import.
     *
     * @param fileName
     *         the file name
     * @param jsonPath
     *         jsonPath like example /Users/shaileshmishra/Downloads/thejson.json
     * @return Call
     */
    public Call<ResponseBody> importWebhook(@NotNull String fileName, @NotNull String jsonPath) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("webhook", fileName, RequestBody.create(mediaType, new File(jsonPath))).build();
        this.headers.put(Util.CONTENT_TYPE, Util.MULTIPART);
        return this.service.imports(this.headers, body);
    }


    /**
     * The Import an Existing Webhook request will allow you to update the details of an existing webhook.
     *
     * @return Call
     */
    public Call<ResponseBody> importExisting() {
        return this.service.importExisting(this.headers);
    }

    /**
     * The Get executions of a webhook request allows you to fetch the execution details of a specific webhook, which
     * includes the execution UID. These details are instrumental in retrieving webhook logs and retrying a failed
     * webhook.
     * <p>
     * Each execution of a webhook is assigned a unique ID that allows you to gather information, such as
     * request-response body, retry attempts, and so on, pertaining to a specific execution of the webhook.
     * <p>
     * To filter the webhook execution details based on a specific date range, you must pass from and to as query
     * parameters. For both of these parameters, provide a date in ISO format as the value. For instance, to set the
     * start date in the from parameter to December 8, 2017, you can pass the date in ISO format as shown below:
     * <p>
     * <code>from="2017-12-08T00:00:00.000Z"</code>
     * <p>
     * To filter the webhook execution details based on whether the webhook successfully ran or failed to execute, pass
     * the query parameter under the URI Parameters section, and provide a query in JSON format as its value. Within the
     * query, you can use the status key to filter the response as per your desired execution status.
     * <p>
     * The following table shows values you can use for the query parameter:
     * <pre>
     *     <b>Success:</b>
     *     {
     *   "status": {
     *     "$gte": "200",
     *     "$lte": "399"
     *   }
     * }
     * <b>Failure:</b>
     * {
     *   "status": {
     *     "$gte": “400",
     *     "$lte": “599"
     *   }
     * }
     * </pre>
     * This API request will return a maximum of 100 records while fetching the execution details for a specific
     * webhook. Previously, there was no limit on the number of records returned. You can use the <b>skip</b> parameter
     * to fetch older records. To limit the number of records returned, you can use the “limit” parameter.
     *
     * @param webhookUid
     *         The unique ID of the webhook of which you want to retrieve the details
     * @return Call
     */
    public Call<ResponseBody> getExecutions(@NotNull String webhookUid) {
        return this.service.getExecutions(this.headers, webhookUid, this.params);
    }

    /**
     * This call makes a manual attempt to execute a webhook after the webhook has finished executing its automatic
     * attempts.
     * <p>
     * When executing the API call, in the <b>URI Parameter</b> section, enter the execution UID that you receive when
     * you execute the 'Get executions of webhooks' call.
     *
     * @param webhookUid
     *         The unique ID of the webhook of which you want to retrieve the details
     * @return Call
     */
    public Call<ResponseBody> retry(@NotNull String webhookUid) {
        return this.service.retry(this.headers, webhookUid);
    }

    /**
     * This call will return a comprehensive detail of all the webhooks that were executed at a particular execution
     * cycle.
     * <p>
     * When executing the API call, in the <b>URI Parameter</b> section, enter the execution UID that you receive when
     * you execute the <b>Get executions of webhooks</b> call.
     *
     * @param webhookUid
     *         The unique ID of the webhook of which you want to retrieve the details
     * @return Call
     */
    public Call<ResponseBody> getExecutionLog(@NotNull String webhookUid) {
        return this.service.getExecutionLog(this.headers, webhookUid);
    }

}