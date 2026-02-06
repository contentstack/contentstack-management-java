package com.contentstack.cms.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Tag("unit")
class DefaultRetryConditionTest {

    private final RetryCondition condition = DefaultRetryCondition.getInstance();

    @Test
    void getInstance_returnsSingleton() {
        RetryCondition a = DefaultRetryCondition.getInstance();
        RetryCondition b = DefaultRetryCondition.getInstance();
        Assertions.assertSame(a, b);
    }

    @Test
    void shouldRetry_status408_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(408, null));
    }

    @Test
    void shouldRetry_status429_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(429, null));
    }

    @Test
    void shouldRetry_status500_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(500, null));
    }

    @Test
    void shouldRetry_status502_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(502, null));
    }

    @Test
    void shouldRetry_status503_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(503, null));
    }

    @Test
    void shouldRetry_status504_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(504, null));
    }

    @Test
    void shouldRetry_status404_returnsFalse() {
        Assertions.assertFalse(condition.shouldRetry(404, null));
    }

    @Test
    void shouldRetry_status400_returnsFalse() {
        Assertions.assertFalse(condition.shouldRetry(400, null));
    }

    @Test
    void shouldRetry_status200_returnsFalse() {
        Assertions.assertFalse(condition.shouldRetry(200, null));
    }

    @Test
    void shouldRetry_status0_networkError_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(0, null));
    }

    @Test
    void shouldRetry_statusMinus1_unknownNoError_returnsFalse() {
        Assertions.assertFalse(condition.shouldRetry(-1, null));
    }

    @Test
    void shouldRetry_statusMinus1_withIOException_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(-1, new IOException("network")));
    }

    @Test
    void shouldRetry_statusMinus1_withSocketTimeoutException_returnsTrue() {
        Assertions.assertTrue(condition.shouldRetry(-1, new SocketTimeoutException("timeout")));
    }

    @Test
    void shouldRetry_statusMinus1_withOtherException_returnsFalse() {
        Assertions.assertFalse(condition.shouldRetry(-1, new RuntimeException("other")));
    }
}
