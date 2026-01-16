package com.mirea.nabiulingb.gamecatalog.presentation.ui.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mirea.nabiulingb.domain.models.GameDetails;
import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModel;
import com.mirea.nabiulingb.gamecatalog.presentation.viewmodel.MainViewModelFactory;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GameFragment extends Fragment {

    private TextView tvGameTitle, tvGameRating, tvGameGenre, tvGamePlatform, tvReleaseDate, tvGameDescription;
    private ImageView ivGameImage;
    private MainViewModel mainViewModel;
    private int gameId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupViewModel();

        if (getArguments() != null) {
            gameId = getArguments().getInt("gameId", -1);
            if (gameId != -1) {
                mainViewModel.loadGameDetails(gameId);
                observeGameDetails();
            }
        }
    }

    private void initViews(View view) {
        tvGameTitle = view.findViewById(R.id.tvGameTitle);
        ivGameImage = view.findViewById(R.id.ivGameImage);
        tvGameRating = view.findViewById(R.id.tvGameRating);
        tvGameGenre = view.findViewById(R.id.tvGameGenre);
        tvGamePlatform = view.findViewById(R.id.tvGamePlatform);
        tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        tvGameDescription = view.findViewById(R.id.tvGameDescription);
    }

    private void setupViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(requireContext());
        mainViewModel = new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
    }

    private void observeGameDetails() {
        mainViewModel.getSelectedGameDetails().observe(getViewLifecycleOwner(), details -> {
            if (details != null) {
                displayGameInfo(details);
            } else {
                Toast.makeText(getContext(), "Информация не найдена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayGameInfo(GameDetails details) {
        if (details == null) return;

        tvGameTitle.setText(details.getTitle());
        tvGameRating.setText(String.format("Рейтинг: %.1f", details.getRating()));

        tvGameGenre.setText("Жанр: " + (details.getGenre() != null ? details.getGenre() : "Не указан"));

        tvGamePlatform.setText("Платформы: " + (details.getPlatform() != null ? details.getPlatform() : "Платформы неизвестны"));

        String rawDate = details.getReleaseDate();
        if (rawDate != null && !rawDate.isEmpty()) {
            try {
                SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                SimpleDateFormat desiredFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                Date date = incomingFormat.parse(rawDate);
                if (date != null) {
                    tvReleaseDate.setText("Дата выхода: " + desiredFormat.format(date));
                }
            } catch (Exception e) {
                tvReleaseDate.setText("Дата выхода: " + rawDate);
            }
        } else {
            tvReleaseDate.setText("Дата выхода: Неизвестно");
        }

        if (details.getDescription() != null && !details.getDescription().isEmpty()) {
            tvGameDescription.setText(Html.fromHtml(details.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvGameDescription.setText("Описание отсутствует");
        }

        Picasso.get()
                .load(details.getBackgroundImage())
                .placeholder(R.drawable.loading_placeholder)
                .into(ivGameImage);
    }
}