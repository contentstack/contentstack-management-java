package com.contentstack.cms.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserModel {

    @SerializedName("uid")
    public String uid;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("email")
    public String email;

    @SerializedName("username")
    public String username;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("company")
    public String company;

    @SerializedName("org_uid")
    public String[] orgUid;

    @SerializedName("shared_org_uid")
    public String[] sharedOrgUid;

    @SerializedName("active")
    public boolean active;

    @SerializedName("failed_attempts")
    public int failedAttempts;

    @SerializedName("authtoken")
    public String authtoken;

    @SerializedName("profile_type")
    public String profileType;

    @SerializedName("roles")
    public List<Object> roles;

    @SerializedName("organizations")
    public List<Object> organizations;

}
