package com.example.music_application.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tracks {
    @SerializedName("items")
    @Expose
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public class Item {
        @SerializedName("album") //"track.items.album"
        @Expose
        private Album album;

        @SerializedName("artists")  //"tracks.items.artists"
        @Expose
        private List<Artist> artists = null;

        @SerializedName("name")  //"tracks.items.name"
        @Expose
        private String name;

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public List<Artist> getArtists() {
            return artists;
        }

        public void setArtists(List<Artist> artists) {
            this.artists = artists;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Album {
        @SerializedName("images")  //"tracks.items.album.images"
        @Expose
        private List<Image> images = null;

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }
    }

    public class Artist {
        @SerializedName("name")  //"tracks.items.artists.name"
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Image {
        @SerializedName("url")  //tracks.items.images.url
        @Expose
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
