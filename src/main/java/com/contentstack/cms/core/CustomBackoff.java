package com.contentstack.cms.core;

import org.jetbrains.annotations.Nullable;

/**
 * Functional interface for custom backoff delay calculation.
 * <p>
 * Allows custom logic to calculate retry delays based on retry count and error information.
 * This enables advanced backoff strategies like exponential backoff with jitter.
 * </p>
 *
 * @author Contentstack
 * @version v1.0.0
 * @since 2026-01-28
 */
@FunctionalInterface
public interface CustomBackoff {

    /**
     * Calculates the delay in milliseconds before the next retry attempt.
     *
     * @param retryCount The current retry attempt number (1-based: 1st retry, 2nd retry, etc.)
     * @param statusCode HTTP status code from the response, or:
     *                   <ul>
     *                     <li>0 for network errors</li>
     *                     <li>-1 for unknown errors</li>
     *                   </ul>
     * @param error      The throwable that caused the failure (may be null)
     * @return The delay in milliseconds before the next retry
     */
    long calculate(int retryCount, int statusCode, @Nullable Throwable error);
}
