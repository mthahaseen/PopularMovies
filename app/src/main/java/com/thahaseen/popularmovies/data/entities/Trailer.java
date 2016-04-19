package com.thahaseen.popularmovies.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thahaseen on 4/18/2016.
 */
public class Trailer implements Parcelable {

    public Trailer(){}

    String key;
    String name;

    protected Trailer(Parcel in) {
        key = in.readString();
        name = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }
        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
    }

    public String getTrailerURL() {
        return key;
    }

    public void setTrailerURL(String trailerURL) {
        this.key = trailerURL;
    }

    public String getTrailerName() {
        return name;
    }

    public void setTrailerName(String trailerName) {
        this.name = trailerName;
    }
}
