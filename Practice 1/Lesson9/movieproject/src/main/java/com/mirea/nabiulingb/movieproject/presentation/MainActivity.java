package com.mirea.nabiulingb.movieproject.presentation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mirea.nabiulingb.movieproject.R;
import com.mirea.nabiulingb.movieproject.data.repository.MovieRepositoryImpl;
import com.mirea.nabiulingb.movieproject.data.storage.MovieStorage;
import com.mirea.nabiulingb.movieproject.data.storage.NetworkMovieStorage;
import com.mirea.nabiulingb.movieproject.data.storage.impl.SharedPrefMovieStorage;
import com.mirea.nabiulingb.movieproject.data.storage.impl.FakeNetworkMovieStorage;
import com.mirea.nabiulingb.movieproject.domain.repository.MovieRepository;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMovie;
    private TextView textViewMovie;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovie = findViewById(R.id.editTextMovie);
        textViewMovie = findViewById(R.id.textViewMovie);

        MovieStorage movieStorage = new SharedPrefMovieStorage(this);
        MovieRepository movieRepository = new MovieRepositoryImpl(movieStorage);
        NetworkMovieStorage networkStorage = new FakeNetworkMovieStorage();

        MainViewModelFactory factory = new MainViewModelFactory(movieRepository, networkStorage);
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        mainViewModel.getCombinedMovieData().observe(this, s -> {
            textViewMovie.setText(s);
        });

        findViewById(R.id.buttonSaveMovie).setOnClickListener(v -> {
            String movieName = editTextMovie.getText().toString();
            if (!movieName.isEmpty()) {
                mainViewModel.saveMovie(movieName);
            } else {
                textViewMovie.setText("Введите название фильма");
            }
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(v -> {
            textViewMovie.setText("Загрузка комбинированных данных запущена...");
        });
    }
}