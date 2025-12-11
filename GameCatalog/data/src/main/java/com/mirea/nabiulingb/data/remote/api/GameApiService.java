package com.mirea.nabiulingb.data.remote.api;

import com.mirea.nabiulingb.data.remote.models.GameListResponse;
import com.mirea.nabiulingb.data.remote.models.GameRemoteModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GameApiService {
    String API_KEY = "0eb1b2bcbc574f4eb50df8faf30be544";

    @GET("games")
    Call<GameListResponse> getAllGames(
            @Query("key") String apiKey
    );

    @GET("games")
    Call<GameListResponse> searchGames(
            @Query("key") String apiKey,
            @Query("search") String query
    );
}