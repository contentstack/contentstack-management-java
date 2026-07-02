package com.contentstack.cms.models;

import lombok.Builder;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.contentstack.cms.core.Util;
import com.contentstack.cms.oauth.TokenCallback;

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
    private final String host;

    /**
     * Callback for token events
     */
    private final TokenCallback tokenCallback;

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
            return validateHttpsEndpoint(authEndpoint);
        }

        // Only use the configured host when it is a genuine Contentstack host;
        // otherwise fall back to the default. This prevents SSRF via a host such
        // as "evil-contentstack.attacker.com" that merely contains the substring.
        String hostname = isTrustedContentstackHost(host) ? host : Util.OAUTH_APP_HOST;

        hostname = hostname
                .replaceAll("-api\\.", "-app.") // eu-api.contentstack.com -> eu-app.contentstack.com
                .replaceAll("^api\\.", "app.") // api.contentstack.io -> app.contentstack.io
                .replaceAll("\\.io$", ".com"); // *.io -> *.com

        return "https://" + hostname + String.format(Util.OAUTH_AUTHORIZE_ENDPOINT, appId);
    }

    /**
     * Gets the formatted token endpoint URL
     *
     * @return The token endpoint URL
     */
    public String getTokenEndpoint() {
        if (tokenEndpoint != null) {
            return validateHttpsEndpoint(tokenEndpoint);
        }

        // Only use the configured host when it is a genuine Contentstack host;
        // otherwise fall back to the default. This prevents SSRF via a host such
        // as "evil-contentstack.attacker.com" that merely contains the substring.
        String hostname = isTrustedContentstackHost(host) ? host : Util.OAUTH_API_HOST;

        hostname = hostname
                .replaceAll("-api\\.", "-developerhub-api.") // eu-api.contentstack.com -> eu-developerhub-api.contentstack.com
                .replaceAll("^api\\.", "developerhub-api.") // api.contentstack.io -> developerhub-api.contentstack.io
                .replaceAll("\\.io$", ".com"); // *.io -> *.com

        return "https://" + hostname + Util.OAUTH_TOKEN_ENDPOINT;
    }

    /**
     * Determines whether the supplied host is a bare Contentstack hostname that
     * is safe to use as an outbound request target.
     *
     * <p>The host must be a bare host name (optionally with a port) — no embedded
     * scheme, credentials, path, query, fragment, or whitespace — and must resolve
     * to a {@code contentstack.com} or {@code contentstack.io} domain. This guards
     * against Server-Side Request Forgery (SSRF) when the host originates from
     * untrusted input.
     *
     * @param candidate the candidate host
     * @return true if the host is a trusted, well-formed Contentstack host
     */
    private static boolean isTrustedContentstackHost(String candidate) {
        if (candidate == null) {
            return false;
        }
        String host = candidate.trim();
        // Reject embedded scheme, credentials, path/query/fragment, whitespace,
        // and any other characters that would change the request target.
        if (!host.matches("^[A-Za-z0-9.-]+(:\\d{1,5})?$")) {
            return false;
        }
        int portIndex = host.indexOf(':');
        String hostOnly = (portIndex >= 0 ? host.substring(0, portIndex) : host).toLowerCase();
        return hostOnly.equals("contentstack.com")
                || hostOnly.equals("contentstack.io")
                || hostOnly.endsWith(".contentstack.com")
                || hostOnly.endsWith(".contentstack.io");
    }

    /**
     * Validates that an explicitly configured OAuth endpoint is a well-formed
     * absolute HTTPS URL. Rejecting non-HTTPS schemes prevents the endpoint from
     * being pointed at internal services or non-web protocols (SSRF).
     *
     * @param endpoint the configured endpoint URL
     * @return the endpoint, unchanged, when valid
     * @throws IllegalArgumentException if the endpoint is not a valid HTTPS URL
     */
    private static String validateHttpsEndpoint(String endpoint) {
        try {
            URL url = new URL(endpoint);
            if (!"https".equalsIgnoreCase(url.getProtocol())) {
                throw new IllegalArgumentException("OAuth endpoint must use HTTPS: " + endpoint);
            }
            return endpoint;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid OAuth endpoint URL: " + endpoint, e);
        }
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
