package com.merchpandas.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.merchpandas.moviesapp.allAdapters.ReviewAdapter;
import com.merchpandas.moviesapp.allAdapters.VideoAdapter;
import com.merchpandas.moviesapp.POJO.Reviewpojo;
import com.merchpandas.moviesapp.Utils.MovieUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {

    MovieVideoAsyncTask asyncTaskVideo;
    MovieReviewAsyncTask asyncTaskReview;

    String id, mtitle, mOverView, mdate, mRating;

    ImageView reviewImage;
    ImageView videoImage;

    RecyclerView videoRV;
    VideoAdapter VideoAdapter;
    RecyclerView reviewRV;
    ReviewAdapter reviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);



        reviewImage = findViewById(R.id.review_load_image);
        videoImage = findViewById(R.id.trailer_load_image);
        reviewImage.setVisibility(View.VISIBLE);
        videoImage.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        detailsUpdate(intent);
        updateVid();
        updateReviews();

        asyncTaskVideo = new MovieVideoAsyncTask();
        asyncTaskVideo.execute(getVidLink(id));

        asyncTaskReview = new MovieReviewAsyncTask();
        asyncTaskReview.execute(getRevLink(id));

        Picasso.get().load(R.drawable.load5).into(reviewImage);
        Picasso.get().load(R.drawable.load5).into(videoImage);

        FloatingActionButton favButton = findViewById(R.id.set_favourite);

    }





    private void updateVid() {
        ArrayList<String> movieVideoArrayList = new ArrayList<String>();
        videoRV = findViewById(R.id.trailer_recycler_view);
        videoRV.setLayoutManager(new GridLayoutManager(this, 4));
        videoRV.setNestedScrollingEnabled(false);
        videoRV.setHasFixedSize(true);
        VideoAdapter = new VideoAdapter(movieVideoArrayList, MovieDetailActivity.this);
        videoRV.setAdapter(VideoAdapter);
    }

    //Updates the details from MovieDetail Intent passed
    private void detailsUpdate(Intent intent) {
        mOverView = intent.getStringExtra("overview");
        mdate = intent.getStringExtra("date");
        mRating = intent.getStringExtra("rating");
        mtitle = intent.getStringExtra("title");
        String image = intent.getStringExtra("image");
        String id = intent.getStringExtra("id");
        this.id = id;

        TextView ratingTextView = findViewById(R.id.rating);
        TextView titleTextView = findViewById(R.id.title);
        TextView dateTextView = findViewById(R.id.date);
        TextView overViewTextView = findViewById(R.id.overView);
        ImageView imageView = findViewById(R.id.image);
        titleTextView.setText(mtitle);
        dateTextView.setText("Release Date:" + mdate);
        ratingTextView.setText("Rating: " + mRating);
        overViewTextView.setText(mOverView);

        Picasso.get().load(image).placeholder(R.drawable.load5).into(imageView);

    }
    private void updateReviews() {
        ArrayList<Reviewpojo> movieReviewArrayList = new ArrayList<>();
        reviewRV = findViewById(R.id.reviews_recycler_view);
        reviewRV.setLayoutManager(new LinearLayoutManager(this));
        reviewRV.setNestedScrollingEnabled(false);
        reviewRV.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(movieReviewArrayList, MovieDetailActivity.this);
        reviewRV.setAdapter(VideoAdapter);
    }





    public class MovieVideoAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (videoRV.getVisibility() == View.VISIBLE) {
                videoRV.setVisibility(View.GONE);
            }
            videoImage.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            return MovieUtils.fetchMovieVideo(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> list) {
            super.onPostExecute(list);

            VideoAdapter = new VideoAdapter(list, MovieDetailActivity.this);
            videoRV.setAdapter(VideoAdapter);

            if (videoRV.getVisibility() == View.GONE) {
                videoRV.setVisibility(View.VISIBLE);
            }
            videoImage.setVisibility(View.GONE);

        }
    }
    private String getVidLink(String id) {
        String videoLink = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=8f067240d8717f510b4c79abe9f714b7&language=en-US";
        return videoLink;
    }


    private String getRevLink(String id) {
        String reviewLink = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=8f067240d8717f510b4c79abe9f714b7&language=en-US";
        return reviewLink;
    }

    public class MovieReviewAsyncTask extends AsyncTask<String, Void, ArrayList<Reviewpojo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (reviewRV.getVisibility() == View.VISIBLE) {
                reviewRV.setVisibility(View.GONE);
            }
            reviewImage.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Reviewpojo> doInBackground(String... strings) {

            return MovieUtils.fetchMovieReview(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Reviewpojo> movieReviews) {
            super.onPostExecute(movieReviews);

            reviewAdapter = new ReviewAdapter(movieReviews, MovieDetailActivity.this);
            reviewRV.setAdapter(reviewAdapter);

            if (reviewRV.getVisibility() == View.GONE) {
                reviewRV.setVisibility(View.VISIBLE);
                reviewImage.setVisibility(View.GONE);

            }
        }
    }
}
