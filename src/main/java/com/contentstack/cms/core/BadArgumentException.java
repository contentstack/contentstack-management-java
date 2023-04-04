package com.contentstack.cms.core;

/**
 * The BadArgumentException is an unchecked exception that is thrown to indicate an illegal or unsuitable argument
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

    public BadArgumentException(String message) {
        super(message);
    }
}
