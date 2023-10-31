package com.contentstack.cms.core;

/**
 * CMARuntimeException that extends Exception class
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-20
 */
public class CMARuntimeException extends Exception {

    /**
     * The code `public CMARuntimeException(String message) { super(message); }` is
     * defining a constructor
     * for the `CMARuntimeException` class.
     *
     * @param message the message for exception
     */
    public CMARuntimeException(String message) {
        super(message);
    }
}
