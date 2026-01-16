package com.mirea.nabiulingb.data.repositories;

import com.mirea.nabiulingb.data.local.dao.GameDao;
import com.mirea.nabiulingb.data.local.entities.GameEntity;
import com.mirea.nabiulingb.data.remote.api.GameApiService;
import com.mirea.nabiulingb.data.remote.models.GameDetailsRemoteModel;
import com.mirea.nabiulingb.data.remote.models.GameListResponse;
import com.mirea.nabiulingb.data.remote.models.GameRemoteModel;
import com.mirea.nabiulingb.data.remote.models.GenreRemoteModel;
import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.models.GameDetails;
import com.mirea.nabiulingb.domain.repositories.GameRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Response;

public class GameRepositoryImpl implements GameRepository {

    private final GameDao gameDao;
    private final GameApiService apiService;

    public GameRepositoryImpl(GameDao gameDao, GameApiService apiService) {
        this.gameDao = gameDao;
        this.apiService = apiService;
    }

    @Override
    public List<Game> getAllGames() {
        try {
            Response<GameListResponse> response = apiService.getAllGames(GameApiService.API_KEY).execute();
            if (response.isSuccessful() && response.body() != null) {
                List<Game> domainGames = new ArrayList<>();
                for (GameRemoteModel remote : response.body().getResults()) {
                    domainGames.add(mapRemoteToDomain(remote));
                }
                return domainGames;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<GameEntity> entities = gameDao.getAllGames();
        List<Game> domainGames = new ArrayList<>();
        for (GameEntity entity : entities) {
            domainGames.add(mapEntityToDomain(entity));
        }
        return domainGames;
    }

    @Override
    public List<Game> searchGames(String query) {
        try {
            Response<GameListResponse> response = apiService.searchGames(GameApiService.API_KEY, query).execute();
            if (response.isSuccessful() && response.body() != null) {
                List<Game> results = new ArrayList<>();
                for (GameRemoteModel remote : response.body().getResults()) {
                    results.add(mapRemoteToDomain(remote));
                }
                return results;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Game> filterGames(String genre, String platform) {
        return Collections.emptyList();
    }

    @Override
    public GameDetails getGameDetails(int gameId) {
        try {
            Response<GameDetailsRemoteModel> response = apiService.getGameDetails(gameId, GameApiService.API_KEY).execute();
            if (response.isSuccessful() && response.body() != null) {
                GameDetailsRemoteModel r = response.body();

                StringBuilder genresBuilder = new StringBuilder();
                if (r.getGenres() != null) {
                    for (int i = 0; i < r.getGenres().size(); i++) {
                        genresBuilder.append(r.getGenres().get(i).getName());
                        if (i < r.getGenres().size() - 1) genresBuilder.append(", ");
                    }
                }

                StringBuilder platformsBuilder = new StringBuilder();
                if (r.getPlatforms() != null) {
                    for (int i = 0; i < r.getPlatforms().size(); i++) {
                        platformsBuilder.append(r.getPlatforms().get(i).getPlatform().getName());
                        if (i < r.getPlatforms().size() - 1) platformsBuilder.append(", ");
                    }
                }

                return new GameDetails(
                        r.getId(), r.getTitle(), r.getDescription(), r.getSlug(),
                        r.getNameOriginal(), r.getMetacritic(), r.getBackgroundImage(),
                        r.getBackgroundImageAdditional(), r.getWebsite(), r.getRating(),
                        r.getRatingTop(), r.getPlaytime(), r.getReleased(),
                        genresBuilder.toString(), platformsBuilder.toString()
                );
            }
        } catch (IOException e) { e.printStackTrace(); }

        GameEntity entity = gameDao.getGameById(gameId);
        if (entity != null) {
            return new GameDetails(
                    entity.getId(), entity.getTitle(), entity.getDescription(), "",
                    entity.getTitle(), 0, entity.getImageUrl(), entity.getImageUrl(),
                    "", entity.getRating(), 0, 0, entity.getReleaseDate(),
                    entity.getGenre(), entity.toString()
            );
        }
        return null;
    }

    private Game mapEntityToDomain(GameEntity entity) {
        return new Game(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getGenre(),
                entity.getPlatform(),
                entity.getReleaseDate(),
                entity.getRating(),
                entity.getImageUrl(),
                "Unknown",
                "Unknown",
                Collections.emptyList()
        );
    }

    private Game mapRemoteToDomain(GameRemoteModel remote) {
        StringBuilder genresStr = new StringBuilder();
        if (remote.getGenres() != null) {
            for (GenreRemoteModel g : remote.getGenres()) {
                if (genresStr.length() > 0) genresStr.append(", ");
                genresStr.append(g.getName());
            }
        }

        return new Game(
                remote.getId(),
                remote.getTitle(),
                remote.getDescription(),
                genresStr.toString(),
                remote.getPlatform(),
                remote.getReleaseDate(),
                remote.getRating(),
                remote.getImageUrl(),
                "Unknown",
                "Unknown",
                Collections.emptyList()
        );
    }
}