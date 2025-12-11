package com.mirea.nabiulingb.gamecatalog.presentation.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mirea.nabiulingb.data.local.AppDatabase;
import com.mirea.nabiulingb.data.remote.api.FakeGameApiService;
import com.mirea.nabiulingb.data.remote.api.RetrofitClient;
import com.mirea.nabiulingb.data.repositories.CollectionRepositoryImpl;
import com.mirea.nabiulingb.data.repositories.GameRepositoryImpl;
import com.mirea.nabiulingb.data.repositories.WishlistRepositoryImpl;
import com.mirea.nabiulingb.domain.repositories.CollectionRepository;
import com.mirea.nabiulingb.domain.repositories.GameRepository;
import com.mirea.nabiulingb.domain.repositories.WishlistRepository;
import com.mirea.nabiulingb.domain.usecases.collections.GetUserCollection;
import com.mirea.nabiulingb.domain.usecases.games.GetAllGames;
import com.mirea.nabiulingb.domain.usecases.games.GetWishlist;
import com.mirea.nabiulingb.domain.usecases.games.SearchGames;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public MainViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            GameRepository gameRepository = new GameRepositoryImpl(
                    AppDatabase.getDatabase(context).gameDao(),
                    RetrofitClient.getGameApiService()
            );
            CollectionRepository collectionRepository = new CollectionRepositoryImpl();
            WishlistRepository wishlistRepository = new WishlistRepositoryImpl();

            GetAllGames getAllGamesUseCase = new GetAllGames(gameRepository);
            GetUserCollection getUserCollectionUseCase = new GetUserCollection(collectionRepository);
            GetWishlist getWishlistUseCase = new GetWishlist(wishlistRepository);
            SearchGames searchGamesUseCase = new SearchGames(gameRepository);

            return (T) new MainViewModel(getAllGamesUseCase, getUserCollectionUseCase, getWishlistUseCase, searchGamesUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}