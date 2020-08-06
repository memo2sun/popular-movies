package net.sunyounglee.browsemovies.viewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Review;
import net.sunyounglee.browsemovies.models.Trailer;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();
    private LiveData<Movie> movies;
    private LiveData<List<Review>> reviews;
    private LiveData<List<Trailer>> trailers;
    private DetailViewRepository mDetailViewRepository;

    public DetailViewModel(Movie movie, @NonNull Application application, DetailViewRepository detailViewRepository) {
        super(application);
        Long movieId = movie.getMovieId();
        Context context = this.getApplication();
        mDetailViewRepository = detailViewRepository;
        reviews = mDetailViewRepository.reviewFromServer(context, movieId);
        trailers = mDetailViewRepository.trailerFromServer(context, movieId);
        movies = mDetailViewRepository.getMovieFromDB(movieId);
    }

    public LiveData<Movie> getMovieFromDB() {
        return movies;
    }

    public LiveData<List<Review>> getReviewFromServer() {
        return reviews;
    }

    public LiveData<List<Review>> getReviewsFromDB(long movieId) {
        return mDetailViewRepository.getReview(movieId);
    }

    public LiveData<List<Trailer>> getTrailerFromServer() {
        return trailers;
    }

    public LiveData<List<Trailer>> getTrailerFromDB(long movieId) {
        return mDetailViewRepository.getTrailer(movieId);
    }

    public void deleteMovie(long movieId) {
        mDetailViewRepository.deleteMovie(movieId);
    }

    public void deleteReview(long movieId) {
        mDetailViewRepository.deleteReview(movieId);
    }

    public void deleteTrailer(long movieId) {
        mDetailViewRepository.deleteTrailer(movieId);
    }

    public void insertMovie(Movie movie) {
        mDetailViewRepository.insertMovie(movie);
    }

    public void insertReview(Review review) {
        mDetailViewRepository.insertReview(review);
    }

    public void insertTrailer(Trailer trailer) {
        mDetailViewRepository.insertTrailer(trailer);
    }
}
