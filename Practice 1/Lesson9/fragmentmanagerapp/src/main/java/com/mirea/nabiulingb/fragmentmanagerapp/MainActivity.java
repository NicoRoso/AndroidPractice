package com.mirea.nabiulingb.fragmentmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private Button switchFragmentButton;
    private boolean isListFragmentShown = true;

    public void setFragmentShown(boolean isList) {
        this.isListFragmentShown = isList;
        if (switchFragmentButton != null) {
            switchFragmentButton.setText(isList ? "Показать Детали" : "Показать Список");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        switchFragmentButton = findViewById(R.id.btn_switch_fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container_view, new ListFragment(), "ListFragmentTag")
                    .commit();
            setFragmentShown(true);
        } else {
            isListFragmentShown = savedInstanceState.getBoolean("isListFragmentShown", true);
            setFragmentShown(isListFragmentShown);
        }

        switchFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = isListFragmentShown ? new DetailsFragment() : new ListFragment();

                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, fragment, null)
                        .addToBackStack(null)
                        .commit();

                setFragmentShown(!isListFragmentShown);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isListFragmentShown", isListFragmentShown);
    }
}