package net.sunyounglee.browsemovies.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Review;
import net.sunyounglee.browsemovies.models.Trailer;

@Database(entities = {Movie.class, Review.class, Trailer.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popularMovieDb";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();

    public abstract ReviewDao reviewDao();

    public abstract TrailerDao trailerDao();
}
