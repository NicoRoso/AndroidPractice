package com.mirea.nabiulingb.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mirea.nabiulingb.data.local.dao.GameDao;
import com.mirea.nabiulingb.data.local.entities.GameEntity;

@Database(entities = {GameEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GameDao gameDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "game_catalog_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}