package com.contentstack.cms.core;

import com.google.gson.annotations.SerializedName;


/**
 * The type Error.
 */
public class Error {

    /**
     * The Error.
     */
    @SerializedName("errors")
    Object error;
    /**
     * The Error message.
     */
    @SerializedName("error_message")
    String errorMessage;
    /**
     * The Error code.
     */
    @SerializedName("error_code")
    int errorCode;

    /**
     * Instantiates a new Error.
     */
    public Error() {
    }

    /**
     * Instantiates a new Error.
     *
     * @param error        the error
     * @param errorMessage the error message
     * @param errorCode    the error code
     */
    public Error(Object error, String errorMessage, int errorCode) {
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public Object getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(Object error) {
        this.error = error;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode the error code
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
