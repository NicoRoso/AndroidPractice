package com.mirea.nabiulingb.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameListResponse {
    @SerializedName("count")
    private final int count;

    @SerializedName("results")
    private final List<GameRemoteModel> results;

    public GameListResponse(int count, List<GameRemoteModel> results) {
        this.count = count;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public List<GameRemoteModel> getResults() {
        return results;
    }
}
