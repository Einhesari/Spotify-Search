package com.example.spotifysearch.model.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ExternalUrl implements Parcelable {


    @SerializedName("spotify")
    private String spotifyUrl;

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    protected ExternalUrl(Parcel in) {
        spotifyUrl = in.readString();
    }

    public static final Creator<ExternalUrl> CREATOR = new Creator<ExternalUrl>() {
        @Override
        public ExternalUrl createFromParcel(Parcel in) {
            return new ExternalUrl(in);
        }

        @Override
        public ExternalUrl[] newArray(int size) {
            return new ExternalUrl[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.spotifyUrl);
    }
}
