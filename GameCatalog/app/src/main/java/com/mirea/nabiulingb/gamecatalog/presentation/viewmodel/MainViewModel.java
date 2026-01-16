package com.mirea.nabiulingb.gamecatalog.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.models.GameDetails;
import com.mirea.nabiulingb.domain.usecases.games.GetAllGames;
import com.mirea.nabiulingb.domain.usecases.games.SearchGames;
import com.mirea.nabiulingb.domain.usecases.collections.GetUserCollection;
import com.mirea.nabiulingb.domain.usecases.games.GetWishlist;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Game>> _gamesList = new MutableLiveData<>();
    public LiveData<List<Game>> getGamesList() { return _gamesList; }

    private final MutableLiveData<GameDetails> _gameDetails = new MutableLiveData<>();
    public LiveData<GameDetails> getSelectedGameDetails() { return _gameDetails; }

    private final MutableLiveData<String> _statusText = new MutableLiveData<>();
    public LiveData<String> getStatusText() { return _statusText; }

    private final GetAllGames getAllGamesUseCase;
    private final SearchGames searchGamesUseCase;
    private final GetUserCollection getUserCollectionUseCase;
    private final GetWishlist getWishlistUseCase;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public MainViewModel(GetAllGames getAllGamesUseCase,
                         GetUserCollection getUserCollectionUseCase,
                         GetWishlist getWishlistUseCase,
                         SearchGames searchGamesUseCase) {
        this.getAllGamesUseCase = getAllGamesUseCase;
        this.getUserCollectionUseCase = getUserCollectionUseCase;
        this.getWishlistUseCase = getWishlistUseCase;
        this.searchGamesUseCase = searchGamesUseCase;
    }

    public void getAllGames() {
        executor.execute(() -> {
            try {
                List<Game> games = getAllGamesUseCase.execute();
                _gamesList.postValue(games);
            } catch (Exception e) {
                _gamesList.postValue(Collections.emptyList());
            }
        });
    }

    public void searchGames(String query) {
        executor.execute(() -> {
            try {
                List<Game> games = searchGamesUseCase.execute(query);
                _gamesList.postValue(games);
            } catch (Exception e) {
                _gamesList.postValue(Collections.emptyList());
            }
        });
    }

    public void loadGameDetails(int gameId) {
        _statusText.postValue("Загрузка деталей...");
        executor.execute(() -> {
            try {
                GameDetails details = getAllGamesUseCase.getRepository().getGameDetails(gameId);
                _gameDetails.postValue(details);
            } catch (Exception e) {
                _statusText.postValue("Ошибка: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdownNow();
    }
}