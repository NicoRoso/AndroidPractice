package com.mirea.nabiulingb.domain.models;

import java.util.List;

public class Game {
    private final int id;
    private final String title;
    private final String description;
    private final String genre;
    private final String platforms;
    private final String releaseDate;
    private final double rating;
    private final String imageUrl;
    private final String developer;
    private final String publisher;
    private final List<String> shortScreenshots;

    public Game(int id, String title, String description, String genre, String platforms,
                String releaseDate, double rating, String imageUrl,
                String developer, String publisher, List<String> shortScreenshots) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.platforms = platforms;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.developer = developer;
        this.publisher = publisher;
        this.shortScreenshots = shortScreenshots;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public String getImageUrl() { return imageUrl; }
    public String getDeveloper() { return developer; }
    public String getPublisher() { return publisher; }
    public List<String> getShortScreenshots() { return shortScreenshots; }
    public double getRating() { return rating; }
    public String getPlatforms() { return platforms; }
    public String getReleaseDate() { return releaseDate; }
}