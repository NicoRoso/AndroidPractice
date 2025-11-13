package com.mirea.nabiulingb.movieproject.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mirea.nabiulingb.movieproject.data.storage.NetworkMovieStorage;
import com.mirea.nabiulingb.movieproject.domain.repository.MovieRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final MovieRepository movieRepository;
    private final NetworkMovieStorage networkStorage;

    public MainViewModelFactory(MovieRepository repository, NetworkMovieStorage networkStorage) {
        this.movieRepository = repository;
        this.networkStorage = networkStorage;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            try {
                return (T) new MainViewModel(movieRepository, networkStorage);
            } catch (Exception e) {
                throw new RuntimeException("Не удается создать экземпляр MainViewModel", e);
            }
        }
        throw new IllegalArgumentException("Неизвестный класс ViewModel");
    }
}