package com.mirea.nabiulingb.resultapifragmentapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SenderFragment extends Fragment {

    public static final String REQUEST_KEY = ReceiverFragment.REQUEST_KEY;
    public static final String BUNDLE_KEY = ReceiverFragment.BUNDLE_KEY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sender, container, false);

        EditText inputEditText = view.findViewById(R.id.edit_text_input);
        Button sendButton = view.findViewById(R.id.btn_send_data);

        sendButton.setOnClickListener(v -> {
            String message = inputEditText.getText().toString();

            Bundle result = new Bundle();
            result.putString(BUNDLE_KEY, message);

            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);

            getParentFragmentManager().popBackStack();
        });

        return view;
    }
}