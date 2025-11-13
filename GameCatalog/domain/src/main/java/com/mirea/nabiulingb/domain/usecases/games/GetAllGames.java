package com.mirea.nabiulingb.domain.usecases.games;

import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.repositories.GameRepository;

import java.util.List;

public class GetAllGames {
    private final GameRepository gameRepository;

    public GetAllGames(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> execute() {
        return gameRepository.getAllGames();
    }
}
