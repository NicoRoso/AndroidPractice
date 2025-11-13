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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> _resultText = new MutableLiveData<>();
    public LiveData<String> getResultText() {
        return _resultText;
    }

    private final GetAllGames getAllGamesUseCase;
    private final GetUserCollection getUserCollectionUseCase;
    private final GetWishlist getWishlistUseCase;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public MainViewModel(GetAllGames getAllGamesUseCase,
                         GetUserCollection getUserCollectionUseCase,
                         GetWishlist getWishlistUseCase) {
        this.getAllGamesUseCase = getAllGamesUseCase;
        this.getUserCollectionUseCase = getUserCollectionUseCase;
        this.getWishlistUseCase = getWishlistUseCase;
    }

    public void getAllGames() {
        _resultText.setValue("Загрузка всех игр...");
        executor.execute(() -> {
            try {
                List<Game> games = getAllGamesUseCase.execute();
                StringBuilder result = new StringBuilder("Каталог игр (" + games.size() + "):\\n");
                for (Game game : games) {
                    result.append("• ").append(game.getTitle())
                            .append(" (").append(game.getGenre()).append(")")
                            .append(" - Оценка: ").append(game.getRating())
                            .append("\\n");
                }
                _resultText.postValue(result.toString());
            } catch (Exception e) {
                _resultText.postValue("Ошибка при получении игр: " + e.getMessage());
            }
        });
    }

    public void getCollections(int userId) {
        _resultText.setValue("Загрузка коллекций...");
        executor.execute(() -> {
            try {
                List<Collection> collections = getUserCollectionUseCase.execute(userId);
                StringBuilder result = new StringBuilder("Мои коллекции (" + collections.size() + "):\\n");
                for (Collection collection : collections) {
                    result.append("• ").append(collection.getName())
                            .append(" (").append(collection.getGames().size()).append(" игр)\\n");
                }
                _resultText.postValue(result.toString());
            } catch (Exception e) {
                _resultText.postValue("Ошибка при получении коллекций: " + e.getMessage());
            }
        });
    }

    public void getWishlist(int userId) {
        _resultText.setValue("Загрузка списка желаний...");
        executor.execute(() -> {
            try {
                List<WishlistItem> wishlist = getWishlistUseCase.execute(userId);
                StringBuilder result = new StringBuilder("Список желаний (" + wishlist.size() + "):\\n");
                for (WishlistItem item : wishlist) {
                    result.append("• ").append(item.getGame().getTitle())
                            .append(" - добавлено: ").append(item.getAddedDate()).append("\\n");
                }
                _resultText.postValue(result.toString());
            } catch (Exception e) {
                _resultText.postValue("Ошибка при получении списка желаний: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}