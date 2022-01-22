package com.contentstack.cms.core;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.logging.Logger;

/**
 * The type Callback with retry.
 *
 * @param <T> the type parameter
 */
public abstract class RetryCallback<T> implements Callback<T> {

    private final Logger log = Logger.getLogger(RetryCallback.class.getName());
    private static final int TOTAL_RETRIES = 3;
    private final Call<T> call;
    private int retryCount = 0;

    /**
     * Instantiates a new Callback with retry.
     *
     * @param call the call
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
