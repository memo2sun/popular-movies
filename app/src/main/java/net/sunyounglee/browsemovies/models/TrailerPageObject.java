package net.sunyounglee.browsemovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailerPageObject {

    @SerializedName("id")
    private long movieId;

    @SerializedName("results")
    private List<Trailer> trailerResultList = new ArrayList<>();

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public List<Trailer> getTrailerResultList() {
        return trailerResultList;
    }
}
