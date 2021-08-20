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
public abstract class CallbackWithRetry<T> implements Callback<T> {

    private static final int TOTAL_RETRIES = 3;
    private static final String TAG = CallbackWithRetry.class.getSimpleName();
    private final Call<T> call;
    private final Logger log = Logger.getLogger(CallbackWithRetry.class.getName());
    private int retryCount = 0;

    /**
     * Instantiates a new Callback with retry.
     *
     * @param call the call
     */
    public CallbackWithRetry(Call<T> call) {
        this.call = call;
    }

    @Override
    public void onFailure(@NotNull Call<T> call, Throwable t) {
        log.info(t.getLocalizedMessage());
        if (retryCount++ < TOTAL_RETRIES) {
            log.fine("Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
            retry();
        }
    }

    private void retry() {
        call.clone().enqueue(this);
    }
}

