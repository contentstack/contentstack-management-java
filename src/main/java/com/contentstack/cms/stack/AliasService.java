package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AliasService {

        /**
         * The function fetch makes a GET request to retrieve branch aliases from a
         * specific endpoint, with
         * optional headers and query parameters.
         * 
         * @param headers A map containing the headers to be included in the request.
         *                The keys in the map
         *                represent the header names, and the values represent the
         *                header values.
         * @param query   The `query` parameter is a map that contains key-value pairs
         *                representing the query
         *                parameters to be included in the request URL. These query
         *                parameters are used to filter or modify
         *                the data that is returned by the API.
         * @return The method is returning a `Call` object with a generic type of
         *         `ResponseBody`.
         */
        @GET("stacks/branch_aliases")
        Call<ResponseBody> fetch(
                        @HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> query);

        /**
         * The above function is a GET request that retrieves a single item from a
         * specific branch alias using
         * the provided headers and branch alias UID.
         * 
         * @param headers The `headers` parameter is a map that contains the headers to
         *                be included in the HTTP
         *                request. Each entry in the map represents a header field,
         *                where the key is the header name and the
         *                value is the header value.
         * @param uid     The `uid` parameter is a string that represents the unique
         *                identifier of a branch alias.
         * @return The method is returning a `Call` object with a generic type of
         *         `ResponseBody`.
         */
        @GET("stacks/branch_aliases/{branch_alias_uid}")
        Call<ResponseBody> single(
                        @HeaderMap Map<String, Object> headers, @Path("branch_alias_uid") String uid);

        /**
         * The above function is a PUT request to update a resource with the given
         * headers and request body.
         * 
         * @param headers A map containing the headers for the API request. The keys in
         *                the map represent the
         *                header names, and the values represent the header values.
         * @param body    The `body` parameter is a JSON object that will be sent in the
         *                request body. It contains
         *                the data that you want to update for the
         *                `stacks/branch_aliases` endpoint.
         * @return The method is returning a `Call` object with a generic type of
         *         `ResponseBody`.
         */
        @PUT("stacks/branch_aliases")
        Call<ResponseBody> update(
                        @HeaderMap Map<String, Object> headers, @Body JSONObject body);

        /**
         * This function sends a DELETE request to a specific endpoint with the provided
         * headers, branch alias
         * UID, and query parameters.
         * 
         * @param headers The `headers` parameter is a map that contains the headers to
         *                be included in the HTTP
         *                request. Each entry in the map represents a header field name
         *                and its corresponding value.
         * @param uid     The `uid` parameter is a string that represents the unique
         *                identifier of the branch alias
         *                that you want to delete.
         * @param query   The `query` parameter is a map that contains key-value pairs
         *                representing the query
         *                parameters to be included in the request URL. These query
         *                parameters are used to provide additional
         *                information or filters for the request.
         * @return The method is returning a `Call` object with a generic type of
         *         `ResponseBody`.
         */
        @DELETE("stacks/branch_aliases/{branch_alias_uid}")
        Call<ResponseBody> delete(
                        @HeaderMap Map<String, Object> headers,
                        @Path("branch_alias_uid") String uid, @QueryMap Map<String, Object> query);

}
