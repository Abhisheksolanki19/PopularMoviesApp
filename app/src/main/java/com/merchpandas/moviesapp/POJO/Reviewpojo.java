package com.merchpandas.moviesapp.POJO;

public class Reviewpojo {

    private String mReviewAuthor;
    private String mReviewDetail;

    public Reviewpojo(String reviewAuthor, String reviewDetail) {
        mReviewAuthor = reviewAuthor;
        mReviewDetail = reviewDetail;
    }

    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    public String getReviewDetail() {
        return mReviewDetail;
    }
}
