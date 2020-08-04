package net.sunyounglee.browsemovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewPageObject {

    @SerializedName("id")
    private long movieId;
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    private List<Review> reviewResultList = new ArrayList<>();

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public List<Review> getReviewResultList() {
        return reviewResultList;
    }

    public void setReviewResultList(List<Review> reviewResultList) {
        this.reviewResultList = reviewResultList;
    }
}
