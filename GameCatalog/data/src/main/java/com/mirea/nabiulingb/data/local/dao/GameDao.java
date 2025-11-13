package com.mirea.nabiulingb.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mirea.nabiulingb.data.local.entities.GameEntity;

import java.util.List;

@Dao
public interface GameDao {
    @Query("SELECT * FROM games")
    List<GameEntity> getAllGames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GameEntity> games);

    @Query("SELECT * FROM games WHERE id = :gameId")
    GameEntity getGameById(int gameId);
}