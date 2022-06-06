package com.example.music_application;

import java.io.Serializable;

public class AudioModel implements Serializable {
    private String path;
    private String songName;
    private String artist;
    private String duration;

    public AudioModel(String path, String songName, String artist, String duration) {
        this.path = path;
        this.songName = songName;
        this.artist = artist;
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
