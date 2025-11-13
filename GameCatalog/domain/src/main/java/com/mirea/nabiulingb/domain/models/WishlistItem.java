package com.mirea.nabiulingb.domain.domain.models;

public class WishlistItem {
    private final int id;
    private final Game game;
    private final String addedDate;

    public WishlistItem(int id, Game game, String addedDate) {
        this.id = id;
        this.game = game;
        this.addedDate = addedDate;
    }

    // Getters
    public int getId() { return id; }
    public Game getGame() { return game; }
    public String getAddedDate() { return addedDate; }
}