package com.contentstack.cms.user;

import com.google.gson.Gson;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

public class CSResponse {

    private Response<ResponseBody> response;

    public CSResponse(Response<ResponseBody> response) {
        this.response = response;
    }

    public CSResponse() {
    }

    public String asString() throws IOException {
        return this.response.body() != null ? this.response.body().string() : null;
    }

    public String asJson() throws IOException {
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().toJson(body);
    }

    public String asJson(String string) {
        Objects.requireNonNull(string, "string == null");
        return new Gson().toJson(string);
    }

    public <T> T toModel(Class<T> tClass) throws IOException {
        Objects.requireNonNull(tClass, "model class == null");
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().fromJson(body, tClass);
    }

    public <T> T toModel(Class<T> tClass, String response) throws IOException {
        Objects.requireNonNull(tClass, "model class == null");
        Objects.requireNonNull(response, "response == null");
        return new Gson().fromJson(response, tClass);
    }

    public <T> T toModel(Class<T> tClass, Response<ResponseBody> response) throws IOException {
        Objects.requireNonNull(tClass, "model class == null");
        Objects.requireNonNull(response, "response == null");
        String body = this.response.body() != null ? this.response.body().string() : null;
        return new Gson().fromJson(body, tClass);
    }

}
