package com.mirea.nabiulingb.gamecatalog.presentation.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mirea.nabiulingb.data.local.AppDatabase;
import com.mirea.nabiulingb.data.remote.api.FakeGameApiService;
import com.mirea.nabiulingb.data.repositories.CollectionRepositoryImpl;
import com.mirea.nabiulingb.data.repositories.GameRepositoryImpl;
import com.mirea.nabiulingb.data.repositories.WishlistRepositoryImpl;
import com.mirea.nabiulingb.domain.repositories.CollectionRepository;
import com.mirea.nabiulingb.domain.repositories.GameRepository;
import com.mirea.nabiulingb.domain.repositories.WishlistRepository;
import com.mirea.nabiulingb.domain.usecases.collections.GetUserCollection;
import com.mirea.nabiulingb.domain.usecases.games.GetAllGames;
import com.mirea.nabiulingb.domain.usecases.games.GetWishlist;

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
                    new FakeGameApiService()
            );
            CollectionRepository collectionRepository = new CollectionRepositoryImpl(); // Создание CollectionRepository
            WishlistRepository wishlistRepository = new WishlistRepositoryImpl();       // Создание WishlistRepository

            GetAllGames getAllGamesUseCase = new GetAllGames(gameRepository);
            GetUserCollection getUserCollectionUseCase = new GetUserCollection(collectionRepository); // Создание GetUserCollection
            GetWishlist getWishlistUseCase = new GetWishlist(wishlistRepository);                     // Создание GetWishlist

            return (T) new MainViewModel(getAllGamesUseCase, getUserCollectionUseCase, getWishlistUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}