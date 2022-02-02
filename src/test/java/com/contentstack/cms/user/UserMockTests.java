package com.contentstack.cms.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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

/**
 * The type User mock tests.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("Mock")
@DisplayName("User class Mock test case")
class UserMockTests {

    private static final Logger logger = Logger.getLogger(UserMockTests.class.getName());
    private JSONObject mockJsonObject;

    /**
     * Read json json object.
     *
     * @param file the file
     * @return the json object
     */
    public JSONObject readJson(String file) {
        String path = "src/test/resources/mockuser/" + file;
        try {
            Object obj = new JSONParser().parse(new FileReader(new File(path).getPath()));
            mockJsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        logger.fine(mockJsonObject.toJSONString());
        return mockJsonObject;
    }

    /**
     * Sets before all.
     */
    @BeforeAll
    public void setupBeforeAll() {
        logger.setLevel(Level.ALL);
    }

    /**
     * Mock test get user.
     */
    @Test
    @DisplayName("Mock testcase for get user to check with all available keys")
    @Order(1)
    @TestFactory
    void mock_test_get_user() {
        JSONObject getUserJSONObj = readJson("getuser.json");
        mockJsonObject = (JSONObject) getUserJSONObj.get("user");
        Set allKeys = mockJsonObject.keySet();
        String authtoken = (String) mockJsonObject.get("authtoken");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(mockJsonObject.toJSONString());
        String prettyJsonString = gson.toJson(je);
        logger.finer(prettyJsonString);

        String[] keyArray = { "org_uid",
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
                "username" };

        Assertions.assertEquals("the_fake_uid", authtoken);
        Assertions.assertArrayEquals(Arrays.stream(keyArray).toArray(), allKeys.toArray());
    }

    /**
     * Test mock testcase update user.
     */
    @Test
    @DisplayName("Mock testcase for update user")
    @Order(2)
    void test_mock_testcase_update_user() {
        mockJsonObject = readJson("updateuser.json");
        Assertions.assertEquals("Profile updated successfully.", mockJsonObject.get("notice"));
        mockJsonObject = (JSONObject) mockJsonObject.get("user");
        Set allKeys = mockJsonObject.keySet();
        logger.finest(mockJsonObject.toJSONString());
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

    /**
     * Test mock testcase activate user.
     */
    @Test
    @DisplayName("Mock test notice message for activate user")
    @Order(3)
    void test_mock_testcase_activate_user() {
        mockJsonObject = readJson("activateuser.json");
        Assertions.assertEquals("Your account has been activated.", mockJsonObject.get("notice"));
    }

    /**
     * Test mock testcase request password.
     */
    @Test
    @DisplayName("Mock testcase for request password")
    @Order(4)
    void test_mock_testcase_request_password() {
        mockJsonObject = readJson("request_password.json");
        Assertions.assertEquals(
                "We sent an email to john.doe@contentstack.com with instructions to reset your password.",
                mockJsonObject.get("notice"));
    }

    /**
     * Test mock testcase reset password.
     */
    @Test
    @DisplayName("Mock testcase for reset password")
    @Order(5)
    void test_mock_testcase_reset_password() {
        mockJsonObject = readJson("reset_password.json");
        Assertions.assertEquals("Your password has been reset successfully.", mockJsonObject.get("notice"));
    }

    /**
     * Test mock testcase logout.
     */
    @Test
    @DisplayName("Mock testcase for user logout")
    @Order(6)
    void test_mock_testcase_logout() {
        mockJsonObject = readJson("logout.json");
        Assertions.assertEquals("You've logged out successfully!", mockJsonObject.get("notice"));
    }

    /**
     * Test mock testcase get user organisation.
     */
    @Test
    @DisplayName("mock testcase for get user organisation")
    @Order(8)
    void test_mock_testcase_get_user_organisation() {
        Assertions.assertTrue(true);
    }

}
