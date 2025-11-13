package com.mirea.nabiulingb.movieproject.data.storage.impl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mirea.nabiulingb.movieproject.data.storage.NetworkMovieStorage;
import com.mirea.nabiulingb.movieproject.domain.models.Movie;

public class FakeNetworkMovieStorage implements NetworkMovieStorage {

    @Override
    public LiveData<Movie> getNetworkMovie() {
        MutableLiveData<Movie> liveData = new MutableLiveData<>();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            liveData.postValue(new Movie(100, "FakeNetwork Film: Iron Man"));
        }).start();

        return liveData;
    }
}