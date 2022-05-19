package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.logging.Logger;

/**
 * The Contentstack RetryCallback
 *
 * @author Shailesh Mishra
 * @version 1.0.0
 * @since 2022-05-19
 */
public abstract class RetryCallback<T> implements Callback<T> {

    private final Logger log = Logger.getLogger(RetryCallback.class.getName());
    private static final int TOTAL_RETRIES = 3;
    private final Call<T> call;
    private int retryCount = 0;

    /**
     * Instantiates a new Callback with retry.
     *
     * @param call
     *         the call
     */
    protected RetryCallback(Call<T> call) {
        this.call = call;
    }

    @Override
    public void onFailure(@NotNull Call<T> call, Throwable t) {
        log.info(t.getLocalizedMessage());
        if (retryCount++ < TOTAL_RETRIES) {
            retry();
        }
    }

    private void retry() {
        call.clone().enqueue(this);
    }
}
