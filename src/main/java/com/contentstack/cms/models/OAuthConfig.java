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
     *
     * @throws IllegalArgumentException if required fields are missing or
     * invalid
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

        try {
            new URL(redirectUri);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("redirectUri must be a valid URL", e);
        }
    }

    /**
     * Checks if PKCE flow should be used (when clientSecret is not provided)
     *
     * @return true if PKCE should be used
     */
    public boolean isPkceEnabled() {
        return clientSecret == null || clientSecret.trim().isEmpty();
    }

    /**
     * Gets the formatted authorization endpoint URL
     *
     * @return The authorization endpoint URL
     */
    public String getFormattedAuthorizationEndpoint() {
        if (authEndpoint != null) {
            return authEndpoint;
        }

        String hostname = "app.contentstack.com";

        // Transform hostname if needed
        if (hostname.contains("contentstack")) {
            hostname = hostname
                    .replaceAll("^api\\.", "app.") // api.contentstack -> app.contentstack
                    .replaceAll("\\.io$", ".com");  // *.io -> *.com
        }

        return "https://" + hostname + "/#!/apps/" + appId + "/authorize";
    }

    /**
     * Gets the formatted token endpoint URL
     *
     * @return The token endpoint URL
     */
    public String getTokenEndpoint() {
        if (tokenEndpoint != null) {
            return tokenEndpoint;
        }

        String hostname = "developerhub-api.contentstack.com";

        // Transform hostname if needed
        if (hostname.contains("contentstack")) {
            hostname = hostname
                    .replaceAll("^dev\\d+\\.", "dev.") // dev1.* -> dev.*
                    .replaceAll("\\.io$", ".com");      // *.io -> *.com
        }

        return "https://" + hostname + "/token";
    }

    /**
     * Gets the response type, defaulting to "code"
     *
     * @return The response type
     */
    public String getResponseType() {
        return responseType != null ? responseType : "code";
    }

    /**
     * Gets the scopes as a list
     *
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
     *
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
         *
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
         *
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
