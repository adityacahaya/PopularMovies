package com.example.android.popularmovies1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.popularmovies1.utilities.JsonUtils;
import com.example.android.popularmovies1.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private GridView mGridView;

    private ArrayList<DataFilm> dataFilmArrayList;

    private String SEND_DATA = "send-data-to-detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mSpinner = (Spinner) findViewById(R.id.spinner_main_activity);
        setupSpinner();

        mGridView = (GridView) findViewById(R.id.gridView_main_activity);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataFilm dataFilm = dataFilmArrayList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(SEND_DATA, dataFilm);
                startActivity(intent);
            }
        });
    }

    private void setupSpinner(){
        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.spinner_mainactivity_array,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.popular))){
                        makeSearchQuery(getString(R.string.popular_param));
                    }else if (selection.equals(getString(R.string.top_rated))){
                        makeSearchQuery(getString(R.string.top_rated_param));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void makeSearchQuery(String serachQuery){
        URL searchUrl = NetworkUtils.buildURL(serachQuery);
        new queryTask().execute(searchUrl);
    }

    public class queryTask extends AsyncTask<URL, Void, List<DataFilm>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        @Override
        protected List<DataFilm> doInBackground(URL... params) {
            URL searchUrl = params[0];
            String searchJSONResult;
            List<DataFilm> searchResult = null;
            try {
                searchJSONResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                searchResult = JsonUtils.getStringsFromJson(MainActivity.this,searchJSONResult);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return searchResult;
        }
        @Override
        protected void onPostExecute(List<DataFilm> dataFilms) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (dataFilms != null) {
                showJsonDataView();
                dataFilmArrayList = (ArrayList) dataFilms;
                mGridView.setAdapter(new GridViewAdapter(MainActivity.this,dataFilmArrayList));
            } else {
                showErrorMessage();
            }
        }
    }

    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mGridView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mGridView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
