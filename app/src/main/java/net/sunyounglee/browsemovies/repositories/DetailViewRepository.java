package net.sunyounglee.browsemovies.repositories;

import androidx.lifecycle.LiveData;

import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.data.ReviewDao;
import net.sunyounglee.browsemovies.data.TrailerDao;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Review;
import net.sunyounglee.browsemovies.models.Trailer;

import java.util.List;

public class DetailViewRepository {
    private ReviewDao reviewDao;
    private TrailerDao trailerDao;
    private MovieDao movieDao;
    private static DetailViewRepository sInstance;
    private static final Object LOCK = new Object();
    private long movieId;

    private DetailViewRepository(MovieDao movieDao, ReviewDao reviewDao, TrailerDao trailerDao, Movie movie) {
        this.movieDao = movieDao;
        this.reviewDao = reviewDao;
        this.trailerDao = trailerDao;
        movieId = movie.getMovieId();
    }

    public static DetailViewRepository getInstance(MovieDao movieDao, ReviewDao reviewDao, TrailerDao trailerDao, Movie movie) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new DetailViewRepository(movieDao, reviewDao, trailerDao, movie);
            }
        }
        return sInstance;
    }

    public LiveData<Movie> getMovie() {
        return movieDao.loadMovieById(movieId);
    }

    public LiveData<List<Review>> getReview(long movieId) {
        return reviewDao.loadReviewById(movieId);
    }

    public LiveData<List<Trailer>> getTrailer(long movieId) {
        return trailerDao.loadTrailerById(movieId);
    }

    public LiveData<Movie> getMovieFromDB(long movieId) {
        return movieDao.loadMovieById(movieId);
    }

    public void deleteMovie(long movieId) {
        movieDao.deleteMovieById(movieId);
    }

    public void deleteReview(long movieId) {
        reviewDao.deleteReviewById(movieId);
    }

    public void deleteTrailer(long movieId) {
        trailerDao.deleteTrailerById(movieId);
    }

    public void insertMovie(Movie movie) {
        movieDao.insertMovie(movie);
    }

    public void insertReview(Review review) {
        reviewDao.insertReview(review);
    }

    public void insertTrailer(Trailer trailer) {
        trailerDao.insertTrailer(trailer);
    }


}
