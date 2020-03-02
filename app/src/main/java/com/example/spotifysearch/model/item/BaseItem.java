package com.example.spotifysearch.model.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BaseItem implements Parcelable {

    private String href;
    private String id;
    private String name;
    private String uri;
    private String type;
    @SerializedName("external_urls")
    private ExternalUrl externalUrl;
    private int popularity;


    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public ExternalUrl getExternalUrl() {
        return externalUrl;
    }

    public int getPopularity() {
        return popularity;
    }



    protected BaseItem(Parcel in) {
        href = in.readString();
        id = in.readString();
        name = in.readString();
        uri = in.readString();
        type = in.readString();
        popularity = in.readInt();
    }

    public static final Creator<BaseItem> CREATOR = new Creator<BaseItem>() {
        @Override
        public BaseItem createFromParcel(Parcel in) {
            return new BaseItem(in);
        }

        @Override
        public BaseItem[] newArray(int size) {
            return new BaseItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(this.href);
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.uri);
        parcel.writeString(this.type);
        parcel.writeInt(this.popularity);
        parcel.writeParcelable(this.externalUrl , flag);
    }
}
