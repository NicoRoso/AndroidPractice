package com.mirea.nabiulingb.gamecatalog.data.repositories;

import com.mirea.nabiulingb.gamecatalog.domain.models.Collection;
import com.mirea.nabiulingb.gamecatalog.domain.models.Game;
import com.mirea.nabiulingb.gamecatalog.domain.repositories.CollectionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionRepositoryImpl implements CollectionRepository {

    private final List<Collection> testCollections = createTestCollections();

    @Override
    public List<Collection> getUserCollections(int userId) {
        List<Collection> userCollections = new ArrayList<>();
        for (Collection collection : testCollections) {
            if (collection.getOwnerId() == userId) {
                userCollections.add(collection);
            }
        }
        return userCollections;
    }

    @Override
    public boolean addGameToCollection(int collectionId, int gameId) {
        return true;
    }

    @Override
    public boolean updateGameStatus(int collectionId, int gameId, String status) {
        return true;
    }

    @Override
    public boolean removeGameFromCollection(int collectionId, int gameId) {
        return true;
    }

    private List<Collection> createTestCollections() {
        List<Collection> collections = new ArrayList<>();

        Game witcher = new Game(1, "The Witcher 3: Wild Hunt",
                "Action RPG", "RPG", "PC", "2015-05-19", 9.7,
                "https://example.com/witcher3.jpg", null, null);

        Game cyberpunk = new Game(2, "Cyberpunk 2077",
                "Open-world RPG", "RPG", "PC", "2020-12-10", 8.5,
                "https://example.com/cyberpunk.jpg", null, null);

        Game rdr2 = new Game(3, "Red Dead Redemption 2",
                "Western action", "Action", "PC", "2018-10-26", 9.8,
                "https://example.com/rdr2.jpg", null, null);

        Game batman = new Game(6, "Batman: Arkham Knight",
                "Action-adventure Batman game", "Action", "PC, PS4", "2015-06-23", 9.2,
                "https://example.com/batman_arkham_knight.jpg", null, null);

        collections.add(new Collection(1, "My RPG Collection",
                "All my favorite RPG games", Arrays.asList(witcher, cyberpunk),
                true, 1));

        collections.add(new Collection(2, "Action Games",
                "Best action games", Arrays.asList(rdr2), true, 1));

        collections.add(new Collection(3, "Completed Games",
                "Games I've finished", Arrays.asList(witcher), false, 2));

        collections.add(new Collection(2, "Action Games",
                "Best action games", Arrays.asList(rdr2, batman), true, 1));

        collections.add(new Collection(3, "Completed Games",
                "Games I've finished", Arrays.asList(witcher, batman), false, 2));

        return collections;
    }
}