package net.sunyounglee.browsemovies.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.data.ReviewDao;
import net.sunyounglee.browsemovies.data.TrailerDao;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Review;
import net.sunyounglee.browsemovies.models.ReviewPageObject;
import net.sunyounglee.browsemovies.models.Trailer;
import net.sunyounglee.browsemovies.models.TrailerPageObject;
import net.sunyounglee.browsemovies.utilities.MovieJsonUtils;

import java.util.List;

public class DetailViewRepository {
    private ReviewDao reviewDao;
    private TrailerDao trailerDao;
    private MovieDao movieDao;
    private static DetailViewRepository sInstance;
    private static final Object LOCK = new Object();
    private long movieId;

    private DetailViewRepository(MovieDao movieDao, ReviewDao reviewDao, TrailerDao trailerDao, long movieId) {
        this.movieDao = movieDao;
        this.reviewDao = reviewDao;
        this.movieId = movieId;
        this.trailerDao = trailerDao;
    }

    public static DetailViewRepository getInstance(MovieDao movieDao, ReviewDao reviewDao, TrailerDao trailerDao, long movieId) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new DetailViewRepository(movieDao, reviewDao, trailerDao, movieId);
            }
        }
        return sInstance;
    }

    public LiveData<ReviewPageObject> refreshReview(Context context, long movieId) {
        return MovieJsonUtils.loadReviewDataFromServer(context, movieId);
    }

    public LiveData<TrailerPageObject> refreshTrailer(Context context, long movieId) {
        return MovieJsonUtils.loadTrailerDataFromServer(context, movieId);
    }

    public LiveData<List<Review>> getReview() {
        return reviewDao.loadReviewById(movieId);
    }


    public LiveData<List<Trailer>> getTrailer() {
        return trailerDao.loadTrailerById(movieId);
    }

    public LiveData<Movie> getMovieInfo(long movieId) {
        return movieDao.loadMovieById(movieId);
    }

    public void deleteMovie(long movieId) {
        movieDao.deleteMovieById(movieId);
    }

    public void updateMovieFavorite(long movieId) {
        movieDao.updateMovieFavorite(true, movieId);
    }
}
