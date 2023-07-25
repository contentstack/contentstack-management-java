package com.contentstack.cms.core;

/**
 * The BadArgumentException is an unchecked exception that is thrown to indicate
 * an illegal or unsuitable argument
 * passed to a method.
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-20
 */
public class BadArgumentException extends IllegalArgumentException {
    public BadArgumentException() {
        super();
    }

    // The code `public BadArgumentException(String message) { super(message); }` is
    // a constructor for the
    // `BadArgumentException` class. It takes a `String` parameter `message` and
    // calls the constructor of
    // the superclass `IllegalArgumentException` with the `message` parameter. This
    // allows you to create an
    // instance of `BadArgumentException` with a custom error message.
    public BadArgumentException(String message) {
        super(message);
    }
}
