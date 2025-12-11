package com.mirea.nabiulingb.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ListFragment extends Fragment {

    public static final String KEY_COUNTRY_NAME = "selected_country_name";

    private final String[] countries = new String[] {
            "Россия",
            "США",
            "Китай",
            "Германия",
            "Франция",
            "Япония",
            "Бразилия",
            "Индия"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = view.findViewById(R.id.countries_list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                countries
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = countries[position];

                Bundle args = new Bundle();
                args.putString(KEY_COUNTRY_NAME, selectedCountry);

                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(args);

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, detailsFragment, null)
                        .addToBackStack(null)
                        .commit();

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).setFragmentShown(false);
                }
            }
        });

        return view;
    }
}