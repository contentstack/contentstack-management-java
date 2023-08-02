package com.contentstack.cms.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Error {

    // The `@SerializedName("errors")` annotation is used in the Gson library to map
    // the JSON key "errors"
    // to the `errors` field in the `Error` class. This allows Gson to automatically
    // deserialize the JSON
    // response into an instance of the `Error` class, where the `errors` field will
    // be populated with the
    // corresponding value from the JSON. The `errors` field is of type `Object`,
    // which means it can hold
    // any type of value.
    @SerializedName("errors")
    public Object errors;

    // The `@SerializedName("error_message")` annotation is used in the Gson library
    // to map the JSON
    // key "error_message" to the `errorMessage` field in the `Error` class. This
    // allows Gson to
    // automatically deserialize the JSON response into an instance of the `Error`
    // class, where the
    // `errorMessage` field will be populated with the corresponding value from the
    // JSON.
    @SerializedName("error_message")
    public String errorMessage;
    // The `@SerializedName("error_code")` annotation is used in the Gson library to
    // map the JSON key
    // "error_code" to the `errorCode` field in the `Error` class. This allows Gson
    // to automatically
    // deserialize the JSON response into an instance of the `Error` class, where
    // the `errorCode` field
    // will be populated with the corresponding value from the JSON.

    @SerializedName("error_code")
    public int errorCode;

}
