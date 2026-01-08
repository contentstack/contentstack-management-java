package com.contentstack.cms.stack;

import com.contentstack.cms.core.ErrorMessages;

import com.contentstack.cms.BaseImplementation;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;

import java.util.HashMap;

/**
 * Terms are the fundamental building blocks in a taxonomy.
 * They are used to create hierarchical structures and are integrated into entries to classify and categorize information systematically.
 * <ul>
 *     <li>Create a Term</li>
 *     <li>Get all Terms of a Taxonomy</li>
 *     <li>Get a single Term</li>
 *     <li>Get descendants of a single Term</li>
 *     <li>Get ancestors of a single Term</li>
 *     <li>Update a Term</li>
 *     <li>Search for all Terms within all Taxonomies</li>
 * </ul>
 */
public class Terms implements BaseImplementation<Terms> {

    /**
     * The Taxonomy service.
     */
    final TaxonomyService taxonomyService;
    /**
     * The Headers.
     */
    final HashMap<String, Object> headers;
    /**
     * The Taxonomy id.
     */
    final String taxonomyId;
    /**
     * The Params.
     */
    final HashMap<String, Object> params;


    /**
     * Instantiates a new Terms.
     *
     * @param service    the service
     * @param headers    the headers
     * @param taxonomyId the taxonomy id
     */
    public Terms(@NotNull TaxonomyService service, HashMap<String, Object> headers, String taxonomyId) {
        this.taxonomyService = service;
        this.headers = headers;
        this.taxonomyId = taxonomyId;
        this.params = new HashMap<>();
    }


    /**
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return instance of Terms
     */
    @Override
    public Terms addParam(@NotNull String key, @NotNull Object value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * @param key   The key parameter is a string that represents the name or
     *              identifier of the header.
     *              It is used to specify the type of information being sent in the
     *              header.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return instance of Terms
     */
    @Override
    public Terms addHeader(@NotNull String key, @NotNull String value) {
        this.headers.put(key, value);
        return this;
    }

    /**
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return instance of Terms
     */
    @Override
    public Terms addParams(@NotNull HashMap<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    /**
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return instance of Terms
     */
    @Override
    public Terms addHeaders(@NotNull HashMap<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }


    /**
     * Create Terms call.
     *
     * @param body The JSONObject request body
     * @return instance of Call <br> <b>Example</b> <pre>     {@code
     *     Stack stack = new Contentstack.Builder().build().stack(headers);
     *     JSONObject body = new JSONObject();
     *     Term term = stack.taxonomy("taxonomyId").terms().create(body);
     *     } </pre>
     */
    public Call<ResponseBody> create(@NotNull JSONObject body) {
        return this.taxonomyService.createTerm(this.headers, taxonomyId, body);
    }

    /**
     * <p>
     * Supported Query Parameters: to use query parameters use #addParams("key", "value");
     * <ul>
     * <li>
     * <b>taxonomy_uid</b> - The taxonomy for which we need the terms
     * </li>
     * <li>
     * <b>depth</b> - Include the terms upto the depth specified if set to a number greater than 0, include all the terms if set to 0, default depth will be set to 1
     * </li>
     * <li>
     * <b>include_children_count</b> - Include count of number of children under each term if set to true
     * </li>
     * <li>
     * <b>include_referenced_entries_count</b> - Include count of the entries where this term is referred
     * </li>
     * <li>
     * <b>include_count</b> - Include count of the documents/nodes that matched the query
     * </li>
     * <li>
     * <b>asc|desc</b> - Sort the given field in either ascending or descending order
     * </li>
     * <li>
     * <b>typeahead</b> - Used to match the given string in all terms and return the matched result
     * </li>
     * <li>
     * <b>deleted</b> - Used to fetch only the deleted terms
     * </li>
     * <li>
     * <b>skip</b> - Skip the number of documents/nodes
     * </li>
     * <li>
     * <b>limit</b> - Limit the result to number of documents/nodes
     * </li>
     * </ul>
     * <br>
     * <b>Example</b>
     * <pre>
     *     {@code
     *     Stack stack = new Contentstack.Builder().build().stack(headers);
     *     Term term = stack.taxonomy("taxonomyId").terms().addParam("limit", 2).find();
     *     }*
     * </pre>
     *
     * @return the call
     * @see #addParam(String, Object) #addParam(String, Object)to add query parameters returns instance of Call
     */
    public Call<ResponseBody> find() {
        return this.taxonomyService.findTerm(this.headers, taxonomyId, this.params);
    }

    /**
     * Fetch single term based on term uid.
     *
     * @param termUid The term for which we need the details
     * @return instance of call <br> Supported Query Parameters: to use query parameters use #addParams("key", "value"); <ul>  <li><b>include_children_count</b> - Include count of number of children under each term <li>     <b>include_referenced_entries_count</b> - Include count of the entries where this term is referred </li> </ul> <br> <b>Example</b> <pre>     {@code
     *     Stack stack = new Contentstack.Builder().build().stack(headers);
     *     Term term = stack.taxonomy("taxonomyId").terms().find();
     *     } </pre>
     * @see #addParam(String, Object) #addParam(String, Object)#addParam(String, Object)to add query parameters
     */
    public Call<ResponseBody> fetch(@NotNull String termUid) {
        return this.taxonomyService.fetchTerm(this.headers, taxonomyId, termUid, this.params);
    }

    /**
     * Get descendants of a single Term
     *
     * @param termUid The term for which we need the details
     * @return The details of the term descendants <p> URL/Query parameters <ul>     <li>     <b>depth</b> - Include the terms upto the depth specified if set to a number greater than 0, include all the terms if set to 0, default depth will be set to 1     </li><li>     <b>include_children_count</b> - Include count of number of children under each term     </li><li>     <b>include_referenced_entries_count</b> - Include count of the entries where atleast 1 term of this taxonomy is referred     </li><li>     <b>include_count</b> - Include count of the documents/nodes that matched the query     </li><li>     <b>skip</b> - Skip the number of documents/nodes     </li><li>     <b>limit</b> - Limit the result to number of documents/nodes     </li> </ul> <p> <b>Example</b> <pre> {@code
     *      Stack stack = new Contentstack.Builder().build().stack(headers);
     *      Term term = stack.taxonomy("taxonomyId").terms().descendants("termId").;
     *     }     </pre> <br>
     */
    public Call<ResponseBody> descendants(@NotNull String termUid) {
        return this.taxonomyService.descendantsTerm(this.headers, this.taxonomyId, termUid, this.params);
    }

    /**
     * Get ancestors of a single Term
     *
     * @param termUid The term for which we need the details
     * @return The details of the term ancestors <p> <b>Example</b> <pre> {@code
     *      Stack stack = new Contentstack.Builder().build().stack(headers);
     *      Term term = stack.taxonomy("taxonomyId").terms().ancestors("termId");
     *     }     </pre>
     */
    public Call<ResponseBody> ancestors(@NotNull String termUid) {
        return this.taxonomyService.ancestorsTerm(this.headers, this.taxonomyId, termUid, this.params);
    }

    /**
     * Update call.
     *
     * @param termUid - The term for which we need the details
     * @param body    the JSONObject body for the request
     * @return instance of Call <p> <b>Example</b> <pre> {@code
     *   body = new RequestBody{
     *   "term": {
     *     "name": "Term 1",
     *     "description": "Term 1 Description updated for Taxonomy 1"
     *   }
     * }
     *
     * Stack stack = new Contentstack.Builder().build().stack(headers);
     * Term term = stack.taxonomy("taxonomyId").terms().update(body);
     * } </pre>
     */
    public Call<ResponseBody> update(@NotNull String termUid, @NotNull JSONObject body) {
        return this.taxonomyService.updateTerm(this.headers, this.taxonomyId, termUid, body);
    }


    /**
     * @param termUid The term which we need to move(change the parent)
     * @param body    the request body
     *                <code>
     *                //At Root Level:
     *                {
     *                "term": {
     *                "parent_uid": null,
     *                "order": 2
     *                }
     *                }
     *                <br>
     *                //Under an existing Term on a different level:
     *                {
     *                "term": {
     *                "parent_uid": "term_1",
     *                "order": 5
     *                }
     *                }
     *                <br>
     *                //Under an existing Term on the same level(Reorder Term):
     *                {
     *                "term": {
     *                "parent_uid": "term_3",
     *                "order": 1
     *                }
     *                }</code>
     * @return Call
     * @see  #addParam(String, Object) to provide additional params for below :
     * <p><b>force</b> - It’s set to false by default, which will give an error if we
     * try to move a term which has got child terms within it, when set to true,
     * it’ll force move the term(which will also affect the ancestors hierarchy of all it’s child terms)
     */
    public Call<ResponseBody> reorder(@NotNull String termUid, @NotNull JSONObject body) {
        return this.taxonomyService.reorder(this.headers, this.taxonomyId, termUid, this.params, body);
    }


    /**
     * Search call.
     *
     * @param termString : The string for which we need to search for the matching terms, should either match with term uid or term name
     * @return instance of Call <p> <b>Example</b> <pre> {@code
     * Stack stack = new Contentstack.Builder().build().stack(headers);
     * Term term = stack.taxonomy("taxonomyId").terms().search("search anything");
     * } </pre>
     * @throws IllegalArgumentException if the termString is empty
     */
    public Call<ResponseBody> search(@NotNull String termString) {
        if (termString.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.TERM_STRING_REQUIRED);
        }
        return this.taxonomyService.searchTerm(this.headers, termString);
    }


    /**
     * Clear params.
     */
    protected void clearParams() {
        this.params.clear();
    }
}
