package com.example.android.popularmovies1;

import java.io.Serializable;

/**
 * Created by I Kadek Aditya on 6/13/2017.
 */

public class DataFilm implements Serializable{
    private String mTitle;
    private String mPoster;
    private String mOverview;
    private double mRating;
    private String mRelaseDate;

    public DataFilm(String title, String poster, String overview, double rating, String relase){
        mTitle = title;
        mPoster = poster;
        mOverview = overview;
        mRating = rating;
        mRelaseDate = relase;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmPoster() {
        return mPoster;
    }

    public String getmOverview() {
        return mOverview;
    }

    public double getmRating() {
        return mRating;
    }

    public String getmRelaseDate() {
        return mRelaseDate;
    }
}
