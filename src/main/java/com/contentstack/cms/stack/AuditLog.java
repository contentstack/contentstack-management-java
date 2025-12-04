package com.contentstack.cms.stack;

import com.contentstack.cms.core.ErrorMessages;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Audit log displays a record of all the activities performed in a stack and
 * helps you keep a track of all published
 * items, updates, deletes, and current status of the existing content. Read
 * more about Audit Log.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage
 * modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter
 * to true to include the branch top-level
 * key in the response. This key specifies the unique ID of the branch where the
 * concerned Contentstack module resides.
 * <p>
 * Read more about <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#audit-log">Audit
 * Log</a>
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-22
 */
public class AuditLog implements BaseImplementation<AuditLog> {

    protected final AuditLogService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private String logItemUid;

    protected AuditLog(Retrofit retrofit,Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.service = retrofit.create(AuditLogService.class);
    }

    protected AuditLog(Retrofit retrofit,Map<String, Object> headers, String uid) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.logItemUid = uid;
        this.service = retrofit.create(AuditLogService.class);
    }

    @Override
    public AuditLog addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    @Override
    public AuditLog addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public AuditLog addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public AuditLog addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    /**
     * Sets header for the request
     *
     * @param key header key for the request
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
     * You can apply queries to filter the results. Refer to the Queries section for
     * more details.
     *
     * @return Call
     */
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }

    /**
     * The Get audit log item request is used to retrieve a specific item from the
     * audit log of a stack.
     *
     * @return Call
     */
    public Call<ResponseBody> fetch() {
        Objects.requireNonNull(this.logItemUid, "ErrorMessages.LOG_ITEM_UID_REQUIRED");
        return this.service.fetch(this.headers, this.logItemUid);
    }

}
