package com.mirea.nabiulingb.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mirea.nabiulingb.data.entities.GameEntity;
import com.mirea.nabiulingb.data.local.entities.GameEntity;

import java.util.List;

@Dao
public interface GameDao {
    @Query("SELECT * FROM games")
    List<GameEntity> getAllGames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GameEntity> games);

    @Query("DELETE FROM games")
    void deleteAll();
}