package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

public interface AuditLogService {

    @GET("audit-logs")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @QueryMap HashMap<String, Object> params);

    @GET("audit-logs/{log_item_uid}")
    Call<ResponseBody> fetch(
            @HeaderMap Map<String, Object> headers,
            @Path("log_item_uid") String logItemUid);
}
