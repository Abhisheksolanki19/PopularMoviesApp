package com.merchpandas.moviesapp.POJO;


public class Moviespojo {

    private String mMovieTitle;
    private String mMovieRatings;
    private String mMovieImage;
    private String mMovieDate;
    private String mMovieSynopsis;
    private String mMovieId;

    public Moviespojo(String movieTitle, String movieDate, String movieRatings, String movieSynopsis, String movieImage, String movieId) {
        mMovieDate = movieDate;
        mMovieImage = movieImage;
        mMovieRatings = movieRatings;
        mMovieSynopsis = movieSynopsis;
        mMovieTitle = movieTitle;
        mMovieId = movieId;

    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public String getMovieRatings() {
        return mMovieRatings;
    }

    public String getMovieImage() {
        return mMovieImage;
    }

    public String getMovieDate() {
        return mMovieDate;
    }

    public String getMovieSynopsis() {
        return mMovieSynopsis;
    }

    public String getMovieId() {
        return mMovieId;
    }
}
