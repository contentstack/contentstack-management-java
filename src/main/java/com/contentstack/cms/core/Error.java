package com.contentstack.cms.core;

import com.google.gson.annotations.SerializedName;


public class Error {

    @SerializedName("errors")
    Object error;
    @SerializedName("error_message")
    String errorMessage;
    @SerializedName("error_code")
    int errorCode;

    public Error() {
    }

    public Error(Object error, String errorMessage, int errorCode) {
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
