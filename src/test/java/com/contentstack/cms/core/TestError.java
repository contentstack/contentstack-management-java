package com.contentstack.cms.core;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestError {

    @Test
    @DisplayName("Text Json mapper is not null")
    public void testGSONMapperNotNull() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertNotNull(error);
    }

    @Test
    public void testGsonErrors() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("{environment=[is required.]}", error.getError().toString());
    }

    @Test
    public void testGsonErrorCode() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals(141, error.getErrorCode());
    }


    @Test
    public void testGsonErrorMessage() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    @Test
    public void testSetGsonErrorMessage() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    @Test
    public void testSetGsonErrorCode() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    @Test
    public void testSetGsonErrors() {
        String jsonInput = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = new Gson().fromJson(jsonInput, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    @Test
    public void testErrorAllArgsContructure() {
        Error error = new Error("{\"environment\":[\"is required.\"]}}", "Failed to fetch entries. Please try again with valid parameters.", 141);
        Assertions.assertNotNull(error.getError());
    }

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
