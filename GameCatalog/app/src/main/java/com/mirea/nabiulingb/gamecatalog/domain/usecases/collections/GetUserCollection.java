package com.mirea.nabiulingb.gamecatalog.domain.usecases.collections;

import com.mirea.nabiulingb.gamecatalog.domain.models.Collection;
import com.mirea.nabiulingb.gamecatalog.domain.repositories.CollectionRepository;

import java.util.List;

public class GetUserCollection {
    private final CollectionRepository collectionRepository;

    public GetUserCollection(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public List<Collection> execute(int userId) {
        return collectionRepository.getUserCollections(userId);
    }
}
