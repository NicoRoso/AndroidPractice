package com.mirea.nabiulingb.gamecatalog.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.nabiulingb.data.repositories.AuthRepositoryImpl;
import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.gamecatalog.presentation.ui.LoginActivity;

public class ProfileFragment extends Fragment {

    private TextView tvUsername;
    private TextView tvIsLoggedIn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvIsLoggedIn = view.findViewById(R.id.tvIsLoggedIn);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        displayUserInfo();

        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void displayUserInfo() {
        AuthRepositoryImpl authRepository = new AuthRepositoryImpl(requireContext());

        if (authRepository.isUserLoggedIn()) {
            String userEmail = authRepository.getUserEmail();

            tvUsername.setText(userEmail);
            tvIsLoggedIn.setText("Авторизован");

            tvIsLoggedIn.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            tvUsername.setText("Гость");
            tvIsLoggedIn.setText("Не авторизован");
            tvIsLoggedIn.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }

    private void logoutUser() {
        AuthRepositoryImpl authRepository = new AuthRepositoryImpl(requireContext());
        authRepository.logout();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}