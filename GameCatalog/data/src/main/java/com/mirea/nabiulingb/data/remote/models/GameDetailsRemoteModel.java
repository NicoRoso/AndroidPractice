package com.mirea.nabiulingb.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameDetailsRemoteModel {
    @SerializedName("id") private int id;
    @SerializedName("name") private String title;
    @SerializedName("description") private String description;
    @SerializedName("slug") private String slug;
    @SerializedName("name_original") private String nameOriginal;
    @SerializedName("metacritic") private Integer metacritic;
    @SerializedName("background_image") private String backgroundImage;
    @SerializedName("background_image_additional") private String backgroundImageAdditional;
    @SerializedName("website") private String website;
    @SerializedName("rating") private double rating;
    @SerializedName("rating_top") private Integer ratingTop;
    @SerializedName("playtime") private Integer playtime;
    @SerializedName("released") private String released;
    @SerializedName("genres") private List<GenreRemoteModel> genres;
    @SerializedName("parent_platforms") private List<ParentPlatformWrapper> platforms;


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
    public String getReleased() { return released; }
    public List<GenreRemoteModel> getGenres() { return genres; }
    public List<ParentPlatformWrapper> getPlatforms() { return platforms; }
}