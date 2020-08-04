package net.sunyounglee.browsemovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.sunyounglee.browsemovies.models.Trailer;

import java.util.List;

@Dao
public interface TrailerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrailer(Trailer trailer);

    @Query("DELETE FROM trailer WHERE trailer_movieId =:movieId")
    void deleteTrailerById(long movieId);

    @Query("SELECT * FROM trailer WHERE trailer_movieId = :id")
    LiveData<List<Trailer>> loadTrailerById(long id);

}
