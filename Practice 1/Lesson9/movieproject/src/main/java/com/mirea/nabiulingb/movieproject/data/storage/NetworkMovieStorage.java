package com.mirea.nabiulingb.movieproject.data.storage;

import androidx.lifecycle.LiveData;

import com.mirea.nabiulingb.movieproject.domain.models.Movie;

public interface NetworkMovieStorage {
    LiveData<Movie> getNetworkMovie();
}