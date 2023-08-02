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

    // The `@SerializedName("uid")` annotation is used in the UserModel class to
    // specify the serialized
    // name of the field when converting the object to JSON or parsing JSON into the
    // object. In this case,
    // it indicates that the field `uid` should be serialized as "uid" when
    // converting the UserModel object
    // to JSON or parsing JSON into the UserModel object.
    @SerializedName("uid")
    public String uid;

    // The `@SerializedName("created_at")` annotation is used in the UserModel class
    // to specify the
    // serialized name of the field when converting the object to JSON or parsing
    // JSON into the object. In
    // this case, it indicates that the field `createdAt` should be serialized as
    // "created_at" when
    // converting the UserModel object to JSON or parsing JSON into the UserModel
    // object.
    @SerializedName("created_at")
    public String createdAt;

    // The `@SerializedName("updated_at")` annotation is used in the UserModel class
    // to specify the
    // serialized name of the field when converting the object to JSON or parsing
    // JSON into the object. In
    // this case, it indicates that the field `updatedAt` should be serialized as
    // "updated_at" when
    // converting the UserModel object to JSON or parsing JSON into the UserModel
    // object.
    @SerializedName("updated_at")
    public String updatedAt;

    // The `@SerializedName("email")` annotation is used in the UserModel class to
    // specify the serialized
    // name of the field when converting the object to JSON or parsing JSON into the
    // object. In this case,
    // it indicates that the field `email` should be serialized as "email" when
    // converting the UserModel
    // object to JSON or parsing JSON into the UserModel object.
    @SerializedName("email")
    public String email;

    // The `@SerializedName("username")` annotation is used in the UserModel class
    // to specify the
    // serialized name of the field when converting the object to JSON or parsing
    // JSON into the object. In
    // this case, it indicates that the field `username` should be serialized as
    // "username" when converting
    // the UserModel object to JSON or parsing JSON into the UserModel object.
    @SerializedName("username")
    public String username;

    // The `@SerializedName("first_name")` annotation is used in the UserModel class
    // to specify the
    // serialized name of the field when converting the object to JSON or parsing
    // JSON into the object. In
    // this case, it indicates that the field `firstName` should be serialized as
    // "first_name" when
    // converting the UserModel object to JSON or parsing JSON into the UserModel
    // object.
    @SerializedName("first_name")
    public String firstName;

    // The `@SerializedName` annotation is used in the UserModel class to specify
    // the serialized name
    // of each field when converting the object to JSON or parsing JSON into the
    // object.
    // The `@SerializedName("last_name")` annotation is used in the UserModel class
    // to specify the
    // serialized name of the field `lastName` when converting the object to JSON or
    // parsing JSON into the
    // object. In this case, it indicates that the field `lastName` should be
    // serialized as "last_name"
    // when converting the UserModel object to JSON or parsing JSON into the
    // UserModel object.
    @SerializedName("last_name")
    public String lastName;

    // The `@SerializedName("company")` annotation is used in the UserModel class to
    // specify the serialized
    // name of the field `company` when converting the object to JSON or parsing
    // JSON into the object. In
    // this case, it indicates that the field `company` should be serialized as
    // "company" when converting
    // the UserModel object to JSON or parsing JSON into the UserModel object.
    @SerializedName("company")
    public String company;

    // The `@SerializedName("org_uid")` annotation is used in the UserModel class to
    // specify the serialized
    // name of the field `orgUid` when converting the object to JSON or parsing JSON
    // into the object. In
    // this case, it indicates that the field `orgUid` should be serialized as
    // "org_uid" when converting
    // the UserModel object to JSON or parsing JSON into the UserModel object.
    @SerializedName("org_uid")
    public String[] orgUid;

    // The `@SerializedName("shared_org_uid")` annotation is used in the UserModel
    // class to specify the
    // serialized name of the field `sharedOrgUid` when converting the object to
    // JSON or parsing JSON into
    // the object. In this case, it indicates that the field `sharedOrgUid` should
    // be serialized as
    // "shared_org_uid" when converting the UserModel object to JSON or parsing JSON
    // into the UserModel
    // object. This annotation helps in maintaining consistency between the field
    // name in the Java class
    // and the serialized name in the JSON representation of the object.
    @SerializedName("shared_org_uid")
    public String[] sharedOrgUid;

    // The `@SerializedName("active")` annotation is used in the UserModel class to
    // specify the serialized
    // name of the field `active` when converting the object to JSON or parsing JSON
    // into the object. In
    // this case, it indicates that the field `active` should be serialized as
    // "active" when converting the
    // UserModel object to JSON or parsing JSON into the UserModel object.
    @SerializedName("active")
    public boolean active;

    // The `@SerializedName("failed_attempts")` annotation is used in the UserModel
    // class to specify the
    // serialized name of the field `failedAttempts` when converting the object to
    // JSON or parsing JSON
    // into the object. In this case, it indicates that the field `failedAttempts`
    // should be serialized as
    // "failed_attempts" when converting the UserModel object to JSON or parsing
    // JSON into the UserModel
    // object. This annotation helps in maintaining consistency between the field
    // name in the Java class
    // and the serialized name in the JSON representation of the object.
    @SerializedName("failed_attempts")
    public int failedAttempts;

    // The `@SerializedName("authtoken")` annotation is used in the UserModel class
    // to specify the
    // serialized name of the field `authtoken` when converting the object to JSON
    // or parsing JSON into the
    // object. In this case, it indicates that the field `authtoken` should be
    // serialized as "authtoken"
    // when converting the UserModel object to JSON or parsing JSON into the
    // UserModel object. This
    // annotation helps in maintaining consistency between the field name in the
    // Java class and the
    // serialized name in the JSON representation of the object.
    @SerializedName("authtoken")
    public String authtoken;

    // The `@SerializedName("profile_type")` annotation is used in the UserModel
    // class to specify the
    // serialized name of the field `profileType` when converting the object to JSON
    // or parsing JSON into
    // the object. In this case, it indicates that the field `profileType` should be
    // serialized as
    // "profile_type" when converting the UserModel object to JSON or parsing JSON
    // into the UserModel
    // object. This annotation helps in maintaining consistency between the field
    // name in the Java class
    // and the serialized name in the JSON representation of the object.
    @SerializedName("profile_type")
    public String profileType;

    // The `@SerializedName("roles")` annotation is used in the UserModel class to
    // specify the serialized
    // name of the field `roles` when converting the object to JSON or parsing JSON
    // into the object. In
    // this case, it indicates that the field `roles` should be serialized as
    // "roles" when converting the
    // UserModel object to JSON or parsing JSON into the UserModel object.
    @SerializedName("roles")
    public List<Object> roles;

    // The `@SerializedName("organizations")` annotation is used in the UserModel
    // class to specify the
    // serialized name of the field `organizations` when converting the object to
    // JSON or parsing JSON into
    // the object. In this case, it indicates that the field `organizations` should
    // be serialized as
    // "organizations" when converting the UserModel object to JSON or parsing JSON
    // into the UserModel
    // object. This annotation helps in maintaining consistency between the field
    // name in the Java class
    // and the serialized name in the JSON representation of the object.
    @SerializedName("organizations")
    public List<Object> organizations;

}
