package com.contentstack.cms.stack;

import com.contentstack.cms.Parametron;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * With the Comparing Branches, you can compare and check the differences between any two branches.
 * <br>
 * The <b>Compare branches</b> request returns a list of all the differences between two branches
 * <br>
 * <b>Note:</b>
 * <br>
 * <ul>
 *     <li>
 *         The compare branches feature is only available for the content types and global fields modules.
 *     </li>
 *     <li>
 *         If the number of Content Types/Global Fields that need to be compared is more
 *         than 100, you will receive a Next URL in the response body.
 *         The comparison limit is set at 100, and for every comparison that goes beyond this limit,
 *         the process will be completed in segments of 100.
 *     </li>
 * </ul>
 *
 * @see <a href=
 * "https://www.contentstack.com/docs/developers/apis/content-management-api/#compare-branches"></a>Compare Branches
 */
public class Compare implements Parametron {

    private final CompareService service;
    private final Map<String, String> headers;
    private final HashMap<String, Object> params;

    /**
     * Instantiates a new Compare.
     *
     * @param instance
     *         the instance
     * @param baseBranchId
     *         The basis on which comparison is done. If kept empty or null, the source branch of the compare branch is
     *         considered by default
     * @param compareBranchId
     *         the branch you want to compare with the base branch
     */
    public Compare(Retrofit instance, String baseBranchId, String compareBranchId) {
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
        this.params = new HashMap<>();
        this.service = instance.create(CompareService.class);
        this.params.put("base_branch", baseBranchId);
        this.params.put("compare_branch", compareBranchId);
    }


    /**
     * Compare all the branches
     * <p>
     * <b>Query Parameters</b>
     * Few optional parameters are available to include in this call.
     * <br>Add single parameters using
     * {@link #addParam(String, Object)}
     * <br> Or <br>
     * Add multiple parameters using {@link #addParams(HashMap)}
     * <ul>
     *     <li>
     *         <b>skip</b> - The number of branches to be skipped from the response body. : Example: 2
     *     </li>
     *     <li>
     *         <b>limit</b> - The maximum number of branches compare result to be returned. The default limit is set at 100.
     *     </li>
     * </ul>
     *
     * @return the call
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query params
     */
    Call<ResponseBody> all() {
        return this.service.all(this.headers, this.params);
    }

    /**
     * <b>Compare Content Type between Branches</b>
     * <br>
     * The Compare content types between branches request returns a list of all the differences in content types between
     * the two specified branches.
     * <br>
     * <b>Query Parameters</b>
     * Few optional parameters are available to include in this call.
     * <br>Add single parameters using
     * <p>
     * Add multiple parameters using {@link #addParams(HashMap)}
     * <ul>
     *     <li>
     *         <b>skip</b> - The number of branches to be skipped from the response body. : Example: 2
     *     </li>
     *     <li>
     *         <b>limit</b> - The maximum number of branches compare result to be returned. The default limit is set at 100.
     *     </li>
     *     <li>
     *         <b>include_schemas</b> - The branch you want to compare with the base branch.
     *     </li>
     * </ul>
     *
     * @return the call
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query parameters
     */
    Call<ResponseBody> contentType() {
        return this.service.compareContentTypesBetweenBranches(this.headers, this.params);
    }

    /**
     * <b>Compare Specific Global Fields between Branches</b>
     * <br>
     * The Compare specific global field between branches request returns all the differences of the specified global
     * field between the two specified branches.
     * <br>
     *
     * @return the call
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query parameters
     */
    Call<ResponseBody> globalField() {
        return this.service.compareGlobalFieldBetweenBranches(this.headers, this.params);
    }


    /**
     * The Compare specific content type between branches request returns all the differences of the specified content
     * type between the two specified branches
     *
     * @param contentTypeId
     *         the unique ID of the content type of which you want to retrieve the difference. The UID is generated
     *         based on the title of the content type. The unique ID of a content type is unique across a stack.
     * @return the call
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query parameters
     */
    Call<ResponseBody> specificContentType(@NotNull String contentTypeId) {
        return this.service.compareSpecificContentTypeBetweenBranches(this.headers, contentTypeId, this.params);
    }


    /**
     * The Compare specific global field between branches request returns all the differences of the specified global
     * field between the two specified branches.
     *
     * @param globalFieldUid
     *         the unique ID of the global field  of which you want to retrieve the difference. The UID is generated
     *         based on the title of the global field. The unique ID of a global field is unique across a stack.
     * @return the call
     * @see #addHeader(String, String) #addHeader(String, String)to add headers
     * @see #addParam(String, Object) #addParam(String, Object)to add query parameters
     */
    Call<ResponseBody> specificGlobalField(String globalFieldUid) {
        return this.service.compareSpecificGlobalFieldBetweenBranches(this.headers, globalFieldUid, this.params);
    }


    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link Compare} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public Compare addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * Adds a header with the specified key and value to this location and returns the updated location.
     *
     * @param key
     *         the key of the header to be added
     * @param value
     *         the value of the header to be added
     * @return a new {@link Compare} object with the specified header added
     * @throws NullPointerException
     *         if the key or value argument is null
     */
    @Override
    public Compare addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param params
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Compare} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public Compare addParams(@NotNull HashMap params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * Adds the specified parameters to this location and returns the updated location.
     *
     * @param headers
     *         a {@link HashMap} containing the parameters to be added
     * @return a new {@link Compare} object with the specified parameters added
     * @throws NullPointerException
     *         if the params argument is null
     */
    @Override
    public Compare addHeaders(@NotNull HashMap headers) {
        this.headers.putAll(headers);
        return this;
    }
}
