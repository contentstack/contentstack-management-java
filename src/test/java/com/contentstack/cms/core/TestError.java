package com.contentstack.cms.core;

import com.contentstack.cms.models.Error;
import com.contentstack.cms.models.UserDetail;
import com.contentstack.cms.models.UserModel;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The type Test error.
 */
@Tag("unit")
class TestError {

    static CMALogger logger = new CMALogger(TestError.class);

    /**
     * Test gson mapper not null.
     */
    @Test
    @DisplayName("Text Json mapper is not null")
    void testGSONMapperNotNull() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        logger.fine(error.errorMessage);
        Assertions.assertNotNull(error);
    }

    /**
     * Test gson errors.
     */
    @Test
    void testGsonErrors() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("{environment=[is required.]}", error.errors.toString());
    }

    /**
     * Test gson error code.
     */
    @Test
    void testGsonErrorCode() {

        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals(141, error.errorCode);
    }

    /**
     * Test gson error message.
     */
    @Test
    void testGsonErrorMessage() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.errorMessage);
    }

//
//    @Test
//    void testSetGsonErrorMessage() {
//        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
//        Error error = new Gson().fromJson(jsonInput, Error.class);
//        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.errorMessage);
//    }
//
//    /**
//     * Test set gson error code.
//     */
//    @Test
//    void testSetGsonErrorCode() {
//        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
//        Error error = new Gson().fromJson(jsonInput, Error.class);
//        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.errorMessage);
//    }
//
//    /**
//     * Test set gson errors.
//     */
//    @Test
//    void testSetGsonErrors() {
//        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
//        Error error = new Gson().fromJson(jsonInput, Error.class);
//        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.errorMessage);
//    }

    /**
     * Test error all args constructor.
     */
    @Test
    void testErrorAllArgsConstructor() {
        Error error = new Error();
        error.setErrorMessage("{\"environment\":[\"is required.\"]}}");
        error.setErrorCode(141);
        error.setErrors("Failed to fetch entries. Please try again with valid parameters.");
        Assertions.assertNotNull(error.getErrors());
    }

    /**
     * Test error builder.
     */
    @Test
    void testErrorBuilder() {
        Error error = new Error();
        error.setErrors("some dummy error");
        error.setErrorCode(400);
        error.setErrorMessage("this is dummy message");
        String jsonInString = new Gson().toJson(error);
        Assertions.assertNotNull(jsonInString);
    }


    @Test
    public void testErrorsMapping() {
        // Given
        String json = "{\"errors\": {\"message\": \"Invalid input.\"}, \"error_code\": 400}";
        Gson gson = new Gson();

        // When
        Error error = gson.fromJson(json, Error.class);

        // Then
        Assertions.assertNotNull(error);
        Assertions.assertNotNull(error.getErrors());
        Assertions.assertEquals("{message=Invalid input.}", error.getErrors().toString());
    }

    @Test
    public void testErrorCodeMapping() {
        // Given
        String json = "{\"errors\": null, \"error_code\": 500}";
        Gson gson = new Gson();

        // When
        Error error = gson.fromJson(json, Error.class);

        // Then
        Assertions.assertNotNull(error);
        Assertions.assertEquals(500, error.getErrorCode());
    }


    @Test
    public void testUserMapping() {
        // Given
        String json = "{\"user\": {\"id\": 123, \"name\": \"John Doe\"}}";
        String jsonNew = "{\n" +
                "  \"uid\": \"123456\",\n" +
                "  \"created_at\": \"2023-07-31\",\n" +
                "  \"updated_at\": \"2023-08-01\",\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"username\": \"johndoe\",\n" +
                "  \"first_name\": \"John\",\n" +
                "  \"last_name\": \"Doe\",\n" +
                "  \"company\": \"Example Corp\",\n" +
                "  \"org_uid\": [\"org1\", \"org2\"],\n" +
                "  \"shared_org_uid\": [\"org3\"],\n" +
                "  \"active\": true,\n" +
                "  \"failed_attempts\": 0,\n" +
                "  \"authtoken\": \"abc123\",\n" +
                "  \"profile_type\": \"user\",\n" +
                "  \"roles\": null, // Add roles data as needed\n" +
                "  \"organizations\": null // Add organizations data as needed\n" +
                "}\n";
        Gson gson = new Gson();

        // When
        UserDetail userDetail = gson.fromJson(json, UserDetail.class);
        UserModel userDetailNew = gson.fromJson(jsonNew, UserModel.class);

        // Then
        Assertions.assertNotNull(userDetail);
        Assertions.assertNotNull(userDetail.getUser());
        Assertions.assertEquals("123456", userDetailNew.getUid());
        Assertions.assertEquals("John", userDetailNew.getFirstName());
    }
}
