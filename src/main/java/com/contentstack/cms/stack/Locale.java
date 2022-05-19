package com.contentstack.cms.stack;

import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.Map;

/*
 * <b>Languages</b>
 * <br>
 * Contentstack has a sophisticated multilingual capability.
 *  It allows you to create and publish entries in any language.
 * This feature allows you to set up multilingual websites
 * and cater to a wide variety of audience by serving content in their local language(s).
 *
 * @author ***REMOVED*** (***REMOVED***)
 * @version 1.0.0
 * @since 2022-05-19
 */
public class Locale {

    protected final LocaleService localeService;
    protected Map<String, Object> headers;

    /**
     * Instantiates a new Language
     *
     * @param client
     *         the retrofit client
     * @param headers
     *         the headers
     */
    public Locale(Retrofit client, Map<String, Object> headers) {
        this.headers = headers;
        this.localeService = client.create(LocaleService.class);
    }

    /**
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
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> fetch() {
        return localeService.locales(this.headers);
    }

    /**
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
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> addLocale(@NotNull JSONObject body) {
        return localeService.addLocale(this.headers, body);
    }

    /**
     * The Get a language call returns information about a specific language available on the stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the authtoken that you receive after
     * logging into your account.
     *
     * @param code
     *         the language code
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> getLocale(@NotNull String code) {
        return localeService.getLocale(this.headers, code);
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
     *         the language code
     * @param body
     *         the request body
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> updateLocale(@NotNull String code, @NotNull JSONObject body) {
        return localeService.updateLocale(this.headers, code, body);
    }

    /**
     * Delete language call deletes an existing language from your stack.
     * <p>
     * When executing the API call, under the 'Header' section, you need to enter the API key of your stack and the
     * authtoken that you receive after logging into your account.
     * <p>
     * Fallback Languages
     *
     * @param localeCode
     *         locale code you want to delete
     * @return Call
     */
    public Call<ResponseBody> deleteLocale(String localeCode) {
        return localeService.deleteLocale(this.headers, localeCode);
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
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> setFallbackLocale(@NotNull JSONObject body) {
        return localeService.setFallbackLocale(this.headers, body);
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
     * @return the retrofit2.Call
     */
    public Call<ResponseBody> updateFallbackLocale(@NotNull String localeUid, @NotNull JSONObject body) {
        return localeService.updateFallbackLocale(this.headers, localeUid, body);
    }

}
