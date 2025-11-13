package com.mirea.nabiulingb.gamecatalog.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mirea.nabiulingb.data.repositories.AuthRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModel;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvResult;
    private Button btnGetAllGames, btnSearchGames, btnGetCollections, btnGetWishlist, btnLogout;

    private AuthRepositoryImpl authRepository;
    private MainViewModel mainViewModel;

    private final int MOCK_USER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authRepository = new AuthRepositoryImpl(this);
        if (!authRepository.isUserLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        initUI();
        initViewModel();
        setListeners();

        tvTitle.setText("Game Catalog (" + authRepository.getUserName() + ")");
    }

    private void initViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(this);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        // Подписываемся на LiveData: когда _resultText обновится, обновится и tvResult
        mainViewModel.getResultText().observe(this, s -> tvResult.setText(s));
    }

    private void initUI() {
        tvTitle = findViewById(R.id.tvTitle);
        tvResult = findViewById(R.id.tvResult);
        btnGetAllGames = findViewById(R.id.btnGetAllGames);
        btnSearchGames = findViewById(R.id.btnSearchGames);
        btnGetCollections = findViewById(R.id.btnGetCollections);
        btnGetWishlist = findViewById(R.id.btnGetWishlist);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void setListeners() {
        btnGetAllGames.setOnClickListener(v -> getAllGames());
        btnSearchGames.setOnClickListener(v -> searchGames());
        btnGetCollections.setOnClickListener(v -> getCollections());
        btnGetWishlist.setOnClickListener(v -> getWishlist());
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void getAllGames() {
        mainViewModel.getAllGames();
    }

    private void searchGames() {
        tvResult.setText("Функционал поиска игр еще не интегрирован в ViewModel.");
    }

    private void getCollections() {
        // Вызываем новый метод ViewModel
        mainViewModel.getCollections(MOCK_USER_ID);
    }

    private void getWishlist() {
        mainViewModel.getWishlist(MOCK_USER_ID);
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
    }
}