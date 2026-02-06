package com.contentstack.cms.core;

import org.jetbrains.annotations.Nullable;

/**
 * Interface for determining if an error should be retried.
 * <p>
 * This interface allows custom logic to determine whether a failed request
 * should be retried based on the HTTP status code and/or the exception that occurred.
 * <p>
 * Status code conventions:
 * <ul>
 *   <li>0 = Network error (IOException, SocketTimeoutException) - typically retryable</li>
 *   <li>-1 = Unknown error - typically not retryable</li>
 *   <li>Other values = HTTP status codes (200-599)</li>
 * </ul>
 *
 */
@FunctionalInterface
public interface RetryCondition {

    /**
     * Determines if an error should be retried.
     *
     * @param statusCode HTTP status code from the response, or:
     *                   <ul>
     *                     <li>0 for network errors (IOException, SocketTimeoutException)</li>
     *                     <li>-1 for unknown errors</li>
     *                   </ul>
     * @param error      The throwable that caused the failure. May be null if statusCode
     *                   is available from the response.
     * @return true if the error should be retried, false otherwise
     */
    boolean shouldRetry(int statusCode, @Nullable Throwable error);
}
