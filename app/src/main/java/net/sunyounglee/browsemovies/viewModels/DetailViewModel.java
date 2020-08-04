package net.sunyounglee.browsemovies.viewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Review;
import net.sunyounglee.browsemovies.models.ReviewPageObject;
import net.sunyounglee.browsemovies.models.Trailer;
import net.sunyounglee.browsemovies.models.TrailerPageObject;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();
    private LiveData<Movie> movies;
    private LiveData<ReviewPageObject> reviews;
    private LiveData<TrailerPageObject> trailers;
    private DetailViewRepository mDetailViewRepository;

    public DetailViewModel(long movieId, @NonNull Application application, DetailViewRepository detailViewRepository) {
        super(application);
        Context context = this.getApplication();
        mDetailViewRepository = detailViewRepository;
        reviews = mDetailViewRepository.refreshReview(context, movieId);
        trailers = mDetailViewRepository.refreshTrailer(context, movieId);
        movies = mDetailViewRepository.getMovieInfo(movieId);
    }


    public LiveData<Movie> getMovies() {
        return movies;
    }

    public LiveData<ReviewPageObject> getReviews() {
        return reviews;
    }

    public LiveData<List<Review>> getReviewsFromDB() {
        return mDetailViewRepository.getReview();
    }

    public LiveData<TrailerPageObject> getTrailers() {
        return trailers;
    }

    public LiveData<List<Trailer>> getTrailerFromDB() {
        return mDetailViewRepository.getTrailer();
    }

    public void deleteMovie(long movieId) {
        mDetailViewRepository.deleteMovie(movieId);
    }

    public void addToFavorite(long movieId) {
        mDetailViewRepository.updateMovieFavorite(movieId);
    }

}
