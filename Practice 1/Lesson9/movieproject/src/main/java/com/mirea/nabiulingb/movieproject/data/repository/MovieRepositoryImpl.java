package com.mirea.nabiulingb.movieproject.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.mirea.nabiulingb.movieproject.data.storage.MovieStorage;
import com.mirea.nabiulingb.movieproject.domain.models.Movie;
import com.mirea.nabiulingb.movieproject.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        return movieStorage.save(movie);
    }

    @Override
    public Movie getMovie() {
        return movieStorage.get();
    }
}