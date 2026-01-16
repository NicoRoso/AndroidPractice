package com.mirea.nabiulingb.gamecatalog.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.gamecatalog.presentation.ui.GameAdapter;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModel;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModelFactory;

public class SearchFragment extends Fragment {

    private EditText etSearchQuery;
    private Button btnSearchAndFilter;
    private TextView tvSearchStatus;
    private RecyclerView rvSearchResults;
    private Spinner spinnerGenres;
    private GameAdapter gameAdapter;
    private MainViewModel mainViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        initViews(view);
        setupViewModel();
        setupRecyclerView();
        observeViewModel();
        setListeners();
    }

    private void initViews(View view) {
        etSearchQuery = view.findViewById(R.id.etSearchQuery);
        btnSearchAndFilter = view.findViewById(R.id.btnSearchAndFilter);
        tvSearchStatus = view.findViewById(R.id.tvSearchStatus);
        rvSearchResults = view.findViewById(R.id.rvSearchResults);
        spinnerGenres = view.findViewById(R.id.spinnerGenres);
    }

    private void setupViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(requireContext());
        mainViewModel = new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
    }

    private void setupRecyclerView() {
        gameAdapter = new GameAdapter();
        gameAdapter.setOnGameClickListener(game -> {
            Bundle args = new Bundle();
            args.putInt("gameId", game.getId());
            navController.navigate(R.id.gameFragment, args);
        });

        rvSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSearchResults.setAdapter(gameAdapter);
    }

    private void observeViewModel() {
        mainViewModel.getGamesList().observe(getViewLifecycleOwner(), games -> {
            if (games != null && !games.isEmpty()) {
                gameAdapter.setGameList(games);
                rvSearchResults.setVisibility(View.VISIBLE);
                tvSearchStatus.setVisibility(View.GONE);
            } else if (gameAdapter.getItemCount() > 0) {
                gameAdapter.setGameList(null);
                rvSearchResults.setVisibility(View.GONE);
                tvSearchStatus.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.getStatusText().observe(getViewLifecycleOwner(), status -> {
            if (status != null && (status.startsWith("Результатов поиска") || status.startsWith("Поиск игр по запросу") || status.startsWith("Ошибка при поиске"))) {
                tvSearchStatus.setText(status);
                tvSearchStatus.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setListeners() {
        btnSearchAndFilter.setOnClickListener(v -> performSearch());

        etSearchQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                performSearch();
                return true;
            }
            return false;
        });
    }

    private void performSearch() {
        String query = etSearchQuery.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(requireContext(), "Введите запрос для поиска", Toast.LENGTH_SHORT).show();
            return;
        }

        mainViewModel.searchGames(query);
    }
}