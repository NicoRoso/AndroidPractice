package com.mirea.nabiulingb.gamecatalog.presentation.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.gamecatalog.data.repositories.AuthRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.data.repositories.CollectionRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.data.repositories.GameRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.data.repositories.WishlistRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.domain.models.Collection;
import com.mirea.nabiulingb.gamecatalog.domain.models.Game;
import com.mirea.nabiulingb.gamecatalog.domain.models.User;
import com.mirea.nabiulingb.gamecatalog.domain.models.WishlistItem;
import com.mirea.nabiulingb.gamecatalog.domain.usecases.auth.LoginUser;
import com.mirea.nabiulingb.gamecatalog.domain.usecases.collections.GetUserCollection;
import com.mirea.nabiulingb.gamecatalog.domain.usecases.games.GetAllGames;
import com.mirea.nabiulingb.gamecatalog.domain.usecases.games.GetWishlist;
import com.mirea.nabiulingb.gamecatalog.domain.usecases.games.SearchGames;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private GameRepositoryImpl gameRepository;
    private AuthRepositoryImpl authRepository;
    private CollectionRepositoryImpl collectionRepository;
    private WishlistRepositoryImpl wishlistRepository;

    // Use Cases
    private GetAllGames getAllGamesUseCase;
    private SearchGames searchGamesUseCase;
    private LoginUser loginUserUseCase;
    private GetUserCollection getUserCollectionUseCase;
    private GetWishlist getWishlistUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRepositories();
        initUseCases();
        setupUI();
    }

    private void initRepositories() {
        gameRepository = new GameRepositoryImpl();
        authRepository = new AuthRepositoryImpl();
        collectionRepository = new CollectionRepositoryImpl();
        wishlistRepository = new WishlistRepositoryImpl();
    }

    private void initUseCases() {
        getAllGamesUseCase = new GetAllGames(gameRepository);
        searchGamesUseCase = new SearchGames(gameRepository);
        loginUserUseCase = new LoginUser(authRepository);
        getUserCollectionUseCase = new GetUserCollection(collectionRepository);
        getWishlistUseCase = new GetWishlist(wishlistRepository);
    }

    private void setupUI() {
        tvResult = findViewById(R.id.tvResult);

        Button btnGetAllGames = findViewById(R.id.btnGetAllGames);
        Button btnSearchGames = findViewById(R.id.btnSearchGames);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnGetCollections = findViewById(R.id.btnGetCollections);
        Button btnGetWishlist = findViewById(R.id.btnGetWishlist);

        btnGetAllGames.setOnClickListener(v -> getAllGames());
        btnSearchGames.setOnClickListener(v -> searchGames());
        btnLogin.setOnClickListener(v -> loginUser());
        btnGetCollections.setOnClickListener(v -> getUserCollections());
        btnGetWishlist.setOnClickListener(v -> getWishlist());
    }

    private void getAllGames() {
        executor.execute(() -> {
            try {
                List<Game> games = getAllGamesUseCase.execute();
                runOnUiThread(() -> {
                    StringBuilder result = new StringBuilder("Все игры (" + games.size() + "):\n");
                    for (Game game : games) {
                        result.append("• ").append(game.getTitle())
                                .append(" (").append(game.getGenre()).append(")\n");
                    }
                    tvResult.setText(result.toString());
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    private void searchGames() {
        executor.execute(() -> {
            try {
                List<Game> games = searchGamesUseCase.execute("RPG");
                runOnUiThread(() -> {
                    StringBuilder result = new StringBuilder("Найдено RPG игр (" + games.size() + "):\n");
                    for (Game game : games) {
                        result.append("• ").append(game.getTitle())
                                .append(" - ").append(game.getRating()).append("/10\n");
                    }
                    tvResult.setText(result.toString());
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    private void loginUser() {
        executor.execute(() -> {
            try {
                User user = loginUserUseCase.execute("test@test.com", "password");
                runOnUiThread(() -> {
                    if (user != null) {
                        tvResult.setText("Авторизация успешна!\n" +
                                "Пользователь: " + user.getUsername() + "\n" +
                                "Email: " + user.getEmail());
                    } else {
                        tvResult.setText("Ошибка авторизации!");
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    private void getUserCollections() {
        executor.execute(() -> {
            try {
                List<Collection> collections = getUserCollectionUseCase.execute(1);
                runOnUiThread(() -> {
                    StringBuilder result = new StringBuilder("Мои коллекции (" + collections.size() + "):\n");
                    for (Collection collection : collections) {
                        result.append("• ").append(collection.getName())
                                .append(" (").append(collection.getGames().size()).append(" игр)\n");
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
                    StringBuilder result = new StringBuilder("Список желаний (" + wishlist.size() + "):\n");
                    for (WishlistItem item : wishlist) {
                        result.append("• ").append(item.getGame().getTitle())
                                .append(" - добавлено: ").append(item.getAddedDate()).append("\n");
                    }
                    tvResult.setText(result.toString());
                });
            } catch (Exception e) {
                runOnUiThread(() -> tvResult.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}