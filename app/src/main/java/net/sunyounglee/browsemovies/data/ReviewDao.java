package net.sunyounglee.browsemovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.sunyounglee.browsemovies.models.Review;

import java.util.List;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM review ORDER BY reveviewId")
    LiveData<List<Review>> loadAllReviews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReview(Review review);

    @Query("DELETE FROM review WHERE review_movieId =:movieId")
    void deleteReviewById(long movieId);

    @Query("SELECT * FROM review WHERE review_movieId = :id")
    LiveData<List<Review>> loadReviewById(long id);

}
