package com.mirea.nabiulingb.domain.domain.repositories;

import com.mirea.nabiulingb.domain.domain.models.Game;

import java.util.List;

public interface GameRepository {
    List<Game> getAllGames();
    List<Game> searchGames(String query);
    List<Game> filterGames(String genre, String platform);
    Game getGameDetails(int gameId);
}