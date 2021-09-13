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

    /**
     * The Notice.
     */
    @SerializedName("notice")
    public String notice;

    /**
     * The User.
     */
    @SerializedName("user")
    public UserModel user;

}
