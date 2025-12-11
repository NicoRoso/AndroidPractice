package com.mirea.nabiulingb.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Импорт для работы с TextView
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView detailsTextView = view.findViewById(R.id.country_details_text);

        Bundle args = getArguments();

        if (args != null) {
            String countryName = args.getString(ListFragment.KEY_COUNTRY_NAME);

            if (countryName != null && !countryName.isEmpty()) {
                String details = String.format("Выбрана страна: %s.\n\nДополнительная информация о стране...", countryName);
                detailsTextView.setText(details);
            }
        }

        return view;
    }
}