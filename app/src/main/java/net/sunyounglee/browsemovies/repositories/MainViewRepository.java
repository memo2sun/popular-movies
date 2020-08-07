package net.sunyounglee.browsemovies.repositories;

import androidx.lifecycle.LiveData;

import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.models.Movie;

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

    public LiveData<List<Movie>> getMovies() {
        return movieDao.loadAllMovies();
    }
}
