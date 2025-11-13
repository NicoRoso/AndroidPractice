package com.mirea.nabiulingb.domain.domain.usecases.games;

import com.mirea.nabiulingb.domain.domain.models.Game;
import com.mirea.nabiulingb.domain.domain.repositories.GameRepository;

import java.util.List;

public class SearchGames {
    private final GameRepository gameRepository;

    public SearchGames(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> execute(String query) {
        return gameRepository.searchGames(query);
    }
}
