package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Configuration for retry behavior across the SDK.
 * <p>
 * This class centralizes retry configuration similar to the JavaScript Delivery SDK's
 * {@code fetchOptions.retry} parameters. It controls:
 * <ul>
 *   <li>How many times to retry (retryLimit)</li>
 *   <li>How long to wait between retries (retryDelay, retryDelayOptions)</li>
 *   <li>Which errors should be retried (retryCondition)</li>
 * </ul>
 * </p>
 * <p>
 * This configuration is used by:
 * <ul>
 *   <li>AuthInterceptor (non-OAuth synchronous calls)</li>
 *   <li>OAuthInterceptor (OAuth synchronous calls)</li>
 *   <li>RetryCallback (asynchronous calls)</li>
 * </ul>
 * </p>
 *
 * @author Contentstack
 * @version v1.0.0
 * @since 2026-01-28
 */
public class RetryConfig {

    /**
     * Default retry limit (matches current SDK behavior for backward compatibility).
     * Can be changed to 5 to match JS SDK default.
     */
    private static final int DEFAULT_RETRY_LIMIT = 3;

    /**
     * Default retry delay in milliseconds (fallback when no delay options specified).
     */
    private static final long DEFAULT_RETRY_DELAY = 300;

    /**
     * Maximum number of retry attempts before giving up.
     */
    private final int retryLimit;

    /**
     * Fallback delay in milliseconds (used when retryDelayOptions are not configured).
     */
    private final long retryDelay;

    /**
     * Condition function to determine if an error should be retried.
     */
    @NotNull
    private final RetryCondition retryCondition;

    /**
     * Options for calculating retry delays (base, custom backoff).
     */
    @Nullable
    private final RetryDelayOptions retryDelayOptions;

    /**
     * Private constructor. Use Builder to create instances.
     */
    private RetryConfig(Builder builder) {
        this.retryLimit = builder.retryLimit;
        this.retryDelay = builder.retryDelay;
        this.retryCondition = builder.retryCondition != null
                ? builder.retryCondition
                : DefaultRetryCondition.getInstance();
        this.retryDelayOptions = builder.retryDelayOptions;
    }

    /**
     * Gets the maximum number of retry attempts.
     *
     * @return the retry limit
     */
    public int getRetryLimit() {
        return retryLimit;
    }

    /**
     * Gets the fallback delay in milliseconds.
     *
     * @return the retry delay
     */
    public long getRetryDelay() {
        return retryDelay;
    }

    /**
     * Gets the retry condition function.
     *
     * @return the retry condition (never null)
     */
    @NotNull
    public RetryCondition getRetryCondition() {
        return retryCondition;
    }

    /**
     * Gets the retry delay options.
     *
     * @return the retry delay options, or null if not set
     */
    @Nullable
    public RetryDelayOptions getRetryDelayOptions() {
        return retryDelayOptions;
    }

    /**
     * Creates a default RetryConfig with sensible defaults.
     * <p>
     * Default values:
     * <ul>
     *   <li>retryLimit: 3 (for backward compatibility with current SDK)</li>
     *   <li>retryDelay: 300ms</li>
     *   <li>retryCondition: DefaultRetryCondition (retries on 408, 429, 5xx, network errors)</li>
     *   <li>retryDelayOptions: null (uses fixed retryDelay)</li>
     * </ul>
     * </p>
     *
     * @return a default RetryConfig instance
     */
    public static RetryConfig defaultConfig() {
        return new Builder().build();
    }

    /**
     * Builder for creating RetryConfig instances.
     */
    public static class Builder {
        private int retryLimit = DEFAULT_RETRY_LIMIT;
        private long retryDelay = DEFAULT_RETRY_DELAY;
        private RetryCondition retryCondition;
        private RetryDelayOptions retryDelayOptions;

        /**
         * Sets the maximum number of retry attempts.
         * Default: 3 (for backward compatibility)
         *
         * @param retryLimit the retry limit (must be >= 0)
         * @return this builder instance
         * @throws IllegalArgumentException if retryLimit is negative
         */
        public Builder retryLimit(int retryLimit) {
            if (retryLimit < 0) {
                throw new IllegalArgumentException("Retry limit must be >= 0");
            }
            this.retryLimit = retryLimit;
            return this;
        }

        /**
         * Sets the fallback delay in milliseconds.
         * This is used when retryDelayOptions are not configured.
         * Default: 300ms
         *
         * @param retryDelay the delay in milliseconds (must be >= 0)
         * @return this builder instance
         * @throws IllegalArgumentException if retryDelay is negative
         */
        public Builder retryDelay(long retryDelay) {
            if (retryDelay < 0) {
                throw new IllegalArgumentException("Retry delay must be >= 0");
            }
            this.retryDelay = retryDelay;
            return this;
        }

        /**
         * Sets a custom retry condition function.
         * If not set, DefaultRetryCondition will be used.
         *
         * @param retryCondition the retry condition function
         * @return this builder instance
         */
        public Builder retryCondition(@NotNull RetryCondition retryCondition) {
            this.retryCondition = retryCondition;
            return this;
        }

        /**
         * Sets the retry delay options (base, custom backoff).
         *
         * @param retryDelayOptions the delay options
         * @return this builder instance
         */
        public Builder retryDelayOptions(@Nullable RetryDelayOptions retryDelayOptions) {
            this.retryDelayOptions = retryDelayOptions;
            return this;
        }

        /**
         * Builds the RetryConfig instance.
         *
         * @return a new RetryConfig instance
         */
        public RetryConfig build() {
            return new RetryConfig(this);
        }
    }

    /**
     * Creates a new builder for RetryConfig.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}
