package com.contentstack.cms.core;

import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Type;

public class ResponseResult<T> {

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


class BaseCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful()) {
            System.out.println("Response Fail");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        System.out.println("Response Fail" + t.toString());
    }
}


interface CustomCallListener<T> {
    public void getResult(T object);
}
