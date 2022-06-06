package com.example.music_application.retrofit;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}