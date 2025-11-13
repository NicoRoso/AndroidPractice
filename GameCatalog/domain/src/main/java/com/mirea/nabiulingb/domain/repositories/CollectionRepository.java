package com.mirea.nabiulingb.domain.domain.repositories;

import com.mirea.nabiulingb.domain.domain.models.Collection;

import java.util.List;

public interface CollectionRepository {
    List<Collection> getUserCollections(int userId);
    boolean addGameToCollection(int collectionId, int gameId);
    boolean updateGameStatus(int collectionId, int gameId, String status);
    boolean removeGameFromCollection(int collectionId, int gameId);
}
