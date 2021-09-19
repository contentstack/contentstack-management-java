package com.contentstack.cms.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginDetailTest {

    @Test
    void testLoginDetail() {
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.getNotice();
        loginDetails.getUser();
        loginDetails.getUser().getUid();
        loginDetails.getUser().getCreatedAt();
        loginDetails.getUser().getUpdatedAt();
        loginDetails.getUser().getEmail();
        loginDetails.getUser().getUsername();
        loginDetails.getUser().getFirstName();
        loginDetails.getUser().getLastName();
        loginDetails.getUser().getCompany();
        loginDetails.getUser().getOrgUid();
        loginDetails.getUser().getSharedOrgUid();
        loginDetails.getUser().isActive();
        loginDetails.getUser().getFailedAttempts();
        loginDetails.getUser().getAuthtoken();
        loginDetails.getUser().getProfileType();
        loginDetails.getUser().getRoles();
        loginDetails.getUser().getOrganizations();

        UserDetail userDetail = new UserDetail();
        userDetail.getUser().isActive();

        Assertions.assertNotNull(loginDetails);
        Assertions.assertNotNull(userDetail);

    }
}
