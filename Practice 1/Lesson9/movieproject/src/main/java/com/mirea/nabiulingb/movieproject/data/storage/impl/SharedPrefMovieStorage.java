package com.mirea.nabiulingb.movieproject.data.storage.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.mirea.nabiulingb.movieproject.data.storage.MovieStorage;
import com.mirea.nabiulingb.movieproject.domain.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {

    private static final String PREFS_NAME = "MoviePrefs";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_NAME = "movie_name";

    private SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean save(Movie movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_MOVIE_ID, movie.getId());
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        return editor.commit();
    }

    @Override
    public Movie get() {
        int id = sharedPreferences.getInt(KEY_MOVIE_ID, -1);
        String name = sharedPreferences.getString(KEY_MOVIE_NAME, "Фильм не выбран");
        return new Movie(id, name);
    }
}