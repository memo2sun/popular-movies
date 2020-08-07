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
import net.sunyounglee.browsemovies.models.MoviePageObject;
import net.sunyounglee.browsemovies.repositories.MainViewRepository;
import net.sunyounglee.browsemovies.utilities.MovieAPIService;
import net.sunyounglee.browsemovies.utilities.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private static String API_KEY;
    private final MutableLiveData<List<Movie>> mMovieListFromServer = new MutableLiveData<>();
    private LiveData<List<Movie>> favoriteMovies;

    public MainViewModel(@NonNull Application application, MainViewRepository mainViewRepository) {
        super(application);
        Context context = this.getApplication();
        API_KEY = context.getString(R.string.my_api_key);
        favoriteMovies = mainViewRepository.getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovieListFromServer;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void loadMovieDataFromServer(String sortBy) {
        MovieAPIService service = RetrofitClient.getRetrofitInstance().create(MovieAPIService.class);
        Log.d(TAG, "loadMovieDataFromServer");
        Log.d(TAG, sortBy);
        service.fetchAllMovies(sortBy, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MoviePageObject>() {
                    @Override
                    public void onSuccess(MoviePageObject moviePageObject) {
                        mMovieListFromServer.setValue(moviePageObject.getResultList());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Network Error: " + e.getMessage());
                    }
                });
    }
}