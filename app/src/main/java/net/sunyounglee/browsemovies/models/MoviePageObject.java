package net.sunyounglee.browsemovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MoviePageObject {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    private List<Movie> resultList = new ArrayList<>();

    public List<Movie> getResultList() {
        return resultList;
    }

}