package net.sunyounglee.browsemovies.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.MoviePageObject;
import net.sunyounglee.browsemovies.utilities.MovieJsonUtils;

import java.util.List;

public class MainViewRepository {

    private static final String TAG = MainViewRepository.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static MainViewRepository sInstance;
    private MovieDao movieDao;

    private MainViewRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public static MainViewRepository getInstance(MovieDao movieDao) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MainViewRepository(movieDao);
            }
        }
        return sInstance;
    }

    public LiveData<MoviePageObject> refreshMovie(Context context, int sortBy) {
        return MovieJsonUtils.loadMovieDataFromServer(sortBy, context);
    }

    public LiveData<MoviePageObject> getPopularMovies(Context context) {
        return refreshMovie(context, 1);
    }

    public LiveData<MoviePageObject> getTopRatedMovies(Context context) {
        return refreshMovie(context, 2);
    }

    public LiveData<List<Movie>> getMovies() {
        return movieDao.loadAllMovies();
    }
}
