package com.example.android.popularmovies1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mPoster;
    private TextView mRelaseDate;
    private TextView mRating;
    private TextView mOverview;

    private DataFilm dataFilm;

    private String RECEIVE_DATA = "send-data-to-detail";
    private String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mTitle = (TextView) findViewById(R.id.title_detail_activity);
        mPoster = (ImageView) findViewById(R.id.poster_detail);
        mRelaseDate = (TextView) findViewById(R.id.relase_detail_view);
        mRating = (TextView) findViewById(R.id.rating_detail_view);
        mOverview = (TextView) findViewById(R.id.overview_detail_view);

        Intent intent = getIntent();
        dataFilm = (DataFilm) intent.getSerializableExtra(RECEIVE_DATA);

        mTitle.setText(dataFilm.getmTitle());
        Picasso.with(this).load(BASE_IMAGE_URL+dataFilm.getmPoster()).into(mPoster);
        mRelaseDate.setText(dataFilm.getmRelaseDate());
        mRating.setText(String.valueOf(dataFilm.getmRating()));
        mOverview.setText(dataFilm.getmOverview());
    }
}
