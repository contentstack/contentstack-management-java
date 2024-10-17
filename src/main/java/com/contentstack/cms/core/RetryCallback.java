package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.logging.Logger;

/**
 * The Contentstack RetryCallback
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-20
 */
public abstract class RetryCallback<T> implements Callback<T> {

    // The code snippet you provided is declaring and initializing instance
    // variables for the
    // `RetryCallback` class:
    private final Logger log = Logger.getLogger(RetryCallback.class.getName());
    private static final int TOTAL_RETRIES = 3;
    private final Call<T> call;
    private int retryCount = 0;

    // The `protected RetryCallback(Call<T> call)` constructor is used to
    // instantiate a new `RetryCallback`
    // object. It takes a `Call<T>` object as a parameter, which represents the
    // network call that was made.
    // The constructor assigns this `Call<T>` object to the `call` instance
    // variable.
    protected RetryCallback(Call<T> call) {
        this.call = call;
    }

    /**
     * The function logs the localized message of the thrown exception and retries
     * the API call if the
     * retry count is less than the total number of retries allowed.
     *
     * @param call The `Call` object represents the network call that was made. It
     *             contains information
     *             about the request and response.
     * @param t    The parameter `t` is the `Throwable` object that represents the
     *             exception or error that
     *             occurred during the execution of the network call. It contains
     *             information about the error, such as
     *             the error message and stack trace.
     */
    @Override
    public void onFailure(@NotNull Call<T> call, Throwable t) {
        log.info(t.getLocalizedMessage());
        if (retryCount++ < TOTAL_RETRIES) {
            retry();
        }
    }

    /**
     * The retry function clones a call and enqueues it.
     */
    private void retry() {
        call.clone().enqueue(this);
    }
}
