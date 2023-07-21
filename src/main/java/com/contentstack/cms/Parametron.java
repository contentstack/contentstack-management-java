package com.contentstack.cms;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

/**
 * The interface Parametron.
 */
public interface Parametron {

    /**
     * The function adds a parameter to a collection using a key-value pair.
     * 
     * @param <T>   the type of the parameter
     * @param key   A string representing the key of the parameter. It cannot be
     *              null and must be
     *              provided as a non-null value.
     * @param value The "value" parameter is of type Object, which means it can
     *              accept any type of
     *              object as its value.
     * @return The method is returning an object of type T.
     */
    public <T> T addParam(@NotNull String key, @NotNull Object value);

    /**
     * The function adds a header with a key-value pair to a request.
     * 
     * @param <T>   the type of the parameter
     * @param key   The key parameter is a string that represents the name or
     *              identifier of the header.
     *              It is used to specify the type of information being sent in the
     *              header.
     * @param value The value parameter is a string that represents the value of the
     *              header.
     * @return The method is returning an object of type T.
     */
    public <T> T addHeader(@NotNull String key, @NotNull String value);

    /**
     * The function "addParams" takes a HashMap of String keys and Object values as
     * input and returns a
     * generic type T.
     * 
     * @param <T>    the type of the parameter
     * @param params The "params" parameter is a HashMap that maps String keys to
     *               Object values. It is
     *               annotated with @NotNull, indicating that it cannot be null.
     * @return The method is returning an object of type T.
     */
    public <T> T addParams(@NotNull HashMap<String, Object> params);

    /**
     * The function adds headers to a HashMap.
     * 
     * @param <T>     the type of the parameter
     * @param headers A HashMap containing key-value pairs of headers, where the key
     *                is a String
     *                representing the header name and the value is a String
     *                representing the header value.
     * @return The method is returning an object of type T.
     */
    public <T> T addHeaders(@NotNull HashMap<String, String> headers);
}
