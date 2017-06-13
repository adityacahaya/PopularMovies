package com.example.android.popularmovies1.utilities;

import android.content.Context;

import com.example.android.popularmovies1.DataFilm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I Kadek Aditya on 6/13/2017.
 */

public final class JsonUtils {

    public static List<DataFilm> getStringsFromJson(Context context, String jsonStr)
            throws JSONException {

        final String RESULT = "results";
        final String TITLE = "title";
        final String POSTER = "poster_path";
        final String OVERVIEW = "overview";
        final String RATING = "vote_average";
        final String  RELASE_DATE = "release_date";

        List<DataFilm> parsedData = new ArrayList<>();
        JSONObject dataJson = new JSONObject(jsonStr);
        JSONArray dataResultArray = dataJson.getJSONArray(RESULT);

        for (int i = 0; i < dataResultArray.length(); i++) {
            String mTitle;
            String mPoster;
            String mOverview;
            double mRating;
            String mRelaseDate;

            JSONObject dataFilm = dataResultArray.getJSONObject(i);
            mTitle = dataFilm.getString(TITLE);
            mPoster = dataFilm.getString(POSTER);
            mOverview = dataFilm.getString(OVERVIEW);
            mRating = dataFilm.getDouble(RATING);
            mRelaseDate = dataFilm.getString(RELASE_DATE);

            DataFilm film = new DataFilm(mTitle,mPoster,mOverview,mRating,mRelaseDate);
            parsedData.add(film);
        }

        return parsedData;
    }
}
