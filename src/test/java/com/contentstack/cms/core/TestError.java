package com.contentstack.cms.core;

import com.contentstack.cms.models.Error;
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
}
