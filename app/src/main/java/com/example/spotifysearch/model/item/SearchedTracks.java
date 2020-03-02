package com.example.spotifysearch.model.item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchedTracks {

    private String href;

    private String next;

    private String previous;

    private int limit;

    private int total;

    private int offset;

    @SerializedName("items")
    private List<Track> tracks;

    public String getHref() {
        return href;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public int getOffset() {
        return offset;
    }

    public List<Track> getTracks() {
        return tracks;
    }


}
