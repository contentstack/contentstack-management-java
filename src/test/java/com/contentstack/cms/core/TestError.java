package com.contentstack.cms.core;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The type Test error.
 */
public class TestError {

    /**
     * Test gson mapper not null.
     */
    @Test
    @DisplayName("Text Json mapper is not null")
    public void testGSONMapperNotNull() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertNotNull(error);
    }

    /**
     * Test gson errors.
     */
    @Test
    public void testGsonErrors() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("{environment=[is required.]}", error.getError().toString());
    }

    /**
     * Test gson error code.
     */
    @Test
    public void testGsonErrorCode() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals(141, error.getErrorCode());
    }


    /**
     * Test gson error message.
     */
    @Test
    public void testGsonErrorMessage() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    /**
     * Test set gson error message.
     */
    @Test
    public void testSetGsonErrorMessage() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    /**
     * Test set gson error code.
     */
    @Test
    public void testSetGsonErrorCode() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    /**
     * Test set gson errors.
     */
    @Test
    public void testSetGsonErrors() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    /**
     * Test error all args constructor.
     */
    @Test
    public void testErrorAllArgsConstructor() {
        Error error = new Error("{\"environment\":[\"is required.\"]}}", "Failed to fetch entries. Please try again with valid parameters.", 141);
        Assertions.assertNotNull(error.getError());
    }

    /**
     * Test error builder.
     */
    @Test
    public void testErrorBuilder() {
        Error error = new Error();
        error.setError("some dummy error");
        error.setErrorCode(400);
        error.setErrorMessage("this is dummy message");
        String jsonInString = new Gson().toJson(error);
        Assertions.assertNotNull(jsonInString);
    }
}
