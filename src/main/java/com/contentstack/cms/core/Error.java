package com.contentstack.cms.core;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
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
