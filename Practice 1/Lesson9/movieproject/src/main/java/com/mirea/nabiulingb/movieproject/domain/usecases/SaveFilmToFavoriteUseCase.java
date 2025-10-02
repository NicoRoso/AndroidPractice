package com.mirea.nabiulingb.movieproject.domain.usecases;

import com.mirea.nabiulingb.movieproject.domain.models.Movie;
import com.mirea.nabiulingb.movieproject.domain.repository.MovieRepository;

public class SaveFilmToFavoriteUseCase {
    private MovieRepository movieRepository;

    public SaveFilmToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean execute(Movie movie) {
        return movieRepository.saveMovie(movie);
    }
}