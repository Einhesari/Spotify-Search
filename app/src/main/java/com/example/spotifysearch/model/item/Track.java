package com.example.spotifysearch.model.item;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Track extends BaseItem {


    private Album album;
    private ArrayList<Artist> artists;

    @SerializedName("explicit")
    private String explicitness;

    protected Track(Parcel in) {
        super(in);
    }

    public Album getAlbum() {
        return album;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public String getExplicitness() {
        return explicitness;
    }


}
