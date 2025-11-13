package com.mirea.nabiulingb.movieproject.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mirea.nabiulingb.movieproject.data.storage.NetworkMovieStorage;
import com.mirea.nabiulingb.movieproject.domain.models.Movie;
import com.mirea.nabiulingb.movieproject.domain.repository.MovieRepository;
import com.mirea.nabiulingb.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import com.mirea.nabiulingb.movieproject.domain.usecases.SaveFilmToFavoriteUseCase;

public class MainViewModel extends ViewModel {

    private final MovieRepository movieRepository;
    private final NetworkMovieStorage networkStorage;

    private final MediatorLiveData<String> combinedMovieData = new MediatorLiveData<>();

    private Movie currentDbMovie = new Movie(-1, "Ожидание...");
    private Movie currentNetworkMovie = new Movie(-1, "Ожидание...");


    public MainViewModel(MovieRepository repository, NetworkMovieStorage networkStorage) {
        this.movieRepository = repository;
        this.networkStorage = networkStorage;

        loadCombinedMovies();
    }

    public LiveData<String> getCombinedMovieData() {
        return combinedMovieData;
    }

    private void loadCombinedMovies() {
        Movie dbResult = new GetFavoriteFilmUseCase(movieRepository).execute();
        MutableLiveData<Movie> dbMovieLiveData = new MutableLiveData<>();
        dbMovieLiveData.setValue(dbResult);

        LiveData<Movie> networkMovieLiveData = networkStorage.getNetworkMovie();

        combinedMovieData.addSource(dbMovieLiveData, dbMovie -> {
            currentDbMovie = dbMovie;
            updateCombinedLiveData();
        });

        combinedMovieData.addSource(networkMovieLiveData, networkMovie -> {
            currentNetworkMovie = networkMovie;
            updateCombinedLiveData();
        });
    }

    private void updateCombinedLiveData() {
        String dbStatus = (currentDbMovie != null && !currentDbMovie.getName().equals("Фильм не выбран"))
                ? "DB: " + currentDbMovie.getName()
                : "DB: Пусто";

        String networkStatus = (currentNetworkMovie != null && currentNetworkMovie.getId() != -1)
                ? "Network: " + currentNetworkMovie.getName()
                : "Network: Загрузка...";

        combinedMovieData.setValue("--- Комбинированные данные ---\n" + dbStatus + "\n" + networkStatus);
    }

    public void saveMovie(String movieName) {
        Movie movie = new Movie(1, movieName);

        SaveFilmToFavoriteUseCase saveUseCase = new SaveFilmToFavoriteUseCase(movieRepository);
        boolean result = saveUseCase.execute(movie);

        currentDbMovie = movie;
        updateCombinedLiveData();
        combinedMovieData.setValue("Сохранено в DB: " + (result ? movie.getName() : "Ошибка сохранения"));
    }
}