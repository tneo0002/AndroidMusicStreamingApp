package com.example.music_application.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tracks1 {
//    @SerializedName("artists")  //"tracks.artists"
//    @Expose
//    private List<Artist> artists = null;
//
//    public List<Artist> getArtists() {
//        return artists;
//    }
//
//    public void setArtists(List<Artist> artists) {
//        this.artists = artists;
//    }
//
//    @SerializedName("name")  //"tracks.name"
//    @Expose
//    private String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public class Artist {
//        @SerializedName("name")  //"tracks.artists.name"
//        @Expose
//        private String name;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }

//    public class Images {
//        @SerializedName("url")  //tracks.images.url
//        @Expose
//        private String url;
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//    }

    @SerializedName("album") //"tracks.album"
    @Expose
    private Album album;

    @SerializedName("artists")  //"tracks.artists"
    @Expose
    private List<Artist> artists = null;

    @SerializedName("name")  //"tracks.name"
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


    public class Album {
        @SerializedName("images")  //"tracks.album.images"
        @Expose
        private List<Image> images = null;

        @SerializedName("name")  //"tracks.album.name"
        @Expose
        private String name;

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setAlbumName(String name) {
            this.name = name;
        }

    }


    public class Artist {
        @SerializedName("name")  //"tracks.artists.name"
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
        @SerializedName("url")  //tracks.images.url
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

