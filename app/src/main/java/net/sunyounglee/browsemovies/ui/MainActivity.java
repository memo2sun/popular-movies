package net.sunyounglee.browsemovies.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.data.AppDatabase;
import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.databinding.ActivityMainBinding;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.repositories.MainViewRepository;
import net.sunyounglee.browsemovies.viewModels.MainViewModel;
import net.sunyounglee.browsemovies.viewModels.MainViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String MOVIE_INTENT_EXTRA = "MOVIE_DATA";

    private static final String MENU_ID = "menu_id";
    private int menuId;

    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter mMovieRecyclerViewAdapter;
    private TextView mErrorMessage;
    private ProgressBar mLoadingIndicator;
    private MainViewModel mainViewModel;

    private boolean savedInstanceStateBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            savedInstanceStateBundle = true;
            menuId = savedInstanceState.getInt(MENU_ID);
        }
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        //   binding.setLifecycleOwner(this);

        AppDatabase mDb = AppDatabase.getInstance(this);
        MovieDao movieDao = mDb.movieDao();
        MainViewRepository mainViewRepository = MainViewRepository.getInstance(movieDao);
        MainViewModelFactory mainViewModelFactory = new MainViewModelFactory(getApplication(), mainViewRepository);
        mainViewModel = new ViewModelProvider(this, mainViewModelFactory).get(MainViewModel.class);

        // binding.setMainViewModel(mainViewModel);
        GridLayoutManager mGridLayoutManager;

        mRecyclerView = binding.rvMovieList;
        mErrorMessage = binding.tvErrorMessage;

        int mNumberOfColumns;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mNumberOfColumns = 3;
        } else {
            mNumberOfColumns = 4;
        }

        mGridLayoutManager = new GridLayoutManager(this, mNumberOfColumns);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mMovieRecyclerViewAdapter);

        mLoadingIndicator = binding.pbLoadingIndicator;

        showLoading();

        setupViewModel();
    }

    private void showMovieDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void setupViewModel() {
        if (!isNetworkAvailable(getApplication())) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mErrorMessage.setVisibility(View.VISIBLE);
        }
        if (savedInstanceStateBundle) {
            switch (menuId) {
                case R.id.sort_by_popularity:
                    showPopularMovies();
                    break;
                case R.id.sort_by_rate:
                    showTopRatedMovies();
                    break;
                case R.id.sort_by_favorite:
                    showFavoriteMovies();
                    break;
            }
        } else {
            showPopularMovies();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        menuId = item.getItemId();
        switch (menuId) {
            case R.id.sort_by_popularity:
                showPopularMovies();
                return true;
            case R.id.sort_by_rate:
                showTopRatedMovies();
                return true;
            case R.id.sort_by_favorite:
                showFavoriteMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopularMovies() {
        mMovieRecyclerViewAdapter.setMovieData(null);
        mainViewModel.getPopularMovies().observe(this, moviePageObject -> {
            Log.d(TAG, "Updating list of movies from POPULAR LiveData in ViewModel");
            List<Movie> movies = moviePageObject.getResultList();
            mMovieRecyclerViewAdapter.setMovieData(movies);
            showMovieDataView();
        });
    }

    private void showTopRatedMovies() {
        mMovieRecyclerViewAdapter.setMovieData(null);
        mainViewModel.getTopRatedMovies().observe(this, moviePageObject -> {
            Log.d(TAG, "Updating list of movies from Top Rated LiveData in ViewModel");
            List<Movie> movies = moviePageObject.getResultList();
            mMovieRecyclerViewAdapter.setMovieData(movies);
            showMovieDataView();
        });
    }

    private void showFavoriteMovies() {
        mMovieRecyclerViewAdapter.setMovieData(null);
        mainViewModel.getFavoriteMovies().observe(this, movies -> {
            Log.d(TAG, "Updating list of movies from favorite LiveData in ViewModel");
            if (movies != null) {
                mMovieRecyclerViewAdapter.setMovieData(movies);
                showMovieDataView();
            }
        });
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MOVIE_INTENT_EXTRA, movie);
        startActivity(intent);
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = null;
        boolean connected;
        if (cm != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Network network = cm.getActiveNetwork();
                if (network != null) {
                    networkCapabilities = cm.getNetworkCapabilities(network);
                } else {
                    return false;
                }
            } else {
                Network[] networks = cm.getAllNetworks();
                for (int i = 0; i < networks.length && networkCapabilities == null; i++) {
                    networkCapabilities = cm.getNetworkCapabilities(networks[i]);
                }
            }
            assert networkCapabilities != null;
            connected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            Log.d(TAG, "network boolean: " + connected);
            return connected;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MENU_ID, menuId);
    }
}