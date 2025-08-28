package com.contentstack.cms.models;

import lombok.Builder;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration class for OAuth 2.0 authentication
 */
@Getter
@Builder
public class OAuthConfig {
    private final String appId;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String responseType;
    private final String scope;
    private final String authEndpoint;
    private final String tokenEndpoint;

    /**
     * Validates the configuration
     * @throws IllegalArgumentException if required fields are missing or invalid
     */
    public void validate() {
        if (appId == null || appId.trim().isEmpty()) {
            throw new IllegalArgumentException("appId is required");
        }
        if (clientId == null || clientId.trim().isEmpty()) {
            throw new IllegalArgumentException("clientId is required");
        }
        if (redirectUri == null || redirectUri.trim().isEmpty()) {
            throw new IllegalArgumentException("redirectUri is required");
        }

        // Validate redirectUri is a valid URL
        try {
            new URL(redirectUri);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("redirectUri must be a valid URL", e);
        }
    }

    /**
     * Checks if PKCE flow should be used (when clientSecret is not provided)
     * @return true if PKCE should be used
     */
    public boolean isPkceEnabled() {
        return clientSecret == null || clientSecret.trim().isEmpty();
    }

    /**
     * Gets the formatted authorization endpoint URL
     * @return The authorization endpoint URL
     */
    public String getFormattedAuthorizationEndpoint() {
        if (authEndpoint != null) {
            return authEndpoint;
        }

        // Transform hostname similar to JS SDK
        String hostname = "app.contentstack.com";
        
        // Handle environment-specific transformations
        if (hostname.endsWith("io")) {
            hostname = hostname.replace("io", "com");
        }
        if (hostname.startsWith("api")) {
            hostname = hostname.replace("api", "app");
        }
        
        return "https://" + hostname + "/apps/oauth/authorize";
    }

    /**
     * Gets the formatted token endpoint URL
     * @return The token endpoint URL
     */
    public String getTokenEndpoint() {
        if (tokenEndpoint != null) {
            return tokenEndpoint;
        }

        // Transform for developer hub
        String hostname = "developerhub-api.contentstack.com";
        
        // Handle environment-specific transformations
        hostname = hostname
            .replaceAll("^dev\\d+", "dev")  // Replace dev1, dev2, etc. with dev
            .replace("io", "com");
        
        return "https://" + hostname + "/apps/oauth/token";
    }

    /**
     * Gets the response type, defaulting to "code"
     * @return The response type
     */
    public String getResponseType() {
        return responseType != null ? responseType : "code";
    }

    /**
     * Gets the scopes as a list
     * @return List of scope strings or empty list if no scopes
     */
    public List<String> getScopesList() {
        if (scope == null || scope.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.asList(scope.split(" "));
    }

    /**
     * Gets the scope string
     * @return The space-delimited scope string or null
     */
    public String getScope() {
        return scope;
    }

    /**
     * Builder class for OAuthConfig
     */
    public static class OAuthConfigBuilder {
        /**
         * Sets scopes from a list
         * @param scopes List of scope strings
         * @return Builder instance
         */
        public OAuthConfigBuilder scopes(List<String> scopes) {
            if (scopes == null || scopes.isEmpty()) {
                this.scope = null;
            } else {
                this.scope = String.join(" ", scopes);
            }
            return this;
        }

        /**
         * Sets scopes from varargs
         * @param scopes Scope strings
         * @return Builder instance
         */
        public OAuthConfigBuilder scopes(String... scopes) {
            if (scopes == null || scopes.length == 0) {
                this.scope = null;
            } else {
                this.scope = String.join(" ", scopes);
            }
            return this;
        }
    }
}