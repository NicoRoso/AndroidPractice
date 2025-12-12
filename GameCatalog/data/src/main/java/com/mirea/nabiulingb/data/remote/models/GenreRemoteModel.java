package com.mirea.nabiulingb.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class GenreRemoteModel {
    @SerializedName("id")
    private final int id;
    @SerializedName("name")
    private final String name;
    @SerializedName("slug")
    private final String slug;

    public GenreRemoteModel(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
}