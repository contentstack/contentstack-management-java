package com.contentstack.cms.user;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class LoginDetails {

    @SerializedName("notice")
    public String notice;

    @SerializedName("user")
    public UserModel user;

}
