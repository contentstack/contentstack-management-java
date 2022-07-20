package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;


/**
 * Audit log displays a record of all the activities performed in a stack and helps you keep a track of all published
 * items, updates, deletes, and current status of the existing content. Read more about Audit Log.
 * <p>
 * You can now pass the branch header in the API request to fetch or manage modules located within specific branches of
 * the stack. Additionally, you can also set the include_branch query parameter to true to include the branch top-level
 * key in the response. This key specifies the unique ID of the branch where the concerned Contentstack module resides.
 * <p>
 * Read more about <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#audit-log">Audit
 * Log</a>
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @since 2022-05-19
 */
public class AuditLog {

    protected final AuditLogService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;
    private String logItemUid;

    protected AuditLog(Retrofit retrofit) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.service = retrofit.create(AuditLogService.class);
    }

    protected AuditLog(Retrofit retrofit, String uid) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.logItemUid = uid;
        this.service = retrofit.create(AuditLogService.class);
    }

    void validate() {
        if (this.logItemUid == null || this.logItemUid.isEmpty())
            throw new IllegalStateException("LogItem Uid can not be null or empty");
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
    public Call<ResponseBody> find() {
        return this.service.fetch(this.headers, this.params);
    }


    /**
     * The Get audit log item request is used to retrieve a specific item from the audit log of a stack.
     *
     * @return Call
     */
    public Call<ResponseBody> fetch() {
        validate();
        return this.service.fetch(this.headers, this.logItemUid);
    }


}
