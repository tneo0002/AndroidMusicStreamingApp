package com.example.music_application.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecommendResponse {
    @SerializedName("tracks")
    @Expose
    public ArrayList<Tracks1> tracks;

    public ArrayList<Tracks1> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Tracks1> tracks) {
        this.tracks = tracks;
    }
}
