package com.mirea.nabiulingb.domain.usecases.games;

import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.repositories.GameRepository;

import java.util.List;

public class SearchGames {
    private final GameRepository gameRepository;

    public SearchGames(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> execute(String query, String genre) {
        return gameRepository.searchGames(query, genre, null);
    }
}
