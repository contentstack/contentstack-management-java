package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Languages</b>
 * <br>
 * Contentstack has a sophisticated multilingual capability. It allows you to create and publish entries in any
 * language. This feature allows you to set up multilingual websites and cater to a wide variety of audience by serving
 * content in their local language(s).
 *
 * @author ***REMOVED***
 * @version 1.0.0
 * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#languages">  Read more about
 * Languages
 * </a>
 * @since 2022-05-19
 */
public class Locale {

    protected final LocaleService localeService;
    protected Map<String, Object> headers;
    protected Map<String, Object> params;


    /**
     * Instantiates a new Language
     *
     * @param client
     *         the retrofit client
     * @param headers
     *         the headers
     */
    public Locale(Retrofit client, Map<String, Object> headers) {
        this.headers = new HashMap<>();
        this.headers.putAll(headers);
        this.params = new HashMap<>();
        this.localeService = client.create(LocaleService.class);
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
     * <b>Get all languages</b>
     * <br>
     * This call fetches the list of all languages (along with the language codes) available for a stack.
     * <p>
     * When executing the API call, under the <b>Header</b> section, you need to enter the authtoken that you receive
     * after logging into your account.
     * <p>
     * You can add queries to extend the functionality of this API call. Under the URI Parameters section, insert a
     * parameter named query and provide a query in JSON format as the value.
     * <p>
     * To learn more about the queries, refer to the <b>Queries</b> section of the Content Delivery API doc.
     *
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-languages">Get
     * all languages
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> find() {
        return localeService.locales(this.headers, this.params);
    }


    /**
     * The Get a language call returns information about a specific language available on the stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the authtoken that you receive after
     * logging into your account.
     *
     * @param code
     *         The code of the language that you want to retrieve
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-languages">Get
     * all languages
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> fetch(@NotNull String code) {
        return localeService.singel(this.headers, code, this.params);
    }


    /**
     * <b>Add a language</b>
     * <br>
     * This call lets you add a new language to your stack. You can either add a supported language or a custom language
     * of your choice.
     * <p>
     * When executing the API call, under the <b>Header</b> section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * In the 'Body' section, enter the language name and code in JSON format. You can also specify the fallback
     * language you want to assign to the new language within the same JSON.
     *
     * @param body
     *         the request body
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#get-all-languages">Get
     * all languages
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> create(@NotNull JSONObject body) {
        return localeService.create(this.headers, body);
    }


    /**
     * The Update language call will let you update the details (such as display name) and the fallback language of an
     * existing language of your stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the authtoken that you receive after
     * logging into your account.
     * <p>
     * In the 'Body' section, enter the updated details of your language name and fallback language in JSON format.
     *
     * @param code
     *         The code of the language that you want to retrieve
     * @param body
     *         the request body
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-language">Update
     * language
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> update(@NotNull String code, @NotNull JSONObject body) {
        return localeService.update(this.headers, code, this.params, body);
    }

    /**
     * Delete language call deletes an existing language from your stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * Fallback Languages
     *
     * @param code
     *         The code of the language that you want to retrieve
     * @return Call
     * @see <a href="https://www.contentstack.com/docs/developers/apis/content-management-api/#delete-language">Delete
     * language
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> delete(String code) {
        return localeService.delete(this.headers, code);
    }

    /**
     * The <b>Set a fallback language</b> request allows you to assign a fallback language for an entry in a particular
     * language.
     * <p>
     * When executing the API call, under the <b>Header</b> section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging in to your account.
     * <p>
     * In the <b>Body</b> section, enter the language codes in JSON format
     *
     * @param body
     *         the request body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#set-a-fallback-language">Set a
     * fallback language
     *
     *
     * </a>
     * @see #addHeader(String, Object) to add headers
     * @see #addParam(String, Object) to add query parameters
     * @since 1.0.0
     */
    public Call<ResponseBody> setFallback(@NotNull JSONObject body) {
        return localeService.setFallback(this.headers, body);
    }

    /**
     * The <b>Update fallback language</b> request allows you to update the fallback language for an existing language
     * of your stack.
     * <p>
     * When executing the API call, under the <b>Header</b> section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging in to your account.
     * <p>
     * In the <b>Body</b> section, enter the updated details of the fallback language in JSON format
     *
     * @param localeUid
     *         the locale uid
     * @param body
     *         the request body
     * @return Call
     * @see <a
     * href="https://www.contentstack.com/docs/developers/apis/content-management-api/#update-fallback-language">Update
     * fallback language</a>
     * @see #addHeader(String, Object) to add headers
     * @since 1.0.0
     */
    public Call<ResponseBody> updateFallback(@NotNull String localeUid, @NotNull JSONObject body) {
        return localeService.updateFallback(this.headers, localeUid, body);
    }

}
