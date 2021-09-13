package com.contentstack.cms.user;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserModel {

    /**
     * The Authtoken.
     */
    @SerializedName("authtoken")
    public String authtoken;

    /**
     * The Uid.
     */
    @SerializedName("uid")
    public String uid;

    /**
     * The Created at.
     */
    @SerializedName("created_at")
    public String createdAt;

    /**
     * The Updated at.
     */
    @SerializedName("updated_at")
    public String updatedAt;

    /**
     * The Email.
     */
    @SerializedName("email")
    public String email;

    /**
     * The Username.
     */
    @SerializedName("username")
    public String username;

    /**
     * The First name.
     */
    @SerializedName("first_name")
    public String firstName;

    /**
     * The Last name.
     */
    @SerializedName("last_name")
    public String lastName;

    /**
     * The Company.
     */
    @SerializedName("company")
    public String company;

    /**
     * The Org uid.
     */
    @SerializedName("org_uid")
    public String[] orgUid;

    /**
     * The Shared org uid.
     */
    @SerializedName("shared_org_uid")
    public String[] sharedOrgUid;

    /**
     * The Mobile number.
     */
    @SerializedName("mobile_number")
    public String mobileNumber;

    /**
     * The Country code.
     */
    @SerializedName("country_code")
    public String countryCode;

    /**
     * The Tfa status.
     */
    @SerializedName("tfa_status")
    public String tfaStatus;

    /**
     * The Authy id.
     */
    @SerializedName("authy_id")
    public String authyId;

    /**
     * The Active.
     */
    @SerializedName("active")
    public boolean active;

    /**
     * The Failed attempts.
     */
    @SerializedName("failed_attempts")
    public int failedAttempts;

    /**
     * The Profile type.
     */
    @SerializedName("profile_type")
    public String profileType;

    /**
     * The Roles.
     */
    @SerializedName("roles")
    public List<Object> roles;


}
