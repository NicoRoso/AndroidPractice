package com.mirea.nabiulingb.gamecatalog.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupViewModel();
        setupRecyclerView();
        observeViewModel();

        getAllGamesOnStart();
    }

    private void initViews(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        tvStatus = view.findViewById(R.id.tvStatus);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setupViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(requireContext());
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
    }

    private void setupRecyclerView() {
        gameAdapter = new GameAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(gameAdapter);
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

    private void getAllGamesOnStart() {
        tvStatus.setText("Загрузка всех игр...");
        mainViewModel.getAllGames();
    }
}