package com.contentstack.cms.core;

/**
 * CMARuntimeException that extends Exception class
 *
 * @author ishaileshmishra
 * @version v0.1.0
 * @since 2022-10-20
 */
public class CMARuntimeException extends Exception {
    public CMARuntimeException(String message) {
        super(message);
    }
}
