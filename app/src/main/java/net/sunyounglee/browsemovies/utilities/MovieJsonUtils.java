package net.sunyounglee.browsemovies.utilities;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.models.MoviePageObject;
import net.sunyounglee.browsemovies.models.ReviewPageObject;
import net.sunyounglee.browsemovies.models.TrailerPageObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieJsonUtils {

    private static final String TAG = MovieJsonUtils.class.getSimpleName();
    private static String API_KEY;

    private static MovieAPIService service;

    public static LiveData<MoviePageObject> loadMovieDataFromServer(int sortBy, Context context) {
        API_KEY = context.getString(R.string.my_api_key);
        MutableLiveData<MoviePageObject> movieData = new MutableLiveData<>();
        service = RetrofitUtil.getRetrofitInstance().create(MovieAPIService.class);
        Call<MoviePageObject> call = null;
        switch (sortBy) {
            case 1:
                call = service.movieListByPopularity(API_KEY);
                break;
            case 2:
                call = service.movieListByTopRated(API_KEY);
                break;
        }

        call.enqueue(new Callback<MoviePageObject>() {
            @Override
            public void onResponse(Call<MoviePageObject> call, Response<MoviePageObject> response) {
                if (response.isSuccessful()) {
                    MoviePageObject moviePageObject = response.body();
                    movieData.setValue(moviePageObject);
                } else {
                    //   showErrorMessage();
                    Log.d(TAG, "response code: " + response.code());
                    //     assert response.errorBody() != null;
                    String data = response.errorBody().toString();
                    try {
                        JSONObject jObjError = new JSONObject(data);
                        Log.d(TAG, "error " + jObjError.getString("message"));
                    } catch (Exception e) {
                        Log.d(TAG, "exception " + e.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<MoviePageObject> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return movieData;
    }


    public static LiveData<ReviewPageObject> loadReviewDataFromServer(Context context, long movieId) {
        API_KEY = context.getString(R.string.my_api_key);
        MutableLiveData<ReviewPageObject> reviewData = new MutableLiveData<>();
        Call<ReviewPageObject> call = service.reviewList(movieId, API_KEY);
        call.enqueue(new Callback<ReviewPageObject>() {

            @Override
            public void onResponse(Call<ReviewPageObject> call, Response<ReviewPageObject> response) {
                if (response.isSuccessful()) {
                    ReviewPageObject reviewPageObject = response.body();
                    reviewData.setValue(reviewPageObject);

                } else {
                    Log.d(TAG, "response code: " + response.code());
                    //     assert response.errorBody() != null;
                    String data = response.errorBody().toString();
                    try {
                        JSONObject jObjError = new JSONObject(data);
                        Log.d(TAG, "error " + jObjError.getString("message"));
                    } catch (Exception e) {
                        Log.d(TAG, "exception " + e.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewPageObject> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return reviewData;
    }

    public static LiveData<TrailerPageObject> loadTrailerDataFromServer(Context context, long movieId) {
        API_KEY = context.getString(R.string.my_api_key);
        MutableLiveData<TrailerPageObject> trailerData = new MutableLiveData<>();
        Call<TrailerPageObject> call = service.trailerList(movieId, API_KEY);
        call.enqueue(new Callback<TrailerPageObject>() {

            @Override
            public void onResponse(Call<TrailerPageObject> call, Response<TrailerPageObject> response) {
                if (response.isSuccessful()) {
                    TrailerPageObject trailerPageObject = response.body();
                    trailerData.setValue(trailerPageObject);
                } else {
                    //   showErrorMessage();
                    Log.d(TAG, "response code: " + response.code());
                    String data = response.errorBody().toString();
                    try {
                        JSONObject jObjError = new JSONObject(data);
                        Log.d(TAG, "error " + jObjError.getString("message"));
                    } catch (Exception e) {
                        Log.d(TAG, "exception " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<TrailerPageObject> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return trailerData;
    }
}
