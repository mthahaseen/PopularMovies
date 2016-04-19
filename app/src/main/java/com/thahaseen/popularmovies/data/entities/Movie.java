package com.thahaseen.popularmovies.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thahaseen on 3/1/2016.
 */
public class Movie implements Parcelable {

    private String id;
    private String overview;
    private String release_date;
    private String poster_path;
    private String backdrop_path;
    private String title;
    private String original_title;
    private String original_language;
    private double vote_average;

    public Movie(){}

    protected Movie(Parcel in) {
        id = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        title = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        vote_average = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(title);
        parcel.writeString(original_title);
        parcel.writeString(original_language);
        parcel.writeDouble(vote_average);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
