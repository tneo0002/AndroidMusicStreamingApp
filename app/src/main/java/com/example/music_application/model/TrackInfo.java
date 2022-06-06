package com.example.music_application.model;

public class TrackInfo {
    private String mName;
    private String mArtist;
    private String mImageUrl;

    public TrackInfo(String name, String artist, String imageUrl) {
        mName = name;
        mArtist = artist;
        mImageUrl = imageUrl;
    }

    public TrackInfo(String name, String artist) {
        mName = name;
        mArtist = artist;
    }

    public String getName() {
        return mName;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getImageUrl() { return mImageUrl; }
}
