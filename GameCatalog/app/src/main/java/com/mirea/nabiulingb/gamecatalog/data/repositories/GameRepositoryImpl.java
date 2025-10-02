package com.mirea.nabiulingb.gamecatalog.data.repositories;

import com.mirea.nabiulingb.gamecatalog.domain.models.Game;
import com.mirea.nabiulingb.gamecatalog.domain.repositories.GameRepository;

import java.util.ArrayList;
import java.util.List;

public class GameRepositoryImpl implements GameRepository {

    private final List<Game> testGames = createTestGames();

    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(testGames);
    }

    @Override
    public List<Game> searchGames(String query) {
        List<Game> result = new ArrayList<>();
        for (Game game : testGames) {
            if (game.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    game.getGenre().toLowerCase().contains(query.toLowerCase())) {
                result.add(game);
            }
        }
        return result;
    }

    @Override
    public List<Game> filterGames(String genre, String platform) {
        List<Game> result = new ArrayList<>(testGames);

        if (genre != null && !genre.isEmpty()) {
            result.removeIf(game -> !game.getGenre().equalsIgnoreCase(genre));
        }

        if (platform != null && !platform.isEmpty()) {
            result.removeIf(game -> !game.getPlatform().toLowerCase().contains(platform.toLowerCase()));
        }

        return result;
    }

    @Override
    public Game getGameDetails(int gameId) {
        for (Game game : testGames) {
            if (game.getId() == gameId) {
                return game;
            }
        }
        return null;
    }

    private List<Game> createTestGames() {
        List<Game> games = new ArrayList<>();

        games.add(new Game(1, "The Witcher 3: Wild Hunt",
                "Action RPG игра в открытом мире в фэнтези", "RPG",
                "PC, PS4, Xbox One", "2015-05-19", 9.7,
                "https://example.com/witcher3.jpg", 39.99, 20));

        games.add(new Game(2, "Cyberpunk 2077",
                "Action-adventure RPG с открытым миром", "RPG",
                "PC, PS5, Xbox Series X", "2020-12-10", 8.5,
                "https://example.com/cyberpunk.jpg", 59.99, null));

        games.add(new Game(3, "Red Dead Redemption 2",
                "Action-adventure игра в сеттинге дикого запада", "Action",
                "PC, PS4, Xbox One", "2018-10-26", 9.8,
                "https://example.com/rdr2.jpg", 49.99, 30));

        games.add(new Game(4, "The Last of Us Part II",
                "Action-adventure survival horror", "Action",
                "PS4, PS5", "2020-06-19", 9.0,
                "https://example.com/tlou2.jpg", 69.99, 15));

        games.add(new Game(5, "Minecraft",
                "Sandbox construction game", "Sandbox",
                "PC, Mobile, Console", "2011-11-18", 9.5,
                "https://example.com/minecraft.jpg", 26.95, null));

        games.add(new Game(6, "Batman: Arkham Knight",
                "Action-adventure игра про Бэтмена и завершающая игра в серии Аркхем", "Action",
                "PC, PS4, Xbox One", "2015-06-23", 9.2,
                "https://example.com/batman_arkham_knight.jpg", 29.99, 40));

        return games;
    }
}