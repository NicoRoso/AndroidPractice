package com.mirea.nabiulingb.domain.usecases.games;

import com.mirea.nabiulingb.domain.models.GameDetails;
import com.mirea.nabiulingb.domain.repositories.GameRepository;

public class GetGameDetails {
    private final GameRepository gameRepository;

    public GetGameDetails(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameDetails execute(int gameId) {
        return gameRepository.getGameDetails(gameId);
    }
}