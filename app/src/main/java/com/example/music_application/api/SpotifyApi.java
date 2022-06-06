package com.example.music_application.api;

import com.example.music_application.retrofit.RecommendResponse;
import com.example.music_application.retrofit.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpotifyApi {


    @GET("/v1/search")
    Call<SearchResponse> getInfo(@Header("Accept") String accept,
                                 @Header("Content-Type") String content,
                                 @Header("Authorization") String token,
                                 @Query("q") String searchKeyword,
                                 @Query("type") String searchType,
                                 @Query("limit") int limit);

    @GET("/v1/recommendations")
    Call<RecommendResponse> customRecommend(@Header("Accept") String accept,
                                            @Header("Content-Type") String content,
                                            @Header("Authorization") String token,
                                            @Query("seed_artists") String seedArtists,
                                            @Query("seed_genres") String seedGenres,
                                            @Query("seed_tracks") String seedTracks);
}