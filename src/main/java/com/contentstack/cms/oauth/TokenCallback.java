package com.contentstack.cms.oauth;

import com.contentstack.cms.models.OAuthTokens;

/**
 * Callback interface for token events
 */
public interface TokenCallback {
    /**
     * Called when tokens are updated
     * @param tokens The new tokens
     */
    void onTokensUpdated(OAuthTokens tokens);

    /**
     * Called when tokens are cleared
     */
    void onTokensCleared();
}
