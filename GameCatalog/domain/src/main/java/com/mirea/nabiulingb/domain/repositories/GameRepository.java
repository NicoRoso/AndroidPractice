package com.mirea.nabiulingb.domain.repositories;

import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.models.GameDetails;

import java.util.List;

public interface GameRepository {
    List<Game> getAllGames();
    List<Game> searchGames(String query);
    List<Game> filterGames(String genre, String platform);

    GameDetails getGameDetails(int gameId);
}