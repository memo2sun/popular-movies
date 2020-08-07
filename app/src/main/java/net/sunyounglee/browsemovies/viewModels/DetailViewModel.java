package net.sunyounglee.browsemovies.viewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Review;
import net.sunyounglee.browsemovies.models.ReviewPageObject;
import net.sunyounglee.browsemovies.models.Trailer;
import net.sunyounglee.browsemovies.models.TrailerPageObject;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;
import net.sunyounglee.browsemovies.utilities.MovieAPIService;
import net.sunyounglee.browsemovies.utilities.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends AndroidViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();
    private LiveData<Movie> movies;

    private DetailViewRepository mDetailViewRepository;

    private static String API_KEY;
    private static MovieAPIService service;
    private final MutableLiveData<List<Review>> reviewListFromServer = new MutableLiveData<>();
    private final MutableLiveData<List<Trailer>> trailerListFromServer = new MutableLiveData<>();

    public DetailViewModel(Movie movie, @NonNull Application application, DetailViewRepository detailViewRepository) {
        super(application);
        Long movieId = movie.getMovieId();
        Context context = this.getApplication();
        API_KEY = context.getString(R.string.my_api_key);
        mDetailViewRepository = detailViewRepository;
        movies = mDetailViewRepository.getMovieFromDB(movieId);
        loadReviewDataFromServer(movieId);
        loadTrailerDataFromServer(movieId);
    }

    public LiveData<Movie> getMovieFromDB() {
        return movies;
    }

    public LiveData<List<Review>> getReviewFromServer() {
        return reviewListFromServer;
    }

    public LiveData<List<Review>> getReviewsFromDB(long movieId) {
        return mDetailViewRepository.getReview(movieId);
    }

    public LiveData<List<Trailer>> getTrailerFromServer() {
        return trailerListFromServer;
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

    private void loadReviewDataFromServer(long movieId) {
        service = RetrofitClient.getRetrofitInstance().create(MovieAPIService.class);
        service.fetchAllReviews(movieId, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ReviewPageObject>() {
                    @Override
                    public void onSuccess(ReviewPageObject reviewPageObject) {
                        reviewListFromServer.setValue(reviewPageObject.getReviewResultList());
                        Log.d(TAG, "Review size: " + String.valueOf(reviewListFromServer.getValue().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Network Error: " + e.getMessage());
                    }
                });
    }

    private void loadTrailerDataFromServer(long movieId) {
        service = RetrofitClient.getRetrofitInstance().create(MovieAPIService.class);
        service.fetchAllTrailers(movieId, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TrailerPageObject>() {
                    @Override
                    public void onSuccess(TrailerPageObject trailerPageObject) {
                        trailerListFromServer.setValue(trailerPageObject.getTrailerResultList());
                        Log.d(TAG, "Trailer size: " + String.valueOf(trailerListFromServer.getValue().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Network Error: " + e.getMessage());
                    }
                });
    }
}
