package com.mirea.nabiulingb.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "games")
public class GameEntity {
    @PrimaryKey
    private final int id;
    private final String title;
    private final String description;
    private final String genre;
    private final String platform;
    private final double rating;
    private final String imageUrl;

    public GameEntity(int id, String title, String description, String genre,
                      String platform, double rating, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.platform = platform;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public String getPlatform() { return platform; }
    public double getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
}