package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;

import java.util.logging.Logger;

import retrofit2.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * The Contentstack RetryCallback
 *
 * @since 2022-10-20
 */
public abstract class RetryCallback<T> implements Callback<T> {

    // The code snippet you provided is declaring and initializing instance
    // variables for the
    // `RetryCallback` class:
    private final Logger log = Logger.getLogger(RetryCallback.class.getName());
    private final Call<T> call;
    private int retryCount = 0;
    private final RetryConfig retryConfig;

    // The `protected RetryCallback(Call<T> call)` constructor is used to
    // instantiate a new `RetryCallback`
    // object. It takes a `Call<T>` object as a parameter, which represents the
    // network call that was made.
    // The constructor assigns this `Call<T>` object to the `call` instance
    // variable.
    protected RetryCallback(Call<T> call) {
        this(call, null);
    }

    protected RetryCallback(Call<T> call, RetryConfig retryConfig) {
        this.call = call;
        this.retryConfig = retryConfig != null ? retryConfig : RetryConfig.defaultConfig();
    }

    /**
     * The function logs the localized message of the thrown exception and
     * retries the API call if the retry count is less than the total number of
     * retries allowed.
     *
     * @param call The `Call` object represents the network call that was made.
     * It contains information about the request and response.
     * @param t The parameter `t` is the `Throwable` object that represents the
     * exception or error that occurred during the execution of the network
     * call. It contains information about the error, such as the error message
     * and stack trace.
     */
    @Override
    public void onFailure(@NotNull Call<T> call, Throwable t) {
        int statusCode = extractStatusCode(t);

        if (!retryConfig.getRetryCondition().shouldRetry(statusCode, t)) {
            onFinalFailure(call, t);
        } else {
            if (retryCount >= retryConfig.getRetryLimit()) {
                onFinalFailure(call,t);    
            } else {
                retryCount++;
                long delay = RetryUtil.calculateDelay(retryConfig, retryCount, statusCode);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    log.log(java.util.logging.Level.WARNING, "Retry interrupted", ex);
                    onFinalFailure(call, t);
                    return;
                }
                retry();
            }
        }
    }

    /**
     * The retry function clones a call and enqueues it.
     */
    private void retry() {
        call.clone().enqueue(this);
    }

    private int extractStatusCode(Throwable t) {
        if (t instanceof HttpException) {
            Response<?> response = ((HttpException) t).response();
            if (response != null) {
                return response.code();
            } else {
                return -1;
            }
        } else if (t instanceof IOException || t instanceof SocketTimeoutException) {
            return 0;
        }
        return -1;
    }

    protected void onFinalFailure(Call<T> call, Throwable t) {
        log.warning("Final failure after " + retryCount + " retries: " + (t != null ? t.getMessage() : ""));
    }
}
