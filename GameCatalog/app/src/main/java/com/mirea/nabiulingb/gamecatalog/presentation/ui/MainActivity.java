package com.mirea.nabiulingb.gamecatalog.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.nabiulingb.data.local.AppDatabase;
import com.mirea.nabiulingb.data.local.dao.GameDao;
import com.mirea.nabiulingb.data.remote.api.FakeGameApiService;
import com.mirea.nabiulingb.data.remote.api.GameApiService;
import com.mirea.nabiulingb.data.repositories.AuthRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.data.repositories.CollectionRepositoryImpl;
import com.mirea.nabiulingb.data.repositories.GameRepositoryImpl;
import com.mirea.nabiulingb.data.repositories.WishlistRepositoryImpl;
import com.mirea.nabiulingb.domain.models.Collection;
import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.models.User;
import com.mirea.nabiulingb.domain.models.WishlistItem;
import com.mirea.nabiulingb.domain.usecases.auth.LoginUser;
import com.mirea.nabiulingb.domain.usecases.collections.GetUserCollection;
import com.mirea.nabiulingb.domain.usecases.games.GetAllGames;
import com.mirea.nabiulingb.domain.usecases.games.GetWishlist;
import com.mirea.nabiulingb.domain.usecases.games.SearchGames;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AuthRepositoryImpl authRepository;
    private ExecutorService executor;
    private TextView tvResult;

    private GetAllGames getAllGamesUseCase;
    private SearchGames searchGamesUseCase;
    private GetUserCollection getCollectionsUseCase;
    private GetWishlist getWishlistUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executor = Executors.newSingleThreadExecutor();

        initDependencies();
        initUI();
    }

    private void initDependencies() {
        GameDao gameDao = AppDatabase.getDatabase(this).gameDao();
        GameApiService apiService = new FakeGameApiService();

        GameRepositoryImpl gameRepository = new GameRepositoryImpl(gameDao, apiService);
        CollectionRepositoryImpl collectionRepository = new CollectionRepositoryImpl();
        WishlistRepositoryImpl wishlistRepository = new WishlistRepositoryImpl();
        authRepository = new AuthRepositoryImpl(this);

        getAllGamesUseCase = new GetAllGames(gameRepository);
        searchGamesUseCase = new SearchGames(gameRepository);
        getCollectionsUseCase = new GetUserCollection(collectionRepository);
        getWishlistUseCase = new GetWishlist(wishlistRepository);
    }

    private void initUI() {
        tvResult = findViewById(R.id.tvResult);
        Button btnGetAllGames = findViewById(R.id.btnGetAllGames);
        Button btnSearchGames = findViewById(R.id.btnSearchGames);
        Button btnGetCollections = findViewById(R.id.btnGetCollections);
        Button btnGetWishlist = findViewById(R.id.btnGetWishlist);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnGetAllGames.setOnClickListener(v -> getAllGames());
        btnSearchGames.setOnClickListener(v -> searchGames("RPG"));
        btnGetCollections.setOnClickListener(v -> getCollections());
        btnGetWishlist.setOnClickListener(v -> getWishlist());
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void getAllGames() {
        executor.execute(() -> {
            try {
                List<Game> games = getAllGamesUseCase.execute();
                runOnUiThread(() -> {
                    StringBuilder result = new StringBuilder("Все игры (" + games.size() + "):\\n");
                    for (Game game : games) {
                        result.append("• ").append(game.getTitle())
                                .append(" (").append(game.getRating()).append(")\\n");
                    }
                    tvResult.setText(result.toString());
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    private void searchGames(String query) {
        executor.execute(() -> {
            try {
                List<Game> games = searchGamesUseCase.execute(query);
                runOnUiThread(() -> {
                    StringBuilder result = new StringBuilder("Поиск игр ('" + query + "') (" + games.size() + "):\\n");
                    for (Game game : games) {
                        result.append("• ").append(game.getTitle())
                                .append(" (").append(game.getGenre()).append(")\\n");
                    }
                    tvResult.setText(result.toString());
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    private void getCollections() {
        executor.execute(() -> {
            try {
                List<Collection> collections = getCollectionsUseCase.execute(1);
                runOnUiThread(() -> {
                    StringBuilder result = new StringBuilder("Мои коллекции (" + collections.size() + "):\\n");
                    for (Collection collection : collections) {
                        result.append("• ").append(collection.getName())
                                .append(" (").append(collection.getGames().size()).append(" игр)\\n");
                    }
                    tvResult.setText(result.toString());
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    private void getWishlist() {
        executor.execute(() -> {
            try {
                List<WishlistItem> wishlist = getWishlistUseCase.execute(1);
                runOnUiThread(() -> {
                    StringBuilder result = new StringBuilder("Список желаний (" + wishlist.size() + "):\\n");
                    for (WishlistItem item : wishlist) {
                        result.append("• ").append(item.getGame().getTitle())
                                .append(" - добавлено: ").append(item.getAddedDate()).append("\\n");
                    }
                    tvResult.setText(result.toString());
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    private void logoutUser() {
        if (authRepository != null) {
            authRepository.logout();
        }

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}