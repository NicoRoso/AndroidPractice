package com.mirea.nabiulingb.resultapifragmentapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

public class ReceiverFragment extends Fragment {

    private TextView resultTextView;
    public static final String REQUEST_KEY = "requestKey";
    public static final String BUNDLE_KEY = "key";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(
                REQUEST_KEY,
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String receivedMessage = result.getString(BUNDLE_KEY);
                        if (resultTextView != null && receivedMessage != null) {
                            resultTextView.setText("Получено в Receiver: " + receivedMessage);
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receiver, container, false);
        resultTextView = view.findViewById(R.id.text_view_result);
        return view;
    }
}