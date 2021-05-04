package com.contentstack.cms.user;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("Mock")
@DisplayName("User class Mock test case")
public class UserMockTests {

    private static final Logger logger = Logger.getLogger(UserMockTests.class.getName());
    private JSONObject mockJsonObject;

    public JSONObject readJson(String file) {
        String path = "src/test/resources/mockuser/" + file;
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader(new File(path).getPath()));
            mockJsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        logger.info(mockJsonObject.toJSONString());
        return mockJsonObject;
    }

    @BeforeAll
    public void setupBeforeAll() {
        logger.setLevel(Level.FINE);
    }


    @Test
    @DisplayName("Mock testcase for get user to check with all available keys")
    @Order(1)
    @TestFactory
    public void mock_test_get_user() {
        JSONObject getUserJSONObj = readJson("getuser.json");
        mockJsonObject = (JSONObject) getUserJSONObj.get("user");
        Set allKeys = mockJsonObject.keySet();
        String authtoken = (String) mockJsonObject.get("authtoken");

        String[] keyArray = {"org_uid",
                "authy_id",
                "failed_attempts",
                "shared_org_uid",
                "roles",
                "authtoken",
                "created_at",
                "last_name",
                "active",
                "uid",
                "country_code",
                "updated_at",
                "tfa_status",
                "company",
                "mobile_number",
                "first_name",
                "email",
                "username"};
        // TODO: Remove the line
        allKeys.forEach(System.out::println);
        Assertions.assertEquals("bltd111c111111c11ec", authtoken);
        Assertions.assertArrayEquals(Arrays.stream(keyArray).toArray(), allKeys.toArray());
    }

    @Test
    @DisplayName("Mock testcase for update user")
    @Order(2)
    void test_mock_testcase_update_user() {
        mockJsonObject = readJson("updateuser.json");
        Assertions.assertEquals("Profile updated successfully.", mockJsonObject.get("notice"));
        mockJsonObject = (JSONObject) mockJsonObject.get("user");
        Set allKeys = mockJsonObject.keySet();
        allKeys.forEach(System.out::println);
        String[] keyArray = {
                "org_uid",
                "authy_id",
                "failed_attempts",
                "shared_org_uid",
                "created_at",
                "last_name",
                "active",
                "uid",
                "country_code",
                "updated_at",
                "tfa_status",
                "company",
                "mobile_number",
                "first_name",
                "email",
                "username"
        };
        Assertions.assertArrayEquals(Arrays.stream(keyArray).toArray(), allKeys.toArray());
    }

    @Test
    @DisplayName("Mock test notice message for activate user")
    @Order(3)
    void test_mock_testcase_activate_user() {
        mockJsonObject = readJson("activateuser.json");
        Assertions.assertEquals("Your account has been activated.", mockJsonObject.get("notice"));
    }

    @Test
    @DisplayName("Mock testcase for request password")
    @Order(4)
    void test_mock_testcase_request_password() {
        mockJsonObject = readJson("request_password.json");
        Assertions.assertEquals("We sent an email to john.doe@contentstack.com with instructions to reset your password.", mockJsonObject.get("notice"));
    }

    @Test
    @DisplayName("Mock testcase for reset password")
    @Order(5)
    void test_mock_testcase_reset_password() {
        mockJsonObject = readJson("reset_password.json");
        Assertions.assertEquals("Your password has been reset successfully.", mockJsonObject.get("notice"));
    }


    @Test
    @DisplayName("Mock testcase for user logout")
    @Order(6)
    void test_mock_testcase_logout() {
        mockJsonObject = readJson("logout.json");
        Assertions.assertEquals("You've logged out successfully!", mockJsonObject.get("notice"));
    }

    @Test
    @DisplayName("mock testcase for get user organisation")
    @Order(8)
    void test_mock_testcase_get_user_organisation() {

    }

}