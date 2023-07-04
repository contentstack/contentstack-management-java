package com.contentstack.cms;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * The interface Parametron.
 */
public interface Parametron {


    /**
     * Add param t.
     *
     * @param <T>
     *         the type parameter
     * @param key
     *         the key
     * @param value
     *         the value
     * @return the t
     */
    public <T> T addParam(@NotNull String key, @NotNull Object value);

    /**
     * Add header t.
     *
     * @param <T>
     *         the type parameter
     * @param key
     *         the key
     * @param value
     *         the value
     * @return the t
     */
    public <T> T addHeader(@NotNull String key, @NotNull String value);


    /**
     * Add params t.
     *
     * @param <T>
     *         the type parameter
     * @param params
     *         the params
     * @return the t
     */
    public <T> T addParams(@NotNull HashMap<String, Object> params);


    /**
     * Add headers t.
     *
     * @param <T>
     *         the type parameter
     * @param headers
     *         the headers
     * @return the t
     */
    public <T> T addHeaders(@NotNull HashMap<String, String> headers);
}
