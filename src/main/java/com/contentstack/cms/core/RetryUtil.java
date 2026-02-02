package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for retry-related calculations.
 * <p>
 * Provides helper methods for calculating retry delays based on RetryConfig.
 * </p>
 *
 * @author Contentstack
 * @version v1.0.0
 * @since 2026-01-28
 */
public class RetryUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private RetryUtil() {
        throw new AssertionError("RetryUtil should not be instantiated");
    }

    /**
     * Calculates the delay in milliseconds before the next retry attempt.
     * <p>
     * Priority order:
     * <ol>
     *   <li>If {@code retryDelayOptions.customBackoff} is set, use it</li>
     *   <li>If {@code retryDelayOptions.base} is set, use linear backoff: base * retryCount</li>
     *   <li>Otherwise, use fixed {@code retryDelay} from RetryConfig</li>
     * </ol>
     * </p>
     *
     * @param config     the RetryConfig containing delay settings
     * @param retryCount the current retry attempt number (1-based: 1st retry, 2nd retry, etc.)
     * @param statusCode HTTP status code from the response, or:
     *                   <ul>
     *                     <li>0 for network errors</li>
     *                     <li>-1 for unknown errors</li>
     *                   </ul>
     * @param error      the throwable that caused the failure (may be null)
     * @return the delay in milliseconds before the next retry
     * @throws IllegalArgumentException if config is null
     */
    public static long calculateDelay(@NotNull RetryConfig config, int retryCount, int statusCode,
                                      @Nullable Throwable error) {
        if (config == null) {
            throw new IllegalArgumentException("RetryConfig cannot be null");
        }

        RetryDelayOptions delayOptions = config.getRetryDelayOptions();

        // Priority 1: Custom backoff function
        if (delayOptions != null && delayOptions.getCustomBackoff() != null) {
            return delayOptions.getCustomBackoff().calculate(retryCount, statusCode, error);
        }

        // Priority 2: Linear backoff (base * retryCount)
        if (delayOptions != null && delayOptions.getBase() != null && delayOptions.getBase() > 0) {
            return delayOptions.getBase() * retryCount;
        }

        // Priority 3: Fixed delay (fallback)
        return config.getRetryDelay();
    }

    /**
     * Calculates the delay in milliseconds before the next retry attempt.
     * Convenience method that passes null for the error parameter.
     *
     * @param config     the RetryConfig containing delay settings
     * @param retryCount the current retry attempt number (1-based)
     * @param statusCode HTTP status code from the response
     * @return the delay in milliseconds before the next retry
     */
    public static long calculateDelay(@NotNull RetryConfig config, int retryCount, int statusCode) {
        return calculateDelay(config, retryCount, statusCode, null);
    }
}
