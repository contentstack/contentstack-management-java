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

    private Date issuedAt;
    private Date expiresAt;

    private static final long EXPIRY_BUFFER_MS = 120000; // 2 minutes buffer

    public OAuthTokens() {
        this.issuedAt = new Date();
    }

    /**
     * Sets the expiration time in seconds and calculates the expiry date
     * @param expiresIn Expiration time in seconds
     */
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        if (expiresIn != null) {
            this.expiresAt = new Date(issuedAt.getTime() + (expiresIn * 1000));
        }
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
     * Sets scopes from a list
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
     * @param scopeToCheck The scope to check for
     * @return true if the token has the scope
     */
    public boolean hasScope(String scopeToCheck) {
        return getScopesList().contains(scopeToCheck);
    }

    /**
     * Checks if the token is expired, including a buffer time
     * @return true if token is expired or will expire soon
     */
    public boolean isExpired() {
        if (expiresAt == null) {
            return true;
        }
        return System.currentTimeMillis() + EXPIRY_BUFFER_MS > expiresAt.getTime();
    }

    /**
     * Checks if the token is valid (has access token and not expired)
     * @return true if token is valid
     */
    public boolean isValid() {
        return hasAccessToken() && !isExpired();
    }

    /**
     * Checks if access token is present
     * @return true if access token exists
     */
    public boolean hasAccessToken() {
        return accessToken != null && !accessToken.trim().isEmpty();
    }

    /**
     * Checks if refresh token is present
     * @return true if refresh token exists
     */
    public boolean hasRefreshToken() {
        return refreshToken != null && !refreshToken.trim().isEmpty();
    }

    /**
     * Gets time until token expiration in milliseconds
     * @return milliseconds until expiration or 0 if expired/invalid
     */
    public long getTimeUntilExpiration() {
        if (expiresAt == null) {
            return 0;
        }
        long timeLeft = expiresAt.getTime() - System.currentTimeMillis();
        return Math.max(0, timeLeft);
    }

    /**
     * Creates a copy of this token object
     * @return A new OAuthTokens instance with copied values
     */
    public OAuthTokens copy() {
        OAuthTokens copy = new OAuthTokens();
        copy.accessToken = this.accessToken;
        copy.refreshToken = this.refreshToken;
        copy.tokenType = this.tokenType;
        copy.expiresIn = this.expiresIn;
        copy.scope = this.scope;
        copy.organizationUid = this.organizationUid;
        copy.userUid = this.userUid;
        copy.issuedAt = this.issuedAt != null ? new Date(this.issuedAt.getTime()) : null;
        copy.expiresAt = this.expiresAt != null ? new Date(this.expiresAt.getTime()) : null;
        return copy;
    }

    @Override
    public String toString() {
        return "OAuthTokens{" +
                "accessToken='" + (accessToken != null ? "[REDACTED]" : "null") + '\'' +
                ", refreshToken='" + (refreshToken != null ? "[REDACTED]" : "null") + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", organizationUid='" + organizationUid + '\'' +
                ", userUid='" + userUid + '\'' +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}