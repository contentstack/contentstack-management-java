package com.contentstack.cms.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Error {

    public Error() {
    }

    public Error(Object error, String errorMessage, int errorCode) {
        this.error = error;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    @SerializedName("errors")
    @Expose
    private String id;
    Object error;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SerializedName("error_message")
    @Expose
    String errorMessage;

    @SerializedName("error_code")
    @Expose
     int errorCode;


    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
