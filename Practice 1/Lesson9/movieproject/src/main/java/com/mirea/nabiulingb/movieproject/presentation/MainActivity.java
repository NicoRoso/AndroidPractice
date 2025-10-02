package com.mirea.nabiulingb.movieproject.presentation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.nabiulingb.movieproject.R;
import com.mirea.nabiulingb.movieproject.data.repository.MovieRepositoryImpl;
import com.mirea.nabiulingb.movieproject.domain.models.Movie;
import com.mirea.nabiulingb.movieproject.domain.repository.MovieRepository;
import com.mirea.nabiulingb.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import com.mirea.nabiulingb.movieproject.domain.usecases.SaveFilmToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMovie;
    private TextView textViewMovie;
    private MovieRepository movieRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMovie = findViewById(R.id.editTextMovie);
        textViewMovie = findViewById(R.id.textViewMovie);

        movieRepository = new MovieRepositoryImpl(this);

        findViewById(R.id.buttonSaveMovie).setOnClickListener(v -> {
            String movieName = editTextMovie.getText().toString();
            Movie movie = new Movie(2, movieName);
            SaveFilmToFavoriteUseCase saveUseCase = new SaveFilmToFavoriteUseCase(movieRepository);
            boolean result = saveUseCase.execute(movie);
            textViewMovie.setText("Сохранено: " + result);
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(v -> {
            GetFavoriteFilmUseCase getUseCase = new GetFavoriteFilmUseCase(movieRepository);
            Movie movie = getUseCase.execute();
            textViewMovie.setText("Фильм: " + movie.getName());
        });
    }
}