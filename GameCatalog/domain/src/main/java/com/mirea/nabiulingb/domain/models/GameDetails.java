package com.mirea.nabiulingb.domain.models;

import java.io.Serializable;

public class GameDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String title;
    private final String description;
    private final String slug;
    private final String nameOriginal;
    private final Integer metacritic;
    private final String backgroundImage;
    private final String backgroundImageAdditional;
    private final String website;
    private final double rating;
    private final Integer ratingTop;
    private final Integer playtime;
    private final String releaseDate;
    private final String genre;
    private final String platform;

    public GameDetails(int id, String title, String description, String slug,
                       String nameOriginal, Integer metacritic, String backgroundImage,
                       String backgroundImageAdditional, String website, double rating,
                       Integer ratingTop, Integer playtime, String releaseDate, String genre, String platform) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.slug = slug;
        this.nameOriginal = nameOriginal;
        this.metacritic = metacritic;
        this.backgroundImage = backgroundImage;
        this.backgroundImageAdditional = backgroundImageAdditional;
        this.website = website;
        this.rating = rating;
        this.ratingTop = ratingTop;
        this.playtime = playtime;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.platform = platform;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSlug() { return slug; }
    public String getNameOriginal() { return nameOriginal; }
    public Integer getMetacritic() { return metacritic; }
    public String getBackgroundImage() { return backgroundImage; }
    public String getBackgroundImageAdditional() { return backgroundImageAdditional; }
    public String getWebsite() { return website; }
    public double getRating() { return rating; }
    public Integer getRatingTop() { return ratingTop; }
    public Integer getPlaytime() { return playtime; }
    public String getReleaseDate() { return releaseDate; }
    public String getGenre() { return genre; }
    public String getPlatform() { return platform; }
}