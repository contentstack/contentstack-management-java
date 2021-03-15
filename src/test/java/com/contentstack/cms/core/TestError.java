package com.contentstack.cms.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestError {

    @Test
    public void testErrorCode() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = objectMapper.readValue(json, Error.class);
        Assertions.assertEquals("141", error.getErrorCode());
    }

    @Test
    public void testErrorMessage() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Error error = objectMapper.readValue(json, Error.class);
        Assertions.assertEquals("Failed to fetch entries. Please try again with valid parameters.", error.getErrorMessage());
    }

    @Test
    public void testError() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"error_message\":\"Failed to fetch entries. Please try again with valid parameters.\",\"error_code\":141,\"errors\":{\"environment\":[\"is required.\"]}}";
        Error error = objectMapper.readValue(json, Error.class);
        Assertions.assertNotNull(error.getError());
    }

    @Test
    public void testErrorAllArgsContructure() {
        Error error = new Error("{\"environment\":[\"is required.\"]}}", "Failed to fetch entries. Please try again with valid parameters.", 141);
        Assertions.assertNotNull(error.getError());
    }

    @Test
    public void testErrorBuilder() {
        Error error = new Error("{\"environment\":[\"is required.\"]}}", "Failed to fetch entries. Please try again with valid parameters.", 141);
        new Error().setError(141);
        Assertions.assertNotNull(error.getError());
    }
}
