package com.mirea.nabiulingb.recyclerviewapp;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewEvents);

        List<HistoricalEvent> events = getHistoricalEvents();

        adapter = new EventAdapter(this, R.layout.list_event_item, events);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            HistoricalEvent event = (HistoricalEvent) parent.getItemAtPosition(position);
            Toast.makeText(MainActivity.this,
                    "Событие: " + event.getTitle() + " (" + event.getYear() + ")",
                    Toast.LENGTH_SHORT).show();
        });
    }

    private List<HistoricalEvent> getHistoricalEvents() {
        List<HistoricalEvent> events = new ArrayList<>();

        events.add(new HistoricalEvent(
                "Великая французская революция",
                "Период радикальных социальных и политических преобразований во Франции",
                "french_revolution",
                1789
        ));

        events.add(new HistoricalEvent(
                "Открытие Америки",
                "Христофор Колумб достиг берегов Америки, начав эпоху Великих географических открытий",
                "columbus",
                1492
        ));

        events.add(new HistoricalEvent(
                "Первая мировая война",
                "Один из самых широкомасштабных вооружённых конфликтов в истории человечества",
                "ww1",
                1914
        ));

        events.add(new HistoricalEvent(
                "Вторая мировая война",
                "Крупнейший вооружённый конфликт в истории человечества",
                "ww2",
                1939
        ));

        events.add(new HistoricalEvent(
                "Полет Гагарина",
                "Первый полет человека в космос - Юрий Гагарин на корабле Восток-1",
                "gagarin",
                1961
        ));

        events.add(new HistoricalEvent(
                "Распад СССР",
                "Прекращение существования Советского Союза как государства",
                "ussr_flag",
                1991
        ));

        events.add(new HistoricalEvent(
                "Падение Западной Римской Империи",
                "Традиционная дата окончания Древнего мира и начала Средневековья",
                "roman_empire_fall",
                476
        ));

        events.add(new HistoricalEvent(
                "Изобретение книгопечатания",
                "Иоганн Гутенберг изобретает печатный станок, что вызывает информационную революцию",
                "printing_press",
                1440
        ));

        events.add(new HistoricalEvent(
                "Первая высадка на Луну",
                "В рамках миссии 'Аполлон-11' Нил Армстронг стал первым человеком на Луне",
                "moon_landing",
                1969
        ));

        events.add(new HistoricalEvent(
                "Создание Всемирной паутины (WWW)",
                "Тим Бернерс-Ли изобретает World Wide Web, изменив коммуникации и информацию",
                "world_wide_web",
                1991
        ));

        events.add(new HistoricalEvent(
                "Чернобыльская катастрофа",
                "Крупнейшая техногенная катастрофа в истории атомной энергетики",
                "chernobyl",
                1986
        ));

        return events;
    }
}