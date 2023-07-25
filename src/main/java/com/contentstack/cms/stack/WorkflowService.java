package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface WorkflowService {

        @GET("workflows")
        Call<ResponseBody> fetch(
                        @HeaderMap Map<String, Object> headers,
                        @QueryMap Map<String, Object> params);

        @GET("workflows/{workflow_uid}")
        Call<ResponseBody> single(
                        @HeaderMap Map<String, Object> headers,
                        @Path("workflow_uid") String workflowUid);

        @POST("workflows")
        Call<ResponseBody> create(
                        @HeaderMap Map<String, Object> headers,
                        @Body JSONObject body);

        @PUT("workflows/{workflow_uid}")
        Call<ResponseBody> update(
                        @HeaderMap Map<String, Object> headers,
                        @Path("workflow_uid") String releaseUid,
                        @Body JSONObject body);

        @GET("workflows/{workflow_uid}/disable")
        Call<ResponseBody> disable(
                        @HeaderMap Map<String, Object> headers,
                        @Path("workflow_uid") String workflowUid);

        @GET("workflows/{workflow_uid}/enable")
        Call<ResponseBody> enable(
                        @HeaderMap Map<String, Object> headers,
                        @Path("workflow_uid") String workflowUid);

        @DELETE("workflows/{workflow_uid}")
        Call<ResponseBody> delete(
                        @HeaderMap Map<String, Object> headers,
                        @Path("workflow_uid") String workflowUid);

        @POST("content_types/{content_type_uid}/entries/{entry_uid}/workflow")
        Call<ResponseBody> updateWorkflowStage(
                        @HeaderMap Map<String, Object> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap Map<String, Object> params,
                        @Body JSONObject body);

        @POST("workflows/publishing_rules")
        Call<ResponseBody> createPublishRules(
                        @HeaderMap Map<String, Object> headers,
                        @Body JSONObject body);

        @PUT("workflows/publishing_rules/{rule_uid}")
        Call<ResponseBody> updatePublishRules(
                        @HeaderMap Map<String, Object> headers,
                        @Path("rule_uid") String ruleUid,
                        @Body JSONObject body);

        @DELETE("workflows/publishing_rules/{rule_uid}")
        Call<ResponseBody> deletePublishRules(
                        @HeaderMap Map<String, Object> headers,
                        @Path("rule_uid") String ruleUid);

        @GET("workflows/publishing_rules")
        Call<ResponseBody> fetchPublishRules(
                        @HeaderMap Map<String, Object> headers,
                        @QueryMap Map<String, Object> params);

        @GET("workflows/publishing_rules/{rule_uid}")
        Call<ResponseBody> fetchPublishRules(
                        @HeaderMap Map<String, Object> headers,
                        @Path("rule_uid") String ruleUid);

        @GET("workflows/content_type/{content_type_uid}")
        Call<ResponseBody> fetchPublishRuleContentType(
                        @HeaderMap Map<String, Object> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @QueryMap Map<String, Object> params);

        @POST("content_types/{content_type_uid}/entries/{entry_uid}/workflow")
        Call<ResponseBody> publishRequestApproval(
                        @HeaderMap Map<String, Object> headers,
                        @Path("content_type_uid") String contentTypeUid,
                        @Path("entry_uid") String entryUid,
                        @QueryMap Map<String, Object> params,
                        @Body JSONObject body);

        @GET("workflows/content_type/{content_type_uid}")
        Call<ResponseBody> fetchTasks(
                        @HeaderMap Map<String, Object> headers,
                        @QueryMap Map<String, Object> params);

}
