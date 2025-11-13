package com.mirea.nabiulingb.movieproject.data.storage;

import com.mirea.nabiulingb.movieproject.domain.models.Movie;

public interface MovieStorage {
    boolean save(Movie movie);
    Movie get();
}