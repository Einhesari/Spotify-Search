package com.example.spotifysearch.model.item;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Album extends BaseItem {
    @SerializedName("album_type")
    private String albumType;
    @SerializedName("release_date")
    private String releaseDate;
    private ArrayList<ItemImages> images;
    private ArrayList<Artist> artists;
    @SerializedName("total_tracks")
    private int totalTracks;
    private ArrayList<String> genres;

    protected Album(Parcel in) {
        super(in);
    }


    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    public ArrayList<ItemImages> getImages() {
        return images;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

}
