package com.mirea.nabiulingb.data.repositories;

import com.mirea.nabiulingb.domain.models.Collection;
import com.mirea.nabiulingb.domain.models.Game;
import com.mirea.nabiulingb.domain.repositories.CollectionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public boolean addGameToCollection(int collectionId, int gameId) { return true; }

    @Override
    public boolean updateGameStatus(int collectionId, int gameId, String status) { return true; }

    @Override
    public boolean removeGameFromCollection(int collectionId, int gameId) { return true; }

    private List<Collection> createTestCollections() {
        List<Collection> collections = new ArrayList<>();

        // Добавлено: null, null, Collections.emptyList() для соответствия 11 параметрам
        Game witcher = new Game(1, "The Witcher 3: Wild Hunt",
                "Action RPG", "RPG", "PC", "2015-05-19", 9.7,
                "https://example.com/witcher3.jpg", "39.99", "20", Collections.emptyList());

        Game cyberpunk = new Game(2, "Cyberpunk 2077",
                "Open-world RPG", "RPG", "PC", "2020-12-10", 8.5,
                "https://example.com/cyberpunk.jpg", "59.99", "0", Collections.emptyList());

        Game rdr2 = new Game(3, "Red Dead Redemption 2",
                "Western action", "Action", "PC", "2018-10-26", 9.8,
                "https://example.com/rdr2.jpg", "49.99", "30", Collections.emptyList());

        collections.add(new Collection(1, "My RPG Collection",
                "All my favorite RPG games", Arrays.asList(witcher, cyberpunk),
                true, 1));

        collections.add(new Collection(2, "Action Games",
                "Best action games", Arrays.asList(rdr2), true, 1));

        return collections;
    }
}