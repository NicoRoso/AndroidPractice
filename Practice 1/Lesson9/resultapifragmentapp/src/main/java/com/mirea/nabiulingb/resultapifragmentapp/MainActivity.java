package com.mirea.nabiulingb.resultapifragmentapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Button btnOpenSender = findViewById(R.id.btn_open_sender);

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container_result, new ReceiverFragment())
                    .commit();
        }

        getSupportFragmentManager().setFragmentResultListener(
                "requestKey",
                this,
                (requestKey, bundle) -> {
                    String result = bundle.getString("key");
                    Log.d("MainActivity", "Activity получила: " + result);
                }
        );

        btnOpenSender.setOnClickListener(v -> {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_result, new SenderFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}