package com.contentstack.cms.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @JsonProperty("user")
    @NonNull
    Object user;

}
