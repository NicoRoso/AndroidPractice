package com.mirea.nabiulingb.retrofitapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements TodoViewHolder.OnTodoCheckedListener {
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Picasso.Builder builder = new Picasso.Builder(this);
        Picasso picasso = builder.build();
        // Включаем индикаторы, чтобы видеть, откуда грузится картинка:
        // ЗЕЛЕНЫЙ (память), СИНИЙ (диск), КРАСНЫЙ (сеть)
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true); // Включаем логирование
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException e) {
            // Picasso уже был инициализирован, игнорируем
            Log.e("Picasso", "Picasso already initialized: " + e.getMessage());
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        loadTodos();
    }

    private void loadTodos() {
        Call<List<Todo>> call = apiService.getTodos();
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Todo> todos = response.body();

                    for (Todo todo : todos) {
                        String imageUrl = String.format("https://placehold.co/60x60/3CB371/FFFFFF/png?text=ID_%d", todo.getId());
                        todo.setImageUrl(imageUrl);
                    }

                    todoAdapter = new TodoAdapter(MainActivity.this, todos, MainActivity.this);
                    recyclerView.setAdapter(todoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Ошибка загрузки: " + t.getMessage());
            }
        });
    }

    public void onTodoChecked(Todo todo, boolean isChecked, int position) {
        Todo updatedTodo = new Todo(todo.getUserId(), todo.getId(), todo.getTitle(),
                isChecked, todo.getImageUrl());

        if (todoAdapter != null) {
            todoAdapter.updateItem(position, updatedTodo);
        }

        Call<Todo> call = apiService.updateTodo(todo.getId(), updatedTodo);
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(MainActivity.this, "Статус обновлен!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка обновления", Toast.LENGTH_SHORT).show();
            }
        });
    }
}