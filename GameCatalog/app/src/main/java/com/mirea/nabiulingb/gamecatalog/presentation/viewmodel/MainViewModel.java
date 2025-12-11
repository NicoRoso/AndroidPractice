package com.mirea.nabiulingb.gamecatalog.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mirea.nabiulingb.domain.models.Collection;
import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.models.WishlistItem;
import com.mirea.nabiulingb.domain.usecases.collections.GetUserCollection;
import com.mirea.nabiulingb.domain.usecases.games.GetAllGames;
import com.mirea.nabiulingb.domain.usecases.games.GetWishlist;
import com.mirea.nabiulingb.domain.usecases.games.SearchGames;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Game>> _gamesList = new MutableLiveData<>();
    public LiveData<List<Game>> getGamesList() {
        return _gamesList;
    }

    private final MutableLiveData<String> _statusText = new MutableLiveData<>();
    public LiveData<String> getStatusText() {
        return _statusText;
    }

    private final GetAllGames getAllGamesUseCase;
    private final GetUserCollection getUserCollectionUseCase;
    private final GetWishlist getWishlistUseCase;
    private final SearchGames searchGamesUseCase;

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
        _statusText.setValue("Загрузка всех игр...");

        executor.execute(() -> {
            try {
                List<Game> games = getAllGamesUseCase.execute();
                _gamesList.postValue(games);
                _statusText.postValue("Загружено игр: " + games.size());
            } catch (Exception e) {
                _statusText.postValue("Ошибка при получении всех игр: " + e.getMessage());
                _gamesList.postValue(Collections.emptyList());
            }
        });
    }

    public void getCollections(int userId) {
        _statusText.setValue("Получение коллекций пользователя " + userId + "...");

        executor.execute(() -> {
            try {
                List<Collection> collections = getUserCollectionUseCase.execute(userId);
                StringBuilder result = new StringBuilder("Коллекции (" + collections.size() + "):\n");
                for (Collection collection : collections) {
                    result.append("• ").append(collection.getName())
                            .append(" - игр: ").append(collection.getGames().size()).append("\n");
                }
                _statusText.postValue(result.toString());
            } catch (Exception e) {
                _statusText.postValue("Ошибка при получении коллекций: " + e.getMessage());
            }
        });
    }

    public void getWishlist(int userId) {
        _statusText.setValue("Получение списка желаний пользователя " + userId + "...");

        executor.execute(() -> {
            try {
                List<WishlistItem> wishlist = getWishlistUseCase.execute(userId);
                StringBuilder result = new StringBuilder("Список желаний (" + wishlist.size() + "):\n");
                for (WishlistItem item : wishlist) {
                    result.append("• ").append(item.getGame().getTitle())
                            .append(" - добавлено: ").append(item.getAddedDate()).append("\n");
                }
                _statusText.postValue(result.toString());
            } catch (Exception e) {
                _statusText.postValue("Ошибка при получении списка желаний: " + e.getMessage());
            }
        });
    }

    public void searchGames(String query) {
        _statusText.setValue("Поиск игр по запросу: '" + query + "'...");
        _gamesList.setValue(Collections.emptyList());

        executor.execute(() -> {
            try {
                List<Game> games = searchGamesUseCase.execute(query);
                _gamesList.postValue(games);
                _statusText.postValue("Результатов поиска по '" + query + "': " + games.size());
            } catch (Exception e) {
                _statusText.postValue("Ошибка при поиске игр: " + e.getMessage());
                _gamesList.postValue(Collections.emptyList());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdownNow();
    }
}