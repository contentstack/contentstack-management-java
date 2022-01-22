package com.contentstack.cms.core;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Error {

    @SerializedName("errors")
    public Object errors;

    @SerializedName("error_message")
    public String errorMessage;

    @SerializedName("error_code")
    public int errorCode;

}
