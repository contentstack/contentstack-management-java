package com.contentstack.cms.core;

import org.jetbrains.annotations.Nullable;

/**
 * Configuration options for retry delay calculation.
 * <p>
 * Supports three delay strategies (in order of precedence):
 * <ol>
 *   <li><b>Custom Backoff:</b> If {@code customBackoff} is set, it will be used</li>
 *   <li><b>Linear Backoff:</b> If {@code base} is set, delay = base * retryCount</li>
 *   <li><b>Fixed Delay:</b> Falls back to {@code retryDelay} from RetryConfig</li>
 * </ol>
 * </p>
 *
 * @author Contentstack
 * @version v1.0.0
 * @since 2026-01-28
 */
public class RetryDelayOptions {

    /**
     * Base multiplier for linear backoff.
     * Delay becomes: base * retryCount
     * Example: base = 1000, retryCount = 1 → 1000ms, retryCount = 2 → 2000ms
     */
    private final Long base;

    /**
     * Custom backoff function for advanced delay calculation.
     * Takes precedence over base if both are set.
     */
    private final CustomBackoff customBackoff;

    /**
     * Private constructor. Use Builder to create instances.
     */
    private RetryDelayOptions(Builder builder) {
        this.base = builder.base;
        this.customBackoff = builder.customBackoff;
    }

    /**
     * Gets the base multiplier for linear backoff.
     *
     * @return the base multiplier, or null if not set
     */
    @Nullable
    public Long getBase() {
        return base;
    }

    /**
     * Gets the custom backoff function.
     *
     * @return the custom backoff function, or null if not set
     */
    @Nullable
    public CustomBackoff getCustomBackoff() {
        return customBackoff;
    }

    /**
     * Builder for creating RetryDelayOptions instances.
     */
    public static class Builder {
        private Long base;
        private CustomBackoff customBackoff;

        /**
         * Sets the base multiplier for linear backoff.
         * Delay calculation: base * retryCount
         *
         * @param base the base multiplier in milliseconds
         * @return this builder instance
         */
        public Builder base(long base) {
            this.base = base;
            return this;
        }

        /**
         * Sets a custom backoff function for advanced delay calculation.
         * This takes precedence over base if both are set.
         *
         * @param customBackoff the custom backoff function
         * @return this builder instance
         */
        public Builder customBackoff(CustomBackoff customBackoff) {
            this.customBackoff = customBackoff;
            return this;
        }

        /**
         * Builds the RetryDelayOptions instance.
         *
         * @return a new RetryDelayOptions instance
         */
        public RetryDelayOptions build() {
            return new RetryDelayOptions(this);
        }
    }

    /**
     * Creates a new builder for RetryDelayOptions.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}
