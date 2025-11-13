package com.mirea.nabiulingb.gamecatalog.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.nabiulingb.data.repositories.AuthRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModel;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModelFactory;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvStatus;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
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
        initRecyclerView();
        setListeners();
        setObservers();

        tvTitle.setText("Game Catalog (" + authRepository.getUserName() + ")");
    }

    private void initViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(this);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    private void initRecyclerView() {
        gameAdapter = new GameAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(gameAdapter);
    }

    private void setObservers() {
        mainViewModel.getGamesList().observe(this, games -> {
            if (games != null && !games.isEmpty()) {
                gameAdapter.setGameList(games);
                recyclerView.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.GONE);
            } else {
                gameAdapter.setGameList(Collections.emptyList());
                recyclerView.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.getStatusText().observe(this, status -> {
            tvStatus.setText(status);
        });
    }

    private void initUI() {
        tvTitle = findViewById(R.id.tvTitle);
        tvStatus = findViewById(R.id.tvStatus);
        recyclerView = findViewById(R.id.recyclerView);
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
        final String TEST_QUERY = "RPG";
        mainViewModel.searchGames(TEST_QUERY);;
    }

    private void getCollections() {
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