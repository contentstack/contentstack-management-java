package com.contentstack.cms.core;
import com.google.gson.annotations.SerializedName;


public class Error {

    public Error() { }

    public Error(Object error, String errorMessage, int errorCode) {
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    @SerializedName("errors")
    Object error;

    @SerializedName("error_message")
    String errorMessage;

    @SerializedName("error_code")
     int errorCode;

    public Object getError() {
        return error;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void setError(Object error) {
        this.error = error;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
