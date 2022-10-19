package com.contentstack.cms.core;

import com.contentstack.cms.models.Error;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * The Contentstack ResponseResult class that accepts different types of Models
 *
 * @author ***REMOVED***
 * @version v0.1.0
 * @since 2022-10-20
 */
class ResponseResult<T> {

    public T execute(Call<T> call) throws IOException {
        T data = null;
        Response<T> result = call.execute();
        if (result.isSuccessful()) {
            if (result.body() != null) {
                data = result.body();
            }
        } else {
            if (result.errorBody() != null) {
                data = new Gson().fromJson(result.errorBody().string(), (Type) Error.class);
            }
        }
        return data;
    }
}
