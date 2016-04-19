package com.thahaseen.popularmovies.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thahaseen on 4/18/2016.
 */
public class Trailer implements Parcelable {

    public Trailer(){}

    String trailerURL;
    String trailerName;

    protected Trailer(Parcel in) {
        trailerURL = in.readString();
        trailerName = in.readString();
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
        parcel.writeString(trailerURL);
        parcel.writeString(trailerName);
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }
}
