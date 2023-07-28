package com.contentstack.cms.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDetail {

    // The `@SerializedName("user")` annotation is used in the Gson library for Java
    // to map a JSON key to a
    // Java field during serialization and deserialization. In this case, it is
    // specifying that the JSON
    // key "user" should be mapped to the `user` field of type `UserModel` in the
    // `UserDetail` class.
    @SerializedName("user")
    public UserModel user;

}
