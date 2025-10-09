package com.contentstack.cms.models;

import com.contentstack.cms.core.AuthInterceptor;
import okhttp3.Interceptor;
import okhttp3.internal.http.RealInterceptorChain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag("unit")
public class LoginDetailTest {

    private static LoginDetails loginDetails;

    @BeforeAll
    public static void testGetterSetterModel() {
        loginDetails = new LoginDetails();
    }

    @Test
    void testSetterGetterNotice() {
        loginDetails.setNotice("this is notice");
        Assertions.assertEquals("this is notice", loginDetails.getNotice());
    }

    @Test
    void getterSetterUserModelAuthtoken() {
        UserModel userModel = new UserModel();
        userModel.setAuthtoken("fake@authtoken");
        Assertions.assertEquals("fake@authtoken", userModel.getAuthtoken());
    }

    @Test
    void getterSetterUserModelActive() {
        UserModel userModel = new UserModel();
        userModel.setActive(true);
        Assertions.assertTrue(userModel.isActive());
    }

    @Test
    void getterSetterUserModelCompany() {
        UserModel userModel = new UserModel();
        userModel.setCompany("company@company.com");
        Assertions.assertEquals("company@company.com",
                userModel.getCompany());
    }

    @Test
    void getterSetterUserModelEmail() {
        UserModel userModel = new UserModel();
        userModel.setEmail("company@company.com");
        Assertions.assertEquals("company@company.com",
                userModel.getEmail());
    }

    @Test
    void getterSetterUserModelCreatedAt() {
        UserModel userModel = new UserModel();
        userModel.setCreatedAt("2016-06-30T05:02:27.516Z");
        Assertions.assertEquals("2016-06-30T05:02:27.516Z",
                userModel.getCreatedAt());
    }

    @Test
    void getterSetterUserModelUpdatedAt() {
        UserModel userModel = new UserModel();
        userModel.setUpdatedAt("2016-06-30T05:02:27.516Z");
        Assertions.assertEquals("2016-06-30T05:02:27.516Z",
                userModel.getUpdatedAt());
    }

    @Test
    void getterSetterUserModelFailedAttempts() {
        UserModel userModel = new UserModel();
        userModel.setFailedAttempts(2);
        Assertions.assertEquals(2,
                userModel.getFailedAttempts());
    }

    @Test
    void getterSetterUserModelFirstName() {
        UserModel userModel = new UserModel();
        userModel.setFirstName("***REMOVED***");
        Assertions.assertEquals("***REMOVED***",
                userModel.getFirstName());
    }

    @Test
    void getterSetterUserModelLastName() {
        UserModel userModel = new UserModel();
        userModel.setLastName("***REMOVED***");
        Assertions.assertEquals("***REMOVED***",
                userModel.getLastName());
    }

    @Test
    void getterSetterUserModelUsername() {
        UserModel userModel = new UserModel();
        // deepcode ignore NoHardcodedCredentials/test: <please specify a reason of ignoring this>
        userModel.setUsername("***REMOVED***");
        Assertions.assertEquals("***REMOVED***",
                userModel.getUsername());
    }

    @Test
    void getterSetterUserModelUid() {
        UserModel userModel = new UserModel();
        userModel.setUid("fakeuidavailable");
        Assertions.assertEquals("fakeuidavailable",
                userModel.getUid());
    }

    @Test
    void getterSetterUserModelOrganizations() {
        UserModel userModel = new UserModel();
        List<Object> list = new ArrayList<>();
        list.add("abc");
        list.add("def");
        list.add("ghi");
        list.add("jkl");
        list.add("mno");
        userModel.setOrganizations(list);
        Assertions.assertEquals("abc",
                userModel.getOrganizations().get(0));
    }

    @Test
    void getterSetterUserModelOrgUid() {
        UserModel userModel = new UserModel();
        String[] strings = {"uidramesh", "uidforjohn", "uidforshailesh", "uidforuttam"};
        userModel.setOrgUid(strings);
        Assertions.assertEquals("uidramesh",
                Arrays.stream(userModel.getOrgUid()).findFirst().get());
    }

    @Test
    void getterSetterUserModelSharedOrgUid() {
        UserModel userModel = new UserModel();
        String[] strings = {"shareduidramesh", "shareduidforjohn", "shareduidforshailesh", "shareduidforuttam"};
        userModel.setSharedOrgUid(strings);
        Assertions.assertEquals("shareduidramesh",
                Arrays.stream(userModel.getSharedOrgUid()).findFirst().get());
    }

    @Test
    void getterSetterUserModelProfileType() {
        UserModel userModel = new UserModel();
        userModel.setProfileType("profileTypeRamesh");
        Assertions.assertEquals("profileTypeRamesh",
                userModel.getProfileType());
    }

    @Test
    void getterSetterUserRoles() throws IOException {
        UserModel userModel = new UserModel();
        List<Object> list = new ArrayList<>();
        list.add("roleAbc");
        list.add("roleDef");
        list.add("roleGhi");
        list.add("roleJkl");
        list.add("roleMno");
        userModel.setRoles(list);
        Assertions.assertEquals("roleAbc",
                userModel.getRoles().get(0).toString());
    }

}
