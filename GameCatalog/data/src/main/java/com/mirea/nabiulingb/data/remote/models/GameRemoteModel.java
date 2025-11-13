package com.mirea.nabiulingb.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class GameRemoteModel {
    @SerializedName("id")
    private final int id;
    @SerializedName("title")
    private final String title;
    @SerializedName("description")
    private final String description;
    @SerializedName("genre")
    private final String genre;
    @SerializedName("platform")
    private final String platform;
    @SerializedName("release_date")
    private final String releaseDate;
    @SerializedName("rating")
    private final double rating;
    @SerializedName("image_url")
    private final String imageUrl;
    @SerializedName("price")
    private final Double price;
    @SerializedName("discount")
    private final Integer discount;

    public GameRemoteModel(int id, String title, String description, String genre,
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