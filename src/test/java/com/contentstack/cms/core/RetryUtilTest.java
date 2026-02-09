package com.contentstack.cms.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class RetryUtilTest {

    @Test
    void calculateDelay_nullConfig_throws() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                RetryUtil.calculateDelay(null, 1, 429));
        Assertions.assertNotNull(ex.getMessage());
    }

    @Test
    void calculateDelay_defaultConfig_returnsFixedDelay() {
        RetryConfig config = RetryConfig.defaultConfig();
        long delay = RetryUtil.calculateDelay(config, 1, 429);
        Assertions.assertEquals(300L, delay);
    }

    @Test
    void calculateDelay_customRetryDelay_returnsThatDelay() {
        RetryConfig config = RetryConfig.builder().retryDelay(500).build();
        long delay = RetryUtil.calculateDelay(config, 1, 429);
        Assertions.assertEquals(500L, delay);
    }

    @Test
    void calculateDelay_withBase_returnsLinearBackoff() {
        RetryConfig config = RetryConfig.builder()
                .retryDelayOptions(RetryDelayOptions.builder().base(1000).build())
                .build();
        Assertions.assertEquals(1000L, RetryUtil.calculateDelay(config, 1, 429));
        Assertions.assertEquals(2000L, RetryUtil.calculateDelay(config, 2, 429));
        Assertions.assertEquals(3000L, RetryUtil.calculateDelay(config, 3, 429));
    }

    @Test
    void calculateDelay_withCustomBackoff_returnsCustomValue() {
        RetryConfig config = RetryConfig.builder()
                .retryDelayOptions(RetryDelayOptions.builder()
                        .customBackoff((retryCount, statusCode, error) -> retryCount * 500L)
                        .build())
                .build();
        Assertions.assertEquals(500L, RetryUtil.calculateDelay(config, 1, 429));
        Assertions.assertEquals(1000L, RetryUtil.calculateDelay(config, 2, 503));
    }

    @Test
    void calculateDelay_customBackoffTakesPrecedenceOverBase() {
        RetryConfig config = RetryConfig.builder()
                .retryDelayOptions(RetryDelayOptions.builder()
                        .base(100)
                        .customBackoff((retryCount, statusCode, error) -> 999L)
                        .build())
                .build();
        long delay = RetryUtil.calculateDelay(config, 2, 429);
        Assertions.assertEquals(999L, delay);
    }

    @Test
    void calculateDelay_threeArgVersion_passesNullError() {
        RetryConfig config = RetryConfig.defaultConfig();
        long delay = RetryUtil.calculateDelay(config, 1, 429);
        Assertions.assertEquals(300L, delay);
    }
}
