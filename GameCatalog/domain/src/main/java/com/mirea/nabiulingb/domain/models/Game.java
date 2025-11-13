package com.mirea.nabiulingb.domain.models;

public class Game {
    private final int id;
    private final String title;
    private final String description;
    private final String genre;
    private final String platform;
    private final String releaseDate;
    private final double rating;
    private final String imageUrl;
    private final Double price;
    private final Integer discount;

    public Game(int id, String title, String description, String genre,
                String platform, String releaseDate, double rating,
                String imageUrl, Double price, Integer discount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.platform = platform;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.price = price;
        this.discount = discount;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public String getPlatform() { return platform; }
    public String getReleaseDate() { return releaseDate; }
    public double getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public Double getPrice() { return price; }
    public Integer getDiscount() { return discount; }
}