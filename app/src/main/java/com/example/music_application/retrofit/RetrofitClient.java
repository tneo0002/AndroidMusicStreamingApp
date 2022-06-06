package com.example.music_application.retrofit;

import com.example.music_application.api.AuthApi;
import com.example.music_application.api.SpotifyApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String TOKEN_BASE_URL = "https://accounts.spotify.com";
    private static final String BASE_URL = "https://api.spotify.com";

    public static AuthApi getRetrofitToken(){
        retrofit = new Retrofit.Builder()
                .baseUrl(TOKEN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AuthApi.class);
    }

    public static SpotifyApi getRetrofitService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SpotifyApi.class);
    }

//    public static RecommendApi getRecommendRetrofitService(){
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit.create(RecommendApi.class);
//    }
}