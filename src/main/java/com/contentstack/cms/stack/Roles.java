package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;

import static com.contentstack.cms.core.Util.CONTENT_TYPE;
import static com.contentstack.cms.core.Util.CONTENT_TYPE_VALUE;


/**
 * A role is a collection of permissions that will be applicable to all the users who are assigned this role.
 * <p>
 * Read more about <a
 * href="https://www.contentstack.com/docs/developers/invite-users-and-assign-roles/about-stack-roles">Roles</a>
 *
 * @author Shailesh Mishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Roles {

    protected final RolesService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    protected Roles(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(RolesService.class);
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
     * The <b>Get all roles</b> request returns comprehensive information about all roles created in a stack.
     * <p>
     * You can add queries to extend the functionality of this API request. Under the URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the Queries section of the Content Delivery API doc
     *
     * @return Call
     */
    public Call<ResponseBody> getRoles() {
        return this.service.getRoles(this.headers, this.params);
    }

    /**
     * The Get a single role request returns comprehensive information on a specific role.
     *
     * @param roleUid
     *         the UID of the role that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> getRole(@NotNull String roleUid) {
        return this.service.getRole(this.headers, roleUid);
    }

    /**
     * The Create a role request creates a new role in a stack.
     * <p>
     * In the <b>Body</b> section, mention the role name, description, users, additional roles, rules (includes the
     * actions that can be performed on entries, fields, and/or assets), and permissions (which include the details of
     * the content types, environments, and languages that are accessible).
     *
     * @param requestBody
     *         details of the delivery role in @{@link JSONObject} format
     * @return Call
     */
    public Call<ResponseBody> createRole(@NotNull JSONObject requestBody) {
        this.headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        return this.service.createRole(this.headers, requestBody);
    }

    /**
     * The Update role request lets you modify an existing role of your stack. However, the pre-existing system roles
     * cannot be modified.
     * <p>
     * In the 'Body' section, include the updated details of the role which include name, description, users, additional
     * roles, rules (includes the actions that can be performed on entries, fields, and/or assets), and permissions
     * (which include the details of the content types, environments, and languages that are accessible).
     *
     * @param roleUid
     *         the UID of the role that you want to retrieve
     * @param requestBody
     *         the body should be of @{@link JSONObject} type
     * @return Call
     */
    public Call<ResponseBody> updateRole(@NotNull String roleUid, @NotNull JSONObject requestBody) {
        this.headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        return this.service.updateRole(this.headers, roleUid, requestBody);
    }

    /**
     * The Delete role call deletes an existing role from your stack.
     *
     * @param roleUid
     *         the UID of the role that you want to retrieve
     * @return Call
     */
    public Call<ResponseBody> deleteRole(@NotNull String roleUid) {
        return this.service.deleteRole(this.headers, roleUid);
    }


}
