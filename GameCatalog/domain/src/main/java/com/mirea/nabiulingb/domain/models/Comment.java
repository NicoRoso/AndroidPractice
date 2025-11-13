package com.mirea.nabiulingb.domain.models;

public class Comment {
    private final int id;
    private final int gameId;
    private final int userId;
    private final String username;
    private final String text;
    private final int rating;
    private final String date;

    public Comment(int id, int gameId, int userId, String username,
                   String text, int rating, String date) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
        this.username = username;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public int getId() { return id; }
    public int getGameId() { return gameId; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getText() { return text; }
    public int getRating() { return rating; }
    public String getDate() { return date; }
}