package com.merchpandas.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.merchpandas.moviesapp.allAdapters.MainAdapter;
import com.merchpandas.moviesapp.POJO.Moviespojo;
import com.merchpandas.moviesapp.Utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {


    TextView emptyTextView;
    MainAdapter movieAdapter;
    RecyclerView recyclerView;
    MyAsyncTask asyncTask2;


    private static final String LAYOUT_MANAGER = "recycler_state";
    private Parcelable listState;
    ArrayList<Moviespojo> moviesList;
    MyAsyncTask asyncTask;
    ProgressBar progressbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyTextView = findViewById(R.id.emptyTextView);
        recyclerView = findViewById(R.id.recyclerView);

        init();


    }



    @Override
    protected void onResume() {
        super.onResume();

        if (listState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }


    private void init() {
        moviesList = new ArrayList<Moviespojo>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MainAdapter(this, moviesList);
        recyclerView.setAdapter(movieAdapter);


        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NI = CM.getActiveNetworkInfo();
        if (NI != null && NI.isConnected()) {
            emptyTextView.setVisibility(GONE);
            asyncTask = new MyAsyncTask();
            asyncTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=39065d6a53d8b1b08d90f1265e4ba535");


        } else {
            progressbar = findViewById(R.id.progress_bar);
            if (progressbar.getVisibility() == View.VISIBLE) {
                progressbar.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NI = CM.getActiveNetworkInfo();

        if (NI != null && NI.isConnected()) {
            emptyTextView.setVisibility(View.INVISIBLE);

            if (itemThatWasClickedId == R.id.popular) {
                asyncTask = new MyAsyncTask();
                asyncTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=8f067240d8717f510b4c79abe9f714b7");

            } else if (itemThatWasClickedId == R.id.top_rated) {
                asyncTask2 = new MyAsyncTask();
                asyncTask2.execute("https://api.themoviedb.org/3/movie/top_rated?api_key=8f067240d8717f510b4c79abe9f714b7");
            }

        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);

        }
        if (itemThatWasClickedId == R.id.favourite) {

            emptyTextView.setVisibility(View.GONE);
            progressbar.setVisibility(View.GONE);


        }

        return true;
    }


    private class MyAsyncTask extends AsyncTask<String, Void, List<Moviespojo>> {

        @Override
        protected void onPreExecute() {
            recyclerView.setVisibility(View.INVISIBLE);
            progressbar = findViewById(R.id.progress_bar);
            if (progressbar.getVisibility() == View.GONE) {
                progressbar.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected List<Moviespojo> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            return MovieUtils.fetchMovieDetails(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Moviespojo> movieDetailsList) {
            super.onPostExecute(movieDetailsList);
            progressbar.setVisibility(View.GONE);


            movieAdapter = new MainAdapter(MainActivity.this, movieDetailsList);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            recyclerView.setAdapter(movieAdapter);


            movieAdapter = new MainAdapter(MainActivity.this, movieDetailsList);
            //  recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            recyclerView.setAdapter(movieAdapter);
            recyclerView.setVisibility(View.VISIBLE);


        }
    }

    protected void onSaveInstanceState(Bundle state) {
        // Save list state
        listState = recyclerView.getLayoutManager().onSaveInstanceState();
        state.putParcelable(LAYOUT_MANAGER, listState);
        super.onSaveInstanceState(state);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if (state != null)
            listState = state.getParcelable(LAYOUT_MANAGER);
    }

}
