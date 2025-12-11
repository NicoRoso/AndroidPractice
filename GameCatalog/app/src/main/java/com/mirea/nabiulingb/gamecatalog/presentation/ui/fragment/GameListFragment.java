package com.mirea.nabiulingb.gamecatalog.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.gamecatalog.presentation.ui.GameAdapter;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModel;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModelFactory;

import java.util.List;

public class GameListFragment extends Fragment {

    private TextView tvTitle;
    private TextView tvStatus;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private Button btnGetAllGames, btnSearchGames, btnGetCollections, btnGetWishlist;

    private MainViewModel mainViewModel;

    private final int MOCK_USER_ID = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        setupRecyclerView();
        setupViewModel();

        setListeners();

        observeViewModel();
    }

    private void initViews(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        tvStatus = view.findViewById(R.id.tvStatus);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnGetAllGames = view.findViewById(R.id.btnGetAllGames);
        btnSearchGames = view.findViewById(R.id.btnSearchGames);
        btnGetCollections = view.findViewById(R.id.btnGetCollections);
        btnGetWishlist = view.findViewById(R.id.btnGetWishlist);
    }

    private void setupRecyclerView() {
        gameAdapter = new GameAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(gameAdapter);
    }

    private void setupViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(requireContext());
        mainViewModel = new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
    }

    private void observeViewModel() {
        mainViewModel.getGamesList().observe(getViewLifecycleOwner(), games -> {
            if (games != null && !games.isEmpty()) {
                gameAdapter.setGameList(games);
                recyclerView.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.GONE);
            } else {
                gameAdapter.setGameList(null);
                recyclerView.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.getStatusText().observe(getViewLifecycleOwner(), status -> {
            if (status != null && !status.isEmpty()) {
                tvStatus.setText(status);
            }
        });
    }

    private void setListeners() {
        btnGetAllGames.setOnClickListener(v -> getAllGames());
        btnSearchGames.setOnClickListener(v -> searchGames());
        btnGetCollections.setOnClickListener(v -> getCollections());
        btnGetWishlist.setOnClickListener(v -> getWishlist());
    }

    private void getAllGames() {
        tvStatus.setText("Загрузка всех игр...");
        mainViewModel.getAllGames();
    }

    private void searchGames() {
        final String TEST_QUERY = "RPG";
        tvStatus.setText("Поиск игр по запросу: '" + TEST_QUERY + "'...");
        mainViewModel.searchGames(TEST_QUERY);
    }

    private void getCollections() {
        tvStatus.setText("Получение коллекций...");
        mainViewModel.getCollections(MOCK_USER_ID);
    }

    private void getWishlist() {
        tvStatus.setText("Получение списка желаний...");
        mainViewModel.getWishlist(MOCK_USER_ID);
    }
}