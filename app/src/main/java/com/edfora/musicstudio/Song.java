package com.edfora.musicstudio;

/**
 * Created by Neeraj on 19/03/17.
 */
public class Song {
    private String song;
    private String url;
    private String artists;
    private String cover;

    public Song() {
    }

    public Song(String song, String url, String artists, String cover) {
        this.song = song;
        this.url= url;
        this.artists = artists;
        this.cover=cover;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtists() {
        return artists;
    }
    public void setArtists(String artists) {
        this.artists = artists;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }
}
