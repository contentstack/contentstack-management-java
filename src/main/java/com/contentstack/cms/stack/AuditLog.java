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
 * @author ishaileshmishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public class AuditLog {

    protected final AuditLogService service;
    protected HashMap<String, Object> headers;
    protected HashMap<String, Object> params;

    protected AuditLog(Retrofit retrofit, HashMap<String, Object> stackHeaders) {
        this.headers = new HashMap<>();
        this.params = new HashMap<>();
        this.headers.putAll(stackHeaders);
        this.service = retrofit.create(AuditLogService.class);
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
     * The Get audit log item request is used to retrieve a specific item from the audit log of a stack.
     *
     * @param logItemUid
     *         The UID of a specific log item you want to retrieve the details of
     * @return Call
     */
    public Call<ResponseBody> fetchAuditLog(@NotNull String logItemUid) {
        return this.service.fetch(this.headers, logItemUid);
    }


}
