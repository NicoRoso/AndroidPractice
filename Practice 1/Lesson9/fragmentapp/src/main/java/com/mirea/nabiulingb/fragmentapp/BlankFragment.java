package com.mirea.nabiulingb.fragmentapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BlankFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.tv_student_number);

        Bundle args = getArguments();

        if (args != null) {
            int numberStudent = args.getInt("my_number_student", 0);

            if (numberStudent != 0) {
                textView.setText("Мой номер в списке: " + numberStudent);
            } else {
                textView.setText("Номер не передан или равен 0.");
            }
        } else {
            textView.setText("Аргументы не были переданы.");
        }
    }
}