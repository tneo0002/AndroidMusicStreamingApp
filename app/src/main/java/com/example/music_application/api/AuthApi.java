package com.example.music_application.api;

import com.example.music_application.retrofit.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {
    @FormUrlEncoded
    @POST("/api/token")
    Call<AccessToken> getToken(@Header("Authorization") String AUTHORIZATION,
                               @Header("Content-Type") String CONTENT_TYPE,
                               @Field("grant_type") String GRANT_TYPE);
}