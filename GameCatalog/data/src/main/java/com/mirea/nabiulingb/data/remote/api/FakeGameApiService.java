package com.mirea.nabiulingb.data.remote.api;

import com.mirea.nabiulingb.data.remote.models.GameDetailsRemoteModel;
import com.mirea.nabiulingb.data.remote.models.GameListResponse;
import com.mirea.nabiulingb.data.remote.models.GameRemoteModel;
import com.mirea.nabiulingb.data.remote.models.GenreRemoteModel; // НОВЫЙ ИМПОРТ

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;

public class FakeGameApiService implements GameApiService {

    private final List<GameRemoteModel> mockGames = createMockGames();

    @Override
    public Call<GameListResponse> getAllGames(String apiKey) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        GameListResponse responseBody = new GameListResponse(mockGames.size(), mockGames);
        return new MockCall<>(Response.success(responseBody));
    }

    @Override
    public Call<GameListResponse> searchGames(String apiKey, String query) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        final String lowerCaseQuery = query.toLowerCase();
        List<GameRemoteModel> filteredGames = mockGames.stream()
                .filter(game -> game.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        game.getGenres().stream()
                                .anyMatch(genre -> genre.getName().toLowerCase().contains(lowerCaseQuery)))
                .collect(Collectors.toList());

        GameListResponse responseBody = new GameListResponse(filteredGames.size(), filteredGames);
        return new MockCall<>(Response.success(responseBody));
    }

    private List<GenreRemoteModel> mockGenreList(String name) {
        List<GenreRemoteModel> genres = new ArrayList<>();
        genres.add(new GenreRemoteModel(name.hashCode(), name, name.toLowerCase().replace(" ", "-")));
        return genres;
    }

    @Override
    public Call<GameDetailsRemoteModel> getGameDetails(int gameId, String apiKey) {
        return null;
    }

    private List<GameRemoteModel> createMockGames() {
        List<GameRemoteModel> games = new ArrayList<>();

        games.add(new GameRemoteModel(1, "The Witcher 3: Wild Hunt",
                "Action RPG игра в открытом мире в фэнтези", mockGenreList("RPG"),
                "PC, PS4, Xbox One", "2015-05-19", 9.7,
                "https://example.com/witcher3.jpg", 39.99, 20));

        games.add(new GameRemoteModel(2, "Cyberpunk 2077",
                "Action-adventure RPG с открытым миром", mockGenreList("RPG"),
                "PC, PS5, Xbox Series X", "2020-12-10", 8.5,
                "https://example.com/cyberpunk.jpg", 59.99, null));

        games.add(new GameRemoteModel(3, "Red Dead Redemption 2",
                "Action-adventure игра в сеттинге дикого запада", mockGenreList("Action"),
                "PC, PS4, Xbox One", "2018-10-26", 9.8,
                "https://example.com/rdr2.jpg", 49.99, 30));

        games.add(new GameRemoteModel(4, "The Last of Us Part II",
                "Action-adventure survival horror", mockGenreList("Action"),
                "PS4, PS5", "2020-06-19", 9.0,
                "https://example.com/tlou2.jpg", 69.99, 15));

        games.add(new GameRemoteModel(5, "Minecraft",
                "Sandbox construction game", mockGenreList("Sandbox"),
                "PC, Mobile, Console", "2011-11-18", 9.5,
                "https://example.com/minecraft.jpg", 26.95, null));

        games.add(new GameRemoteModel(6, "Batman: Arkham Knight",
                "Action-adventure игра про Бэтмена и завершающая игра в серии Аркхем", mockGenreList("Action"),
                "PC, PS4, Xbox One", "2015-06-23", 9.2,
                "https://example.com/batman_arkham_knight.jpg", 29.99, 40));

        return games;
    }
}