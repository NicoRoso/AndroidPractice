package com.mirea.nabiulingb.movieproject.domain.repository;

import com.mirea.nabiulingb.movieproject.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}