package com.mirea.nabiulingb.data.remote.api;

import com.mirea.nabiulingb.data.remote.models.GameRemoteModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GameApiService {
    @GET("games")
    Call<List<GameRemoteModel>> getAllGames();

    @GET("games/search")
    Call<List<GameRemoteModel>> searchGames(@Query("query") String query);
}