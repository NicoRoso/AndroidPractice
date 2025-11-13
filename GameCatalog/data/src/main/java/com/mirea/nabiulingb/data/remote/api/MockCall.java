package com.mirea.nabiulingb.data.remote.api;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockCall<T> implements Call<T> {

    private final Response<T> response;

    public MockCall(Response<T> response) {
        this.response = response;
    }

    @Override
    public Response<T> execute() throws IOException {
        return response;
    }

    @Override
    public void enqueue(Callback<T> callback) {
    }

    @Override
    public boolean isExecuted() { return false; }

    @Override
    public void cancel() { }

    @Override
    public boolean isCanceled() { return false; }

    @Override
    public Call<T> clone() { return new MockCall<>(response); }

    @Override
    public Request request() {
        return new Request.Builder().url("http://mock.api/").build();
    }

    @Override
    public okio.Timeout timeout() {
        return okio.Timeout.NONE;
    }
}