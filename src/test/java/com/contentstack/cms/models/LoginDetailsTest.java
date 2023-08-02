package com.contentstack.cms.models;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDetailsTest {

    @Test
    public void testNoticeMapping() {
        // Given
        String json = "{\"notice\": \"Welcome to our website!\"}";
        Gson gson = new Gson();

        // When
        LoginDetails loginDetails = gson.fromJson(json, LoginDetails.class);

        // Then
        assertNotNull(loginDetails);
        assertEquals("Welcome to our website!", loginDetails.getNotice());
    }

    @Test
    public void testUserMapping() {
        // Given
        String json = "{\"user\": {\"id\": 123, \"name\": \"John Doe\"}}";
        Gson gson = new Gson();

        LoginDetails loginDetails = gson.fromJson(json, LoginDetails.class);

        assertNotNull(loginDetails);
        assertNotNull(loginDetails.getUser());
        assertNull(loginDetails.getUser().uid);
        assertNotNull(loginDetails.getUser());
    }
}

