package com.example.spotifysearch.model.item;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("tracks")
    private SearchedTracks foundedTracks;

    @SerializedName("artists")
    private SearchedArtists foundedArtists;

    @SerializedName("albums")
    private SearchedAlbums foundedAlbums;

    public SearchedTracks getFoundedTracks() {
        return foundedTracks;
    }

    public SearchedArtists getFoundedArtists() {
        return foundedArtists;
    }

    public SearchedAlbums getFoundedAlbums() {
        return foundedAlbums;
    }

}
