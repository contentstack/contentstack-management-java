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
 *
 * @author Shailesh Mishra
 * @version 1.0.0
 * @see <a href="https://www.contentstack.com/docs/developers/invite-users-and-assign-roles/about-stack-roles">Roles
 *
 * </a>
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
     * The <b>Get all roles</b> request returns comprehensive information about all roles created in a stack.
     * <p>
     * You can add queries to extend the functionality of this API request. Under the URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the Queries section of the Content Delivery API doc
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-roles">Get
     * all Roles
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch() {
        return this.service.getRoles(this.headers, this.params);
    }

    /**
     * The Get a single role request returns comprehensive information on a specific role.
     *
     * @param roleUid
     *         The unique ID of the role of which you want to retrieve the details.
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-role">Get
     * a single Roles
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> single(@NotNull String roleUid) {
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
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-role">Create
     * a Roles
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
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
     *         The unique ID of the role of which you want to retrieve the details
     * @param requestBody
     *         the body should be of @{@link JSONObject} type
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-role">Update
     * Role
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull String roleUid, @NotNull JSONObject requestBody) {
        this.headers.put(CONTENT_TYPE, CONTENT_TYPE_VALUE);
        return this.service.updateRole(this.headers, roleUid, requestBody);
    }

    /**
     * The Delete role call deletes an existing role from your stack.
     *
     * @param roleUid
     *         The unique ID of the role of which you want to retrieve the details
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-role">Delete
     * a Role
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(@NotNull String roleUid) {
        return this.service.deleteRole(this.headers, roleUid);
    }


}
