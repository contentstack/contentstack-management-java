package com.contentstack.cms.app;

import com.contentstack.cms.core.BadArgumentException;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


public class Installation {

    private String installationId;
    protected HashMap<String, String> headers;
    private final InstallationService service;
    protected HashMap<String, Object> params;
    protected String manifestId;
    private static final String INSTALLATION_ID_ERR_MES = "installation uid is required";

    public Installation(Retrofit client, @NotNull String organizationUid, @NotNull String manifestUid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.manifestId = manifestUid;
        this.headers.put("organization_uid", organizationUid);
        this.service = client.create(InstallationService.class);
    }

    public Installation(Retrofit client, @NotNull String organizationUid, @NotNull String manifestUid, @NotNull String installationId) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.manifestId = manifestUid;
        this.installationId = installationId;
        this.headers.put("organization_uid", organizationUid);
        this.service = client.create(InstallationService.class);
    }

    private void validateBody(JSONObject body) {
        if (!body.containsKey("target_type") || !body.containsKey("target_uid")) {
            throw new BadArgumentException("target_type and target_uid are required field in the body");
        }
    }


    /**
     * HTTP header parameters are key-value pairs that are included in the header to provide additional information
     * about the request or response:
     *
     * @param key
     *         header key is being sent in the request
     * @param value
     *         header value is being sent in the request against to the header key
     */
    public Installation addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Params in a request refer to the key-value pairs that are sent along with the request to provide additional
     * information or data:
     *
     * @param key
     *         key for the request param
     * @param value
     *         value for the request param
     */
    public Installation addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * (protected) responsible to clear all params
     */
    protected Installation clearParams() {
        this.params.clear();
        return this;
    }

    /**
     * Removes available parameter from the request.
     *
     * @param key
     *         parameter key of the request
     */
    public Installation removeParam(@NotNull String key) {
        this.params.remove(key);
        return this;
    }

    /**
     * This is used to initiate the installation of the app
     *
     * @param body
     *         the request body
     *         <p>(required) target_type is the required field name in the request body</p>
     *         <p>(required) target_uid is the required field name in the request body</p>
     *         <p>
     *         <code>
     *         { "data": { "status": "installed", "installation_uid": "installation_uid123", "redirect_to": "config",
     *         "redirect_uri": "<a href="https://app.contentstack.com">...</a>" } }
     *         </code>
     *         <p>
     * @return Call
     */
    public Call<ResponseBody> create(JSONObject body) {
        validateBody(body);
        return this.service.create(this.headers, this.manifestId, body);
    }

    /**
     * The re-installation call is used to upgrade the installation of an app
     *
     * @param body
     *         the request body should contain the required field <b>target_type</b> and <b>target_uid</b>
     *         <p>(required) target_type is the required field name in the request body</p>
     *         <p>(required) target_uid is the required field name in the request body</p>
     *         <p>
     *         <code>  { "status": "installed", "target_type": "_uid123", "target_uid": "config", "redirect_uri": <a
     *         href="https://app.contentstack.com/installed-apps/{uid}/stacks/{stack-uid}/config">...</a> }
     *         </code>
     * @return Call
     */
    public Call<ResponseBody> reinstall(@NotNull JSONObject body) {
        validateBody(body);
        return this.service.update(this.headers, this.manifestId, body);
    }


    /**
     * The GET installation call is used to retrieve all installations of an app
     * <p>
     * <b>Note : It will provide installation data based on manifest uid</b>
     *
     * @return Call gets installation by manifest uid
     */
    public Call<ResponseBody> fetch() {
        if (this.manifestId == null || this.manifestId.isEmpty()) {
            throw new BadArgumentException("Manifest uid required");
        }
        return this.service.fetch(this.headers, this.manifestId);
    }

    /**
     * The List Installations call is used to retrieve a list of installations inside an organization.
     * <p>
     * Use the #addParams method to add any optional parameters to the request
     * <ul>
     * <li>(Optional) uid : String(comma separated): The app uid of which installation need to be listed</li>
     * <li>(Optional) installation_uid : String(comma separated): List of installations uid</li>
     * <li>(Optional) target_uid's : String(comma separated): List of stack api keys or organization UID</li>
     * <li>(Optional) sort : String : Sort field name</li>
     * <li>(Optional) order : String : Sort order</li>
     * <li>(Optional) limit : Int : Page size</li>
     * <li>(Optional) skip : Int : Number of records to skip</li>
     * </ul>
     *
     * @return Call
     */
    public Call<ResponseBody> find() {
        if (this.installationId == null || this.installationId.isEmpty()) {
            throw new BadArgumentException(INSTALLATION_ID_ERR_MES);
        }
        return this.service.find(this.headers, this.installationId, this.params);
    }


    /**
     * The GET installation call is used to retrieve a specific installation of an app
     *
     * @param installationUid
     *         The Installation ID of the app
     * @return Call
     */
    public Call<ResponseBody> fetch(String installationUid) {
        return this.service.fetch(this.headers, installationUid);
    }


    /**
     * The update call is used to update information of an app
     *
     * @param installationUid
     *         The Installation id of the app
     * @param body
     *         The request body of type JSONObject
     *         <ul>
     *         <li> (Optional) config : Object: Config that needs to be updated</li>
     *         <li> (Optional) server_config : Object: Server Config that needs to be updated</li>
     *         <li> (Optional) webhooks : Array of objects with webhook_uid and channels: Webhooks attached to the app
     *         that needs to be updated</li>
     *         <li> (Optional) ui_locations : Array of object with type and meta keys: The location of the app in the Ui
     *         flow that is to updated</li>
     *         </ul>
     * @return Call
     */
    public Call<ResponseBody> update(@NotNull String installationUid, JSONObject body) {
        if (installationUid.isEmpty()) {
            throw new BadArgumentException(INSTALLATION_ID_ERR_MES);
        }
        this.installationId = installationUid;
        return this.service.updateById(this.headers, this.installationId, body);
    }


    /**
     * The update call is used to update information of an app (if installation uid is already available)
     *
     * @param body
     *         The request body of type JSONObject
     *         <ul>
     *         <li> (Optional) config : Object: Config that needs to be updated</li>
     *         <li> (Optional) server_config : Object: Server Config that needs to be updated</li>
     *         <li> (Optional) webhooks : Array of objects with webhook_uid and channels: Webhooks attached to the app
     *         that needs to be updated</li>
     *         <li> (Optional) ui_locations : Array of object with type and meta keys: The location of the app in the Ui
     *         flow that is to updated</li>
     *         </ul>
     * @return Call: request to an HTTP server that can be executed asynchronously/synchronously and returns a response
     * of type ResponseBody
     */
    public Call<ResponseBody> update(JSONObject body) {
        if (this.installationId.isEmpty()) {
            throw new BadArgumentException(INSTALLATION_ID_ERR_MES);
        }
        return this.service.updateById(this.headers, this.installationId, body);
    }


    /**
     * Uninstall call is used to delete an existing installation
     *
     * @param installationId
     *         The installation id of the app
     * @return Call: request to an HTTP server that can be executed asynchronously/synchronously and returns a response
     * of type ResponseBody
     */
    public Call<ResponseBody> uninstall(String installationId) {
        return this.service.uninstall(this.headers, installationId);
    }


    /**
     * The Create token call is used to exchange tokens between the redirect URL and our apis.When we install the oauth
     * app
     *
     * @param body
     *         the request body
     *         <ul>
     *         <li>(Required) : grant_type : String : Token grant type</li>
     *         <li>(Required when grand_type is authorization_code) : code : int : The code received through the create
     *         installation API</li>
     *         <li>(Required) : grant_type : int : Required when grand_type is authorization_code</li>
     *         <li>(Required) : client_id : int : The client ID of the app</li>
     *         <li>(Required) : client_secret : String : Client Secret of the App</li>
     *         <li>(Required) : redirect_uri : String : The redirect URL of the app</li>
     *         <li>(Required when grand_type is refresh_token) : refresh_token : String : The refresh token received</li>
     *         </ul>
     * @return Call: request to an HTTP server that can be executed asynchronously/synchronously and returns a response
     * of type ResponseBody
     */
    public Call<ResponseBody> createToken(@NotNull JSONObject body) {
        if (body.isEmpty()) {
            throw new BadArgumentException("body should not ne empty");
        }
        return this.service.createToken(body);
    }


    /**
     * The GET webhook call is used to retrieve ui location configuration details of an installation.
     *
     * @return Call: request to an HTTP server that can be executed asynchronously/synchronously and returns a response
     * of type ResponseBody
     */
    public Call<ResponseBody> getWebhook() {
        if (installationId.isEmpty()) {
            throw new BadArgumentException("Installation uid should not be empty");
        }
        return this.service.getWebhook(this.headers, this.installationId);
    }

    /**
     * The GET webhook call is used to retrieve ui location configuration details of an installation.
     *
     * @param installationId
     *         the installation id of an app
     * @return Call: request to an HTTP server that can be executed asynchronously/synchronously and returns a response
     * of type ResponseBody
     */
    public Call<ResponseBody> getWebhook(@NotNull String installationId) {
        if (installationId.isEmpty()) {
            throw new BadArgumentException("Installation uid should not be empty");
        }
        return this.service.getWebhook(this.headers, this.installationId);
    }

    /**
     * The GET executions call is used to retrieve all executions of webhook of an installation.
     *
     * @param webhookId
     *         The id of the webhook of installation of an app
     * @return Call: request to an HTTP server that can be executed asynchronously/synchronously and returns a response
     * of type ResponseBody
     */
    public Call<ResponseBody> findExecutions(@NotNull String webhookId) {
        if (webhookId.isEmpty()) {
            throw new BadArgumentException("webhook uid should not empty");
        }
        return this.service.findExecutions(this.headers, this.installationId, webhookId);
    }

    /**
     * The GET executions call is used to retrieve an execution of webhook of an installation.
     *
     * @param webhookId
     *         The uid of the webhook of installation of an app
     * @param executionId
     *         The execution uid of webhook of the installed an app
     * @return Call: request to an HTTP server that can be executed asynchronously/synchronously and returns a response
     * of type ResponseBody
     */
    public Call<ResponseBody> fetchExecution(@NotNull String webhookId, @NotNull String executionId) {
        if (webhookId.isEmpty() || executionId.isEmpty()) {
            throw new BadArgumentException("webhook/execution uid should not empty");
        }
        return this.service.fetchExecution(this.headers, this.installationId, webhookId, executionId);
    }

}
