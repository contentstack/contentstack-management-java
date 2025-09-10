package com.contentstack.cms.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Model class for OAuth tokens and related data
 */
@Getter
@Setter
public class OAuthTokens {

    private static final String BEARER_TOKEN_TYPE = "Bearer";
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Long expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("organization_uid")
    private String organizationUid;

    @SerializedName("user_uid")
    private String userUid;

    @SerializedName("stack_api_key")
    private String stackApiKey;

    private Date issuedAt;
    private Date expiresAt;

    private static final long EXPIRY_BUFFER_MS = 120000; // 2 minutes buffer

    public OAuthTokens() {
        this.issuedAt = new Date();
    }

    /**
     * Sets the expiration time in seconds and calculates the expiry date
     *
     * @param expiresIn Expiration time in seconds
     */
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        if (expiresIn != null) {
            setExpiresAt(new Date(System.currentTimeMillis() + (expiresIn * 1000)));
        }
    }

    public synchronized void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt != null ? new Date(expiresAt.getTime()) : null;
        if (expiresAt != null) {
            this.expiresIn = (expiresAt.getTime() - System.currentTimeMillis()) / 1000;
        }
    }

    public synchronized Date getExpiresAt() {
        return expiresAt != null ? new Date(expiresAt.getTime()) : null;
    }

    public synchronized Date getIssuedAt() {
        return issuedAt != null ? new Date(issuedAt.getTime()) : null;
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
     * Sets scopes from a list
     *
     * @param scopes List of scope strings
     */
    public void setScopesList(List<String> scopes) {
        if (scopes == null || scopes.isEmpty()) {
            this.scope = null;
        } else {
            this.scope = String.join(" ", scopes);
        }
    }

    /**
     * Checks if the token has a specific scope
     *
     * @param scopeToCheck The scope to check for
     * @return true if the token has the scope
     */
    public boolean hasScope(String scopeToCheck) {
        return getScopesList().contains(scopeToCheck);
    }

    /**
     * Checks if the token is expired, including a buffer time
     *
     * @return true if token is expired or will expire soon
     */
    public boolean isExpired() {
        // No expiry time means token is considered expired
        if (expiresAt == null) {
            return true;
        }

        // No access token means token is considered expired
        if (!hasAccessToken()) {
            return true;
        }

        // Check if current time + buffer is past expiry
        long currentTime = System.currentTimeMillis();
        long expiryTime = expiresAt.getTime();
        long timeUntilExpiry = expiryTime - currentTime;

        // Consider expired if within buffer window
        return timeUntilExpiry <= EXPIRY_BUFFER_MS;
    }

    /**
     * Checks if the token is valid (has access token and not expired)
     *
     * @return true if token is valid
     */
    public synchronized boolean isValid() {
        return hasAccessToken() && !isExpired()
                && BEARER_TOKEN_TYPE.equalsIgnoreCase(tokenType);
    }

    /**
     * Checks if access token is present
     *
     * @return true if access token exists
     */
    public boolean hasAccessToken() {
        return accessToken != null && !accessToken.trim().isEmpty();
    }

    /**
     * Checks if refresh token is present
     *
     * @return true if refresh token exists
     */
    public boolean hasRefreshToken() {
        return refreshToken != null && !refreshToken.trim().isEmpty();
    }

    /**
     * Gets time until token expiration in milliseconds
     *
     * @return milliseconds until expiration or 0 if expired/invalid
     */
    public long getTimeUntilExpiration() {
        if (expiresAt == null) {
            return 0;
        }
        long timeLeft = expiresAt.getTime() - System.currentTimeMillis();
        return Math.max(0, timeLeft);
    }


    @Override
    public String toString() {
        return "OAuthTokens{"
                + "accessToken='" + (accessToken != null ? "[REDACTED]" : "null") + '\''
                + ", refreshToken='" + (refreshToken != null ? "[REDACTED]" : "null") + '\''
                + ", tokenType='" + tokenType + '\''
                + ", expiresIn=" + expiresIn
                + ", scope='" + scope + '\''
                + ", organizationUid='" + organizationUid + '\''
                + ", userUid='" + userUid + '\''
                + ", stackApiKey='" + stackApiKey + '\''
                + ", issuedAt=" + issuedAt
                + ", expiresAt=" + expiresAt
                + '}';
    }
}
