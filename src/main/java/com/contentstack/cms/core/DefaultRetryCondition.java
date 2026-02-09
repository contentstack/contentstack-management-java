package com.contentstack.cms.core;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Default implementation of RetryCondition that retries on:
 * <ul>
 *   <li>HTTP status codes: 408 (Request Timeout), 429 (Too Many Requests),
 *       500 (Internal Server Error), 502 (Bad Gateway), 503 (Service Unavailable),
 *       504 (Gateway Timeout)</li>
 *   <li>Network errors: IOException, SocketTimeoutException</li>
 * </ul>
 * <p>
 * This matches the default retry behavior of the JavaScript Delivery SDK.
 * </p>
 *
 * @author Contentstack
 * @version v1.0.0
 * @since 2026-01-28
 */
public class DefaultRetryCondition implements RetryCondition {

    /**
     * Default retryable HTTP status codes.
     * Matches JS SDK default: [408, 429, 500, 502, 503, 504]
     */
    private static final int[] RETRYABLE_STATUS_CODES = {408, 429, 500, 502, 503, 504};

    /**
     * Singleton instance for reuse.
     */
    private static final DefaultRetryCondition INSTANCE = new DefaultRetryCondition();

    /**
     * Private constructor to enforce singleton pattern.
     */
    private DefaultRetryCondition() {
    }

    /**
     * Gets the singleton instance of DefaultRetryCondition.
     *
     * @return the singleton instance
     */
    public static DefaultRetryCondition getInstance() {
        return INSTANCE;
    }

    /**
     * Determines if an error should be retried based on status code and exception type.
     *
     * @param statusCode HTTP status code (0 = network error, -1 = unknown)
     * @param error      The throwable that caused the failure (may be null)
     * @return true if the error should be retried, false otherwise
     */
    @Override
    public boolean shouldRetry(int statusCode, @Nullable Throwable error) {
        // Network errors (statusCode = 0) are always retryable
        if (statusCode == 0) {
            return true;
        }

        // Unknown errors (statusCode = -1) are not retryable by default
        if (statusCode == -1) {
            // However, if it's a network-related exception, we should retry
            if (error != null && (error instanceof IOException || error instanceof SocketTimeoutException)) {
                return true;
            }
            return false;
        }

        // Check if status code is in the retryable list
        for (int code : RETRYABLE_STATUS_CODES) {
            if (statusCode == code) {
                return true;
            }
        }

        return false;
    }
}
