package com.contentstack.cms.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter

public class LoginDetails {

    @SerializedName("notice")
    public String notice;

    @SerializedName("user")
    public UserModel user;

}
