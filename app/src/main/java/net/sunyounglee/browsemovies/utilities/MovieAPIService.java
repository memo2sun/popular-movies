package net.sunyounglee.browsemovies.utilities;

import net.sunyounglee.browsemovies.models.MoviePageObject;
import net.sunyounglee.browsemovies.models.ReviewPageObject;
import net.sunyounglee.browsemovies.models.TrailerPageObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPIService {

    String SORT_BY_POPULARITY = "3/movie/popular?";
    String SORT_BY_TOP_RATED = "3/movie/top_rated?";

    String GET_REVIEWS = "3/movie/{id}/reviews?";
    String GET_TRAILERS = "3/movie/{id}/videos?";

    @GET(SORT_BY_POPULARITY)
    Call<MoviePageObject> movieListByPopularity(@Query("api_key") String api_key);

    @GET(SORT_BY_TOP_RATED)
    Call<MoviePageObject> movieListByTopRated(@Query("api_key") String api_key);

    @GET(GET_REVIEWS)
    Call<ReviewPageObject> reviewList(@Path("id") long movieId, @Query("api_key") String api_key);

    @GET(GET_TRAILERS)
    Call<TrailerPageObject> trailerList(@Path("id") long movieId, @Query("api_key") String api_key);


}