package com.contentstack.cms.stack;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * A role is a collection of permissions that will be applicable to all the
 * users who are assigned this role.
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @see <a href=
 * "https://www.contentstack.com/docs/developers/invite-users-and-assign-roles/about-stack-roles">Roles
 * </a>
 * @since 2022-10-22
 */
public class Roles implements BaseImplementation<Roles> {

    protected final RolesService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    protected String roleUid;

    protected Roles(Retrofit retrofit,Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = retrofit.create(RolesService.class);
    }

    protected Roles(Retrofit retrofit,Map<String, Object> headers, String roleUid) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.roleUid = roleUid;
        this.service = retrofit.create(RolesService.class);
    }

    void validate() {
        if (this.roleUid == null || this.roleUid.isEmpty())
            throw new IllegalAccessError("Role uid can not be null or empty");
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
     * The <b>Get all roles</b> request returns comprehensive information about all
     * roles created in a stack.
     * <p>
     * You can add queries to extend the functionality of this API request. Under
     * the URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the Queries section of the Content
     * Delivery API doc
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-roles">Get
     * all
     * Roles
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> find() {
        return this.service.getRoles(this.headers, this.params);
    }

    /**
     * The Get a single role request returns comprehensive information on a specific
     * role.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#get-a-single-role">Get
     * a
     * single Roles
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.getRole(this.headers, this.roleUid);
    }

    /**
     * The Create a role request creates a new role in a stack.
     * <p>
     * In the <b>Body</b> section, mention the role name, description, users,
     * additional roles, rules (includes the
     * actions that can be performed on entries, fields, and/or assets), and
     * permissions (which include the details of
     * the content types, environments, and languages that are accessible).
     *
     * @param requestBody details of the delivery role in @{@link JSONObject} format
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#create-a-role">Create
     * a
     * Roles
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject requestBody) {
        return this.service.createRole(this.headers, requestBody);
    }

    /**
     * The Update role request lets you modify an existing role of your stack.
     * However, the pre-existing system roles
     * cannot be modified.
     * <p>
     * In the 'Body' section, include the updated details of the role which include
     * name, description, users, additional
     * roles, rules (includes the actions that can be performed on entries, fields,
     * and/or assets), and permissions
     * (which include the details of the content types, environments, and languages
     * that are accessible).
     *
     * @param requestBody the body should be of @{@link JSONObject} type
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#update-role">Update
     * Role
     * </a>
     * @see #addHeader(String, String) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 0.1.0
     */
    public Call<ResponseBody> update(@NotNull JSONObject requestBody) {
        this.validate();
        return this.service.updateRole(this.headers, this.roleUid, requestBody);
    }

    /**
     * The Delete role call deletes an existing role from your stack.
     *
     * @return Call
     * @see <a href=
     * "https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-role">Delete
     * a
     * Role
     * </a>
     * @see #addHeader(String, String) to add headers
     * @since 0.1.0
     */
    public Call<ResponseBody> delete() {
        this.validate();
        return this.service.deleteRole(this.headers, this.roleUid);
    }

    @Override
    public Roles addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    @Override
    public Roles addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public Roles addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public Roles addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }
}
