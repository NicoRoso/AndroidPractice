package com.mirea.nabiulingb.data.repositories;

import com.mirea.nabiulingb.data.local.dao.GameDao;
import com.mirea.nabiulingb.data.local.entities.GameEntity;
import com.mirea.nabiulingb.data.remote.api.GameApiService;
import com.mirea.nabiulingb.data.remote.models.GameListResponse;
import com.mirea.nabiulingb.data.remote.models.GameRemoteModel;
import com.mirea.nabiulingb.domain.models.Game;
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
        initializeDatabaseWithTestData();
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
                entity.getPrice(),
                entity.getDiscount()
        );
    }

    private List<Game> mapEntitiesToDomains(List<GameEntity> entities) {
        List<Game> games = new ArrayList<>();
        for (GameEntity entity : entities) {
            games.add(mapEntityToDomain(entity));
        }
        return games;
    }

    private GameEntity mapRemoteToEntity(GameRemoteModel remote) {
        return new GameEntity(
                remote.getId(),
                remote.getTitle(),
                remote.getDescription(),
                remote.getGenre(),
                remote.getPlatform(),
                remote.getReleaseDate(),
                remote.getRating(),
                remote.getImageUrl(),
                remote.getPrice(),
                remote.getDiscount()
        );
    }

    private Game mapRemoteToDomain(GameRemoteModel remote) {
        return new Game(
                remote.getId(),
                remote.getTitle(),
                remote.getDescription(),
                remote.getGenre(),
                remote.getPlatform(),
                remote.getReleaseDate(),
                remote.getRating(),
                remote.getImageUrl(),
                remote.getPrice(),
                remote.getDiscount()
        );
    }

    private List<GameEntity> mapRemoteToEntities(List<GameRemoteModel> remoteModels) {
        List<GameEntity> entities = new ArrayList<>();
        for (GameRemoteModel remote : remoteModels) {
            entities.add(mapRemoteToEntity(remote));
        }
        return entities;
    }

    private List<Game> mapRemoteToDomains(List<GameRemoteModel> remoteModels) {
        List<Game> domains = new ArrayList<>();
        for (GameRemoteModel remote : remoteModels) {
            domains.add(mapRemoteToDomain(remote));
        }
        return domains;
    }

    @Override
    public List<Game> getAllGames() {
        try {
            Response<GameListResponse> response =
                    apiService.getAllGames(GameApiService.API_KEY).execute();

            if (response.isSuccessful() && response.body() != null) {
                List<GameRemoteModel> remoteGames = response.body().getResults();

                List<GameEntity> entitiesToCache = mapRemoteToEntities(remoteGames);
                gameDao.insertAll(entitiesToCache);

                return mapRemoteToDomains(remoteGames);
            } else {
                String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                System.err.println("API Error " + response.code() + ": " + errorBody);
            }
        } catch (IOException e) {
            System.err.println("API Error. Falling back to local cache: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GameEntity> entities = gameDao.getAllGames();
        return mapEntitiesToDomains(entities);
    }

    @Override
    public List<Game> searchGames(String query) {
        try {
            Response<GameListResponse> response =
                    apiService.searchGames(GameApiService.API_KEY, query).execute();

            if (response.isSuccessful() && response.body() != null) {
                return mapRemoteToDomains(response.body().getResults());
            } else {
                String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                System.err.println("Search API Error " + response.code() + ": " + errorBody);
            }
        } catch (IOException e) {
            System.err.println("API Error during search: " + e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public List<Game> filterGames(String genre, String platform) {
        return getAllGames();
    }


    @Override
    public Game getGameDetails(int gameId) {
        GameEntity entity = gameDao.getGameById(gameId);
        return entity != null ? mapEntityToDomain(entity) : null;
    }

    private void initializeDatabaseWithTestData() {
        new Thread(() -> {
            if (gameDao.getAllGames().isEmpty()) {
                List<GameEntity> entities = createTestGameEntities();
                gameDao.insertAll(entities);
            }
        }).start();
    }
    private List<GameEntity> createTestGameEntities() {
        List<GameEntity> games = new ArrayList<>();

        games.add(new GameEntity(1, "The Witcher 3: Wild Hunt",
                "Action RPG игра в открытом мире в фэнтези", "RPG",
                "PC, PS4, Xbox One", "2015-05-19", 9.7,
                "https://example.com/witcher3.jpg", 39.99, 20));

        games.add(new GameEntity(2, "Cyberpunk 2077",
                "Action-adventure RPG с открытым миром", "RPG",
                "PC, PS5, Xbox Series X", "2020-12-10", 8.5,
                "https://example.com/cyberpunk.jpg", 59.99, null));

        games.add(new GameEntity(3, "Red Dead Redemption 2",
                "Action-adventure игра в сеттинге дикого запада", "Action",
                "PC, PS4, Xbox One", "2018-10-26", 9.8,
                "https://example.com/rdr2.jpg", 49.99, 30));

        games.add(new GameEntity(4, "The Last of Us Part II",
                "Action-adventure survival horror", "Action",
                "PS4, PS5", "2020-06-19", 9.0,
                "https://example.com/tlou2.jpg", 69.99, 15));

        games.add(new GameEntity(5, "Minecraft",
                "Sandbox construction game", "Sandbox",
                "PC, Mobile, Console", "2011-11-18", 9.5,
                "https://example.com/minecraft.jpg", 26.95, null));

        games.add(new GameEntity(6, "Batman: Arkham Knight",
                "Action-adventure игра про Бэтмена и завершающая игра в серии Аркхем", "Action",
                "PC, PS4, Xbox One", "2015-06-23", 9.2,
                "https://example.com/batman_arkham_knight.jpg", 29.99, 40));

        return games;
    }
}