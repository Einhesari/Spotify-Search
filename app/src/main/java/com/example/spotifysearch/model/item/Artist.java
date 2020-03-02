package com.example.spotifysearch.model.item;

import android.os.Parcel;

import java.util.ArrayList;

public class Artist extends BaseItem {


    private ArrayList<ItemImages> images;
    private ArrayList<String> genres;

    protected Artist(Parcel in) {
        super(in);
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
    public ArrayList<ItemImages> getImages() {
        return images;
    }

}


