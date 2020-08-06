package net.sunyounglee.browsemovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.sunyounglee.browsemovies.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM movie WHERE movieId =:movieId")
    void deleteMovieById(long movieId);


    @Query("SELECT * FROM movie WHERE movieId = :id")
    LiveData<Movie> loadMovieById(long id);


    @Query("SELECT COUNT(*) FROM movie WHERE movieId = :id")
    int getMovieCountById(long id);

    @Query("UPDATE movie SET title = :title, poster_image= :poster_image, backDrop_image = :backDrop_image, overview=:overview, rating =:rating, release_Date =:release_date WHERE movieId =:movieId")
    void updateMovieExceptFavorite(long movieId, String title, String poster_image, String backDrop_image, String overview, float rating, String release_date);

}
