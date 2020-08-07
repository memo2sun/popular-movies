package net.sunyounglee.browsemovies.utilities;

import net.sunyounglee.browsemovies.models.MoviePageObject;
import net.sunyounglee.browsemovies.models.ReviewPageObject;
import net.sunyounglee.browsemovies.models.TrailerPageObject;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPIService {

    // For popular endpoint 3/movie/popular? , for top rated endpoint 3/movie/top_rated?
    String GET_MOVIES = "3/movie/{sortBy}?";
    String GET_REVIEWS = "3/movie/{id}/reviews?";
    String GET_TRAILERS = "3/movie/{id}/videos?";

    //Fetch all popular movies or all top rated movies
    @GET(GET_MOVIES)
    Single<MoviePageObject> fetchAllMovies(@Path("sortBy") String sortBy, @Query("api_key") String api_key);

    //Fetch all reviews for a movie
    @GET(GET_REVIEWS)
    Single<ReviewPageObject> fetchAllReviews(@Path("id") long movieId, @Query("api_key") String api_key);

    //Fetch all trailers for a movie
    @GET(GET_TRAILERS)
    Single<TrailerPageObject> fetchAllTrailers(@Path("id") long movieId, @Query("api_key") String api_key);

}