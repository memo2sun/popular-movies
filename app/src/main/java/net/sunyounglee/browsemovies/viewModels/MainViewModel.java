package net.sunyounglee.browsemovies.viewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.MoviePageObject;
import net.sunyounglee.browsemovies.repositories.MainViewRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private LiveData<MoviePageObject> mPopularMovies;
    private LiveData<MoviePageObject> mTopRatedMovies;
    private LiveData<List<Movie>> favoriteMovies;

    private MainViewRepository mMainViewRepository;

    public MainViewModel(@NonNull Application application, MainViewRepository mainViewRepository) {
        super(application);
        Context context = this.getApplication();
        mMainViewRepository = mainViewRepository;

        Log.d(TAG, "Actively retrieving the movies from the DataBase");
        mPopularMovies = mainViewRepository.getPopularMovies(context);
        mTopRatedMovies = mainViewRepository.getTopRatedMovies(context);
        favoriteMovies = mainViewRepository.getMovies();
    }

    public LiveData<MoviePageObject> getPopularMovies() {
        return mPopularMovies;
    }

    public LiveData<MoviePageObject> getTopRatedMovies() {
        return mTopRatedMovies;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }

}
