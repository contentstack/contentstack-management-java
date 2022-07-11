package com.contentstack.cms.core;

/**
 * The Callback for the contentstack response
 *
 * @author ishaileshmishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public interface Callback {
    void result();

    void fail();
}
