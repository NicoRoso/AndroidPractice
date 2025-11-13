package com.mirea.nabiulingb.listviewapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String[] books = {
            "Война и мир - Л.Н. Толстой",
            "Преступление и наказание - Ф.М. Достоевский",
            "1984 - Джордж Оруэлл",
            "Мастер и Маргарита - М.А. Булгаков",
            "Как снять отличное видео - Стив Стокман",
            "World of Warcraft. Трилогия Войны Древних: Раскол - Ричард А. Кнаак",
            "Red Dead Redemption. Хорошая, плохая, культовая. Рождение вестерна от Rockstar Games - Ромен Даснуа",
            "Английский в Minecraft. Учим язык с любимой игрой - Аника Брейн",
            "StarCraft: Линия фронта. Том 1 - \n" +
                    "Ричард А. Кнаак,\n" +
                    "Пол Бенджамин\n" +
                    "\n" +
                    "и 4 автора",
            "World of Warcraft. Волчье сердце - Ричард А. Кнаак",
            "Трилогия Halo. Истоки легендарной космической оперы Bungie - Лоик Рале",
            "Фауст - Иоганн Вольфганг Гёте",
            "Декамерон - Джованни Боккаччо",
            "Божественная комедия - Данте Алигьери",
            "Евгений Онегин - А.С. Пушкин",
            "Мертвые души - Н.В. Гоголь",
            "Отцы и дети - И.С. Тургенев",
            "Обломов - И.А. Гончаров",
            "Герой нашего времени - М.Ю. Лермонтов"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksListView = findViewById(R.id.booksListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                books
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setText(String.format("%d. %s", position + 1, getItem(position)));
                return view;
            }
        };

        booksListView.setAdapter(adapter);
    }
}