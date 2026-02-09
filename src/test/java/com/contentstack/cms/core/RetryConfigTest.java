package com.contentstack.cms.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
class RetryConfigTest {

    @Test
    void defaultConfig_returnsNonNull() {
        RetryConfig config = RetryConfig.defaultConfig();
        Assertions.assertNotNull(config);
    }

    @Test
    void defaultConfig_hasExpectedDefaults() {
        RetryConfig config = RetryConfig.defaultConfig();
        Assertions.assertEquals(3, config.getRetryLimit());
        Assertions.assertEquals(300L, config.getRetryDelay());
        Assertions.assertNotNull(config.getRetryCondition());
        Assertions.assertTrue(config.getRetryCondition() instanceof DefaultRetryCondition);
    }

    @Test
    void builder_withCustomLimit() {
        RetryConfig config = RetryConfig.builder()
                .retryLimit(5)
                .build();
        Assertions.assertEquals(5, config.getRetryLimit());
        Assertions.assertEquals(300L, config.getRetryDelay());
    }

    @Test
    void builder_withCustomDelay() {
        RetryConfig config = RetryConfig.builder()
                .retryDelay(500)
                .build();
        Assertions.assertEquals(3, config.getRetryLimit());
        Assertions.assertEquals(500L, config.getRetryDelay());
    }

    @Test
    void builder_withCustomCondition() {
        RetryCondition customCondition = (statusCode, error) -> statusCode == 503;
        RetryConfig config = RetryConfig.builder()
                .retryCondition(customCondition)
                .build();
        Assertions.assertSame(customCondition, config.getRetryCondition());
    }

    @Test
    void builder_withNullCondition_usesDefault() {
        RetryConfig config = RetryConfig.builder().build();
        Assertions.assertNotNull(config.getRetryCondition());
        Assertions.assertTrue(config.getRetryCondition() instanceof DefaultRetryCondition);
    }

    @Test
    void builder_withRetryDelayOptions() {
        RetryDelayOptions options = RetryDelayOptions.builder().base(1000).build();
        RetryConfig config = RetryConfig.builder()
                .retryDelayOptions(options)
                .build();
        RetryDelayOptions delayOptions = config.getRetryDelayOptions();
        Assertions.assertNotNull(delayOptions);
        Long base = delayOptions.getBase();
        Assertions.assertNotNull(base);
        Assertions.assertEquals(1000L, base.longValue());
    }

    @Test
    void builder_retryLimitNegative_throws() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                RetryConfig.builder().retryLimit(-1).build());
        Assertions.assertNotNull(ex.getMessage());
    }

    @Test
    void builder_retryDelayNegative_throws() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                RetryConfig.builder().retryDelay(-1).build());
        Assertions.assertNotNull(ex.getMessage());
    }

    @Test
    void builder_retryLimitZero_allowed() {
        RetryConfig config = RetryConfig.builder().retryLimit(0).build();
        Assertions.assertEquals(0, config.getRetryLimit());
    }
}
