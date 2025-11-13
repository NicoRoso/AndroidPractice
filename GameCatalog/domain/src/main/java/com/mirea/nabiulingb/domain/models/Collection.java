package com.mirea.nabiulingb.domain.models;

import java.util.List;

public class Collection {
    private final int id;
    private final String name;
    private final String description;
    private final List<Game> games;
    private final boolean isPublic;
    private final int ownerId;

    public Collection(int id, String name, String description,
                      List<Game> games, boolean isPublic, int ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.games = games;
        this.isPublic = isPublic;
        this.ownerId = ownerId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Game> getGames() { return games; }
    public boolean isPublic() { return isPublic; }
    public int getOwnerId() { return ownerId; }
}