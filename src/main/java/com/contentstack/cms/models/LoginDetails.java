package com.contentstack.cms.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginDetails {

    // The `@SerializedName("notice")` annotation is used in the Gson library for
    // Java. It is used to map
    // the JSON key "notice" to the corresponding field in the Java class. In this
    // case, it is mapping the
    // "notice" key in the JSON response to the `notice` field of type `String` in
    // the `LoginDetails`
    // class. This allows Gson to automatically deserialize the JSON response and
    // populate the `notice`
    // field with the value from the "notice" key in the JSON.
    @SerializedName("notice")
    public String notice;

    // The `@SerializedName("user")` annotation is used in the Gson library for
    // Java. It is used to map
    // the JSON key "user" to the corresponding field in the Java class. In this
    // case, it is mapping the
    // "user" key in the JSON response to the `user` field of type `UserModel` in
    // the `LoginDetails`
    // class.
    @SerializedName("user")
    public UserModel user;

}
